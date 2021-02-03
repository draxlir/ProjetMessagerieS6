package interfaceUtilisateur;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import base.DBConnect;
import base.FilDeDiscussion;
import base.UtilisateurCampus;


public class Arbre {
	DBConnect connect = new DBConnect();
	JTree arbre;
	public Arbre() {
		
	}
	
	public JTree creationArbre(String identifiant) {
		/*Création de l'arbre des fils de discussion */
		DefaultMutableTreeNode racine = new DefaultMutableTreeNode();		
		DefaultMutableTreeNode l_groupe = new DefaultMutableTreeNode("Liste des Groupes");
		racine.add(l_groupe);
		//Set contenant tout les groupes avec lequel l'utilisateur peut écrire			
		NavigableSet<String> l = connect.listeGroupeUtilisateur(identifiant);
			
		/*Permet d'avoir les groupes dans l'ordre */
		for(Iterator<String> iter = l.iterator(); iter.hasNext();) {
			String s = iter.next();
			DefaultMutableTreeNode groupe = new DefaultMutableTreeNode(s);
			l_groupe.add(groupe);
			NavigableSet<FilDeDiscussion> filSet = new TreeSet<>();				
			if( connect.appartientGroupe(s, identifiant)) {       //si l'utilisateur est dans le groupe
				filSet = connect.listeFil(s);
			}else {                                               //sinon c'est qu'il à crée un ticket avec un autre groupe  
				filSet = connect.listFilCreateur(s, identifiant);
			}
			if(filSet.isEmpty()) {
				DefaultMutableTreeNode vide = new DefaultMutableTreeNode("pas de fil");
				groupe.add(vide);
			}else {
				/*Ajout des fils de chaque groupe */
				for(Iterator<FilDeDiscussion> iterF = filSet.iterator(); iterF.hasNext();) {
					FilDeDiscussion f = iterF.next();
					String nom = f.getTitre();
					DefaultMutableTreeNode fil = new DefaultMutableTreeNode(nom);
					groupe.add(fil);
				}
			}
				
		}
		arbre = new JTree(racine);	
		return arbre;
	}
}
