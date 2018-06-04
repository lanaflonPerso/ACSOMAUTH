package fr.laposte.bscc.pi_premiss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.laposte.bscc.pi_premiss.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
	
	List<User> findByUsername(String username);
	
	List<User> findByEmail(String email);
}
