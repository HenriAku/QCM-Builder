package IHM;

import Controlleur.Controlleur;

import java.util.ArrayList;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.rtf.RTFEditorKit;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class PanelAjoutQuestion extends JPanel implements ActionListener
{
	private Controlleur ctrl;

	private JPanel panelHaut;
	private JPanel panelCentre;
	private JPanel panelBas;

	private JEditorPane txtQuestion;

	private ArrayList<JTextField> lstTxtReponse;
	private ArrayList<JButton> lstBtnSupprimer;
	private ArrayList<JButton> lstBtnReponseValide;

	private JScrollPane scrollPane;

	private JButton btnAjouter;
	private JButton btnExplication;
	private JButton btnEnregistrer;

	private ArrayList<JPanel> lstPanelReponse;

	private FrameFeedBack frameFeedBack;

	public PanelAjoutQuestion(Controlleur ctrl)
	{
		this.ctrl = ctrl;

		this.setLayout(new BorderLayout());
		
		/* Panel Haut */
		this.txtQuestion = new JEditorPane();
		this.txtQuestion.setEditorKit(new RTFEditorKit());
		this.txtQuestion.setMargin(new java.awt.Insets(5, 5, 5, 5)); // Ajout de marges pour l'esthétique

		/* Panel Bas */
		this.lstTxtReponse = new ArrayList<JTextField>();
		this.lstTxtReponse.add(new JTextField());
		this.lstTxtReponse.get(0).setMargin(new java.awt.Insets(5, 5, 5, 5));
		this.lstTxtReponse.add(new JTextField());
		this.lstTxtReponse.get(1).setMargin(new java.awt.Insets(5, 5, 5, 5));
		this.lstTxtReponse.add(new JTextField());
		this.lstTxtReponse.add(new JTextField());

		this.lstBtnSupprimer = new ArrayList<JButton>();
		this.lstBtnReponseValide = new ArrayList<JButton>();

		/* Ajout éléments panel haut */
		JScrollPane scrollPaneHaut = new JScrollPane(this.txtQuestion);
		scrollPaneHaut.setPreferredSize(new Dimension(200, 20));

		this.panelHaut = new JPanel();
		this.panelHaut.setLayout(new GridLayout(2, 1));
		this.panelHaut.setPreferredSize(new Dimension(200, 200));

		this.panelHaut.add(new JLabel("Question"));
		this.panelHaut.add(scrollPaneHaut);


		/* Ajout éléments panel centre */
		this.panelCentre = new JPanel();
		this.panelCentre.setLayout(new GridLayout(0, 1, 20, 20));

		this.lstPanelReponse = new ArrayList<JPanel>();
		for (int i=0; i<this.lstTxtReponse.size(); i++)
		{
			JPanel panelReponse = new JPanel();
			panelReponse.setLayout(new BorderLayout(10, 10));

			this.lstBtnSupprimer.add(new JButton(new ImageIcon("QCM Builder" + File.separator + "img" + File.separator + "LogoSuppr.png")));
			this.lstBtnReponseValide.add(new JButton(""));
			this.lstBtnReponseValide.get(i).setPreferredSize(new Dimension(25,25));

			this.lstBtnSupprimer.get(i).addActionListener(this);
			this.lstBtnReponseValide.get(i).addActionListener(this);

			panelReponse.add(this.lstBtnSupprimer    .get(i), BorderLayout.WEST);
			panelReponse.add(this.lstTxtReponse      .get(i), BorderLayout.CENTER);
			panelReponse.add(this.lstBtnReponseValide.get(i), BorderLayout.EAST);

			this.lstPanelReponse.add(panelReponse);
			this.panelCentre.add(panelReponse);
		}
		
		this.scrollPane = new JScrollPane(this.panelCentre);

		/* Panel Bas */
		this.panelBas = new JPanel();
		this.panelBas.setLayout(new FlowLayout(FlowLayout.LEFT));

		this.btnAjouter = new JButton(new ImageIcon("QCM Builder" + File.separator + "img" + File.separator + "Ajout.png"));
		this.btnAjouter.setBorderPainted(false);
		this.btnAjouter.setContentAreaFilled(false);
		this.btnAjouter.addActionListener(this);

		this.btnExplication = new JButton(new ImageIcon("QCM Builder" + File.separator + "img" + File.separator + "LogoModif.png"));
		this.btnExplication.setBorderPainted(false);
		this.btnExplication.setContentAreaFilled(false);
		this.btnExplication.addActionListener(this);

		this.btnEnregistrer = new JButton("Enregistrer");
		this.btnEnregistrer.addActionListener(this);

		this.panelBas.add(new JLabel());
		this.panelBas.add(this.btnAjouter);
		this.panelBas.add(this.btnExplication);
		this.panelBas.add(this.btnEnregistrer);

		this.add(this.panelHaut, BorderLayout.NORTH);
		this.add(this.scrollPane, BorderLayout.CENTER);
		this.add(this.panelBas, BorderLayout.SOUTH);
	}

	public void majIHM()
	{
		this.panelCentre.revalidate();
		this.panelCentre.repaint();
	}

	public void supprimerReponse(int indice)
	{
		this.lstBtnSupprimer.remove(indice);
		this.lstTxtReponse.remove(indice);
		this.lstBtnReponseValide.remove(indice);
		
		this.panelCentre.remove(this.lstPanelReponse.get(indice));
		this.lstPanelReponse.remove(indice);
		this.majIHM();
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this.btnAjouter))
		{
			this.lstBtnSupprimer.add(new JButton(new ImageIcon("QCM Builder" + File.separator + "img" + File.separator + "LogoSuppr.png")));
			this.lstTxtReponse.add(new JTextField(""));
			this.lstTxtReponse.get(this.lstTxtReponse.size()-1).setMargin(new java.awt.Insets(5, 5, 5, 5));
			this.lstBtnReponseValide.add(new JButton(""));
			this.lstBtnReponseValide.get(this.lstBtnSupprimer.size()-1).setPreferredSize(new Dimension(25,25));

			this.lstBtnSupprimer.get(this.lstBtnSupprimer.size()-1).addActionListener(this);
			this.lstBtnReponseValide.get(this.lstBtnReponseValide.size()-1).addActionListener(this);

			JPanel panelReponse = new JPanel();
			panelReponse.setLayout(new BorderLayout(10, 10));
	
			panelReponse.add(this.lstBtnSupprimer    .get(this.lstBtnSupprimer    .size()-1), BorderLayout.WEST);
			panelReponse.add(this.lstTxtReponse      .get(this.lstTxtReponse      .size()-1), BorderLayout.CENTER);
			panelReponse.add(this.lstBtnReponseValide.get(this.lstBtnReponseValide.size()-1), BorderLayout.EAST);
	
			this.panelCentre.add(panelReponse);
			this.lstPanelReponse.add(panelReponse);
			this.majIHM();
		}
		if (e.getSource().equals(this.btnExplication))
		{
			// Ouvre une frame pour les explications de la question
			this.frameFeedBack.setVisible(true);
		}
		if (e.getSource().equals(this.btnEnregistrer))
		{
			// Appelle controlleur ajoute la question
			//this.ctrl.
		}
		
		for (int i=0; i<this.lstBtnSupprimer.size(); i++)
		{
			if (e.getSource().equals(this.lstBtnSupprimer.get(i)))
			{
				this.supprimerReponse(i);
				break;
			}
		}

		for (JButton btnRepValide:this.lstBtnReponseValide)
		{
			if (e.getSource().equals(btnRepValide))
			{
				// Met une image de fleche verte dans le btn
				// Rend la reponse valide
				if (btnRepValide.getIcon() == null)
					btnRepValide.setIcon(new ImageIcon("QCM Builder" + File.separator + "img" + File.separator + "LogoValide.png"));
				else
					btnRepValide.setIcon(null);
			}
		}
	}

}