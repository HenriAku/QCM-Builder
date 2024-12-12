/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */

package Metier;

import java.util.ArrayList;
import java.util.Scanner;

public class Ressource
{

	/*
	 * VARIABLES
	*/

	private String nom;
	private ArrayList<Notion> notions;

	/*
	 * CONSTRUCTEUR
	*/

	public Ressource(String nom)
	{
		this.nom = nom;
		this.notions = new ArrayList<>(); 
		Ecriture ef = new Ecriture("../ressources/");
		ef.creerDossier(nom);
	}

	public Ressource(String nom, ArrayList<Notion> notions)
	{
		this.nom = nom;
		this.notions = notions; 
		Ecriture ef = new Ecriture("../ressources/");
		ef.creerDossier(nom);
	}

	//ajout d'une notion dans la liste en vérifaint s'il n'existe pas encore
	public void addNotion(Notion not)
	{
		boolean existe = false;
		System.out.println("ajout du notion");
		for (Notion notion : this.notions) 
		{
			if (notion.getNom().equals(not.getNom())) 
			{
				existe = true;
				System.out.println("Notion déja existante");
				break;
			}
		}
		if (!existe) 
		{
			this.notions.add(not);
			System.out.println("Notion ajouté");
		}
	}

	public Notion rechercheNotion(String nom)
	{
		for (Notion notion : notions) 
		{
			if (notion.getNom().equals(nom))
				return notion;
		}
		return null;
	}

	/**
	 * GETTERS ET SETTERS
	 */

	public String getNom() {return this.nom;}

	public void setNom(String nom) {this.nom = nom;}

	public ArrayList<Notion> getNotions() {return this.notions;}

	public void ajouterNotions(Notion not) {this.notions.add(not);}

	public void setNotions(ArrayList<Notion> notions) {this.notions = notions;}

	//methode getNbNotions qui retourne le nombre de notions
	public int getNbNotions() {return this.notions.size();}

	//methode getNbQuestions qui retourne le nombre de questions totales
	public int getNbQuestions()
	{
		int nbQuestions = 0;
		for (Notion not : notions) 
		{
			nbQuestions += not.getNbQuestions();
		}
		return nbQuestions;
	}
	/* */

	//methode creerRessource en demandant les informations à l'utilisateur
	public static Ressource creerRessource(Scanner sc) 
	//"sc" utilisation de scanner pour récupérer les informations de l'utilisateur en version console
	{
		System.out.print("Entrez le nom de la ressource : ");
		String nom = sc.nextLine();

		return new Ressource(nom);
	}

	public String toString ()
	{
		String 	str ="Ressource : " +this.nom +"\n";
				str += "Liste des Notions :\n";
		for (Notion not : notions) 
		{
			str += " - " + not.getNom() + "\n";
		}

		return str;
	}

	//ajouter ressources
	public void ajouterNotions(Scanner sc)
	{
		//demander si on creer la liste des notions, tant que la réponse n'est pas oui ou non
		String reponse;
		do
		{
			System.out.print("Voulez-vous ajouter des notions ? (oui/non) : ");
			reponse = sc.nextLine();
		}
		while (!reponse.equalsIgnoreCase("oui") && !reponse.equalsIgnoreCase("non"));
		
		//si oui on demande le nombre et on les ajoute
		if (reponse.equalsIgnoreCase("oui")) 
		{
			//on demande le nombre de notions tant que le nombre n'est pas positif et entier
			int nbNot;
			do
			{
				System.out.print("Combien de notions voulez-vous ajouter ? ");
				while (!sc.hasNextInt()) 
				{
					System.out.print("Veuillez entrer un nombre entier : ");
					sc.next();
				}
				nbNot = sc.nextInt();
				sc.nextLine();
			}
			while (nbNot <= 0);

			//on crée les notions
			for (int i=0; i<nbNot; i++)
			{	
				System.out.println("\nCréation du notion "+(i+1)+" : ");
				Notion not = Notion.creerNotion(sc);
				this.addNotion(not);
			}
		}
	}

	//ajouter question
	public void ajouterQuestion(String notion, String question, String type, String explication, Difficulte difficulte,double point, float temps, ArrayList<Reponse> reponses)
	{
		//on récupère le notion dans lstNotions
		for (Notion not : this.notions) 
		{
			if (not.getNom().equals(notion)) 
			{
				not.ajouterQuestion(question, type, explication, difficulte,point, temps, reponses);
			}
		}


	}

	//affiche la resource et ses notions
	public String afficherRessource()
	{
		String 	str ="Ressource : " +this.nom +"\n";
				str += "Liste des Notions :\n";

		for (Notion not : notions) 
		{
			str += " - " + not.getNom() + "\n";
		}

		return str;
	}

	//affiche la ressource et le détail de ses notions
	public String afficherRessourceDetail()
	{
		String 	str ="\nRessource : " +this.nom +"\n";
				str += "Liste des Notions :\n";

		for (Notion not : notions) 
		{
			str += " - " + not.getNom() + "\n";
			str += "\t"+ not.afficherNotion();
		}

		return str;
	}
}