/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */

package Metier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;

public class Metier 
{
	private List<Ressource> lstRessources;
	private Lire            lecteur      ;
	private Ecriture        ecriture     ;

	//////////////////
	// CONSTRUCTEUR //
	//////////////////

	public Metier()
	{
		this.lstRessources = new ArrayList<>();

		this.lecteur  = new Lire    (".." + File.separator + "ressources");
		this.ecriture = new Ecriture(".." + File.separator + "ressources");

		this.init();

		Collections.sort(this.lstRessources);
	}

	/**
	 * Initialise les ressources et leurs notions à partir des dossiers et fichiers présents
	 * dans le répertoire 'ressources'.
	 */
	public void init() 
	{
		if (getLecteur().lireDossier("") == null) 
			return;
		
		ArrayList<Ressource> lstRes  = new ArrayList<>();
		ArrayList<String>    nomsRessources = getLecteur().lireDossier("");

		for (int i = 0; i < nomsRessources.size(); i++) 
		{
			ArrayList<Notion> lstNotion  = new ArrayList<Notion>();
			ArrayList<String> nomsNotion = getLecteur().lireDossier(nomsRessources.get(i) + File.separator);

			for (int j = 0; j < nomsNotion.size(); j++) 
			{
				ArrayList<Question> lstQuestions = new ArrayList<>();
				String cheminQuestion = lecteur.getEmplacementRessources()+ File.separator + nomsRessources.get(i) + File.separator + nomsNotion.get(j);
	
				lstQuestions = getLecteur().lireQuestion(cheminQuestion);

				lstNotion.add(new Notion(nomsNotion.get(j), lstQuestions));
			}
			lstRes.add(new Ressource(nomsRessources.get(i), lstNotion));
		}
		setLstRessource(lstRes);
	}


	public boolean creerDossier(String nomRes)
	{
		return this.ecriture.creerDossier(nomRes);
	}

	public boolean creerDossierNotion(Ressource ressource, Notion notion)
	{
		return this.ecriture.creerDossierNotion(ressource, notion);
	}

	public boolean creerDossierRessource(Ressource ressource)
	{
		return this.ecriture.creerDossierRessource(ressource);
	}

	public boolean renommerDossier(String ancienNom, String nouveauNom)
	{
		return this.ecriture.renommerDossier(ancienNom, nouveauNom);
	}

	public boolean supprimerDossier(String nomDossier)
	{
		return this.ecriture.supprimerDossier(nomDossier);
	}

	public boolean supprimerDossierQuestion(String chemin) 
	{
		return this.ecriture.supprimerDossierQuestion(chemin);
	}

