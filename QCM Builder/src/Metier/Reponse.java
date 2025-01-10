/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */

package Metier;

public abstract class Reponse 
{
	//////////////
	// ATTRIBUT //
	//////////////

	private String  reponse;

	//////////////////
	// CONSTRUCTEUR //
	//////////////////

	public Reponse(String reponse) 
	{
		this.reponse = reponse;
	}

	/***********************/
	/*		GETTEURS	   */
	/***********************/

	public String getReponse()
	{
		return this.reponse;
	}

	/***********************/
	/*		SETTEURS	   */
	/***********************/

	public void setReponse (String  reponse) {this.reponse = reponse;}

	/***********************/
	/*		STRING		   */
	/***********************/

	public String toString() 
	{
		return this.reponse;
	}
}
