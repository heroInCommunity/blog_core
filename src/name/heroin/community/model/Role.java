package name.heroin.community.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table (name = "roles")
public class Role {
	public Role() {
		
	}
	
	@Id
	@GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
	private int id;
	
	private String name;
	
	@OneToMany (cascade = CascadeType.PERSIST)
	@JoinTable (
			name = "role_permissions",
			joinColumns = @JoinColumn (name = "role_id"),
			inverseJoinColumns = @JoinColumn (name = "permission_id")
	)
	private Collection<Permission> permissions = new ArrayList<Permission>();

	public Collection<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Collection<Permission> permissions) {
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
