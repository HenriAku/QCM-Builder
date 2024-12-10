package IHM;

import javax.swing.*;

import Controlleur.Controlleur;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Metier.Chapitre;
import Metier.Ressource;

public class PanelChapitre extends JPanel implements ActionListener 
{
	private Controlleur ctrl  ;
	private JButton[][] tabBtn;
	private JButton     btnAdd;
	private ArrayList<Chapitre> lstQ;
	private Ressource ressource;
	private FramePrincipal frame;

	public PanelChapitre(Controlleur ctrl, Ressource ressource, FramePrincipal frame) 
	{
		this.ctrl = ctrl;
		this.frame = frame;
		this.ressource = ressource;
		this.lstQ = ressource.getChapitres();
		
		// Initialisation des boutons et positionnement
		tabBtn = new JButton[this.lstQ.size()][3];

		// Récupérer la taille de l'écran de l'utilisateur
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth  = screenSize.width;
		int screenHeight = screenSize.height;

		// Créer un panneau pour contenir tous les sous-panneaux
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null); // Disposition manuelle pour positionner les éléments
		contentPanel.setPreferredSize(new Dimension(screenWidth, this.lstQ.size() * 100)); // Hauteur dynamique selon le nombre de panneaux

		// Dimensions fixes pour chaque panneau et espacement
		int panelWidth  = 600;
		int panelHeight = 70 ;
		int spacing     = 20 ;

		// Dimensions des boutons
		int buttonSize = 30;

		// Calculer la position de départ pour centrer verticalement
		int totalHeight = (panelHeight + spacing) * this.lstQ.size() - spacing; // Hauteur totale
		int startY = (screenHeight - totalHeight) / 2;


		/*********************************/
		/*  Creation du panel ajout      */
		/*********************************/

		JPanel panelAjout = new JPanel();
		panelAjout.setLayout(null);
		panelAjout.setBounds((screenWidth - panelWidth) / 2, 20, panelWidth, panelHeight);
		
		JLabel lblTitre = new JLabel("Chapitre de " + ressource.getNom());
		lblTitre.setBounds(0, 10, panelWidth - 2 * (buttonSize + 30), panelHeight - 20);


		this.btnAdd = new JButton(new ImageIcon(
			new ImageIcon("QCM Builder\\\\img\\\\Ajout.png").getImage().getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH)));
		this.btnAdd.setBounds(150, 10,buttonSize, buttonSize);

		panelAjout.add(lblTitre   );
		panelAjout.add(this.btnAdd); 

		/*********************************/
		/*  Creation des panel ressource */
		/*********************************/

		for (int i = 0; i < this.lstQ.size(); i++) 
		{
			int posX = (screenWidth - panelWidth) / 2; // Centrer horizontalement
			int posY = startY + i * (panelHeight + spacing); // Calculer la position Y

			JPanel panelRes = new JPanel();
			panelRes.setLayout(null); // Layout nul pour le panneau
			panelRes.setBounds(posX, posY, panelWidth, panelHeight); // Position et taille du panneau
			contentPanel.add(panelRes);

			// Ajouter les boutons au panneau
			tabBtn[i][0] = new JButton(this.lstQ.get(i).getNom());
			tabBtn[i][0].setBounds(0, 10, panelWidth - 2 * (buttonSize + 30), panelHeight - 20); // Largeur ajustée pour le texte
			panelRes.add(tabBtn[i][0]);

			tabBtn[i][1] = new JButton(new ImageIcon(
				new ImageIcon("QCM Builder\\img\\LogoSuppr.png").getImage().getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH)));
			tabBtn[i][1].setBounds(panelWidth - 3 * buttonSize, 10, buttonSize, buttonSize);
			panelRes.add(tabBtn[i][1]);

			tabBtn[i][2] = new JButton(new ImageIcon(
				new ImageIcon("QCM Builder\\img\\LogoModif.png").getImage().getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH)));
			tabBtn[i][2].setBounds(panelWidth - buttonSize - 20, 10, buttonSize, buttonSize);
			panelRes.add(tabBtn[i][2]);
		}

		// Ajouter le panneau à un JScrollPane
		JScrollPane scrollPane = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 100, screenWidth, screenHeight - 100);


		/*********************************/
		/* positionnement des composants */
		/*********************************/
		this.setLayout(null);
		this.add(panelAjout);
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
			new FrameCreation(this.ctrl, "Chapitre",this.ressource.getNom(),this.frame);
			this.repaint();
		}

		for(int i=0; i<this.tabBtn.length; i++)
		{
			if (e.getSource() == this.tabBtn[i][0]) 
			{
				
			}
			if (e.getSource() == this.tabBtn[i][1]) 
			{
				System.out.println("Test Tab : " + i + "colonne : " + 1);    
			}
			if (e.getSource() == this.tabBtn[i][2]) 
			{
				System.out.println("Test Tab : " + i + "colonne : " + 2);    
			}
		}
	}
}
