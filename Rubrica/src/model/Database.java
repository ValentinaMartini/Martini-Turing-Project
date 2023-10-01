package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Database {

	private ArrayList<Persona> persone;
	static String dirResourcePath = "src";
	static String delimiter = "\\";
	static String filePathUser =dirResourcePath + delimiter + "user.txt";
	static String dirPath = dirResourcePath + delimiter + "informazioni";
	static String dirIconPath = dirResourcePath + delimiter + "icon";
	

	public Database() {
		persone = new ArrayList<Persona>();
	}

	//metodo che ritorna la lista di contatti da mostrare all'interno della rubrica
	public List<Persona> getPersone() {
		return persone;
	}
	
	public String getIconPath(String iconName) {
		return dirIconPath + delimiter + iconName;
	}

	//metodo per creare il file in cui sono presente i dati degli utenti
	public void createFileUser() {
		createFile(filePathUser);
	}

	//metodo per creare i file
	private void createFile(String filePath) {
		try {
			File file = new File(filePath);
			
			if (file.createNewFile()) {
				System.out.println("File creato: " + file.getName());
			} else {
				System.out.println("File già esistente.");
			}

		} catch (IOException e) {
			System.out.println("Errore.");
			e.printStackTrace();
		}
	}
	
	//metodo per creare la cartella informazioni
	public void createDir() {
		try {
			File file = new File(dirPath);

			if (file.mkdir()) {
				System.out.println("Cartella creato: " + file.getName());
			} else {
				System.out.println("Cartella Informazioni già esistente.");
			}

			persone = initContacts(file.list());

		} catch (Exception e) {
			System.out.println("createDir() --> Exception: ");
			e.printStackTrace();
		}

	}
	
	//metodo per popolare la lista di contatti
	public ArrayList<Persona> initContacts(String[] fileList) {
		ArrayList<Persona> persone = new ArrayList<Persona>();

		for (int i = 0; i < fileList.length; i++) {

			try {
				File file = new File(dirPath + "\\" + fileList[i]);
				String line;
				Scanner scnr = new Scanner(file);

				if (scnr.hasNextLine()) {
					line = scnr.nextLine();
					String[] personaStr = line.split(";");
					if (personaStr.length == 5) {
						Persona persona = new Persona(personaStr[0], personaStr[1], personaStr[2], personaStr[3],
								Integer.parseInt(personaStr[4]));
						persone.add(persona);
					}
				}
				scnr.close();

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		Collections.sort(persone, NameComparator);

		return persone;
	}

	 
	//metodo che gestisce l'aggiunta di un nuovo contatto nella cartella informazioni. Viene creato un file per ogni contatto. Il nome del file sarà equivalente al numero di telefono
	public int addFilePerson(Persona persona) {
		try {
			Boolean value = checkUniqueNumber(persona.getTelephone()); //controllo che il numero del nuovo contatto da aggiungere non sia già presente in rubrica
			if (value == false) {
				return -1;
			}
			String str = persona.getName() + ";" + persona.getSurname() + ";" + persona.getAddress() + ";"
					+ persona.getTelephone() + ";" + persona.getAge() + "\n";
			String filePath = dirPath + delimiter + persona.getTelephone() + ".txt";

			createFile(filePath);

			PrintStream stream = new PrintStream(new FileOutputStream(filePath, true));
			stream.append(str);

			stream.close();
			addPersonaOnTable(persona);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}

	//metodo che gestisce l'aggiunta di un nuovo contatto nella tabella
	public void addPersonaOnTable(Persona persona) {
		persone.add(persona);
		Collections.sort(persone, NameComparator);
		System.out.println("Aggiunto: " + persona.getName() + persona.getSurname() + persona.getAddress()
				+ persona.getTelephone() + persona.getAge());

	}

	//metodo che gestisce la modifica di un contatto
	public int modifyFilePerson(Persona persona, String oldTelephone) {
		try {
			File file;
			if (!oldTelephone.equals(persona.getTelephone())) {
				Boolean value = checkUniqueNumber(persona.getTelephone()); //se è stato modificato il numero del contatto selezionato, controllo che il nuovo numero non sia già esistente in rubrica
				if (value == false) {
					return -1;
				}

				file = new File(dirPath + delimiter + oldTelephone + ".txt");
				File newFile = new File(dirPath + delimiter + persona.getTelephone() + ".txt");
				if (file.renameTo(newFile)) {	//visto che è stato modificato il numero di telefono, allora rinomino anche il file specifico per quel contatto
					System.out.println("Modifica contatto: File rinominato correttamente");
				} else {
					System.out.println("Modifica contatto: Errore rinominazione file");
				}

			} else {
				file = new File(dirPath + delimiter + oldTelephone + ".txt");
			}

			String str = persona.getName() + ";" + persona.getSurname() + ";" + persona.getAddress() + ";"
					+ persona.getTelephone() + ";" + persona.getAge();

			FileOutputStream fileOut = new FileOutputStream(dirPath + delimiter + persona.getTelephone() + ".txt");
			fileOut.write(str.getBytes());
			fileOut.close();

			modifyPersonaOnTable(persona, oldTelephone);
			System.out.println("Contatto modificato correttamente");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("Errore modifica contatto");
			e1.printStackTrace();
		}
		
		return 1;
	}

	//metodo che gestisce la modifica di un contatto nella tabella
	public void modifyPersonaOnTable(Persona persona, String oldTelephone) {
		int index = getPersonaIndex(oldTelephone);
		if (index != -1) {
			persone.set(index, persona);
		}

		Collections.sort(persone, NameComparator);

	}

	//metodo che gestisce l'eliminazione di un contatto nella cartella informazioni
	public void deleteFilePerson(String telephone) {

		File file = new File(dirPath + delimiter + telephone + ".txt");
		if (file.exists()) {

			if (file.delete()) {

				System.out.println(file.getName() + " cancellato");
			} else {
				System.out.println("fallito");
			}
		} else {
			System.out.println("Errore: file non trovato");
		}
		deletePersonaFromTable(telephone);
	}

	//metodo che gestisce l'eliminazione di un contatto dalla tabella
	public void deletePersonaFromTable(String telephone) {

		int index = getPersonaIndex(telephone);
		if (index != -1) {
			persone.remove(index);
		}

	}

	//ritorna l'indice del contatto nella lista di persone a cui corrisponde il numero di telefono passato in input
	public int getPersonaIndex(String telephone) {
		for (int i = 0; i < persone.size(); i++) {

			String tel = persone.get(i).getTelephone();
			if (tel.equals(telephone)) {
				return i;
			}
		}
		return -1;
	}

	//metodo per prendere le informazioni del contatto a cui corrisponde il numero di telefono passato in input
	public Persona getInfoPersona(String telephone) {
		for (int i = 0; i < persone.size(); i++) {
			String tel = persone.get(i).getTelephone();
			if (tel.equals(telephone)) {
				return persone.get(i);
			}
		}
		return null;
	}

	//metodo per controllare che il numero di telefono sia univoco
	public Boolean checkUniqueNumber(String telephone) {
		for (int i = 0; i < persone.size(); i++) {
			String tel = persone.get(i).getTelephone();
			if (tel.equals(telephone)) {
				return false;

			}
		}
		return true;
	}

	//metodo per creazione del comparator per ordinare in ordine alfabetico i contatti nella tabella
	public static Comparator<Persona> NameComparator = new Comparator<Persona>() {

		@Override
		public int compare(Persona e1, Persona e2) {
			int value = e1.getName().compareTo(e2.getName());
			if (value != 0) {
				return (value);
			}
			return (int) (e1.getSurname().compareTo(e2.getSurname()));
		}
	};
	
	//metodo per controllare che le credenziali del login siano corrette
	public boolean checkUser(String username, String password) {

		try {
			File file = new File(filePathUser);
			String line;
			Scanner scnr = new Scanner(file);

			while (scnr.hasNextLine()) {
				line = scnr.nextLine();
				String[] personaStr = line.split(";");
				String encryptPassword = getMd5Hash(password);
				if (personaStr[0].equals(username) && personaStr[1].equals(encryptPassword)) {
					return true;
				}
			}
			scnr.close();

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return false;
	}
	
	//metodo per criptare la password dell'utente
	public static String getMd5Hash(String input)  {  
		try   {  
			MessageDigest md = MessageDigest.getInstance("MD5");  
			byte[] messageDigest = md.digest(input.getBytes());  
			BigInteger no = new BigInteger(1, messageDigest);  
			String hashtext = no.toString(16);  
			while (hashtext.length() < 32)   {  
				hashtext = "0" + hashtext;  
			}  
			return hashtext;  
		}  
		
		catch (NoSuchAlgorithmException e)   {  
			throw new RuntimeException(e);  
		}  
	}  
}
