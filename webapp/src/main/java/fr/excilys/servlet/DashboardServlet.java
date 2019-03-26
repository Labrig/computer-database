package fr.excilys.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.service.ComputerService;

/**
 * Servlet implementation class DashboardServlet
 * @author Matheo
 */
@Controller
@RequestMapping("/")
public class DashboardServlet {

	private ComputerService computerService;
	
	private static Logger logger = LoggerFactory.getLogger(DashboardServlet.class);
	
	private DashboardServlet(ComputerService computerService) {
		this.computerService = computerService;
	}
	
	@GetMapping
	public ModelAndView doGet(@RequestParam(value = "pageNumber", defaultValue = "1") String pageNumber, 
			@RequestParam(value = "pageSize", defaultValue = "50") String pageSize,
			@RequestParam(value = "error", required = false) String error, ModelAndView modelView) {
		modelView.addObject("computers", computerService.listWithPagination(PageRequest.of((Integer.valueOf(pageNumber)-1)*Integer.valueOf(pageSize), Integer.valueOf(pageSize))));
		int totalComputer = computerService.count();
		int pageTotal = totalComputer/Integer.valueOf(pageSize) + (totalComputer%Integer.valueOf(pageSize) != 0 ? 1 : 0);
		modelView.addObject("pageSize", pageSize);
		modelView.addObject("pageNumber", pageNumber);
		modelView.addObject("totalComputer", totalComputer);
		modelView.addObject("pageTotal", pageTotal);
		modelView.addObject("error", error);
		logger.info("Redirect to dashboard.jsp");
		modelView.setViewName("dashboard");
		return modelView;
	}

}
