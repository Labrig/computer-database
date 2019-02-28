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

import fr.excilys.dto.CompanyDTO;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.dto.ComputerDTO.ComputerDTOBuilder;
import fr.excilys.exceptions.ValidationException;
import fr.excilys.exceptions.DAOException;
import fr.excilys.exceptions.MappingException;
import fr.excilys.mapper.MapperFactory;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.service.ServiceFactory;
import fr.excilys.validator.ComputerValidator;

/**
 * Servlet implementation class EditComputerServlet
 * @author Matheo
 */
@WebServlet("/EditComputer")
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private static MapperFactory mapperFactory = MapperFactory.getInstance();
	
	private Logger logger = LoggerFactory.getLogger(EditComputerServlet.class);
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerDTO dto = new ComputerDTOBuilder().setId(request.getParameter("idEditComputer")).build();
		try {
			dto = mapperFactory.getComputerMapper().mapObjectInDTO(serviceFactory.getComputerService().find(mapperFactory.getComputerMapper().mapDTOInObject(dto).getId()));
			List<CompanyDTO> companies = new ArrayList<>();
			for(Company company : serviceFactory.getCompanyService().list())
				companies.add(mapperFactory.getCompanyMapper().mapObjectInDTO(company));
			request.setAttribute("companies", companies);
		} catch (MappingException | DAOException | ValidationException e) {
			logger.warn(e.getMessage(), e);
		}
		request.setAttribute("editComputer", dto);
		logger.info("Redirect to editComputer.jsp");
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerDTO dto = new ComputerDTOBuilder().setId(request.getParameter("computerId"))
				.setName(request.getParameter("computerName"))
				.setIntroduced(request.getParameter("introduced"))
				.setDiscontinued(request.getParameter("discontinued"))
				.setCompanyId(request.getParameter("companyId")).build();
		try {
			Computer computer = mapperFactory.getComputerMapper().mapDTOInObject(dto);
			ComputerValidator.getInstance().verifyIntroBeforeDisco(computer);
			serviceFactory.getComputerService().update(computer);
			logger.info("The computer "+computer+" has been created");
		} catch (ValidationException | MappingException | DAOException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
		}
		logger.info("Redirect to dashboard.jsp");
		this.getServletContext().getRequestDispatcher("/").forward(request, response);
	}

}
