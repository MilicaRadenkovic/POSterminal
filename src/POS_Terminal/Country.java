package POS_Terminal;

public class Country {
	
	private int id_country;
	private String name;
	private String pdv;
	
	public int getId_country() { //geteri
		return id_country;
	}
	public void setId_country(int id_country) { //seteri
		this.id_country = id_country;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPdv() {
		return pdv;
	}
	public void setPdv(String pdv) {
		this.pdv = pdv;
	}
	
	@Override
	public String toString() { //metoda toString
		return "Country [id_country=" + id_country + ", name=" + name + ", pdv=" + pdv + "]";
	}
	
	
	
	
}
