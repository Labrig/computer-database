package fr.excilys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import fr.excilys.model.Company;

/**
 * The DAO of the company object
 * 
 * @author Matheo
 */
public interface CompanyDAO extends Repository<Company, Long> {
	
	public List<Company> findAll();
	
	public Company getById(Long id);
	
	@Transactional
	@Modifying
	@Query("delete from Company where id = :id")
	public int deleteById(@Param("id") Long id);
}
