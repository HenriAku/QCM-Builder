/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package IHM;

import javax.swing.*;
import Controlleur.Controlleur;
import Metier.Notion;
import Metier.Ressource;
import java.awt.*;

public class FramePrincipal extends JFrame 
{
	private JPanel      mainPanel ; 
	private CardLayout  cardLayout; 
	private Controlleur ctrl      ;

	private Ressource ressourceActuelle;
	private Notion    notionActuelle;

	private static final String PANEL_ACCEUIL    = "Acceuil"  ;
	private static final String PANEL_RESSOURCE  = "Ressource";
	private static final String PANEL_NOTION     = "Notion"   ;
	private static final String PANEL_QUESTION   = "Question" ;


	public FramePrincipal(Controlleur ctrl) 
	{
		//Recupere la taille de l'écran
		Dimension screenSize   = Toolkit.getDefaultToolkit().getScreenSize();
		int       screenWidth  = screenSize.width ;
		int       screenHeight = screenSize.height;

		this.setTitle("QCM Builder");
		this.setSize (screenWidth, screenHeight);
		this.setMinimumSize(new Dimension(1550, 300));

		this.ctrl = ctrl;

		this.ressourceActuelle = null;
		this.notionActuelle    = null;

		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);

		mainPanel.add(new PanelAcceuil  (ctrl, this), PANEL_ACCEUIL  );
		mainPanel.add(new PanelRessource(ctrl, this), PANEL_RESSOURCE);

		this.add(mainPanel);

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	//Méthode pour mettre à jour l'affichage
	public void majIHM() 
	{
		this.mainPanel.revalidate();
		this.mainPanel.repaint();
	}

	//Méthode pour afficher le panneau d'accueil, ressource, question
	public void afficheAcceuil  () {cardLayout.show(mainPanel, PANEL_ACCEUIL  );}
	public void afficheRessource() {cardLayout.show(mainPanel, PANEL_RESSOURCE);}
	public void afficherQuestion() {cardLayout.show(mainPanel, PANEL_QUESTION); }


	//Méthode pour mettre à jour le panneau des ressources
	public void refreshRessource() 
	{
		mainPanel.remove(mainPanel.getComponent(1)); // Suppression par index ressource
		
		PanelRessource newRessourcePanel = new PanelRessource(ctrl, this);
		mainPanel.add(newRessourcePanel, PANEL_RESSOURCE);

		cardLayout.show(mainPanel, PANEL_RESSOURCE);
		this.majIHM();
	}

	//Méthode pour mettre à jour le panneau des notion
	public void refreshNotion(Ressource ressource) 
	{
		mainPanel.remove(mainPanel.getComponent(2));
		
		PanelNotion panelNotion = new PanelNotion(ctrl, ressource, this);
		mainPanel.add(panelNotion, PANEL_NOTION);

		cardLayout.show(mainPanel, PANEL_NOTION);
		this.majIHM();
	}

	//Méthode pour mettre à jour le panneau des question
	public void refreshQuestion(Notion notion, Ressource ressource) 
	{
		mainPanel.remove(mainPanel.getComponent(3));
		
		PanelQuestion panelQuestion = new PanelQuestion(ctrl, notion, ressource, this);
		mainPanel.add(panelQuestion, PANEL_QUESTION);

		cardLayout.show(mainPanel, PANEL_QUESTION);
		this.majIHM();
	}

	//Méthode pour afficher le panneau des notions
	public void afficheNotion(Ressource ressource) 
	{
		PanelNotion panelNotion = new PanelNotion(ctrl, ressource, this);
		mainPanel.add(panelNotion, PANEL_NOTION);

		cardLayout.show(mainPanel, PANEL_NOTION);

		this.majIHM();
	}

	//Méthode pour afficher le panneau des notions
	public void AfficheQuestion(Notion notion, Ressource ressource) 
	{
		PanelQuestion panelQuestion = null;

		if (notion != null && ressource != null)
		{
			this.notionActuelle = notion;
			this.ressourceActuelle = ressource;
			panelQuestion = new PanelQuestion(ctrl, notion, ressource, this);
		}
		else
		{
			panelQuestion = new PanelQuestion(ctrl, this.notionActuelle, this.ressourceActuelle, this);
		}
		mainPanel.add(panelQuestion, PANEL_QUESTION);

		cardLayout.show(mainPanel, PANEL_QUESTION);
		this.majIHM();
	}
}
