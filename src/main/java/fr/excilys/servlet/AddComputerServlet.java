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
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.dto.CompanyDTO;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.dto.ComputerDTO.ComputerDTOBuilder;
import fr.excilys.exceptions.DAOException;
import fr.excilys.exceptions.mapping.MappingException;
import fr.excilys.exceptions.validation.ValidationException;
import fr.excilys.mapper.CompanyMapper;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.service.CompanyService;
import fr.excilys.service.ComputerService;

/**
 * Servlet implementation class AddComputerServlet
 * @author Matheo
 */
@Controller
@RequestMapping("/AddComputer")
public class AddComputerServlet {

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private ComputerMapper computerMapper;
	
	private static Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);
	
	@GetMapping
	protected ModelAndView doGet(ModelAndView modelView) {
		try {
			List<CompanyDTO> companies = new ArrayList<>();
			for(Company company : companyService.list())
				companies.add(companyMapper.mapObjectInDTO(company));
			modelView.addObject("companies", companies);
		} catch (DAOException e) {
			logger.warn(e.getMessage(), e);
		}
		modelView.setViewName("addComputer");
		logger.info("Redirect to addComputer.jsp");
		return modelView;
	}

	@PostMapping
	protected ModelAndView doPost(WebRequest request, ModelAndView modelView) {
		ComputerDTO dto = new ComputerDTOBuilder().setName(request.getParameter("computerName"))
				.setIntroduced(request.getParameter("introduced"))
				.setDiscontinued(request.getParameter("discontinued"))
				.setCompanyId(request.getParameter("companyId")).build();
		try {
			Computer computer = computerMapper.mapDTOInObject(dto);
			computerService.create(computer);
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
