package fr.excilys.validator;

import java.text.ParseException;

import org.junit.Test;

import fr.excilys.exceptions.ComputerFormatException;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Computer;

public class ComputerValidatorTest {
	
	@Test(expected = ComputerFormatException.class)
	public void verifyIntroBeforeDisco1() throws ComputerFormatException, ParseException {
		Computer computer = new Computer();
		computer.setIntroduced(ComputerMapper.getInstance().convertStringToDate("2019-01-23"));
		computer.setDiscontinued(ComputerMapper.getInstance().convertStringToDate("2019-01-15"));
		ComputerValidator.getInstance().verifyIntroBeforeDisco(computer);
	}
	
	@Test(expected = ComputerFormatException.class)
	public void verifyIntroBeforeDisco2() throws ComputerFormatException, ParseException {
		Computer computer = new Computer();
		computer.setIntroduced(ComputerMapper.getInstance().convertStringToDate("2019-01-15"));
		computer.setDiscontinued(ComputerMapper.getInstance().convertStringToDate("2019-01-15"));
		ComputerValidator.getInstance().verifyIntroBeforeDisco(computer);
	}
	
	@Test(expected = ComputerFormatException.class)
	public void verifyIntroBeforeDisco3() throws ComputerFormatException, ParseException {
		Computer computer = new Computer();
		computer.setDiscontinued(ComputerMapper.getInstance().convertStringToDate("2019-01-15"));
		ComputerValidator.getInstance().verifyIntroBeforeDisco(computer);
	}

}
