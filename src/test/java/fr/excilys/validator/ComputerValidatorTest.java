package fr.excilys.validator;

import java.text.ParseException;

import org.junit.Test;

import fr.excilys.exceptions.validation.ComputerValidationException;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Computer;

public class ComputerValidatorTest {
	
	@Test(expected = ComputerValidationException.class)
	public void verifyIntroBeforeDisco1() throws ComputerValidationException, ParseException {
		Computer computer = new Computer();
		computer.setIntroduced(ComputerMapper.getInstance().convertStringToDate("2019-01-23"));
		computer.setDiscontinued(ComputerMapper.getInstance().convertStringToDate("2019-01-15"));
		ComputerValidator.getInstance().verifyIntroBeforeDisco(computer);
	}
	
	@Test(expected = ComputerValidationException.class)
	public void verifyIntroBeforeDisco2() throws ComputerValidationException, ParseException {
		Computer computer = new Computer();
		computer.setIntroduced(ComputerMapper.getInstance().convertStringToDate("2019-01-15"));
		computer.setDiscontinued(ComputerMapper.getInstance().convertStringToDate("2019-01-15"));
		ComputerValidator.getInstance().verifyIntroBeforeDisco(computer);
	}
	
	@Test(expected = ComputerValidationException.class)
	public void verifyIntroBeforeDisco3() throws ComputerValidationException, ParseException {
		Computer computer = new Computer();
		computer.setDiscontinued(ComputerMapper.getInstance().convertStringToDate("2019-01-15"));
		ComputerValidator.getInstance().verifyIntroBeforeDisco(computer);
	}

}
