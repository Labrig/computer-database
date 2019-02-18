package fr.excilys.controller;

import java.util.List;

import fr.excilys.exceptions.ComputerFormatException;
import fr.excilys.exceptions.NotCommandeException;
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
	
	private static CompanyService companyService = ServiceFactory.getCompanyService();
	private static ComputerService computerService = ServiceFactory.getComputerService();
	
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

	public void executeCommand(String message) throws NotCommandeException, ComputerFormatException {
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

	private void deleteComputer(StringBuilder sb) throws NotCommandeException, ComputerFormatException {
		String[] attributesD = {"id"};
		Computer computer = this.fillComputerField(attributesD);
		try {
			computerService.deleteComputer(computer);
			sb.append("Computer as been deleted");
		} catch(Exception e) {
			throw new NotCommandeException("Can not delete this computer");
		}
	}

	private void updateComputer(StringBuilder sb) throws NotCommandeException, ComputerFormatException {
		String[] attributesU = {"id","name","intro","disco","idCompany"};
		Computer computer = this.fillComputerField(attributesU);
		try {
			computerService.updateComputer(computer);
			sb.append("Computer as been updated");
		} catch(Exception e) {
			throw new NotCommandeException("Can not update this computer");
		}
	}

	private void findComputer(StringBuilder sb) throws NotCommandeException, ComputerFormatException {
		String[] attributesR = {"id"};
		Computer computer = computerService.findComputer(this.fillComputerField(attributesR).getId());
		sb.append(computer == null ? "Computer not found" : computer.toString());
	}

	private void createComputer(StringBuilder sb) throws NotCommandeException, ComputerFormatException {
		String[] attributesC = {"name","intro","disco","idCompany"};
		Computer computer = this.fillComputerField(attributesC);
		try {
			computerService.createComputer(computer);
			sb.append("Computer as been created");
		} catch(Exception e) {
			e.printStackTrace();
			throw new NotCommandeException("Can not create this computer");
		}
	}

	private void listComputer(StringBuilder sb) {
		List<Computer> computers = computerService.listComputer();
		for(Computer comput : computers) {
			sb.append(comput.toString()+"\n");
		}
	}

	private void listCompany(StringBuilder sb) {
		List<Company> companys = companyService.listCompany();
		for(Company company : companys) {
			sb.append(company.toString()+"\n");
		}
	}
	
	public Computer fillComputerField(String[] attributes) throws NotCommandeException, ComputerFormatException {
		Computer computer = new Computer();
		for(String attribute : attributes) {
			String value = this.view.requestAttribute(attribute);
			if(!"".equals(value)) {
				switch(attribute) {
				case "id":
					ComputerValidator.getInstance().verifyId(computer, value);
					break;
				case "name":
					computer.setName(value);
					break;
				case "intro":
					ComputerValidator.getInstance().verifyIntro(computer, value);
					ComputerValidator.getInstance().verifyIntroBeforeDisco(computer);
					break;
				case "disco":
					ComputerValidator.getInstance().verifyDisco(computer, value);
					ComputerValidator.getInstance().verifyIntroBeforeDisco(computer);
					break;
				case "idCompany":
					try {
						computer.setCompany(companyService.findCompany(Long.valueOf(value)));
					} catch(NumberFormatException e) {
						throw new NotCommandeException("id is not a number");
					}
					break;
				default:
					throw new NotCommandeException(attribute+" is not an attribute of computer");
				}
			}
		}
		return computer;
	}
}
