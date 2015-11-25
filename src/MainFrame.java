import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import com.sun.media.sound.ModelAbstractChannelMixer;

import javafx.scene.control.ComboBox;
import model.Client;
import model.Operation;

public class MainFrame extends JFrame {
	private class ClientOperationTableModel extends AbstractTableModel {
		
		private ArrayList<Operation> operations = null;

		private final String[] headers = {"ID","N° Carte","N°Compte", "Montant","Date"};
		
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
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	protected MainFrame()
	{
		setTitle("GUI");
		setSize(600, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Middle of the screen
		 
		prepareComponents();
		 
		setVisible(true); // This will paint the entire frame
	}
	
	
	private void search(String nom, String prenom) {
		
	}
	
	
	
	private void prepareComponents()
	{	 
		JPanel panel = new JPanel();
		
		BoxLayout layout = new BoxLayout(panel,BoxLayout.Y_AXIS);
		panel.setLayout(layout);

		JTextField nameField = new JTextField("Nom");
		panel.add(nameField);
		
		JTextField surnameField = new JTextField("Prénom");
		panel.add(surnameField);
		
		JPanel H_panel = new JPanel();
		BoxLayout H_layout = new BoxLayout(H_panel, BoxLayout.X_AXIS);
		H_panel.setLayout(H_layout);
		
		JButton buttonSearch = new JButton("Recherche");
		buttonSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				search(nameField.getText(),surnameField.getText());
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
		
		
		JComboBox<String> cbClients = new JComboBox<>();
		panel.add(cbClients);
		
		
		JTable tableOpe = new JTable(new ClientOperationTableModel(new ArrayList<Operation>()));
		tableOpe.createDefaultColumnsFromModel();
		tableOpe.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		panel.add(tableOpe.getTableHeader());
		panel.add(tableOpe);
		
		this.add(panel);
	}
	  
	  
public static void main(String[] args)
{
	SwingUtilities.invokeLater(
	new Runnable() {
		@Override
		public void run()
		{
			new MainFrame();
		}
	}
	);

}
	 
	
	
}
