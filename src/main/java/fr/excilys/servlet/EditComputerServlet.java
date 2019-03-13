package fr.excilys.servlet;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.dto.CompanyDTO;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.dto.ComputerDTO.ComputerDTOBuilder;
import fr.excilys.exceptions.DAOException;
import fr.excilys.exceptions.mapping.MappingException;
import fr.excilys.exceptions.validation.ComputerValidationException;
import fr.excilys.exceptions.validation.ValidationException;
import fr.excilys.mapper.CompanyMapper;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.service.CompanyService;
import fr.excilys.service.ComputerService;

/**
 * Servlet implementation class EditComputerServlet
 * @author Matheo
 */
@Controller
@RequestMapping("/EditComputer")
public class EditComputerServlet {

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private ComputerMapper computerMapper;
	
	private static Logger logger = LoggerFactory.getLogger(EditComputerServlet.class);
	
	@GetMapping
	protected ModelAndView doGet(@RequestParam(value = "idEditComputer", defaultValue = "1") String idComputer, ModelAndView modelView) {
		ComputerDTO dto = new ComputerDTOBuilder().setId(idComputer).build();
		try {
			dto = computerMapper.mapObjectInDTO(computerService.find(computerMapper.mapDTOInObject(dto).getId()));
			List<CompanyDTO> companies = new ArrayList<>();
			for(Company company : companyService.list())
				companies.add(companyMapper.mapObjectInDTO(company));
			modelView.addObject("companies", companies);
		} catch (MappingException | DAOException | ComputerValidationException e) {
			logger.warn(e.getMessage(), e);
		}
		modelView.addObject("editComputer", dto);
		modelView.setViewName("editComputer");
		logger.info("Redirect to editComputer.jsp");
		return modelView;
	}

	@PostMapping
	protected ModelAndView doPost(WebRequest request, ModelAndView modelView) {
		ComputerDTO dto = new ComputerDTOBuilder().setId(request.getParameter("computerId"))
				.setName(request.getParameter("computerName"))
				.setIntroduced(request.getParameter("introduced"))
				.setDiscontinued(request.getParameter("discontinued"))
				.setCompanyId(request.getParameter("companyId")).build();
		try {
			Computer computer = computerMapper.mapDTOInObject(dto);
			computerService.update(computer);
			logger.info("The computer {} has been created", computer);
		} catch (ValidationException | MappingException | DAOException e) {
			logger.error(e.getMessage(), e);
			modelView.addObject("error", e.getMessage());
		}
		logger.info("Redirect to dashboard.jsp");
		modelView.setViewName("redirect:/");
		return modelView;
	}

}
