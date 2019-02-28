package fr.excilys.mapper;

public class MapperFactory {

	private static MapperFactory instance = new MapperFactory();
	
	private MapperFactory() { }
	
	public static MapperFactory getInstance() {
		return instance;
	}
	
	public CompanyMapper getCompanyMapper() {
		return CompanyMapper.getInstance();
	}
	
	public ComputerMapper getComputerMapper() {
		return ComputerMapper.getInstance();
	}
}
