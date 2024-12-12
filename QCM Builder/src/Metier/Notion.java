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
	//private Ressource ressource;
	private ArrayList<Question> lstQuestionsFacile;
	private ArrayList<Question> lstQuestionsMoyenne;
	private ArrayList<Question> lstQuestionsDifficile;
	private ArrayList<Question> lstQuestionsTresFacile;

	private ArrayList<Question> lstQuestions;
	
	public Notion(String notion, /*Ressource ressource,*/ ArrayList<Question> lstQuestions)
	{
		this.notion = notion;
		//this.ressource = ressource;

		this.lstQuestionsFacile     = new ArrayList<Question>();
		this.lstQuestionsMoyenne    = new ArrayList<Question>();
		this.lstQuestionsDifficile  = new ArrayList<Question>();
		this.lstQuestionsTresFacile = new ArrayList<Question>();

		this.lstQuestions = lstQuestions;


		Ecriture ef = new Ecriture("../ressources/" );
		ef.creerDossier(notion);
	}

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
				Question question = Question.creerQuestion(sc);
				lstQuestions.add(question);
			}
		}
		return new Notion(notion, lstQuestions);
	}

	//ajouter une question
	public void ajouterQuestion(String question, String type, String explication, Difficulte difficulte, double point, float temps, ArrayList<Reponse> lstReponses)
	{
		this.addQuestion(Question.creerQuestion(question, type, explication, difficulte,point, temps, lstReponses));
	}


	public void addQuestion(Question q)
	{
		this.lstQuestions.add(q);
		Collections.sort(this.lstQuestions);
	}

	/** //TODO: Ptt a changer
	 * Supprime une question d'une list
	 * @param nom la question 
	 * @param difficulte de la question
	 */
	public void supprQuestion(Question question)
	{
		this.lstQuestions.remove(question);
	}

	public ArrayList<Question> oldaleaQuestions(int nbQuestionFacile, int nbQuestionMoyenne, int nbQuestionDifficile)
	{
		// Entrée : Nombre de question facile, moyenne et difficiles
		// Sortie : Une arraylist avec les questions choisis aléatoirement

		ArrayList<Question> lstQuest = new ArrayList<Question>();

		for (int i=0; i<nbQuestionFacile; i++)
		{
			// Ajoute dans la liste en évitant de mettre plusieurs fois la même questions
			if (i < this.lstQuestionsFacile.size())
			{
				boolean estDejaDansLaListe = true;
				while (estDejaDansLaListe) 
				{
					Question question = this.lstQuestionsFacile.get((int)(Math.random()*this.lstQuestionsFacile.size()));
					
					estDejaDansLaListe = false;
					for (Question quest:lstQuest)
					{
						if (quest.equals(question))
							estDejaDansLaListe = true;
					}

					if (! estDejaDansLaListe)
						lstQuest.add(question);
				}
			}
		}

		for (int i=0; i<nbQuestionMoyenne; i++)
		{
			// Ajoute dans la liste en évitant de mettre plusieurs fois la même questions
			if (i < this.lstQuestionsMoyenne.size())
			{
				boolean estDejaDansLaListe = true;
				while (estDejaDansLaListe) 
				{
					Question question = this.lstQuestionsMoyenne.get((int)(Math.random()*this.lstQuestionsMoyenne.size()));
					
					estDejaDansLaListe = false;
					for (Question quest:lstQuest)
					{
						if (quest.equals(question))
							estDejaDansLaListe = true;
					}

					if (! estDejaDansLaListe)
						lstQuest.add(question);
				}
			}
		}

		for (int i=0; i<nbQuestionDifficile; i++)
		{
			// Ajoute dans la liste en évitant de mettre plusieurs fois la même questions
			if (i < this.lstQuestionsDifficile.size())
			{
				boolean estDejaDansLaListe = true;
				while (estDejaDansLaListe) 
				{
					Question question = this.lstQuestionsDifficile.get((int)(Math.random()*this.lstQuestionsDifficile.size()));
					
					estDejaDansLaListe = false;
					for (Question quest:lstQuest)
					{
						if (quest.equals(question))
							estDejaDansLaListe = true;
					}

					if (! estDejaDansLaListe)
						lstQuest.add(question);
				}
			}
		}

		return lstQuest;
	}

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
		ArrayList<Question> lstQuest = new ArrayList<Question>();

		for (int i=0; i<nbQuestion; i++)
		{
			// Ajoute dans la liste en évitant de mettre plusieurs fois la même questions
			if (i < this.lstQuestionsFacile.size())
			{
				boolean estDejaDansLaListe = true;
				while (estDejaDansLaListe) 
				{
					Question question = this.lstQuestionsFacile.get((int)(Math.random()*this.lstQuestionsFacile.size()));
					
					estDejaDansLaListe = false;
					for (Question quest:lstQuest)
					{
						if (quest.equals(question))
							estDejaDansLaListe = true;
					}

					if (! estDejaDansLaListe)
						lstQuest.add(question);
				}
			}
		}

		return lstQuest;
	}

	public String getNom(		   ) { return this.notion; }
	public void   setNom(String nom) { this.notion = nom ; }

	public int getNbQuestions()
	{
		return this.lstQuestions.size();
	} 

	
	//public Ressource getRessource() { return this.ressource; }

	//affiche le notion et ses questions
	public String afficherNotion()
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

	}
}