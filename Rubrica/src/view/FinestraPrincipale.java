package view;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.Controller;



public class FinestraPrincipale extends JFrame{
	
	private TablePanel tablePanel;
	private Controller controller;
	private JButton buttonNew;
	private JButton buttonModify;
	private JButton buttonDelete;
	private FinestraPrincipale finestraPrincipale;
	private JPanel panel;

	
	public FinestraPrincipale() {
		
		super("Rubrica");
		
		setLayout(new BorderLayout());
		
		controller = new Controller();
		tablePanel = new TablePanel(controller);
		buttonNew = new JButton("Nuovo");
		buttonModify = new JButton("Modifica");
		buttonDelete = new JButton("Elimina");
		panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		panel.add(buttonNew);
		panel.add(buttonModify);
		panel.add(buttonDelete);
		
		controller.createDir();
		
		finestraPrincipale = this;
		
		buttonNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				EditorPersona editorPersona = new EditorPersona(finestraPrincipale, controller);
				editorPersona.setVisible(true);
				setVisible(false);
				//controller.addPersona("Giovanni", "rossi", "via delle pere", "3392162800",34 );
				//tablePanel.updating();
			}
		});
		
		buttonModify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Persona persona = tablePanel.getInfoPersona();
				String[] infoPersona = tablePanel.getInfoPersona();
				if(infoPersona != null) {
					EditorPersona editorPersona = new EditorPersona(finestraPrincipale, controller, infoPersona);
					editorPersona.setVisible(true);
					setVisible(false);
				}
			}
		});
		
		buttonDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					tablePanel.deletePersona();
					tablePanel.updating();    
			}
		});
		
		tablePanel.setData(controller.getPersone());
		
		add(tablePanel, BorderLayout.CENTER);
		add(panel, BorderLayout.PAGE_END);
		
		
		
		setSize(800,500);	
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void refreshTable() {
		tablePanel.updating();    
	}

}
