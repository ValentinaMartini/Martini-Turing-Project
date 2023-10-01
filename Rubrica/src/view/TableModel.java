package view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import model.Persona;

public class TableModel extends AbstractTableModel{
	
	private List<Persona> listaPersone = new ArrayList<Persona>();
	private String[] nameColumn = {"NOME", "COGNOME", "NUMERO"};
	

	
	public TableModel() {
		
	}
	
	public void setData(List<Persona> listaPersone) {
		this.listaPersone = listaPersone;
	}
	
	
	 
	@Override
	public int getColumnCount() {	
		return 3;
	}

	@Override
	public int getRowCount() {
		return listaPersone.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		Persona persona = listaPersone.get(arg0);
		switch(arg1) {
		case 0:
			return persona.getName();
		case 1:
			return persona.getSurname();
		case 2:
			return persona.getTelephone();
		default:
			return null;
		}
		
	}
	
	@Override
	public String getColumnName(int column) {	
		return nameColumn[column];
	}
	
	
	

}
