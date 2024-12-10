/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Théo Wychowski
 * @date 09/12/2024
 */

package Metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import Controlleur.Controlleur;

public class Metier 
{
	private Controlleur     ctrl;
	private List<Ressource> lstRessources;
	private Lire lecteur;
	private Ecriture ecriture;

	public Metier(Controlleur ctrl)
	{
		this.ctrl = ctrl;
		this.lstRessources = new ArrayList<>();
		this.lecteur  = new Lire("QCM Builder\\\\ressources/");
		this.ecriture = new Ecriture("QCM Builder\\ressources/");
		this.init();
	}

	public Metier()
	{
		this.ctrl = null;
		this.lstRessources = new ArrayList<>();
		this.lecteur  = new Lire("QCM Builder\" + File.separator + \"ressources\" + File.separator");
		this.ecriture = new Ecriture("QCM Builder\" + File.separator + \"ressources\" + File.separator");
		this.init();
	}


	/**
	 * Initialise les ressources et leurs chapitres à partir des dossiers et fichiers présents
	 * dans le répertoire 'ressources'.
	 */
	public void init() {
		ArrayList<Ressource> lstRessources  = new ArrayList<>();
		ArrayList<String>    nomsRessources = getLecteur().lireDossier("");
	
		for (int i = 0; i < nomsRessources.size(); i++) 
		{
			ArrayList<Chapitre> lstChapitre   = new ArrayList<Chapitre>();
			ArrayList<String  > nomsChapitres = getLecteur().lireDossier(nomsRessources.get(i));
	
			for (int j = 0; j < nomsChapitres.size(); j++) 
			{
				ArrayList<Question> lstQuestions = new ArrayList<>();
				String cheminQuestion = lecteur.getEmplacementRessources() + nomsRessources.get(i) + "/" + nomsChapitres.get(j);
	
				lstQuestions = getLecteur().lireQuestion(cheminQuestion);
				lstChapitre.add(new Chapitre(nomsChapitres.get(j), lstQuestions));
			}
	
			lstRessources.add(new Ressource(nomsRessources.get(i), lstChapitre));
		}
	
		setLstRessource(lstRessources);
	}
	

	public boolean creerDossier(String nomRes)
	{
		return this.ecriture.creerDossier(nomRes);
	}

	/**
	 * Cherche une ressource
	 * @param nom le nom d'une ressource
	 * @return une "Ressource"
	 */
	public Ressource rechercheRessource(String nom)
	{
		for (Ressource ressource : this.lstRessources) 
		{
			if (ressource.getNom().equals(nom))
				return ressource;
		}
		return null;
	}

	/**
	 * Cherche un chapitre dans une ressource
	 * @param nomR le nom d'une ressource
	 * @param nomC le nom d'un chapitre
	 * @return un "Chapitre"
	 */
	public Chapitre rechercheChap(String nomR, String nomC)
	{
		for (Ressource ressource : this.lstRessources) 
		{
			for (Chapitre chap : ressource.getChapitres()) 
			{
				if (ressource.getNom().equals(nomR) && chap.getNom().equals(nomC))
					return chap;
			}
		}
		return null;
	}

	/**
	 * Ajoute une ressource a la list
	 * @param res est une ressource
	 */
	public void addRessource(Ressource res)
	{
		boolean bOk = false;
		for (Ressource ressource : this.lstRessources)
		{
			if (!ressource.getNom().equals(res.getNom()))
				bOk = true;
		}
		this.lstRessources.add(res);
	}

	//generer un questionnaire
	public Questionnaire genererQCM(Scanner sc)
	{

		return Questionnaire.genererQuestionnaire(sc, this);
	}

	/** 
	 * Getters et Setters
	 */
	

	public void setLstRessource(ArrayList<Ressource> lstRessources)
	{
		this.lstRessources = lstRessources;
	}

	public Lire getLecteur()
	{
		return this.lecteur;
	}

	public List<Ressource> getLstRessource()
	{
		return this.lstRessources;
	}


}
