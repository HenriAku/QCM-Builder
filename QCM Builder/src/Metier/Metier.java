/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */

package Metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Metier 
{
	private List<Ressource> lstRessources;
	private Lire lecteur;
	private Ecriture ecriture;

	public Metier()
	{
		this.lstRessources = new ArrayList<>();
		this.lecteur  = new Lire("QCM Builder\\\\ressources/");
		this.ecriture = new Ecriture("QCM Builder\\ressources/");
		this.init();
	}

	/**
	 * Initialise les ressources et leurs notions à partir des dossiers et fichiers présents
	 * dans le répertoire 'ressources'.
	 */
	public void init() {
		ArrayList<Ressource> lstRessources  = new ArrayList<>();
		ArrayList<String>    nomsRessources = getLecteur().lireDossier("");
	
		for (int i = 0; i < nomsRessources.size(); i++) 
		{
			ArrayList<Notion> lstNotion   = new ArrayList<Notion>();
			ArrayList<String  > nomsNotion = getLecteur().lireDossier(nomsRessources.get(i));
	
			for (int j = 0; j < nomsNotion.size(); j++) 
			{
				ArrayList<Question> lstQuestions = new ArrayList<>();
				String cheminQuestion = lecteur.getEmplacementRessources() + nomsRessources.get(i) + "/" + nomsNotion.get(j);
	
				lstQuestions = getLecteur().lireQuestion(cheminQuestion);
				lstNotion.add(new Notion(nomsNotion.get(j), lstQuestions));
			}
	
			lstRessources.add(new Ressource(nomsRessources.get(i), lstNotion));
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
	 * Cherche une notion dans une ressource
	 * @param nomR le nom d'une ressource
	 * @param nomN le nom d'un notion
	 * @return une "Notion"
	 */
	public Notion rechercheNotion(String nomR, String nomN)
	{
		for (Ressource ressource : this.lstRessources) 
		{
			for (Notion notion : ressource.getNotions()) 
			{
				if (ressource.getNom().equals(nomR) && notion.getNom().equals(nomN))
					return notion;
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

	//creer les questions
	public String creerQuestion(String r, String c, String question, String type, String explication, String difficulte, int point, float temps)
	{
		//associer r à la ressource portant le même nom dans la liste
		Ressource ressource = null;
		for (Ressource res : this.lstRessources)
		{
			if (res.getNom().equals(r))
				ressource = res;
		}

		String erreur = "";
		//question
		if (question.equals(""))
			erreur += "La question ne peut pas être vide\n";

		//type
		if (!type.equalsIgnoreCase("QCM") && !type.equalsIgnoreCase("Association"))
			erreur += "Le type doit être QCM ou Association\n";

		//explication
		if (explication.equals(""))
			erreur += "L'explication ne peut pas être vide\n";

		//difficulte
		if (!difficulte.equalsIgnoreCase("facile") && !difficulte.equalsIgnoreCase("moyen") && !difficulte.equalsIgnoreCase("difficile"))
			erreur += "La difficulté doit être facile, moyen ou difficile\n";
			//transformer la string en un enum difficulte



			

			

		//point
		if (point < 0)
			erreur += "Le nombre de points doit être positif ou 0\n";

		//temps
		if (temps < 0)
			erreur += "Le temps doit être positif ou 0\n";
		
		//si il n'y a pas d'erreur appeler la methode ajouterQuestion de la ressource
		if (erreur.equals(""))
		{
			ressource.ajouterQuestion(c, question,type, explication, difficulte, point, temps);
			/*if (type.equalsIgnoreCase("QCM"))
				return r.ajouterQuestion(c, new QCM(question, explication, difficulte, point, timeMin, timeSec, nbRep));
			else
				return r.ajouterQuestion(c, new Association(question, explication, difficulte, point, timeMin, timeSec, nbRep));
			*/
		}
		return erreur;
	}


	//generer un questionnaire
	public Questionnaire genererQCM(Scanner sc)
	{
		return Questionnaire.genererQuestionnaire(sc, this);
	}

	public boolean renommerDossier(String ancienNom, String nouveauNom)
	{
		return this.ecriture.renommerDossier(ancienNom, nouveauNom);
	}

	public boolean supprimerDossier(String nomDossier)
	{
		return this.ecriture.supprimerDossier(nomDossier);
	}

	public String[] getNomRessources()
	{
		String[] noms = new String[this.lstRessources.size()];
		for (int i=0; i<this.lstRessources.size(); i++) 
		{
			noms[i] = this.lstRessources.get(i).getNom();
		}
		return noms;
	}

	public String[] getNomNotion(String res)
	{
		Ressource ressource = this.rechercheRessource(res);
		String[] noms = new String[ressource.getNotions().size()];
		for (int i=0; i<ressource.getNotions().size(); i++) 
		{
			noms[i] = ressource.getNotions().get(i).getNom();
		}
		return noms;
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
