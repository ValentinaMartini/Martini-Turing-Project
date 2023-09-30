package view;

import java.awt.BorderLayout;
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



public class EditorPersona extends JFrame {
	
	private JLabel labelName;
	private JTextField fieldName;
	private JLabel labelSurname;
	private JTextField fieldSurname;
	private JLabel labelAddress;
	private JTextField fieldAddress;
	private JLabel labelTelephone;
	private JTextField fieldTelephone;
	private JLabel labelAge;
	private JTextField fieldAge;
	private JButton buttonCancel;
	private JButton buttonSave;
	private FinestraPrincipale finestraPrincipale;
	private JPanel panelData;
	private JPanel panelButton;
	
	public EditorPersona(FinestraPrincipale finestraPrincipale, Controller controller, String[] infoPersona) {
		
		setAttributes();
		
		this.finestraPrincipale = finestraPrincipale;
		
		String oldTelephone = infoPersona[3];
		
		fieldName.setText(infoPersona[0]);
		fieldSurname.setText(infoPersona[1]);
		fieldAddress.setText(infoPersona[2]);
		fieldTelephone.setText(infoPersona[3]);
		fieldAge.setText(infoPersona[4]);
		
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String name = fieldName.getText();
				String surname = fieldSurname.getText();
				String address = fieldAddress.getText();
				String telephone = fieldTelephone.getText();
				String ageStr = fieldAge.getText();
				
				if(!checkTelephone(telephone)) {
					return;
				}
				
				//String ageStr = fieldAge.getText();
				if( !checkEmptyField(name, surname, address, telephone, ageStr) ) {
					return;
				}
				
				int age;
				try {
					age = Integer.parseInt(fieldAge.getText());
				} catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "Errore: l'eta deve essere un valore numerico");
					return;
				}
				
				
				
				int value = controller.modifyPersona(name, surname, address, telephone, age, oldTelephone);
				if(value == -1) {
					JOptionPane.showMessageDialog(null, "Errore: il numero inserito è già esistente");
					return;
				}
				
				finestraPrincipale.refreshTable();
				  
				backToFinestraPrincipale();
			}
		});
		
		buttonCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backToFinestraPrincipale();
			}
		});
		
		setInterface();
		
	}
	
	public EditorPersona(FinestraPrincipale finestraPrincipale, Controller controller) {
		
		setAttributes();
		
		this.finestraPrincipale = finestraPrincipale;
		
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String name = fieldName.getText();
				String surname = fieldSurname.getText();
				String address = fieldAddress.getText();
				String telephone = fieldTelephone.getText(); 
				
				if(!checkTelephone(telephone)) {
					return;
				}
				
				String ageStr = fieldAge.getText();
				
				if( !checkEmptyField(name, surname, address, telephone, ageStr) ) {
					return;
				}
				
				int age;
				try {
					age = Integer.parseInt(fieldAge.getText());
				} catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "Errore: l'eta deve essere un valore numerico");
					return;
				}
				int value = controller.addPersona(name, surname, address, telephone,age );
				if(value == -1) {
					JOptionPane.showMessageDialog(null, "Errore: il numero inserito è già esistente");
					return;
				}
				finestraPrincipale.refreshTable();
				backToFinestraPrincipale();
				
			}
		});
		
		buttonCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backToFinestraPrincipale();
			}
		});
		
		setInterface();
		
	}
	
	public boolean checkEmptyField(String name, String surname, String address, String telephone, String ageStr) {
		if(!name.isEmpty() && !surname.isEmpty() && !address.isEmpty() && !telephone.isEmpty() && !ageStr.isEmpty()) {
			return true;
		}
		JOptionPane.showMessageDialog(null, "Errore: alcuni campi sono vuoti");
		return false;
	}
	
	public boolean checkTelephone(String telephone) {
		for(int i =0; i < telephone.length(); i++) {
			if ( !Character.isDigit(telephone.charAt(i)) ) {
				JOptionPane.showMessageDialog(null, "Errore: il numero di telefono può contenere solo caratteri numerici");
				return false;
			}
			
		}
		if( telephone.length() > 12 ) {
			JOptionPane.showMessageDialog(null, "Errore: il numero di telefono non può superare le 12 cifre");
			return false;
		}
		return true;
	}
	
	public void backToFinestraPrincipale() {
		finestraPrincipale.setVisible(true);
		setVisible(false);
	}
	
	public void setAttributes() {
		
		labelName = new JLabel("Nome");
		fieldName = new JTextField(15);
		labelSurname = new JLabel("Cognome");
		fieldSurname = new JTextField(15);
		labelAddress = new JLabel("Indirizzo");
		fieldAddress = new JTextField(15);
		labelTelephone = new JLabel("Telefono");
		fieldTelephone = new JTextField(15);
		labelAge = new JLabel("Età");
		fieldAge = new JTextField(15);
		buttonSave = new JButton("Salva");
		buttonCancel = new JButton("Annulla");
	}
	
	public void setInterface() {
		setLayout(new BorderLayout());
		panelData = new JPanel();
		panelButton = new JPanel();
		
		panelData.setLayout(new GridBagLayout());
		panelButton.setLayout(new FlowLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		//gbc.gridwidth = 2;
		//gbc.gridheight = 1;
		//gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;
		panelData.add(labelName, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		panelData.add(fieldName, gbc);
		
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.anchor = GridBagConstraints.WEST;
		panelData.add(labelSurname, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		panelData.add(fieldSurname, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.anchor = GridBagConstraints.WEST;
		panelData.add(labelAddress, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		panelData.add(fieldAddress, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.anchor = GridBagConstraints.WEST;
		panelData.add(labelTelephone, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		panelData.add(fieldTelephone, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.anchor = GridBagConstraints.WEST;
		panelData.add(labelAge, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		panelData.add(fieldAge, gbc);
		
		panelButton.add(buttonSave);
		panelButton.add(buttonCancel);
		
		add(panelData, BorderLayout.CENTER);
		add(panelButton, BorderLayout.PAGE_END);
		setSize(800,500);	
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}

}
