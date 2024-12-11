/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package IHM;

import javax.swing.*;
import Controlleur.Controlleur;
import Metier.Ressource;
import java.awt.*;

public class FramePrincipal extends JFrame 
{
    private JPanel      mainPanel ; 
    private CardLayout  cardLayout; 
    private Controlleur ctrl      ;

    private static final String PANEL_ACCEUIL   = "Acceuil"  ;
    private static final String PANEL_RESSOURCE = "Ressource";
    private static final String PANEL_NOTION    = "Notion" ;

    public FramePrincipal(Controlleur ctrl) 
    {
        // Récupérer la taille de l'écran
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

        this.setTitle("QCM Builder");
        this.setSize(screenWidth, screenHeight);
        this.setMinimumSize(new Dimension(1500, 300));

        this.ctrl = ctrl;

        //Initialisation du CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        //Ajout des panneaux au conteneur principal
        mainPanel.add(new PanelAcceuil(ctrl, this), PANEL_ACCEUIL);
        mainPanel.add(new PanelRessource(ctrl, this), PANEL_RESSOURCE);

        //Ajout du conteneur principal à la fenêtre
        this.add(mainPanel);

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //Méthode pour rafraîchir l'affichage
    public void majIHM() 
    {
        this.mainPanel.revalidate();
        this.mainPanel.repaint();
    }

    // Méthode pour afficher le panneau d'accueil et ressource
    public void afficheAcceuil  () {cardLayout.show(mainPanel, PANEL_ACCEUIL  );}
    public void afficheRessource() {cardLayout.show(mainPanel, PANEL_RESSOURCE);}


    // Méthode pour rafraîchir dynamiquement le panneau des ressources
    public void refreshRessource() 
    {
        mainPanel.remove(mainPanel.getComponent(1)); // Suppression par index ressource
        
        // Ajouter un nouveau panneau de ressources
        PanelRessource newRessourcePanel = new PanelRessource(ctrl, this);
        mainPanel.add(newRessourcePanel, PANEL_RESSOURCE);

        // Afficher le panneau des ressources
        cardLayout.show(mainPanel, PANEL_RESSOURCE);
        this.majIHM();
    }

    public void refreshNotion(Ressource ressource) 
    {
        mainPanel.remove(mainPanel.getComponent(2)); // Suppression par index notion
        
        // Ajouter un nouveau panneau de ressources
        PanelNotion panelNotion = new PanelNotion(ctrl, ressource, this);
        mainPanel.add(panelNotion, PANEL_NOTION);

        // Afficher le panneau des ressources
        cardLayout.show(mainPanel, PANEL_NOTION);
        this.majIHM();
    }

    // Méthode pour afficher dynamiquement le panneau des notions
    public void afficheNotion(Ressource ressource) 
    {
        PanelNotion panelNotion = new PanelNotion(ctrl, ressource, this);
        mainPanel.add(panelNotion, PANEL_NOTION);

        // Afficher le panneau des notions
        cardLayout.show(mainPanel, PANEL_NOTION);

        // Rafraîchir l'interface
        this.majIHM();
    }
}
