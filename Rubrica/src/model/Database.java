package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Database {
	
	private ArrayList<Persona> persone;
	static String filePathContatti = "src\\contatti\\informazioni2.txt";
	static String filePathUser = "src\\user.txt";
	static String dirPath = "src\\informazioni";
	static String delimiter = "\\";
	private long time = System.currentTimeMillis();
	
	public Database() {
		persone = new ArrayList<Persona>();
	}
	
	public List<Persona> getPersone() {
		return persone;
	}
	
	public void createFileContatti() {
		createFile(filePathContatti);
	}
	
	public void createFileUser() {
		createFile(filePathUser);
	}
	
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
		System.out.println("createFile-> Persone: " + persone.toString() + time);
	}
	
	public void createDir() {
		try {
		      File file = new File(dirPath);
		      
		      if (file.mkdir()) {
		        System.out.println("Cartella creato: " + file.getName());
		      } else {
		        System.out.println("Cartella già esistente.");
		      }
		      
		      persone = initContacts(file.list());
      
		 } catch (Exception e) {
		      System.out.println("Errore.");
		      e.printStackTrace();
		 }
		System.out.println("createFile-> Persone: " + persone.toString() + time);
	}
	
	public ArrayList<Persona> initContacts(String[] fileList){
		ArrayList<Persona> persone = new ArrayList<Persona>();
		
		for(int i = 0; i < fileList.length; i++) {
			
			System.out.println("File nella lista:" + fileList[i]);
			
			try {
				System.out.println("ENTRO NEL TRY\n");
				File file = new File(dirPath +"\\"+fileList[i] );
		        String line;
				Scanner scnr = new Scanner(file);
			
			    if(scnr.hasNextLine()){
			         line = scnr.nextLine();
			         System.out.println("Line: " + line);
			         String[] personaStr = line.split(";");
			         if(personaStr.length == 5) {
			        	 Persona persona = new Persona(personaStr[0], personaStr[1], personaStr[2], personaStr[3], Integer.parseInt(personaStr[4]));
				         persone.add(persona);
			         }
			     }
			    scnr.close();
    
			}catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	    Collections.sort(persone,NameComparator);
		
		return persone;
	}
	
	public void addPersonaOnTable(Persona persona) {
		persone.add(persona);	
		Collections.sort(persone,NameComparator);
		System.out.println("Aggiunto: " + persona.getName() + persona.getSurname() + persona.getAddress()+ persona.getTelephone()+ persona.getAge() );
		
	}
	
	public int addPersonaOnFile(Persona persona) {
		try {
			Boolean value = checkUniqueNumber(persona.getTelephone());
			if(value == false) {
				return -1;
			}
			String str = persona.getName()+";"+persona.getSurname()+";"+persona.getAddress()+";"+persona.getTelephone()+";"+persona.getAge()+"\n";
			String filePath = dirPath+delimiter+persona.getTelephone()+".txt";
			
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
	
	public int modifyPersonaOnFile(Persona persona, String oldTelephone) {
		try {
			
			if(!oldTelephone.equals(persona.getTelephone())) {
				Boolean value = checkUniqueNumber(persona.getTelephone());
				if(value == false) {
					return -1;
				}
				
			}
			
			BufferedReader file = new BufferedReader(new FileReader(filePathContatti));
	        StringBuffer inputBuffer = new StringBuffer();
	        String line;
			Scanner scnr = new Scanner(file);
		    //String telephone = persona.getTelephone();
		    String str = persona.getName()+";"+persona.getSurname()+";"+persona.getAddress()+";"+persona.getTelephone()+";"+persona.getAge();
		    String[] personaStr;
		    while ((line = file.readLine()) != null) {
		    	
		    	personaStr = line.split(";");  
	            
		    	if(personaStr[3].equals(oldTelephone)) {
		        	line = str; 
		         }
		    	inputBuffer.append(line);
	            inputBuffer.append('\n');
	            
	            
	        }
		    file.close();
		    FileOutputStream fileOut = new FileOutputStream(filePathContatti);
	        fileOut.write(inputBuffer.toString().getBytes());
	        fileOut.close();
	          
		    
		    modifyPersonaOnTable(persona, oldTelephone);
		}catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return 1;
	}
	
	public void modifyPersonaOnTable(Persona persona, String oldTelephone) {
		int index = getPersonaIndex(oldTelephone);
		if(index != -1) {
			persone.set(index, persona);
		}
		
		Collections.sort(persone,NameComparator);
		
	}
	
	public void deletePersonaFromFile(String telephone) {

		File file = new File(dirPath+delimiter+telephone+".txt");
		System.out.println( "file: " +dirPath+delimiter+telephone+".txt");  
		if(file.exists()) {
			
			if (file.delete())  {  
				
				System.out.println(file.getName() +  " cancellato" );   //recupera e stampa il nome del file  
			}  
			else{  
				System.out.println( "fallito" );  
			}  
		}
		else {
			System.out.println("Errore: file non trovato");
		}
		deletePersonaFromTable(telephone);
	}
	
	public void deletePersonaFromTable(String telephone) {
		//System.out.println("TELEFONO DELLA PERSONA DA ELIMINARE: " + telephone);
		
		int index = getPersonaIndex(telephone);
		if(index != -1) {
			persone.remove(index);
		}
		
	}
	
	public ArrayList<Persona> getPersone2(File file) throws FileNotFoundException {
		ArrayList<Persona> persone = new ArrayList<Persona>();
		
		Scanner scnr = new Scanner(file);
	      
	    while(scnr.hasNextLine()){
	         String line = scnr.nextLine();
	         System.out.println("Line: " + line);
	         String[] personaStr = line.split(";");
	         if(personaStr.length != 5) {
	        	 continue;
	         }
	         Persona persona = new Persona(personaStr[0], personaStr[1], personaStr[2], personaStr[3], Integer.parseInt(personaStr[4]));
	         persone.add(persona);
	         //System.out.println(personaStr[0]);
	     }  
	    Collections.sort(persone,NameComparator);
	    return persone;       
	}
	
	public int getPersonaIndex(String telephone) {
		for(int i = 0; i < persone.size(); i++) {
			
			String tel = persone.get(i).getTelephone();
			if (tel.equals(telephone)) {
				return i;
			}
		}
		return -1;
	}
	
	public Persona getInfoPersona(String telephone) {
		for(int i = 0; i < persone.size(); i++) {
			String tel = persone.get(i).getTelephone();
			System.out.println("tel: " + tel);
			if (tel.equals(telephone)) {
				return persone.get(i);
			}
		}
		return null;
	}
	
	public Boolean checkUniqueNumber(String telephone) {
		for(int i = 0; i < persone.size(); i++) {
			String tel = persone.get(i).getTelephone();
			if (tel.equals(telephone)) {
		    	return false;
				
			}
		}
		return true;
		
	}
	
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
    
    public boolean checkUser(String username, String password) {
    	try {
			File file = new File(filePathUser);
			//StringBuffer inputBuffer = new StringBuffer();
	        String line;
			Scanner scnr = new Scanner(file);
		      
		    while(scnr.hasNextLine()){
		         line = scnr.nextLine();
		         
		         String[] personaStr = line.split(";");
		         if(personaStr[0].equals(username) && personaStr[1].equals(password)) {
		        	 return true;
		         }
		    } 
		    
		    //return false;	 
		    
		}catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
    }
}