	////////////////
	// RESSOURCES //
	////////////////

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
	 * Ajoute une ressource a la list
	 * @param res est une ressource
	 */
	public void addRessource(String sRes)
	{
		Ressource ressource = new Ressource(sRes);
		boolean   bOk       = true;
		for (Ressource res : this.lstRessources)
		{
			if (res.getNom().equals(ressource.getNom()))
				bOk = false;
		}
		if (bOk)this.lstRessources.add(ressource);
		
		this.creerDossierRessource(ressource);
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

	////////////////
	// NOTIONS    //
	////////////////

	//ajouter une notion dans une ressource existante
	public void addNotion(String nomR, String n)
	{
		Notion notion = new Notion(n, null);
		Ressource res = this.rechercheRessource(nomR);

		res.addNotion(notion);
		this.creerDossierNotion(res , notion); 
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

	////////////////
	/// QUESTIONS //
	////////////////

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
		if (! type.equals("question à choix multiple à réponse unique") && ! type.equals("question à choix multiple à réponse multiple") &&
			! type.equals("question à association d’éléments")          && ! type.equals("question avec élimination de propositions de réponses"))
			erreur += "Aucun type de question choisi\n";

		//verifier si la difficulté est tres facile, facile, moyen ou difficile
		if (!difficulte.equalsIgnoreCase("Tres facile") && !difficulte.equalsIgnoreCase("Facile") && !difficulte.equalsIgnoreCase("Moyen") && !difficulte.equalsIgnoreCase("Difficile"))
			erreur += "La difficulté doit être Tres facile, Facile, Moyen ou Difficile\n";

		//verifier si le point est positif
		if (point < 0)
			erreur += "Le point doit être positif ou 0\n";

		//verifier si le temps est coherrant
		if (temps.equals(""))
			erreur += "Le temps ne peut pas être vide\n";
		else if (temps.indexOf(":") == -1)
			erreur += "Le temps doit être sous la forme mm:ss\n";
		
		//vérifier si secondes > 60
		else
		{
			String[] tab     = temps.split(":");
			int      timeMin = -1;
			int      timeSec = -1;

			boolean erreurTemps = false;
			try
			{
				timeMin = Integer.parseInt(tab[0]);
				timeSec = Integer.parseInt(tab[1]);
			}
			catch (Exception e)
			{
				erreur += "Le temps doit être sous la forme mm:ss\n";
				erreurTemps = true;
			}

			if (! erreurTemps)
			{
				if (timeMin < 0 || timeSec < 0)
					erreur += "Le temps doit être positif\n";
				if (timeSec >= 60)
					erreur += "Les secondes doivent être inférieures à 60\n";
			}
		}
		//retourne les erreurs si il y en a, sinon retourne une chaine vide
		return erreur;
	}

	public String creerQuestionElimination(String ressource, String notion, String question, String type, String explication, String difficulte, double point, String temps, ArrayList<String> ordreElimination, ArrayList<String> nbPointPerdu, ArrayList<String> lstRep, ArrayList<Boolean> validite)
	{
		String erreur = "";

		Ressource ress = null;
		ress = rechercheRessource(ressource);

		Difficulte diff = null;
		difficulte = difficulte.toLowerCase();

		if (difficulte.equals("tres facile"))
			diff = Difficulte.TF;
		else if (difficulte.equals("facile"))
			diff = Difficulte.F;
		else if (difficulte.equals("moyen"))
			diff = Difficulte.M;
		else if (difficulte.equals("difficile"))
			diff = Difficulte.D;

		String[] tab = temps.split(":");
		int timeMin  = Integer.parseInt(tab[0]);
		int timeSec  = Integer.parseInt(tab[1]);

		float tempsS = timeMin*60 + timeSec;

		ArrayList<ReponseElimination> lstReponses = new ArrayList<ReponseElimination>();

		boolean txtReponseValide           = true ;
		boolean ordreEliminationValide     = true ;
		boolean nbPointPerduValide         = true ;
		boolean auMoinsUneReponseSupprimer = false;

		if (question.length() == 0)
			erreur += "L'énnoncé de la question est vide\n";

		boolean auMoinsUneReponseCorrecte = false;
		for (boolean estValide:validite)
		{
			if (estValide)
				auMoinsUneReponseCorrecte = true ;
		}

		if (! auMoinsUneReponseCorrecte)
			erreur += "Au moins une réponse doit être valide\n";
		
		for (int i=0; i<lstRep.size(); i++)
		{
			if (lstRep.get(i).length() == 0 && txtReponseValide)
			{
				erreur += "Le texte d'une réponse est vide\n";
				txtReponseValide = false;
			}

			if (validite.get(i))
			{
				if (! ordreElimination.get(i).equals("") || ! nbPointPerdu.get(i).equals(""))
				{
					erreur = "La réponse valide ne doit pas avoir d'ordre d'élimination ou de point perdu\n";
				}
					
				lstReponses.add(new ReponseElimination(lstRep.get(i), -1, -1, validite.get(i)));
			}
			else
			{
				int    ordre   = -2;
				double nbPoint = -2;

				try
				{
					if (ordreElimination.get(i).equals(""))
						ordre = -1;
					else
					{
						ordre = Integer.parseInt(ordreElimination.get(i));
						if (ordre <= 0 && ordreEliminationValide)
						{
							erreur += "L'ordre d'élimination doit être positif\n";
							ordreEliminationValide = false;
						}
						else
							auMoinsUneReponseSupprimer = true;
					}
				}

				catch (Exception e)
				{
					if (ordreEliminationValide)
					{
						erreur += "Erreur format ordre élimination";
						ordreEliminationValide = false;
					}
				}

				try
				{
					if (nbPointPerdu.get(i).equals(""))
					{
						nbPoint = -1;
					}
					else
					{
						nbPoint = Double.parseDouble(nbPointPerdu.get(i));
						if (nbPoint > 0 && nbPointPerduValide)
						{
							erreur += "Le nombre de point enlevé doit être inférieur ou égal à 0\n";
							nbPointPerduValide = false;
						}
						if (ordre == -1)
						{
							erreur += "Ordre d'élimination vide\n";
						}
					}

				}
				catch (Exception e)
				{
					if (nbPointPerduValide)
					{
						erreur += "Erreur format nombre de point à enlever";
						nbPointPerduValide = false;
					}
				}

				if (erreur.length() == 0)
					lstReponses.add(new ReponseElimination(lstRep.get(i), ordre, nbPoint, validite.get(i)));
			}
		}
		if (! auMoinsUneReponseSupprimer)
		{
			erreur += "Au moins une erreur doit être supprimer\n";
		}
		
		if (erreur.length() == 0)
		{
			Elimination elvt = ress.ajouterQuestionElimination(notion, question, type, explication, diff, point, tempsS, lstReponses);
			this.ecriture.creerElimination(elvt, ressource + File.separator + notion);
		}
			

		return erreur;
	}

	public String creerQuestionElimination(String ressource, String notion, String question, String type, String explication, String difficulte, double point, String temps, ArrayList<String> ordreElimination, ArrayList<String> nbPointPerdu, ArrayList<String> lstRep, ArrayList<Boolean> validite, String path)
	{
		String erreur = "";

		Ressource ress = null;
		ress = rechercheRessource(ressource);

		Difficulte diff = null;
		difficulte = difficulte.toLowerCase();

		if (difficulte.equals("tres facile"))
			diff = Difficulte.TF;
		else if (difficulte.equals("facile"))
			diff = Difficulte.F;
		else if (difficulte.equals("moyen"))
			diff = Difficulte.M;
		else if (difficulte.equals("difficile"))
			diff = Difficulte.D;

		String[] tab = temps.split(":");
		int timeMin  = Integer.parseInt(tab[0]);
		int timeSec  = Integer.parseInt(tab[1]);

		float tempsS = timeMin*60 + timeSec;

		ArrayList<ReponseElimination> lstReponses = new ArrayList<ReponseElimination>();

		boolean txtReponseValide = true;
		boolean ordreEliminationValide = true;
		boolean nbPointPerduValide = true;
		boolean auMoinsUneReponseSupprimer = false;

		if (question.length() == 0)
			erreur += "L'énnoncé de la question est vide\n";

		boolean auMoinsUneReponseCorrecte = false;
		for (boolean estValide:validite)
		{
			if (estValide)
				auMoinsUneReponseCorrecte = true;
		}

		if (! auMoinsUneReponseCorrecte)
			erreur += "Au moins une réponse doit être valide\n";
		
		for (int i=0; i<lstRep.size(); i++)
		{
			if (lstRep.get(i).length() == 0 && txtReponseValide)
			{
				erreur += "Le texte d'une réponse est vide\n";
				txtReponseValide = false;
			}

			if (validite.get(i))
			{
				lstReponses.add(new ReponseElimination(lstRep.get(i), -1, -1, validite.get(i)));
			}
			else
			{
				int    ordre   = -2;
				double nbPoint = -2;

				try
				{
					if (ordreElimination.get(i).equals(""))
						ordre = -1;
					else
					{
						ordre = Integer.parseInt(ordreElimination.get(i));
						if (ordre <= 0 && ordreEliminationValide)
						{
							erreur += "L'ordre d'élimination doit être positif\n";
							ordreEliminationValide = false;
						}
						else
							auMoinsUneReponseSupprimer = true;
					}
				}

				catch (Exception e)
				{
					if (ordreEliminationValide)
					{
						erreur += "Erreur format ordre élimination";
						ordreEliminationValide = false;
					}
				}

				try
				{
					if (nbPointPerdu.get(i).equals(""))
					{
						nbPoint = -1;
					}
					else
					{
						nbPoint = Double.parseDouble(nbPointPerdu.get(i));
						if (nbPoint > 0 && nbPointPerduValide)
						{
							erreur += "Le nombre de point enlevé doit être inférieur ou égal à 0\n";
							nbPointPerduValide = false;
						}
						if (ordre == -1)
						{
							erreur += "Ordre d'élimination vide\n";
						}
					}

				}
				catch (Exception e)
				{
					if (nbPointPerduValide)
					{
						erreur += "Erreur format nombre de point à enlever";
						nbPointPerduValide = false;
					}
				}

				if (erreur.length() == 0)
					lstReponses.add(new ReponseElimination(lstRep.get(i), ordre, nbPoint, validite.get(i)));
			}
		}
		if (! auMoinsUneReponseSupprimer)
		{
			erreur += "Au moins une erreur doit être supprimer\n";
		}
		
		if (erreur.length() == 0)
		{
			Elimination elvt = null;
			if(path != null)
				elvt = ress.ajouterQuestionElimination(notion, question, type, explication, diff, point, tempsS, lstReponses, path);
			else
				elvt = ress.ajouterQuestionElimination(notion, question, type, explication, diff, point, tempsS, lstReponses);
			this.ecriture.creerElimination(elvt, ressource + File.separator + notion);
		}
			

		return erreur;
	}

	public String creerQuestionQCM(String ressource, String notion, String question, String type, String explication, String difficulte, double point, String temps, ArrayList<String> lstRep, ArrayList<Boolean> validite)
	{
		if (question == null) question = "";

		Ressource ress = null;
		ress = rechercheRessource(ressource);

		String erreur = "";

		if (question.equals(""))
			erreur += "La question ne peut pas être vide\n";

		Difficulte diff = null;

		difficulte = difficulte.toLowerCase();

		if (difficulte.equals("tres facile"))
			diff = Difficulte.TF;
		else if (difficulte.equals("facile"))
			diff = Difficulte.F;
		else if (difficulte.equals("moyen"))
			diff = Difficulte.M;
		else if (difficulte.equals("difficile"))
			diff = Difficulte.D;
			
		//temps
		//transformer le temps (mm::ss) en minutes a virgule
		//si le temps est négatif ou égal à 0
		int timeMin  = 0;
		int timeSec  = 0;
		String[] tab = temps.split(":");
		timeMin      = Integer.parseInt(tab[0]);
		timeSec      = Integer.parseInt(tab[1]);

		float tempsS = timeMin*60 + timeSec;

		//creer la liste de reponses par la list de string entree en parametre
		ArrayList<ReponseQcm> lstReponses = new ArrayList<ReponseQcm>();
		//vérifier si la liste de reponses est vide
		boolean auMoinsUneReponseVrai = false;
		boolean texteReponseValide    = true ;

		if (lstRep.size() == 0)
			erreur += "Il faut au moins une réponse\n";
		else
		{
			for (int i=0; i<lstRep.size(); i++)
			{
				if (lstRep.get(i).length() == 0)
					texteReponseValide = false;
				if (validite.get(i) == true)
					auMoinsUneReponseVrai = true;
				lstReponses.add(new ReponseQcm(lstRep.get(i), validite.get(i)));
			}
		}
		
		// Verif si il y a au moins une réponse vrai (lstRep.size() != 0 pour que si il n'y a aucune réponse ça n'affiche pas ces erreurs)
		if (! auMoinsUneReponseVrai && lstRep.size() != 0)
		{
			erreur += "Au moins une réponse doit être correcte\n";
		}
		if (! texteReponseValide && lstRep.size() != 0)
		{
			erreur += "Au moin une des réponses n'a pas de texte\n";
		}
		
		//si il n'y a pas d'erreur appeler la methode ajouterQuestion de la ressource
		if (erreur.equals(""))
		{
			QCM q = ress.ajouterQuestionQCM(notion, question, explication, diff, point, tempsS, lstReponses);
			this.ecriture.creerQCM(q, ressource + File.separator + notion);
		}
		return erreur;
	}

	public String creerQuestionQCM(String Sressource, String notion, String question, String type, String explication, String difficulte, double point, String temps, ArrayList<String> lstRep, ArrayList<Boolean> validite, String path)
	{
		if (question == null) question = "";

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

		//difficulte
		Difficulte diff = null;
		difficulte = difficulte.toLowerCase();

		if (difficulte.equals("tres facile"))
			diff = Difficulte.TF;
		else if (difficulte.equals("facile"))
			diff = Difficulte.F;
		else if (difficulte.equals("moyen"))
			diff = Difficulte.M;
		else if (difficulte.equals("difficile"))
			diff = Difficulte.D;
			
		//temps
		//transformer le temps (mm::ss) en minutes a virgule
		//si le temps est négatif ou égal à 0
		int timeMin  = 0;
		int timeSec  = 0;
		String[] tab = temps.split(":");
		timeMin      = Integer.parseInt(tab[0]);
		timeSec      = Integer.parseInt(tab[1]);

		float tempsS = timeMin*60 + timeSec;

		//creer la liste de reponses par la list de string entree en paremetre
		ArrayList<ReponseQcm> lstReponses = new ArrayList<ReponseQcm>();
		//vérifier si la liste de reponses est vide
		boolean auMoinsUneReponseVrai = false;
		boolean texteReponseValide    = true;

		if (lstRep.size() == 0)
			erreur += "Il faut au moins une réponse\n";
		else
		{
			for (int i=0; i<lstRep.size(); i++)
			{
				if (lstRep.get(i).length() == 0)
					texteReponseValide = false;
				if (validite.get(i) == true)
					auMoinsUneReponseVrai = true;
				lstReponses.add(new ReponseQcm(lstRep.get(i), validite.get(i)));
			}
		}
		
		// Verif si il y a au moins une réponse vrai (lstRep.size() != 0 pour que si il n'y a aucune réponse ça n'affiche pas ces erreurs)
		if (! auMoinsUneReponseVrai && lstRep.size() != 0)
		{
			erreur += "Au moins une réponse doit être correcte\n";
		}
		if (! texteReponseValide && lstRep.size() != 0)
		{
			erreur += "Au moin une des réponses n'a pas de texte\n";
		}
		
		//si il n'y a pas d'erreur appeler la methode ajouterQuestion de la ressource
		if (erreur.equals(""))
		{
			QCM q = null;
			if(path != null)
				q = ressource.ajouterQuestionQCM(notion, question, explication, diff, point, tempsS, lstReponses, path);
			else
				q = ressource.ajouterQuestionQCM(notion, question, explication, diff, point, tempsS, lstReponses);
			this.ecriture.creerQCM(q, Sressource + File.separator + notion);
		}
		return erreur;
	}

	public String creerQuestionAsso(String Sressource, String notion, String question, String type, String explication, String difficulte, double point, String temps, ArrayList<String> lstRep, String path)
	{
		if (question == null) question = "";

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

		//difficulte
		Difficulte diff = null;
		difficulte = difficulte.toLowerCase();

		if (difficulte.equals("tres facile"))
			diff = Difficulte.TF;
		else if (difficulte.equals("facile"))
			diff = Difficulte.F;
		else if (difficulte.equals("moyen"))
			diff = Difficulte.M;
		else if (difficulte.equals("difficile"))
			diff = Difficulte.D;

		//point
		if (point < 0)
			erreur += "Le point doit être positif ou 0\n";
			
		//temps
		//transofrmer le temps (mm::ss) en minutes a virgule
		//si le temps est négatif ou égal à 0
		int timeMin = 0;
		int timeSec = 0;
		if (temps.equals(""))
			erreur += "Le temps ne peut pas être vide\n";
		if (temps.indexOf(":") == -1)
			erreur += "Le temps doit être sous la forme mm:ss\n";
		//vérifier si secondes > 60
		else
		{
			String[] tab = temps.split(":");
			timeMin      = Integer.parseInt(tab[0]);
			timeSec      = Integer.parseInt(tab[1]);
			if (timeMin < 0 || timeSec < 0)
				erreur += "Le temps doit être positif\n";
			if (timeSec >= 60)
				erreur += "Les secondes doivent être inférieures à 60\n";
		}

		float tempsS = timeMin*60 + timeSec;

		//creer la liste de reponses par la list de string entree en paremetre
		ArrayList<ReponseAsso> lstReponses = new ArrayList<ReponseAsso>();
		//vérifier si la liste de reponses est vide

		if (lstRep.size() == 0)
			erreur += "Il faut au moins une réponse\n";
		else
		{
			for (int i=0; i<lstRep.size()/2; i++)
			{
				if (lstRep.get(i).length() == 0 || lstRep.get(i + (lstRep.size()/2)).length() == 0 || lstRep.get(i).equals(null) || lstRep.get(i).equals("") || lstRep.get(i).equals(null) || lstRep.get(i-1 + (lstRep.size()/2)).equals(""))
				{
					erreur = "Reponse sans texte";
				}
				else
				{
					//cree les reponse
					lstReponses.add(new ReponseAsso(lstRep.get(i), null));
					ReponseAsso rep2 = new ReponseAsso(lstRep.get(i + (lstRep.size()/2)), null);
					//associe les 2 reponse 
					lstReponses.get(i).setAssocie(rep2);
					rep2.setAssocie(lstReponses.get(i));
				}
			}
		}
		
		//si il n'y a pas d'erreur appeler la methode ajouterQuestion de la ressource
		if (erreur.equals(""))
		{
			Association a = null;
			if(path != null)
				a = ressource.ajouterQuestionAsso(notion, question, type, explication, diff, point, tempsS, lstReponses, path);
			else
				a = ressource.ajouterQuestionAsso(notion, question, type, explication, diff, point, tempsS, lstReponses);
			this.ecriture.creerAssociation(a, Sressource + File.separator + notion);
		}
		return erreur;
	}

	public String modifQuestionQCM(String ressource, String notion, String question, String type, String explication, String difficulte, double point, String temps, ArrayList<String> lstRep, ArrayList<Boolean> validite, QCM questionAModifier, String path)
	{
		if (question == null) question = "";

		Ressource ressourceQuestion = null;
		Notion    notionQuestion    = null;
		for(Ressource res:this.lstRessources)
		{
			for (Notion not:res.getNotions())
			{
				for (Question quest:not.getLstQuestions())
				{
					if (quest.getQuestion().equals(questionAModifier.getQuestion()) && quest.getDifficulte().equals(questionAModifier.getDifficulte()) &&
						quest.getExplication().equals(questionAModifier.getExplication()) && quest.getPoint() == questionAModifier.getPoint() && quest.getTemps() == questionAModifier.getTemps())
					{
						ressourceQuestion = res;
						notionQuestion    = not;
					}
				}
			}
		}

		String erreur = "";
		//question
		if (question.equals(""))
			erreur += "La question ne peut pas être vide\n";

		//difficulte
		Difficulte diff = null;
		//mettre en minuscule la difficulté
		diff = questionAModifier.getDifficulte();
			
		//temps
		float tempsS = questionAModifier.getTemps();

		//creer la liste de reponses par la list de string entree en paremetre
		ArrayList<ReponseQcm> lstReponses = new ArrayList<ReponseQcm>();
		//vérifier si la liste de reponses est vide
		boolean auMoinsUneReponseVrai = false;
		boolean texteReponseValide = true;

		if (lstRep.size() == 0)
			erreur += "Il faut au moins une réponse\n";
		else
		{
			for (int i=0; i<lstRep.size(); i++)
			{
				if (lstRep.get(i).length() == 0)
					texteReponseValide = false;
				if (validite.get(i) == true)
					auMoinsUneReponseVrai = true;
				lstReponses.add(new ReponseQcm(lstRep.get(i), validite.get(i)));
			}
		}
		
		// Verif si il y a au moins une réponse vrai (lstRep.size() != 0 pour que si il n'y a aucune réponse ça n'affiche pas ces erreurs)
		if (! auMoinsUneReponseVrai && lstRep.size() != 0)
		{
			erreur += "Au moins une réponse doit être correcte\n";
		}
		if (! texteReponseValide && lstRep.size() != 0)
		{
			erreur += "Au moin une des réponses n'a pas de texte\n";
		}
		
		//si il n'y a pas d'erreur appeler la methode ajouterQuestion de la ressource
		if (erreur.equals(""))
		{
			ressourceQuestion.supprimerQuestion(questionAModifier, notionQuestion);
			ressourceQuestion.ajouterQuestionQCM(notionQuestion.getNom(), question, explication, diff, point, tempsS, lstReponses);

			String chemin = this.ecriture.rechercherFichierQuestion(questionAModifier, ressourceQuestion, notionQuestion);
			File ancierFichier = new File(chemin);
			System.out.println("chemin -> " +chemin);
			System.out.println("chemin modif  -> " +chemin.substring(0, chemin.lastIndexOf(File.separator)));
			File ancienDossier = new File(chemin.substring(0, chemin.lastIndexOf(File.separator)));
			ancierFichier.delete();
			
			File dossierTemp = new File("../temp");
			if (! dossierTemp.exists())
				dossierTemp.mkdir();

			for (File file:ancienDossier.listFiles())
			{
				if (file.isDirectory())
				{
					for (File fileDir:file.listFiles())
					{
						try
						{
							if (path == null)
							{
								Path source      = Paths.get(fileDir.getPath());
								Path destination = Paths.get(dossierTemp.getPath() + File.separator + fileDir.getName());
								Files.copy(source, destination);
							}
						} catch (IOException e) {}

						fileDir.delete();
					}
				}
				file.delete();
			}
			
			ancienDossier.delete();
			questionAModifier.setDifficulte(diff);
			questionAModifier.setExplication(explication);
			questionAModifier.setPoint(point);
			questionAModifier.setQuestion(question);
			questionAModifier.setTemps(tempsS);
			questionAModifier.setReponses(lstRep, validite);

			if (path != null)
				questionAModifier.setPath(path);
			
			this.ecriture.creerQCM(questionAModifier, ressourceQuestion.getNom() + File.separator + notionQuestion.getNom());

			File dossierComp = new File(this.ecriture.rechercherFichierQuestion(questionAModifier, ressourceQuestion, notionQuestion));
			dossierComp = new File(dossierComp.getParent() + File.separator + "complement");
			
			if (! dossierComp.exists() && (dossierTemp.list().length > 0))
				dossierComp.mkdir();
			
			try
			{
				if (path == null)
				{
					if (dossierTemp.list().length > 0)
					{
						Path source      = Paths.get(dossierTemp.getAbsolutePath() + File.separator + dossierTemp.list()[0]);
						Path destination = Paths.get(dossierComp.getAbsolutePath() + File.separator + dossierTemp.list()[0]);
						Files.copy(source, destination);
					}

				}

			} catch (IOException e) {}


			for (File f:dossierTemp.listFiles())
				f.delete();
			
			dossierTemp.delete();
		}
		return erreur;
	}

	public static void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                deleteDirectory(file); // Appel récursif pour supprimer les fichiers/sous-dossiers
            }
        }
        directory.delete(); // Supprime le fichier ou dossier vide
    }

	public String modifQuestionAsso(String Sressource, String notion, String question, String type, String explication, String difficulte, double point, String temps, ArrayList<String> lstRep, Association questionAModifier, String path)
	{
		if (question == null) question = "";

		Ressource ressourceQuestion = null;
		Notion    notionQuestion    = null;
		for(Ressource res:this.lstRessources)
		{
			for (Notion not:res.getNotions())
			{
				for (Question quest:not.getLstQuestions())
				{
					if (quest.getQuestion().equals(questionAModifier.getQuestion()) && quest.getDifficulte().equals(questionAModifier.getDifficulte()) &&
						quest.getExplication().equals(questionAModifier.getExplication()) && quest.getPoint() == questionAModifier.getPoint() && quest.getTemps() == questionAModifier.getTemps())
					{
						ressourceQuestion = res;
						notionQuestion    = not;
					}
				}
			}
		}

		String erreur = "";
		//question
		if (question.equals(""))
			erreur += "La question ne peut pas être vide\n";

		//difficulte
		Difficulte diff = null;
		//mettre en minuscule la difficulté
		diff = questionAModifier.getDifficulte();
			
		//temps
		float tempsS = questionAModifier.getTemps();

		//point
		if (point < 0)
			erreur += "Le point doit être positif ou 0\n";

		//creer la liste de reponses par la list de string entree en paremetre
		ArrayList<ReponseAsso> lstReponses = new ArrayList<ReponseAsso>();
		//vérifier si la liste de reponses est vide
		boolean auMoinsUneReponseVrai = false;
		boolean texteReponseValide = true;

		if (lstRep.size() == 0)
			erreur += "Il faut au moins une réponse\n";
		else
		{
			for (int i=0; i<lstRep.size(); i = i + 2)
			{
				if (lstRep.get(i).length() == 0 || lstRep.get(i + 1).length() == 0)
					texteReponseValide = false;

				//cree les reponse
				lstReponses.add(new ReponseAsso(lstRep.get(i  ), null));
				lstReponses.add(new ReponseAsso(lstRep.get(i+1), null));
				//associe les 2 reponse 
				lstReponses.get(i  ).setAssocie(lstReponses.get(i+1));
				lstReponses.get(i+1).setAssocie(lstReponses.get(i  ));
			}
		}
		
		//si il n'y a pas d'erreur appeler la methode ajouterQuestion de la ressource
		if (erreur.equals(""))
		{
			ressourceQuestion.supprimerQuestion(questionAModifier, notionQuestion);
			ressourceQuestion.ajouterQuestionAsso(notion, question, type, explication, diff, point, tempsS, lstReponses);

			String chemin = this.ecriture.rechercherFichierQuestion(questionAModifier, ressourceQuestion, notionQuestion);
			File ancierFichier = new File(chemin);
			File ancienDossier = new File(chemin.substring(0, chemin.lastIndexOf(File.separator)));
			ancierFichier.delete();
			
			File dossierTemp = new File("../temp");
			if (! dossierTemp.exists())
				dossierTemp.mkdir();

			for (File file:ancienDossier.listFiles())
			{
				if (file.isDirectory())
				{
					for (File fileDir:file.listFiles())
					{
						try
						{
							if (path == null)
							{
								Path source      = Paths.get(fileDir.getPath());
								Path destination = Paths.get(dossierTemp.getPath() + File.separator + fileDir.getName());
								Files.copy(source, destination);
							}
						} catch (IOException e) {}

						fileDir.delete();
					}
				}
				file.delete();
			}
			
			ancierFichier.delete();
			ancienDossier.delete();
			questionAModifier.setDifficulte(diff);
			questionAModifier.setExplication(explication);
			questionAModifier.setPoint(point);
			questionAModifier.setQuestion(question);
			questionAModifier.setTemps(tempsS);
			questionAModifier.setReponses(lstReponses);

			if (path != null)
				questionAModifier.setPath(path);
			
			this.ecriture.creerAssociation(questionAModifier, ressourceQuestion.getNom() + File.separator + notionQuestion.getNom());

			File dossierComp = new File(this.ecriture.rechercherFichierQuestion(questionAModifier, ressourceQuestion, notionQuestion));
			dossierComp = new File(dossierComp.getParent() + File.separator + "complement");
			
			if (! dossierComp.exists() && (dossierTemp.list().length > 0))
				dossierComp.mkdir();
			
			try
			{
				if (path == null)
				{
					if (dossierTemp.list().length > 0)
					{
						Path source      = Paths.get(dossierTemp.getAbsolutePath() + File.separator + dossierTemp.list()[0]);
						Path destination = Paths.get(dossierComp.getAbsolutePath() + File.separator + dossierTemp.list()[0]);
						Files.copy(source, destination);
					}

				}

			} catch (IOException e) {}


			for (File f:dossierTemp.listFiles())
				f.delete();
			
			dossierTemp.delete();
			

		}
		return erreur;
	}

	public String modifQuestionElimination(String ressource, String notion, String question, String type, String explication, String difficulte, double point, String temps, ArrayList<String> ordreElimination, ArrayList<String> nbPointPerdu, ArrayList<String> lstRep, ArrayList<Boolean> validite, Elimination questionAModifier, String path)
	{
		String erreur = "";

		Ressource ressourceQuestion = null;
		Notion    notionQuestion    = null;
		for(Ressource res:this.lstRessources)
		{
			for (Notion not:res.getNotions())
			{
				for (Question quest:not.getLstQuestions())
				{
					if (quest.getQuestion().equals(questionAModifier.getQuestion()) && quest.getDifficulte().equals(questionAModifier.getDifficulte()) &&
						quest.getExplication().equals(questionAModifier.getExplication()) && quest.getPoint() == questionAModifier.getPoint() && quest.getTemps() == questionAModifier.getTemps())
					{
						ressourceQuestion = res;
						notionQuestion    = not;
					}
				}
			}
		}

		//difficulte
		Difficulte diff = null;
		diff = questionAModifier.getDifficulte();
			
		//temps
		float tempsS = questionAModifier.getTemps();

		ArrayList<ReponseElimination> lstReponses = new ArrayList<ReponseElimination>();

		boolean txtReponseValide           = true ;
		boolean ordreEliminationValide     = true ;
		boolean nbPointPerduValide         = true ;
		boolean auMoinsUneReponseSupprimer = false;

		if (question.length() == 0)
			erreur += "L'énnoncé de la question est vide\n";

		boolean auMoinsUneReponseCorrecte = false;
		for (boolean estValide:validite)
		{
			if (estValide)
				auMoinsUneReponseCorrecte = true;
		}

		if (! auMoinsUneReponseCorrecte)
			erreur += "Au moins une réponse doit être valide\n";
		
		for (int i=0; i<lstRep.size(); i++)
		{
			if (lstRep.get(i).length() == 0 && txtReponseValide)
			{
				erreur += "Le texte d'une réponse est vide\n";
				txtReponseValide = false;
			}

			if (validite.get(i))
			{
				lstReponses.add(new ReponseElimination(lstRep.get(i), -1, -1, validite.get(i)));
			}
			else
			{
				int    ordre   = -2;
				double nbPoint = -2;

				try
				{
					if (ordreElimination.get(i).equals(""))
						ordre = -1;
					else
					{
						ordre = Integer.parseInt(ordreElimination.get(i));
						if (ordre <= 0 && ordreEliminationValide)
						{
							erreur += "L'ordre d'élimination doit être positif\n";
							ordreEliminationValide = false;
						}
						else
							auMoinsUneReponseSupprimer = true;
					}
				}

				catch (Exception e)
				{
					if (ordreEliminationValide)
					{
						erreur += "Erreur format ordre élimination";
						ordreEliminationValide = false;
					}
				}

				try
				{
					if (nbPointPerdu.get(i).equals(""))
					{
						nbPoint = -1;
					}
					else
					{
						nbPoint = Double.parseDouble(nbPointPerdu.get(i));
						if (nbPoint > 0 && nbPointPerduValide)
						{
							erreur += "Le nombre de point enlevé doit être inférieur ou égal à 0\n";
							nbPointPerduValide = false;
						}
						if (ordre == -1)
						{
							erreur += "Ordre d'élimination vide\n";
						}
					}

				}
				catch (Exception e)
				{
					if (nbPointPerduValide)
					{
						erreur += "Erreur format nombre de point à enlever";
						nbPointPerduValide = false;
					}
				}

				if (erreur.length() == 0)
					lstReponses.add(new ReponseElimination(lstRep.get(i), ordre, nbPoint, validite.get(i)));
			}
		}
		if (! auMoinsUneReponseSupprimer)
		{
			erreur += "Au moins une erreur doit être supprimer\n";
		}
		
		if (erreur.length() == 0)
		{
			ressourceQuestion.supprimerQuestion(questionAModifier, notionQuestion);
			ressourceQuestion.ajouterQuestionElimination(notion, question, type, explication, diff, point, tempsS, lstReponses);

			String chemin = this.ecriture.rechercherFichierQuestion(questionAModifier, ressourceQuestion, notionQuestion);
			File ancierFichier = new File(chemin);
			File ancienDossier = new File(chemin.substring(0, chemin.lastIndexOf(File.separator)));
			ancierFichier.delete();
			
			File dossierTemp = new File("../temp");
			if (! dossierTemp.exists())
				dossierTemp.mkdir();

			for (File file:ancienDossier.listFiles())
			{
				if (file.isDirectory())
				{
					for (File fileDir:file.listFiles())
					{
						try
						{
							if (path == null)
							{
								Path source      = Paths.get(fileDir.getPath());
								Path destination = Paths.get(dossierTemp.getPath() + File.separator + fileDir.getName());
								Files.copy(source, destination);
							}
						} catch (IOException e) {}

						fileDir.delete();
					}
				}
				file.delete();
			}
			ancienDossier.delete();

			questionAModifier.setDifficulte(diff);
			questionAModifier.setExplication(explication);
			questionAModifier.setPoint(point);
			questionAModifier.setQuestion(question);
			questionAModifier.setTemps(tempsS);
			questionAModifier.setReponses(lstReponses);

			if (path != null)
				questionAModifier.setPath(path);
			
			this.ecriture.creerElimination(questionAModifier, ressourceQuestion.getNom() + File.separator + notionQuestion.getNom());

			File dossierComp = new File(this.ecriture.rechercherFichierQuestion(questionAModifier, ressourceQuestion, notionQuestion));
			dossierComp = new File(dossierComp.getParent() + File.separator + "complement");
			
			if (! dossierComp.exists() && (dossierTemp.list().length > 0))
				dossierComp.mkdir();
			
			try
			{
				if (path == null)
				{
					if (dossierTemp.list().length > 0)
					{
						Path source      = Paths.get(dossierTemp.getAbsolutePath() + File.separator + dossierTemp.list()[0]);
						Path destination = Paths.get(dossierComp.getAbsolutePath() + File.separator + dossierTemp.list()[0]);
						Files.copy(source, destination);
					}

				}

			} catch (IOException e) {}


			for (File f:dossierTemp.listFiles())
				f.delete();
			
			dossierTemp.delete();
		}


		return erreur;
	}

	public String rechercherFichierQuestion (Question question,Ressource res, Notion not)
	{
		return this.ecriture.rechercherFichierQuestion(question, res, not);
	}

	public void supprimerComplement(Question question, String res, String not)
	{
		String chemin = rechercherFichierQuestion(question, this.rechercheRessource(res), this.rechercheNotion(res, not));
		chemin = chemin.substring(0, chemin.lastIndexOf(File.separator)) + File.separator + "complement";
		File dossierComplement = new File(chemin);
		
		if (dossierComplement.exists())
		{
			for (File f:dossierComplement.listFiles())
			{
				f.delete();
			}
			dossierComplement.delete();
		}
	}

	///////////////////
	// Evaluation    //
	///////////////////
	 
	//valide si la ressource selectionnée existe contient des ressources et contient des questions. verifie si chrno est "oui" ou "non"
	public String validerEvaluation(String r,boolean chrono, HashMap<String, int[]> nbQuestParNotionDiff)
	{
		
		String erreur = "";
		//verifier si la ressource existe
		if (rechercheRessource(r) == null)
			erreur += "La ressource n'existe pas\n";
		else //verifier si la ressource contient des notions
		if (rechercheRessource(r).getNotions().size() == 0)
			erreur += "La ressource ne contient pas de notions\n";
		else //verifier si la notion contient des questions
		if (rechercheRessource(r).getNotions().get(0).getLstQuestions().size() == 0)
			erreur += "La notion ne contient pas de questions\n";

		//verifier si chrono est true ou false
		if (! chrono && chrono)
			erreur += "Le chrono doit être 'oui' ou 'non'\n";

		//verifier si chrono est true ou false
		if (! chrono && chrono)
			erreur += "Le chrono doit être 'oui' ou 'non'\n";

		//verifier si le nombre de questions par notion et par difficulté est cohérent
		for (String notion : nbQuestParNotionDiff.keySet())
		{
			int[] tab = nbQuestParNotionDiff.get(notion);
			if (tab[0] < 0 || tab[1] < 0 || tab[2] < 0 || tab[3] < 0)
				erreur += "Le nombre de questions doit être positif\n";
			//verifier si le nombre slecetionné est inférieur ou égal au nombre de questions de la difficulté
			Notion not = rechercheNotion(r, notion);
			if (not == null) 
			{
				erreur += "Notion null \n";
			}
			else if (tab[0] > not.getNbQuestionsTresFacile() || tab[1] > not.getNbQuestionsFacile() || tab[2] > not.getNbQuestionsMoyenne() || tab[3] > not.getNbQuestionsDifficile())
			{
				erreur += "Notion[" + notion +  "] : Le nombre de questions doit être inférieur ou égal au nombre de questions de la difficulté\n";
				//detailler les erreurs
				if (tab[0] > not.getNbQuestionsTresFacile())
					erreur += "Tres facile : " + tab[0] + " > " + not.getNbQuestionsTresFacile() + "\n";
				if (tab[1] > not.getNbQuestionsFacile())
					erreur += "Facile : " + tab[1] + " > " + not.getNbQuestionsFacile() + "\n";
				if (tab[2] > not.getNbQuestionsMoyenne())
					erreur += "Moyen : " + tab[2] + " > " + not.getNbQuestionsMoyenne() + "\n";
				if (tab[3] > not.getNbQuestionsDifficile())
					erreur += "Difficile : " + tab[3] + " > " + not.getNbQuestionsDifficile() + "\n";
			}
		}

		return erreur;
	}
	
	//generer un Evaluation
	//appel genererEvaluation de la classe Ressource en associant la ressource, si il y a un chrono
	// le duree si le chrno est oui, la liste de notions et le nombre de questions
	public ArrayList<Question> genererEvaluation(String r, boolean chrono, HashMap<String, int[]> nbQuestParNotionDiff, String nomFichier, String emplacement)
	{
		ArrayList<Question> lstQuestionsGenere = new ArrayList<Question>();
		ArrayList<Notion>   lstNotions         = new ArrayList<Notion>  ();
		
		Ressource           ressource = rechercheRessource(r);
		Notion              not       = null;

		//ajouter les question de chaque notion selon la difficulte
		for (String nomNotion : nbQuestParNotionDiff.keySet()) 
		{
			not = rechercheNotion(r, nomNotion);
			lstNotions.add(not);
			lstQuestionsGenere.addAll(not.aleaQuestions(nbQuestParNotionDiff.get(nomNotion)[0], nbQuestParNotionDiff.get(nomNotion)[1], nbQuestParNotionDiff.get(nomNotion)[2], nbQuestParNotionDiff.get(nomNotion)[3]));
		}
		
		//generer le web de l'evaluation
		this.webEval(new Evaluation(ressource, lstNotions, lstQuestionsGenere, chrono), nomFichier, emplacement);
		return lstQuestionsGenere;
	}

	//afficher les questions IHM
	public void afficherQuestionsIHM(ArrayList<Question> lstQuestions)
	{
		
	}

	private void webEval(Evaluation eval, String nomFichier, String emplacement)
	{
		new EcritureWeb(eval, nomFichier, emplacement);
	}



	/////////////
	// GETTERS //
	/////////////

	public Lire getLecteur()
	{
		return this.lecteur;
	}

	public List<Ressource> getLstRessource()
	{
		return this.lstRessources;
	}
	
	/////////////
	// SETTERS //
	/////////////

	public void setLstRessource(ArrayList<Ressource> lstRessources)
	{
		this.lstRessources = lstRessources;
	}

	/////////
	// CUI //
	/////////

	//afficher ressources
	public String afficherRessources()
	{
		String res = "";
		for (Ressource r : this.lstRessources)
		{
			res += r.afficherRessourceDetail() + "\n";
		}
		return res;
	}

	//generer un Evaluation
	public Evaluation genererQCM(Scanner sc)
	{
		return Evaluation.genererEvaluation(sc, this);
	}

}
