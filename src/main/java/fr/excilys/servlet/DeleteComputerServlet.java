package fr.excilys.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.exceptions.DTOException;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.service.ServiceFactory;

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
		for(String idComputer : computersId) {
			ComputerDTO dto = new ComputerDTO();
			dto.setId(idComputer);
			try {
				ServiceFactory.getInstance().getComputerService().delete(ComputerMapper.getInstance().mapDTOInObject(dto).getId());
			} catch (SQLException | DTOException e) {
				request.setAttribute("error", e.getMessage());
			}
		}
		doGet(request, response);
	}

}
