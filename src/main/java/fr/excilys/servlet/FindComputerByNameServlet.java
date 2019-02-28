package fr.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.exceptions.DAOException;
import fr.excilys.mapper.MapperFactory;
import fr.excilys.model.Computer;
import fr.excilys.service.ServiceFactory;

/**
 * Servlet implementation class FindComputerByNameServlet
 * @author Matheo
 */
@WebServlet("/FindComputerByName")
public class FindComputerByNameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<ComputerDTO> computers = new ArrayList<>();
			for(Computer computer : ServiceFactory.getInstance().getComputerService().listByName(request.getParameter("search")))
				computers.add(MapperFactory.getInstance().getComputerMapper().mapObjectInDTO(computer));
			request.setAttribute("computers", computers);
		} catch (DAOException e) {
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
