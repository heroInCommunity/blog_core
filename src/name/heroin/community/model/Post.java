package name.heroin.community.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "posts")
public class Post {
	
	@Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;
	
    private String title;
    private String body;
    private Date timestamp;

    public Post() {
        // This is used by JPA
    }
    
    @OneToMany (cascade = CascadeType.PERSIST)
	@JoinTable (
			name = "post_tags",
			joinColumns = @JoinColumn (name = "post_id"),
			inverseJoinColumns = @JoinColumn (name = "tag_id")
	)
    private Collection<Tag> tags = new ArrayList<Tag>();
    
    @OneToMany (cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable (
			name = "post_comments",
			joinColumns = @JoinColumn (name = "post_id"),
			inverseJoinColumns = @JoinColumn (name = "comment_id")
	)
    private Collection<Comment> comments = new ArrayList<Comment>();

    public int getId() {
        return id;
    }

    public Collection<Tag> getTags() {
		return tags;
	}

	public void setTags(Collection<Tag> tags) {
		this.tags = tags;
	}

	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="timestamp")
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}
