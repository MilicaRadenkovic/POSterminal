package POS_Terminal;

/*
 *	 U ovoj klasi pravim dugme Login gde zaposlen klikom na to dugme dobija Login formu
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends Main {
	private JButton login; //prijavljivanje zaposlenog na terminal
	private JFrame jf=new JFrame();
	
	public Login(String name) {
		super(name); //konstruktor definisan u nadklasi Main
		setComponentsOnForm(); //pozivamo metodu koja postavlja komponente na formu jf
		jf.setUndecorated(true); //uklanja title bar
		jf.setAlwaysOnTop(true); 
		jf.setResizable(false);
		jf.setVisible(true);

		Toolkit tk=Toolkit.getDefaultToolkit();
			int xsize=(int) tk.getScreenSize().getWidth();
			int ysize=(int) tk.getScreenSize().getHeight();
		jf.setSize(xsize, ysize); //postavljamo max velicinu prozora
		jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); //frame se ne zatvara,samo se hide-uje
	}
	
	public void setComponentsOnForm() {
		login=new JButton("Login");
		login.setFont(new Font("Times New Roman", Font.PLAIN, 40));
		login.setBounds(750,500,300, 70);
			
		Container cont=jf.getContentPane();//retrieves the content pane layer so that we can add an object to it.
		cont.setLayout(null); // determines the size and position of the components within a container
		cont.add(login);
		jf.getRootPane().setDefaultButton(login); //postavljamo da bi sa tasterom 'enter' uneli podatke	
		login.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				 	jf.setVisible(false); //frame vise nije vidljiv
				 	jf.dispose(); //causes the JFrame window to be destroyed and cleaned up by the operating system
			        new Employee(); //pozivamo objekat koji otvara novi jframe
				
			}
		});
	}
	
	public void openWindow() {
		jf.setVisible(true);
	}
	
}
