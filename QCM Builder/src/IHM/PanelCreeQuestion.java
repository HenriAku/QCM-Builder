/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package IHM;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import IHM.FramePrincipal;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Controlleur.Controlleur;

import javax.swing.JOptionPane;


public class PanelCreeQuestion extends JPanel implements ActionListener
{
	private JTextField txtNbPoints;
	private JTextField txtTempsReponse;

	private JComboBox<String> ddlstRessource;
	private JComboBox<String> ddlstNotion;

	private JRadioButton rbQcmUnique;
	private JRadioButton rbQcmMulti;
	private JRadioButton rbAssociation;
	private JRadioButton rbElimination;

	private ButtonGroup btgTypeQuestion;

	private JButton btnCreeQuestion;

	private Graphics2D g2;

	private int btnCouleurSelectionner;

	private boolean boutonCouleurEditable;

	private FrameAjoutQuestion frameAjoutQuestion;

	private Controlleur    ctrl ;

	public PanelCreeQuestion(Controlleur ctrl, FramePrincipal frame)
	{
		this.setLayout(new GridLayout(5,1));

		this.ctrl  = ctrl ;
		this.frameAjoutQuestion = null;

		this.btnCouleurSelectionner = -1;
		this.boutonCouleurEditable  = false;

		/* Création Objets */
		// Partie 1
		this.txtNbPoints = new JTextField(4);
		this.txtTempsReponse = new JTextField(9);

		// Partie2
		this.ddlstRessource = new JComboBox<>(this.ctrl.getNomRessources());
		this.ddlstRessource.setSelectedItem(null);
		this.ddlstNotion = new JComboBox<>();
		this.ddlstNotion.setEnabled(false); // Désactivé par défaut

		// Partie 3
		this.rbQcmUnique   = new JRadioButton("question à choix multiple à réponse unique"           );
		this.rbQcmMulti    = new JRadioButton("question à choix multiple à réponse multiple"         );
		this.rbAssociation = new JRadioButton("question à association d’éléments"                    );
		this.rbElimination = new JRadioButton("question avec élimination de propositions de réponses");

		this.btgTypeQuestion = new ButtonGroup();
		this.btgTypeQuestion.add(this.rbQcmUnique  );
		this.btgTypeQuestion.add(this.rbQcmMulti   );
		this.btgTypeQuestion.add(this.rbAssociation);
		this.btgTypeQuestion.add(this.rbElimination);

		// Partie 4
		this.btnCreeQuestion = new JButton("Créer");

		/* Ajouts */
		// Partie 1
		JPanel panelTexte = new JPanel();
		panelTexte.setLayout(new GridLayout(2,4));

		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel1.add(new JLabel("nombre de points"));

		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel2.add(new JLabel("temps de réponse (min:sec)"));

		
		JPanel panel3 = new JPanel();
		panel3.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel3.add(this.txtNbPoints);
		
		JPanel panel4 = new JPanel();
		panel4.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel4.add(this.txtTempsReponse);


		panelTexte.add(panel1);
		panelTexte.add(panel2);

		panelTexte.add(new JLabel());
		panelTexte.add(new JLabel());

		panelTexte.add(panel3);
		panelTexte.add(panel4);

		panelTexte.add(new JLabel());
		panelTexte.add(new JLabel());

		// Partie 2
		JPanel panelRessouce = new JPanel();
		panelRessouce.setLayout(new GridLayout(2, 3, 15, 15));

		panelRessouce.add(new JLabel("ressource"));
		panelRessouce.add(new JLabel("notion"));
		panelRessouce.add(new JLabel("niveau"));

		panelRessouce.add(this.ddlstRessource);
		panelRessouce.add(this.ddlstNotion);
		JLabel lblVide = new JLabel();
		lblVide.setOpaque(false);
		panelRessouce.add(lblVide);

		panelRessouce.setOpaque(false);

		// Partie 3
		JPanel panelTypeQuestion = new JPanel();
		panelTypeQuestion.setLayout(new GridLayout(2, 4));

		panelTypeQuestion.add(this.rbQcmUnique);
		panelTypeQuestion.add(this.rbQcmMulti);
		panelTypeQuestion.add(this.rbAssociation);
		panelTypeQuestion.add(this.rbElimination);

		// Partie 4
		JPanel panelBtnCree = new JPanel();
		panelBtnCree.add(this.btnCreeQuestion);

		this.add(panelTexte);
		this.add(panelRessouce);
		this.add(panelTypeQuestion);
		this.add(new JLabel());
		this.add(panelBtnCree);

		// Activation des composants
		this.addMouseListener(new GereSouris());
		this.ddlstRessource .addActionListener(this);
		this.ddlstNotion    .addActionListener(this);
		this.btnCreeQuestion.addActionListener(this);
	}

	public String[] getRessources()
	{
		String[] tabRessources = new String[3];
		tabRessources[0] = "R1.01 Dev";
		tabRessources[1] = "R1.02 Bado";
		tabRessources[2] = "R1.03 Prog Sys";

		return tabRessources;
	}

