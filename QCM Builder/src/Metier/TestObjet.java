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
        ArrayList<ReponseQcm> reponsesH1_1 = new ArrayList<ReponseQcm>();
        ArrayList<ReponseQcm> reponsesT1_1 = new ArrayList<ReponseQcm>();
        ArrayList<ReponseQcm> reponsesT1_2 = new ArrayList<ReponseQcm>();
        ArrayList<ReponseQcm> reponsesT2_1 = new ArrayList<ReponseQcm>();
        ArrayList<ReponseQcm> reponsesT2_2 = new ArrayList<ReponseQcm>();

        reponsesT1_1.add(new ReponseQcm("2", true));
        reponsesT1_1.add(new ReponseQcm("3", false));

        reponsesT1_2.add(new ReponseQcm("1", true));
        reponsesT1_2.add(new ReponseQcm("8", false));

        reponsesT2_2.add(new ReponseQcm("1997", false));
        reponsesT2_2.add(new ReponseQcm("1998", false));

        reponsesH1_1.add(new ReponseQcm("Montréal", false));
        reponsesH1_1.add(new ReponseQcm("Paris", false));
        reponsesH1_1.add(new ReponseQcm("Djibouti", true));

        //questions
        ArrayList<Question> questionsA = new ArrayList<Question>();
        ArrayList<Question> questionsB = new ArrayList<Question>();
        ArrayList<Question> questionsC = new ArrayList<Question>();
        ArrayList<Question> questionsF = new ArrayList<Question>();
        ArrayList<Question> questionsM = new ArrayList<Question>();
        ArrayList<Question> questionsH1 = new ArrayList<Question>();
        ArrayList<Question> questionsT1 = new ArrayList<Question>();
        ArrayList<Question> questionsT2 = new ArrayList<Question>();

        Question qT1_1 = new QCM("1+1 = ?", "logique", Difficulte.F, 40, 80, reponsesT1_1);
        Question qT1_2 = new QCM("12-11 = ?", "logique", Difficulte.D, 30, 80, reponsesT1_2);

        Question qT2_1 = new QCM("Quelle est la capitale de la République de Djibouti ?", "Explication", Difficulte.F, 2, 30, reponsesT2_1);

        Question qH1_1 = new QCM("annee de sortie Tianic ?", "logique", Difficulte.D, 4, 80, reponsesH1_1);

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
        Notion notAlgo1       = new Notion("Algo1", questionsA);
        Notion notBado1       = new Notion("Bado", questionsB);
        Notion notCrypto1     = new Notion("Crypto", questionsC);
        Notion notFrancais1   = new Notion("Francais", questionsF);
        Notion notMath1       = new Notion("Math", questionsM);
        Notion notHistoire1   = new Notion("Histoire", questionsH1);
        Notion notTest1       = new Notion("Test1", questionsT1);
        Notion notTest2       = new Notion("Test2", questionsT2);

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
        Notion notTest3 = new Notion("Histoire" /* questionsT);
        rscTest.ajouterNotions(notTest3);
        */

        //System.out.println(rsc.toString());
        System.out.println("Ressource et ses notions crées");
        System.out.println(rscTest.afficherRessourceDetail());


        ///////////////////////
        //test Evaluation
        ///////////////////////
        
        Scanner sc = new Scanner(System.in);

        Metier m = new Metier();

        System.out.println("ajout des ressources");
        m.addRessource(rscAlgo);
        m.addRessource(rscBado);
        m.addRessource(rscCrypto);
        m.addRessource(rscFrancais);
        m.addRessource(rscMath);
        m.addRessource(rscHistoire);
        m.addRessource(rscTest);

        //test creation d'une question
        System.out.println(m.validerQuestion("Algo", "Algo", "qcm", "Facile", 5, "5:30"));
        ArrayList<String> reponses = new ArrayList<String>();
        ArrayList<Boolean> validite= new ArrayList<Boolean>();
        reponses.add("lundi");
        validite.add(false);
        reponses.add("mardi");
        validite.add(false);
        reponses.add("mercredi");
        validite.add(true);
        reponses.add("jeudi");
        validite.add(false);
        reponses.add("vendredi");
        validite.add(false);

        System.out.println("ajout de la question");
        System.out.println(m.creerQuestionQCM("Algo", "Algo1", "On est quel jour ?", "qcm","oui exactement, nous somme un mercredi", "Facile", 5, "5:30", reponses, validite));

        //tester creerEvaluation
        System.out.println("generer Evaluation");
        Evaluation qst = Evaluation.genererEvaluation(sc,m);

        System.out.println(qst.afficherEvaluation());

        //tester Utiliser un Evaluation
        
    }
}