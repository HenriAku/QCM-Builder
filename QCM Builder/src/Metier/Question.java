/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */

package Metier;

import java.util.Scanner;
import java.util.ArrayList;

public abstract class Question implements Comparable<Question>
{
	private String       question    ;
	private String       explication ;
	private Difficulte   difficulte  ;
	private double		 point       ;
	private float        temps        ;
	private String		 filePath;


	public Question(String question, String explication, Difficulte difficulte, double point, float temps) 
	{
		this.question     = question    ;
		this.explication  = explication ;
		this.difficulte   = difficulte  ;
		this.point        = point       ;
		this.temps		  = temps       ;
	}

	public Question(String question, String explication, Difficulte difficulte, double point, float temps, String path) 
	{
		this.question     = question    ;
		this.explication  = explication ;
		this.difficulte   = difficulte  ;
		this.point        = point       ;
		this.temps		  = temps       ;
		this.filePath     = path;
	}

	// Compare les difficultés
	public int compareTo(Question autreQuestion)
	{
		return this.difficulte.getPoint() - autreQuestion.difficulte.getPoint();
	}

	/***********************/
	/*		GETTEURS	   */
	/***********************/

	public String     getQuestion     () {return question    ;}
	public String     getExplication  () {return explication ;}
	public Difficulte getDifficulte   () {return difficulte  ;}
	public double     getPoint        () {return point       ;}
	public float      getTemps        () {return temps       ;}
	public String 	  getFilePath         () {return this.filePath;}

	public abstract ArrayList<?> getLstRep();


	/***********************/
	/*		Setteurs	   */
	/***********************/
    
	public void setQuestion    (String question        ) {this.question     = question    ;}
	public void setExplication (String explication     ) {this.explication  = explication ;}
	public void setDifficulte  (Difficulte difficulte  ) {this.difficulte   = difficulte  ;}
	public void setPoint       (int    point           ) {this.point        = point       ;}
	public void setTemps       (float  temps           ) {this.temps        = temps       ;}
	public void setPath       (String  path           ) {this.filePath       = path       ;}


	
	/***********************/
	/*		Methodes	   */
	/***********************/

	
	/***********************/
	/*		String		   */
	/***********************/
	public String toString() 
	{
		return question;
	}
	
	public String afficherQuestion()
	{	
		String str = "";

		str += "Question : " + question +"\n";
		str += "Explication : " + explication + "\n";
		str += "Difficulte : " + difficulte + "\n";
		str += "Point : " + point + "\n";
	
		return str + "\n";
	}

	/////////
	// CUI //
	/////////

	//creer question qcm en demandant chaque element (terminal)
	public static Question creerQuestionQCM(Scanner sc)
	{
		return QCM.creerQCM(sc);
	}

	/*
	//creer question en demandant chaque element (terminal)
	public static Question creerQuestion(Scanner sc)
	//"sc" utilisation de scanner pour récupérer les informations de l'utilisateur en version console
	{
		//question
		//recuperation de la question
		System.out.print("Question : ");
		String question = sc.nextLine();

		//type
		//recuperation du type tant qu'il n'est pas "QCM" ou "Association"
		String type;
		do
		{
			System.out.print("Type (QCM, Association) : ");
			type = sc.nextLine();
			//mettre majuscule pour "QCM" et mettre une majuscule puis en minuscule pour "Association"
			type = type.toUpperCase();
			if (type.equalsIgnoreCase("Association"))
				type = type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
			
			if (!type.equalsIgnoreCase("QCM") && !type.equalsIgnoreCase("Association"))
				System.out.println("Type incorrect");
		}
		while (!type.equalsIgnoreCase("QCM") && !type.equalsIgnoreCase("Association"));

		//explication
		//recuperation de l'explication
		System.out.print("Explication : ");
		String explication = sc.nextLine();

		//difficulte
		//recuperation de la difficulte tant qu'elle n'est pas "facile", "moyen" ou "difficile"
		Difficulte difficulte = Difficulte.TF;
		String stringDifficulter;
		do
		{
			System.out.print("Difficulte (très facile, facile, moyen, difficile) : ");
			stringDifficulter = sc.nextLine();
			//mettre une majuscule au début et le reste en minuscule
			stringDifficulter = stringDifficulter.substring(0, 1).toUpperCase() + stringDifficulter.substring(1).toLowerCase();
			//message d'erreur
			if (!stringDifficulter.equalsIgnoreCase("très facile") && !stringDifficulter.equalsIgnoreCase("facile") && !stringDifficulter.equalsIgnoreCase("moyen") && !stringDifficulter.equalsIgnoreCase("difficile"))
				System.out.println("Difficulte incorrecte");
			
			switch (stringDifficulter) 
			{
				case "très facile": difficulte = Difficulte.TF;
					break;
				case "facile": difficulte = Difficulte.F;
					break;
				case "moyen": difficulte = Difficulte.M;
					break;
				case "difficile": difficulte = Difficulte.D;
					break;
			
				default:
					break;
			}
		}
		while (!stringDifficulter.equalsIgnoreCase("facile") && !stringDifficulter.equalsIgnoreCase("moyen") && !stringDifficulter.equalsIgnoreCase("difficile"));
		
		//point
		//recuperation des points tant qu'ils sont positifs et tant que ce n'est pas un entier
		int point;
		do
		{
			System.out.print("Point : ");
			while (!sc.hasNextInt())
			{
				System.out.print("Ce n'est pas un entier, réessayez : ");
				sc.next();
			}
			point = sc.nextInt();
			sc.nextLine();

			if (point < 0)
				System.out.println("Le nombre de points doit être positif ou 0");

		}
		while (point < 0);
	

		//temps en secondes
		//recuperation du temps en secondes tant qu'il est positif et cohérent
		float temps;
		do
		{
			System.out.print("Temps (en secondes) : ");
			while (!sc.hasNextFloat())
			{
				System.out.print("Ce n'est pas un nombre, réessayez : ");
				sc.next();
			}
			temps = sc.nextFloat();
			sc.nextLine();

			if (temps < 0)
				System.out.println("Le temps doit être positif ou 0");

		}
		while (temps < 0);
		

		//reponses
		//recuperation du nombre de reponses tant qu'il est positif et que c'est un entier
		int nbRep;
		do
		{
			System.out.print("Nombre de reponses : ");
			while (!sc.hasNextInt())
			{
				System.out.print("Ce n'est pas un entier, réessayez : ");
				sc.next();
			}
			nbRep = sc.nextInt();
			sc.nextLine();

			if (nbRep <= 0)
				System.out.println("Le nombre de reponses doit être positif");

		}
		while (nbRep <= 0);

		//creation de la liste de reponses
		List<Reponse> lstRep = Arrays.asList(new Reponse[nbRep]);
		for (int i = 0; i < nbRep; i++)
		{
			System.out.println("\nCréation de la reponse "+(i+1)+" : ");
			lstRep.set(i, Reponse.creerReponse(sc));
		}

		return new Question(question, type, explication, difficulte, point, temps, lstRep);
	}
	*/


	
}
 