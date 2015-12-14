import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import Dao.JerseyClientDao;
import Dao.JerseyOperationDao;
import dao.ClientDao;
import dao.DbClientDao;
import dao.DbOperationDao;
import dao.OperationDao;
import db.DbManagement;
import model.Client;
import model.Operation;

public class MainFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Client> clientList;
	
	private JComboBox<Client> cbClients;
	private JTable tableOpe;
	private JScrollPane scroll;

	protected MainFrame(boolean mode) throws SQLException {
		
		setTitle("GUI");
		setSize(600, 400);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Middle of the screen
		 
		prepareComponents();
		
		DbManagement.getInstance().setDelegate(new MysqlDbManagement());
		DbManagement.getInstance().connexion("jdbc:mysql://venus.returnator.com/si?user=alex&password=alex");
		
		if(mode) {
							
			ClientDao.getInstance().setDelegate(new DbClientDao());
			OperationDao.getInstance().setDelegate(new DbOperationDao());	
			System.out.println("1");
		}
		else {
			
			ClientDao.getInstance().setDelegate(new JerseyClientDao());
			OperationDao.getInstance().setDelegate(new JerseyOperationDao());
			System.out.println("2");
		}
		
		clientList = ClientDao.getInstance().getClients();
	}
	

	private List<Client> search(String familyName, String firstName) throws SQLException {		
		
		if(firstName.equals(""))
			return ClientDao.getInstance().getByName(familyName);
		else
			return ClientDao.getInstance().getByFullname(familyName, firstName);
	}
	
	private void prepareComponents() {
		
		JPanel panel = new JPanel();
		
		BoxLayout layout = new BoxLayout(panel,BoxLayout.Y_AXIS);
		panel.setLayout(layout);

		JLabel name = new JLabel("Nom");
		panel.add(name);
		
		JTextField nameField = new JTextField();
		panel.add(nameField);
		
		JLabel surname = new JLabel("Prénom");
		panel.add(surname);
		
		JTextField surnameField = new JTextField();
		panel.add(surnameField);
		
		JPanel H_panel = new JPanel();
		BoxLayout H_layout = new BoxLayout(H_panel, BoxLayout.X_AXIS);
		H_panel.setLayout(H_layout);
		
		JButton buttonSearch = new JButton("Recherche");
		buttonSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					clientList = search(nameField.getText(), surnameField.getText());
					
					Iterator<Client> clientIterator = clientList.iterator();
					
					cbClients.removeAllItems();
					
					if(clientList.size() == 0) {
						
						JPanel panelPopUp = new JPanel();
						panelPopUp.setLayout(new BoxLayout(panelPopUp, BoxLayout.PAGE_AXIS));
						
						JLabel label = new JLabel("Aucun client n'a été trouvé :(");
						panelPopUp.add(label);
						
						JFrame popUp = new JFrame("Information");
						
						JButton button = new JButton("OK");
						button.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								
								popUp.setVisible(false);
							}
						});
						panelPopUp.add(button);
												
						popUp.add(panelPopUp);						
						
						popUp.pack();
						popUp.setVisible(true);
					}
					else {
						Client client;
						while(clientIterator.hasNext()) {
							
							client = clientIterator.next();
							//String clientDescription = "Id : " + client.getId() + " Nom : " + client.getFamilyName() + " Prénom : " + client.getFirstName();
										
							cbClients.addItem(client);
						}
					}
										
				} catch (SQLException e1) {
					
					JTextField erreur = new JTextField("Une erreur est survenue. Vous ne pouvez rien y faire, ça a sûrement été mal codé !");
					panel.add(erreur);
					e1.printStackTrace();
				}
			}
		});
		H_panel.add(buttonSearch);
		
		JButton buttonReset = new JButton("Réinitialiser");
		buttonReset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				nameField.setText("");
				surnameField.setText("");
			}
		});
		
		H_panel.add(buttonReset);	
		
		JButton buttonQuit = new JButton("Quitter");
		buttonQuit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		H_panel.add(buttonQuit);
		
		panel.add(H_panel);
		
		
		cbClients = new JComboBox<>();
		
		cbClients.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				
				Client client = (Client) cbClients.getSelectedItem();	
				
				try {
					ArrayList<Operation> operationList = (ArrayList<Operation>) OperationDao.getInstance().getById(client.getId());
					
					panel.remove(scroll);
					tableOpe = new JTable(new ClientOperationTableModel(operationList));
					scroll = new JScrollPane(tableOpe);
					panel.add(scroll);
				} catch (SQLException e1) {
					
					JTextField erreur = new JTextField("Une erreur est survenue. Vous ne pouvez rien y faire, ça a sûrement été mal codé !");
					panel.add(erreur);
					e1.printStackTrace();
				}				
			}
		});
		panel.add(cbClients);
		
		tableOpe = new JTable(new ClientOperationTableModel(new ArrayList<Operation>()));
		//tableOpe.createDefaultColumnsFromModel();
		//tableOpe.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		panel.add(tableOpe.getTableHeader());
		
		scroll = new JScrollPane(tableOpe);
		panel.add(scroll);
		
		this.add(panel);
	}
	  

	private class ClientOperationTableModel extends AbstractTableModel {
		
		private ArrayList<Operation> operations = null;
	
		private final String[] headers = {"ID", "N° Carte", "N°Compte", "Montant", "Date"};
		
		public ClientOperationTableModel(ArrayList<Operation> operations) {
			super();
			this.operations = operations;
		}
	
		@Override
		public String getColumnName(int column) {
			return headers[column];
		}
	
		@Override
		public int getRowCount() {
			return operations.size();
		}
	
		@Override
		public int getColumnCount() {
			return headers.length;
		}
	
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if(columnIndex >= headers.length)
				return null;
			if(columnIndex == 0)
				return operations.get(rowIndex).getIdOperation();
			else if(columnIndex == 1)
				return operations.get(rowIndex).getIdCard();
			else if(columnIndex == 2)
				return operations.get(rowIndex).getIdAccount();
			else if(columnIndex == 3)
				return operations.get(rowIndex).getOperationAmount();
			else 
				return operations.get(rowIndex).getOperationDate();
		}
	}	
}
