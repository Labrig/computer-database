package fr.excilys.servlet;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.dto.ComputerDTO;
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
	
	private ComputerService computerService;
	private ComputerMapper computerMapper;
	
	private static Logger logger = LoggerFactory.getLogger(FindComputerByNameServlet.class);
	
	private FindComputerByNameServlet(ComputerService computerService, ComputerMapper computerMapper) {
		this.computerService = computerService;
		this.computerMapper = computerMapper;
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@GetMapping
	public ModelAndView doGet(@RequestParam(value = "search", defaultValue = "") String search, ModelAndView modelView) {
		List<ComputerDTO> computers = new ArrayList<>();
		for(Computer computer : computerService.listByName(search))
			computers.add(computerMapper.mapObjectInDTO(computer));
		modelView.addObject("computers", computers);
		modelView.addObject("totalComputer", computers.size());
		logger.info("Redirect to dashboard.jsp");
		modelView.setViewName("dashboard");
		return modelView;
	}

}
