package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;
import model.Persona;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;

import controller.Controller;

public class TablePanel extends JPanel{
	
	private JTable table;
	private TableModel tableModel;
	private Controller controller;

	
	public TablePanel(Controller controller) {
		
		tableModel = new TableModel();
		table = new JTable(tableModel);
		JTableHeader header = table.getTableHeader();
	    header.setBackground(new Color(165, 105, 189));
	    header.setForeground(Color.black);
		
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		if (defaults.get("Table.alternateRowColor") == null)
		    defaults.put("Table.alternateRowColor", new Color(232, 218, 239));
		//table.setAutoCreateRowSorter(true);
		this.controller = controller;
		
		setLayout(new BorderLayout());
		add(new JScrollPane(table),BorderLayout.CENTER);
	}
	
	public void setData(List<Persona> listaPersone) {
		tableModel.setData(listaPersone);
	}
	
	public void updating() {
		tableModel.fireTableDataChanged();
	}
	
	public int deletePersona() {
		// check for selected row first
		int row = table.getSelectedRow();
		int result = -1;
	    if(row != -1) { 
	    	
	    	String name = table.getValueAt(row, 0).toString();
	    	String surname = table.getValueAt(row, 1).toString();
	    	String telephone = table.getValueAt(row, 2).toString();
	    	
	    	result = JOptionPane.showConfirmDialog(null, "Eliminare il contatto " + name + " "+ surname + "?", "Delete Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE );
	    	
	    	if(result == 0) {
	    		controller.deletePersona(telephone);
		    	JOptionPane.showMessageDialog(null, "Contatto eliminato con successo");
	    	}   	
	    }
	    else {
	    	JOptionPane.showMessageDialog(null, "Errore: selezionare prima il contatto da eliminare");
	    }
	    
	    return result;
	}
	
	public String[] getInfoPersona() {
		// check for selected row first
		int row = table.getSelectedRow();
	    if(row != -1) { 
	    	String telephone = table.getValueAt(row, 2).toString();
	    	String[] infoPersona = controller.getInfoPersona(telephone);
	    	//Persona persona = controller.getInfoPersona(telephone);
	    	return infoPersona;
	    }
	    else {
	    	JOptionPane.showMessageDialog(null, "Errore: selezionare prima il contatto da modificare");
	    	
	    }
		return null;
	}
	
	
	
}

