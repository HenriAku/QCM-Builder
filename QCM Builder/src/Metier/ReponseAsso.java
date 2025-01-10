/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package Metier;

public class ReponseAsso extends Reponse
{
	//////////////
	// ATTRIBUT //
	//////////////

	private ReponseAsso reponseAsso;

	///////////////////
	// CONSTRUCTEURS //
	///////////////////

	public ReponseAsso(String reponse, ReponseAsso reponseAsso)
	{
		super(reponse);

		this.reponseAsso = reponseAsso;
	}
	
	public ReponseAsso(String reponse)
	{
		super(reponse);
	}

	/////////////
	// SETTERS //
	/////////////

	public void        setAssocie(ReponseAsso r){this.reponseAsso = r   ;} 

	/////////////
	// GETTERS //
	/////////////

	public ReponseAsso getAssocie(             ){return this.reponseAsso;}

	//////////////
	// TOSTRING //
	//////////////

	public String toString() 
	{
		return super.toString();
	}
}
