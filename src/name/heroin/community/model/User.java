package name.heroin.community.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table (name = "users")
public class User {
	public User() {
		
	}
	
	@Id
	@GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
	private int id;
	
	@OneToOne
	@JoinColumn (name = "role_id")
	private Role role;

	private String email;
	
	private String name;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}	
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
