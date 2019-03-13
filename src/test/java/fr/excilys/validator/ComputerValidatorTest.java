package fr.excilys.validator;

import java.text.ParseException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.excilys.config.SpringTestConfiguration;
import fr.excilys.exceptions.validation.ComputerValidationException;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Computer;

public class ComputerValidatorTest {
	
	private static ComputerMapper mapper;
	private static ComputerValidator validator;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		@SuppressWarnings("resource")
		ApplicationContext vApplicationContext = new AnnotationConfigApplicationContext(SpringTestConfiguration.class);
		mapper = vApplicationContext.getBean("computerMapper", ComputerMapper.class);
		validator = vApplicationContext.getBean("computerValidator", ComputerValidator.class);	
	}
	
	@Test(expected = ComputerValidationException.class)
	public void expectExceptionWhenIdIsNull() throws ComputerValidationException, ParseException {
		validator.verifyIdNotNull(null);;
	}
	
	@Test(expected = ComputerValidationException.class)
	public void expectExceptionWhenIdIsEqualsTo0() throws ComputerValidationException, ParseException {
		validator.verifyIdNotNull(new Long(0));
	}
	
	@Test
	public void expectNoExceptionWhenIdIsNotNull() throws ComputerValidationException, ParseException {
		validator.verifyIdNotNull(new Long(1));
	}
	
	@Test(expected = ComputerValidationException.class)
	public void expectExceptionWhenNameIsNull() throws ComputerValidationException, ParseException {
		validator.verifyName(null);;
	}
	
	@Test(expected = ComputerValidationException.class)
	public void expectExceptionWhenNameIsEmpty() throws ComputerValidationException, ParseException {
		validator.verifyName("");
	}
	
	@Test
	public void expectNoExceptionWhenNameIsNotNull() throws ComputerValidationException, ParseException {
		validator.verifyName("test");
	}
	
	@Test(expected = ComputerValidationException.class)
	public void expectExceptionWhenDiscoBeforeIntro() throws ComputerValidationException, ParseException {
		Computer computer = new Computer();
		computer.setIntroduced(mapper.convertStringToDate("2019-01-23"));
		computer.setDiscontinued(mapper.convertStringToDate("2019-01-15"));
		validator.verifyIntroBeforeDisco(computer);
	}
	
	@Test(expected = ComputerValidationException.class)
	public void expectExceptionWhenDiscoEqualsIntro() throws ComputerValidationException, ParseException {
		Computer computer = new Computer();
		computer.setIntroduced(mapper.convertStringToDate("2019-01-15"));
		computer.setDiscontinued(mapper.convertStringToDate("2019-01-15"));
		validator.verifyIntroBeforeDisco(computer);
	}
	
	@Test(expected = ComputerValidationException.class)
	public void expectExceptionWhenDiscoNotNullAndIntroNull() throws ComputerValidationException, ParseException {
		Computer computer = new Computer();
		computer.setDiscontinued(mapper.convertStringToDate("2019-01-15"));
		validator.verifyIntroBeforeDisco(computer);
	}
	
	@Test
	public void expectNoExceptionWhenDiscoAfterIntro() throws ComputerValidationException, ParseException {
		Computer computer = new Computer();
		computer.setIntroduced(mapper.convertStringToDate("2019-01-15"));
		computer.setDiscontinued(mapper.convertStringToDate("2019-01-23"));
		validator.verifyIntroBeforeDisco(computer);
	}
	
	@Test(expected = ComputerValidationException.class)
	public void expectExceptionWhenComputerIsNull() throws ComputerValidationException {
		validator.verifyComputerNotNull(null);
	}

	@Test
	public void expectNoExceptionWhenComputerIsNotNull() throws ComputerValidationException {
		validator.verifyComputerNotNull(new Computer());
	}

}
