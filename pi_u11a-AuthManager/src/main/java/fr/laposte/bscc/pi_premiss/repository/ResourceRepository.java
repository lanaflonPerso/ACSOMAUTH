package fr.laposte.bscc.pi_premiss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.laposte.bscc.pi_premiss.model.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
	public List<Resource> findByIdIn(List<Long> listOfId); 
}
