/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */

package Metier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Notion
{
	//changer les list pour une
	private String notion;

	private ArrayList<Question> lstQuestions;

	//////////////////
	// CONSTRUCTEUR //
	//////////////////
	
	public Notion(String notion, /*Ressource ressource,*/ ArrayList<Question> lstQuestions)
	{
		this.notion = notion;
		this.lstQuestions = lstQuestions;

			if (this.lstQuestions == null)
			this.lstQuestions = new ArrayList<Question>();

		Ecriture ef = new Ecriture("../ressources/" );
		ef.creerDossier(notion);
	}

	//////////////
	// QUESTION //
	//////////////
	

	/************************************/
	/* Ajout des question dans la list  */
	/************************************/
	public QCM ajouterQuestionQCM(String question, String explication, Difficulte difficulte, double point, float temps, ArrayList<ReponseQcm> lstReponses)
	{
		QCM q = new QCM(question, explication, difficulte, point, temps, lstReponses);
		this.addQuestion(q);
		return q;
	}

	public void addQuestion(Question q)
	{
		this.lstQuestions.add(q);
		Collections.sort(this.lstQuestions);
	}

	public Association ajouterQuestionAsso(String question, String explication, Difficulte difficulte, double point, float temps, ArrayList<ReponseAsso> lstReponses)
	{
		Association a = new Association(question, explication, difficulte, point, temps, lstReponses);
		this.addQuestion(a);
		return a;
	}

	public Enlevement ajouterQuestionEnleve(String question, String explication, Difficulte difficulte, double point, float temps, ArrayList<ReponseEnlevement> lstReponses)
	{
		Enlevement e = new Enlevement(question, explication, difficulte, point, temps, lstReponses);
		this.addQuestion(e);
		return e;
	}

	/** //TODO: Ptt a changer
	 * Supprime une question d'une list
	 * @param nom la question 
	 * @param difficulte de la question
	 */
	public void supprQuestion(Question question) {this.lstQuestions.remove(question);}

	// TODO: A Tester
	public ArrayList<Question> aleaQuestions(int nbQuestionTresFacile, int nbQuestionFacile, int nbQuestionMoyenne, int nbQuestionDifficile)
	{
		int idFinQuestionTresFacile = -1;
		int idFinQuestionFacile     = -1;
		int idFinQuestionMoyenne    = -1;

		for(int i=0; i<this.lstQuestions.size(); i++)
		{
			if (this.lstQuestions.get(i).getDifficulte().equals(Difficulte.F) && idFinQuestionTresFacile == -1)
			{
				idFinQuestionTresFacile = i-1;
			}
			if (this.lstQuestions.get(i).getDifficulte().equals(Difficulte.M) && idFinQuestionFacile     == -1)
			{
				idFinQuestionFacile = i-1;
			}
			if (this.lstQuestions.get(i).getDifficulte().equals(Difficulte.D) && idFinQuestionMoyenne    == -1)
			{
				idFinQuestionMoyenne = i-1;
			}
		}

		ArrayList<Question> lstQuestionsAlea = new ArrayList<Question>();

		for (int i=0; i<nbQuestionTresFacile; i++)
		{
			Question questionAlea = this.lstQuestions.get((int)(Math.random()*idFinQuestionTresFacile+1));

			boolean questionDejaDansLaListe = false;
			for (Question question:lstQuestionsAlea)
			{
				if (question.equals(questionAlea))
					questionDejaDansLaListe = true;
			}

			if (questionDejaDansLaListe)
				i--;
			else
				lstQuestionsAlea.add(questionAlea);
		}

		for (int i=0; i<nbQuestionFacile; i++)
		{
			Question questionAlea = this.lstQuestions.get(idFinQuestionTresFacile + 1 + (int)(Math.random()*(idFinQuestionFacile-idFinQuestionTresFacile)));

			boolean questionDejaDansLaListe = false;
			for (Question question:lstQuestionsAlea)
			{
				if (question.equals(questionAlea))
					questionDejaDansLaListe = true;
			}

			if (questionDejaDansLaListe)
				i--;
			else
				lstQuestionsAlea.add(questionAlea);
		}

		for (int i=0; i<nbQuestionMoyenne; i++)
		{
			Question questionAlea = this.lstQuestions.get(idFinQuestionFacile + 1 + (int)(Math.random()*(idFinQuestionMoyenne-idFinQuestionFacile)));

			boolean questionDejaDansLaListe = false;
			for (Question question:lstQuestionsAlea)
			{
				if (question.equals(questionAlea))
					questionDejaDansLaListe = true;
			}

			if (questionDejaDansLaListe)
				i--;
			else
				lstQuestionsAlea.add(questionAlea);
		}

		for (int i=0; i<nbQuestionDifficile; i++)
		{
			Question questionAlea = this.lstQuestions.get(idFinQuestionMoyenne + (int)(Math.random()*(this.lstQuestions.size()-idFinQuestionMoyenne)));

			boolean questionDejaDansLaListe = false;
			for (Question question:lstQuestionsAlea)
			{
				if (question.equals(questionAlea))
					questionDejaDansLaListe = true;
			}

			if (questionDejaDansLaListe)
				i--;
			else
				lstQuestionsAlea.add(questionAlea);
		}

		return lstQuestionsAlea;
	}

	//aleaQuestionsSimple qui prend un nombre de question et renvoie une liste de question aléatoire
	public ArrayList<Question> aleaQuestionsSimple(int nbQuestion)
	{
		ArrayList<Question> lstQuestionsAlea = new ArrayList<Question>();

		for (int i=0; i<nbQuestion; i++)
		{
			Question questionAlea = this.lstQuestions.get((int)(Math.random()*this.lstQuestions.size()));

			boolean questionDejaDansLaListe = false;
			for (Question question:lstQuestionsAlea)
			{
				if (question.equals(questionAlea))
					questionDejaDansLaListe = true;
			}

			if (questionDejaDansLaListe)
				i--;
			else
				lstQuestionsAlea.add(questionAlea);
		}

		return lstQuestionsAlea;
	}

	////////////
	// GETTER //
	////////////

	public String getNom() { return this.notion; }

	public int getNbQuestions()
	{
		return this.lstQuestions.size();
	} 

	//get nombre de question par difficulté
	public int getNbQuestionsTresFacile()
	{
		int nbQuestionsTresFacile = 0;
		for (Question question : this.lstQuestions)
		{
			if (question.getDifficulte().equals(Difficulte.TF))
				nbQuestionsTresFacile++;
		}
		return nbQuestionsTresFacile;
	}
	
	public int getNbQuestionsFacile()
	{
		int nbQuestionsFacile = 0;
		for (Question question : this.lstQuestions)
		{
			if (question.getDifficulte().equals(Difficulte.F))
				nbQuestionsFacile++;
		}
		return nbQuestionsFacile;
	}

	public int getNbQuestionsMoyenne()
	{
		int nbQuestionsMoyenne = 0;
		for (Question question : this.lstQuestions)
		{
			if (question.getDifficulte().equals(Difficulte.M))
				nbQuestionsMoyenne++;
		}
		return nbQuestionsMoyenne;
	}

	public int getNbQuestionsDifficile()
	{
		int nbQuestionsDifficile = 0;
		for (Question question : this.lstQuestions)
		{
			if (question.getDifficulte().equals(Difficulte.D))
				nbQuestionsDifficile++;
		}
		return nbQuestionsDifficile;
	}

	////////////
	// SETTER //
	////////////

	public void setNom(String nom) { this.notion = nom ; }

	////////////
	// STRING //
	////////////

	public String toString()
	{
		String str = "Notion : "+this.notion+"\n";
		for (Question question : this.lstQuestions)
		{
			str += question.toString()+"\n";
		}
		return str;
	}

	//affiche le notion et un résumé de ses questions
	public String afficherNotion()
	{
		String str = "Notion : "+this.notion+"\n";

		//affiche les questions tres facile
		str += "\tQuestions très facile : \n";
		//boucle sur le nombre de question tres facile
		for (int i = 0; i < this.getNbQuestionsTresFacile(); i++)
		{ 
			//affiche la question
			str += "\t"+this.lstQuestions.get(i).toString()+"\n";
		}

		//affiche les questions facile
		str += "\tQuestions facile : \n";
		//boucle sur le nombre de question facile
		for (int i = this.getNbQuestionsTresFacile(); i < this.getNbQuestionsTresFacile()+this.getNbQuestionsFacile(); i++)
		{
			//affiche la question
			str += "\t"+this.lstQuestions.get(i).toString()+"\n";
		}

		//affiche les questions moyenne
		str += "\tQuestions moyenne : \n";
		//boucle sur le nombre de question moyenne
		for (int i = this.getNbQuestionsTresFacile()+this.getNbQuestionsFacile(); i < this.getNbQuestionsTresFacile()+this.getNbQuestionsFacile()+this.getNbQuestionsMoyenne(); i++)
		{
			//affiche la question
			str += "\t"+this.lstQuestions.get(i).toString()+"\n";
		}

		//affiche les questions difficile
		str += "\tQuestions difficile : \n";
		//boucle sur le nombre de question difficile
		for (int i = this.getNbQuestionsTresFacile()+this.getNbQuestionsFacile()+this.getNbQuestionsMoyenne(); i < this.getNbQuestionsTresFacile()+this.getNbQuestionsFacile()+this.getNbQuestionsMoyenne()+this.getNbQuestionsDifficile(); i++)
		{
			//affiche la question
			str += "\t"+this.lstQuestions.get(i).toString()+"\n";
		}

		return str;
	}

	//renvoi la ressource contenant la notion en verifiants touts les ressources

	//affiche le notion et ses questions
	/*public String afficherNotion()
	{
		String str = "Notion : "+this.notion+"\n";

		str += "Questions Faciles : \n";
		for (Question question : this.lstQuestionsFacile) 
		{
			str += question.toString()+"\n";
		}

		str += "Questions Moyennes : \n";
		for (Question question : this.lstQuestionsMoyenne) 
		{
			str += question.toString()+"\n";
		}

		str += "Questions Difficiles : \n";
		for (Question question : this.lstQuestionsDifficile) 
		{
			str += question.toString()+"\n";
		}

		str += "Questions Très Faciles : \n";
		for (Question question : this.lstQuestionsTresFacile) 
		{
			str += question.toString()+"\n";
		}

		return str;

	} */

	/////////
	// CUI //
	/////////

	
	//methode creerNotion en demandant les informations à l'utilisateur
	public static Notion creerNotion(Scanner sc)
	//"sc" utilisation de scanner pour récupérer les informations de l'utilisateur en version console
	{
		// Demande le nom du notion
		System.out.print("Entrez le nom du notion : ");
		String notion = sc.nextLine();

		ArrayList<Question> lstQuestions = new ArrayList<Question>();
		
		//demander si on creer la liste de question tant que la réponse n'est pas oui ou non
		String reponse;
		do
		{
			System.out.print("Voulez-vous ajouter des questions ? (oui/non) : ");
			reponse = sc.nextLine();
		}
		while (!reponse.equals("oui") && !reponse.equals("non"));

		//si oui on demande le nombre de questions et reer la liste de question
		
		if (reponse.equals("oui"))
		{	
			//demander le nombre de question tant que le nombre n'est pas positif et tant que ce n'est pas un entier
			int nbQuestion;
			do
			{
				System.out.print("Entrez le nombre de questions : ");
				while (!sc.hasNextInt())
				{
					System.out.print("Ce n'est pas un entier, réessayez : ");
					sc.next();
				}
				nbQuestion = sc.nextInt();
			}
			while (nbQuestion <= 0);

			sc.nextLine();

			//créer les questions

			for (int i=0; i<nbQuestion; i++)
			{
				System.out.println("\nCréation de la question "+(i+1)+" : ");
				Question question = Question.creerQuestionQCM(sc);
				lstQuestions.add(question);
			}
		}
		return new Notion(notion, lstQuestions);
	}
	
}