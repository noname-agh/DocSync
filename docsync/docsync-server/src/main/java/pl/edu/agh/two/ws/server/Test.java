package pl.edu.agh.two.ws.server;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import pl.edu.agh.two.ws.CloudFile;
import pl.edu.agh.two.ws.CloudStorage;

public class Test {
	
	public static void main(String[] args) throws MalformedURLException {
		Service service = Service.create(new URL("http://localhost:8080/CloudStorage?wsdl"), new QName("http://server.ws.two.agh.edu.pl/", "CloudStorage"));
		CloudStorage storage = service.getPort(CloudStorage.class);
		storage.getAllFilesWithContent();
		
		//CloudFile file = storage.getFiles().get(0);
		//System.out.format("%s%n", file.getContent().length);
	}

}
