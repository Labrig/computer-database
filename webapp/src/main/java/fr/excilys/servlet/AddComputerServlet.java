package fr.excilys.servlet;

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

import fr.excilys.dto.ComputerDTO;
import fr.excilys.dto.ComputerDTO.ComputerDTOBuilder;
import fr.excilys.exception.DAOException;
import fr.excilys.exception.MappingException;
import fr.excilys.exception.ValidationException;
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
	
	private static Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);
	
	private AddComputerServlet(CompanyService companyService, ComputerService computerService) {
		this.companyService = companyService;
		this.computerService = computerService;
	}
	
	@ModelAttribute("dto")
	public ComputerDTO newDTO() {
		return new ComputerDTOBuilder().build();
	}
	
	@GetMapping
	public ModelAndView doGet(ModelAndView modelView) {
		modelView.addObject("companies", companyService.list());
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
    			computerService.create(dto);
    			logger.info("The computer {} has been created", dto);
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
