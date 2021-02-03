package interfaceUtilisateur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import base.DBConnect;

public class CreationFil extends JFrame implements ActionListener{
	//Creation des composants
	private JPanel pan = new JPanel();
	private JLabel info = new JLabel(" Nom du groupe receveur :");
	private JLabel titre = new JLabel(" Titre du du fil de discussion :");
	private JLabel message = new JLabel(" Premier message :");
	private JLabel erreur = new JLabel(" Ce fil existe déjà");
	private JLabel succes = new JLabel(" Le fil est crée");
	private JTextField nomF = new JTextField();
	private JTextField titreF = new JTextField();
	private JTextArea messageF = new JTextArea();
	private String identifiant;
	private JButton creation = new JButton("Création");
	private JButton retour = new JButton("Retour");
	
	public CreationFil(String identifiant) {
		this.identifiant = identifiant;
		this.setTitle("Interface Serveur");
	    this.setSize(500, 300);
	    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);     
		this.setVisible(true);
		creation.addActionListener(this);
		retour.addActionListener(this);
		pan.setLayout(null);
		pan.add(info);
		pan.add(titre);		
		pan.add(nomF);
		pan.add(titreF);
		pan.add(creation);
		pan.add(message);
		pan.add(messageF);
		pan.add(retour);
		info.setBounds(10, 50, 200, 30);
		titre.setBounds(10, 110, 200, 30);
		nomF.setBounds(210, 55, 200, 20);
		titreF.setBounds(210, 115, 200, 20);
		message.setBounds(10, 170, 200, 30);
		messageF.setBounds(200, 170, 250, 50);
		creation.setBounds(130, 230, 130, 30);
		retour.setBounds(3, 3, 130, 30);
		this.setContentPane(pan);
	}
	
	
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		String nom = nomF.getText();
		String titre = titreF.getText();
		String message = messageF.getText();
		DBConnect con = new DBConnect();
		if(source == creation) {
			String r = con.creationTicket(identifiant, titre, message, nom);
			if(r == "PAS OK") {               //PAS OK signifie que la creation du ticket a échouée
				pan.remove(succes);
				pan.add(erreur);
				erreur.setBounds(270, 230, 130, 30);
				this.setContentPane(pan);
			}else {
				pan.remove(erreur);
				pan.add(succes);
				succes.setBounds(270, 230, 130, 30);
				this.setContentPane(pan);
			}
		}

		if(source == retour) {
			InterfaceUtilisateur inter = new InterfaceUtilisateur(identifiant);
			this.dispose();
		}
		
	}
}
