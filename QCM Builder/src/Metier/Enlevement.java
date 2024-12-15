package Metier;

import java.util.ArrayList;
import java.util.List;

public class Enlevement extends Question
{
    private String type;
    private ArrayList<ReponseEnlevement> lstRep;
    private ArrayList<ReponseEnlevement> lstRepOrdre;

	public Enlevement(String question, String explication, Difficulte difficulte, double point, float temps, List<ReponseEnlevement> lstRep) 
    {
        super(question, explication, difficulte, point, temps);
        this.type = "Enlevement";
        this.lstRep = new ArrayList<>(lstRep);
        this.lstRepOrdre = new ArrayList<ReponseEnlevement>();
        ranger();
    }

    private void ranger()
    {
        for(int i = 0; i<lstRep.size(); i++)
        {
            if(lstRep.get(i).getOrdreEnleve() == i+1)
            {
                lstRepOrdre.add(lstRep.get(i));
            }
        }
    }

    public String getType() 
    {
        return type;
    }

    public ArrayList<ReponseEnlevement> getLstRep() 
    {
        return lstRep;
    }

    public ArrayList<ReponseEnlevement> getLstRepOrdre() 
    {
        return lstRepOrdre;
    }

    
}
