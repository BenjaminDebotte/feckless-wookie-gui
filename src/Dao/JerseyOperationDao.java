package Dao;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import dao.ClientDao;
import dao.IOperationDao;
import model.Operation;
import model.Resultat;

public class JerseyOperationDao implements IOperationDao {

	private static WebResource service = null;
	private String baseURI = "jdbc:mysql://172.28.106.1:3306/si?user=2A&password=2A";
	
	private URI getBaseURI() {
		
		return UriBuilder.fromUri(baseURI).build();
	}
	
	public JerseyOperationDao() {
		
		ClientConfig config = new DefaultClientConfig();
		com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create(config);
		service = client.resource(getBaseURI());
	}
	
	public JerseyOperationDao(String baseURI) {
		
		ClientConfig config = new DefaultClientConfig();
		com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create(config);
		this.baseURI = baseURI;
		service = client.resource(getBaseURI());
	}

	@Override
	public Resultat getByFullName(String firstName, String familyName) throws SQLException {
		
		List<Operation> listOperations = service.path("operation").path(familyName).path(firstName)
				.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML).get(new GenericType<List<Operation>>() {});
		Resultat resultat = new Resultat(ClientDao.getInstance().getByFullname(familyName, firstName), listOperations);
		resultat.setOperationList(listOperations);
		
		return resultat;
	}

	@Override
	public List<Operation> getById(int id) throws SQLException {
		
		String idString = "" + id;
		List<Operation> listOperations = service.path("operation").path("id").path(idString)
				.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML).get(new GenericType<List<Operation>>() {});
		
		return listOperations;
	}

	@Override
	public Resultat getByName(String name) throws SQLException {
		
		List<Operation> listOperations = service.path("operation").path(name)
				.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML).get(new GenericType<List<Operation>>() {});
		Resultat resultat = new Resultat(ClientDao.getInstance().getByName(name), listOperations);
		resultat.setOperationList(listOperations);
		
		return resultat;
	}

}
