package InterfaceServer;
import base.UtilisateurCampus;
import base.DBConnect;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class AjoutUtilisateur extends JFrame implements ActionListener{
	//Déclaration des composants
	private JPanel pan = new JPanel();
	private JButton bouton_acc = new JButton("Accueil");
	private JButton bouton_crea = new JButton("Création");
	private JLabel crea_lab = new JLabel(" Création d'un nouvel utilisateur :");
	private JLabel nomL = new JLabel(" Nom :");
	private JLabel prenomL = new JLabel(" Prénom :");
	private JLabel identifiantL = new JLabel(" Identifiant :");
	private JLabel mdpL = new JLabel(" Mot De Passe :");
	private JLabel administratif = new JLabel(" Agent administratif  :");
	private JLabel ajouter = new JLabel(" Utilisateur ajouté ! Retournez à l'accueil");
	private JLabel erreurVide = new JLabel(" Attention : Un des champs est vide ou incomplet (2 à 64 caractères)");
	private JLabel erreurMDP = new JLabel(" Attention : le mot de passe n'est pas de la bonne taille (8 à 64 caractères)");	
	private JTextField nomF = new JTextField();
	private JTextField prenomF = new JTextField();
	private JTextField identifiantF = new JTextField();
	private JPasswordField mdpF  = new JPasswordField();	
	JToggleButton admin = new JToggleButton("OUI");

	//Group de radio buttons.
	ButtonGroup groupe = new ButtonGroup();

	public AjoutUtilisateur() {
		
		this.setTitle("Interface Serveur");
	    this.setSize(700, 500);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);     
		this.setVisible(true);		
		groupe.add(admin);
		
		//layout du jpanel	
		pan.setLayout(null); 	   
		     
		//Ajout des composants au Panel
		pan.add(bouton_acc);
		pan.add(bouton_crea);	
		pan.add(crea_lab);		
		pan.add(nomL);
		pan.add(prenomL);
		pan.add(identifiantL);
		pan.add(mdpL);		
		pan.add(administratif);		
		pan.add(nomF);
		pan.add(prenomF);
		pan.add(identifiantF);
		pan.add(mdpF);
		pan.add(admin);
		
		//Ajout des action listener
		bouton_acc.addActionListener(this);
		bouton_crea.addActionListener(this);
		
		//Placement des composants
		bouton_acc.setBounds(30, 20, 120, 30);
		bouton_crea.setBounds(200, 370, 300, 30);		
		crea_lab.setBounds(30, 60, 300, 30);
		bouton_crea.setBounds(200, 370, 300, 30);
		nomL.setBounds(240, 100 , 150, 20);
		prenomL.setBounds(225, 150 , 150, 20);
		identifiantL.setBounds(215, 200 , 150, 20);
		mdpL.setBounds(195, 250 , 150, 20);
		administratif.setBounds(100, 300 , 250, 20);
		nomF.setBounds(350, 100, 100, 20);
		prenomF.setBounds(350, 150, 100, 20);
		identifiantF.setBounds(350, 200, 100, 20);
		mdpF.setBounds(350, 250, 100, 20);		
		admin.setBounds(350, 300, 60, 20);
				
		this.setContentPane(pan);
	}
	
	//Nom entre 2 et 64 caractères
	public boolean verifNom(String nom) {
		return (nom.length() >= 2) && (nom.length() < 64);
	}
	//Mot de passe entre 8 et 64 caractères
	public boolean verifMPD(String MDP) {
		return (MDP.length() > 8 && MDP.length() < 64);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		String nom; 
		String prenom;
		String identifiant;
		String mdp;
		boolean agentAdministratif = false; 
		//Si le bouton accueil est actionné
		if(source == bouton_acc){
			InterfaceServer iterS = new InterfaceServer();
			this.dispose();
		}
		else {			
			boolean fini = false;
			while(!fini) { 
				if(source == bouton_crea){
					nom = nomF.getText(); 
					prenom = prenomF.getText();
					identifiant = identifiantF.getText();
					mdp = mdpF.getText();					
					if(!verifNom(nom) || !verifNom(prenom) || !verifNom(identifiant)) {
						pan.remove(erreurMDP);
						erreurVide.setBounds(180, 400, 400, 35);
						pan.add(erreurVide);
						this.setContentPane(pan);
						break;
					}
					if(!verifMPD(mdp)) {
						pan.remove(erreurVide);
						erreurMDP.setBounds(170, 400, 425, 35);
						pan.add(erreurMDP);
						this.setContentPane(pan);
						break;
					}
					pan.remove(erreurMDP);
					pan.remove(erreurVide);
					System.out.println("Le nom est : "+nom);
					System.out.println(agentAdministratif);
					
					DBConnect con = new DBConnect();
					UtilisateurCampus user = new UtilisateurCampus(nom, prenom, identifiant, mdp, agentAdministratif);
					
					String m = con.ajouterUtilisateur(user);
					if( m == "OK") {
						ajouter.setBounds(230, 400, 400, 30);	//
						pan.add(ajouter);		// Affiche un message disant que l'utilisateur a bien été ajouté
					}else {
						JLabel supprimer = new JLabel("Identifiant déjà utilisé");
						supprimer.setBounds(230, 400, 400, 30);
						pan.add(supprimer);
					}
					this.setContentPane(pan);				
					fini = true;					
				}				
			}					
		}
	}
}




