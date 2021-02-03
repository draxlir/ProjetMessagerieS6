package interfaceUtilisateur;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import InterfaceServer.AjoutGroupe;
import InterfaceServer.AjoutUtilisateur;
import InterfaceServer.SupprimerUtilisateur;
import InterfaceServer.UtilisateurGroupe;
import base.DBConnect;

public class Connexion extends JFrame implements ActionListener{
	DBConnect connect = new DBConnect();
	private JPanel pan = new JPanel();                                          //Création du jpannel
	private JButton bouton_c = new JButton("Connexion");						//Création des boutons
	
	
	private JLabel idL = new JLabel("Identifiant  : ");
	private JLabel mdpL = new JLabel("Mot de passe  : ");
	private JTextField idF = new JTextField();
	private JPasswordField mdpF = new JPasswordField();
	

	public Connexion(){
	    this.setTitle("Portail Connexion");
	    this.setSize(700, 500);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);                       //empeche la modification de taille

	    //Ajout du bouton à notre content pane
	    pan.setLayout(null);   //layout du jpanel
	   
	    bouton_c.addActionListener(this);             //créer une écoute sur le bouton
	   
	    pan.add(bouton_c);
	    pan.add(idL);
	    pan.add(mdpL);
	    pan.add(idF);
	    pan.add(mdpF);
	  
	    bouton_c.setBounds(250, 350, 200, 30);
	    idL.setBounds(170, 170, 200, 30);
	    mdpL.setBounds(150, 240, 200, 30);
	    idF.setBounds(350, 175, 150, 20 );
	    mdpF.setBounds(350, 245, 150, 20);
	    this.setContentPane(pan);
	    this.setVisible(true);
	    
	    
	  }    
	
	public void actionPerformed(ActionEvent e) {		
		Object source = e.getSource();
		 
		if(source == bouton_c){
			String id = idF.getText();
			String mdp = mdpF.getText();
			if(connect.connexion(id, mdp)) {           //la fonction connexion retourne un booleen, true si la connexion est possible
				InterfaceUtilisateur iu = new InterfaceUtilisateur(id);
				this.dispose();
			}
			else {
				JLabel erreur = new JLabel("identifiant ou mot de passe invalides");
				erreur.setBounds(230, 390, 400, 30);
				pan.add(erreur);
			}
			this.setContentPane(pan);			
		} 
	
	}
	}
