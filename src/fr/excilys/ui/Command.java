package fr.excilys.ui;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

import fr.excilys.business.CompanyManager;
import fr.excilys.business.ComputerManager;
import fr.excilys.business.ManagerFactory;
import fr.excilys.exceptions.NotCommandeException;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;

/**
 * Read and treat the user command
 * @author Matheo
 */
public class Command {
	
	private Scanner sc;
	
	private CompanyManager companyManager = ManagerFactory.getInstance().getCompanyManager();
	private ComputerManager computerManager = ManagerFactory.getInstance().getComputerManager();
	
	public Command(Scanner sc) {
		this.sc = sc;
	}
	
	public String execute(String message) throws NotCommandeException {
		StringBuilder sb = new StringBuilder();
		Computer computer;
		switch(message) {
		case "/l company":
			List<Company> companys = this.companyManager.listCompany();
			for(Company company : companys) {
				sb.append(company.toString()+"\n");
			}
			break;
		case "/l computer":
			List<Computer> computers = this.computerManager.listComputer();
			for(Computer comput : computers) {
				sb.append(comput.toString()+"\n");
			}
			break;
		case "/c computer":
			String[] attributesC = {"name","intro","disco","idCompany"};
			computer = this.fillComputerField(attributesC);
			try {
				this.computerManager.createComputer(computer);
				sb.append("The computer as been created");
			} catch(Exception e) {
				throw new NotCommandeException("Can not create this computer");
			}
			break;
		case "/f computer":
			String[] attributesR = {"id"};
			computer = this.computerManager.findComputer(this.fillComputerField(attributesR).getId());
			sb.append(computer == null ? "Computer not found" : computer.toString());
			break;
		case "/u computer":
			String[] attributesU = {"id","name","intro","disco","idCompany"};
			computer = this.fillComputerField(attributesU);
			try {
				this.computerManager.updateComputer(computer);
				sb.append("Computer updated");
			} catch(Exception e) {
				throw new NotCommandeException("Can not update this computer");
			}
			break;
		case "/d computer":
			String[] attributesD = {"id"};
			computer = this.fillComputerField(attributesD);
			try {
				this.computerManager.deleteComputer(computer);
				sb.append("The computer as been deleted");
			} catch(Exception e) {
				throw new NotCommandeException("Can not delete this computer");
			}
			break;
		case "/h":
			sb.append(help());
			break;
		default:
			throw new NotCommandeException("La commande n'existe pas");
		}
		return sb.toString();
	}

	private String help() {
		StringBuilder sb = new StringBuilder();
		sb.append("Command :\n");
		sb.append("-> /l company (list of the company)\n");
		sb.append("-> /l computer (list of the computer)\n");
		sb.append("-> /f computer (find a computer computer)\n");
		sb.append("-> /c computer (create a computer)\n");
		sb.append("-> /d computer (delete a computer)\n");
		sb.append("-> /u computer (update a computer)\n");
		sb.append("\n");
		sb.append("Computer options :\n");
		sb.append("* id (the id of a computer)\n");
		sb.append("* name (the name of a computer)\n");
		sb.append("* intro (the introduction date of a computer, YYYY-MM-DD format)\n");
		sb.append("* disco (the discontinued date of a computer, YYYY-MM-DD format)\n");
		sb.append("* idCompany (the id of the company that product a computer\n");
		return sb.toString();
	}
	
	public Computer fillComputerField(String[] attributes) throws NotCommandeException {
		Computer computer = new Computer();
		for(String attribute : attributes) {
			System.out.print("Enter the computer "+attribute+" : ");
			String value = this.sc.nextLine();
			if(!"".equals(value)) {
				switch(attribute) {
				case "id":
					try {
						computer.setId(Long.valueOf(value));
					} catch(NumberFormatException e) {
						throw new NotCommandeException("id is not a number");
					}
					break;
				case "name":
					computer.setName(value);
					break;
				case "intro":
					try {
						computer.setIntroduced(Timestamp.valueOf(value+" 00:00:00"));
					} catch(IllegalArgumentException e) {
						throw new NotCommandeException("the date is not at format yyyy-mm-dd");
					}
					break;
				case "disco":
					try {
						computer.setIntroduced(Timestamp.valueOf(value+" 00:00:00"));
					} catch(IllegalArgumentException e) {
						throw new NotCommandeException("the date is not at format yyyy-mm-dd");
					}
					break;
				case "idCompany":
					try {
						computer.setCompany(this.companyManager.findCompany(Long.valueOf(value)));
					} catch(NumberFormatException e) {
						throw new NotCommandeException("idCompany is not a number");
					}
					break;
				default:
					throw new NotCommandeException(attribute + " does not exist for the computer object");
				}
			}
		}
		return computer;
	}
}
