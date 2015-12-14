package Dao;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import dao.AClientDao;
import model.Client;

public class JerseyClientDao extends AClientDao {

	private static WebResource service = null;
	private String baseURI = "jdbc:mysql://172.28.106.1:3306/si?user=2A&password=2A";
	
	private URI getBaseURI() {
		
		return UriBuilder.fromUri(baseURI).build();
	}
	
	public JerseyClientDao() {
		
		ClientConfig config = new DefaultClientConfig();
		com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create(config);
		service = client.resource(getBaseURI());
		refreshData();
	}
	
	public JerseyClientDao(String baseURI) {
		
		ClientConfig config = new DefaultClientConfig();
		com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create(config);
		this.baseURI = baseURI;
		service = client.resource(getBaseURI());
		refreshData();
	}

	public void refreshData() {
		
		/*List<Client> listClients = service.path("clients")
				.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML).get(new GenericType<List<Client>>() {});
		for(Client c : listClients) {
			clientList.put(c.getId(), c);
		}*/
	}
}
