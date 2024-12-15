/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */

package Metier;

public abstract class Reponse 
{
	private String  reponse     ;

	public Reponse(String reponse) 
	{
		this.reponse      = reponse     ;
	}

	/***********************/
	/*		GETTEURS	   */
	/***********************/

	public String getReponse()
	{
		return this.reponse;
	}

	/***********************/
	/*		SETTEURS	   */
	/***********************/

	public void setReponse     (String  reponse     ) {this.reponse      = reponse     ;}

	/***********************/
	/*		STRING		   */
	/***********************/

	//toString
	public String toString() 
	{
		return this.reponse;
	}

	///////////
	// CUI   //
	///////////
	
	/*
	//methode creerRéponse en demandant les informations à l'utilisateur  scanner
	public static Reponse creerReponse(Scanner sc)
	//"sc" utilisation de scanner pour récupérer les informations de l'utilisateur en version console
	{
		try 
		{
			System.out.print("Entrez la réponse : ");
			String reponse = sc.nextLine();

			// Demande si la réponse est la bonne réponse tant que la réponse n'est pas oui ou non, prend un boolean
			String reponseValeur;
			boolean valeur;
			do
			{
				System.out.print("Est-ce la bonne réponse ? (oui/non) : ");
				reponseValeur = sc.nextLine();
			}
			while (!reponseValeur.equalsIgnoreCase("oui") && !reponseValeur.equalsIgnoreCase("non"));

			if (reponseValeur.equalsIgnoreCase("oui"))
				valeur = true;
			else
				valeur = false;
			
			// Demande l'ordre d'enlèvement de la réponse tant que ce n'est pas un entier
			int ordreEnlever;
			do
			{
				System.out.print("Entrez l'ordre d'enlèvement de la réponse : ");
				while (!sc.hasNextInt()) 
				{
					System.out.print("Veuillez entrer un nombre entier : ");
					sc.next();
				}
				ordreEnlever = sc.nextInt();
				sc.nextLine();
			}
			while (ordreEnlever <= 0);
			
			return new Reponse(reponse, valeur, ordreEnlever);
		}
		catch (Exception e) 
		{
			System.out.println("Erreur lors de la création de la réponse");
			return null;
		}
	}
	*/
}
