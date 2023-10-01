package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.Controller;

public class Login extends JFrame{
	
	private JPanel panelData;
	private JPanel panelButton;
	private JLabel labelUsername;
	private JTextField fieldUsername;
	private JLabel labelPassword;
	private JTextField fieldPassword;
	private JButton buttonLogin;
	
	private Controller controller;
	
	public Login() {
		
		labelUsername = new JLabel("Nome");
		labelUsername.setPreferredSize(new Dimension(80,30));
		fieldUsername = new JTextField(15);
		
		labelPassword = new JLabel("Cognome");
		labelPassword.setPreferredSize(new Dimension(80,30));
		fieldPassword = new JTextField(15);
		
		buttonLogin = new JButton("Login");
		buttonLogin.setPreferredSize(new Dimension(100, 40));
		
		controller = new Controller();
		
		setLayout(new BorderLayout());
		
		panelData = new JPanel();
		panelData.setLayout(new GridBagLayout());
		
		panelButton = new JPanel();
		panelButton.setLayout(new FlowLayout());	
		
		controller.createFileUser();
		
		buttonLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String username  = fieldUsername.getText();
				String password  = fieldPassword.getText();
				
				//controllo che i campi username e password non siano vuoti
				if( !checkEmptyField(username, password) ) {
					return;
				}
				//controllo che l'utente esista
				if (!controller.checkUser(username, password)) {
					JOptionPane.showMessageDialog(null, "Errore: username o password errato");
					return;
				}
				
				//se le verifiche vanno a buon fine, passo alla finestra principale
				FinestraPrincipale finestraPrincipale = new FinestraPrincipale();
				finestraPrincipale.setVisible(true);
				setVisible(false);
				
			}
		});
		
		setGrid();
		
		panelButton.add(buttonLogin);
		
		
		add(panelData, BorderLayout.CENTER);
		add(panelButton, BorderLayout.PAGE_END);
		setSize(600,400);	
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void setGrid() {
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.anchor = GridBagConstraints.WEST;
		panelData.add(labelUsername, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		panelData.add(fieldUsername, gbc);
		
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.anchor = GridBagConstraints.WEST;
		panelData.add(labelPassword, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		panelData.add(fieldPassword, gbc);
	}
	
	public boolean checkEmptyField(String username, String password) {
		if(!username.isEmpty() && !password.isEmpty()) {
			return true;
		}
		JOptionPane.showMessageDialog(null, "Errore: alcuni campi sono vuoti");
		return false;
	}
}
