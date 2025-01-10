/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package Metier;

import java.util.ArrayList;
import java.util.List;

public class Elimination extends Question
{
	///////////////
	// ATTRIBUTS //
	///////////////
	private String type;
	private ArrayList<ReponseElimination> lstRep;
	private ArrayList<ReponseElimination> lstRepOrdre;

	///////////////////
	// CONSTRUCTEURS //
	///////////////////
	/**
	 * Constructeur des questions Elimination
	 */
	public Elimination(String question, String explication, Difficulte difficulte, double point, float temps, List<ReponseElimination> lstRep) 
	{
		super(question, explication, difficulte, point, temps);

		this.type        = "Elimination"                      ;
		this.lstRep      = new ArrayList<>(lstRep)           ;
		this.lstRepOrdre = new ArrayList<ReponseElimination>();

		ranger();
	}

	/**
	 * Constructeur des questions Association avec le path en cas de complément à la question
	 */
	public Elimination(String question, String explication, Difficulte difficulte, double point, float temps, List<ReponseElimination> lstRep, String path) 
	{
		super(question, explication, difficulte, point, temps, path);

		this.type        = "Elimination"                      ;
		this.lstRep      = new ArrayList<>(lstRep)           ;
		this.lstRepOrdre = new ArrayList<ReponseElimination>();

		ranger();
	}

	/////////////
	// METHODE //
	/////////////

	private void ranger()
	{
		for(int i = 0; i<lstRep.size(); i++)
		{
			if(lstRep.get(i).getOrdreEnleve() == i+1)
				lstRepOrdre.add(lstRep.get(i));
		}
	}

	public String                        getType       () {return type       ; }
	public ArrayList<ReponseElimination> getLstRep     () {return lstRep     ; }
	public ArrayList<ReponseElimination> getLstRepOrdre() {return lstRepOrdre; }

	/////////////
	// SETTERS //
	/////////////
	public void setReponses(ArrayList<ReponseElimination> lstNouvellesReponses)
	{
		this.lstRep      = lstNouvellesReponses;
		this.lstRepOrdre = lstNouvellesReponses;
	}

	//////////////
	// TOSTRING //
	//////////////
	/**
	 * Affiche dans la console des informations sur la question
	 * 
	 * @return un String
	 */
	public String toString() 
	{
		String str  = "[" + super.toString();
			   str += " Type : " + this.type ;
			   str += " Reponses : ";

		for (ReponseElimination reponseEnlev : lstRep) 
			str += reponseEnlev.toString() + "]";
		
		return str + "]";
	}
}
