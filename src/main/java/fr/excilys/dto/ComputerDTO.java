package fr.excilys.dto;

/**
 * The computer object used by the client
 * @author Matheo
 */
public class ComputerDTO {

	private String id;
	private String name;
	
	//The date of the introduction of the product
	private String introduced;
	
	//The date of the end of the production
	private String discontinued;
	
	private String companyId;
	
	private String companyName;
	
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
	 * @return the introduced
	 */
	public String getIntroduced() {
		return introduced;
	}

	/**
	 * @param introduced the introduced to set
	 */
	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	/**
	 * @return the discontinued
	 */
	public String getDiscontinued() {
		return discontinued;
	}

	/**
	 * @param discontinued the discontinued to set
	 */
	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	/**
	 * @return the companyId
	 */
	public String getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	/**
	 * The computerDTO builder
	 * @author Matheo
	 */
	public static class ComputerDTOBuilder {
		
		private String id;
		private String name;
		
		//The date of the introduction of the product
		private String introduced;
		
		//The date of the end of the production
		private String discontinued;
		
		private String companyId;
		
		private String companyName;

		/**
		 * @param id the id to set
		 */
		public ComputerDTOBuilder setId(String id) {
			this.id = id;
			return this;
		}

		/**
		 * @param name the name to set
		 */
		public ComputerDTOBuilder setName(String name) {
			this.name = name;
			return this;
		}

		/**
		 * @param introduced the introduced to set
		 */
		public ComputerDTOBuilder setIntroduced(String introduced) {
			this.introduced = introduced;
			return this;
		}

		/**
		 * @param discontinued the discontinued to set
		 */
		public ComputerDTOBuilder setDiscontinued(String discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		/**
		 * @param companyId the companyId to set
		 */
		public ComputerDTOBuilder setCompanyId(String companyId) {
			this.companyId = companyId;
			return this;
		}

		/**
		 * @param companyName the companyName to set
		 */
		public ComputerDTOBuilder setCompanyName(String companyName) {
			this.companyName = companyName;
			return this;
		}
		
		/**
		 * @return the companyDTO built based
		 * on the attribute gave to the builder
		 */
		public ComputerDTO build() {
			ComputerDTO dto = new ComputerDTO();
			dto.setId(this.id);
			dto.setName(this.name);
			dto.setIntroduced(this.introduced);
			dto.setDiscontinued(this.discontinued);
			dto.setCompanyId(this.companyId);
			dto.setCompanyName(this.companyName);
			return dto;
		}
	}
}
