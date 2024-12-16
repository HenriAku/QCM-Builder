package Metier;

import java.util.ArrayList;
import java.util.Arrays;

class TestMetier
{
	public static void main(String[] args) 
	{
		/////////////////////
		//test init Métier //
		/////////////////////

		Metier metierTest = new Metier();

		//affcihage des ressoucres
		//si la liste est vide
		if (metierTest.getLstRessource().isEmpty())
		{
			System.out.println("\nAucune ressource\n");
		}
		for (Ressource ressource : metierTest.getLstRessource())
		{
			System.out.println(ressource.afficherRessourceDetail());
		}

		////////////////////////////
		// test ajout de question //
		////////////////////////////

		//ajouter une ressource
		metierTest.addRessource(new Ressource("R1.01"));

		//affcihage des notions
		metierTest.addNotion("R1.01", new Notion("TP1 : Première approche",null));
		metierTest.addNotion("R1.01", new Notion("TP2 : Les variables, Instructions de bases ",null));

		//creation d'un QCM
		//Preparation de la question
		String ressource = "R1.01"; String notion = "TP1 : Première approche"; String type = "question à choix multiple à réponse unique"; String difficulte = "tres facile"; Double points = 2.0; String temps = "00:00";
		String erreur = metierTest.validerQuestion(ressource, notion,type, difficulte,points,temps);
		if (erreur != "")
		{
			System.out.println(erreur);
		}
		else
		{	
			//creer une question
			String question = "De quel couleur est le ciel ?";
			//explication de la question
			String explication = "Le ciel est bleu";
			//creation des reponses
			ArrayList<String> lstReponses = new ArrayList<String>(Arrays.asList("bleu", "jaune", "vert", "rouge"));
			ArrayList<Boolean> lstvalidite = new ArrayList<Boolean>(Arrays.asList(true, false, false, false));
			metierTest.creerQuestionQCM(ressource, notion, question, type, explication, difficulte,points,temps, lstReponses, lstvalidite);
		}

		//creation d'une question a association
		//Preparation de la question
		ressource = "R1.01"; notion = "TP1 : Première approche"; type = "question à association d’éléments"; difficulte = "facile"; points = 3.0; temps = "00:00";
		erreur = metierTest.validerQuestion(ressource, notion,type, difficulte,points,temps);
		if (erreur != "")
		{
			System.out.println(erreur);
		}
		else
		{	
			//creer une question
			String question = "Associer les couleurs aux objets";
			//explication de la question
			String explication = "Les couleurs sont associées aux objets";
			//creation des reponses (chaque reponses est ssocié à la reponse suivante due par deux)
			ArrayList<String> lstReponses = new ArrayList<String>(Arrays.asList("pomme", "rouge", "ciel", "bleu", "banane", "jaune", "herbe", "vert"));
			System.out.println(metierTest.creerQuestionAsso(ressource, notion, question, type, explication, difficulte,points,temps, lstReponses));
		}

		//creation d'une questiion par elemination
		//Preparation de la question
		ressource = "R1.01"; notion = "TP2 : Les variables, Instructions de bases "; type = "question avec élimination de propositions de réponses"; difficulte = "difficile"; points = 4.0; temps = "05:00";
		erreur = metierTest.validerQuestion(ressource, notion,type, difficulte,points,temps);
		if (erreur != "")
		{
			System.out.println(erreur);
		}
		else
		{	
			//creer une question
			String question = "On est quel jour ? Eliminer les mauvaises réponses";
			//explication de la question
			String explication = "Lundi ajourd'hui";
			//ordres d'élimination string
			ArrayList<String> lstOrdreElim = new ArrayList<String>(Arrays.asList("-1", "3", "2", "1"));
			//nombre de points perdu arraylis String
			ArrayList<String> lstPointsPerdu = new ArrayList<String>(Arrays.asList("0", "-1", "-1", "-1"));
			//creation des reponses
			ArrayList<String> lstReponses = new ArrayList<String>(Arrays.asList("lundi", "mercredi", "vendredi", "dimanche"));
			ArrayList<Boolean> lstvalidite = new ArrayList<Boolean>(Arrays.asList(true, false, false, false));
			System.out.println(metierTest.creerQuestionElimination(ressource, notion, question, type, explication, difficulte,points,temps, lstOrdreElim, lstPointsPerdu, lstReponses, lstvalidite));
		}

		//affcihage des ressources
		for (Ressource res : metierTest.getLstRessource())
		{
			System.out.println(res.afficherRessourceDetail());
		}

		//test creation d'un questionnaire
		//creation d'un questionnaire
		//Preparation du questionnaire
		ressource = "R1.01"; String chrono = "non" ; 
		ArrayList<String> notions = new ArrayList<>(Arrays.asList ("TP1 : Première approche"," TP2 : Les variables, Instructions de bases " ));
		int nbQuestions = 3;
		String nom = "QCM1";String duree = "00:30"; String emplacement = "Evaluations/R1.01";
		erreur = metierTest.validerEvaluation(ressource, chrono);
		if (erreur != "")
		{
			System.out.println(erreur);
		}
		else
		{
			//creation du questionnaire
			System.out.println(metierTest.genererEvaluation(ressource, chrono, duree, notions, nbQuestions));
		}

		//affcihage des ressources


	}
}