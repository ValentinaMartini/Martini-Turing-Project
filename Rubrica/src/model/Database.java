package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Database {
	
	private ArrayList<Persona> persone;
	private File file;
	static String filePath = "src\\contatti\\informazioni2.txt";
	private long time = System.currentTimeMillis();
	
	public Database() {
		persone = new ArrayList<Persona>();
	}
	
	public void addPersonaOnTable(Persona persona) {
		persone.add(persona);	
		Collections.sort(persone,AgeComparator);
		System.out.println("Aggiunto: " + persona.getName() + persona.getSurname() + persona.getAddress()+ persona.getTelephone()+ persona.getAge() );
		
	}
	
	public int addPersonaOnFile(Persona persona) {
		try {
			//file = new File(filPath);
			Boolean value = checkUniqueNumber(persona.getTelephone());
			if(value == false) {
				return -1;
			}
			String str = persona.getName()+";"+persona.getSurname()+";"+persona.getAddress()+";"+persona.getTelephone()+";"+persona.getAge()+"\n";
			PrintStream stream = new PrintStream(new FileOutputStream(filePath, true)); 
			stream.append(str);
			
			addPersonaOnTable(persona);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	    
		
	}
	
	public List<Persona> getPersone() {
		return persone;
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
	
	public int modifyPersonaOnFile(Persona persona, String oldTelephone) {
		try {
			
			if(!oldTelephone.equals(persona.getTelephone())) {
				Boolean value = checkUniqueNumber(persona.getTelephone());
				if(value == false) {
					return -1;
				}
				
			}
			
			file = new File(filePath);
			
			BufferedReader file = new BufferedReader(new FileReader(filePath));
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
		    FileOutputStream fileOut = new FileOutputStream(filePath);
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
		
		Collections.sort(persone,AgeComparator);
		
	}
	
	
	public ArrayList<Persona> getPersone2() throws FileNotFoundException {
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
	    Collections.sort(persone,AgeComparator);
	    return persone;       
	}
	
	
	public void deletePersonaFromFile(String telephone) {
		System.out.println("deletePersonaFromFile -> Persone: " + persone.toString() + time);
		try {
			file = new File(filePath);
			StringBuffer inputBuffer = new StringBuffer();
	        String line;
			Scanner scnr = new Scanner(file);
		      
		    while(scnr.hasNextLine()){
		         line = scnr.nextLine();
		         
		         String[] personaStr = line.split(";");
		         if(!personaStr[3].equals(telephone)) {
		        	 inputBuffer.append(line);
			         inputBuffer.append('\n');
		         }
		    } 
		   
		    
		    PrintStream stream = new PrintStream(file);
		    stream.print(inputBuffer.toString());
		    
		    deletePersonaFromTable(telephone);
		    
		}catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void deletePersonaFromTable(String telephone) {
		//System.out.println("TELEFONO DELLA PERSONA DA ELIMINARE: " + telephone);
		
		int index = getPersonaIndex(telephone);
		if(index != -1) {
			persone.remove(index);
		}
		
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
	
	public void createFile() {
		try {
		      file = new File(filePath);
		      if (file.createNewFile()) {
		        System.out.println("File creato: " + file.getName());
		      } else {
		        System.out.println("File gi� esistente.");
		      }
		      
		      persone = this.getPersone2();
		      //deletePersona();
		      
		      
		 } catch (IOException e) {
		      System.out.println("Errore.");
		      e.printStackTrace();
		 }
		System.out.println("createFile-> Persone: " + persone.toString() + time);
	}
	
	public static Comparator<Persona> AgeComparator = new Comparator<Persona>() {

        @Override
        public int compare(Persona e1, Persona e2) {
        	int value = e1.getName().compareTo(e2.getName());
            if (value != 0) {
            	return (value);
            }
            return (int) (e1.getSurname().compareTo(e2.getSurname()));
        }
    };
}
