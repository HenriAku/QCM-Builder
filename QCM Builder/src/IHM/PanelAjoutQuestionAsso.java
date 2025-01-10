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

import Controlleur.Controlleur;
import Metier.Association;
import Metier.Question;
import Metier.ReponseAsso;

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
	private JPanel      panelCentre;
	private String 		pathFile   ;


	private JLabel      lblFilAriane;
	private JEditorPane txtQuestion ;

	private ArrayList<JTextField> lstTxtReponseG ;
	private ArrayList<JTextField> lstTxtReponseD ;
	private ArrayList<JButton>    lstBtnSupprimer;

	private JScrollPane scrollPane;

	private JButton btnAjouter    ;
	private JButton btnExplication;
	private JButton btnEnregistrer;
	private JButton btnImage      ;

	private ArrayList<JPanel> lstPanelReponse;

	private FrameFeedBack      frameFeedBack     ;
	private FrameAjoutQuestion frameAjoutQuestion;
	private FrameAddFile       frameFile         ;

	private Association question;

	private boolean estCreeDepuisRessource;

	public PanelAjoutQuestionAsso(Controlleur ctrl, Question question, FrameAjoutQuestion frameAjoutQuestion, boolean estCreeDepuisRessource)
	{
		this.setLayout(new BorderLayout());

		this.pathFile = null;
		this.ctrl     = ctrl;

		this.estCreeDepuisRessource = estCreeDepuisRessource;

		if (question != null)
			this.question = (Association)(question);
		
		this.frameFeedBack = new FrameFeedBack(question);
		this.frameAjoutQuestion = frameAjoutQuestion;

		/* Panel Haut */
		this.txtQuestion = new JEditorPane();
		this.txtQuestion.setMargin(new java.awt.Insets(5, 5, 5, 5)); // Ajout de marges pour l'esthétique
		if (question != null)
			this.txtQuestion.setText(this.question.getQuestion());
		
		this.lblFilAriane = new JLabel("Question");

		/* Panel Bas */
		this.lstTxtReponseD  = new ArrayList<JTextField>();
		this.lstTxtReponseG  = new ArrayList<JTextField>();
		this.lstBtnSupprimer = new ArrayList<JButton>   ();

		/* Ajout éléments panel haut */
		JScrollPane scrollPaneHaut = new JScrollPane(this.txtQuestion);
		scrollPaneHaut.setPreferredSize(new Dimension(200, 20));

		JPanel panelHaut = new JPanel();
		panelHaut.setLayout(new GridLayout(2, 1));
		panelHaut.setPreferredSize(new Dimension(200, 200));

		panelHaut.add(this.lblFilAriane);
		panelHaut.add(scrollPaneHaut);


		/* Ajout éléments panel centre */
		this.panelCentre = new JPanel();
		this.panelCentre.setLayout(new GridLayout(0, 1, 20, 20));

		this.lstPanelReponse = new ArrayList<JPanel>();

		if (question == null)
		{
			for(int i=0; i<2; i++)
			{
				JPanel panelReponse = new JPanel();
				panelReponse.setLayout(new GridLayout(1, 4, 5, 5));

				this.lstBtnSupprimer.add(new JButton(new ImageIcon(".." + File.separator + "img" + File.separator + "LogoSuppr.png")));
				this.lstBtnSupprimer.get(i).setContentAreaFilled(false);
				this.lstBtnSupprimer.get(i).setBorderPainted    (false);
				this.lstBtnSupprimer.get(i).setFocusPainted     (false);
				this.lstBtnSupprimer.get(i).addActionListener   (this   );
		
				this.lstTxtReponseG  .add(new JTextField());
				this.lstTxtReponseG  .get(i).setMargin(new java.awt.Insets(5, 5, 5, 5));
		
				this.lstTxtReponseD  .add(new JTextField());
				this.lstTxtReponseD  .get(i).setMargin(new java.awt.Insets(5, 5, 5, 5));
		
		
				panelReponse.add(this.lstBtnSupprimer.get(i));
				panelReponse.add(this.lstTxtReponseG .get(i));
				panelReponse.add(this.lstTxtReponseD .get(i));
				this.lstPanelReponse.add(panelReponse);
				this.panelCentre    .add(panelReponse);
			}
		}
		else
		{
			ArrayList<ReponseAsso> lstReponse = this.question.getlstRepPasMelanger();
			ArrayList<ReponseAsso> lstTxtG = new ArrayList<ReponseAsso>();
			ArrayList<ReponseAsso> lstTxtD = new ArrayList<ReponseAsso>();

			for (int i=0; i<lstReponse.size(); i++)
			{
				if (! lstTxtG.contains(lstReponse.get(i)) && ! lstTxtD.contains(lstReponse.get(i)))
				{
					System.out.println("test rep asso");
					lstTxtG.add(lstReponse.get(i));
					lstTxtD.add(lstReponse.get(i).getAssocie());
				}
			}

			int limiteBoucle = lstTxtG.size();
			if (lstTxtD.size() > limiteBoucle)
				limiteBoucle = lstTxtD.size();

			for (int i=0; i<limiteBoucle; i++)
			{
				JPanel panelReponse = new JPanel();
				panelReponse.setLayout(new GridLayout(1, 4, 5, 5));

				this.lstBtnSupprimer.add(new JButton(new ImageIcon(".." + File.separator + "img" + File.separator + "LogoSuppr.png")));
				this.lstBtnSupprimer.get(this.lstBtnSupprimer.size()-1).setContentAreaFilled(false);
				this.lstBtnSupprimer.get(this.lstBtnSupprimer.size()-1).setBorderPainted    (false);
				this.lstBtnSupprimer.get(this.lstBtnSupprimer.size()-1).setFocusPainted     (false);
				this.lstBtnSupprimer.get(this.lstBtnSupprimer.size()-1).addActionListener   (this   );

				this.lstTxtReponseG.add(new JTextField(lstTxtG.get(i).getReponse()));
				this.lstTxtReponseG.get(this.lstTxtReponseG.size()-1).setMargin(new java.awt.Insets(5, 5, 5, 5));

				this.lstTxtReponseD.add(new JTextField(lstTxtD.get(i).getReponse()));
				this.lstTxtReponseD.get(this.lstTxtReponseD.size()-1).setMargin(new java.awt.Insets(5, 5, 5, 5));

				panelReponse.add(this.lstBtnSupprimer.get(i));
				panelReponse.add(this.lstTxtReponseG .get(i));
				panelReponse.add(this.lstTxtReponseD .get(i));
				this.lstPanelReponse.add(panelReponse);
			}
		}

		for (JPanel panelRep:this.lstPanelReponse)
			this.panelCentre.add(panelRep);

		this.scrollPane = new JScrollPane(this.panelCentre);
		
		/* Panel Bas */
		JPanel panelBas = new JPanel();
		panelBas.setLayout(new FlowLayout(FlowLayout.LEFT));

		this.btnAjouter = new JButton(new ImageIcon(".." + File.separator + "img" + File.separator + "Ajout.png"));
		this.btnAjouter.setBorderPainted    (false);
		this.btnAjouter.setContentAreaFilled(false);
		this.btnAjouter.addActionListener   (this   );

		this.btnExplication = new JButton("FeedBack");
		this.btnExplication.addActionListener   (this   );

		this.btnEnregistrer = new JButton("Enregistrer");
		this.btnEnregistrer.addActionListener(this);

		this.btnImage = new JButton(new ImageIcon(".." + File.separator + "img" + File.separator + "Upload.png"));
		this.btnImage.setBorderPainted    (false);
		this.btnImage.setContentAreaFilled(false);
		this.btnImage.addActionListener   (this   );

		panelBas.add(new JLabel("Ajouter réponse :"));
		panelBas.add(this.btnAjouter    );
		panelBas.add(this.btnExplication);
		panelBas.add(this.btnEnregistrer);
		panelBas.add(this.btnImage      );


		this.add(panelHaut      , BorderLayout.NORTH );
		this.add(this.scrollPane, BorderLayout.CENTER);
		this.add(panelBas       , BorderLayout.SOUTH );
	}

	public void majIHM()
	{
		this.panelCentre.revalidate();
		this.panelCentre.repaint();
	}

	public void supprimerReponse(int indice)
	{
		this.lstBtnSupprimer.remove(indice);
		this.lstTxtReponseG .remove(indice);
		this.lstTxtReponseD .remove(indice);
		
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

		this.setTextEnnonce();
	}

	public void setTextEnnonce()
	{
		//Affiche la ressource et la notion dans laquelle va être crée la question
		this.lblFilAriane.setText("<html>Ressource : " + this.ressource + " | Notion : " + this.notion + "<br>Question :</html>");
		this.repaint();
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this.btnImage))
		{
			this.frameFile = new FrameAddFile(this.ctrl, (Question)(this.question), this.ressource, this.notion, this.question != null);
		}

		if (e.getSource().equals(this.btnAjouter))
		{
			JPanel panelReponse = new JPanel();
			panelReponse.setLayout(new GridLayout(1, 4, 5, 5));
	
			this.lstBtnSupprimer.add(new JButton(new ImageIcon(".."+ File.separator +"img"+File.separator+ "LogoSuppr.png")));
			this.lstBtnSupprimer.get(this.lstBtnSupprimer.size()-1).setContentAreaFilled(false);
			this.lstBtnSupprimer.get(this.lstBtnSupprimer.size()-1).setBorderPainted    (false);
			this.lstBtnSupprimer.get(this.lstBtnSupprimer.size()-1).setFocusPainted     (false);
			
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
			//Ouvre une frame pour les explications de la question
			this.frameFeedBack.setVisible(true);
		}

		if (e.getSource().equals(this.btnEnregistrer))
		{
			// Appelle controlleur ajoute la question
			ArrayList<String> lstS = new ArrayList<String>();
			if (this.lstTxtReponseG.isEmpty() || this.lstTxtReponseD.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Aucune réponse n'a été ajoutée !", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (this.lstTxtReponseG.size() != this.lstTxtReponseD.size()) {
				JOptionPane.showMessageDialog(null, "Le nombre de réponses gauche et droite ne correspond pas !", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			for (int i = 0; i < this.lstTxtReponseG.size(); i++)
				lstS.add(this.lstTxtReponseG.get(i).getText());

			for (int i = 0; i < this.lstTxtReponseD.size(); i++)
				lstS.add(this.lstTxtReponseD.get(i).getText());


			if(frameFile != null &&frameFile.getPath()!=null)
				this.pathFile = frameFile.getPath();

			String erreur = "";
			if (this.question == null)
			{
				if(this.pathFile != null)
					erreur = this.ctrl.creerQuestionAsso(this.ressource, this.notion, this.txtQuestion.getText(), this.type, this.frameFeedBack.getFeedback(), this.difficulte, this.point, this.temps, lstS, this.pathFile);
				else	
					erreur = this.ctrl.creerQuestionAsso(this.ressource, this.notion, this.txtQuestion.getText(), this.type, this.frameFeedBack.getFeedback(), this.difficulte, this.point, this.temps, lstS, null);
			}	
			else	
			{
				if (this.pathFile != null)
					erreur = this.ctrl.modifQuestionAsso(this.ressource, this.notion, this.txtQuestion.getText(), this.question.getType(), this.frameFeedBack.getFeedback(), this.difficulte, this.question.getPoint(), String.valueOf(this.question.getTemps()), lstS, this.question, this.pathFile);
				else
					erreur = this.ctrl.modifQuestionAsso(this.ressource, this.notion, this.txtQuestion.getText(), this.question.getType(), this.frameFeedBack.getFeedback(), this.difficulte, this.question.getPoint(), String.valueOf(this.question.getTemps()), lstS, this.question, null);
			}		
			

			if (erreur.length() > 0)
			{
				JOptionPane.showMessageDialog(null, erreur, "Erreur", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				if (this.question == null)
					JOptionPane.showMessageDialog(null, "La question à été crée"  , "Question crée"   , JOptionPane.INFORMATION_MESSAGE);
				else
				{
					JOptionPane.showMessageDialog(null, "La question à été modifé", "Question modifié", JOptionPane.INFORMATION_MESSAGE);
				}
				if (estCreeDepuisRessource)
				{
					this.frameAjoutQuestion.dispose();
					this.ctrl.majPanelQuestion();
				}

			}
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
