/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Théo Wychowski
 * @date 09/12/2024
 */

 package Metier;

 import java.util.*;
 
 public class Questionnaire 
 {
     //0 = très facile; 1 = facile; 2 = moyen; 3 = difficile
     //private int[] nbQuesParDificulté;
 
     private Ressource ressource;
     private ArrayList<Chapitre> lstChapitres;
     private ArrayList<Question> lstQuestions;
     private int nbQuestion;
     
     public Questionnaire(Ressource r, ArrayList<Chapitre> lstC, ArrayList<Question> lstQ)
     {
         this.ressource = r;
         this.lstChapitres = lstC;
         this.lstQuestions = lstQ;
         this.nbQuestion = lstQ.size();
     }
     
     public static Questionnaire genererQuestionnaire(Scanner sc, Metier m)
     {
         /*Generer un questionnaire avec un nombre de question total et demande le nombre de question par chapitre
         on a des ressources contenant des chapitres contenant des questions*/ 
 
         //on affiche les ressources
         System.out.println("Création d'un questionnaire : \n");
         
         for(int i = 0; i<m.getLstRessource().size(); i++)
         {   
             System.out.println((i+1) + " : " + m.getLstRessource().get(i).getNom());
         }
         //on demande de choisir la ressource pour le questionnaire tant que le choix n'est pas valide
         int choixRessource;
         do
         {
             System.out.println("\nEntrez le numéro de la ressource que vous voulez : ");
             choixRessource = sc.nextInt() - 1;
             sc.nextLine();
             if (choixRessource < 0 || choixRessource >= m.getLstRessource().size()) 
             {
                 System.out.println("Le numéro de la ressource n'est pas valide.");
             }
         }
         while(choixRessource < 0 || choixRessource >= m.getLstRessource().size());
         Ressource ressource = m.getLstRessource().get(choixRessource);
 
         //on demande le nombre de question total tant que le nombre n'est pas valide
         int nbQuestion;
         do
         {
             System.out.println("Entrez le nombre de questions total : ");
             nbQuestion = sc.nextInt();
             sc.nextLine();
             if (nbQuestion <= 0) 
             {
                 System.out.println("Le nombre de questions doit être supérieur à 0.");
             }
         }
         while(nbQuestion <= 0);
 
         //on affiche les chapitres de la ressource
         ArrayList<Chapitre> lstChapitres = new ArrayList<Chapitre>();
         System.out.print("\n");
         for(int i = 0; i<ressource.getChapitres().size(); i++)
         {
             System.out.println((i+1) + " : " + ressource.getChapitres().get(i).getNom());
         }
 
         //on demande de choisir les chapitres sur lesquels on veut faire le questionnaire
         String choixChapitres;
         String[] tabChapitres;
         do{
             System.out.println("Entrez les numéros des chapitres que vous voulez (ex:2,4,8) : ");
             choixChapitres = sc.next();
             sc.nextLine();
             tabChapitres = choixChapitres.split(",");
             if (choixChapitres.equals("")) 
             {
                 System.out.println("Veuillez entrer des chiffres.");
             }
             else if (Arrays.stream(tabChapitres).anyMatch(s -> Integer.parseInt(s) < 1 || Integer.parseInt(s) > ressource.getChapitres().size())) 
             {
                 System.out.println("Les numéros des chapitres ne sont pas valides.");
             }
         }//tant que les chiffres choisits ne sont pas dans la liste
         while(choixChapitres.equals("") || Arrays.stream(choixChapitres.split(",")).anyMatch(s -> Integer.parseInt(s) < 1 || Integer.parseInt(s) > ressource.getChapitres().size()));
         //on ajoute les chapitres choisis à la liste des chapitres
         for(int i = 0; i<tabChapitres.length; i++)
         {
             lstChapitres.add(ressource.getChapitres().get(Integer.parseInt(tabChapitres[i])-1));
         }
 
         //on demande le nombre de question par chapitre 
         //tant que le total de question n'est pas atteint et ne dépasse pas le nombre de question total et que le nombre de question n'est pas valide
         ArrayList<Question> lstQuestions = new ArrayList<Question>();
         int totalQuestions = 0;
         int nbQues;
 
         for (Chapitre chapitre : lstChapitres) 
         {
            do{
                System.out.println("Entrez le nombre de questions pour le chapitre " + chapitre.getNom() + " : ");
                nbQues = sc.nextInt();
                sc.nextLine();
                if (nbQues <= 0) 
                {
                    System.out.println("Le nombre de questions doit être supérieur à 0.");
                }
                else if (totalQuestions + nbQues > nbQuestion) 
                {
                    System.out.println("Le nombre total de questions ("+ (totalQuestions+nbQues)  +") dépasse le nombre de questions total ("+ nbQuestion +").");
                }
            }
            while (totalQuestions + nbQues > nbQuestion || nbQues <= 0 );

            totalQuestions += nbQues;
            lstQuestions.addAll(chapitre.aleaQuestionsSimple(nbQues));
         }
         //creer un questionnaire
         return new Questionnaire(ressource, lstChapitres, lstQuestions);
     }
 
     public ArrayList<Question> getQuestions()
     {
         return this.lstQuestions;
     }
 
 }
 
 
 