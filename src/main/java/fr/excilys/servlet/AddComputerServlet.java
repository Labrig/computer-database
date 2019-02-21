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
 * Servlet implementation class AddComputerServlet
 */
@WebServlet("/AddComputer")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setAttribute("companies", ServiceFactory.getInstance().getCompanyService().list());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Computer computer = new Computer();
		computer.setName(request.getParameter("computerName"));
		try {
			ComputerValidator.getInstance().verifyIntro(computer, request.getParameter("introduced"));
			ComputerValidator.getInstance().verifyDisco(computer, request.getParameter("discontinued"));
			ComputerValidator.getInstance().verifyIdCompany(computer, request.getParameter("companyId"));
			ComputerValidator.getInstance().verifyIntroBeforeDisco(computer);
			ServiceFactory.getInstance().getComputerService().create(computer);
			this.getServletContext().getRequestDispatcher("/").forward(request, response);
		} catch (ComputerFormatException | SQLException e) {
			request.setAttribute("error", e.getMessage());
			doGet(request, response);
		}
	}

}
