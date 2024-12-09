/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Théo Wychowski
 * @date 09/12/2024
 */

package Metier;

import java.util.Scanner; 

public class Reponse 
{
	private String  reponse     ;
	private boolean valeur      ;
	private int     ordreEnveler;

	public Reponse(String reponse, boolean valeur, int ordreEnveler) 
	{
		this.reponse      = reponse     ;
		this.valeur       = valeur      ;
		this.ordreEnveler = ordreEnveler;
	}

	/***********************/
	/*		GETTEURS	   */
	/***********************/

	public String  getReponse     () {return reponse     ;}
	public boolean isValeur       () {return valeur      ;}
	public int     getOrdreEnveler() {return ordreEnveler;}


	/***********************/
	/*		SETTEURS	   */
	/***********************/

	public void setReponse     (String  reponse     ) {this.reponse      = reponse     ;}
	public void setValeur      (boolean valeur      ) {this.valeur       = valeur      ;}
	public void setOrdreEnveler(int     ordreEnveler) {this.ordreEnveler = ordreEnveler;}

	/***********************/
	/*		SETTEURS	   */
	/***********************/

	//methode creerRéponse en demandant les informations à l'utilisateur  scanner
	public static Reponse creerReponse(Scanner sc)
	//"sc" utilisation de scanner pour récupérer les informations de l'utilisateur en version console
	{
		try 
		{
			System.out.print("Entrez la réponse : ");
			String reponse = sc.nextLine();

			System.out.print("Est-ce la bonne réponse ? (true/false) : ");
			Boolean valeur = sc.nextBoolean();
			sc.nextLine();

			System.out.print("Entrez l'ordre d'enlèvement de la réponse : ");
			int ordreEnveler = sc.nextInt();
			sc.nextLine();

			return new Reponse(reponse, valeur, ordreEnveler);
		}
		catch (Exception e) 
		{
			System.out.println("Erreur lors de la création de la réponse");
			return null;
		}
	}

	
	@Override
	public String toString() 
	{
		return "Reponse [reponse=" + reponse + ", valeur=" + valeur + ", ordreEnveler=" + ordreEnveler + "]";
	}

	//methode afficher réponse dans le terminal
	public String afficherReponse()
	{
		return "Reponse : " + reponse + " | Valeur : " + valeur + " | Ordre : " + ordreEnveler  + "\n";
	}

	
}
