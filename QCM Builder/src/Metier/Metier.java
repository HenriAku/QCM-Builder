/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */

package Metier;

import java.io.File;
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
		this.lecteur  = new Lire("QCM Builder" + File.separator + "ressources/");
		this.ecriture = new Ecriture("QCM Builder" + File.separator + "ressources/");
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

	///////////////
	// RESSORCES //
	///////////////

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
	public String validerQuestion (String ressource, String notion, String type, String difficulte, double point, String temps)
	{
		String erreur = "";
		//verifier si la ressource existe
		if (rechercheRessource(ressource) == null)
			erreur += "La ressource n'existe pas\n";
		//verifier si la notion existe
		if (rechercheNotion(ressource, notion) == null)
			erreur += "La notion n'existe pas\n";
		//verifier si le type est QCM ou Association
		if (!type.equalsIgnoreCase("QCM") && !type.equalsIgnoreCase("Association"))
			erreur += "Le type doit être QCM ou Association\n";
		//verifier si la difficulté est tres facile, facile, moyen ou difficile
		if (!difficulte.equalsIgnoreCase("Tres facile") && !difficulte.equalsIgnoreCase("Facile") && !difficulte.equalsIgnoreCase("Moyen") && !difficulte.equalsIgnoreCase("Difficile"))
			erreur += "La difficulté doit être Tres facile, Facile, Moyen ou Difficile\n";
		//verifier si le point est positif
		if (point < 0)
			erreur += "Le point doit être positif ou 0\n";
		//verifier si le temps est coherrant
		if (temps.equals(""))
			erreur += "Le temps ne peut pas être vide\n";
		if (temps.indexOf(":") == -1)
			erreur += "Le temps doit être sous la forme mm:ss\n";
		//vérifier si secondes > 60
		else
		{
			String[] tab = temps.split(":");
			int timeMin = Integer.parseInt(tab[0]);
			int timeSec = Integer.parseInt(tab[1]);
			if (timeMin < 0 || timeSec < 0)
				erreur += "Le temps doit être positif\n";
			if (timeSec >= 60)
				erreur += "Les secondes doivent être inférieures à 60\n";
		}
		//retourne les erreurs si il y en a, sinon retourne une chaine vide
		return erreur;
	}

	public String creerQuestion(String Sressource, String notion, String question, String type, String explication, String difficulte, double point, String temps, ArrayList<String> lstRep, ArrayList<Boolean> validite)
	{
		//associer r à la ressource portant le même nom dans la liste
		Ressource ressource = null;
		for (Ressource res : this.lstRessources)
		{
			if (res.getNom().equals(Sressource))
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
		Difficulte diff = null;
		//mettre en minuscule la difficulté
		difficulte = difficulte.toLowerCase();
		//associer le string difficulté à un objet Difficulte
		if (difficulte.equals("tres facile"))
			diff = Difficulte.TF;
		else if (difficulte.equals("facile"))
			diff = Difficulte.F;
		else if (difficulte.equals("moyen"))
			diff = Difficulte.M;
		else if (difficulte.equals("difficile"))
			diff = Difficulte.D;
		else if (difficulte.equals(""))
			erreur += "La difficulté ne peut pas être vide\n";
		else
			erreur += "La difficulté doit être facile, moyen ou difficile\n";

		//point
		if (point < 0)
			erreur += "Le point doit être positif ou 0\n";
			
		//temps
		//transofrmer le temps (mm::ss) en minutes a virgule
		//si le temps est négatif ou égal à 0
		int timeMin =0;
		int timeSec =0;
		if (temps.equals(""))
			erreur += "Le temps ne peut pas être vide\n";
		if (temps.indexOf(":") == -1)
			erreur += "Le temps doit être sous la forme mm:ss\n";
		//vérifier si secondes > 60
		else
		{
			String[] tab = temps.split(":");
			timeMin = Integer.parseInt(tab[0]);
			timeSec = Integer.parseInt(tab[1]);
			if (timeMin < 0 || timeSec < 0)
				erreur += "Le temps doit être positif\n";
			if (timeSec >= 60)
				erreur += "Les secondes doivent être inférieures à 60\n";
		}
		//calculer le temps en en numérique 1h30 = 1,5h
		float tempsS = timeMin + (timeSec/60);

		//creer la liste de reponses par la list de string entree en paremetre
		ArrayList<Reponse> lstReponses = new ArrayList<Reponse>();
		//vérifier si la liste de reponses est vide
		if (lstRep.size() == 0)
			erreur += "Il faut au moins une réponse\n";
		else
		{
			for (int i=0; i<lstRep.size(); i++)
			{
			lstReponses.add(new Reponse(lstRep.get(i), validite.get(i)));
			}
		}
		
		
		//si il n'y a pas d'erreur appeler la methode ajouterQuestion de la ressource
		if (erreur.equals(""))
		{
			ressource.ajouterQuestion(notion, question,type, explication, diff, point,  tempsS , lstReponses);
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
