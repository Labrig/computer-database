package fr.excilys.servlet;

import java.io.IOException;

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
import fr.excilys.dto.ComputerDTO.ComputerDTOBuilder;
import fr.excilys.exceptions.DAOException;
import fr.excilys.exceptions.MappingException;
import fr.excilys.exceptions.ValidationException;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.service.ComputerService;

/**
 * Servlet implementation class DeleteComputerServlet
 * @author Matheo
 */
@WebServlet("/DeleteComputer")
public class DeleteComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private ComputerMapper computerMapper;
	
	private Logger logger = LoggerFactory.getLogger(DeleteComputerServlet.class);

	@Override
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Redirect to dashboard.jsp");
		this.getServletContext().getRequestDispatcher("/").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] computersId = request.getParameter("selection").split(",");
		for(String idComputer : computersId) {
			ComputerDTO dto = new ComputerDTOBuilder().setId(idComputer).build();
			try {
				computerService.delete(computerMapper.mapDTOInObject(dto).getId());
				logger.info("The computer with the id "+idComputer+" has been deleted");
			} catch (MappingException | ValidationException | DAOException e) {
				logger.error(e.getMessage(), e);
				request.setAttribute("error", e.getMessage());
			}
		}
		doGet(request, response);
	}

}
