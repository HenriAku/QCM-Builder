/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */

package Metier;

import java.util.*;

public class Questionnaire 
{
	// 0 = très facile; 1 = facile; 2 = moyen; 3 = difficile
	// private int[] nbQuesParDificulté;

	private Ressource           ressource   ;
	private ArrayList<Notion>   lstNotions  ;
	private ArrayList<Question> lstQuestions;
	private int nbQuestion;

	public Questionnaire(Ressource r, ArrayList<Notion> lstN, ArrayList<Question> lstQ) 
	{
		this.ressource = r;
		this.lstNotions = lstN;
		this.lstQuestions = lstQ;
		this.nbQuestion = lstQ.size();
	}

	public static Questionnaire genererQuestionnaire(Scanner sc, Metier m) 
	{
		/*
		 * Generer un questionnaire avec un nombre de question total et demande le
		 * nombre de question par notion
		 * on a des ressources contenant des Notion contenant des questions
		 */

		// on affiche les ressources, le nombre de notion à l'interieur et le nombre de
		// questions total
		System.out.println("Création d'un questionnaire : \n");

		for (int i = 0; i < m.getLstRessource().size(); i++) 
		{
			System.out.println((i + 1) + " : " + m.getLstRessource().get(i).getNom() + " ("
					+ m.getLstRessource().get(i).getNbNotions() + " Notion, "
					+ m.getLstRessource().get(i).getNbQuestions() + " questions)");
		}
		// on demande de choisir la ressource pour le questionnaire tant que le choix
		// n'est pas valide
		int choixRessource;
		do {
			System.out.println("\nEntrez le numéro de la ressource que vous voulez : ");
			choixRessource = sc.nextInt() - 1;
			sc.nextLine();
			if (choixRessource < 0 || choixRessource >= m.getLstRessource().size()) 
			{
				System.out.println("Le numéro de la ressource n'est pas valide.");
			}
		} while (choixRessource < 0 || choixRessource >= m.getLstRessource().size());
		Ressource ressource = m.getLstRessource().get(choixRessource);

		// on demande le nombre de question total tant que le nombre n'est pas valide et
		// ne depasse pas le nombre de question total du notion
		int nbQuestion;
		do {
			System.out.println("Entrez le nombre de questions total : ");
			nbQuestion = sc.nextInt();
			sc.nextLine();
			if (nbQuestion <= 0) 
			{
				System.out.println("Le nombre de questions doit être supérieur à 0.");
			} else if (nbQuestion > ressource.getNbQuestions())
				System.out.println("Le nombre de questions total (" + nbQuestion
						+ ") dépasse le nombre de questions total de la ressource (" + ressource.getNbQuestions()
						+ ").");
		} while (nbQuestion <= 0 || nbQuestion > ressource.getNbQuestions());

		// on affiche les notion de la ressource et le nombre de question à l'interieur
		ArrayList<Notion> lstNotion = new ArrayList<Notion>();
		System.out.print("\n");
		for (int i = 0; i < ressource.getNotions().size(); i++) 
		{
			System.out.println((i + 1) + " : " + ressource.getNotions().get(i).getNom() + " ("
					+ ressource.getNotions().get(i).getNbQuestions() + " questions)");
		}

		// on demande de choisir les Notion sur lesquels on veut faire le questionnaire
		String choixNotion;
		String[] tabNotion;
		boolean valide = false;
		do {
			valide = true;
			System.out.println("Entrez les numéros des Notion que vous voulez (ex:2,4,8) : ");
			choixNotion = sc.next();
			sc.nextLine();
			tabNotion = choixNotion.split(",");
			if (choixNotion.equals("")) {
				System.out.println("Veuillez entrer des chiffres.");
				valide = false;
			} else if (Arrays.stream(tabNotion)
					.anyMatch(s -> Integer.parseInt(s) < 1 || Integer.parseInt(s) > ressource.getNotions().size())) {
				System.out.println("Les numéros des Notion ne sont pas valides.");
				valide = false;
			}
			// le nombre de question de l'essemble des Notion selectonnés doit etre au moins
			// egal au nombre de question total
			else if (Arrays.stream(tabNotion).mapToInt(Integer::parseInt)
					.mapToObj(i -> ressource.getNotions().get(i - 1)).mapToInt(Notion::getNbQuestions)
					.sum() < nbQuestion) {
				System.out.println("Le nombre de questions contnues dans les Notion sélectionnés ("
						+ Arrays.stream(tabNotion).mapToInt(Integer::parseInt)
								.mapToObj(i -> ressource.getNotions().get(i - 1)).mapToInt(Notion::getNbQuestions).sum()
						+ ") est inférieur au nombre de questions total (" + nbQuestion + ").");
				valide = false;
			}
		} // tant que les chiffres choisits ne sont pas dans la liste
		while (!valide);
		// on ajoute les Notion choisis à la liste des Notion
		for (int i = 0; i < tabNotion.length; i++) 
		{
			lstNotion.add(ressource.getNotions().get(Integer.parseInt(tabNotion[i]) - 1));
		}

		// on demande le nombre de question par notion
		// tant que le total de question n'est pas atteint et ne dépasse pas le nombre
		// de question total et que le nombre de question n'est pas valide
		ArrayList<Question> lstQuestions = new ArrayList<Question>();
		int totalQuestions = 0;
		int nbQues;

		for (Notion notion : lstNotion) 
		{
			do {
				System.out.println("Entrez le nombre de questions pour la notion " + notion.getNom() + " : ");
				nbQues = sc.nextInt();
				sc.nextLine();
				if (nbQues <= 0) 
				{
					System.out.println("Le nombre de questions doit être supérieur à 0.");
				} else if (totalQuestions + nbQues > nbQuestion) 
				{
					System.out.println("Le nombre total de questions (" + (totalQuestions + nbQues)
							+ ") dépasse le nombre de questions total (" + nbQuestion + ").");
				}
			} while (totalQuestions + nbQues > nbQuestion || nbQues <= 0);

			totalQuestions += nbQues;
			lstQuestions.addAll(notion.aleaQuestionsSimple(nbQues));
		}
		// creer un questionnaire
		return new Questionnaire(ressource, lstNotion, lstQuestions);
	}

	public ArrayList<Question> getQuestions() 
	{
		return this.lstQuestions;
	}

}
