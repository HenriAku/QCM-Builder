package Metier;

import java.lang.reflect.Array;
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
        //test objets Chapitre et Ressource
        ///////////////////////////////////
        /// 
        //création de ressource
        Ressource rscAlgo           = new Ressource("Algo");
        Ressource rscchapBado       = new Ressource("Bado");
        Ressource rscCrypto         = new Ressource("Crypto");
        Ressource rscFrancais       = new Ressource("Francais");
        Ressource rscMath           = new Ressource("Math");
        Ressource rscHistoire       = new Ressource("Histoire");
        Ressource rscTest           = new Ressource("Test");

        //création liste de chapitres
        ArrayList<Chapitre> chapitresA = new ArrayList<Chapitre>();
        ArrayList<Chapitre> chapitresB = new ArrayList<Chapitre>();
        ArrayList<Chapitre> chapitresC = new ArrayList<Chapitre>();
        ArrayList<Chapitre> chapitresF = new ArrayList<Chapitre>();
        ArrayList<Chapitre> chapitresM = new ArrayList<Chapitre>();
        ArrayList<Chapitre> chapitresH = new ArrayList<Chapitre>();
        ArrayList<Chapitre> chapitresT = new ArrayList<Chapitre>();

        //création de chapitres
        Chapitre chapAlgo1       = new Chapitre("Algo", /*rsc,*/ questionsA);
        Chapitre chapBado1       = new Chapitre("Bado", /*rsc,*/ questionsB);
        Chapitre chapCrypto1     = new Chapitre("Crypto", /*rsc,*/ questionsC);
        Chapitre chapFrancais1   = new Chapitre("Francais", /*rsc,*/ questionsF);
        Chapitre chapMath1       = new Chapitre("Math", /*rsc,*/ questionsM);
        Chapitre chapHistoire1   = new Chapitre("Histoire", /*rsc,*/ questionsH1);
        Chapitre chapTest1       = new Chapitre("Test1", /*rsc,*/ questionsT1);
        Chapitre chapTest2       = new Chapitre("Test2", /*rsc,*/ questionsT2);

        //ajout de chapitres aux listes
        chapitresA.add(chapAlgo1);
        chapitresB.add(chapBado1);
        chapitresC.add(chapCrypto1);
        chapitresF.add(chapFrancais1);
        chapitresM.add(chapMath1);
        chapitresH.add(chapHistoire1);
        chapitresT.add(chapTest1);
        chapitresT.add(chapTest2);

        //ajout de chapitres aux ressources
        rscAlgo.setChapitres(chapitresA);
        rscchapBado.setChapitres(chapitresB);
        rscCrypto.setChapitres(chapitresC);
        rscFrancais.setChapitres(chapitresF);
        rscMath.setChapitres(chapitresM);
        rscHistoire.setChapitres(chapitresH);
        rscTest.setChapitres(chapitresT);
        

        //test ajout de chapitr
        /* 
        Chapitre chapTest3 = new Chapitre("Histoire",/*rsc,*/ /* questionsT);
        rscTest.ajouterChapitres(chapTest3);
        */

        //System.out.println(rsc.toString());
        System.out.println("Ressource et ses chapitres crées");


        ///////////////////////
        //test Questionnaire
        ///////////////////////
        
        Scanner sc = new Scanner(System.in);

        Metier m = new Metier();

        m.addRessource(rscAlgo);
        m.addRessource(rscchapBado);
        m.addRessource(rscCrypto);
        m.addRessource(rscFrancais);
        m.addRessource(rscMath);
        m.addRessource(rscHistoire);
        m.addRessource(rscTest);
        
        //tester creerQuestionnaire
        Questionnaire qst = Questionnaire.genererQuestionnaire(sc,m);

        for (Question q : qst.getQuestions())
        {
            System.out.println(q.afficherQuestion());
        }

        //tester Utiliser un questionnaire
        
    }
}