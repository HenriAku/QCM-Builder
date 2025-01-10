/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package Metier;

public enum Difficulte
{
	TF("tres facile"),
	F("facile"      ),
	M("moyen"       ),
	D("difficile"   );

	private String dif  ;
	private int    point;

	Difficulte(String dif)
	{
		int point = -1;

		switch (dif) 
		{
			case "très facile":
				point = 1;
				break;
				
			case "facile":
				point = 2;
				break;

			case "moyen":
				point = 3;
				break;

			case "difficile":
				point = 4;
				break;
		
			default:
				break;
		}
		this.dif   = dif  ;
		this.point = point;
	}

	/////////////
	// GETTERS //
	/////////////

	public String getDifficulte(){return this.dif  ;}
	public int    getPoint     (){return this.point;}
	
	/////////////
	// SETTERS //
	/////////////
	
	public void setPoint (int p){this.point = p;}
} 