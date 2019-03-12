package fr.excilys.dto;

/**
 * The company object used by the client
 * @author Matheo
 */
public class CompanyDTO {

	private String id;
	private String name;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * The companyDTO builder
	 * @author Matheo
	 */
	public static class CompanyDTOBuilder {
		
		private String id;
		private String name;
		
		/**
		 * @param id the id to set
		 */
		public CompanyDTOBuilder setId(String id) {
			this.id = id;
			return this;
		}

		/**
		 * @param name the name to set
		 */
		public CompanyDTOBuilder setName(String name) {
			this.name = name;
			return this;
		}
		
		/**
		 * @return the companyDTO built based
		 * on the attribute gave to the builder
		 */
		public CompanyDTO build() {
			CompanyDTO dto = new CompanyDTO();
			dto.setId(this.id);
			dto.setName(this.name);
			return dto;
		}
	}
}
