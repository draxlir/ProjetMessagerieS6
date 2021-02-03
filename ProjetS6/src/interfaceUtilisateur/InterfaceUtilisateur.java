package interfaceUtilisateur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.View;
import javax.swing.tree.DefaultMutableTreeNode;

import base.DBConnect;
import base.FilDeDiscussion;
import base.Message;

public class InterfaceUtilisateur extends JFrame implements ActionListener{
	JMenuBar menu = new JMenuBar();
	JButton itemCreer = new JButton("Créer un ticket");	
	JMenuItem itemCouleur = new JMenuItem("itemCouleur");
	JMenuItem itemTaille = new JMenuItem("itemTaille");
	JPanel pan = new JPanel();
	JPanel panelFil = new JPanel();
	JPanel panelFieldChat = new JPanel();
	JPanel panelVisuChat = new JPanel();
	JTextArea areaChat = new JTextArea();
	JScrollPane scrollChat;
	JButton boutonChat = new JButton("Envoyer");
	private JTree arbre;
	private String identifiant;
	DBConnect connect = new DBConnect();
	List<JLabel> testlabel = new ArrayList<>();		
	Date maDateJava =new java.util.Date();	
	private FilDeDiscussion fil_actif = new FilDeDiscussion("init", maDateJava);
	
	public InterfaceUtilisateur(String identifiant) {
		
		this.identifiant = identifiant;		
		this.setTitle("Interface utilisateur");
		this.setSize(1200,700);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		menu.setLayout(null);
		panelFil.add(itemCreer);		
		
		this.setJMenuBar(menu);
		panelVisuChat.setBackground(new Color(158, 158, 158));
		panelVisuChat.setLayout(null);
		panelFieldChat.setBackground(new Color(175, 175, 175));
		panelFil.setBackground(new Color(175, 175, 175));		
		scrollChat = new JScrollPane(panelVisuChat);
		pan.setLayout(null);
		this.setContentPane(pan);
		pan.add(panelFil);
		pan.add(scrollChat);
		pan.add(panelFieldChat);
		panelVisuChat.setBackground(new Color(206, 206, 206));
		panelVisuChat.setLayout(new GridLayout(1,1));
		scrollChat.setBackground(new Color(200,100,100));
		
		pan.setBackground(new Color(0,0,0));

		Dimension dim = this.getSize();
		panelFil.setBounds(0, 0, (int) (dim.width/5), dim.height - 60);		
		scrollChat.setBounds((int) (dim.width/5) , 0, (int) (dim.width*0.8) - 15, (int) (dim.height*0.90)- 60);
		panelVisuChat.setBounds((int) (dim.width/5) , 0, (int) (dim.width*0.8) - 15, (int) (dim.height*0.90)- 60);
		panelFieldChat.setBounds((int) (dim.width/5), (int) (dim.height*0.90 - 60), (int) (dim.width*0.8) - 15 ,(int) (dim.height*0.10));
		panelFieldChat.add(areaChat);
		panelFieldChat.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		dim = panelFieldChat.getSize();
		areaChat.setPreferredSize(new Dimension((int) (dim.width - 100) ,(int) dim.height - 10));
		itemCreer.setBounds(5, 5, 100, 30);
		itemCreer.addActionListener(this);
		
		//Creation de l'arbre
		Arbre arb = new Arbre();
		arbre = arb.creationArbre(identifiant);		
		panelFil.add(arbre);
		
		
		panelFieldChat.add(boutonChat);
		boutonChat.addActionListener(new ActionListener() {      //écriture d'un nouveau message
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = areaChat.getText();
				if(msg != "") {
					connect.ecrireMessage(identifiant, msg, fil_actif.getTitre());
					areaChat.setText("");
					affichageChat(fil_actif);
				
				}
			}
		});
		this.setVisible(true);
		//Gestion de la séléction du fil de discussion dans l'arbre
		arbre.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				
				panelVisuChat.removeAll();
		        DefaultMutableTreeNode node = (DefaultMutableTreeNode)arbre.getLastSelectedPathComponent();
		        if (node == null)
		            return;
		        String s = (String)node.getUserObject();
		        Date maDateJava =new java.util.Date();
				maDateJava.getDate();
		        FilDeDiscussion fil = new FilDeDiscussion(s, maDateJava);		      
		        fil_actif = fil;
		        JLabel labelTitre = new JLabel(fil.getTitre());
				labelTitre.setBounds(5, 5, 150, 12);
				panelVisuChat.add(labelTitre);
				panelVisuChat.removeAll();
				affichageChat(fil_actif);
				
			}	
		});

	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {		
		Object source = e.getSource();
		if(source == itemCreer) {
			CreationFil crea = new CreationFil(identifiant);
			this.dispose();
		}
		
	
	}


	public void affichageChat(FilDeDiscussion fil) {
		Date date =new java.util.Date();
		maDateJava.getDate();
		NavigableSet<Message> setMessage = new TreeSet<>(	
				new Comparator<Message>() {
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
		//Set qui contient l'ensemble des messages d'un fil
		setMessage = connect.listeMessage(fil);	
		Iterator<Message> iter = setMessage.iterator();
		int numberMessageDone = 0;
		int numberLinesDone = 0;
		Rectangle rect = scrollChat.getBounds();
		panelVisuChat.removeAll();
		panelVisuChat.setLayout(new GridLayout(2*setMessage.size(),1));
		System.out.println(2*setMessage.size());
		while(iter.hasNext()) {
			
			Message msg = iter.next();
			Date d_message = msg.getDate();
			String date_s = d_message.toString();
		
			JLabel nomExpe = new JLabel(connect.getNomPrenom(msg.getId_envoyeur())+"   "+date_s);  //affiche le nom prenom et la date du message
			JTextArea texteMsg = new JTextArea();
			texteMsg.setLineWrap(true);
			texteMsg.setText(msg.getTexte());
			texteMsg.setWrapStyleWord(true);
			texteMsg.setEditable(false);

			if (msg.getId_envoyeur() == "id_envoyeur2") {
				System.out.println("yo,"+msg.getTexte());
				nomExpe.setBounds((int) (0.30* rect.getWidth())-5, 30 + 65 * numberMessageDone, 450, 12);
				texteMsg.setBackground(new Color(0,153,2));
				texteMsg.setBounds((int) (0.30* rect.getWidth())-5, 42 + 65 * numberMessageDone, (int) (0.70* rect.getWidth()), 4+ 16 * texteMsg.getLineCount()); // fontsize = 12 (16 = 4 + 12 avec 4 etant les border en haut et en bas des lignes)	
			
			}
			else {			
				nomExpe.setBounds(5, 30 + 65 * numberMessageDone, 450, 12);
				texteMsg.setBounds(5, 42 + 65 * numberMessageDone, (int) (0.70* rect.getWidth()), 4+ 16 * texteMsg.getLineCount()); // fontsize = 12 (16 = 4 + 12 avec 4 etant les border en haut et en bas des lignes)
			}		
			panelVisuChat.add(nomExpe);
			panelVisuChat.add(texteMsg);
			numberMessageDone++;		
		}
	}
}




