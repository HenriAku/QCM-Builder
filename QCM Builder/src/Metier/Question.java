/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Théo Wychowski
 * @date 09/12/2024
 */

package Metier;
import java.util.Scanner; 
import java.util.Arrays;
import java.util.List;


public class Question
{
	private String   question    ;
	private String   type        ;
	private String   explication ;
	private String   difficulte  ;
	private int      point       ;
	private int      timeMin     ;
	private int      timeSec     ;
	private List<Reponse> lstRep ;

	public Question(String question, String type, String explication, String difficulte, int point, int timeMin,
			int timeSec, List<Reponse> lstRep) 
	{
		this.question     = question    ;
		this.type         = type        ;
		this.explication  = explication ;
		this.difficulte   = difficulte  ;
		this.point        = point       ;
		this.timeMin      = timeMin     ;
		this.timeSec      = timeSec     ;
		this.lstRep       = lstRep      ;
	}

	public boolean equals(Question q)
	{
		return this.question.equals(q.question) && this.difficulte == q.difficulte;
	}

	/***********************/
	/*		GETTEURS	   */
	/***********************/

	public String getQuestion     () {return question    ;}
	public String getType         () {return type        ;}
	public String getExplication  () {return explication ;}
	public String getDifficulte   () {return difficulte  ;}
	public int    getPoint        () {return point       ;}
	public int    getTimeMin      () {return timeMin     ;}
	public int    getTimeSec      () {return timeSec     ;}
	public List<Reponse> getLstRep() {return lstRep      ;}

	
	/***********************/
	/*		Setteurs	   */
	/***********************/

	public void setQuestion    (String question    ) {this.question     = question    ;}
	public void setType        (String type        ) {this.type         = type        ;}
	public void setExplication (String explication ) {this.explication  = explication ;}
	public void setDifficulte  (String difficulte  ) {this.difficulte   = difficulte  ;}
	public void setPoint       (int    point       ) {this.point        = point       ;}
	public void setTimeMin     (int    timeMin     ) {this.timeMin      = timeMin     ;}
	public void setTimeSec     (int    timeSec     ) {this.timeSec      = timeSec     ;}
	public void setLstRep(List<Reponse> lstRep) {this.lstRep = lstRep;}

	/***********************/
	/*		Methodes	   */
	/***********************/

	//reponses
	public void addReponse(Reponse r)
	{lstRep.add(r);}
	public void removeReponse(Reponse r)
	{lstRep.remove(r);}
	public void removeReponse(int index)
	{lstRep.remove(index);}

	//creer question en demandant chaque element (terminal)
	public static Question creerQuestion(Scanner sc)
	//"sc" utilisation de scanner pour récupérer les informations de l'utilisateur en version console
	{
		//question
		//recuperation de la question
		System.out.print("Question : ");
		String question = sc.nextLine();

		//type
		//recuperation du type tant qu'il n'est pas "QCM" ou "Association"
		String type;
		do
		{
			System.out.print("Type (QCM, Association) : ");
			type = sc.nextLine();
		}
		while (!type.equalsIgnoreCase("QCM") && !type.equalsIgnoreCase("Association"));

		//explication
		//recuperation de l'explication
		System.out.print("Explication : ");
		String explication = sc.nextLine();

		//difficulte
		//recuperation de la difficulte tant qu'elle n'est pas "facile", "moyen" ou "difficile"
		String difficulte;
		do
		{
			System.out.print("Difficulte (facile, moyen, difficile) : ");
			difficulte = sc.nextLine();
		}
		while (!difficulte.equalsIgnoreCase("facile") && !difficulte.equalsIgnoreCase("moyen") && !difficulte.equalsIgnoreCase("difficile"));
		
		//point
		//recuperation des points tant qu'ils sont positifs
		int point;
		do
		{
			System.out.print("Points : ");
			point = sc.nextInt();
		}
		while (point < 0);

		//temps
		//recuperation du temps tant qu'il est positif et cohérent
		int timeMin;
		int timeSec;
		do
		{
			System.out.println("Temps : ");
			System.out.print("Min : ");
			timeMin = sc.nextInt();
			System.out.print("Sec : ");
			timeSec = sc.nextInt();
		}
		while (timeMin < 0 || timeSec < 0 || timeSec > 59);

		//reponses
		//recuperation du nombre de reponses
		System.out.print("Combien de reponses ? : ");
		int nbRep = sc.nextInt();
		sc.nextLine();
		List<Reponse> lstRep = Arrays.asList(new Reponse[nbRep]);
		for (int i = 0; i < nbRep; i++)
		{
			System.out.println("\nCréation de la reponse "+(i+1)+" : ");
			lstRep.set(i, Reponse.creerReponse(sc));
		}

		return new Question(question, type, explication, difficulte, point, timeMin, timeSec, lstRep);
	}


	/***********************/
	/*		String		   */
	/***********************/
	public String toString() 
	{
		return "Question [question=" + question + ", type=" + type + ", explication=" + explication + ", difficulte="
				+ difficulte + ", point=" + point + ", timeMin=" + timeMin + ", timeSec=" + timeSec + ", ordreEnveler=" + "]";
	}
	
	public String afficherQuestion()
	{	
		String str = "";

		str += "Question : " + question +"\n";
		str += "Type : " + type + "\n";
		str += "Explication : " + explication + "\n";
		str += "Difficulte : " + difficulte + "\n";
		str += "Point : " + point + "\n";
		str += "Temps : " + timeMin + " min " + timeSec + " sec" + "\n";
		str += "Reponses :\n";
		for (Reponse r : lstRep)
		{
			str += r.afficherReponse();
		}

		return str + "\n";
	}
	
}
 