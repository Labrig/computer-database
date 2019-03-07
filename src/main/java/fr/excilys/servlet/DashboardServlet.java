package fr.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.exceptions.DAOException;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Computer;
import fr.excilys.service.ComputerService;

/**
 * Servlet implementation class DashboardServlet
 * @author Matheo
 */
@WebServlet("/")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private ComputerMapper computerMapper;
	
	@Override
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
	}
	
	private Logger logger = LoggerFactory.getLogger(DashboardServlet.class);
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageNumber = request.getParameter("pageNumber");
		String pageSize = request.getParameter("pageSize");
		if (pageNumber == null || pageSize == null) {
			pageNumber = "1";
			pageSize = "50";
		}
		try {
			List<ComputerDTO> computers = new ArrayList<>();
			for(Computer computer : computerService.listWithPagination((Integer.valueOf(pageNumber)-1)*Integer.valueOf(pageSize), Integer.valueOf(pageSize)))
				computers.add(computerMapper.mapObjectInDTO(computer));
			request.setAttribute("computers", computers);
			int totalComputer = computerService.count();
			int pageTotal = totalComputer/Integer.valueOf(pageSize) + (totalComputer%Integer.valueOf(pageSize) != 0 ? 1 : 0);
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("pageNumber", pageNumber);
			request.setAttribute("totalComputer", totalComputer);
			request.setAttribute("pageTotal", pageTotal);
		} catch (DAOException e) {
			logger.warn(e.getMessage(), e);
		}
		logger.info("Redirect to dashboard.jsp");
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
