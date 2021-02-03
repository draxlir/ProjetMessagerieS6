package InterfaceServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import base.DBConnect;

public class UtilisateurGroupe extends JFrame implements ActionListener {
	private JPanel pan = new JPanel();
	//Déclaration des boutons 
	private JButton bouton_acc = new JButton("Accueil");
	private JButton bouton_aj = new JButton("Ajouter l'utilisateur au groupe");
	private JButton bouton_suppr = new JButton("Supprimer l'utilisateur du groupe");
	
	//Déclaration des JLabel 
	private JLabel ajoutL = new JLabel("Ajout d'un utilisateur à un groupe ");
	private JLabel nomAL = new JLabel("Entrez le nom du groupe :");
	private JLabel idAL = new JLabel("Entrez l'identifiant de l'utilisateur :");
	private JLabel supprL = new JLabel("Suppression d'un utilisateur à un groupe");
	private JLabel nomSL = new JLabel("Entrez le nom du groupe :");
	private JLabel idSL = new JLabel("Entrez l'identifiant de l'utilisateur :");
	
	//Déclaration des JTextFiel 
	private JTextField nomAF = new JTextField();
	private JTextField idAF = new JTextField();
	private JTextField nomSF = new JTextField();
	private JTextField idSF = new JTextField();
	
	public UtilisateurGroupe() {
		//Création de la JFrame	
		this.setTitle("Interface Serveur");
	    this.setSize(700, 500);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);     
		this.setVisible(true);
		
		// Aucun layout défini
		pan.setLayout(null);
		
		//Ajout des composants au JPanel
		pan.add(bouton_acc);
		pan.add(bouton_aj);
		pan.add(bouton_suppr);
		pan.add(ajoutL);
		pan.add(nomAL);
		pan.add(idAL);
		pan.add(supprL);
		pan.add(nomSL);
		pan.add(idSL);
		pan.add(nomAF);
		pan.add(idAF);
		pan.add(nomSF);
		pan.add(idSF);
		
		//Ajout de l'écoute d'événements 
		bouton_acc.addActionListener(this);
		bouton_aj.addActionListener(this);
		bouton_suppr.addActionListener(this);
		
		// Postion des composants
		bouton_acc.setBounds(30, 20, 120, 30);
		bouton_aj.setBounds(210, 200, 300, 30);		
		ajoutL.setBounds(30, 60, 300, 30);
		nomAL.setBounds(70, 100, 300, 20);
		nomAF.setBounds(350, 100, 150, 20);
		idAL.setBounds(70, 150, 300, 20);
		idAF.setBounds(350, 150, 150, 20);
		supprL.setBounds(30, 240, 300, 30);
		nomSL.setBounds(70, 280, 300, 20);
		nomSF.setBounds(350, 280, 150, 20);
		idSL.setBounds(70, 330, 300, 20);
		idSF.setBounds(350, 330, 150, 20);
		bouton_suppr.setBounds(210, 380, 300, 30);
		this.setContentPane(pan);
	}
	

	public void actionPerformed(ActionEvent e) {		
		Object source = e.getSource();
		String nom = nomAF.getText();
		String nomS = nomSF.getText();
		String idA = idAF.getText();
		String idS = idSF.getText();
		if(source == bouton_aj) {	
			DBConnect con = new DBConnect();				
			String m = con.ajouterUtilisateurGroupe(idA,nom);
			if(m == "OK") {
					JLabel ajout = new JLabel("Utilisateur ajouté ! ");
					ajout.setBounds(240, 170, 400, 30);
					pan.add(ajout);				            //Message indiquant que l'utisateur est ajouté au groupe
			}else {
					JLabel supprimer = new JLabel(" Utilisateur ou Groupe incorrect ");	
					supprimer.setBounds(240, 170, 400, 30);
					pan.add(supprimer);
			}
			this.setContentPane(pan);
		
	    }
		if(source == bouton_suppr) {	
			DBConnect con = new DBConnect();				
			String message = con.supprimerGroupe(nom);	
			//Si le groupe existe 
			if(message == "OK") {
				JLabel supprimer = new JLabel(" Utilisateur supprimé du groupe ");	
				supprimer.setBounds(230, 410, 400, 30);
				pan.add(supprimer);
			}else {
				JLabel supprimer = new JLabel("Utilisateur pas dans le groupe");
				supprimer.setBounds(230, 410, 400, 30);
				pan.add(supprimer);
			}				
			this.setContentPane(pan);		
		}
		if(source == bouton_acc){
			InterfaceServer iterS = new InterfaceServer();
			this.dispose();
		}
	  
	}

}

