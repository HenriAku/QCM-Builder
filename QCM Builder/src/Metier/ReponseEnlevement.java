package Metier;

public class ReponseEnlevement extends Reponse
{
    private int ordreEnleve;

    public ReponseEnlevement(String reponse, int ordreEnleve)
    {
        super(reponse);

        this.ordreEnleve = ordreEnleve;
    }

    public int getOrdreEnleve() 
    {
        return ordreEnleve;
    }
}
