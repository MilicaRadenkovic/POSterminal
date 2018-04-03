package POS_Terminal;

public class Total {
	
	private double total;
	private double total_pdv;
	private String name;
	public double getTotal() {
		return total;
	}
	
	public Total(String name,double total_pdv) { //konstruktor
		super();
		this.total_pdv = total_pdv;
		this.name = name;
	}

	public void setTotal(double total) { //seteri
		this.total = total;
	}
	public double getTotal_pdv() { //geteri
		return total_pdv;
	}
	public void setTotal_pdv(double total_pdv) {
		this.total_pdv = total_pdv;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() { //metoda toString
		return "Total [total=" + total + ", total_pdv=" + total_pdv + ", name=" + name + "]";
	}
	
	
}
