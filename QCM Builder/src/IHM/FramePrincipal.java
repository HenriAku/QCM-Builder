package IHM;

import javax.swing.*;
import Controlleur.Controlleur;
import Metier.Ressource;
import java.awt.*;

public class FramePrincipal extends JFrame {
    private JPanel mainPanel; // Conteneur principal avec CardLayout
    private CardLayout cardLayout; // Gestionnaire de disposition
    private Controlleur ctrl;

    // Identifiants pour les panneaux
    private static final String PANEL_ACCEUIL = "Acceuil";
    private static final String PANEL_RESSOURCE = "Ressource";
    private static final String PANEL_CHAPITRE = "Chapitre";

    public FramePrincipal(Controlleur ctrl) {
        // Initialisation de la fenêtre
        this.setTitle("QCM Builder");
        this.setSize(1000, 1000);
        this.ctrl = ctrl;

        // Initialisation du CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Ajout des panneaux au conteneur principal
        mainPanel.add(new PanelAcceuil(ctrl, this), PANEL_ACCEUIL);
        mainPanel.add(new PanelRessource(ctrl, this), PANEL_RESSOURCE);

        // Ajout du conteneur principal à la fenêtre
        this.add(mainPanel);

        // Paramètres de la fenêtre
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Méthode pour rafraîchir l'affichage
    public void majIHM() {
        this.mainPanel.revalidate();
        this.mainPanel.repaint();
    }

    // Méthode pour afficher le panneau d'accueil
    public void afficheAcceuil() {
        cardLayout.show(mainPanel, PANEL_ACCEUIL);
    }

    // Méthode pour afficher le panneau des ressources
    public void afficheRessource() {
        cardLayout.show(mainPanel, PANEL_RESSOURCE);
    }

    // Méthode pour rafraîchir dynamiquement le panneau des ressources
    public void refreshRessource() {
        // Supprimer l'ancien panneau de ressources
        mainPanel.remove(mainPanel.getComponent(1)); // Suppression par index
        
        // Ajouter un nouveau panneau de ressources
        PanelRessource newRessourcePanel = new PanelRessource(ctrl, this);
        mainPanel.add(newRessourcePanel, PANEL_RESSOURCE);

        // Afficher le panneau des ressources
        cardLayout.show(mainPanel, PANEL_RESSOURCE);

        // Rafraîchir l'interface
        this.majIHM();
    }

    // Méthode pour afficher dynamiquement le panneau des chapitres
    public void afficheChapitre(Ressource ressource) {
        PanelChapitre chapitrePanel = new PanelChapitre(ctrl, ressource, this);
        mainPanel.add(chapitrePanel, PANEL_CHAPITRE);

        // Afficher le panneau des chapitres
        cardLayout.show(mainPanel, PANEL_CHAPITRE);

        // Rafraîchir l'interface
        this.majIHM();
    }
}
