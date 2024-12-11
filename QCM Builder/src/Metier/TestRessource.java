package Metier;

import java.util.Scanner;

public class TestRessource 
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        Ressource r1 = Ressource.creerRessource(sc);
		r1.ajouterNotions(sc);
    
        System.out.println(r1.afficherRessourceDetail());
        sc.close();
    }
}
