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
	
	public void execute(String message) throws NotCommandeException {
		Computer computer;
		switch(message) {
		case "/l company":
			List<Company> companys = this.companyManager.listCompany();
			for(Company company : companys) {
				System.out.println(company.toString());
			}
			break;
		case "/l computer":
			List<Computer> computers = this.computerManager.listComputer();
			for(Computer comput : computers) {
				System.out.println(comput.toString());
			}
			break;
		case "/c computer":
			String[] attributesC = {"name","intro","disco","idCompany"};
			computer = this.fillComputerField(attributesC);
			try {
				this.computerManager.createComputer(computer);
			} catch(Exception e) {
				throw new NotCommandeException("Can not create this computer");
			}
			break;
		case "/f computer":
			String[] attributesR = {"id"};
			computer = this.computerManager.findComputer(this.fillComputerField(attributesR).getId());
			System.out.println(computer == null ? "Computer not found" : computer.toString());
			break;
		case "/u computer":
			String[] attributesU = {"id","name","intro","disco","idCompany"};
			computer = this.fillComputerField(attributesU);
			try {
				this.computerManager.updateComputer(computer);
			} catch(Exception e) {
				throw new NotCommandeException("Can not update this computer");
			}
			break;
		case "/d computer":
			String[] attributesD = {"id"};
			computer = this.fillComputerField(attributesD);
			try {
				this.computerManager.deleteComputer(computer);
			} catch(Exception e) {
				throw new NotCommandeException("Can not delete this computer");
			}
			break;
		case "/h":
			help();
			break;
		default:
			throw new NotCommandeException("La commande n'existe pas");
		}
	}

	private void help() {
		System.out.println("Command :");
		System.out.println("-> /l company (list of the company)");
		System.out.println("-> /l computer (list of the computer)");
		System.out.println("-> /f computer (find a computer computer)");
		System.out.println("-> /c computer (create a computer)");
		System.out.println("-> /d computer (delete a computer)");
		System.out.println("-> /u computer (update a computer)");
		System.out.println();
		System.out.println("Computer options :");
		System.out.println("* id (the id of a computer)");
		System.out.println("* name (the name of a computer)");
		System.out.println("* intro (the introduction date of a computer, YYYY-MM-DD format)");
		System.out.println("* disco (the discontinued date of a computer, YYYY-MM-DD format)");
		System.out.println("* idCompany (the id of the company that product a computer");
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
						throw new NotCommandeException("id is not a number");
					}
					break;
				default:
				}
			}
		}
		return computer;
	}
}
