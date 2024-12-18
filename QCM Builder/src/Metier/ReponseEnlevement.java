/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package Metier;

public class ReponseEnlevement extends Reponse
{
	private int     ordreEnleve ;
	private double  nbPointEleve;
	private boolean valeur      ;

	public ReponseEnlevement(String reponse, int ordreEnleve, double nbPointEleve, boolean valeur)
	{
		super(reponse);

		this.ordreEnleve  = ordreEnleve ;
		this.nbPointEleve = nbPointEleve;
		this.valeur       = valeur      ;
	}

	public int     getOrdreEnleve (){return this.ordreEnleve ;}
	public double  getNbPointEleve(){return this.nbPointEleve;}
	public boolean getValeur      (){return this.valeur      ;}
	
	//toString
    @Override
    public String toString() 
    {
        return super.toString() + "({"+this.ordreEnleve+"};" + this.nbPointEleve + ")"; 
    }
}
