/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Théo Wychowski
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
	private ArrayList<Chapitre> chapitres;

	/*
	 * CONSTRUCTEUR
	*/

	public Ressource(String nom)
	{
		this.nom = nom;
		this.chapitres = new ArrayList<>(); 
		Ecriture ef = new Ecriture("../ressources/");
		ef.creerDossier(nom);
	}

	public Ressource(String nom, ArrayList<Chapitre> chapitres)
	{
		this.nom = nom;
		this.chapitres = chapitres; 
		Ecriture ef = new Ecriture("../ressources/");
		ef.creerDossier(nom);
	}

	//ajout d'un chapitre dans la liste en vérifaint s'il n'existe pas encore
	public void addChap(Chapitre chap)
	{
		boolean existe = false;
		System.out.println("ajout du chapitre");
		for (Chapitre chapitre : this.chapitres) 
		{
			if (chapitre.getNom().equals(chap.getNom())) 
			{
				existe = true;
				System.out.println("Chapitre déja existant");
				break;
			}
		}
		if (!existe) 
		{
			this.chapitres.add(chap);
			System.out.println("Chapitre ajouté");
		}
	}

	public Chapitre rechercheChapitre(String nom)
	{
		for (Chapitre chapitre : chapitres) 
		{
			if (chapitre.getNom().equals(nom))
				return chapitre;
		}
		return null;
	}

	/**
	 * GETTERS ET SETTERS
	 */

	public String getNom() {return this.nom;}

	public void setNom(String nom) {this.nom = nom;}

	public ArrayList<Chapitre> getChapitres() {return this.chapitres;}

	public void ajouterChapitres(Chapitre chap) {this.chapitres.add(chap);}

	public void setChapitres(ArrayList<Chapitre> chapitres) {this.chapitres = chapitres;}

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
				str += "Liste des Chapitres :\n";
		for (Chapitre chap : chapitres) 
		{
			str += " - " + chap.getNom() + "\n";
		}

		return str;
	}

	//affiche la resource et ses chapitres
	public String afficherRessource()
	{
		String 	str ="Ressource : " +this.nom +"\n";
				str += "Liste des Chapitres :\n";

		for (Chapitre chap : chapitres) 
		{
			str += " - " + chap.getNom() + "\n";
		}

		return str;
	}

	//affiche la ressource et le détail de ses chapitres
	public String afficherRessourceDetail()
	{
		String 	str ="Ressource : " +this.nom +"\n";
				str += "Liste des Chapitres :\n";

		for (Chapitre chap : chapitres) 
		{
			str += " - " + chap.getNom() + "\n";
			str += "\t"+ chap.afficherChapitre();
		}

		return str;
	}
}