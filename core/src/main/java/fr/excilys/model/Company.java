package fr.excilys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The company bean and his builder
 * @author Matheo
 */
@Entity
@Table(name = "company")
public class Company {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "name")
	private String name;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
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

	@Override
	public String toString() {
		return "Company [id=" + this.id + ", name=" + this.name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Company)) {
			return false;
		}
		Company other = (Company) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
	
	/**
	 * The company builder
	 * @author Matheo
	 */
	public static class CompanyBuilder {
		
		private Long id;
		private String name;

		/**
		 * @param id the id to set
		 */
		public CompanyBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		/**
		 * @param name the name to set
		 */
		public CompanyBuilder setName(String name) {
			this.name = name;
			return this;
		}
		
		/**
		 * @return the company built based
		 * on the attribute gave to the builder
		 */
		public Company build() {
			Company company = new Company();
			company.setId(this.id);
			company.setName(this.name);
			return company;
		}
		
	}
}
