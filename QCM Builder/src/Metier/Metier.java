/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */

package Metier;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;

public class Metier 
{
	private List<Ressource> lstRessources;
	private Lire lecteur;
	private Ecriture ecriture;

	//////////////////
	// CONSTRUCTEUR //
	//////////////////

	public Metier()
	{
		this.lstRessources = new ArrayList<>();
		this.lecteur  = new Lire    ("QCM Builder" + File.separator + "ressources");
		this.ecriture = new Ecriture("QCM Builder" + File.separator + "ressources");
		this.init();
	}

	/**
	 * Initialise les ressources et leurs notions à partir des dossiers et fichiers présents
	 * dans le répertoire 'ressources'.
	 */
	public void init() 
	{
		ArrayList<Ressource> lstRessources  = new ArrayList<>();
		ArrayList<String>    nomsRessources = getLecteur().lireDossier("");
	
		for (int i = 0; i < nomsRessources.size(); i++) 
		{
			ArrayList<Notion> lstNotion   = new ArrayList<Notion>();
			ArrayList<String  > nomsNotion = getLecteur().lireDossier(nomsRessources.get(i));
	
			for (int j = 0; j < nomsNotion.size(); j++) 
			{
				ArrayList<Question> lstQuestions = new ArrayList<>();
				String cheminQuestion = lecteur.getEmplacementRessources()+ File.separator + nomsRessources.get(i) + File.separator + nomsNotion.get(j);
	
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

	public boolean renommerDossier(String ancienNom, String nouveauNom)
	{
		return this.ecriture.renommerDossier(ancienNom, nouveauNom);
	}

	public boolean supprimerDossier(String nomDossier)
	{
		return this.ecriture.supprimerDossier(nomDossier);
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
	public void addRessource(Ressource res)
	{
		boolean bOk = true;
		for (Ressource ressource : this.lstRessources)
		{
			if (ressource.getNom().equals(res.getNom()))
				bOk = false;
		}
		if (bOk)this.lstRessources.add(res);
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
	public void addNotion(String nomR, Notion n)
	{
		for (Ressource ressource : this.lstRessources) 
		{
			if (ressource.getNom().equals(nomR))
				ressource.addNotion(n);
		}
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
		if (!difficulte.equalsIgnoreCase("Tres facile") && !difficulte.equalsIgnoreCase("Facile") && !difficulte.equalsIgnoreCase("Moyenne") && !difficulte.equalsIgnoreCase("Difficile"))
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
			String[] tab = temps.split(":");
			int timeMin = -1;
			int timeSec = -1;

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

		//associer r à la ressource portant le même nom dans la liste
		Ressource ress = null;
		ress = rechercheRessource(ressource);

		Difficulte diff = null;
		difficulte = difficulte.toLowerCase();
		//associer le string difficulté à un objet Difficulte
		if (difficulte.equals("tres facile"))
			diff = Difficulte.TF;
		else if (difficulte.equals("facile"))
			diff = Difficulte.F;
		else if (difficulte.equals("moyenne"))
			diff = Difficulte.M;
		else if (difficulte.equals("difficile"))
			diff = Difficulte.D;

		String[] tab = temps.split(":");
		int timeMin = Integer.parseInt(tab[0]);
		int timeSec = Integer.parseInt(tab[1]);
		//calculer le temps en en numérique 1h30 = 1,5h
		float tempsS = timeMin + (timeSec/60);

		ArrayList<ReponseEnlevement> lstReponses = new ArrayList<ReponseEnlevement>();

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
				lstReponses.add(new ReponseEnlevement(lstRep.get(i), -1, -1, validite.get(i)));
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
					lstReponses.add(new ReponseEnlevement(lstRep.get(i), ordre, nbPoint, validite.get(i)));
			}
		}
		if (! auMoinsUneReponseSupprimer)
		{
			erreur += "Au moins une erreur doit être supprimer\n";
		}
		
		if (erreur.length() == 0)
		{
			Enlevement elvt = ress.ajouterQuestionEnleve(notion, question, type, explication, diff, point, tempsS, lstReponses);
			this.ecriture.creerElimination(elvt, ressource + File.separator + notion);
		}
			

		return erreur;
	}

	public String creerQuestionQCM(String ressource, String notion, String question, String type, String explication, String difficulte, double point, String temps, ArrayList<String> lstRep, ArrayList<Boolean> validite)
	{
		if (question == null) question = "";

		//associer r à la ressource portant le même nom dans la liste
		Ressource ress = null;
		ress = rechercheRessource(ressource);

		String erreur = "";
		//question
		if (question.equals(""))
			erreur += "La question ne peut pas être vide\n";

		//difficulte
		Difficulte diff = null;
		//mettre en minuscule la difficulté
		difficulte = difficulte.toLowerCase();
		//associer le string difficulté à un objet Difficulte
		if (difficulte.equals("tres facile"))
			diff = Difficulte.TF;
		else if (difficulte.equals("facile"))
			diff = Difficulte.F;
		else if (difficulte.equals("moyenne"))
			diff = Difficulte.M;
		else if (difficulte.equals("difficile"))
			diff = Difficulte.D;
			
		//temps
		//transofrmer le temps (mm::ss) en minutes a virgule
		//si le temps est négatif ou égal à 0
		int timeMin =0;
		int timeSec =0;
		String[] tab = temps.split(":");
		timeMin = Integer.parseInt(tab[0]);
		timeSec = Integer.parseInt(tab[1]);
		//calculer le temps en en numérique 1h30 = 1,5h
		float tempsS = timeMin + (timeSec/60);

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
			QCM q = ress.ajouterQuestionQCM(notion, question, explication, diff, point, tempsS, lstReponses);
			this.ecriture.creerQCM(q, ressource + File.separator + notion);
		}
		return erreur;
	}

	public String creerQuestionAsso(String Sressource, String notion, String question, String type, String explication, String difficulte, double point, String temps, ArrayList<String> lstRep)
	{
		if (question == null) question = "";

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
			Association a = ressource.ajouterQuestionAsso(notion, question, type, explication, diff, point, tempsS, lstReponses);
			this.ecriture.creerAssociation(a, Sressource + File.separator + notion);
		}
		return erreur;
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

		//verifier si la ressource contient des notions
		if (rechercheRessource(r).getNotions().size() == 0)
			erreur += "La ressource ne contient pas de notions\n";

		//verifier si la ressource contient des questions
		if (rechercheRessource(r).getNotions().get(0).getLstQuestions().size() == 0)
			erreur += "La ressource ne contient pas de questions\n";

		//verifier si chrono est true ou false
		if (! chrono && chrono)
			erreur += "Le chrono doit être 'oui' ou 'non'\n";

		//verifier si le nombre de questions par notion et par difficulté est cohérent
		for (String notion : nbQuestParNotionDiff.keySet())
		{
			int[] tab = nbQuestParNotionDiff.get(notion);
			if (tab[0] < 0 || tab[1] < 0 || tab[2] < 0 || tab[3] < 0)
				erreur += "Le nombre de questions doit être positif\n";
		}

		return erreur;
	}
	
	

	//generer un Evaluation
	//appel genererEvaluation de la classe Ressource en associant la ressource, si il y a un chrono
	// le duree si le chrno est oui, la liste de notions et le nombre de questions
	public String genererEvaluation(String r, boolean chrono, HashMap<String, int[]> nbQuestParNotionDiff)
	{

		String erreur = "";

		erreur = validerEvaluation(r, chrono, nbQuestParNotionDiff);
		if (erreur.equals(""))
		{
			ArrayList<Question> lstQuestionsGenere = new ArrayList<Question>();
			ArrayList<Notion> lstNotions = new ArrayList<Notion>();
			Ressource ressource = rechercheRessource(r);
			Notion not = null;

			//ajouter les question de chaque notion selon la difficulte
			for (String nomNotion : nbQuestParNotionDiff.keySet()) 
			{
				not = rechercheNotion(r, nomNotion);
				lstNotions.add(not);
				lstQuestionsGenere.addAll(not.aleaQuestions(nbQuestParNotionDiff.get(nomNotion)[0], nbQuestParNotionDiff.get(nomNotion)[1], nbQuestParNotionDiff.get(nomNotion)[2], nbQuestParNotionDiff.get(nomNotion)[3]));
			}
			Evaluation eval = new Evaluation(ressource, lstNotions, lstQuestionsGenere);
			
			//generer le web de l'evaluation
			
			this.webEval(eval);
		}
		
		return erreur;
	}

	private void webEval(Evaluation eval)
	{
		this.ecritureWeb.creerEvaluation(eval);
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
