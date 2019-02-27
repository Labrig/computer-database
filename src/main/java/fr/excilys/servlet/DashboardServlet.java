package fr.excilys.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Computer;
import fr.excilys.service.ServiceFactory;

/**
 * Servlet implementation class DashboardServlet
 * @author Matheo
 */
@WebServlet("/")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
			for(Computer computer : ServiceFactory.getInstance().getComputerService().listWithPagination((Integer.valueOf(pageNumber)-1)*Integer.valueOf(pageSize), Integer.valueOf(pageSize)))
				computers.add(ComputerMapper.getInstance().mapObjectInDTO(computer));
			request.setAttribute("computers", computers);
			request.setAttribute("totalComputer", ServiceFactory.getInstance().getComputerService().count());
			request.setAttribute("pageNumber", pageNumber);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
