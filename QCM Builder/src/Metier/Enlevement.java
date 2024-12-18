/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package Metier;

import java.util.ArrayList;
import java.util.List;

public class Enlevement extends Question
{
	private String type;
	private ArrayList<ReponseEnlevement> lstRep;
	private ArrayList<ReponseEnlevement> lstRepOrdre;

	public Enlevement(String question, String explication, Difficulte difficulte, double point, float temps, List<ReponseEnlevement> lstRep) 
	{
		super(question, explication, difficulte, point, temps);

		this.type        = "Enlevement"                      ;
		this.lstRep      = new ArrayList<>(lstRep)           ;
		this.lstRepOrdre = new ArrayList<ReponseEnlevement>();

		ranger();
	}


	public Enlevement(String question, String explication, Difficulte difficulte, double point, float temps, List<ReponseEnlevement> lstRep, String path) 
	{
		super(question, explication, difficulte, point, temps, path);

		this.type        = "Enlevement"                      ;
		this.lstRep      = new ArrayList<>(lstRep)           ;
		this.lstRepOrdre = new ArrayList<ReponseEnlevement>();

		ranger();
	}

	private void ranger()
	{
		for(int i = 0; i<lstRep.size(); i++)
		{
			if(lstRep.get(i).getOrdreEnleve() == i+1)
				lstRepOrdre.add(lstRep.get(i));
		}
	}

	public String                       getType       () {return type       ; }
	public ArrayList<ReponseEnlevement> getLstRep     () {return lstRep     ; }
	public ArrayList<ReponseEnlevement> getLstRepOrdre() {return lstRepOrdre; }

	public void setReponses(ArrayList<ReponseEnlevement> lstNouvellesReponses)
	{
		this.lstRep = lstNouvellesReponses;
		this.lstRepOrdre = lstNouvellesReponses;
	}

	//toString
	@Override
	public String toString() 
	{
		String str = "[" + super.toString();
		str += " Type : " + this.type ;
		str += " Reponses : ";

		for (ReponseEnlevement reponseEnlev : lstRep) 
			str += reponseEnlev.toString() + "]";
		
		return str + "]";
	}
}
