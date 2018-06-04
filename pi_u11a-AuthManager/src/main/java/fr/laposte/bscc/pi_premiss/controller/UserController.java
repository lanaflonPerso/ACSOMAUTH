package fr.laposte.bscc.pi_premiss.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.laposte.bscc.pi_premiss.model.User;
import fr.laposte.bscc.pi_premiss.repository.UserRepository;
import fr.laposte.bscc.pi_premiss.service.MyResourceNotFoundException;
import fr.laposte.bscc.pi_premiss.service.UtilisateurService;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserRepository repository;
	
	@Autowired
    private UtilisateurService utilisateurService;
 
    @RequestMapping(
      value = "/user/get", 
      params = { "page", "size" }, 
      method = RequestMethod.GET
    )
    public Page<User> findPaginated(
      @RequestParam("page") int page, @RequestParam("size") int size) {
 
        Page<User> resultPage = utilisateurService.findPaginated(page, size);
        if (page > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }
 
        return resultPage;
    }
	
	@GetMapping("/user")
	List<User> getAllUser(){
		return repository.findAll();
	}
	
	@GetMapping("/user/{id}")
	ResponseEntity<User> getUserById(@PathVariable(value="id") long id) {
	    User user = repository.findOne(id);
	    if(user == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(user);
	}
	
	@PostMapping("/user")
	User addUser(@RequestBody User user){
		user.setPassword("password");
		user.setStatus(User.UserStatus.ACTIVE);
		return repository.save(user);
	}
	
	// TODO : fonction à revoir
	@PutMapping("/user/{id}")
	ResponseEntity<User> updateUser(@PathVariable(value="id") long id, @RequestBody User user){
		User userToUpdate = repository.findOne(id);
		if(userToUpdate == null)
			return ResponseEntity.notFound().build();
		
		if(user.getUsername() != null)
			userToUpdate.setUsername(user.getUsername());
		
		if(user.getEmail() != null)
			userToUpdate.setEmail(user.getEmail());
		
		if(user.getStatus() != null)
			userToUpdate.setStatus(user.getStatus());
		
		if(user.getRole() != null)
			userToUpdate.setRole(user.getRole());
		
		User updatedUser = repository.save(userToUpdate);
		return ResponseEntity.ok(updatedUser);
	}
	
	@DeleteMapping("/user/{id}")
	ResponseEntity<User> deleteUser(@PathVariable(value="id") long id){
		User user = repository.findOne(id);
		if(user == null)
			return ResponseEntity.notFound().build();
		
		repository.delete(user);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/user/username/{username}" )
	public ResponseEntity<List<User>> findByCodeRh(@PathVariable(value = "username") String username) {
		List <User> foundUser = repository.findByUsername(username);
		if (foundUser.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(foundUser, HttpStatus.OK);
	}
	
//	@GetMapping("/user/{codeRh}")
//	ResponseEntity<User> getUserByCodeRh(@PathVariable(value="codeRh") String username) {
//	    List<User> user = repository.findByUsername(username);
//	    if(user == null) {
//	        return ResponseEntity.notFound().build();
//	    }
//	    return ResponseEntity.ok().body(user);
//	}
}
