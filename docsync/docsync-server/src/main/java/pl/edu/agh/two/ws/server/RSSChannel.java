package pl.edu.agh.two.ws.server;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RSSChannel implements Serializable {

	@Id
	private String address;

	public RSSChannel() {
	}

	public RSSChannel(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
