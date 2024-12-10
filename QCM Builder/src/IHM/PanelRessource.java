package IHM;

import javax.swing.*;
import Controlleur.Controlleur;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import Metier.Ressource;

public class PanelRessource extends JPanel implements ActionListener 
{
    private Controlleur ctrl;
    private JButton[][] tabBtn;
    private JButton btnAdd;
    private List<Ressource> lstRes;
    private JScrollPane scrollPane;
    private FramePrincipal frame;

    public PanelRessource(Controlleur ctrl, FramePrincipal frame) 
    {
        this.ctrl = ctrl;
        this.frame = frame;

        // Récupérer les ressources
        lstRes = this.ctrl.getLstRessource();
        int tailleLst = lstRes.size();

        // Récupérer la taille de l'écran
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Panel principal
        this.setLayout(null);

        /*********************************/
        /*  Création du panneau d'ajout  */
        /*********************************/
        JPanel panelAjout = new JPanel();
        panelAjout.setLayout(null);
        panelAjout.setBounds((screenWidth - 600) / 2, 20, 600, 70);

        JLabel lblTitre = new JLabel("Ressource");
        lblTitre.setBounds(0, 10, 400, 50);

        btnAdd = new JButton(new ImageIcon(
                new ImageIcon("QCM Builder\\img\\Ajout.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        btnAdd.setBounds(450, 10, 30, 30);

        panelAjout.add(lblTitre);
        panelAjout.add(btnAdd);
        this.add(panelAjout);

        /*********************************/
        /*  Création des ressources      */
        /*********************************/
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null); // Positionnement manuel
        contentPanel.setPreferredSize(new Dimension(screenWidth, tailleLst * 100)); // Définir une taille préférée pour le défilement

        tabBtn = new JButton[tailleLst][3];

        int panelWidth = 600;
        int panelHeight = 70;
        int spacing = 20;

        for (int i = 0; i < tailleLst; i++) 
        {
            JPanel panelRes = new JPanel();
            panelRes.setLayout(null);
            panelRes.setBounds((screenWidth - panelWidth) / 2, i * (panelHeight + spacing), panelWidth, panelHeight);
            contentPanel.add(panelRes);

            // Boutons pour chaque ressource
            tabBtn[i][0] = new JButton(lstRes.get(i).getNom());
            tabBtn[i][0].setBounds(0, 10, 400, 50);
            panelRes.add(tabBtn[i][0]);

            tabBtn[i][1] = new JButton(new ImageIcon(
                    new ImageIcon("QCM Builder\\img\\LogoSuppr.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
            tabBtn[i][1].setBounds(410, 10, 30, 30);
            panelRes.add(tabBtn[i][1]);

            tabBtn[i][2] = new JButton(new ImageIcon(
                    new ImageIcon("QCM Builder\\img\\LogoModif.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
            tabBtn[i][2].setBounds(450, 10, 30, 30);
            panelRes.add(tabBtn[i][2]);
        }

        // Ajouter contentPanel dans un JScrollPane
        scrollPane = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(0, 100, screenWidth, screenHeight - 150); // Position et taille du JScrollPane
        this.add(scrollPane);

        /*********************************/
        /* Activation des composants     */
        /*********************************/
        btnAdd.addActionListener(this);

        for (int i = 0; i < tabBtn.length; i++) 
        {
            tabBtn[i][0].addActionListener(this);
            tabBtn[i][1].addActionListener(this);
            tabBtn[i][2].addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == btnAdd) 
        {
            new FrameCreation(ctrl, "Ressource", "", frame);
        }

        for (int i = 0; i < tabBtn.length; i++) 
        {
            if (e.getSource() == tabBtn[i][0]) 
            {
                frame.afficheChapitre(lstRes.get(i));
            }
            if (e.getSource() == tabBtn[i][1]) 
            {
                System.out.println("Supprimer ressource : " + lstRes.get(i).getNom());
                //Supprimer sans frame
            }
            if (e.getSource() == tabBtn[i][2]) 
            {
                new FrameModification(ctrl, "Ressource", lstRes.get(i),null);
            }
        }
    }
}
