/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package IHM;

import javax.swing.*;
import Controlleur.Controlleur;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import Metier.Ressource;

public class PanelRessource extends JPanel implements ActionListener 
{
	private Controlleur     ctrl      ;
	private JButton[][]     tabBtn    ;
	private JButton         btnAdd    ;
	private List<Ressource> lstRes    ;
	private JScrollPane     scrollPane;
	private FramePrincipal  frame     ;

	public PanelRessource(Controlleur ctrl, FramePrincipal frame) 
	{
		this.ctrl  = ctrl ;
		this.frame = frame;

		//Récupére les ressources
		this.lstRes   = this.ctrl.getLstRessource();
		int tailleLst = this.lstRes.size();

		//Récupére la taille de l'écran
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth      = screenSize.width ;
		int screenHeight     = screenSize.height;

		//Panel principal
		this.setLayout(null);

		PanelNavigation navigation = new PanelNavigation(frame, null);
		navigation.setBounds(0, 0, screenWidth, 100); 
		this.add(navigation);

		/*********************************/
		/*  Création du panneau d'ajout  */
		/*********************************/
		JPanel panelAjout = new JPanel();
		panelAjout.setLayout(null);
		panelAjout.setBounds((screenWidth - 600) / 2, 100, 600, 50);

		JLabel lblTitre = new JLabel("Toute vos ressources : ");
		lblTitre.setBounds(0, 5, 400, 20);

		this.btnAdd = new JButton(new ImageIcon(
				new ImageIcon(".." + File.separator + "img" + File.separator + "Ajout.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		this.btnAdd.setContentAreaFilled(false);
		this.btnAdd.setBorderPainted    (false);
		this.btnAdd.setFocusPainted     (false);
		this.btnAdd.setBounds(450, 10, 30, 30);
				
		JLabel lblExplication = new JLabel("Cliquer sur les Ressource pour voir leur notions");
		lblExplication.setBounds(0, 25, 400, 20);

		panelAjout.add(lblTitre   );
		panelAjout.add(this.btnAdd);
		panelAjout.add(lblExplication);
		this.add(panelAjout);

		/*********************************/
		/*  Création des ressources      */
		/*********************************/
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null); // Positionnement manuel
		contentPanel.setPreferredSize(new Dimension(screenWidth, tailleLst * 100));

		this.tabBtn = new JButton[tailleLst][3];

		int panelWidth  = 600;
		int panelHeight = 70 ;
		int spacing     = 20 ;

		for (int i = 0; i < tailleLst; i++) 
		{
			JPanel panelRes = new JPanel();
			panelRes.setLayout(null);
			panelRes.setBounds((screenWidth - panelWidth) / 2, i * (panelHeight + spacing), panelWidth, panelHeight);
			contentPanel.add(panelRes);

			//Boutons pour chaque ressource
			this.tabBtn[i][0] = new JButton(this.lstRes.get(i).getNom());
			this.tabBtn[i][0].setBounds(0, 10, 400, 50);
			this.tabBtn[i][0].setBackground(new Color(201,80,46));
			this.tabBtn[i][0].setForeground(Color.WHITE);
			panelRes.add(this.tabBtn[i][0]);

			this.tabBtn[i][1] = new JButton(new ImageIcon(
					new ImageIcon(".." + File.separator + "img" + File.separator + "LogoSuppr.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
			this.tabBtn[i][1].setBounds(410, 10, 30, 30);
			this.tabBtn[i][1].setContentAreaFilled(false);
			this.tabBtn[i][1].setBorderPainted    (false);
			this.tabBtn[i][1].setFocusPainted     (false);
			panelRes.add(this.tabBtn[i][1]);

			this.tabBtn[i][2] = new JButton(new ImageIcon(
					new ImageIcon(".." + File.separator + "img" + File.separator + "LogoModif.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
			this.tabBtn[i][2].setBounds(450, 10, 30, 30);
			this.tabBtn[i][2].setContentAreaFilled(false);
			this.tabBtn[i][2].setBorderPainted    (false);
			this.tabBtn[i][2].setFocusPainted     (false);
			panelRes.add(this.tabBtn[i][2]);
		}

		//Ajouter contentPanel dans un JScrollPane
		scrollPane = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 160, screenWidth, screenHeight - 200); 
		this.add(scrollPane);

		/*********************************/
		/* Activation des composants     */
		/*********************************/
		this.btnAdd.addActionListener(this);

		for (int i = 0; i < this.tabBtn.length; i++) 
		{
			this.tabBtn[i][0].addActionListener(this);
			this.tabBtn[i][1].addActionListener(this);
			this.tabBtn[i][2].addActionListener(this);
		}
	}

	
	public void actionPerformed(ActionEvent e) 
	{
		//affiche une frame pour crée une ressource
		if (e.getSource() == this.btnAdd) 
		{
			new FrameCreation(this.ctrl, "Ressource", "", frame);
		}

		for (int i = 0; i < this.tabBtn.length; i++) 
		{
			//Affiche les notions de la ressource
			if (e.getSource() == this.tabBtn[i][0]) 
			{
				frame.afficheNotion(this.lstRes.get(i));
			}
			//supprime la ressource de la ligne
			if (e.getSource() == this.tabBtn[i][1]) 
			{
				Object[] options = { "Oui", "Non" };
				int choix = JOptionPane.showOptionDialog(
					null, 
					"Voulez-vous supprimer ?", 
					"Confirmation", 
					JOptionPane.YES_NO_OPTION, 
					JOptionPane.QUESTION_MESSAGE, 
					null, 
					options,
					options[0]
				);


				//Vérifie le choix de l'utilisateur
				if (choix == JOptionPane.YES_OPTION) 
				{
					this.ctrl.supprimerDossier(this.tabBtn[i][0].getText());
					this.ctrl.getLstRessource().remove(i);
					this.frame.refreshRessource();
				}
			}
			//modifie le nom de la ressource de la ligne
			if (e.getSource() == this.tabBtn[i][2]) 
			{
				new FrameModification(this.ctrl, this.frame ,"Ressource", this.lstRes.get(i),null);
			}
		}
	}
}
