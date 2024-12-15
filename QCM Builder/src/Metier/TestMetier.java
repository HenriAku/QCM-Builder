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
		metierTest.init();

		//affcihage des ressoucres
		System.out.println("nombre de ressources : " + metierTest.getLstRessource().size());
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

		//affcihage des ressources
		System.out.println("nombre de ressources : " + metierTest.getLstRessource().size());
		for (Ressource ressource : metierTest.getLstRessource())
		{
			System.out.println(ressource.afficherRessourceDetail());
		}

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
			ArrayList<String> lstReponses = new ArrayList<String>(Arrays.asList("reponse1", "reponse2", "reponse3", "reponse4"));
			ArrayList<Boolean> lstvalidite = new ArrayList<Boolean>(Arrays.asList(true, false, false, false));
			metierTest.creerQuestionQCM(ressource, notion, question, type, explication, difficulte,points,temps, lstReponses, lstvalidite);
			System.out.println("QCM créé");
		}
		//affcihage des ressources
		System.out.println("nombre de ressources : " + metierTest.getLstRessource().size());
		for (Ressource res : metierTest.getLstRessource())
		{
			System.out.println(res.afficherRessourceDetail());
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
			metierTest.creerQuestionAsso(ressource, notion, question, type, explication, difficulte,points,temps, lstReponses);
			System.out.println("Association créé");
		}
		//affcihage des ressources
		System.out.println("nombre de ressources : " + metierTest.getLstRessource().size());
		for (Ressource res : metierTest.getLstRessource())
		{
			System.out.println(res.afficherRessourceDetail());
		}

		//creation d'une questiion par elemination

	}
}