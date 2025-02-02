/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */

package Metier;

import java.util.ArrayList;

public class Ressource implements Comparable<Ressource>
{

	///////////////
	// ATTRIBUTS //
	///////////////

	private String nom;
	private ArrayList<Notion> notions;

	///////////////////
	// CONSTRUCTEURS //
	///////////////////

	public Ressource(String nom)
	{
		this.nom     = nom;
		this.notions = new ArrayList<>(); 
		Ecriture ef  = new Ecriture("../ressources/");
		ef.creerDossier(nom);
	}

	public Ressource(String nom, ArrayList<Notion> notions)
	{
		this.nom     = nom;
		this.notions = notions; 
		Ecriture ef  = new Ecriture("../ressources/");
		ef.creerDossier(nom);
	}

	/////////////
	// NOTIONS //
	/////////////

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

	public void ajouterNotions(Notion not) {this.notions.add(not);}

	public Notion rechercheNotion(String nom)
	{
		for (Notion notion : notions) 
		{
			if (notion.getNom().equals(nom))
				return notion;
		}
		return null;
	}
	

	///////////////
	// QUESTIONS //
	///////////////

	/************************************/
	/* Ajout des question dans la list  */
	/************************************/
	public QCM ajouterQuestionQCM(String notion, String question, String explication, Difficulte difficulte,double point, float temps, ArrayList<ReponseQcm> reponses)
	{
		//on récupère le notion dans lstNotions
		for (Notion not : this.notions) 
		{
			if (not.getNom().equals(notion)) 
				return not.ajouterQuestionQCM(question, explication, difficulte,point, temps, reponses);
		}
		return null;
	}
	
	public QCM ajouterQuestionQCM(String notion, String question, String explication, Difficulte difficulte,double point, float temps, ArrayList<ReponseQcm> reponses, String path)
	{
		//on récupère le notion dans lstNotions
		for (Notion not : this.notions) 
		{
			if (not.getNom().equals(notion)) 
				return not.ajouterQuestionQCM(question, explication, difficulte,point, temps, reponses, path);
		}
		return null;
	}

	public Association ajouterQuestionAsso(String notion, String question, String type, String explication, Difficulte difficulte,double point, float temps, ArrayList<ReponseAsso> reponses)
	{
		//on récupère le notion dans lstNotions
		for (Notion not : this.notions) 
		{
			if (not.getNom().equals(notion)) 
			{
				return not.ajouterQuestionAsso(question, explication, difficulte, point, temps, reponses);
			}
		}
		return null;
	}

	public Association ajouterQuestionAsso(String notion, String question, String type, String explication, Difficulte difficulte,double point, float temps, ArrayList<ReponseAsso> reponses, String path)
	{
		//on récupère le notion dans lstNotions
		for (Notion not : this.notions) 
		{
			if (not.getNom().equals(notion)) 
			{
				return not.ajouterQuestionAsso(question, explication, difficulte, point, temps, reponses, path);
			}
		}
		return null;
	}

	public Elimination ajouterQuestionElimination(String notion, String question, String type, String explication, Difficulte difficulte,double point, float temps, ArrayList<ReponseElimination> reponses)
	{
		//on récupère le notion dans lstNotions
		for (Notion not : this.notions) 
		{
			if (not.getNom().equals(notion)) 
				return not.ajouterQuestionElimination(question, explication, difficulte, point, temps, reponses);
		}
		return null;
	}

	public Elimination ajouterQuestionElimination(String notion, String question, String type, String explication, Difficulte difficulte,double point, float temps, ArrayList<ReponseElimination> reponses, String path)
	{
		//on récupère le notion dans lstNotions
		for (Notion not : this.notions) 
		{
			if (not.getNom().equals(notion)) 
				return not.ajouterQuestionElimination(question, explication, difficulte, point, temps, reponses, path);
		}
		return null;
	}

	public void supprimerQuestion(Question questionn, Notion notion)
	{
		notion.supprQuestion(questionn);
	}

	/////////////
	// GETTERS //
	/////////////

	public String getNom() {return this.nom;}

	public ArrayList<Notion> getNotions() {return this.notions;}

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

	/////////////
	// SETTERS //
	/////////////

	public void setNom(String nom) {this.nom = nom;}

	public void setNotions(ArrayList<Notion> notions) {this.notions = notions;}

	////////////
	// STRING //
	////////////
	
	public String toString ()
	{
		String 	str  = "Ressource : " +this.nom +"\n";
				str += "Liste des Notions :\n";
		for (Notion not : notions) 
		{
			str += " - " + not.getNom() + "\n";
		}

		return str;
	}

	//affiche la resource et ses notions
	public String afficherRessource()
	{
		String 	str  = "Ressource : " +this.nom +"\n";
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
		String 	str  = "\nRessource : " +this.nom +"\n";
				str += "Liste des Notions :\n";

		for (Notion not : this.notions) 
		{
			str += " - " + not.getNom() + "\n";
			str += "\t"+ not.afficherNotion();
		}

		return str;
	}

	@Override
	public int compareTo(Ressource o) 
	{
		return this.nom.compareToIgnoreCase(o.nom);
	}
}