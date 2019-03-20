package fr.excilys.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

	private CompanyService companyService;
	private ComputerService computerService;
	private CompanyMapper companyMapper;
	private ComputerMapper computerMapper;
	
	private static Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);
	
	private AddComputerServlet(CompanyService companyService, ComputerService computerService, CompanyMapper companyMapper,ComputerMapper computerMapper) {
		this.companyService = companyService;
		this.computerService = computerService;
		this.companyMapper = companyMapper;
		this.computerMapper = computerMapper;
	}
	
	@ModelAttribute("dto")
	public ComputerDTO newDTO() {
		return new ComputerDTOBuilder().build();
	}
	
	@GetMapping
	public ModelAndView doGet(ModelAndView modelView) {
		List<CompanyDTO> companies = new ArrayList<>();
		for(Company company : companyService.list())
			companies.add(companyMapper.mapObjectInDTO(company));
		modelView.addObject("companies", companies);
		modelView.setViewName("addComputer");
		logger.info("Redirect to addComputer.jsp");
		return modelView;
	}

	@PostMapping
	public ModelAndView doPost(@ModelAttribute("dto") @Valid ComputerDTO dto, BindingResult bindingResult, ModelAndView modelView) {
		if (bindingResult.hasErrors()) {
			modelView.setViewName("addComputer");
			logger.info("Redirect to addComputer.jsp");
			return modelView;
        } else {
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

}
