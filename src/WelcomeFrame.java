import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WelcomeFrame extends JFrame {

	public WelcomeFrame() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel makeYourChoice = new JLabel("Quelle méthode de fonctionnement souhaitez-vous utiliser ?");
		panel.add(makeYourChoice);
		
		CheckboxGroup choiceBox = new CheckboxGroup();
		Checkbox jdbcCheckBox = new Checkbox("Accès direct JDBC", choiceBox, true);
		Checkbox restCheckBox = new Checkbox("RESTful WebService", choiceBox, false);
		panel.add(jdbcCheckBox);
		panel.add(restCheckBox);
		
		Button okButton = new Button("OK");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				MainFrame mainFrame;
				
					
				try {
					if(jdbcCheckBox.getState()) {
						
						mainFrame = new MainFrame(true);
					}
					else {
						
						mainFrame = new MainFrame(false);
					}
					
					mainFrame.setVisible(true);
					setVisible(false);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		panel.add(okButton);
		
		add(panel);
	}
}
