package IHM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Controlleur.Controlleur;

public class PanelAcceuil extends JPanel implements ActionListener 
{
    private Controlleur ctrl;
    private FramePrincipal frame;

    public PanelAcceuil(Controlleur ctrl, FramePrincipal frame) 
    {
        this.ctrl = ctrl;
        this.frame = frame;

        // Configuration du layout principal
        GridBagLayout grid = new GridBagLayout();
        setLayout(grid);

        GridBagConstraints c = new GridBagConstraints();

        // Contenu entête
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0.1; // 10% de la hauteur totale
        c.gridx = 0;
        c.gridy = 0;
        JPanel entete = new JPanel();
        entete.setBackground(Color.ORANGE);
        this.add(entete, c);

        // Contenu principal (Milieu)
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0.9; // 80% de la hauteur totale
        c.gridx = 0;
        c.gridy = 1;

        // Panneau pour les éléments dans la zone centrale
        JPanel contenuMilieu = new JPanel();
        contenuMilieu.setLayout(new GridBagLayout());
        contenuMilieu.setBackground(Color.WHITE);

        // Configuration des éléments dans contenuMilieu
        GridBagConstraints cmConstraints = new GridBagConstraints();
        cmConstraints.fill = GridBagConstraints.HORIZONTAL;
        cmConstraints.insets = new Insets(20, 20, 20, 20); // Espacement autour des composants
        cmConstraints.weightx = 1;
        cmConstraints.weighty = 1;

        // Ajout de "Créer une question"
        cmConstraints.gridx = 0; // Position 0, colonne 0
        cmConstraints.gridy = 0; // Ligne 0
        JPanel creationQuestion = createPanel("Créer une question", "Créer question");
        contenuMilieu.add(creationQuestion, cmConstraints);

        // Ajout de "Créer un questionnaire"
        cmConstraints.gridx = 1; // Colonne 1
        cmConstraints.gridy = 0; // Ligne 0
        JPanel creationQuestionnaire = createPanel("Créer un questionnaire", "Créer questionnaire");
        contenuMilieu.add(creationQuestionnaire, cmConstraints);

        // Ajout de "Créer une ressource"
        cmConstraints.gridx = 2; // Colonne 2
        cmConstraints.gridy = 0; // Ligne 0
        JPanel creationRessource = createPanel("Créer une ressource", "Créer ressource");
        contenuMilieu.add(creationRessource, cmConstraints);

        // Ajout du panneau central
        this.add(contenuMilieu, c);

        frame.setVisible(true);
    }

    private JPanel createPanel(String titre, String boutonTexte) 
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1)); // Grille à deux lignes (titre + bouton)
        
        // Pas de fond pour le panneau
        panel.setBackground(null); 

        // Label avec le titre
        JLabel labelTitre = new JLabel(titre);
        labelTitre.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitre.setFont(new Font("Arial", Font.BOLD, 20)); // Taille du titre

        // Bouton avec le texte
        JButton bouton = new JButton(boutonTexte);
        bouton.setFont(new Font("Arial", Font.BOLD, 14)); // Taille plus petite du texte
        bouton.setPreferredSize(new Dimension(150, 40)); // Taille plus petite et plus rectangulaire
        
        // Enlever le fond du bouton et arrondir les bords
        bouton.setBackground(Color.ORANGE); // Fond orange pour les boutons
        bouton.setFocusPainted(false); // Enlever la surbrillance lorsqu'on clique sur le bouton
        bouton.setContentAreaFilled(true); // Remplir la zone de contenu du bouton

        bouton.addActionListener(this);

        panel.add(labelTitre);
        panel.add(bouton);

        return panel;
    }

    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() instanceof JButton) 
		{
            JButton btn = (JButton) e.getSource();
            if("Créer question".equals(btn.getText()))
            {
                System.out.println("test1");
            }

            if("Créer questionnaire".equals(btn.getText()))
            {
                System.out.println("test2");
            }

            if("Créer ressource".equals(btn.getText()))
            {
                this.frame.afficheRessource();
            }
        }
    }
}
