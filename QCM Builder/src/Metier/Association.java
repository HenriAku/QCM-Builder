package Metier;

import java.util.ArrayList;
import java.util.List;

public class Association extends Question
{
    private String type;
    private ArrayList<ReponseAsso> lstRep;
    private ArrayList<ReponseAsso> lstRepAsso;

    public Association( String question, String explication, Difficulte difficulte, double point, float temps, 
                        List<ReponseAsso> lstRep) 
    {
        super(question, explication, difficulte, point, temps);
        
        this.type = "Association";
        
        this.lstRep = new ArrayList<>(lstRep);

        this.lstRepAsso = new ArrayList<ReponseAsso>();    

        for (ReponseAsso rep : lstRep) 
        {
            this.lstRepAsso.add(rep.getAssocie());
        }

        melanger();
    }

    public ReponseAsso getReponseAsso(ReponseAsso rep)
    {
        return rep.getAssocie();
    }

    public void melanger()
    {
        for(int i = 0; i < 20; i++)
        {
            intervertir(this.lstRep, (int) (Math.random() * this.lstRep.size()), (int) (Math.random() * this.lstRep.size()));
            intervertir(this.lstRepAsso, (int) (Math.random() * this.lstRep.size()), (int) (Math.random() * this.lstRep.size()));
        }
    }

    private void intervertir(ArrayList<ReponseAsso> lst, int index1, int index2) 
    {
        if (lst == null || index1 < 0 || index2 < 0 || index1 >= lst.size() || index2 >= lst.size()) 
        {
            throw new IllegalArgumentException("Indices invalides ou liste null");
        }
    
        ReponseAsso temp = lst.get(index1);
        lst.set(index1, lst.get(index2));
        lst.set(index2, temp);
    }

    public String getType() 
    {
        return type;
    }

    public ArrayList<ReponseAsso> getLstRep() 
    {
        return lstRep;
    }

    public ArrayList<ReponseAsso> getLstRepAsso() 
    {
        return lstRepAsso;
    }
    
    //toString
    @Override
    public String toString() 
    {
    String str = "[" + super.toString();
    str += "| Type : " + this.type ;
    str += "| Reponses : ";
    for (ReponseAsso reponseAsso : lstRep) 
    {
        str += reponseAsso.toString() + "/";
    }
    return str + "]";
    }
    
}
