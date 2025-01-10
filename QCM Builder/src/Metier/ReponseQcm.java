/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package Metier;

public class ReponseQcm extends Reponse
{
	//////////////
	// ATTRIBUT //
	//////////////

	private boolean valeur;

	//////////////////
	// CONSTRUCTEUR //
	//////////////////

	public ReponseQcm(String reponse, boolean valeur)
	{
		super(reponse);

		this.valeur = valeur;
	}

	////////////
	// GETTER //
	////////////
	
	public boolean getValeur() {return valeur;}

	//////////////
	// TOSTRING //
	//////////////
	public String toString() 
	{
		return super.toString() + "[" + this.valeur+"]";
	}   
}