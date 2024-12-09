package Metier;

import java.util.ArrayList;

class TestObjet
{
    public static void main(String[] args) 
    {
        /////////////////////
        //test objet Question
        /////////////////////

        //reponses
        ArrayList<Reponse> reponses1 = new ArrayList<Reponse>();
        reponses1.add(new Reponse("Reponse 1", true, 1));
        reponses1.add(new Reponse("Reponse 2", true, 2));

        ArrayList<Reponse> reponses2 = new ArrayList<Reponse>();
        reponses2.add(new Reponse("Reponse 1", true, 1));
        reponses2.add(new Reponse("Reponse 2", false, 2));

        //questions
        ArrayList<Question> questions = new ArrayList<Question>();

        Question q1 = new Question("Question 1", "Type 1", "Explication", "Dificile", 10, 30, 60, reponses1);
        Question q2 = new Question("Question 2", "Type 2", "Explication", "Facile", 20, 3, 30, reponses2);

        questions.add(q1);
        questions.add(q2);
        //affichage des questions
        for(Question q : questions)
        {
            System.out.println(q.afficherQuestion());
        }
        
        ///////////////////////////////////
        //test objets Chapitre et Ressource
        ///////////////////////////////////
        ArrayList<Chapitre> chapitres = new ArrayList<Chapitre>();
        //test création de ressource
        Ressource rsc = new Ressource("Ressource 1");

        //test création de chapitre
        Chapitre chap1 = new Chapitre("Chapitre 1", /*rsc,*/ questions);
        Chapitre chap2 = new Chapitre("Chapitre 2", /*rsc,*/ questions);
       
        chapitres.add(chap1);
        chapitres.add(chap2);

        rsc.setChapitres(chapitres);

        //test ajout de chapitre
        Chapitre chap3 = new Chapitre("Chapitre 3",/*rsc,*/  questions);
        rsc.ajouterChapitres(chap3);

        System.out.println(rsc.toString());


        ///////////////////////
        //test Questionnaire
        ///////////////////////
        /*
        Questionnaire qn = new Questionnaire("Questionnaire 1", "Type 1", "Explication", "Dificile", 10, 30, 60, chapitres);
        System.out.println(qn.toString());
        */
    }
}