package controller;

import java.io.FileNotFoundException;
import java.util.List;

import model.Database;
import model.Persona;

public class Controller {
	
	Database database = new Database();
	public void createFile() {
		database.createFile();
		
	}
	public int addPersona(String name, String surname,String address,String telephone, int age) {
		Persona persona = new Persona(name, surname, address, telephone, age);
		int value = database.addPersonaOnFile(persona);
		return value;
		
	}
	
	/*public int modifyPersona(String name, String surname,String address,String telephone, int age, String oldTelephone){
		Persona persona = new Persona(name, surname, address, telephone, age);
		int value = database.modifyPersonaOnFile( persona, oldTelephone);
		return value;
		
	}*/
	
	public int modifyPersona(String name, String surname,String address,String telephone, int age, String oldTelephone){
		Persona persona = new Persona(name, surname, address, telephone, age);
		int value = database.modifyPersonaOnFile(persona, oldTelephone);
		return value;
		
	}
	
	public void deletePersona(String telephone){
		database.deletePersonaFromFile(telephone);
		
	}
	
	
	
	public List<Persona> getPersone(){
		return database.getPersone();
	}
	
	public String[] getInfoPersona(String telephone) {
		Persona persona = database.getInfoPersona(telephone);
		String[] infoPersona = new String[5];
		infoPersona[0] = persona.getName();
		infoPersona[1] = persona.getSurname();
		infoPersona[2] = persona.getAddress();
		infoPersona[3] = persona.getTelephone();
		infoPersona[4] = Integer.toString(persona.getAge());
		return infoPersona;
	}

}
