package Metier;

import java.util.Scanner;

class TestQuestion
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        //tester creerQuestion
        Question q1 = Question.creerQuestion(sc);

        System.out.println(q1.afficherQuestion());
    }

}