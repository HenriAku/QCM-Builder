package Metier;

import java.util.Scanner;

public class TestRessource 
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        Ressource r1 = Ressource.creerRessource(sc);
        
        //demander si on creer la liste des chapitres, tant que la réponse n'est pas oui ou non
		String reponse;
		do
		{
			System.out.print("Voulez-vous ajouter des chapitres ? (oui/non) : ");
			reponse = sc.nextLine();
		}
		while (!reponse.equalsIgnoreCase("oui") && !reponse.equalsIgnoreCase("non"));
		
		//si oui on demande le nombre et on les ajoute
		if (reponse.equalsIgnoreCase("oui")) 
		{
			//on demande le nombre de chapitres tant que le nombre n'est pas positif et entier
			int nbChap;
			do
			{
				System.out.print("Combien de chapitres voulez-vous ajouter ? ");
				while (!sc.hasNextInt()) 
				{
					System.out.print("Veuillez entrer un nombre entier : ");
					sc.next();
				}
				nbChap = sc.nextInt();
			}
			while (nbChap <= 0);

			//on crée les chapitres
			for (int i=0; i<nbChap; i++)
			{	
				System.out.println("\nCréation du chapitre "+(i+1)+" : ");
				Chapitre chap = Chapitre.creerChapitre(sc);
				r1.addChap(chap);
			}
		}
        System.out.println(r1.afficherRessourceDetail());
        sc.close();
    }
}
