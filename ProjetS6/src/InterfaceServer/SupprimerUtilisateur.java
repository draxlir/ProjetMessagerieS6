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

public class SupprimerUtilisateur extends JFrame implements ActionListener {
	//Ajout des composants
	private JPanel pan = new JPanel();
	private JButton bouton_acc = new JButton("Accueil");
	private JButton bouton_suppr = new JButton("Suppression");
	private JLabel supprL = new JLabel("Suppression d'un utilisateur ? :");
	private JLabel id_supprL = new JLabel("Entrez l'identfiant de l'utilisateur à supprimer :");
	private JTextField idF = new JTextField();
	private JLabel supprimer = new JLabel(" Utilisateur supprimé ! Retournez à l'accueil");
	private JLabel erreurVide = new JLabel(" Attention : le nom de l'utilisateur est incorrect !");
	
	
	public SupprimerUtilisateur() {		
		this.setTitle("Interface Serveur");
	    this.setSize(700, 500);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);     
		this.setVisible(true);		
		pan.setLayout(null);
		
		//Ajout des boutons
		pan.add(bouton_acc);
		pan.add(bouton_suppr);
		pan.add(supprL);
		pan.add(id_supprL);
		pan.add(idF);
		
		//Ajout des écoutes
		bouton_acc.addActionListener(this);
		bouton_suppr.addActionListener(this);
		
		//Placement des composants
		bouton_acc.setBounds(30, 20, 120, 30);
		bouton_suppr.setBounds(200, 370, 300, 30);
		supprL.setBounds(30, 60, 300, 30);
		id_supprL.setBounds(100, 200 , 300, 20);
		idF.setBounds(400, 200, 100, 20);
		erreurVide.setBounds(230, 400, 400, 30);
		this.setContentPane(pan);
	}
	
	public boolean verifNom(String nom) {
		return (nom.length() >= 2) && (nom.length() < 64);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		String id;
		if(source == bouton_acc){
			InterfaceServer iterS = new InterfaceServer();
			this.dispose();
		}
		else {			
			boolean fini = false;
			while(!fini) {
				if(source == bouton_suppr){
					id = idF.getText();
					if(!verifNom(id)) {
						pan.remove(supprimer);
						pan.add(erreurVide);
						this.setContentPane(pan);
						break;
					}
					DBConnect con = new DBConnect();					
					UtilisateurCampus user = new UtilisateurCampus(".", ".", id, ".", false);  //Création d'un objet déstructuré 
					con.supprimerUtilisateur(user);
					pan.remove(erreurVide);
					supprimer.setBounds(230, 400, 400, 30);					
					pan.add(supprimer);									//Message indiquant que l'utilisateur à été supprimé
					this.setContentPane(pan);
					fini = true;
				
				}
			}
		}		
	}
}
