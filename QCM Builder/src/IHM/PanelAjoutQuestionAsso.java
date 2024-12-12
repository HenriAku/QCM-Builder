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
import javax.swing.JPanel;
import javax.swing.text.rtf.RTFEditorKit;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class PanelAjoutQuestionAsso extends JPanel implements ActionListener
{
	private JPanel panelHaut;
	private JPanel panelCentre;
	private JPanel panelBas;

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

	public PanelAjoutQuestionAsso()
	{
		this.setLayout(new BorderLayout());
		
		this.frameFeedBack = new FrameFeedBack();

		/* Panel Haut */
		this.txtQuestion = new JEditorPane();
		this.txtQuestion.setEditorKit(new RTFEditorKit());
		this.txtQuestion.setMargin(new java.awt.Insets(5, 5, 5, 5)); // Ajout de marges pour l'esthétique

		/* Panel Bas */
		this.lstTxtReponseD = new ArrayList<JTextField>();
		this.lstTxtReponseG = new ArrayList<JTextField>();
		this.lstBtnSupprimer = new ArrayList<JButton>();

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

		JPanel panelReponse = new JPanel();
		panelReponse.setLayout(new GridLayout(1, 4, 5, 5));

		this.lstBtnSupprimer.add(new JButton(new ImageIcon("QCM Builder" + File.separator + "img" + File.separator + "LogoSuppr.png")));
		this.lstBtnSupprimer.get(0).addActionListener(this);

		this.lstTxtReponseG  .add(new JTextField());
		this.lstTxtReponseG  .get(0).setMargin(new java.awt.Insets(5, 5, 5, 5));

		this.lstTxtReponseD  .add(new JTextField());
		this.lstTxtReponseD  .get(1).setMargin(new java.awt.Insets(5, 5, 5, 5));


		panelReponse.add(this.lstBtnSupprimer.get(0));
		panelReponse.add(this.lstTxtReponseD .get(0));
		panelReponse.add(this.lstTxtReponseG .get(0));

		this.lstPanelReponse.add(panelReponse);
		this.panelCentre    .add(panelReponse);
		
		
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

		this.lstTxtReponseD.remove(indice);
		this.lstTxtReponseG.remove(indice);
		
		this.panelCentre    .remove(this.lstPanelReponse.get(indice-1));
		this.lstPanelReponse.remove(indice);
		this.majIHM();
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this.btnAjouter))
		{
			JPanel panelReponse = new JPanel();
			panelReponse.setLayout(new GridLayout(1, 4, 5, 5));
	
			this.lstBtnSupprimer.add(new JButton(new ImageIcon("QCM Builder" + File.separator + "img" + File.separator + "LogoSuppr.png")));
			this.lstBtnSupprimer.get(this.lstBtnSupprimer.size()-1).addActionListener(this);
			this.lstTxtReponseG .add(new JTextField());
			this.lstTxtReponseG .get(this.lstTxtReponseD.size()-1).setMargin(new java.awt.Insets(5, 5, 5, 5));
	
			this.lstTxtReponseD .add(new JTextField());
			this.lstTxtReponseD .get(this.lstTxtReponseG.size()-1).setMargin(new java.awt.Insets(5, 5, 5, 5));
	
			panelReponse.add(this.lstBtnSupprimer.get(this.lstBtnSupprimer.size()-1));
			panelReponse.add(this.lstTxtReponseG  .get(this.lstTxtReponseG.size()-1));
			panelReponse.add(this.lstTxtReponseD  .get(this.lstTxtReponseD.size()-1));
	
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
		}
		
		for (int i=0; i<this.lstBtnSupprimer.size(); i++)
		{
			if (e.getSource().equals(this.lstBtnSupprimer.get(i)))
			{
				this.supprimerReponse(i);
				break;
			}
		}

		
	}

}
