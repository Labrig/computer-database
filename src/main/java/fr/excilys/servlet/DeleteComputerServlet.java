package fr.excilys.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.exceptions.ComputerFormatException;
import fr.excilys.model.Computer;
import fr.excilys.service.ServiceFactory;
import fr.excilys.validator.ComputerValidator;

/**
 * Servlet implementation class DeleteComputerServlet
 */
@WebServlet("/DeleteComputer")
public class DeleteComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] computersId = request.getParameter("selection").split(",");
		Computer computer = new Computer();
		for(String idComputer : computersId) {
			try {
				ComputerValidator.getInstance().verifyId(computer, idComputer);
				ServiceFactory.getInstance().getComputerService().delete(computer.getId());
			} catch (ComputerFormatException | SQLException e) {
				request.setAttribute("error", e.getMessage());
			}
		}
		doGet(request, response);
	}

}
