package POS_Terminal;

public class Articles {
	private String code;
	private String name;
	private String price;

	public Articles(String name) { //konstruktor
		super();
		this.name = name;
	}

	public String getCode() { //geteri
		return code;
	}

	public void setCode(String code) { //seteri
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
		public String toString() { //metoda toString
			return this.getCode()+" "+this.getName()+ " "+this.getPrice();
		}

}
