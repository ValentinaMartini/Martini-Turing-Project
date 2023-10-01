package controller;

import java.util.List;

import model.Database;
import model.Persona;

public class Controller {
	
	Database database = new Database();
	
	//creazione file contenente i dati degli utenti
	public void createFileUser() {
		database.createFileUser();
		
	}
	
	//creazione cartella informazioni
	public void createDir() {
		database.createDir();
		
	}
	
	//ritorna il path dell'icona
	public String getIconPath(String iconName) {
		return database.getIconPath(iconName);
	}
	
	//metodo per aggiungere un nuovo contatto
	public int addPersona(String name, String surname,String address,String telephone, int age) {
		Persona persona = new Persona(name, surname, address, telephone, age);
		int value = database.addFilePerson(persona);
		return value;
		
	}
	
	//metodo per modificare un contatto
	public int modifyPersona(String name, String surname,String address,String telephone, int age, String oldTelephone){
		Persona persona = new Persona(name, surname, address, telephone, age);
		int value = database.modifyFilePerson(persona, oldTelephone);
		return value;
		
	}
	
	//metodo per eliminare un contatto
	public void deletePersona(String telephone){
		database.deleteFilePerson(telephone);
		
	}
	
	//metodo che ritorna la lista dei contatti
	public List<Persona> getPersone(){
		return database.getPersone();
	}
	
	//metodo per prendere le informazioni del contatto, facendo la ricerca tramite telefono
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
	
	//metodo per controllare le credenziali del login
	public boolean checkUser(String username, String password) {
		boolean value = database.checkUser(username, password);
		return value;
	}
}
