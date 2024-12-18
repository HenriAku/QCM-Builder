/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package Metier;

public class ReponseAsso extends Reponse
{
	private ReponseAsso reponseAsso;

	public ReponseAsso(String reponse, ReponseAsso reponseAsso)
	{
		super(reponse);

		this.reponseAsso = reponseAsso;
	}
	
	public ReponseAsso(String reponse)
	{
		super(reponse);
	}

	public void        setAssocie(ReponseAsso r){this.reponseAsso = r   ;} 
	public ReponseAsso getAssocie(             ){return this.reponseAsso;}

	//toString
	@Override
	public String toString() 
	{
		return super.toString();
	}
}
