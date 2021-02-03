package InterfaceServer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


	

public class InterfaceServer extends JFrame implements ActionListener{
    //Création du jpannel
	private JPanel pan = new JPanel();    
	 //Création des composants
	private JButton bouton_au = new JButton("Création d'un nouvel utilisateur");
	private JButton bouton_su = new JButton("Suppression d'un utilisateur");
	private JButton bouton_g = new JButton("Ajouter/Supprimer des groupes");
	private JButton bouton_ad = new JButton("Gérer les adhesions");
	private JLabel accueil_lab = new JLabel("Modifications des données :");
	

	public InterfaceServer(){
		
	    this.setTitle("Interface Serveur");
	    this.setSize(700, 500);
	    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);                       //empeche la modification de taille

	  //layout du jpanel
	    pan.setLayout(null);   
	   
	    //Ajout des écoutes
	    bouton_au.addActionListener(this);             
	    bouton_su.addActionListener(this);            
	    bouton_g.addActionListener(this);
	    bouton_ad.addActionListener(this);
	    
	    //Ajout des composants
	    pan.add(accueil_lab);
	    pan.add(bouton_au);
	    pan.add(bouton_su);
	    pan.add(bouton_g);
	    pan.add(bouton_ad);
	    
	    //Placement des composants
	    bouton_au.setBounds(150, 100, 350, 30);
	    bouton_su.setBounds(150, 170, 350, 30);
	    bouton_g.setBounds(150, 240, 350, 30);
	    bouton_ad.setBounds(150, 310, 350, 30);
	    accueil_lab.setBounds(50, 50, 350, 30);
	    this.setContentPane(pan);
	    this.setVisible(true);
	    
	    
	  }    
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		//4 actions possibles en fonction des boutons sélectionnés
		if(source == bouton_au){
			AjoutUtilisateur ajout = new AjoutUtilisateur();
			this.dispose();
		}		
		if(source == bouton_su){
			SupprimerUtilisateur suppr = new SupprimerUtilisateur();
			this.dispose();
		}
		if(source == bouton_g){
			AjoutGroupe ajout = new AjoutGroupe();
			this.dispose();
		}
		if(source == bouton_ad){
			UtilisateurGroupe util = new UtilisateurGroupe();
			this.dispose();
		}
	}
	}

