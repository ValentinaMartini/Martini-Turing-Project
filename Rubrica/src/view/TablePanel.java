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
		
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		if (defaults.get("Table.alternateRowColor") == null)
		    defaults.put("Table.alternateRowColor", new Color(255, 228, 225));
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
	
	
	
	public void deletePersona() {
		// check for selected row first
		int row = table.getSelectedRow();
	    if(row != -1) { 
	    	
	    	String name = table.getValueAt(row, 0).toString();
	    	String surname = table.getValueAt(row, 1).toString();
	    	String telephone = table.getValueAt(row, 2).toString();
	    	
	    	int result = JOptionPane.showConfirmDialog(null, "Eliminare la persona " + name + " "+ surname + "?" );
	    	
	    	if(result == 0) {
	    		controller.deletePersona(telephone);
		    	JOptionPane.showMessageDialog(null, "Contatto eliminato con successo");
	    	}
	    
	    	System.out.println(result);
	    	
	    	//controller.deletePersona(value);
	    	//JOptionPane.showMessageDialog(null, "Selected row deleted successfully");
	    }
	    else {
	    	JOptionPane.showMessageDialog(null, "Errore: selezionare prima il contatto da eliminare");
	    }
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

