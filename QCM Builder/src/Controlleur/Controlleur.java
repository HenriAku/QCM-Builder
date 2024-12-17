/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package Controlleur;

import java.util.List;
import java.util.ArrayList;

import IHM.FramePrincipal;
import Metier.Difficulte;
import Metier.Metier;
import Metier.Ressource;

public class Controlleur
{
	private Metier         metier;
	private FramePrincipal frame;
	public Controlleur()
	{
		this.metier = new Metier   		(    );
		this.frame  = new FramePrincipal(this);
	}

	/////////////
	// DOSIERS //
	/////////////

	public boolean creerDossier(String nomRes)
	{
		return this.metier.creerDossier(nomRes);
	}

	public boolean renommerDossier(String ancienNom, String nouveauNom)
	{
		return this.metier.renommerDossier(ancienNom, nouveauNom);
	}

	public boolean supprimerDossier(String nomDossier)
	{
		System.out.println(nomDossier);
		return this.metier.supprimerDossier(nomDossier);
	}

	////////////////
	// RESSOURCES //
	////////////////

	public void addRessource(Ressource ressource)
	{
		this.metier.addRessource(ressource);
	}

	/**
	 * Appele la methode de metier pour chercher une ressource
	 * @param nom d'une ressource
	 * @return Une "Ressource"
	 */
	public Ressource rechercheRessource(String nom)	{return this.metier.rechercheRessource(nom);}

	public List<Ressource>	 getLstRessource() 		{return this.metier.getLstRessource();}

	public String[] 		getNomRessources() 		{return this.metier.getNomRessources(   );}

	public Metier getMetier() {return this.metier;}

	//////////////
	// NOTIONS  //
	//////////////
	
	public String[] getNomNotion (String res) {return this.metier.getNomNotion    (res);}

	//////////////
	// QUESTION //
	//////////////

	//cration d'une question
	public String validerQuestion (String ressource, String notion, String type, String difficulte, double point, String temps)
	{
		return this.metier.validerQuestion(ressource, notion, type, difficulte, point, temps);
	}

	public String creerQuestionQCM(String r, String n, String question, String type, String explication, String difficulte, double point, String temps, ArrayList<String> reponses ,  ArrayList<Boolean> validite)
	{
		return (this.metier.creerQuestionQCM(r, n, question, type, explication, difficulte, point, temps, reponses, validite));
	}
	public String creerQuestionQCM(String r, String c, String question, String type, String explication, String difficulte, double point, String temps, ArrayList<String> reponses ,  ArrayList<Boolean> validite, String path)
	{
		return (this.metier.creerQuestionQCM(r, c, question, type, explication, difficulte, point, temps, reponses, validite, path));
	}

	public String creerQuestionAsso(String r, String c, String question, String type, String explication, String difficulte, double point, String temps, ArrayList<String> reponses, String path)
	{
		return (this.metier.creerQuestionAsso(r, c, question, type, explication, difficulte, point, temps, reponses, path));
	}
	
	public String creerQuestionElimination(String Sressource, String notion, String question, String type, String explication, String difficulte, double point, String temps, ArrayList<String> ordreElimination, ArrayList<String> nbPointPerdu, ArrayList<String> lstRep, ArrayList<Boolean> validite)
	{
		return this.metier.creerQuestionElimination(Sressource, notion, question, type, explication, difficulte, point, temps, ordreElimination, nbPointPerdu, lstRep, validite);
	}

	public String creerQuestionElimination(String Sressource, String notion, String question, String type, String explication, String difficulte, double point, String temps, ArrayList<String> ordreElimination, ArrayList<String> nbPointPerdu, ArrayList<String> lstRep, ArrayList<Boolean> validite, String path)
	{
		return this.metier.creerQuestionElimination(Sressource, notion, question, type, explication, difficulte, point, temps, ordreElimination, nbPointPerdu, lstRep, validite, path);
	}

	public static void main(String[] args) 
	{
		new Controlleur();
	}
}
