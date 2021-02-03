package InterfaceServer;
import base.UtilisateurCampus;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import base.DBConnect;
import base.UtilisateurCampus;

public class AjoutGroupe extends JFrame implements ActionListener {
	private JPanel pan = new JPanel();
	/* Déclaration des boutons */
	private JButton bouton_acc = new JButton("Accueil");
	private JButton bouton_aj = new JButton("Ajout Groupe");
	private JButton bouton_suppr = new JButton("Suppression Groupe");

	/*Déclaration des JLabel */
	private JLabel ajoutL = new JLabel("Ajout d'un groupe ");
	private JLabel nomAL = new JLabel("Entrez le nom du groupe :");
	private JLabel supprL = new JLabel("Suppression d'un groupe ");
	private JLabel nomSL = new JLabel("Entrez le nom du groupe :");
	private JLabel erreurVide = new JLabel("Attention : Le nom de groupe n'est pas de la bonne taille (1 à 32 caractères)");
	private JLabel erreurVide2 = new JLabel("Attention : Le nom de groupe n'est pas de la bonne taille (1 à 32 caractères)");
	private JLabel info2 = new JLabel(" Groupe déjà existant");
	/*Déclaration des JTextFiel */
	private JTextField nomAF = new JTextField();
	private JTextField nomSF = new JTextField();
	
	public boolean verifNomGp(String nom) {
		return (nom.length() > 0 && nom.length() < 32);
	}
	
	public AjoutGroupe() {
		/*Création de la JFrame*/		
		this.setTitle("Interface Serveur");
	    this.setSize(700, 500);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);     
		this.setVisible(true);
		
		/* Aucun layout défini*/
		pan.setLayout(null);
		
		/*Ajout des composants au JPanel*/
		pan.add(bouton_acc);
		pan.add(bouton_aj);
		pan.add(bouton_suppr);
		pan.add(ajoutL);
		pan.add(nomAL);
		pan.add(supprL);
		pan.add(nomSL);
		pan.add(nomAF);
		pan.add(nomSF);
		
		/*Ajout de l'écoute d'événements */
		bouton_acc.addActionListener(this);
		bouton_aj.addActionListener(this);
		bouton_suppr.addActionListener(this);
		
		/* Postion des composants*/
		bouton_acc.setBounds(30, 20, 120, 30);
		ajoutL.setBounds(30, 60, 300, 30);
		nomAL.setBounds(30, 100, 300, 20);
		nomAF.setBounds(350, 100, 150, 20);
		bouton_aj.setBounds(240, 150, 200, 30);		
		supprL.setBounds(30, 240, 300, 30);
		nomSL.setBounds(30, 280, 300, 20);
		nomSF.setBounds(350, 280, 150, 20);
		bouton_suppr.setBounds(240, 330, 200, 30);
		erreurVide.setBounds(180, 180, 450, 30);
		erreurVide2.setBounds(180, 370, 450, 30);
		info2.setBounds(230, 180, 400, 30);	
		
		this.setContentPane(pan);
	}
	
	public void actionPerformed(ActionEvent e) {		
		Object source = e.getSource();
		String nom_gpe_ajout;
		String nom_gpe_sup;
		/*Retour Accueil */
		if(source == bouton_acc){
			InterfaceServer iterS = new InterfaceServer();
			this.dispose();
		}
		else {

			/*Ajout d'un groupe*/
			boolean fini = false;
			while(!fini) {
				if(source == bouton_aj){		
					nom_gpe_ajout = nomAF.getText();
					if(!verifNomGp(nom_gpe_ajout)) {
						pan.remove(info2);
						pan.add(erreurVide);
						this.setContentPane(pan);
						break;
					}
					DBConnect con = new DBConnect();		
					String message = con.ajouterGroupe(nom_gpe_ajout);
					/*Si il n'y a pas de groupe du même nom*/
					if(message == "OK") {
						JLabel info = new JLabel(" Groupe "+nom_gpe_ajout+" ajouté !");
						info.setBounds(230, 180, 400, 30);
						pan.remove(erreurVide);
						pan.add(info);		
						
					} else {
												
						pan.remove(erreurVide);
						pan.add(info2);
					}
					this.setContentPane(pan);
					fini = true;
				}
	
			
				/*Suppression d'un groupe */				
				if(source == bouton_suppr) {
					nom_gpe_sup = nomSF.getText();
					if(!verifNomGp(nom_gpe_sup)) {
						pan.remove(supprL);
						pan.add(erreurVide2);
						this.setContentPane(pan);
						break;
					}
					DBConnect con = new DBConnect();				
					String message = con.supprimerGroupe(nom_gpe_sup);	
					/*Si le groupe existe */
					if(message == "OK") {
						JLabel supprimer = new JLabel(" Groupe "+nom_gpe_sup+" supprimé ! ");	
						supprimer.setBounds(230, 370, 400, 30);
						pan.remove(erreurVide2);
						pan.add(supprimer);
					}else {
						JLabel supprimer = new JLabel("Groupe "+nom_gpe_sup+" inexistants");
						supprimer.setBounds(230, 370, 400, 30);
						pan.remove(erreurVide2);
						pan.add(supprimer);
					}				
					this.setContentPane(pan);			
					fini = true;
				}
				
			}
		}
			
		
		
	}
}
