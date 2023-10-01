package model;



public class Persona {
		private String name;
		private String surname;
		private String address;
		private String telephone;
		private int age;
		
		public Persona(String name, String surname,String address,String telephone, int age) {
			this.name = name;
			this.surname = surname;
			this.address = address;
			this.telephone = telephone;
			this.age = age;
			
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSurname() {
			return surname;
		}
		public void setSurname(String surname) {
			this.surname = surname;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getTelephone() {
			return telephone;
		}
		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		

		

}
