package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;

import controller.Controller;
import model.Persona;

public class TablePanel extends JPanel{
	
	private JTable table;
	private TableModel tableModel;
	private Controller controller;

	
	public TablePanel(Controller controller) {
		
		tableModel = new TableModel();
		table = new JTable(tableModel);
		
		table.getTableHeader().setReorderingAllowed(false); //riordinamento colonne disabilitato
	    table.getTableHeader().setResizingAllowed(false);	//ridimentionamento colonne disabilitato
		JTableHeader header = table.getTableHeader();
	    header.setBackground(new Color(165, 105, 189)); //colore header tabella
	    header.setForeground(Color.black);
		
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		if (defaults.get("Table.alternateRowColor") == null)
		    defaults.put("Table.alternateRowColor", new Color(232, 218, 239)); //colore righe alternate
		this.controller = controller;
		
		setLayout(new BorderLayout());
		add(new JScrollPane(table),BorderLayout.CENTER);
	}
	
	//setta i dati all'interno della tabella
	public void setData(List<Persona> listaPersone) {
		tableModel.setData(listaPersone);
	}
	
	//aggiorna i dati della tabella
	public void updating() {
		tableModel.fireTableDataChanged();
	}
	
	//metodo per eliminate un contatto
	public int deletePersona() {
		int row = table.getSelectedRow();
		int result = -1;
	    if(row != -1) { //controllo se prima è stato selezionato un contatto da eliminare
	    	
	    	String name = table.getValueAt(row, 0).toString();
	    	String surname = table.getValueAt(row, 1).toString();
	    	String telephone = table.getValueAt(row, 2).toString();
	    	
	    	result = JOptionPane.showConfirmDialog(null, "Eliminare il contatto " + name + " "+ surname + "?", "Delete Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE );
	    	
	    	if(result == 0) {
	    		controller.deletePersona(telephone);	//elimino il contatto: elimino il file specifico dalla cartella informazioni
		    	JOptionPane.showMessageDialog(null, "Contatto eliminato con successo");
	    	}   	
	    }
	    else {
	    	JOptionPane.showMessageDialog(null, "Errore: selezionare prima il contatto da eliminare");
	    }
	    
	    return result;
	}
	
	//prende le informazioni del contatto selezionato
	public String[] getInfoPersona() {
		
		int row = table.getSelectedRow();
	    if(row != -1) {  //controllo che sia stato selezionato un contatto
	    	String telephone = table.getValueAt(row, 2).toString();
	    	String[] infoPersona = controller.getInfoPersona(telephone);
	    	return infoPersona;
	    }
	    else {
	    	JOptionPane.showMessageDialog(null, "Errore: selezionare prima il contatto da modificare");
	    	
	    }
		return null;
	}
	
	
	
}

