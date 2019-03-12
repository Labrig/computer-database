package fr.excilys.logger;

public enum ELoggerMessage {

	STATEMENT_EXECUTED("The statement {} has been executed."),
	NO_RESULSET_RETURNED("No result returned by the statement");
	
	private String message = "";
	   
	ELoggerMessage(String message){
		this.message = message;
	}
	   
	@Override
	public String toString(){
		return message;
	}
}
