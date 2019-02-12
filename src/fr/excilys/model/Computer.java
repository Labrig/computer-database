package fr.excilys.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Beans using JPA for persistence
 * @author Matheo
 */
@Entity
@Table(name="computer")
public class Computer {

	@Id
	private long id;
	private String name;
	
	//The date of the introduction of the product
	private Timestamp introduced;
	
	//The date of the end of the production
	private Timestamp discontinued;

	@ManyToOne(fetch=FetchType.EAGER)
	private Company company;
	
	public Computer() { }

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
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
	public Timestamp getIntroduced() {
		return introduced;
	}

	/**
	 * @param introduced the introduced to set
	 */
	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}

	/**
	 * @return the discontinued
	 */
	public Timestamp getDiscontinued() {
		return discontinued;
	}

	/**
	 * @param discontinued the discontinued to set
	 */
	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}

	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}
	
	@Override
	public String toString() {
		return "Computer [id=" + this.id + ", name=" + this.name + ", introduced=" + ((this.introduced == null) ? "NA" : this.introduced.toString())
			+ ", discontinued=" + ((this.discontinued == null) ? "NA" : this.discontinued.toString()) + ", company=" + (this.company == null ? "NA" : this.company.toString()) + "]";
	}
}
