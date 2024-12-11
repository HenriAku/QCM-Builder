/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */

package Metier;

import java.util.ArrayList;
import java.util.Scanner; 

public class Reponse 
{
	private String  reponse     ;
	private boolean valeur      ;
	private int     ordreEnlever;
	private ArrayList<Reponse> lstReponsesAsso; 

	public Reponse(String reponse, boolean valeur, int ordreEnlever) 
	{
		this.reponse      = reponse     ;
		this.valeur       = valeur      ;
		this.ordreEnlever = ordreEnlever;
	}

	public Reponse(String reponse) 
	{
		this.reponse      = reponse     ;
		this.lstReponsesAsso       = new ArrayList<Reponse>()      ;
	}

	public void associerReponse(Reponse r)
	{
		if(lstReponsesAsso.contains(r))
				return;	

		this.lstReponsesAsso.add(r);
		return;
	}

	/***********************/
	/*		GETTEURS	   */
	/***********************/

	public String  getReponse     () {return reponse     ;}
	public boolean isValeur       () {return valeur      ;}
	public int     getOrdreEnleverordreEnlever() {return ordreEnlever;}

	public String  getReponseAsso () 
	{
		String sRet = "";
	
		for(int i = 0; i < this.lstReponsesAsso.size(); i++)
		{
			//System.out.print("remi ");
			System.out.print(this.lstReponsesAsso.get(i).getReponse().strip() + "/");

		}

		return sRet;
	}

	public ArrayList<Reponse> getLstRep() {return lstReponsesAsso;}


	/***********************/
	/*		SETTEURS	   */
	/***********************/

	public void setReponse     (String  reponse     ) {this.reponse      = reponse     ;}
	public void setValeur      (boolean valeur      ) {this.valeur       = valeur      ;}
	public void setOrdreEnleverordreEnlever(int     ordreEnlever) {this.ordreEnlever = ordreEnlever;}

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

	
	@Override
	public String toString() 
	{
		return "Reponse [reponse=" + reponse + ", valeur=" + valeur + ", ordreEnlever=" + ordreEnlever + "]";
	}

	//methode afficher réponse dans le terminal
	public String afficherReponse()
	{
		return "Reponse : " + reponse + " | Valeur : " + valeur + " | Ordre : " + ordreEnlever  + "\n";
	}

	
}
