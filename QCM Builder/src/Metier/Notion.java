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

		if (lstQuestions != null) 
			initQuestion(lstQuestions);
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
	public void ajouterQuestion(String question, String type, String explication, String difficulte, int point, float temps)
	{
		this.addQuestion(Question.creerQuestion(question, type, explication, difficulte, point, temps));
	}


	/**
	 * Ajoute les questions dans la bonne list
	 * @param lstQuestions List de question
	 */
	public void initQuestion(ArrayList<Question> lstQuestions)
	{
		// Initialise les questions
		for (Question question:lstQuestions)
		{
			switch (question.getDifficulte())
			{
				case "Très Facile":
					this.lstQuestionsTresFacile.add(question);
					break;

				case "Facile":
					this.lstQuestionsFacile.add(question);
					break;
				
				case "Moyen":
					this.lstQuestionsMoyenne.add(question);
					break;
				
				case "Difficile":
					this.lstQuestionsDifficile.add(question);
					break;
			
				default:
					break;
			}
		}
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

	public ArrayList<Question> aleaQuestions(int nbQuestionFacile, int nbQuestionMoyenne, int nbQuestionDifficile)
	{
		int idFinQuestionTresFacile;
		int idFinQuestionFacile;
		int idFinQuestionMoyenne;

		for(int i=0; i<this.lstQuestions.size(); i++)
		{
			
		}
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
		return this.lstQuestionsFacile.size() + this.lstQuestionsMoyenne.size() + this.lstQuestionsDifficile.size() + this.lstQuestionsTresFacile.size();
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