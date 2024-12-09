/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Théo Wychowski
 * @date 09/12/2024
 */

package Metier;

import java.util.ArrayList;
import java.util.Scanner;

public class Chapitre
{

	private String chapitre;
	//private Ressource ressource;
	private ArrayList<Question> lstQuestionsFacile;
	private ArrayList<Question> lstQuestionsMoyenne;
	private ArrayList<Question> lstQuestionsDifficile;
	private ArrayList<Question> lstQuestionsTresFacile;

	
	public Chapitre(String chapitre, /*Ressource ressource,*/ ArrayList<Question> lstQuestions)
	{
		this.chapitre = chapitre;
		//this.ressource = ressource;

		this.lstQuestionsFacile     = new ArrayList<Question>();
		this.lstQuestionsMoyenne    = new ArrayList<Question>();
		this.lstQuestionsDifficile  = new ArrayList<Question>();
		this.lstQuestionsTresFacile = new ArrayList<Question>();


		Ecriture ef = new Ecriture("../ressources/" );
		ef.creerDossier(chapitre);

		initQuestion(lstQuestions);
	}

	//methode creerChapitre en demandant les informations à l'utilisateur
	public static Chapitre creerChapitre(Scanner sc)
	//"sc" utilisation de scanner pour récupérer les informations de l'utilisateur en version console
	{
		// Demande le nom du chapitre
		System.out.print("Entrez le nom du chapitre : ");
		String chapitre = sc.nextLine();

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
		return new Chapitre(chapitre, lstQuestions);
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
				
				case "Moyenne":
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
	

	/**
	 * Ajoute une question dnas la bonne list
	 * @param q une question
	 */
	public void addQuestion(Question q)
	{
		if (q.getDifficulte().equals("Difficile")) 
		{
			for (Question question : this.lstQuestionsDifficile) 
			{
				if (!question.equals(q)) 
					this.lstQuestionsDifficile.add(q);
			}
		}
		else if (q.getDifficulte().equals("Facile")) 
		{
			for (Question question : this.lstQuestionsFacile) 
			{
				if (!question.equals(q)) 
					this.lstQuestionsFacile.add(q);
			}
		}
		else if (q.getDifficulte().equals("Très Facile")) 
		{
			for (Question question : this.lstQuestionsTresFacile) 
			{
				if (!question.equals(q)) 
					this.lstQuestionsTresFacile.add(q);
			}
		}
		else 
		{
			for (Question question : this.lstQuestionsMoyenne) 
			{
				if (!question.equals(q)) 
					this.lstQuestionsMoyenne.add(q);
			}
		}
	}

	/**
	 * Supprime une question d'une list
	 * @param nom la question 
	 * @param difficulte de la question
	 */
	public void supprQuestion(String nom, String difficulte)
	{
		if (difficulte.equals("Difficile")) 
		{
			for (Question question : this.lstQuestionsDifficile) 
			{
				if (question.getQuestion().equals(nom)) 
					this.lstQuestionsDifficile.remove(question);
			}
		}
		else if (difficulte.equals("Facile")) 
		{
			for (Question question : this.lstQuestionsFacile) 
			{
				if (question.getQuestion().equals(nom)) 
					this.lstQuestionsFacile.remove(question);
			}
		}
		else if (difficulte.equals("Très Facile")) 
		{
			for (Question question : this.lstQuestionsTresFacile) 
			{
				if (question.getQuestion().equals(nom)) 
					this.lstQuestionsTresFacile.remove(question);
			}
		}
		else 
		{
			for (Question question : this.lstQuestionsMoyenne) 
			{
				if (question.getQuestion().equals(nom)) 
					this.lstQuestionsMoyenne.remove(question);
			}
		}
	}

	public ArrayList<Question> aleaQuestions(int nbQuestionFacile, int nbQuestionMoyenne, int nbQuestionDifficile)
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

	public String getNom() { return this.chapitre; }
	
	//public Ressource getRessource() { return this.ressource; }

	//affiche le chapitre et ses questions
	public String afficherChapitre()
	{
		String str = "Chapitre : "+this.chapitre+"\n";

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