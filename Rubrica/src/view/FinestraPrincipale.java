package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import controller.Controller;

public class FinestraPrincipale extends JFrame {

	private TablePanel tablePanel;
	private Controller controller;
	private JButton buttonNew;
	private JButton buttonModify;
	private JButton buttonDelete;
	private FinestraPrincipale finestraPrincipale;
	JToolBar toolbar;

	public FinestraPrincipale() {

		super("Rubrica");
		setLayout(new BorderLayout());
		
		controller = new Controller();

		try {
			
			//setto le icone dei pulsanti aggiunti/modifica/elimina
			ImageIcon iconAdd = createIcon("addIcon.png"); 			
			ImageIcon iconModify = createIcon("modifyIcon.png"); 
			ImageIcon iconDelete = createIcon("deleteIcon.png"); 

			tablePanel = new TablePanel(controller);
			
			buttonNew = new JButton(iconAdd);
			buttonNew.setToolTipText("Aggiungi");
			buttonNew.setBackground(new Color(130, 224, 170));
			buttonNew.setOpaque(true);

			buttonModify = new JButton(iconModify);
			buttonModify.setToolTipText("Modifica");
			buttonModify.setBackground(new Color(247, 220, 111));
			buttonModify.setOpaque(true);

			buttonDelete = new JButton(iconDelete);
			buttonDelete.setToolTipText("Elimina");
			buttonDelete.setBackground(new Color(241, 148, 138));
			buttonDelete.setOpaque(true);

			toolbar = new JToolBar();

			toolbar.add(buttonNew);

			toolbar.add(buttonModify);

			toolbar.add(buttonDelete);
			toolbar.setFloatable(false);
			toolbar.setRollover(true);

			toolbar.setLayout(new FlowLayout(FlowLayout.CENTER));
			
			
			controller.createDir();	//creo la cartella informazioni che contiene i file per i sincoli contatti

			finestraPrincipale = this;
			
			//Gestione pulsante Aggiunti: se si clicca il pulsante, viene mostrata la finestra editor persona in cui poter aggiungere un nuovo contatto
			buttonNew.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					EditorPersona editorPersona = new EditorPersona(finestraPrincipale, controller);
					editorPersona.setVisible(true);
					setVisible(false);
					
				}
			});
			
			//Gestione pulsante modifica: se si clicca il pulsante, dopo aver selezionato un cotatto, viene mostrata la finestra editor-persona in cui poter modificare un contatto già esistente		
			buttonModify.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					String[] infoPersona = tablePanel.getInfoPersona(); //prendo le informazioni del contatto selezionato per inizializzare la finestra editor-persona
					if (infoPersona != null) {
						EditorPersona editorPersona = new EditorPersona(finestraPrincipale, controller, infoPersona);
						editorPersona.setVisible(true);
						setVisible(false);
					}
				}
			});
			
			//Gestione pulsante elimina: se si clicca il pulsante, dopo aver selezionato un cotatto verrà mostrato un msg di conferma
			//in caso di conferma positiva si elimina il contatto, altrimenti non si fa nulla
			buttonDelete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if( tablePanel.deletePersona() == 0) {
						tablePanel.updating();	//aggiorno la tabella dopo l'eliminazione del contatto
					}
				}
			});
			
			//setto la tabella dei contatti con i contatti già esistente in rubrica
			tablePanel.setData(controller.getPersone());

			add(tablePanel, BorderLayout.CENTER);
			add(toolbar, BorderLayout.PAGE_END);

			setSize(600, 400);
			setLocationRelativeTo(null);
			setResizable(false);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
		
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	//metodo per aggiornare i dati nella tabella
	public void refreshTable() {
		tablePanel.updating();
	}
	
	//metodo per settare le icone da inserire nei pulsanti aggiungi/modifica/elimina
	private ImageIcon createIcon(String iconName) throws IOException {
		Image img = ImageIO.read(new File(controller.getIconPath(iconName)));
		Image addImg = img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(addImg);
	}

}
