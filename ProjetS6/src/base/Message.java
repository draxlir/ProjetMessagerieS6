package base;

import java.util.Date;

public class Message {
	private String texte;
	private Date date;
	private String titre;
	private String status;
	private String id_envoyeur;
	
	public Message(String texte, Date date, String titre, String status, String id_envoyeur) {	
		this.texte = texte;
		this.date = date;
		this.titre = titre;
		this.status = status;
		this.id_envoyeur = id_envoyeur;
	}

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getId_envoyeur() {
		return id_envoyeur;
	}

	public void setId_envoyeur(String id_envoyeur) {
		this.id_envoyeur = id_envoyeur;
	}

	
}
