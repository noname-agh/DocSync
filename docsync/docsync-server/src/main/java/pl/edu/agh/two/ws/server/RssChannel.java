package pl.edu.agh.two.ws.server;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RssChannel {
	@Id
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
