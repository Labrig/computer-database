package fr.excilys.validator;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import fr.excilys.exceptions.ComputerFormatException;
import fr.excilys.model.Computer;

public class ComputerValidatorTest {

	@Test
	public void testVerifyId() throws ComputerFormatException {
		Computer computer = new Computer();
		ComputerValidator.getInstance().verifyId(computer, "10");
		assertEquals(new Long(10), computer.getId());
	}
	
	@Test(expected = ComputerFormatException.class)
	public void testVerifyIdException() throws ComputerFormatException {
		ComputerValidator.getInstance().verifyId(new Computer(), "test");
	}
	
	@Test
	public void testVerifyIntro() throws ComputerFormatException, ParseException {
		Computer computer = new Computer();
		ComputerValidator.getInstance().verifyIntro(computer, "10-06-2016");
		assertEquals(new SimpleDateFormat("dd-MM-yyyy").parse("10-06-2016"), computer.getIntroduced());
	}
	
	@Test(expected = ComputerFormatException.class)
	public void testVerifyIntroException1() throws ComputerFormatException {
		ComputerValidator.getInstance().verifyIntro(new Computer(), "test");
	}
	
	@Test(expected = ComputerFormatException.class)
	public void testVerifyIntroException2() throws ComputerFormatException {
		ComputerValidator.getInstance().verifyIntro(new Computer(), "10-06");
	}
	
	@Test(expected = ComputerFormatException.class)
	public void testVerifyDiscoException1() throws ComputerFormatException {
		ComputerValidator.getInstance().verifyDisco(new Computer(), "test");
	}
	
	@Test(expected = ComputerFormatException.class)
	public void testVerifyDiscoException2() throws ComputerFormatException {
		ComputerValidator.getInstance().verifyDisco(new Computer(), "10-06");
	}
	
	@Test(expected = ComputerFormatException.class)
	public void verifyIntroBeforeDisco1() throws ComputerFormatException {
		Computer computer = new Computer();
		ComputerValidator.getInstance().verifyIntro(computer, "23-01-2019");
		ComputerValidator.getInstance().verifyDisco(computer, "15-01-2019");
		ComputerValidator.getInstance().verifyIntroBeforeDisco(computer);
	}
	
	@Test(expected = ComputerFormatException.class)
	public void verifyIntroBeforeDisco2() throws ComputerFormatException {
		Computer computer = new Computer();
		ComputerValidator.getInstance().verifyIntro(computer, "15-01-2019");
		ComputerValidator.getInstance().verifyDisco(computer, "15-01-2019");
		ComputerValidator.getInstance().verifyIntroBeforeDisco(computer);
	}
	
	@Test(expected = ComputerFormatException.class)
	public void verifyIntroBeforeDisco3() throws ComputerFormatException {
		Computer computer = new Computer();
		ComputerValidator.getInstance().verifyDisco(computer, "15-01-2019");
		ComputerValidator.getInstance().verifyIntroBeforeDisco(computer);
	}

}
