package Metier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
		String ressource = "R1.01"; String notion = "TP1 : Première approche"; String type = "question à choix multiple à réponse unique"; String difficulte = "tres facile"; Double points = 2.0; String temps = "00:25";
		String erreur = metierTest.validerQuestion(ressource, notion,type, difficulte,points,temps);
		if (erreur != "")
		{
			System.out.println(erreur);
		}
		else
		{	
			//creer une question QCM
			String question = "De quel couleur est le ciel ?";
			//explication de la question
			String explication = "Le ciel est bleu";
			//creation des reponses
			ArrayList<String> lstReponses = new ArrayList<String>(Arrays.asList("bleu", "jaune", "vert", "rouge"));
			ArrayList<Boolean> lstvalidite = new ArrayList<Boolean>(Arrays.asList(true, false, false, false));
			metierTest.creerQuestionQCM(ressource, notion, question, type, explication, difficulte,points,temps, lstReponses, lstvalidite);

			}

		String ressource2 = "R1.01"; String notion2 = "TP1 : Première approche"; String type2 = "question à choix multiple à réponse unique"; String difficulte2 = "tres facile"; Double points2 = 2.0; String temps2 = "00:50";
		String erreur2 = metierTest.validerQuestion(ressource, notion,type, difficulte,points,temps);
		if (erreur != "")
		{
			System.out.println(erreur);
		}
		else
		{
			String question2 = "De quel couleur est la marque coca cola ?";
			//explication de la question
			String explication2 = "La marque est rouge sur google";
			//creation des reponses
			ArrayList<String> lstReponses2 = new ArrayList<String>(Arrays.asList("rouge", "jaune", "vert", "blanc"));
			ArrayList<Boolean> lstvalidite2 = new ArrayList<Boolean>(Arrays.asList(true, false, false, false));
			metierTest.creerQuestionQCM(ressource2, notion2, question2, type2, explication2, difficulte,points,temps, lstReponses2, lstvalidite2);
		}
		
		String ressource3 = "R1.01"; String notion3 = "TP1 : Première approche"; String type3 = "question à choix multiple à réponse unique"; String difficulte3 = "tres facile"; Double points3 = 2.0; String temps3 = "00:50";
		String erreur3 = metierTest.validerQuestion(ressource, notion,type, difficulte,points,temps);
		if (erreur != "")
		{
			System.out.println(erreur);
		}
		else
		{
			String question3 = "De quel couleur est le cheval d'Henri 4 ?";
			//explication de la question
			String explication3 = "C'est le cheval blanc d'Henri 4";
			//creation des reponses
			ArrayList<String> lstReponses3 = new ArrayList<String>(Arrays.asList("bleu", "blanc", "vert", "rouge"));
			ArrayList<Boolean> lstvalidite3 = new ArrayList<Boolean>(Arrays.asList(false, true, false, false));
			metierTest.creerQuestionQCM(ressource3, notion3, question3, type3, explication3, difficulte3,points3,temps3, lstReponses3, lstvalidite3);
		}
		

		//creation d'une question a association
		//Preparation de la question
		ressource = "R1.01"; notion = "TP1 : Première approche"; type = "question à association d’éléments"; difficulte = "facile"; points = 3.0; temps = "01:00";
		String path = "Evaluations/R1.01";
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
			System.out.println(metierTest.creerQuestionAsso(ressource, notion, question, type, explication, difficulte,points,temps, lstReponses, path));
		}

		/* COMMENTE
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
			String question = "Quel jour est lundi ? Eliminer les mauvaises réponses";
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
		*/

		//affcihage des ressources
		for (Ressource res : metierTest.getLstRessource())
		{
			System.out.println(res.afficherRessourceDetail());
		}

		//test creation d'un questionnaire
		//creation d'un questionnaire
		//Preparation du questionnaire
		ressource = "R1.01"; Boolean chrono = false ; 
		HashMap<String, int[]> nbQuestions = new HashMap<String, int[]>();
		nbQuestions.put("TP1 : Première approche", new int[]{3,0,0,0});
		nbQuestions.put("TP2 : Les variables, Instructions de bases ", new int[]{0,0,0,0});
		
		String nom = "QCM1"; String emplacement = "Evaluations/R1.01";
		erreur = metierTest.validerEvaluation(ressource, chrono, nbQuestions);
		if (erreur != "")
		{
			System.out.println(erreur);
		}
		else
		{
			//creation du questionnaire
			System.out.println(metierTest.genererEvaluation(ressource, chrono, nbQuestions, nom,emplacement));
		}

		//affcihage d l'evaluation
		System.out.println(metierTest.getEvaluation().afficherEvaluation());


	}
}