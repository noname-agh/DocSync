package pl.edu.agh.two.ws;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class RSSItem  implements Serializable {

	private static final long serialVersionUID = 7908115507714381149L;

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne 
	@JoinColumn(name="address")
	private String channelAddress;
	
	private Boolean readed;
	private String link;
	private String description;
	private String guid;
	private Date date;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getChannelAddress() {
		return channelAddress;
	}
	public void setChannelAddress(String channelAddress) {
		this.channelAddress = channelAddress;
	}
	public Boolean getReaded() {
		return readed;
	}
	public void setReaded(Boolean readed) {
		this.readed = readed;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
