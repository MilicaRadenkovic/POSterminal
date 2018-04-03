package POS_Terminal;
/*
* 	U ovoj klasi pozivam klasu Login koja ima jedno dugme gde zaposlen moze pristupiti prijavljivanju na terminal
*/
public class Main {
	public Main(String name) {
	}

	public static void main(String[] args) {
		Login login=new Login("Client"); //pravimo objekat klase Login
		login.openWindow(); //pozivamo metodu koja otvara prozor za Login
	}
	
}
