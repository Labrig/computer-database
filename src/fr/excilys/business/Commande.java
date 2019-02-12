package fr.excilys.business;

import java.sql.Timestamp;
import java.util.List;

import fr.excilys.exceptions.NotCommandeException;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;

public class Commande {
	
	public String execute(String message) throws NotCommandeException {
		StringBuilder result = new StringBuilder();
		String[] messageDecompose = message.split(" ");
		if(messageDecompose.length > 2) {
			switch(messageDecompose[1]) {
			case "computer":
				Computer computer = this.buildComputer(messageDecompose);
				result.append(this.readCommande(messageDecompose[0], computer));
				break;
			case "company":
				CompanyManager manager = ManagerFactory.getInstance().getCompanyManager();
				if(messageDecompose[0].equals("/l")) {
					List<Company> companys = manager.listCompany();
					for(Company company : companys) {
						result.append(company.toString());
						result.append("\n");
					}
				} else {
					throw new NotCommandeException("La commande n'existe pas");
				}
				break;
			default:
				throw new NotCommandeException("L'objet demmande n'existe pas");
			}
		} else {
			throw new NotCommandeException("La commande est invalide");
		}
		return result.toString();
	}

	private String readCommande(String commande, Computer computer)
			throws NotCommandeException {
		StringBuilder result = new StringBuilder();
		ComputerManager manager = ManagerFactory.getInstance().getComputerManager();
		switch(commande) {
		case "/l":
			List<Computer> computers = manager.listComputer();
			for(Computer comput : computers) {
				result.append(comput.toString());
				result.append("\n");
			}
			break;
		case "/c":
			manager.createComputer(computer);
			result.append("New computer create");
			break;
		case "/r":
			computer = manager.findComputer(computer.getId());
			result.append("Computer found :\n");
			result.append(computer.toString());
			break;
		case "/u":
			computer = manager.updateComputer(computer);
			result.append("Computer updated :\n");
			result.append(computer.toString());
			break;
		case "/d":
			manager.deleteComputer(computer);
			result.append("The computer as been deleted");
			break;
		default:
			throw new NotCommandeException("La commande n'existe pas");
		}
		return result.toString();
	}
	
	public Computer buildComputer(String[] messageDecompose) throws NotCommandeException {
		Computer computer = new Computer();
		for(int i = 2; i < messageDecompose.length; i++) {
			String[] argument = messageDecompose[i].split("=");
			if(argument.length == 2) {
				switch(argument[0]) {
				case "id":
					try {
						computer.setId(Long.valueOf(argument[1]));
					} catch(NumberFormatException e) {
						throw new NotCommandeException("La valeur de l'argument id est incorrect");
					}
					break;
				case "name":
					computer.setName(argument[1]);
					break;
				case "intro":
					try {
						computer.setIntroduced(Timestamp.valueOf(argument[1]));
					} catch(IllegalArgumentException e) {
						throw new NotCommandeException("La date n'est pas au format yyyy-[m]m-[d]d hh:mm:ss");
					}
					break;
				case "disco":
					try {
						computer.setDiscontinued(Timestamp.valueOf(argument[1]));
					} catch(IllegalArgumentException e) {
						throw new NotCommandeException("La date n'est pas au format yyyy-[m]m-[d]d hh:mm:ss");
					}
					break;
				case "idCompany":
					try {
						computer.setCompany(ManagerFactory.getInstance().getCompanyManager()
								.findCompany(Long.valueOf(argument[1])));
					} catch(NumberFormatException e) {
						throw new NotCommandeException("La valeur de l'argument idCompany est incorrect");
					}
					break;
				default:
					throw new NotCommandeException("Un des arguments specifie ne peut être identifie");
				}
			} else {
				throw new NotCommandeException("Un des arguments n'est pas valide");
			}
		}
		return computer;
	}
	
}
