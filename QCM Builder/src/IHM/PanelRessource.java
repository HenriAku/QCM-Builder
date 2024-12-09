package IHM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Metier.Ressource;

public class PanelRessource extends JPanel implements ActionListener {
    private JButton[][] tabBtn;
    private ArrayList<Ressource> lstRes;

    public PanelRessource(ArrayList<Ressource> lstRes) 
    {
        this.lstRes = lstRes;

        // Récupérer la taille de l'écran de l'utilisateur
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Créer un panneau pour contenir tous les sous-panneaux
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null); // Disposition manuelle pour positionner les éléments
        contentPanel.setPreferredSize(new Dimension(screenWidth, lstRes.size() * 100)); // Hauteur dynamique selon le nombre de panneaux

        // Dimensions fixes pour chaque panneau et espacement
        int panelWidth = 600;
        int panelHeight = 70;
        int spacing = 20;

        // Dimensions des boutons
        int buttonSize = 30;

        // Calculer la position de départ pour centrer verticalement
        int totalHeight = (panelHeight + spacing) * lstRes.size() - spacing; // Hauteur totale
        int startY = (screenHeight - totalHeight) / 2;

        // Initialisation des boutons et positionnement
        tabBtn = new JButton[lstRes.size()][3];

        for (int i = 0; i < lstRes.size(); i++) {
            int posX = (screenWidth - panelWidth) / 2; // Centrer horizontalement
            int posY = startY + i * (panelHeight + spacing); // Calculer la position Y

            JPanel panel = new JPanel();
            panel.setLayout(null); // Layout nul pour le panneau
            panel.setBounds(posX, posY, panelWidth, panelHeight); // Position et taille du panneau
            contentPanel.add(panel);

            // Ajouter les boutons au panneau
            tabBtn[i][0] = new JButton(lstRes.get(i).getNom());
            tabBtn[i][0].setBounds(0, 10, panelWidth - 2 * (buttonSize + 30), panelHeight - 20); // Largeur ajustée pour le texte
            panel.add(tabBtn[i][0]);

            tabBtn[i][1] = new JButton(new ImageIcon(
                new ImageIcon("QCM Builder\\img\\LogoSuppr.png").getImage().getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH)));
            tabBtn[i][1].setBounds(panelWidth - 3 * buttonSize, 10, buttonSize, buttonSize);
            panel.add(tabBtn[i][1]);

            tabBtn[i][2] = new JButton(new ImageIcon(
                new ImageIcon("QCM Builder\\img\\LogoModif.png").getImage().getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH)));
            tabBtn[i][2].setBounds(panelWidth - buttonSize - 20, 10, buttonSize, buttonSize);
            panel.add(tabBtn[i][2]);
        }

        // Ajouter le panneau à un JScrollPane
        JScrollPane scrollPane = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(0, 0, screenWidth, screenHeight);

        // Ajouter le JScrollPane à ce panneau principal
        this.setLayout(null);
        this.add(scrollPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Gestion des événements à implémenter
    }
}
