/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package Metier;

import java.util.ArrayList;
import java.util.List;

public class Association extends Question
{
	///////////////
	// ATTRIBUTS //
	///////////////
	private String type;

	private ArrayList<ReponseAsso> lstRep           ;
	private ArrayList<ReponseAsso> lstRepAsso       ;
	private ArrayList<ReponseAsso> lstRepPasMelanger;

	///////////////////
	// CONSTRUCTEURS //
	///////////////////
	/**
	 * Constructeur des questions Association
	 */
	public Association( String question, String explication, Difficulte difficulte, double point, float temps, 
						List<ReponseAsso> lstRep) 
	{
		super(question, explication, difficulte, point, temps);

		this.type       = "Association";
		this.lstRep     = new ArrayList<>(lstRep)     ;
		this.lstRepAsso = new ArrayList<ReponseAsso>();    

		for (ReponseAsso rep : lstRep)
			this.lstRepAsso.add(rep.getAssocie());

		this.lstRepPasMelanger = new ArrayList<>(lstRep);

		melanger();
	}

	/**
	 * Constructeur des questions Association avec le path en cas de complément à la question
	 */
	public Association( String question, String explication, Difficulte difficulte, double point, float temps, 
						List<ReponseAsso> lstRep, String path) 
	{
		super(question, explication, difficulte, point, temps,path);

		this.type       = "Association";
		this.lstRep     = new ArrayList<>(lstRep)     ;
		this.lstRepAsso = new ArrayList<ReponseAsso>();

		for (ReponseAsso rep : lstRep)
			this.lstRepAsso.add(rep.getAssocie());

			this.lstRepPasMelanger = new ArrayList<>(lstRep);

		melanger();
	}
	
	//////////////
	// METHODES //
	//////////////

	public ReponseAsso getReponseAsso(ReponseAsso rep) {return rep.getAssocie();}

	public void melanger()
	{
		for(int i = 0; i < 20; i++)
		{
			intervertir(this.lstRep    , (int) (Math.random() * this.lstRep.size()), (int) (Math.random() * this.lstRep.size()));
			intervertir(this.lstRepAsso, (int) (Math.random() * this.lstRep.size()), (int) (Math.random() * this.lstRep.size()));
		}
	}

	private void intervertir(ArrayList<ReponseAsso> lst, int index1, int index2) 
	{
		if (lst == null || index1 < 0 || index2 < 0 || index1 >= lst.size() || index2 >= lst.size()) 
			throw new IllegalArgumentException("Indices invalides ou liste null");
	
		ReponseAsso temp = lst.get(index1);
		lst.set(index1, lst.get(index2));
		lst.set(index2, temp           );
	}

	/////////////
	// SETTERS //
	/////////////

	public void setReponses(ArrayList<ReponseAsso> lstReponseAssos){this.lstRep = lstReponseAssos;}


	/////////////
	// GETTERS //
	/////////////

	public String                 getType             () {return this.type             ;}
	public ArrayList<ReponseAsso> getLstRep           () {return this.lstRep           ;}
	public ArrayList<ReponseAsso> getLstRepAsso       () {return this.lstRepAsso       ;}
	public ArrayList<ReponseAsso> getlstRepPasMelanger() {return this.lstRepPasMelanger;}

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
		String str = "[" + super.toString();
		str += "| Type : " + this.type ;
		str += "| Reponses : ";
		
		for (ReponseAsso reponseAsso : lstRep) 
			str += reponseAsso.toString() + "/";
		
		return str + "]";
	}
	
}
