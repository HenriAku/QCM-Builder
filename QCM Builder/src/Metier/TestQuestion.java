package Metier;

import java.util.Scanner;

class TestQuestion
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        //tester creerQuestion
        //Question q1 = Question.creerQuestion("question", "type", "explication", "difficulte", -20, "20:80", -5);
        //Question q1 = Question.creerQuestion("question", "qcm", "explication", "difficile", 2, "20:2", 2);

        Question q2 = Question.creerQuestion(sc);

        //si q1 est bien créé on affiche les informations
        
        if (q2 != null)
        {
            System.out.println(q2.afficherQuestion());
        }
        
    }

}