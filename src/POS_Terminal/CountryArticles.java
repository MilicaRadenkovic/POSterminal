package POS_Terminal;
/*
 * Glavna klasa, u njoj se nalaze opcije odabira drzave, artikli ciji su nazivi smesteni u button-e, mogucnost unosenja kolicine 
 * artikla, postoje i dva dugmeta gde klikom na njih zaposlen dobija ukupnu cenu+pdv i jos jedno gde se resetuje sve na 0
 * i zaposlen moze nov racun da napravi. Klikom na dugme 'show all' prikazuju se tabele sa trenutnim stanjima u bazi 
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CountryArticles{
	
		private JFrame jf=new JFrame("Izbor drzave");
		
		//labele
		private JLabel country;
		private JLabel quantity;
		private JLabel sub;
		private JLabel nan;
		
		//buttoni
		private JButton show;
		private JButton submit;
		private JButton reset;
		private JButton a1;
		private JButton logout;
		
		//racunanje
		private double total_price=0.0;
		private double total_price_pdv=0.0;
		private double pdv;
		private double s=0.0;
		
		//nizovske liste
		private ArrayList<JButton> buttonList = new ArrayList<JButton>();
		private ArrayList<Articles> articles;
		private ArrayList<Country> ctry;
		private ArrayList<Total> t;
		private ArrayList<Articles> a;
		private ArrayList<ContArt> ca;
		
		private JTextField field;
		private JComboBox<String> select;
		private Container cont;
		private Statement n;
		
		public CountryArticles() { //konstruktor
			jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			jf.setUndecorated(true); //uklanja title bar
			jf.setAlwaysOnTop(true); 
			jf.setResizable(false);
			jf.setVisible(true);
			jf.getRootPane().setDefaultButton(submit);
			Toolkit tk=Toolkit.getDefaultToolkit();
				int xsize=(int) tk.getScreenSize().getWidth();
				int ysize=(int) tk.getScreenSize().getHeight();
			jf.setSize(xsize, ysize);
			setComponentsOnForm();
		}
		
		public void setComponentsOnForm() {
			
			//pravljenje objekata koji ce se nalaziti na formi
			country=new JLabel("Choose country:");
			country.setBounds(20,5,200,50);
			select= new JComboBox<String>();
			select.setBounds(150,15,200,30);
			select.grabFocus();
			quantity=new JLabel("Qtty:");
			quantity.setBounds(100,100,70,20);
			field=new JTextField();
			field.setBounds(170,100,100,30);
			submit=new JButton("SUBMIT");
			submit.setBounds(300, 900,100, 30);
			sub=new JLabel();
			sub.setBounds(450,890,100,50);
			nan=new JLabel();
			nan.setBounds(300,100,100,30);
			nan.setForeground(Color.RED);
			reset=new JButton("NEW");
			reset.setBounds(300,950,100,30);
			show=new JButton("SHOW ALL");
			show.setBounds(550,910,150,50);
			logout=new JButton("LOGOUT");
			logout.setFont(new Font("Times New Roman", Font.PLAIN, 20));
			logout.setBounds(1610, 920, 200, 70);
			
			//dodavanje objekata u kontejner
			cont=jf.getContentPane();
			cont.setLayout(null);
			cont.add(country);
			cont.add(select);
			cont.add(quantity);
			cont.add(field);
			cont.add(submit);
			cont.add(sub);
			cont.add(nan);
			cont.add(reset);
			cont.add(show);
			cont.add(logout);
			
		try { //uspostavljanje konekcije sa bazom
			n=Connection_db.getInstance().getStatement();
			String drzava="SELECT * FROM drzava";
			String artikli="SELECT * FROM artikli";
			ResultSet rs=null;
			ResultSet rs1=null;
			articles = new ArrayList<Articles>();
			ctry = new ArrayList<Country>();
			  
			try {
				rs=n.executeQuery(drzava);
			} catch (SQLException sqle) {
				System.out.println("Greska u izvrsenju upita: "+sqle);
			}
			
			while(rs.next()) {
				Country c=new Country(); //pravimo objekte klase Country
				c.setId_country(rs.getInt("id_drzave"));
				c.setName(rs.getString("naziv"));
				c.setPdv(rs.getString("pdv"));
				ctry.add(c); //dodajemo u nizovsku listu
			}
			for(Country cc: ctry) {
				select.addItem(cc.getName()); //dodajemo u JComboBox drzave
			}
			
			n.close();
			n=Connection_db.getInstance().getStatement();
			try {
				rs1=n.executeQuery(artikli);
			} catch (SQLException sqle) {
				System.out.println("Greska u izvrsenju upita: "+sqle);
			}
			while(rs1.next()) {
				Articles a=new Articles(artikli); //pravimo objekte klase Articles
				a.setCode(rs1.getString("sifra"));
				a.setName(rs1.getString("naziv"));
				a.setPrice(rs1.getString("cena"));
				articles.add(a); //dodajemo u nizovsku listu
			}
			
			int i=1;
			int br=0;
			int size=200;
			
			for(Articles a : articles) {
				br++;
				a1=new JButton(a.getName()); //za svaki artikal dinamicki pravimo button
				
				buttonList.add(a1); //svaki button dodajemo u nizovsku listu
				a1.setBounds(30*i, size, 120, 100);
				i+=5;
				if(br>5) {
					i=1;
					br=1;
					a1.setBounds(30*i, size+=120, 120, 100);
					i+=5;
				}
				cont.add(a1); //svaki button dodajemo u kontejner	
			}	
			
			for(JButton b : buttonList) { //za svaki button iz nizovske liste
					
				b.addActionListener(new ActionListener() { //pravimo listener
					
					@Override
					public void actionPerformed(ActionEvent e) {
						//povezivanje sa bazom
						try {
							n.close();
							n=Connection_db.getInstance().getStatement();
							String update="UPDATE artikli SET br_selektovanih=br_selektovanih+"+"'"+field.getText()+"'"
											+ "WHERE naziv="+"'"+b.getText()+"'"+";";
							n.addBatch(update); //returns an array of integers, and each element of the array represents the update count for the respective update statement
							n.executeBatch();
							}catch (SQLException sqle) {
								System.out.println("Greska u izvrsenju upita buttoni: "+sqle);
							}
						
						submit.setEnabled(true);
						b.setEnabled(false); //na koji god button da kliknemo on postaje disabled
						sub.setText("");
						field.setEditable(true);
						field.requestFocus();
						
						for(Country co : ctry) {
							if(select.getSelectedItem().equals(co.getName())) //ukoliko se ime drzave poklapa sa izabranom drzavom
								 pdv=Double.parseDouble(co.getPdv()); //uzimamo iznos pdv-a
						}
						try {
							if(field.getText()!=null && field.getText().length()>0) { //proveravamo da li je unos kolicine broj
								nan.setText("");
								for(Articles aa : articles) {
									if(aa.getName().equals(b.getText())) { //racunamo vrednost ukupne cene i cene sa pdv-om
										total_price+=Double.valueOf(field.getText())*Double.valueOf(aa.getPrice());
										total_price_pdv=(total_price*(100.0+pdv))/100.0; //zaokruzujemo na dve decimale
									}
								}
							}else {
								b.setEnabled(true); //ako nije broj, omogucavamo zaposlenom da pokusa jos jednom
								nan.setText("Not number");
							}
							
						}catch(NumberFormatException number) {
							nan.setText("Not number");
							b.setEnabled(true);
						}
						
						//ubacivanje kolicine artikala sa drzavom koja je selektovana u tabelu drzavaArtikli
						try {
							String insert;
							n.close();
							n=Connection_db.getInstance().getStatement();
							if(field.getText().trim().length() != 0) {
									insert = "INSERT INTO drzavaArtikli(naziv_d,naziv_a,kolicina) "
												+ "VALUES('"+select.getSelectedItem().toString()+"'"+","+"'"+b.getText()+"'"+",'"+field.getText().toString()+"'"+");";
									n.addBatch(insert);
									n.executeBatch();
							}
							n.close();
						}catch (SQLException sqle) {
								System.out.println("Greska u izvrsenju upita ubacivanje artikala: "+sqle);
						}
						field.setText("");	
					}	
				});
			}
			
				field.addKeyListener(new KeyAdapter() { //onemogucavamo da zaposlen unese ista sto nije broj
					
					public void keyTyped(KeyEvent e) {
						char input=e.getKeyChar();//kad zaposlen unese nesto cuva se u varijablu
						if((input < '0' || input>'9') && input!='\b') {
							e.consume();//blokiramo za ulaz
							nan.setText("Not a number");
						}
					}
				});
				submit.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						for(JButton b : buttonList) {
							b.setEnabled(false); //klikom na dugme submit onemogucava se selektovanje artikala dok se ne klikne dugme new							
						}
						
						s=Math.round(total_price_pdv*100.00)/100.00; //zaokruzujemo na dve decimale
						sub.setText(String.valueOf(s));
						nan.setText("");
						field.setText("");
						submit.setEnabled(false);
						
						//unos podataka u bazu
						try {						
							n=Connection_db.getInstance().getStatement();
							String insert="INSERT INTO total "+"VALUES("+total_price+","+s+","+"'"+select.getSelectedItem().toString()+"'"+");";
							n.addBatch(insert);
							n.executeBatch();
						}catch (SQLException sqle) {
							System.out.println("Greska u izvrsenju upita: "+sqle);
						}
					}
				});
				reset.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						for(JButton b: buttonList) { //omogucavamo ponovan unos novih artikala, i resetujemo sve na 0
							b.setEnabled(true);
						}
						field.requestFocus();
						total_price=0;
						total_price_pdv=0;
						sub.setText("");
						submit.setEnabled(true);
					}
				});
					n.close();
		}catch (SQLException sqle) {
			System.out.println("Greska: "+sqle);
		} 
		
		show.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) { //pozivamo metode koje ce prikazati trenutno stanje u bazi
				gridViewTotal();
				gridViewArticles3();
				gridViewArticles0();
				gridViewTop10();
			}
		});
		
		logout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Login("Client");	//pozivamo formu koja nas vraca na ponovno logovanje	
			}
		});
	}	
/*
 * kreiranje metoda koje prikazuju tabele i stanje u bazi	
 */
	public ArrayList<Total> getTotalList() {
		
		//konektovanje sa bazom
		try {
			t=new ArrayList<Total>(); //pravimo nizovsku listu objekata klase Total
			n=Connection_db.getInstance().getStatement();
			String sel="SELECT drzava_naziv naziv, sum(total_price_pdv) ukupno\n" + 
						"FROM terminal.total\n" + 
						"GROUP BY naziv\n" + 
						"ORDER BY sum(total_price_pdv);";
			ResultSet rs=null;
			try {
				rs=n.executeQuery(sel);
			} catch (SQLException sqle) {
				System.out.println("Greska u izvrsenju upita artikli: "+sqle);
			}
			Total tt;
			while(rs.next()) {
				tt=new Total(rs.getString("naziv"),rs.getDouble("ukupno")); //pravimo objekte klase Total
				t.add(tt); //dodajemo ih u nizovsku listu
			}
			n.close();
			}catch (SQLException sqle) {
				System.out.println("Greska u izvrsenju upita artikli: "+sqle);
			}
		return t; //vracamo listu Objekata klase Total
	}

	public void gridViewTotal() {
		ArrayList<Total> list=getTotalList();
		String[] cols = {"Name","Total(bill+pdv)"};
		String[][] data = new String[][] {};
		JTable table = new JTable(new DefaultTableModel(data, cols)); //pravimo tabelu koja ima dve kolone
		DefaultTableModel model=(DefaultTableModel)table.getModel(); //This method returns information on the current data model during the processing of a personalized reverse engineering
		
		JPanel c=new JPanel(); //pravimo panel kome dodajemo tabelu
		c.setBounds(1000,100,500,100);
		cont.add(c);
		c.add(table);
		c.setLayout(new BorderLayout());
		c.add(table.getTableHeader(), BorderLayout.PAGE_START);
		c.add(table, BorderLayout.CENTER);

		Object[] row=new Object[2];
		for(int i=0;i<list.size();i++) {
			row[0]=list.get(i).getName();
			row[1]=list.get(i).getTotal_pdv();
			model.addRow(row); //u model dodajemo redove 
		}	
	}
	
	public ArrayList<Articles> getArticlesList() {
		
		//konektovanje sa bazom
		try {
			a=new ArrayList<Articles>();
			n=Connection_db.getInstance().getStatement();
			String sel="SELECT naziv FROM terminal.artikli\n" + 
						"WHERE br_selektovanih>3;";
			ResultSet rs=null;
			try {
				rs=n.executeQuery(sel);
			} catch (SQLException sqle) {
				System.out.println("Greska u izvrsenju upita >3: "+sqle);
			}
			Articles aa;
			while(rs.next()) {
				aa=new Articles(rs.getString("naziv"));
				a.add(aa);
			}
			n.close();
			}catch (SQLException sqle) {
				System.out.println("Greska u izvrsenju upita >3: "+sqle);
			}
		return a;
	}

	public void gridViewArticles3() {
		ArrayList<Articles> list=getArticlesList();
		String[] cols = {"Articles>3 sales"};
		String[][] data = new String[][] {};
		JTable table = new JTable(new DefaultTableModel(data, cols));
		DefaultTableModel model=(DefaultTableModel)table.getModel();
		
		JPanel c=new JPanel();
		c.setBounds(1000,300,500,100);
		cont.add(c);
		c.add(table);
		c.setLayout(new BorderLayout());
		c.add(table.getTableHeader(), BorderLayout.PAGE_START);
		c.add(table, BorderLayout.CENTER);

		Object[] row=new Object[1];
		for(int i=0;i<list.size();i++) {
			row[0]=list.get(i).getName();
			model.addRow(row);
		}
	}
	
	public ArrayList<Articles> getArticlesList0() {
			
		//konektovanje sa bazom
			try {
				a=new ArrayList<Articles>();
				n=Connection_db.getInstance().getStatement();
				String sel="SELECT naziv FROM terminal.artikli\n" + 
							"WHERE br_selektovanih=0;";
				ResultSet rs=null;
				try {
					rs=n.executeQuery(sel);
				} catch (SQLException sqle) {
					System.out.println("Greska u izvrsenju upita neprodavani: "+sqle);
				}
				Articles aa;
				while(rs.next()) {
					aa=new Articles(rs.getString("naziv"));
					a.add(aa);
				}
				n.close();
				}catch (SQLException sqle) {
					System.out.println("Greska u izvrsenju upita neprodavani: "+sqle);
				}
			return a;
		}

		public void gridViewArticles0() {
			ArrayList<Articles> list=getArticlesList0();
			String[] cols = {"Articles not saled"};
			String[][] data = new String[][] {};
			JTable table = new JTable(new DefaultTableModel(data, cols));
			DefaultTableModel model=(DefaultTableModel)table.getModel();
			
			JPanel c=new JPanel();
			c.setBounds(1000,500,500,100);
			cont.add(c);
			c.add(table);
			c.setLayout(new BorderLayout());
			c.add(table.getTableHeader(), BorderLayout.PAGE_START);
			c.add(table, BorderLayout.CENTER);
			c.add(new JScrollPane(table));			

			Object[] row=new Object[1];
			for(int i=0;i<list.size();i++) {
				row[0]=list.get(i).getName();
				model.addRow(row);
			}
		}
		
		public ArrayList<ContArt> getArticlesList10() {
			
			//konektovanje sa bazom
			try {
				ca=new ArrayList<ContArt>();
				n=Connection_db.getInstance().getStatement();
				String sel="SELECT  terminal.drzavaArtikli.naziv_d, terminal.drzavaArtikli.naziv_a\n" + 
							"FROM terminal.drzavaArtikli\n" + 
							"GROUP by terminal.drzavaArtikli.naziv_d,terminal.drzavaArtikli.naziv_a\n" + 
							"HAVING sum(terminal.drzavaArtikli.kolicina) AND count(terminal.drzavaArtikli.naziv_d)<10\n" + 
							"order by 1,sum(terminal.drzavaArtikli.kolicina) desc;\n" + 
							"";
				ResultSet rs=null;
				try {
					rs=n.executeQuery(sel);
				} catch (SQLException sqle) {
					System.out.println("Greska u izvrsenju upita top10: "+sqle);
				}
				ContArt caa;
				while(rs.next()) {
					caa=new ContArt(rs.getString("naziv_d"),rs.getString("naziv_a"));
					ca.add(caa);
				}
				n.close();
				
				}catch (SQLException sqle) {
					System.out.println("Greska u izvrsenju upita top10: "+sqle);
				}
			return ca;
		}

		public void gridViewTop10() {
			ArrayList<ContArt> list=getArticlesList10();
			String[] cols = {"Country","Top 10 articles"};
			String[][] data = new String[][] {};
			JTable table = new JTable(new DefaultTableModel(data, cols));
			DefaultTableModel model=(DefaultTableModel)table.getModel();
			
			JPanel c=new JPanel();
			c.setBounds(1000,700,500,300);
			cont.add(c);
			c.add(table);
			c.setLayout(new BorderLayout());
			c.add(table.getTableHeader(), BorderLayout.PAGE_START);
			c.add(table, BorderLayout.CENTER);
			c.add(new JScrollPane(table));			

			Object[] row=new Object[2];
			for(int i=0;i<list.size();i++) {
				row[0]=list.get(i).getName_country();
				row[1]=list.get(i).getName_articles();
				model.addRow(row);
			}
		}
}