	public String[] getNotions()
	{
		String[] tabNotions = new String[3];
		tabNotions[0] = "Jointure";
		tabNotions[1] = "Test";
		tabNotions[2] = "TestTest";

		return tabNotions;
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.ddlstRessource) 
		{
			if (this.ddlstRessource.getSelectedItem() != null) 
			{
				// Récupérer les notions correspondant à la ressource sélectionnée
				String ressourceSelectionnee = (String) this.ddlstRessource.getSelectedItem();
				String[] notions = this.ctrl.getNomNotion(ressourceSelectionnee);
		
				// Mettre à jour le JComboBox des notions
				this.ddlstNotion.removeAllItems(); // Vider les anciennes notions
				for (String notion : notions) 
				{
					this.ddlstNotion.addItem(notion); // Ajouter chaque notion
				}
		
				// Activer le JComboBox des notions
				this.ddlstNotion.setEnabled(true);
			} else 
			{
				// Désactiver le JComboBox des notions si aucune ressource n'est sélectionnée
				this.ddlstNotion.removeAllItems();
				this.ddlstNotion.setEnabled(false);
			}
		}
		if (e.getSource() == this.ddlstNotion)
		{
			if (this.ddlstRessource.getSelectedItem() != null)
			{
				this.boutonCouleurEditable = true;
				this.repaint();
			}
		}
		if (e.getSource() == this.btnCreeQuestion)
		{
			String ressource = (String)(this.ddlstRessource.getSelectedItem());
			String notion    = (String)(this.ddlstNotion   .getSelectedItem());
			String type      = "";
			String difficulte = "";
			double point = -1;

			switch (this.btnCouleurSelectionner)
			{
				case 1:
					difficulte = "Tres Facile";
					break;
				
				case 2:
					difficulte = "Facile";
					break;

				case 3:
					difficulte = "Moyenne";
					break;

				case 4:
					difficulte = "Difficile";
					break;
			
				default:
					break;
			}
			try
			{
				point = Double.parseDouble(this.txtNbPoints.getText());
			} catch (Exception err) {}

			String temps = this.txtTempsReponse.getText();
			
			if (this.rbQcmUnique  .isSelected()) type = this.rbQcmUnique  .getText();
			if (this.rbQcmMulti   .isSelected()) type = this.rbQcmMulti   .getText();
			if (this.rbAssociation.isSelected()) type = this.rbAssociation.getText();
			if (this.rbElimination.isSelected()) type = this.rbElimination.getText();
			


			String erreur = this.ctrl.validerQuestion(ressource, notion, type, difficulte, point, temps);
			System.out.println(erreur);
			if (erreur.length() == 0)
			{
				this.frameAjoutQuestion = new FrameAjoutQuestion(this.ctrl, type);
				this.frameAjoutQuestion.setParametres(ressource, notion, type, difficulte, temps, point);
				this.frameAjoutQuestion.setVisible(true);
			}
			else
				JOptionPane.showMessageDialog(null, erreur, "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void paintComponent(Graphics g)
	{
		// Dessine les ronds de couleur pour le niveau des questions
		super.paintComponent(g);

		g2 = (Graphics2D) g;

		// Dessin des ronds
		g2.setColor(Color.GREEN);
		if (! this.boutonCouleurEditable) g2.setColor(new Color(185, 225, 185));
		g2.fillOval(620, 95, 25, 25);

		g2.setColor(Color.BLUE);
		if (! this.boutonCouleurEditable) g2.setColor(new Color(185, 190, 225));
		g2.fillOval(660, 95, 25, 25);

		g2.setColor(Color.RED);
		if (! this.boutonCouleurEditable) g2.setColor(new Color(225, 185, 185));
		g2.fillOval(700, 95, 25, 25);

		g2.setColor(Color.GRAY);
		if (! this.boutonCouleurEditable) g2.setColor(new Color(211, 211, 211));
		g2.fillOval(740, 95, 25, 25);

		// Dessin du texte (TF, F, M, D)
		if (this.boutonCouleurEditable)
		{
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("Arial", Font.PLAIN, 13));
			
			g2.drawString("TF", 626, 113);
			g2.drawString("F" , 670, 113);
			g2.drawString("M" , 708, 113);
			g2.drawString("D" , 749, 113);
		}

		// Dessin de la selection du bouton
		if (this.btnCouleurSelectionner != -1)
		{
			switch (this.btnCouleurSelectionner)
			{
				case 1:
					g2.setColor(new Color(51, 186, 0));
					break;
				
				case 2:
					g2.setColor(new Color(0,60, 200));
					break;
				
				case 3:
					g2.setColor(new Color(186, 0, 0));
					break;
				
				case 4:
					g2.setColor(new Color(97, 97, 97));
					break;
			}
			g2.setStroke(new BasicStroke(4));
			g2.drawOval(620 + 40 * (this.btnCouleurSelectionner - 1), 95, 25, 25);
			g2.setStroke(new BasicStroke(1));

		}
	}

	private class GereSouris extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			if (PanelCreeQuestion.this.boutonCouleurEditable)
			{
				// Bouton Vert
				if (e.getX() >= 620 && e.getX() <= 640 && e.getY() >= 95 && e.getY() <= 117)
				{
					PanelCreeQuestion.this.btnCouleurSelectionner = 1;
				}

				// Bouton Bleue
				if (e.getX() >= 660 && e.getX() <= 680 && e.getY() >= 95 && e.getY() <= 117)
				{
					PanelCreeQuestion.this.btnCouleurSelectionner = 2;
				}

				// Bouton Rouge
				if (e.getX() >= 700 && e.getX() <= 720 && e.getY() >= 95 && e.getY() <= 117)
				{
					PanelCreeQuestion.this.btnCouleurSelectionner = 3;
				}

				// Bouton Gris
				if (e.getX() >= 740 && e.getX() <= 760 && e.getY() >= 95 && e.getY() <= 117)
				{
					PanelCreeQuestion.this.btnCouleurSelectionner = 4;
				}
			}


			PanelCreeQuestion.this.repaint();
		}
	}
}
