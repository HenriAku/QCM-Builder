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
import java.util.ArrayList;

import Metier.Notion;
import Metier.Question;
import Metier.Ressource;

public class PanelQuestion extends JPanel implements ActionListener
{
	private Controlleur ctrl  ;
	private JButton[][] tabBtn;
	private JButton     btnAdd;
	private ArrayList<Question> lstQ;
	private Notion notion;
	private FramePrincipal frame;
	private JScrollPane scrollPane;
	private Ressource ressource;


	public PanelQuestion(Controlleur ctrl, Notion notion, Ressource ressource, FramePrincipal frame) 
	{
		this.ctrl      = ctrl     ;
		this.frame     = frame    ;
		this.notion    = notion   ;
		this.ressource = ressource;
		this.lstQ   = notion.getLstQuestions();


		int tailleLst  = this.lstQ.size();

		// Récupérer la taille de l'écran
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		// Panel principal
		this.setLayout(null);

		/*********************************/
		/*  Création du panneau d'ajout  */
		/*********************************/

		PanelNavigation navigation = new PanelNavigation(frame, this.ressource);
		navigation.setBounds(0, 0, screenWidth, 100); 
		this.add(navigation);

		JPanel panelAjout = new JPanel();
		panelAjout.setLayout(null);
		panelAjout.setBounds((screenWidth - 600) / 2, 100, 600, 50);

		JLabel lblTitre = new JLabel("Question");
		lblTitre.setBounds(0, 10, 400, 50);

		this.btnAdd = new JButton(new ImageIcon(
				new ImageIcon("QCM Builder" + File.separator + "img" + File.separator + "Ajout.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		this.btnAdd.setContentAreaFilled(false);
		this.btnAdd.setBorderPainted    (false);
		this.btnAdd.setFocusPainted     (false);
		this.btnAdd.setBounds(450, 10, 30, 30);

		panelAjout.add(lblTitre);
		panelAjout.add(this.btnAdd);
		this.add(panelAjout);

		/*********************************/
		/*  Création des notions      */
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

			// Boutons pour chaque notion
			String question = this.lstQ.get(i).getQuestion();
			String motif = "{\\rtf1\\ansi{\\fonttbl\\f0\\fnil Monospaced;}";
			
			if (question.startsWith(motif)) 
				question = question.substring(motif.length());
			
			this.tabBtn[i][0] = new JButton(question);			
			this.tabBtn[i][0].setBounds(0, 10, 400, 50);
			this.tabBtn[i][0].setBackground(new Color(201,80,46));
			panelRes.add(this.tabBtn[i][0]);

			this.tabBtn[i][1] = new JButton(new ImageIcon(
					new ImageIcon("QCM Builder" + File.separator + "img" + File.separator + "LogoSuppr.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
			this.tabBtn[i][1].setBounds(410, 10, 30, 30);
			this.tabBtn[i][1].setContentAreaFilled(false);
			this.tabBtn[i][1].setBorderPainted    (false);
			this.tabBtn[i][1].setFocusPainted     (false);
			panelRes.add(this.tabBtn[i][1]);

			this.tabBtn[i][2] = new JButton(new ImageIcon(
					new ImageIcon("QCM Builder" + File.separator + "img" + File.separator + "LogoModif.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
			this.tabBtn[i][2].setBounds(450, 10, 30, 30);
			this.tabBtn[i][2].setContentAreaFilled(false);
			this.tabBtn[i][2].setBorderPainted    (false);
			this.tabBtn[i][2].setFocusPainted     (false);
			panelRes.add(this.tabBtn[i][2]);
		}

		// Ajouter contentPanel dans un JScrollPane
		scrollPane = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 160, screenWidth, screenHeight - 200); // Ajusté pour prendre en compte PanelNavigation et panelAjout
		this.add(scrollPane);


		/*********************************/
		/*	Activation des composants    */
		/*********************************/

		this.btnAdd.addActionListener(this);

		for(int i=0; i<this.tabBtn.length; i++)
		{
			this.tabBtn[i][1].addActionListener(this);
			this.tabBtn[i][2].addActionListener(this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.btnAdd) 
		{
			new FrameCreationQuestion(frame, ctrl);
			this.repaint();
		}

		for (int i = 0; i < this.tabBtn.length; i++) 
		{
			//supprime la notion de la ligne
			if (e.getSource() == this.tabBtn[i][1]) 
			{
				this.ctrl  .supprimerDossier(this.ressource.getNom() + File.separator + this.notion.getNom() + File.separator + "Question "+(i+1));
				this.notion.getLstQuestions().remove(i);
				this.frame .refreshQuestion(notion, this.ressource);
			}
			//modifie le nom de la notion de la ligne
			if (e.getSource() == this.tabBtn[i][2]) 
			{
				//new FrameModification(this.ctrl, this.frame ,"Notion", this.notion,this.lstQ.get(i));
			}
		}
	}
}
