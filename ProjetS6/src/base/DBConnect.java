package base;
import java.sql.*;
import java.util.*;

//import com.mysql.cj.xdevapi.Result;

import java.sql.Date;
public class DBConnect {

	private Connection con;
	private Statement st;
	private ResultSet rs;
	//Connection à la base de donnée
	public DBConnect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projets6","root","");
			st = con.createStatement();	
		
		}catch(Exception ex) {
		System.out.println("Erro: "+ex);
		}
	}
	//Retourne le nom est prénom d'un utilisateur
	public String getNomPrenom(String identifiant) {
		String nomPrenom = "";
		try {
			String q_insert = "select nom, prénom from utilisateur where identifiant = ?";
			PreparedStatement preparedStatement = con.prepareStatement(q_insert);  //statement qui gère la conversion String/Varchar par exemple
			preparedStatement.setString(1, identifiant); 
			ResultSet setUser = preparedStatement.executeQuery();
			while(setUser.next()) {
				nomPrenom = setUser.getString(1)+ " "+ setUser.getString(2);
			}
			
			return nomPrenom;
		}catch(Exception ex){
			System.out.println(ex);
		}
		return nomPrenom;
	}
	
	//Ajoute un utilisateur 
	public String ajouterUtilisateur(UtilisateurCampus utilisateur){
		try {
			String q_insert = "insert into utilisateur (Nom, Prénom, Identifiant, MotDePasse, AgentAdministratif) values (?,?,?,?,?)";
			PreparedStatement preparedStatement = con.prepareStatement(q_insert);  //statement qui gère la conversion String/Varchar par exemple
			preparedStatement.setString(1, utilisateur.getNom());  //1er parametre correspond au premier ? de q_insert
			preparedStatement.setString(2, utilisateur.getPrenom());			
			preparedStatement.setString(3, utilisateur.getIdentifiant());
			preparedStatement.setString(4, utilisateur.getMotDePasse());
			preparedStatement.setBoolean(5, utilisateur.isAgentAdministratif());
			
			int row = preparedStatement.executeUpdate();  // execute la requete 
			return "OK";
		}catch(Exception ex){
			return "PAS OK";
		}
	}
	
	//Suprime un utilisateur
	public void supprimerUtilisateur(UtilisateurCampus utilisateur) {
		String sql = "delete from utilisateur where identifiant =?";
		String sql1 = "delete from Message where identifiant = ?";
		String sql2 = "delete from Appartient where identifiant = ?";
		//String sql4 = "select * from FilDeDiscussion where "
		String sql3 = "delete from Ticket where identifiant = ?";	
		try{	
				
				PreparedStatement pst1 = con.prepareStatement(sql1);		
				pst1.setString(1, utilisateur.getIdentifiant());				
				int result1 = pst1.executeUpdate();
				
				PreparedStatement pst2 = con.prepareStatement(sql2);		
				pst2.setString(1, utilisateur.getIdentifiant());				
				int result2 = pst2.executeUpdate();
				
				PreparedStatement pst3 = con.prepareStatement(sql3);		
				pst3.setString(1, utilisateur.getIdentifiant());				
				int result3 = pst3.executeUpdate();
			
			   PreparedStatement pst = con.prepareStatement(sql);		
			   pst.setString(1, utilisateur.getIdentifiant());				
			   int result = pst.executeUpdate();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
				
	}
	
	//Ajoute un groupe si il n'existe pas 
	public String ajouterGroupe(String nom) {
		String sql = "insert into groupe (Nom) values (?)";
		try {
			 PreparedStatement pst = con.prepareStatement(sql);
			 pst.setString(1, nom);			 
			 int row = pst.executeUpdate();
			 return "OK";
		}catch(SQLException e) {
			e.printStackTrace();
			return "PAS OK";
		}		
	}
	
	//Supprime un groupe
	public String supprimerGroupe(String nom) {
		int compteur = 0;
		String sql = "delete from ticket where nom =?";
		String sql1 = "delete from appartient where nom = ?";
		String sql2 = "delete from groupe where nom = ?";	
        String sqltest = "select count(*) from groupe where nom = ?";
		try{	
				PreparedStatement pst = con.prepareStatement(sqltest);		
				pst.setString(1, nom);		
				ResultSet res = pst.executeQuery();
				while(res.next()){
					compteur = res.getInt(1);
				}
				if(compteur == 0) {
					return "PAS OK";
				}
			
				PreparedStatement pst1 = con.prepareStatement(sql);		
				pst1.setString(1, nom);				
				int result1 = pst1.executeUpdate();
				
				PreparedStatement pst2 = con.prepareStatement(sql1);		
				pst2.setString(1, nom);				
				int result2 = pst2.executeUpdate();
				
				PreparedStatement pst3 = con.prepareStatement(sql2);		
				pst3.setString(1, nom);				
				int result3 = pst3.executeUpdate();	
				
				return "OK";
			 
		} catch (SQLException e) {			
			e.printStackTrace();
			return "PAS OK";
		}
				
	
	}
	
	//Ajoute un utilisateur dans un groupe
	public String ajouterUtilisateurGroupe(String identifiant, String groupe) {
		String sql = "insert into appartient (Identifiant, Nom) values (?,?)";
		try {
			 PreparedStatement pst = con.prepareStatement(sql);
			 pst.setString(1, identifiant);
			 pst.setString(2, groupe);
			 int row = pst.executeUpdate();
			 return "OK";
		}catch(SQLException e) {
			e.printStackTrace();			
			return "PAS OK";
		}
		
	}
	
	//Supprime un utilisateur d'un groupe
	public String supprimerUtilisateurGroupe(String identifiant, String groupe) {
	    int compteur = 0;
		String sql = "delete from appartient where Identifiant = ? and nom = ?";
	    String sqltest = "select count(*) from groupe where nom = ? and Identifiant = ?";
		try {			
			PreparedStatement pst = con.prepareStatement(sqltest);		
			pst.setString(1, groupe);
			pst.setString(2, identifiant);		
			ResultSet res = pst.executeQuery();
			while(res.next()){
				compteur = res.getInt(1);
			}
			if(compteur == 0) {
				return "PAS OK";
			}
			PreparedStatement pst2 = con.prepareStatement(sql);			
			pst2.setString(1, identifiant);
			pst2.setString(2, groupe);			
		    int row = pst2.executeUpdate();
		    return "OK";
		}catch(SQLException e) {
			e.printStackTrace();
			return "PAS OK";
		}
		
	}
	
	//Teste le login est mot de passe 
	public boolean connexion(String identifiant, String motDePasse) {
		String sql = "select Identifiant,MotDePasse from utilisateur where Identifiant =? and MotDePasse=?"; 
		try( PreparedStatement preparedStatement = con.prepareStatement(sql) ) { //préparation de la requête 			 
		    preparedStatement.setString(1,identifiant);  //on assigne l'ID au premier paramètre 		 
		    preparedStatement.setString(2,motDePasse);  //on assigne le mot de passe au second paramètre 
		 
		    ResultSet result = preparedStatement.executeQuery(); // exécution de la requête
		    if ( result.next() ) {		    	
		    	return true;
		    }
		    else {
		        return false;
		    }
		 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//Création d'un fil de discussion
	public String creationTicket(String identifiant, String nomTicket, String message, String groupeDest) {		
		String sql0 = "insert into FilDeDiscussion(Titre, DateDernier) values (?,?)";
	
		String sql = "insert into Ticket(Titre,Identifiant,Nom) values (?,?,?)";
		String ajoutMess = "insert into Message(Texte, Date, Status, Titre, Identifiant) values (?,?,?,?,?)";
		try {			
			PreparedStatement pst0 = con.prepareStatement(sql0);
			pst0.setString(1, nomTicket);
			pst0.setTimestamp(2,getCurrentTimeStamp());
			int row0 = pst0.executeUpdate();
			
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, nomTicket);
			pst.setString(2, identifiant);
			pst.setString(3, groupeDest);
			int row = pst.executeUpdate();
			
			PreparedStatement pst2 = con.prepareStatement(ajoutMess);
			pst2.setString(1, message);			
			pst2.setTimestamp(2,getCurrentTimeStamp());
			pst2.setString(3, "reçu");
			pst2.setString(4, nomTicket);
			pst2.setString(5, identifiant);
			int row2 = pst2.executeUpdate(); 
			return "OK";
		}catch(SQLException e) {
			e.printStackTrace();
			return "PAS OK";
		}		
	}
	
	/*Permet d'ecrire un message dans un fil de discussion en vérifiant l'accès */
	public void ecrireMessage(String utilisateur, String message, String titreFil) {		
		boolean ecriture_possible = false;
		String groupe = "select nom from appartient where identifiant = ?";
		String sql = "insert into Message(Texte, Date, Status, Titre, Identifiant) values (?,?,?,?,?)";	
		String date = "update fildediscussion set titre = ?, datedernier = ? where titre = ?";		
		int compteur = 0;
		try {		
						
			PreparedStatement gr = con.prepareStatement(groupe);    //selectionne tout les nom des groupes de l'utilisateur
			gr.setString(1,utilisateur);
			ResultSet setGroupe = gr.executeQuery();               
			while(setGroupe.next()) {			                   //pour chaque groupe
				String groupe2 = "select count(*) from ticket where nom = ? and titre = ?";   //on regarde si il y a un ticket ayant le même titre que en parametre de la fonction et un nom identique à celui du groupe
				PreparedStatement gr2 = con.prepareStatement(groupe2);
				gr2.setString(1,setGroupe.getString(1));
				gr2.setString(2, titreFil);
				ResultSet set_ticket_id = gr2.executeQuery();
				while(set_ticket_id.next()){               //on regarde si un tel ticket existe
					compteur = set_ticket_id.getInt(1);
				}  
				if(compteur != 0) {                        //si il en existe un  
					ecriture_possible = true;              //l'écriture est possible 
				}
			}
			if(!ecriture_possible) {	                  //sinon
				String createur = "select count(*) from ticket where titre = ? and identifiant = ?";  //on cherche si il y a un ticket correspondant au nom passé en parametre et pour créateur l'utilisateur	
				PreparedStatement crea = con.prepareStatement(createur);
				crea.setString(1,titreFil);
				crea.setString(2, utilisateur);
				ResultSet creat = crea.executeQuery();
				while(creat.next()){
					compteur = creat.getInt(1);
				}
				if(compteur != 0) {
					ecriture_possible = true;
				}
			}
			if(ecriture_possible) {
							
				PreparedStatement pst = con.prepareStatement(sql);
				pst.setString(1, message);			
				pst.setTimestamp(2,getCurrentTimeStamp());
				pst.setString(3, "reçu");
				pst.setString(4, titreFil);
				pst.setString(5, utilisateur);
				int row2 = pst.executeUpdate();
				
				PreparedStatement pstdate = con.prepareStatement(date);
				pstdate.setString(1, titreFil);
				pstdate.setTimestamp(2,getCurrentTimeStamp());
				pstdate.setString(3, titreFil);
				int row3 = pstdate.executeUpdate();
				
			
			}else {
				System.out.println("Vous ne pouvez pas écrire dans ce fil de discussion");
			}
			}catch(SQLException e) {
				e.printStackTrace();
			}
	}
	
	//Renvoit la liste des groupes auquel appartient l'utilisateur	
	public NavigableSet<String> listeGroupeUtilisateur(String identifiant) {
		String groupe = "select nom from appartient where identifiant = ?";
		String groupe_dest = "select nom from ticket where identifiant = ?";
		NavigableSet<String> resultat = new TreeSet<>();
		try {			
			PreparedStatement gr = con.prepareStatement(groupe);    //selectionne tout les nom des groupes de l'utilisateur
			gr.setString(1,identifiant);
			ResultSet setGroupe = gr.executeQuery();    
			while(setGroupe.next()) {
				String s = setGroupe.getString(1);  //1 car il y a que une colonne
				resultat.add(s);
			}
			PreparedStatement gr_dest = con.prepareStatement(groupe_dest);    //selectionne tout les nom des groupes de l'utilisateur
			gr_dest.setString(1,identifiant);
			ResultSet setGroupe2 = gr_dest.executeQuery();    
			while(setGroupe2.next()) {
				String s2 = setGroupe2.getString(1);  
				resultat.add(s2);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();			
		}		
		return resultat;
	}
	
	/*Connaitre la liste des fils de discussions d'un groupe*/
	public NavigableSet<FilDeDiscussion> listeFil(String nomGroupe){
		String fil = "select titre from ticket where nom = ?";
		String fil2 = "select titre, dateDernier from fildediscussion where titre = ?";
		NavigableSet<FilDeDiscussion> resultat = new TreeSet<>(new Comparator<FilDeDiscussion>() {
			public int compare(FilDeDiscussion f1 , FilDeDiscussion f2) {
				java.util.Date d1 = f1.getDateDernierMessage();
				java.util.Date d2 = f2.getDateDernierMessage();
				if(d1.after(d2)) {
					return -1;
				}else {
					return 1;
				}
			}
		}	
		);
		try {			
			PreparedStatement psFil = con.prepareStatement(fil);    //selectionne tout les nom des groupes de l'utilisateur
			psFil.setString(1, nomGroupe);
			ResultSet setGroupe = psFil.executeQuery();    
			while(setGroupe.next()) {
				PreparedStatement psFil2 = con.prepareStatement(fil2);
				psFil2.setString(1, setGroupe.getString(1));
				ResultSet setFil = psFil2.executeQuery();
				while(setFil.next()) {
					FilDeDiscussion f = new FilDeDiscussion(setFil.getString(1), setFil.getTimestamp(2));
					resultat.add(f);
				}
			}			
		}catch(SQLException e) {
			e.printStackTrace();			
		}		
		return resultat;
	}
	
	//Retourne la liste des fils de discussion crée par un utilisateur
	public NavigableSet<FilDeDiscussion> listFilCreateur(String nomG, String identfiantUser){
		String fil = "select titre from ticket where nom = ? and identifiant = ?";
		String fil2 = "select titre, dateDernier from fildediscussion where titre = ?";
		NavigableSet<FilDeDiscussion> resultat = new TreeSet<>(new Comparator<FilDeDiscussion>() {
			public int compare(FilDeDiscussion f1 , FilDeDiscussion f2) {
				java.util.Date d1 = f1.getDateDernierMessage();
				java.util.Date d2 = f2.getDateDernierMessage();
				if(d1.after(d2)) {
					return -1;
				}else {
					return 1;
				}
			}
		}	
		);
		try {			
			PreparedStatement psFil = con.prepareStatement(fil);    //selectionne tout les nom des groupes de l'utilisateur
			psFil.setString(1, nomG);
			psFil.setString(2, identfiantUser );
			ResultSet setGroupe = psFil.executeQuery();    
			while(setGroupe.next()) {
				PreparedStatement psFil2 = con.prepareStatement(fil2);
				psFil2.setString(1, setGroupe.getString(1));
				ResultSet setFil = psFil2.executeQuery();
				while(setFil.next()) {
					FilDeDiscussion f = new FilDeDiscussion(setFil.getString(1), setFil.getTimestamp(2));
					resultat.add(f);
				}
			}			
		}catch(SQLException e) {
			e.printStackTrace();			
		}		
		return resultat;
	}
	
	//Teste l'appartenance d'un utilisateur à un groupe
	public boolean appartientGroupe(String nomG, String identfiantUser){
		String app = "select count(*) from appartient where nom = ? and identifiant = ?";
		int compteur = 0;
		try {			
			PreparedStatement psApp = con.prepareStatement(app);    //selectionne tout les nom des groupes de l'utilisateur
			psApp.setString(1, nomG);
			psApp.setString(2, identfiantUser );
			ResultSet setGroupe = psApp.executeQuery();    
			while(setGroupe.next()){               //on regarde si un tel ticket existe
				compteur = setGroupe.getInt(1);
			}  
			if(compteur != 0) {                        //si il en existe un  
				return true;              //l'écriture est possible 
			}	
		}catch(SQLException e) {
			e.printStackTrace();			
		}		
		return false;
	}
	
	//Renvoit la liste des messages d'un groupe
	public NavigableSet<Message> listeMessage(FilDeDiscussion fil){
		String sql = "select * from message where titre = ?";
		NavigableSet<Message> resultat = new TreeSet<>(new Comparator<Message>() {
			public int compare(Message m1 , Message m2) {
				java.util.Date d1 = m1.getDate();
				java.util.Date d2 = m2.getDate();
				if(d1.after(d2)) {
					return 1;      //pour avoir en premier les vieux messages
				}else {
					return -1;
				}
			}
		}	
		);
		try {			
			PreparedStatement psMess = con.prepareStatement(sql);    
			psMess.setString(1, fil.getTitre());
			ResultSet setMessage = psMess.executeQuery();
			while(setMessage.next()) {
				Message m = new Message(setMessage.getString(1), setMessage.getTimestamp(2), setMessage.getString(3), setMessage.getString(4), setMessage.getString(5));
				resultat.add(m);
			}
		}catch(SQLException e) {
			e.printStackTrace();			
		}		
		return resultat;
	}
	
	public static java.sql.Timestamp getCurrentTimeStamp() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());
	}	
}
