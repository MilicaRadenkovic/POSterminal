package POS_Terminal;

public class ContArt {
	
	private String name_country;
	private String name_articles;
	
	
	public ContArt(String name_country, String name_articles) { //konstruktor
		super();
		this.name_country = name_country;
		this.name_articles = name_articles;
	}

	public String getName_country() { //geteri
		return name_country;
	}

	public void setName_country(String name_country) { //seteri
		this.name_country = name_country;
	}

	public String getName_articles() {
		return name_articles;
	}

	public void setName_articles(String name_articles) { 
		this.name_articles = name_articles;
	}

	@Override
	public String toString() { //metoda toString
		return "ContArt [name_country=" + name_country + ", name_articles=" + name_articles + "]";
	}
	
}
