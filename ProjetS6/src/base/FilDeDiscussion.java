package base;
import java.util.Date;

public class FilDeDiscussion {
	private String titre;
	private Date dateDernierMessage;
	
	public FilDeDiscussion(String titre, Date date) {
		this.titre = titre;
		this.dateDernierMessage = date;
		
	}

	public String getTitre() {
		return titre;
	}

	public Date getDateDernierMessage() {
		return dateDernierMessage;
	}
	
	public void setTitre(String titre) {
		this.titre = titre;
	}

	public void setDateDernierMessage(Date dateDernierMessage) {
		this.dateDernierMessage = dateDernierMessage;
	}
}
