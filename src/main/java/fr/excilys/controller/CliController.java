package fr.excilys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import fr.excilys.dto.CompanyDTO;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.exceptions.ValidationException;
import fr.excilys.exceptions.DAOException;
import fr.excilys.exceptions.MappingException;
import fr.excilys.exceptions.NotCommandeException;
import fr.excilys.mapper.CompanyMapper;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.service.ServiceFactory;
import fr.excilys.validator.ComputerValidator;
import fr.excilys.view.CliView;

/**
 * Read and treat the user command
 * @author Matheo
 */
@Controller
public class CliController {
	
	@Autowired
	private CliView view;
	
	@Autowired
	private ServiceFactory serviceFactory;
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private ComputerMapper computerMapper;
	
	@Autowired
	private ComputerValidator computerValidator;
	
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
	 * @throws ValidationException
	 * @throws MappingException
	 */
	public void executeCommand(String message) throws NotCommandeException, ValidationException, MappingException {
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
	
	private void deleteCompany(StringBuilder sb) throws MappingException, ValidationException, NotCommandeException {
		String[] attributesD = {"id"};
		try {
			this.serviceFactory.getCompanyService().delete(this.fillCompanyField(attributesD).getId());
			sb.append("Company as been deleted");
		} catch(DAOException e) {
			throw new NotCommandeException("Can not delete this company");
		}
	}

	/**
	 * @param sb the StringBuilder need to construct the answer
	 * @throws NotCommandeException
	 * @throws ValidationException
	 * @throws MappingException
	 */
	private void deleteComputer(StringBuilder sb) throws NotCommandeException, ValidationException, MappingException {
		String[] attributesD = {"id"};
		try {
			this.serviceFactory.getComputerService().delete(this.fillComputerField(attributesD).getId());
			sb.append("Computer as been deleted");
		} catch(DAOException e) {
			throw new NotCommandeException("Can not delete this computer");
		}
	}

	/**
	 * @param sb the StringBuilder need to construct the answer
	 * @throws NotCommandeException
	 * @throws ValidationException
	 * @throws MappingException
	 */
	private void updateComputer(StringBuilder sb) throws NotCommandeException, ValidationException, MappingException {
		String[] attributesU = {"id","name","intro","disco","idCompany"};
		Computer computer = this.fillComputerField(attributesU);
		try {
			this.serviceFactory.getComputerService().update(computer);
			sb.append("Computer as been updated");
		} catch(DAOException e) {
			throw new NotCommandeException("Can not update this computer");
		}
	}

	/**
	 * @param sb the StringBuilder need to construct the answer
	 * @throws NotCommandeException
	 * @throws ValidationException
	 * @throws MappingException
	 */
	private void findComputer(StringBuilder sb) throws NotCommandeException, ValidationException, MappingException {
		String[] attributesR = {"id"};
		try {
			Computer computer = this.serviceFactory.getComputerService().find(this.fillComputerField(attributesR).getId());
			sb.append(computer.toString());
		} catch(DAOException e) {
			throw new NotCommandeException("Computer not found");
		}
	}

	/**
	 * @param sb the StringBuilder need to construct the answer
	 * @throws NotCommandeException
	 * @throws ValidationException
	 * @throws MappingException
	 */
	private void createComputer(StringBuilder sb) throws NotCommandeException, ValidationException, MappingException {
		String[] attributesC = {"name","intro","disco","idCompany"};
		Computer computer = this.fillComputerField(attributesC);
		try {
			this.serviceFactory.getComputerService().create(computer);
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
			List<Computer> computers = this.serviceFactory.getComputerService().list();
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
			List<Company> companies = this.serviceFactory.getCompanyService().list();
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
	 * @throws ValidationException
	 * @throws MappingException
	 */
	public Computer fillComputerField(String[] attributes) throws MappingException, ValidationException {
		ComputerDTO dto = new ComputerDTO();
		for(String attribute : attributes) {
			String value = this.view.requestAttribute(attribute);
			if(!"".equals(value)) {
				switch(attribute) {
				case "id":
					dto.setId(value);
					break;
				case "name":
					dto.setName(value);
					break;
				case "intro":
					dto.setIntroduced(value);
					break;
				case "disco":
					dto.setDiscontinued(value);
					break;
				case "idCompany":
					dto.setCompanyId(value);
					break;
				default:
					throw new MappingException(attribute+" is not an attribute of computer");
				}
			}
		}
		Computer computer = this.computerMapper.mapDTOInObject(dto);
		this.computerValidator.verifyIntroBeforeDisco(computer);
		return computer;
	}
	
	/**
	 * Request the user to fill the company attribute to complete his command
	 * 
	 * @param attributes the attributes to fill in company
	 * @return the company fill with the user answer
	 * @throws MappingException
	 * @throws ValidationException
	 */
	public Company fillCompanyField(String[] attributes) throws MappingException, ValidationException {
		CompanyDTO dto = new CompanyDTO();
		for(String attribute : attributes) {
			String value = this.view.requestAttribute(attribute);
			if(!"".equals(value)) {
				switch(attribute) {
				case "id":
					dto.setId(value);
					break;
				case "name":
					dto.setName(value);
					break;
				default:
					throw new MappingException(attribute+" is not an attribute of company");
				}
			}
		}
		Company company = this.companyMapper.mapDTOInObject(dto);
		return company;
	}
}
