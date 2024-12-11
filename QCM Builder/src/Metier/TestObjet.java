package Metier;

import java.util.ArrayList;
import java.util.Scanner;

class TestObjet
{
    public static void main(String[] args) 
    {
        /////////////////////
        //test objet Question
        /////////////////////

        //reponses
        ArrayList<Reponse> reponsesA = new ArrayList<Reponse>();
        ArrayList<Reponse> reponsesB = new ArrayList<Reponse>();
        ArrayList<Reponse> reponsesC = new ArrayList<Reponse>();
        ArrayList<Reponse> reponsesF = new ArrayList<Reponse>();
        ArrayList<Reponse> reponsesM = new ArrayList<Reponse>();
        ArrayList<Reponse> reponsesH1_1 = new ArrayList<Reponse>();
        ArrayList<Reponse> reponsesT1_1 = new ArrayList<Reponse>();
        ArrayList<Reponse> reponsesT1_2 = new ArrayList<Reponse>();
        ArrayList<Reponse> reponsesT2_1 = new ArrayList<Reponse>();
        ArrayList<Reponse> reponsesT2_2 = new ArrayList<Reponse>();

        reponsesT1_1.add(new Reponse("2", true, 2));
        reponsesT1_1.add(new Reponse("3", false, 1));

        reponsesT1_2.add(new Reponse("1", true, 1));
        reponsesT1_2.add(new Reponse("8", false, 2));

        reponsesT2_2.add(new Reponse("1997", false, 1));
        reponsesT2_2.add(new Reponse("1998", false, 2));

        reponsesH1_1.add(new Reponse("Montréal", false, 1));
        reponsesH1_1.add(new Reponse("Paris", false, 2));
        reponsesH1_1.add(new Reponse("Djibouti", true, 3));

        //questions
        ArrayList<Question> questionsA = new ArrayList<Question>();
        ArrayList<Question> questionsB = new ArrayList<Question>();
        ArrayList<Question> questionsC = new ArrayList<Question>();
        ArrayList<Question> questionsF = new ArrayList<Question>();
        ArrayList<Question> questionsM = new ArrayList<Question>();
        ArrayList<Question> questionsH1 = new ArrayList<Question>();
        ArrayList<Question> questionsT1 = new ArrayList<Question>();
        ArrayList<Question> questionsT2 = new ArrayList<Question>();

        Question qT1_1 = new Question("1+1 = ?", "QCM", "logique", "Facile", 40, 3, 0, reponsesT1_1);
        Question qT1_2 = new Question("12-11 = ?", "QCM", "logique", "Dificile", 4, 3, 0, reponsesT1_2);

        Question qT2_1 = new Question("Quelle est la capitale de la République de Djibouti ?", "QCM", "Explication", "Facile", 2, 1, 30, reponsesT2_1);

        Question qH1_1 = new Question("annee de sortie Tianic ?", "QCM", "logique", "Dificile", 4, 3, 0, reponsesH1_1);

        questionsT1.add(qT1_1);
        questionsT1.add(qT1_2);

        questionsT2.add(qT2_1);

        questionsH1.add(qH1_1);

        //affichage des questions
       
       /* for(Question q : questions)
        {
            System.out.println(q.afficherQuestion());
        }
        */
        System.out.println("questions crées");
        
        

        ///////////////////////////////////
        //test objets Notions et Ressource
        ///////////////////////////////////
        /// 
        //création de ressource
        Ressource rscAlgo           = new Ressource("Algo");
        Ressource rscBado            = new Ressource("Bado");
        Ressource rscCrypto         = new Ressource("Crypto");
        Ressource rscFrancais       = new Ressource("Francais");
        Ressource rscMath           = new Ressource("Math");
        Ressource rscHistoire       = new Ressource("Histoire");
        Ressource rscTest           = new Ressource("Test");

        //création liste de notion
        ArrayList<Notion> notionsA = new ArrayList<Notion>();
        ArrayList<Notion> notionsB = new ArrayList<Notion>();
        ArrayList<Notion> notionsC = new ArrayList<Notion>();
        ArrayList<Notion> notionsF = new ArrayList<Notion>();
        ArrayList<Notion> notionsM = new ArrayList<Notion>();
        ArrayList<Notion> notionsH = new ArrayList<Notion>();
        ArrayList<Notion> notionsT = new ArrayList<Notion>();

        //création de notions
        Notion notAlgo1       = new Notion("Algo", /*rsc,*/ questionsA);
        Notion notBado1       = new Notion("Bado", /*rsc,*/ questionsB);
        Notion notCrypto1     = new Notion("Crypto", /*rsc,*/ questionsC);
        Notion notFrancais1   = new Notion("Francais", /*rsc,*/ questionsF);
        Notion notMath1       = new Notion("Math", /*rsc,*/ questionsM);
        Notion notHistoire1   = new Notion("Histoire", /*rsc,*/ questionsH1);
        Notion notTest1       = new Notion("Test1", /*rsc,*/ questionsT1);
        Notion notTest2       = new Notion("Test2", /*rsc,*/ questionsT2);

        //ajout de notions aux listes
        notionsA.add(notAlgo1);
        notionsB.add(notBado1);
        notionsC.add(notCrypto1);
        notionsF.add(notFrancais1);
        notionsM.add(notMath1);
        notionsH.add(notHistoire1);
        notionsT.add(notTest1);
        notionsT.add(notTest2);

        //ajout de notions aux ressources
        rscAlgo.    setNotions(notionsA);
        rscBado.    setNotions(notionsB);
        rscCrypto.  setNotions(notionsC);
        rscFrancais.setNotions(notionsF);
        rscMath.    setNotions(notionsM);
        rscHistoire.setNotions(notionsH);
        rscTest.    setNotions(notionsT);
        

        //test ajout de notion
        /* 
        Notion notTest3 = new Notion("Histoire",/*rsc,*/ /* questionsT);
        rscTest.ajouterNotions(notTest3);
        */

        //System.out.println(rsc.toString());
        System.out.println("Ressource et ses notions crées");


        ///////////////////////
        //test Questionnaire
        ///////////////////////
        
        Scanner sc = new Scanner(System.in);

        Metier m = new Metier();

        m.addRessource(rscAlgo);
        m.addRessource(rscBado);
        m.addRessource(rscCrypto);
        m.addRessource(rscFrancais);
        m.addRessource(rscMath);
        m.addRessource(rscHistoire);
        m.addRessource(rscTest);

        //test creation d'une question
        System.out.println(m.creerQuestion("Algo", "Algo", "On est quel jour ?", "qcm","oui exactement, nous somme un mercredi", "Facile", 5, "5:30", 7));

        //tester creerQuestionnaire
        Questionnaire qst = Questionnaire.genererQuestionnaire(sc,m);

        for (Question q : qst.getQuestions())
        {
            System.out.println(q.afficherQuestion());
        }

        //tester Utiliser un questionnaire
        
    }
}