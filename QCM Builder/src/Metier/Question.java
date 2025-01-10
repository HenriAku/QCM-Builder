/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */

package Metier;

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public abstract class Question implements Comparable<Question>
{
	///////////////
	// ATTRIBUTS //
	///////////////

	private String       question    ;
	private String       explication ;
	private Difficulte   difficulte  ;
	private double		 point       ;
	private float        temps       ;
	private String		 filePath    ;

	///////////////////
	// CONSTRUCTEURS //
	///////////////////

	public Question(String question, String explication, Difficulte difficulte, double point, float temps) 
	{
		this.question     = question    ;
		this.explication  = explication ;
		this.difficulte   = difficulte  ;
		this.point        = point       ;
		this.temps		  = temps       ;
	}

	public Question(String question, String explication, Difficulte difficulte, double point, float temps, String path) 
	{
		this(question, explication, difficulte, point, temps);
		this.filePath     = path;
	}

	// Compare les difficultés
	public int compareTo(Question autreQuestion)
	{
		return this.difficulte.getPoint() - autreQuestion.difficulte.getPoint();
	}

	/***********************/
	/*		GETTEURS	   */
	/***********************/

	public String     getQuestion     () {return question     ;}
	public String     getExplication  () {return explication  ;}
	public Difficulte getDifficulte   () {return difficulte   ;}
	public double     getPoint        () {return point        ;}
	public float      getTemps        () {return temps        ;}
	public String 	  getFilePath     () {return this.filePath;}

	//récuperer le nom du fichier de filepath
	public String getFileName()
	{
		if (this.filePath == null)
			return null;
		File fichier = new File(this.filePath);
        return fichier.getName();
	}

	public abstract ArrayList<?> getLstRep();


	/***********************/
	/*		Setteurs	   */
	/***********************/
    
	public void setQuestion    (String     question   ) {this.question     = question    ;}
	public void setExplication (String     explication) {this.explication  = explication ;}
	public void setDifficulte  (Difficulte difficulte ) {this.difficulte   = difficulte  ;}
	public void setPoint       (double     point      ) {this.point        = point       ;}
	public void setTemps       (float      temps      ) {this.temps        = temps       ;}
	public void setPath        (String     path       ) {this.filePath     = path        ;}

	
	/***********************/
	/*		String		   */
	/***********************/
	public String toString() 
	{
		return question;
	}
	
	public String afficherQuestion()
	{	
		String str = "";

		str += "Question    : " + question    + "\n";
		str += "Explication : " + explication + "\n";
		str += "Difficulte  : " + difficulte  + "\n";
		str += "Point       : " + point       + "\n";
	
		return str + "\n";
	}

	/////////
	// CUI //
	/////////

	//creer question qcm en demandant chaque element (terminal)
	public static Question creerQuestionQCM(Scanner sc)
	{
		return QCM.creerQCM(sc);
	}
}
 