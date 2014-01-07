package name.heroin.community.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "posts")
@NamedQueries({
	@NamedQuery(
	name = "getIdNameMap",
	query = "from SlimPost p where p.title like :search"
	)
})
public class SlimPost {
	
	@Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;
	
    private String title;
    private Date timestamp;
    
    @OneToMany (cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable (
			name = "post_tags",
			joinColumns = @JoinColumn (name = "post_id"),
			inverseJoinColumns = @JoinColumn (name = "tag_id")
	)
    private Set<Tag> tags = new HashSet<Tag>();
    
    public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public SlimPost() {
        // This is used by JPA
    }
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}