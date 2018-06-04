package fr.laposte.bscc.pi_premiss.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class User {
	
	public enum UserStatus {ACTIVE, INACTIVE};
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotBlank
	@Column(unique=true)
	@Pattern(regexp="^\\w+$")
	private String username;
	
	@JsonIgnore
	@NotBlank
	private String password;
	
	@NotBlank
	@Column(unique=true)
	@Pattern(regexp="^(\\w||\\.)+@\\w+\\.\\w+$")
	private String email;
	
	@OneToOne
	private Role role;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 8)
	private UserStatus status;
}
