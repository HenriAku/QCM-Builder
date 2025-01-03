package IHM;

import Controlleur.Controlleur;
import Metier.Question;
import Metier.QCM;
import Metier.Enlevement;
import Metier.Association;

import javax.swing.JFrame;

public class FrameAjoutQuestion extends JFrame
{
	private Controlleur ctrl;

	private Question question;

	private PanelAjoutQuestionQCM         panelAjoutQuestionQCM ;
	private PanelAjoutQuestionAsso        panelAjoutQuestionAsso;
	private PanelAjoutQuestionElimination panelAjoutQuestionElimination;

	public FrameAjoutQuestion(Controlleur ctrl, String type, Question question)
	{
		this.ctrl = ctrl;

		this.question = question;

		this.setTitle    ("Création question");
		this.setSize     (920,550);
		this.setLocation ( 350,200);
		this.setResizable(false);

		switch (type) 
		{
			case "question à choix multiple à réponse multiple": this.panelAjoutQuestionQCM = new PanelAjoutQuestionQCM(this.ctrl, true, question);
					    this.add(this.panelAjoutQuestionQCM);
				break;

			case "question à choix multiple à réponse unique": this.panelAjoutQuestionQCM = new PanelAjoutQuestionQCM(this.ctrl, false, question);
					    this.add(this.panelAjoutQuestionQCM);
				break;

			case "question à association d’éléments": this.panelAjoutQuestionAsso = new PanelAjoutQuestionAsso(this.ctrl, question);
				this.add(this.panelAjoutQuestionAsso);
				break;

			case "question avec élimination de propositions de réponses": this.panelAjoutQuestionElimination= new PanelAjoutQuestionElimination(this.ctrl, question);
				this.add(this.panelAjoutQuestionElimination);
				break;

		
			default:
				break;
		}

		if (this.question == null)
			this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		else
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void setParametres(String ressource, String notion, String type, String difficulte, String temps, double point)
	{
		switch (type) 
		{
			case "question à choix multiple à réponse multiple": this.panelAjoutQuestionQCM.setParametres(ressource, notion, type, difficulte, temps, point);
				break;
			case "question à choix multiple à réponse unique": this.panelAjoutQuestionQCM.setParametres(ressource, notion, type, difficulte, temps, point);
				break;
			case "question à association d’éléments": this.panelAjoutQuestionAsso.setParametres(ressource, notion, type, difficulte, temps, point);
				break;
			case "question avec élimination de propositions de réponses": this.panelAjoutQuestionElimination.setParametres(ressource, notion, type, difficulte, temps, point);
		
			default:
				break;
		}
	}

}
