package fr.excilys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import fr.excilys.model.Computer;

/**
 * The DAO of the computer object
 * 
 * @author Matheo
 */
public interface ComputerDAO extends Repository<Computer, Long> {
	
	public List<Computer> findAll();
	
	public Computer getById(Long id);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (:#{#computer.name}, :#{#computer.introduced},  :#{#computer.discontinued}, :#{#computer.company.id})",
			nativeQuery = true)
	public int insert(@Param("computer") Computer computer);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE computer SET name = :#{#computer.name}, introduced = :#{#computer.introduced}, discontinued = :#{#computer.discontinued}, company_id = :#{#computer.company.id} WHERE id = :#{#computer.id}",
			nativeQuery = true)
	public int update(@Param("computer") Computer computer);
	
	@Transactional
	@Modifying
	@Query("delete from Computer where id = :id")
	public int deleteById(@Param("id") Long id);
	
	@Transactional
	@Modifying
	@Query("delete from Computer where company_id = :idCompany")
	public void deleteByIdCompany(@Param("idCompany") Long idCompany);
	
	public List<Computer> findByNameContaining(String name);
	
	@Query(value = "SELECT id, name, introduced, discontinued, company_id FROM computer LIMIT :start, :size",
			nativeQuery = true)
	public List<Computer> findAllWithPagination(@Param("start") int start, @Param("size") int size);
	
	public int count();
}
