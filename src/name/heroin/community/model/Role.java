package name.heroin.community.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table (name = "roles")
public class Role implements Serializable {
	private static final long serialVersionUID = -8322948063419676837L;

	public Role() {
		
	}
	
	@Id
	@GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
	private int id;
	
	private String name;
	
	@OneToMany (cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable (
			name = "role_permissions",
			joinColumns = @JoinColumn (name = "role_id"),
			inverseJoinColumns = @JoinColumn (name = "permission_id")
	)
	private Set<Permission> permissions = new HashSet<Permission>();

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
