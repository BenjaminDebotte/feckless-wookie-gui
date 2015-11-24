import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import com.sun.media.sound.ModelAbstractChannelMixer;

import model.Client;

public class MainFrame extends JFrame {
	private class ClientTableModel extends AbstractTableModel {

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	protected MainFrame()
	{
		setTitle("GUI");
		setSize(300, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Middle of the screen
		 
		prepareComponents();
		 
		setVisible(true); // This will paint the entire frame
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
		
		JButton buttonSearch = new JButton("Recherche");
		panel.add(buttonSearch);
		
		JButton buttonReset = new JButton("Réinitialiser");
		panel.add(buttonReset);	
		
		JButton buttonQuit = new JButton("Quitter");
		panel.add(buttonQuit);
		
		JComboBox<Client> cbClients = new JComboBox<>();
		panel.add(cbClients);
		
		JTable tableOpe = new JTable(new ClientTableModel());
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
