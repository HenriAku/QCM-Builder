/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Théo Wychowski
 * @date 09/12/2024
 */

package Metier;

import java.util.ArrayList;
import java.util.List;

public class Metier 
{
	//private Controlleur     ctrl;
	private List<Ressource> lstRessources;
	private Lire lecteur;

	public Metier()//Controlleur ctrl)
	{
		//this.ctrl = ctrl;
		this.lstRessources = new ArrayList<>();
		this.lecteur = new Lire("./../ressources/");
	
	}


	/**
	 * Initialise les ressources et leurs chapitres à partir des dossiers et fichiers présents
	 * dans le répertoire 'ressources'.
	 */
	public void init()
	{
		ArrayList<Ressource> lstRessources = new ArrayList<>();
		ArrayList<String> nomsRessources = getLecteur().lireDossier(lecteur.getEmplacementRessources());

		for (int i=0; i<nomsRessources.size(); i++)
		{
			ArrayList<Chapitre> lstChapitre = new ArrayList<>();
			ArrayList<String> nomsChapitres = getLecteur().lireDossier(lecteur.getEmplacementRessources()+""+nomsRessources.get(i));
			
			for (int j=0; j<nomsChapitres.size(); j++)
			{
				ArrayList<Question> lstQuestions;
				lstQuestions = getLecteur().lireQuestion(lecteur.getEmplacementRessources()+""+nomsRessources.get(i)+""+nomsChapitres.get(j));
				lstChapitre.add(new Chapitre(nomsChapitres.get(i), lstQuestions));
			}

			lstRessources.add(new Ressource(nomsRessources.get(i)));

		}
		
		setLstRessource(lstRessources);

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
		for (Ressource ressource : this.lstRessources)
		{
			if (!ressource.getNom().equals(res.getNom())) 
				this.lstRessources.add(res);
		}
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
