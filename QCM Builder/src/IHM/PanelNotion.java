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
import java.util.ArrayList;

import Metier.Notion;
import Metier.Ressource;

public class PanelNotion extends JPanel implements ActionListener 
{
	private Controlleur ctrl  ;
	private JButton[][] tabBtn;
	private JButton     btnAdd;
	private ArrayList<Notion> lstN;
	private Ressource ressource;
	private FramePrincipal frame;
	private JScrollPane scrollPane;

	public PanelNotion(Controlleur ctrl, Ressource ressource, FramePrincipal frame) 
	{
		this.ctrl      = ctrl     ;
		this.frame     = frame    ;
		this.ressource = ressource;
		this.lstN      = ressource.getNotions();
		int tailleLst  = this.lstN.size      ();

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

		JLabel lblTitre = new JLabel("Notion");
		lblTitre.setBounds(0, 10, 400, 50);

		this.btnAdd = new JButton(new ImageIcon(
				new ImageIcon("QCM Builder\\img\\Ajout.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		this.btnAdd.setBounds(450, 10, 30, 30);

		panelAjout.add(lblTitre);
		panelAjout.add(this.btnAdd);
		this.add(panelAjout);

		/*********************************/
		/*  Création des ressources      */
		/*********************************/
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null); // Positionnement manuel
		contentPanel.setPreferredSize(new Dimension(screenWidth, tailleLst * 100)); // Définir une taille préférée pour le défilement

		this.tabBtn = new JButton[tailleLst][3];

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
			this.tabBtn[i][0] = new JButton(this.lstN.get(i).getNom());
			this.tabBtn[i][0].setBounds(0, 10, 400, 50);
			panelRes.add(this.tabBtn[i][0]);

			this.tabBtn[i][1] = new JButton(new ImageIcon(
					new ImageIcon("QCM Builder\\img\\LogoSuppr.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
			this.tabBtn[i][1].setBounds(410, 10, 30, 30);
			panelRes.add(this.tabBtn[i][1]);

			this.tabBtn[i][2] = new JButton(new ImageIcon(
					new ImageIcon("QCM Builder\\img\\LogoModif.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
			this.tabBtn[i][2].setBounds(450, 10, 30, 30);
			panelRes.add(this.tabBtn[i][2]);
		}

		// Ajouter contentPanel dans un JScrollPane
		scrollPane = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 100, screenWidth, screenHeight - 150); // Position et taille du JScrollPane
		this.add(scrollPane);


		/*********************************/
		/*	Activation des composants    */
		/*********************************/

		this.btnAdd.addActionListener(this);

		for(int i=0; i<this.tabBtn.length; i++)
		{
			this.tabBtn[i][0].addActionListener(this);
			this.tabBtn[i][1].addActionListener(this);
			this.tabBtn[i][2].addActionListener(this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.btnAdd) 
		{
			new FrameCreation(this.ctrl, "Notion",this.ressource.getNom(),this.frame);
			this.repaint();
		}

		for (int i = 0; i < this.tabBtn.length; i++) 
		{
			//Affiche les question des notions
			if (e.getSource() == this.tabBtn[i][0]) 
			{
				//affiche les question
			}
			//supprime la notion de la ligne
			if (e.getSource() == this.tabBtn[i][1]) 
			{
				this.ctrl.supprimerDossier(this.ressource.getNom()+"/"+this.tabBtn[i][0].getText());
				this.ressource.getNotions().remove(i);
				this.frame.refreshNotion(ressource);
			}
			//modifie le nom de la notion de la ligne
			if (e.getSource() == this.tabBtn[i][2]) 
			{
				new FrameModification(this.ctrl, this.frame ,"Notion", this.ressource,this.lstN.get(i));
			}
		}
	}
}