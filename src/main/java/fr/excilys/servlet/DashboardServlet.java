package fr.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.exceptions.DAOException;
import fr.excilys.mapper.MapperFactory;
import fr.excilys.model.Computer;
import fr.excilys.service.ServiceFactory;

/**
 * Servlet implementation class DashboardServlet
 * @author Matheo
 */
@WebServlet("/")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
	
	private Logger logger = LoggerFactory.getLogger(DashboardServlet.class);
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageNumber = request.getParameter("pageNumber");
		String pageSize = request.getParameter("pageSize");
		if (pageNumber != null) {
			request.getSession().setAttribute("pageNumber", pageNumber);
		} else if (pageSize != null) {
			request.getSession().setAttribute("pageSize", pageSize);
		} else {
			request.getSession().setAttribute("pageNumber", "1");
			request.getSession().setAttribute("pageSize", "50");
		}
		pageNumber = (String)request.getSession().getAttribute("pageNumber");
		pageSize = (String)request.getSession().getAttribute("pageSize");
		try {
			List<ComputerDTO> computers = new ArrayList<>();
			for(Computer computer : serviceFactory.getComputerService().listWithPagination((Integer.valueOf(pageNumber)-1)*Integer.valueOf(pageSize), Integer.valueOf(pageSize)))
				computers.add(MapperFactory.getInstance().getComputerMapper().mapObjectInDTO(computer));
			request.setAttribute("computers", computers);
			request.setAttribute("totalComputer", serviceFactory.getComputerService().count());
			request.setAttribute("pageNumber", pageNumber);
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
