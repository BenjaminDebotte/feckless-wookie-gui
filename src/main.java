import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import Dao.JerseyClientDao;
import dao.ClientDao;
import dao.DbClientDao;
import dao.DbOperationDao;
import dao.OperationDao;
import db.DbManagement;
import model.Client;

public class main {

	public static void main(String[] args) {
		
		
		try {
			
			DbManagement.getInstance().setDelegate(new MysqlDbManagement());
			DbManagement.getInstance().connexion("jdbc:mysql://venus.returnator.com/si?user=alex&password=alex");
			ClientDao.getInstance().setDelegate(new DbClientDao());
			OperationDao.getInstance().setDelegate(new DbOperationDao());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		WelcomeFrame welcomeFrame = new WelcomeFrame();
		welcomeFrame.setSize(500, 500);
		welcomeFrame.setVisible(true);
		
		JerseyClientDao jerseyClientDao = new JerseyClientDao();
		ClientDao.getInstance().setDelegate(jerseyClientDao);
	}
}
