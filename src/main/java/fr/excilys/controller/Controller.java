package fr.excilys.controller;

import java.util.List;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.exceptions.ValidationException;
import fr.excilys.exceptions.DAOException;
import fr.excilys.exceptions.MappingException;
import fr.excilys.exceptions.NotCommandeException;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.service.CompanyService;
import fr.excilys.service.ComputerService;
import fr.excilys.service.ServiceFactory;
import fr.excilys.validator.ComputerValidator;
import fr.excilys.view.CliView;

/**
 * Read and treat the user command
 * @author Matheo
 */
public class Controller {
	
	private CliView view;
	
	private static CompanyService companyService = ServiceFactory.getInstance().getCompanyService();
	private static ComputerService computerService = ServiceFactory.getInstance().getComputerService();
	
	public Controller(CliView view) {
		this.view = view;
	}
	
	/**
	 * @return the view
	 */
	public CliView getView() {
		return view;
	}

	/**
	 * @param view the view to set
	 */
	public void setView(CliView view) {
		this.view = view;
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

	/**
	 * @param sb the StringBuilder need to construct the answer
	 * @throws NotCommandeException
	 * @throws ValidationException
	 * @throws MappingException
	 */
	private void deleteComputer(StringBuilder sb) throws NotCommandeException, ValidationException, MappingException {
		String[] attributesD = {"id"};
		try {
			computerService.delete(this.fillComputerField(attributesD).getId());
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
			computerService.update(computer);
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
			Computer computer = computerService.find(this.fillComputerField(attributesR).getId());
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
			computerService.create(computer);
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
			List<Computer> computers = computerService.list();
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
			List<Company> companies = companyService.list();
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
		Computer computer = ComputerMapper.getInstance().mapDTOInObject(dto);
		ComputerValidator.getInstance().verifyIntroBeforeDisco(computer);
		return computer;
	}
}
