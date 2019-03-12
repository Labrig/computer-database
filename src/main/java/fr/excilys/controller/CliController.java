package fr.excilys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import fr.excilys.dto.CompanyDTO;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.exceptions.DAOException;
import fr.excilys.exceptions.NotCommandeException;
import fr.excilys.exceptions.mapping.CompanyMappingException;
import fr.excilys.exceptions.mapping.ComputerMappingException;
import fr.excilys.exceptions.mapping.MappingException;
import fr.excilys.exceptions.validation.ComputerValidationException;
import fr.excilys.mapper.CompanyMapper;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.service.CompanyService;
import fr.excilys.service.ComputerService;
import fr.excilys.view.CliView;

/**
 * Read and treat the user command
 * @author Matheo
 */
@Controller
public class CliController {
	
	//TODO change this with enum
	private static final String ID_COMPANY = "idCompany";

	private static final String DISCO = "disco";

	private static final String INTRO = "intro";

	private static final String NAME = "name";

	private static final String ID = "id";

	@Autowired
	private CliView view;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private ComputerMapper computerMapper;
	
	private CliController() { }
	
	/**
	 * @return the view
	 */
	public CliView getView() {
		return view;
	}

	/**
	 * @param message the command to threat
	 * @throws NotCommandeException
	 * @throws ComputerValidationException
	 * @throws MappingException
	 */
	public void executeCommand(String message) throws NotCommandeException, ComputerValidationException, MappingException {
		StringBuilder sb = new StringBuilder();
		switch(message) {
		case "/l company":
			listCompany(sb);
			break;
		case "/d company":
			deleteCompany(sb);
			break;
		case "/l computer":
			listComputer(sb);
			break;
		case "/c computer":
			createComputer(sb);
			break;
		case "/f computer":
			findComputer(sb);
			break;
		case "/u computer":
			updateComputer(sb);
			break;
		case "/d computer":
			deleteComputer(sb);
			break;
		case "/h":
			this.view.displayHelp();
			break;
		default:
			throw new NotCommandeException("La commande n'existe pas");
		}
		this.view.displayResultCommand(sb.toString());
	}
	
	private void deleteCompany(StringBuilder sb) throws CompanyMappingException, ComputerValidationException, NotCommandeException {
		String[] attributesD = {ID};
		try {
			this.companyService.delete(this.fillCompanyField(attributesD).getId());
			sb.append("Company as been deleted");
		} catch(DAOException e) {
			throw new NotCommandeException("Can not delete this company");
		}
	}

	/**
	 * @param sb the StringBuilder need to construct the answer
	 * @throws NotCommandeException
	 * @throws ComputerValidationException
	 * @throws MappingException
	 */
	private void deleteComputer(StringBuilder sb) throws NotCommandeException, ComputerValidationException, ComputerMappingException {
		String[] attributesD = {ID};
		try {
			this.computerService.delete(this.fillComputerField(attributesD).getId());
			sb.append("Computer as been deleted");
		} catch(DAOException e) {
			throw new NotCommandeException("Can not delete this computer");
		}
	}

	/**
	 * @param sb the StringBuilder need to construct the answer
	 * @throws NotCommandeException
	 * @throws ComputerValidationException
	 * @throws MappingException
	 */
	private void updateComputer(StringBuilder sb) throws NotCommandeException, ComputerValidationException, ComputerMappingException {
		String[] attributesU = {ID,NAME,INTRO,DISCO,ID_COMPANY};
		Computer computer = this.fillComputerField(attributesU);
		try {
			this.computerService.update(computer);
			sb.append("Computer as been updated");
		} catch(DAOException e) {
			throw new NotCommandeException("Can not update this computer");
		}
	}

	/**
	 * @param sb the StringBuilder need to construct the answer
	 * @throws NotCommandeException
	 * @throws ComputerValidationException
	 * @throws MappingException
	 */
	private void findComputer(StringBuilder sb) throws NotCommandeException, ComputerValidationException, ComputerMappingException {
		String[] attributesR = {ID};
		try {
			Computer computer = this.computerService.find(this.fillComputerField(attributesR).getId());
			sb.append(computer.toString());
		} catch(DAOException e) {
			throw new NotCommandeException("Computer not found");
		}
	}

	/**
	 * @param sb the StringBuilder need to construct the answer
	 * @throws NotCommandeException
	 * @throws ComputerValidationException
	 * @throws MappingException
	 */
	private void createComputer(StringBuilder sb) throws NotCommandeException, ComputerValidationException, ComputerMappingException {
		String[] attributesC = {NAME,INTRO,DISCO,ID_COMPANY};
		Computer computer = this.fillComputerField(attributesC);
		try {
			this.computerService.create(computer);
			sb.append("Computer as been created");
		} catch(DAOException e) {
			throw new NotCommandeException("Can not create this computer");
		}
	}

	/**
	 * @param sb the StringBuilder need to construct the answer
	 * @throws NotCommandeException
	 */
	private void listComputer(StringBuilder sb) throws NotCommandeException {
		try {
			List<Computer> computers = this.computerService.list();
			for(Computer comput : computers) {
				sb.append(comput.toString()+"\n");
			}
		} catch (DAOException e) {
			throw new NotCommandeException("Can not list the computers");
		}
	}

	/**
	 * @param sb the StringBuilder need to construct the answer
	 * @throws NotCommandeException
	 */
	private void listCompany(StringBuilder sb) throws NotCommandeException {
		try {
			List<Company> companies = this.companyService.list();
			for(Company company : companies) {
				sb.append(company.toString()+"\n");
			}
		} catch (DAOException e) {
			throw new NotCommandeException("Can not list the companies");
		}
	}
	
	/**
	 * Request the user to fill the computer attribute to complete his command
	 * 
	 * @param attributes the attributes to fill in computer
	 * @return the computer fill with the user answer
	 * @throws ComputerValidationException
	 * @throws MappingException
	 */
	public Computer fillComputerField(String[] attributes) throws ComputerMappingException {
		ComputerDTO dto = new ComputerDTO();
		for(String attribute : attributes) {
			String value = this.view.requestAttribute(attribute);
			if(!"".equals(value)) {
				switch(attribute) {
				case ID:
					dto.setId(value);
					break;
				case NAME:
					dto.setName(value);
					break;
				case INTRO:
					dto.setIntroduced(value);
					break;
				case DISCO:
					dto.setDiscontinued(value);
					break;
				case ID_COMPANY:
					dto.setCompanyId(value);
					break;
				default:
					throw new ComputerMappingException();
				}
			}
		}
		Computer computer = this.computerMapper.mapDTOInObject(dto);
		return computer;
	}
	
	/**
	 * Request the user to fill the company attribute to complete his command
	 * 
	 * @param attributes the attributes to fill in company
	 * @return the company fill with the user answer
	 * @throws MappingException
	 * @throws ComputerValidationException
	 */
	public Company fillCompanyField(String[] attributes) throws CompanyMappingException {
		CompanyDTO dto = new CompanyDTO();
		for(String attribute : attributes) {
			String value = this.view.requestAttribute(attribute);
			if(!"".equals(value)) {
				switch(attribute) {
				case ID:
					dto.setId(value);
					break;
				case NAME:
					dto.setName(value);
					break;
				default:
					throw new CompanyMappingException();
				}
			}
		}
		return this.companyMapper.mapDTOInObject(dto);
	}
}
