package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

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
	private Controller controller;
	JToolBar toolbar;
	
	//costruttore che viene chiamato quando si clicca su modifica contatto dalla finestra principale
	public EditorPersona(FinestraPrincipale finestraPrincipale, Controller controller, String[] infoPersona) {
		
		this.controller = controller;
		setAttributes();
		
		this.finestraPrincipale = finestraPrincipale;

		String oldTelephone = infoPersona[3];
		
		//valorizzo i textField con i dati del contatto che è stato selezionato per la modifica
		fieldName.setText(infoPersona[0]);
		fieldSurname.setText(infoPersona[1]);
		fieldAddress.setText(infoPersona[2]);
		fieldTelephone.setText(infoPersona[3]);
		fieldAge.setText(infoPersona[4]);
		
		//gestione del pulsante salva: se tutto va a buon fine, aggiorna il file specifico del contatto modificato, aggiorna la tabella contatti, torna alla finestra principale
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String name = capitalizeFirstLetter(fieldName.getText());//prendo il nome con la lettera maiuscola
				String surname = capitalizeFirstLetter(fieldSurname.getText());//prendo il cognome con la lettera maiuscola
				String address = fieldAddress.getText();
				String telephone = fieldTelephone.getText();
				String ageStr = fieldAge.getText();
				
				if(!checkTelephone(telephone)) { //controllo che il numero di telefono sia costituito solo da numeri
					return;
				}
				
				if( !checkEmptyField(name, surname, address, telephone, ageStr) ) {//controllo che non siano stati lasciati campi vuoti
					return;
				}
				
				int age;
				try {
					age = Integer.parseInt(fieldAge.getText());	//controllo che l'età sia costituito solo da numeri
				} catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "Errore: l'eta deve essere un valore numerico");
					return;
				}
				
				
				
				int value = controller.modifyPersona(name, surname, address, telephone, age, oldTelephone); //aggiorna il file specifico del contatto modificato, aggiorna la tabella contatti 
				if(value == -1) {
					JOptionPane.showMessageDialog(null, "Errore: il numero inserito è già esistente"); 
					return;
				}
				
				finestraPrincipale.refreshTable();
				  
				backToFinestraPrincipale();
			}
		});
		
		//gestione del pulsante annulla: torna alla finestra principale senza modificare nulla
		buttonCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backToFinestraPrincipale();
			}
		});
		
		setInterface();
		
	}
	
	//costruttore che viene chiamato quando si clicca su aggiungi contatto dalla finestra principale
	public EditorPersona(FinestraPrincipale finestraPrincipale, Controller controller) {
		
		this.controller = controller;
		setAttributes();
		
		this.finestraPrincipale = finestraPrincipale;
		
		//gestione del pulsante salva: se tutto va a buon fine, aggiunge il file specifico del contatto inserito, aggiorna la tabella contatti, torna alla finestra principale
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String name = capitalizeFirstLetter(fieldName.getText()); //prendo il nome con la lettera maiuscola
				String surname = capitalizeFirstLetter(fieldSurname.getText());//prendo il cognome con la lettera maiuscola
				String address = fieldAddress.getText();
				String telephone = fieldTelephone.getText(); 
				String ageStr = fieldAge.getText();
				
				if(!checkTelephone(telephone)) {//controllo che il numero di telefono sia costituito solo da numeri
					return;
				}
		
				if( !checkEmptyField(name, surname, address, telephone, ageStr) ) {//controllo che non siano stati lasciati campi vuoti
					return;
				}
				
				int age;
				try {
					age = Integer.parseInt(fieldAge.getText()); //controllo che l'età sia costituito solo da numeri
				} catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "Errore: l'eta deve essere un valore numerico");
					return;
				}
				
				int value = controller.addPersona(name, surname, address, telephone,age ); //aggiunge il file specifico del contatto modificato, aggiorna la tabella contatti 
				if(value == -1) {
					JOptionPane.showMessageDialog(null, "Errore: il numero inserito è già esistente");
					return;
				}
				finestraPrincipale.refreshTable();
				backToFinestraPrincipale();
				
			}
		});
		
		//gestione del pulsante annulla: torna alla finestra principale senza salvare nulla
		buttonCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backToFinestraPrincipale();
			}
		});
		
		setInterface();
		
	}
	
	//controlla che i campi non siano vuoti
	public boolean checkEmptyField(String name, String surname, String address, String telephone, String ageStr) {
		if(!name.isEmpty() && !surname.isEmpty() && !address.isEmpty() && !telephone.isEmpty() && !ageStr.isEmpty()) {
			return true;
		}
		JOptionPane.showMessageDialog(null, "Errore: alcuni campi sono vuoti");
		return false;
	}
	
	//controlla che il numero di telefono sia un valore numerico e che sia minore si 12 caratteri
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
	
	//metodo che torna alla finestra principale
	public void backToFinestraPrincipale() {
		finestraPrincipale.setVisible(true);
		setVisible(false);
	}
	
	
	public void setAttributes() {
		
		labelName = new JLabel("Nome");
		labelName.setPreferredSize(new Dimension(80,30));
		fieldName = new JTextField(15);
		
		labelSurname = new JLabel("Cognome");
		labelSurname.setPreferredSize(new Dimension(80,30));
		fieldSurname = new JTextField(15);
		
		labelAddress = new JLabel("Indirizzo");
		labelAddress.setPreferredSize(new Dimension(80,30));
		fieldAddress = new JTextField(15);
		
		labelTelephone = new JLabel("Telefono");
		labelTelephone.setPreferredSize(new Dimension(80,30));
		fieldTelephone = new JTextField(15);
		
		labelAge = new JLabel("Età");
		labelAge.setPreferredSize(new Dimension(80,30));
		fieldAge = new JTextField(15);
		
		try {
			ImageIcon iconSave = createIcon("saveIcon.png");
			buttonSave = new JButton(iconSave);
			buttonSave.setToolTipText("Salva");
			buttonSave.setBackground(new Color(130, 224, 170));
			buttonSave.setOpaque(true);
			
			ImageIcon iconCancel = createIcon("cancelIcon.png");
			buttonCancel = new JButton(iconCancel);
			buttonCancel.setToolTipText("Annulla");
			buttonCancel.setBackground(new Color(241, 148, 138 ));
			buttonCancel.setOpaque(true);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} 	
		
		
		toolbar = new JToolBar();
		
		buttonSave.setPreferredSize(new Dimension(50, 50));
		buttonCancel.setPreferredSize(new Dimension(50, 50));
	}
	
	private ImageIcon createIcon(String iconName) throws IOException {
		Image img = ImageIO.read(new File(controller.getIconPath(iconName)));
		Image saveImg = img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(saveImg);
	}
	
	public void setInterface() {
		
		setLayout(new BorderLayout());
		panelData = new JPanel();
		//panelButton = new JPanel();
		
		panelData.setLayout(new GridBagLayout());
		//panelButton.setLayout(new FlowLayout());
		
		toolbar.add(buttonSave);
		toolbar.add(buttonCancel);
		
		toolbar.setFloatable(false);
		toolbar.setRollover(true);

		toolbar.setLayout(new FlowLayout(FlowLayout.CENTER));
		
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
		
		//panelButton.add(buttonSave);
		//panelButton.add(buttonCancel);
		
		add(panelData, BorderLayout.CENTER);
		//add(panelButton, BorderLayout.PAGE_END);
		add(toolbar, BorderLayout.PAGE_END);
		setSize(600,400);	
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	

	//metodo per salvare una stringa con la prima lettera maiuscola
	private String capitalizeFirstLetter(String str) {
		if(str.length() == 0) {
			return "";
		}
		return  str.substring(0, 1).toUpperCase() + str.substring(1);
	}
}
