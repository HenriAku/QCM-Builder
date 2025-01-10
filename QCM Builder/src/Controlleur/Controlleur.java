/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package Controlleur;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import IHM.FramePrincipal;
import Metier.Association;
import Metier.Elimination;
import Metier.Metier;
import Metier.Notion;
import Metier.Question;
import Metier.Ressource;
import Metier.QCM;

public class Controlleur
{
	private Metier         metier;
	private FramePrincipal framePrincipal;
	public Controlleur()
	{
		this.metier = new Metier();
		this.framePrincipal = new FramePrincipal(this);
	}

	/////////////
	// DOSIERS //
	/////////////

	public boolean creerDossier(String nomRes)
	{
		return this.metier.creerDossier(nomRes);
	}

	public boolean creerDossierNotion(Ressource ressource, Notion notion)
	{
		return this.metier.creerDossierNotion(ressource, notion);
	}

	public boolean creerDossierRessource(Ressource ressource)
	{
		return this.metier.creerDossierRessource(ressource);
	}

	public boolean renommerDossier(String ancienNom, String nouveauNom)
	{
		return this.metier.renommerDossier(ancienNom, nouveauNom);
	}

	public boolean supprimerDossier(String nomDossier)
	{
		return this.metier.supprimerDossier(nomDossier);
	}

	public boolean supprimerDossierQuestion(String chemin) 
	{
		return this.metier.supprimerDossierQuestion(chemin);
	}

	////////////////
	// RESSOURCES //
	////////////////

	public void addRessource(String ressource)
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

	public void addNotion(String nomR, String n) 
	{
		this.metier.addNotion(nomR, n);
	}


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

	public String modifQuestionQCM(String r, String c, String question, String type, String explication, String difficulte, double point, String temps, ArrayList<String> reponses ,  ArrayList<Boolean> validite, QCM questionAMettreAJour, String path)
	{
		return this.metier.modifQuestionQCM(r, c, question, type, explication, difficulte, point, temps, reponses, validite, questionAMettreAJour, path);
	}

	public String modifQuestionAsso(String r, String c, String question, String type, String explication, String difficulte, double point, String temps, ArrayList<String> reponses, Association questionAModifier, String path)
	{
		return (this.metier.modifQuestionAsso(r, c, question, type, explication, difficulte, point, temps, reponses, questionAModifier, path));
	}

	public String modifQuestionElimination(String Sressource, String notion, String question, String type, String explication, String difficulte, double point, String temps, ArrayList<String> ordreElimination, ArrayList<String> nbPointPerdu, ArrayList<String> lstRep, ArrayList<Boolean> validite, Elimination questionAModifier, String path)
	{
		return this.metier.modifQuestionElimination(Sressource, notion, question, type, explication, difficulte, point, temps, ordreElimination, nbPointPerdu, lstRep, validite, questionAModifier, path);
	}

	public String rechercherFichierQuestion (Question question,Ressource res, Notion not)
	{
		return this.metier.rechercherFichierQuestion(question, res, not);
	}

	public void majPanelQuestion()
	{
		this.framePrincipal.afficheAcceuil();
		this.framePrincipal.AfficheQuestion(null, null);
	}

	public void supprimerComplement(Question question, String res, String not)
	{
		this.metier.supprimerComplement(question, res, not);
	}

	////////////////
	// Evaluation //
	////////////////
	
	public String validerEvaluation(String ressource, boolean chrono, HashMap<String, int[]> mapQuestion)
	{
		return this.metier.validerEvaluation(ressource, chrono, mapQuestion);
	}
	
	public ArrayList<Question> genererEvaluation(String r, boolean chrono, HashMap<String, int[]> mapQuestion, String nomFichier,String emplacement)
	{
		return this.metier.genererEvaluation(r, chrono, mapQuestion, nomFichier, emplacement);
	}

	public static void main(String[] args) 
	{
		new Controlleur();
	}
}
