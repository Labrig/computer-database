package fr.excilys.business;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.excilys.model.Computer;

public class ComputerManagerTest {

	@Test
	public void testFindComputer() {
		ComputerManager manager = ManagerFactory.getInstance().getComputerManager();
		Computer computer = manager.findComputer(1L);
		System.out.println(computer.toString());
	}

}
