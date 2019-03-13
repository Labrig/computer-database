package fr.excilys.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.dto.ComputerDTO.ComputerDTOBuilder;
import fr.excilys.exceptions.DAOException;
import fr.excilys.exceptions.mapping.MappingException;
import fr.excilys.exceptions.validation.ValidationException;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.service.ComputerService;

/**
 * Servlet implementation class DeleteComputerServlet
 * @author Matheo
 */
@Controller
@RequestMapping("/DeleteComputer")
public class DeleteComputerServlet {
	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private ComputerMapper computerMapper;
	
	private static Logger logger = LoggerFactory.getLogger(DeleteComputerServlet.class);
	
	@PostMapping
	protected ModelAndView doPost(WebRequest request, ModelAndView modelView) {
		String[] computersId = request.getParameter("selection").split(",");
		for(String idComputer : computersId) {
			ComputerDTO dto = new ComputerDTOBuilder().setId(idComputer).build();
			try {
				computerService.delete(computerMapper.mapDTOInObject(dto).getId());
				logger.info("The computer with the id {} has been deleted", idComputer);
			} catch (MappingException | ValidationException | DAOException e) {
				logger.error(e.getMessage(), e);
				modelView.addObject("error", e.getMessage());
			}
		}
		logger.info("Redirect to dashboard.jsp");
		modelView.setViewName("redirect:/");
		return modelView;
	}

}
