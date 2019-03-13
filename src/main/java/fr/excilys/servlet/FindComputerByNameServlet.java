package fr.excilys.servlet;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.exceptions.DAOException;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Computer;
import fr.excilys.service.ComputerService;

/**
 * Servlet implementation class FindComputerByNameServlet
 * @author Matheo
 */
@Controller
@RequestMapping("/FindComputerByName")
public class FindComputerByNameServlet {
	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private ComputerMapper computerMapper;
	
	private static Logger logger = LoggerFactory.getLogger(FindComputerByNameServlet.class);
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@GetMapping
	protected ModelAndView doGet(WebRequest request, ModelAndView modelView) {
		try {
			List<ComputerDTO> computers = new ArrayList<>();
			for(Computer computer : computerService.listByName(request.getParameter("search")))
				computers.add(computerMapper.mapObjectInDTO(computer));
			modelView.addObject("computers", computers);
			modelView.addObject("totalComputer", computers.size());
		} catch (DAOException e) {
			logger.warn(e.getMessage(), e);
		}
		logger.info("Redirect to dashboard.jsp");
		modelView.setViewName("dashboard");
		return modelView;
	}

}
