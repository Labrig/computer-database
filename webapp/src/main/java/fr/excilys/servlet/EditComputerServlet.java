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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.dto.ComputerDTO.ComputerDTOBuilder;
import fr.excilys.exception.ServiceException;
import fr.excilys.exception.ComputerMappingException;
import fr.excilys.exception.MappingException;
import fr.excilys.exception.ComputerValidationException;
import fr.excilys.exception.ValidationException;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.service.CompanyService;
import fr.excilys.service.ComputerService;

/**
 * Servlet implementation class EditComputerServlet
 * @author Matheo
 */
@Controller
@RequestMapping("/EditComputer")
public class EditComputerServlet {

	private CompanyService companyService;
	private ComputerService computerService;
	private ComputerMapper computerMapper;
	
	private static Logger logger = LoggerFactory.getLogger(EditComputerServlet.class);
	
	private EditComputerServlet(CompanyService companyService, ComputerService computerService, ComputerMapper computerMapper) {
		this.companyService = companyService;
		this.computerService = computerService;
		this.computerMapper = computerMapper;
	}
	
	@ModelAttribute("dto")
	public ComputerDTO editingDTO(@RequestParam(value = "idEditComputer") String idComputer) throws ComputerValidationException, ComputerMappingException, ServiceException {
		ComputerDTO dto = new ComputerDTOBuilder().setId(idComputer).build();
		return computerService.find(computerMapper.mapDTOInObject(dto).getId());
	}
	
	@GetMapping
	public ModelAndView doGet(ModelAndView modelView) {
		modelView.addObject("companies", companyService.list());
		modelView.setViewName("editComputer");
		logger.info("Redirect to editComputer.jsp");
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
				computerService.update(dto);
				logger.info("The computer {} has been created", dto);
			} catch (ValidationException | MappingException | ServiceException e) {
				logger.error(e.getMessage(), e);
				modelView.addObject("error", e.getMessage());
			}
			logger.info("Redirect to dashboard.jsp");
			modelView.setViewName("redirect:/");
			return modelView;
        }
	}

}
