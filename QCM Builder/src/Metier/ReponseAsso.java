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

        this.reponseAsso = reponseAsso;
    }

    public void setAssocie(ReponseAsso r)
    {
        this.reponseAsso = r;
    }

    public ReponseAsso getAssocie()
    {
        return this.reponseAsso;
    }

    //toString
    @Override
    public String toString() 
    {
        return super.toString() + "[" + this.reponseAsso.getReponse()+"]";
    }
}
