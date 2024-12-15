package Metier;

public class ReponseQcm extends Reponse
{
    private boolean valeur;
    public ReponseQcm(String reponse, boolean valeur)
    {
        super(reponse);

        this.valeur = valeur;
    }
    public boolean getValeur() 
    {
        return valeur;
    }

    //toString
    @Override
    public String toString() 
    {
        return super.toString() + "[" + this.valeur+"]";
    }
    
    
    
}