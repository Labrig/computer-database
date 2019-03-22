package fr.excilys.controller;

import java.util.List;

import org.springframework.stereotype.Controller;

import fr.excilys.dto.CompanyDTO;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.exception.DAOException;
import fr.excilys.exception.NotCommandeException;
import fr.excilys.exception.CompanyMappingException;
import fr.excilys.exception.ComputerMappingException;
import fr.excilys.exception.MappingException;
import fr.excilys.exception.ComputerValidationException;
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

	private CliView view;
	private CompanyService companyService;
	private ComputerService computerService;
	
	private CliController(CliView view, CompanyService companyService, ComputerService computerService) {
		this.view = view;
		this.companyService = companyService;
		this.computerService = computerService;
	}
	
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
			this.companyService.delete(Long.valueOf(this.fillCompanyField(attributesD).getId()));
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
			this.computerService.delete(Long.valueOf(this.fillComputerField(attributesD).getId()));
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
		ComputerDTO computer = this.fillComputerField(attributesU);
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
			ComputerDTO computer = this.computerService.find(Long.valueOf(this.fillComputerField(attributesR).getId()));
			sb.append(computer.toString());
		} catch(DAOException e) {
			throw new NotCommandeException(e.getMessage());
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
		ComputerDTO computer = this.fillComputerField(attributesC);
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
		List<ComputerDTO> computers = this.computerService.list();
		for(ComputerDTO comput : computers) {
			sb.append(comput.toString()+"\n");
		}
	}

	/**
	 * @param sb the StringBuilder need to construct the answer
	 * @throws NotCommandeException
	 */
	private void listCompany(StringBuilder sb) throws NotCommandeException {
		List<CompanyDTO> companies = this.companyService.list();
		for(CompanyDTO company : companies) {
			sb.append(company.toString()+"\n");
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
	public ComputerDTO fillComputerField(String[] attributes) throws ComputerMappingException {
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
		return dto;
	}
	
	/**
	 * Request the user to fill the company attribute to complete his command
	 * 
	 * @param attributes the attributes to fill in company
	 * @return the company fill with the user answer
	 * @throws MappingException
	 * @throws ComputerValidationException
	 */
	public CompanyDTO fillCompanyField(String[] attributes) throws CompanyMappingException {
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
		return dto;
	}
}
