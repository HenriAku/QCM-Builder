package Metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QCM extends Question
{
    private String type;
    private ArrayList<ReponseQcm> lstRep;

	public QCM(String question, String explication, Difficulte difficulte, double point, float temps, List<ReponseQcm> lstRep) 
    {
        super(question, explication, difficulte, point, temps);
        this.type = "QCM";
        this.lstRep = new ArrayList<>(lstRep);
    }

    public QCM(String question, String explication, Difficulte difficulte, double point, float temps, List<ReponseQcm> lstRep, String path) 
    {
        super(question, explication, difficulte, point, temps, path);
        this.type = "QCM";
        this.lstRep = new ArrayList<>(lstRep);
    }

    public ArrayList<ReponseQcm> getBonneReponse()
    {
        ArrayList<ReponseQcm> bonnesReponse = new ArrayList<ReponseQcm>();

        for (ReponseQcm reponseQcm : lstRep) 
        {
            if(reponseQcm.getValeur())
                bonnesReponse.add(reponseQcm);
        }

        return bonnesReponse;
    }

    @Override
    public ArrayList<ReponseQcm> getLstRep()
    {
        return this.lstRep;
    }
    

    public String getType() 
    {
        return type;
    }
    
    //toString
    @Override
    public String toString() 
    {
        String str = "[" + super.toString();
        str += " Type : " + this.type ;
        str += " Reponses : ";
        for (ReponseQcm reponseQcm : lstRep) 
        {
            str += reponseQcm.toString() + "/";
        }
        return str + "]";
    }


    ////////
    //CUI //
    ////////

    //creer une question QCM avc les entree de l'utilisateur
    public static QCM creerQCM(Scanner sc)
    {
        //demande de la question
        System.out.println("Veuillez saisir la question : ");
        String question = sc.nextLine();
        
        //demande de l'explication
        System.out.println("Veuillez saisir l'explication : ");
        String explication = sc.nextLine();

        //demande de la difficulte (tres facile, facile, moyen, difficile)
        String difficulte = "";
        do 
        {
            System.out.println("Veuillez saisir la difficulte (tres facile, facile, moyen, difficile) : ");
            difficulte = sc.nextLine();
        } while (!difficulte.equals("tres facile") && !difficulte.equals("facile") && !difficulte.equals("moyen") && !difficulte.equals("difficile"));
        Difficulte diff;
        switch (difficulte) 
        {
            case "tres facile":
                diff = Difficulte.TF;
                break;
            case "facile":
                diff = Difficulte.F;
                break;
            case "moyen":
                diff = Difficulte.M;
                break;
            case "difficile":
                diff = Difficulte.D;
                break;
            default:
                diff = Difficulte.F;
                break;
        }

        //demande du nombre de reponse
        System.out.println("Combien de reponse voulez-vous ajouter ?");
        int nbRep = sc.nextInt();
        sc.nextLine();

        //creation de la liste de reponse
        ArrayList<ReponseQcm> lstRep = new ArrayList<ReponseQcm>();

        //demande des reponses
        for (int i = 0; i < nbRep; i++) 
        {
            //demande de la reponse
            System.out.println("Veuillez saisir la reponse : ");
            String reponse = sc.nextLine();

            //demande si la reponse est bonne
            System.out.println("Est-ce une bonne reponse ? (oui/non)");
            String bonneReponse = sc.nextLine();
            boolean valeur = bonneReponse.equals("oui") ? true : false;

            //ajout de la reponse a la liste
            lstRep.add(new ReponseQcm(reponse, valeur));
        }

        //demande du point
        System.out.println("Veuillez saisir le nombre de point : ");
        double point = sc.nextDouble();
        sc.nextLine();

        //demande du temps
        System.out.println("Veuillez saisir le temps : ");
        float temps = sc.nextFloat();
        sc.nextLine();

        return new QCM(question, explication, diff, point, temps, lstRep);
    }
}
