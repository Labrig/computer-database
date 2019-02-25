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

import fr.excilys.dto.CompanyDTO;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.exceptions.ComputerFormatException;
import fr.excilys.exceptions.DTOException;
import fr.excilys.mapper.CompanyMapper;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.service.ComputerService;
import fr.excilys.service.ServiceFactory;
import fr.excilys.validator.ComputerValidator;

/**
 * Servlet implementation class EditComputerServlet
 */
@WebServlet("/EditComputer")
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerDTO dto = new ComputerDTO();
		dto.setId(request.getParameter("idEditComputer"));
		try {
			dto = ComputerMapper.getInstance().mapObjectInDTO(ComputerService.getInstance().find(ComputerMapper.getInstance().mapDTOInObject(dto).getId()));
			List<CompanyDTO> companies = new ArrayList<>();
			for(Company company : ServiceFactory.getInstance().getCompanyService().list())
				companies.add(CompanyMapper.getInstance().mapObjectInDTO(company));
			request.setAttribute("companies", companies);
		} catch (SQLException | DTOException e) {
			System.out.println(e.getMessage());
		}
		request.setAttribute("editComputer", dto);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerDTO dto = new ComputerDTO();
		dto.setId(request.getParameter("computerId"));
		dto.setName(request.getParameter("computerName"));
		dto.setIntroduced(request.getParameter("introduced"));
		dto.setDiscontinued(request.getParameter("discontinued"));
		dto.setCompanyId(request.getParameter("companyId"));
		try {
			Computer computer = ComputerMapper.getInstance().mapDTOInObject(dto);
			ComputerValidator.getInstance().verifyIntroBeforeDisco(computer);
			ServiceFactory.getInstance().getComputerService().update(computer);
		} catch (ComputerFormatException | SQLException | DTOException e) {
			request.setAttribute("error", e.getMessage());
		}
		this.getServletContext().getRequestDispatcher("/").forward(request, response);
	}

}
