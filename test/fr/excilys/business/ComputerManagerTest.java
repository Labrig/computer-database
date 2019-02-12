package fr.excilys.business;

import java.util.List;

import org.junit.Test;

import fr.excilys.model.Computer;

public class ComputerManagerTest {

	@Test
	public void testFindComputer() {
		ComputerManager manager = ManagerFactory.getInstance().getComputerManager();
		Computer computer = manager.findComputer(1L);
		System.out.println(computer.toString());
	}
	
	@Test
	public void testCreateUpdateDeleteComputer() {
		ComputerManager manager = ManagerFactory.getInstance().getComputerManager();
		Computer computer = new Computer();
		computer.setName("test");
		manager.createComputer(computer);
		List<Computer> computers = manager.listComputer();
		computer = computers.get(computers.size()-1);
		System.out.println(computer.toString());
		computer.setName("test2");
		computer.setCompany(ManagerFactory.getInstance().getCompanyManager().findCompany(1));
		manager.updateComputer(computer);
		computers = manager.listComputer();
		computer = computers.get(computers.size()-1);
		System.out.println(computer.toString());
		manager.deleteComputer(computer);
		computers = manager.listComputer();
		computer = computers.get(computers.size()-1);
		System.out.println(computer.toString());
	}

}
