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

import fr.excilys.dto.CompanyDTO;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.dto.ComputerDTO.ComputerDTOBuilder;
import fr.excilys.exceptions.ValidationException;
import fr.excilys.exceptions.DAOException;
import fr.excilys.exceptions.MappingException;
import fr.excilys.mapper.CompanyMapper;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.service.CompanyService;
import fr.excilys.service.ComputerService;
import fr.excilys.validator.ComputerValidator;

/**
 * Servlet implementation class EditComputerServlet
 * @author Matheo
 */
@WebServlet("/EditComputer")
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private ComputerMapper computerMapper;
	
	@Autowired
	private ComputerValidator computerValidator;
	
	private Logger logger = LoggerFactory.getLogger(EditComputerServlet.class);
	
	@Override
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerDTO dto = new ComputerDTOBuilder().setId(request.getParameter("idEditComputer")).build();
		try {
			dto = computerMapper.mapObjectInDTO(computerService.find(computerMapper.mapDTOInObject(dto).getId()));
			List<CompanyDTO> companies = new ArrayList<>();
			for(Company company : companyService.list())
				companies.add(companyMapper.mapObjectInDTO(company));
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
			Computer computer = computerMapper.mapDTOInObject(dto);
			computerValidator.verifyIntroBeforeDisco(computer);
			computerService.update(computer);
			logger.info("The computer "+computer+" has been created");
		} catch (ValidationException | MappingException | DAOException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
		}
		logger.info("Redirect to dashboard.jsp");
		this.getServletContext().getRequestDispatcher("/").forward(request, response);
	}

}
