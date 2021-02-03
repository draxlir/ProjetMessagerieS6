package base;
public class UtilisateurCampus {
	private String nom;	
	private String prenom;
	private String identifiant;
	private boolean agentAdministratif;
	private String motDePasse;
	
	public UtilisateurCampus(String nom, String prenom, String identifiant, String motDePasse, boolean agentAdministratif)
	{
		this.nom = nom;
		this.prenom = prenom;
		this.identifiant = identifiant;		
		this.agentAdministratif = agentAdministratif;
		this.motDePasse = motDePasse;
		
	}
	
	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getIdentifiant() {
		return identifiant;
	}
	
	public boolean isAgentAdministratif() {
		return agentAdministratif;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

}
