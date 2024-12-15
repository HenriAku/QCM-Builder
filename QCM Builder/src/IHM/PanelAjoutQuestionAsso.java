package IHM;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.rtf.RTFEditorKit;

import Controlleur.Controlleur;
import Metier.Ressource;

import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class PanelAjoutQuestionAsso extends JPanel implements ActionListener
{
	private String      ressource  ;
	private String      notion     ;
	private String      type       ;
	private String      difficulte ;
	private String      temps      ;
	private double      point      ;
	private Controlleur ctrl       ;
	private JPanel 		panelCentre;

	private JEditorPane txtQuestion;

	private ArrayList<JTextField> lstTxtReponseG;
	private ArrayList<JTextField> lstTxtReponseD;
	private ArrayList<JButton> lstBtnSupprimer;

	private JScrollPane scrollPane;

	private JButton btnAjouter;
	private JButton btnExplication;
	private JButton btnEnregistrer;

	private ArrayList<JPanel> lstPanelReponse;

	private FrameFeedBack frameFeedBack;

	public PanelAjoutQuestionAsso(Controlleur ctrl)
	{
		this.setLayout(new BorderLayout());

		this.ctrl = ctrl;
		
		this.frameFeedBack = new FrameFeedBack();

		/* Panel Haut */
		this.txtQuestion = new JEditorPane();
		this.txtQuestion.setMargin(new java.awt.Insets(5, 5, 5, 5)); // Ajout de marges pour l'esthétique

		/* Panel Bas */
		this.lstTxtReponseD = new ArrayList<JTextField>();
		this.lstTxtReponseG = new ArrayList<JTextField>();
		this.lstBtnSupprimer = new ArrayList<JButton>();

		/* Ajout éléments panel haut */
		JScrollPane scrollPaneHaut = new JScrollPane(this.txtQuestion);
		scrollPaneHaut.setPreferredSize(new Dimension(200, 20));

		JPanel panelHaut = new JPanel();
		panelHaut.setLayout(new GridLayout(2, 1));
		panelHaut.setPreferredSize(new Dimension(200, 200));

		panelHaut.add(new JLabel("Question"));
		panelHaut.add(scrollPaneHaut);


		/* Ajout éléments panel centre */
		this.panelCentre = new JPanel();
		this.panelCentre.setLayout(new GridLayout(0, 1, 20, 20));

		this.lstPanelReponse = new ArrayList<JPanel>();

		JPanel panelReponse = new JPanel();
		panelReponse.setLayout(new GridLayout(1, 4, 5, 5));

		this.lstBtnSupprimer.add(new JButton(new ImageIcon("QCM Builder" + File.separator + "img" + File.separator + "LogoSuppr.png")));
		this.lstBtnSupprimer.get(0).addActionListener(this);

		this.lstTxtReponseG  .add(new JTextField());
		this.lstTxtReponseG  .get(0).setMargin(new java.awt.Insets(5, 5, 5, 5));

		this.lstTxtReponseD  .add(new JTextField());
		this.lstTxtReponseD  .get(0).setMargin(new java.awt.Insets(5, 5, 5, 5));


		panelReponse.add(this.lstBtnSupprimer.get(0));
		panelReponse.add(this.lstTxtReponseD .get(0));
		panelReponse.add(this.lstTxtReponseG .get(0));

		this.lstPanelReponse.add(panelReponse);
		this.panelCentre    .add(panelReponse);
		
		
		this.scrollPane = new JScrollPane(this.panelCentre);

		/* Panel Bas */
		JPanel panelBas = new JPanel();
		panelBas.setLayout(new FlowLayout(FlowLayout.LEFT));

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

		panelBas.add(new JLabel());
		panelBas.add(this.btnAjouter);
		panelBas.add(this.btnExplication);
		panelBas.add(this.btnEnregistrer);

		this.add(panelHaut      , BorderLayout.NORTH);
		this.add(this.scrollPane, BorderLayout.CENTER);
		this.add(panelBas       , BorderLayout.SOUTH);
	}

	public void majIHM()
	{
		this.panelCentre.revalidate();
		this.panelCentre.repaint();
	}

	public void supprimerReponse(int indice)
	{
		this.lstBtnSupprimer.remove(indice);
		this.lstTxtReponseD .remove(indice);
		this.lstTxtReponseG .remove(indice);
		
		this.panelCentre    .remove(this.lstPanelReponse.get(indice));
		this.lstPanelReponse.remove(indice);
		this.majIHM();
	}

	public void setParametres(String ressource, String notion, String type, String difficulte, String temps, double point)
	{
		this.ressource  = ressource;
		this.notion     = notion;
		this.type       = type;
		this.difficulte = difficulte;
		this.temps      = temps;
		this.point      = point;
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this.btnAjouter))
		{
			JPanel panelReponse = new JPanel();
			panelReponse.setLayout(new GridLayout(1, 4, 5, 5));
	
			this.lstBtnSupprimer.add(new JButton(new ImageIcon("QCM Builder" + File.separator + "img" + File.separator + "LogoSuppr.png")));
			this.lstTxtReponseG .add(new JTextField());
			this.lstTxtReponseD .add(new JTextField());

			int i = 0;
			if (!this.lstBtnSupprimer.isEmpty()) 
				i = this.lstBtnSupprimer.size()-1;

			this.lstBtnSupprimer.get(i).addActionListener(this);
			this.lstTxtReponseG .get(i).setMargin(new java.awt.Insets(5, 5, 5, 5));
			this.lstTxtReponseD .get(i).setMargin(new java.awt.Insets(5, 5, 5, 5));
	
			panelReponse.add(this.lstBtnSupprimer .get(this.lstBtnSupprimer.size()-1));
			panelReponse.add(this.lstTxtReponseG  .get(this.lstTxtReponseG .size()-1));
			panelReponse.add(this.lstTxtReponseD  .get(this.lstTxtReponseD .size()-1));
	
			this.lstPanelReponse.add(panelReponse);
			this.panelCentre    .add(panelReponse);
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
			ArrayList<String> lstS = new ArrayList<String>();
			System.out.println(this.lstTxtReponseG.size()+ "    test     " +this.lstTxtReponseD.size());
			for(int i=0; i<this.lstTxtReponseG.size()+this.lstTxtReponseD.size()-2; i = i+2)
			{
				lstS.add(this.lstTxtReponseG.get(i  ).getText());
				lstS.add(this.lstTxtReponseD.get(i+1).getText());
			}
			
			String erreur = this.ctrl.creerQuestionAsso(this.ressource, this.notion, this.txtQuestion.getText(), this.type, this.frameFeedBack.getFeedback(), this.difficulte, this.point, this.temps, lstS);
			if (erreur.length() > 0)
			{
				JOptionPane.showMessageDialog(null, erreur, "Erreur", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "La question à été crée", "Question crée", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		for (int i=0; i<this.lstBtnSupprimer.size(); i++)
		{
			if (e.getSource().equals(this.lstBtnSupprimer.get(i)))
			{
				System.out.println(i);
				this.supprimerReponse(i);
				break;
			}
		}
	}

}
