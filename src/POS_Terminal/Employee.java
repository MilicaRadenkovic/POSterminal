package POS_Terminal;
/*
 * U ovoj klasi postavljam inpute gde zaposlen mora da unese svoj jedinstveni PIN, ime i prezime koji se vec nalaze na bazi
 * Ukoliko se podaci poklapaju sa podacima iz baze zaposlen moze otvoriti novu formu
 */
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.*;

public class Employee {
		private JFrame jf=new JFrame("NameUsername"); 
		private Statement n=null;
		//labele
		private JLabel name;
		private JLabel surname;
		private JLabel pin;
		private JButton login;
		private JLabel errorpin;
		private JLabel errorname;
		private JLabel errorsurname;
		private JLabel errorLogin;
		
		//inputi
		private JTextField tname;
		private JTextField tsurname;
		private JPasswordField tpin;
		
		public Employee() { //konstruktor
			
			jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			jf.setUndecorated(true); //uklanja title bar
			jf.setAlwaysOnTop(true); 
			jf.setResizable(false);
			jf.setVisible(true);
			Toolkit tk=Toolkit.getDefaultToolkit();
				int xsize=(int) tk.getScreenSize().getWidth();
				int ysize=(int) tk.getScreenSize().getHeight();
			jf.setSize(xsize, ysize);
			setComponentsOnForm();
		}
		
		public void setComponentsOnForm() {
			
			//labele
			name=new JLabel("Name: ");
			surname=new JLabel("Surname: ");
			pin=new JLabel("Pin: ");
			login=new JButton("Login");
			errorpin=new JLabel();
			errorname=new JLabel();
			errorsurname=new JLabel();
			errorLogin=new JLabel();
			
			pin.setBounds(600,400,200,20);
			name.setBounds(600,500,200,20);
			surname.setBounds(600,600,200,20);
			errorpin.setBounds(950, 400, 200, 20);
			errorpin.setForeground(Color.RED);
			errorname.setBounds(950, 500, 200, 20);
			errorname.setForeground(Color.RED);
			errorsurname.setBounds(950, 600, 200, 20);
			errorsurname.setForeground(Color.RED);
			errorLogin.setBounds(1200, 800, 200, 20);
			errorLogin.setForeground(Color.RED);
			
			//inputi
			tname=new JTextField();
			tsurname=new JTextField();
			tpin=new JPasswordField();
			
			tpin.setBounds(700,400,200,40);
			tname.setBounds(700,500,200,40);
			tsurname.setBounds(700,600,200,40);
			
			//button
			login.setBounds(1200, 700, 200, 50);
			login.setFont(new Font("Times New Roman", Font.PLAIN, 40));
			
			//kontejner
			Container cont=jf.getContentPane();
			cont.setLayout(null);
			cont.add(pin);
			cont.add(tpin);
			cont.add(name);
			cont.add(tname);
			cont.add(surname);
			cont.add(tsurname);
			cont.add(login);
			cont.add(errorpin);
			cont.add(errorname);
			cont.add(errorsurname);
			cont.add(errorLogin);
			jf.getRootPane().setDefaultButton(login);//Return this component's single JRootPane child
			
			login.addActionListener(new ActionListener() 
			{

				@Override
				public void actionPerformed(ActionEvent e) {
						
						//Uspostavljanje konekcije sa bazom
					try {
						n=Connection_db.getInstance().getStatement();
						String upit="SELECT * FROM zaposleni";
						
						ArrayList<EmployeeInfo> employees = new ArrayList<EmployeeInfo>(); //pravimo listu objekata EmployeeInfo
					    ResultSet rs=null;
						try {
							rs=n.executeQuery(upit);
						} catch (SQLException sqle) {
							System.out.println("Greska u izvrsenju upita: "+sqle);
						}
						while(rs.next()) {
							EmployeeInfo employee=new EmployeeInfo(); //za svaki red pravimo nov objekat
							employee.setPin(rs.getString("pinKod"));
							employee.setName(rs.getString("ime"));
							employee.setSurname(rs.getString("prezime"));
							employees.add(employee); //objekte smestamo u nizovsku listu
							
						}
						for(EmployeeInfo i : employees) {
							errorLogin.setText("Field again");
							if(i.getPin().equals(String.valueOf(tpin.getPassword())) && //ukoliko se podaci poklapaju otvara se nova forma
									i.getName().equals(tname.getText()) &&
									i.getSurname().equals(tsurname.getText())) {
											jf.setVisible(false);
											jf.dispose();
											 new CountryArticles();
							}					
							else {
								//ukoliko je neko polje prazno stavljamo naznaku da sva polja moraju biti popunjena
								 if(String.valueOf(tpin.getPassword()).equals("")) errorpin.setText("Required");
								 
								 	else if(!String.valueOf(tpin.getPassword()).equals("")) errorpin.setText("");
									
								 if(tname.getText().equals("")) errorname.setText("Required");
									 
								 	else if(!tname.getText().equals("")) errorname.setText("");
									 
								 if(tsurname.getText().equals("")) errorsurname.setText("Required");
									 else if(!tsurname.getText().equals("")) errorsurname.setText("");
									 
								 if(!tname.getText().equals("") && !String.valueOf(tpin.getPassword()).equals("") && !tsurname.getText().equals("")) {
										 tpin.requestFocus();
										 tname.setText("");
										 tpin.setText("");
										 tsurname.setText("");							
								 }
							}
							
						}	
					}catch (SQLException sqle) {
						System.out.println("Greska: "+sqle);
					} 
					
					finally {
						try {
							if(n!=null) //zatvaramo konekciju
							n.close();
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
				}
			});
		}	
}
