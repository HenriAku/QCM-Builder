/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package Metier;

public class ReponseElimination extends Reponse
{
	///////////////
	// ATTRIBUTS //
	///////////////

	private int     ordreEnleve ;
	private double  nbPointEleve;
	private boolean valeur      ;

	//////////////////
	// CONSTRUCTEUR //
	//////////////////

	public ReponseElimination(String reponse, int ordreEnleve, double nbPointEleve, boolean valeur)
	{
		super(reponse);

		this.ordreEnleve  = ordreEnleve ;
		this.nbPointEleve = nbPointEleve;
		this.valeur       = valeur      ;
	}

	/////////////
	// GETTERS //
	/////////////

	public int     getOrdreEnleve (){return this.ordreEnleve ;}
	public double  getNbPointEleve(){return this.nbPointEleve;}
	public boolean getValeur      (){return this.valeur      ;}
	
	//////////////
	// TOSTRING //
	//////////////

	public String toString() 
	{
		return super.toString() + "({"+this.ordreEnleve+"};" + this.nbPointEleve + ")"; 
	}
}
