package POS_Terminal;

public class EmployeeInfo {
		
	private String pin;
	private String name;
	private String surname;
	
	public String getPin() { //geteri
	    return pin;
	}
	public void setPin(String pin) { //seteri
	    this.pin = pin;
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
	@Override
		public String toString() { //metoda toString
			return this.getPin()+" "+this.getName()+ " "+this.getSurname();
	}
}

