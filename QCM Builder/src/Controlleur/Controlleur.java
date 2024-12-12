/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package Controlleur;

import java.util.List;
import java.util.ArrayList;

import IHM.FramePrincipal;
import Metier.Difficulte;
import Metier.Metier;
import Metier.Ressource;

public class Controlleur
{
	private Metier         metier;
	private FramePrincipal frame ;
	public Controlleur()
	{
		this.metier = new Metier   		(    );
		this.frame  = new FramePrincipal(this);
	}

	public boolean creerDossier(String nomRes)
	{
		return this.metier.creerDossier(nomRes);
	}

	public boolean renommerDossier(String ancienNom, String nouveauNom)
	{
		return this.metier.renommerDossier(ancienNom, nouveauNom);
	}

	public boolean supprimerDossier(String nomDossier)
	{
		return this.metier.supprimerDossier(nomDossier);
	}


	public void addRessource(Ressource ressource)
	{
		this.metier.addRessource(ressource);
	}

	//cration d'une question
	public String validerQuestion (String ressource, String notion, String type, String difficulte, double point, String temps)
	{
		return this.metier.validerQuestion(ressource, notion, type, difficulte, point, temps);
	}

	public String creerQuestion(String r, String c, String question, String type, String explication, String difficulte, int point, String temps, ArrayList<String> reponses ,  ArrayList<Boolean> validite)
	{
		return (this.metier.creerQuestion(r, c, question, type, explication, difficulte, point, temps, reponses, validite));
	}

	/**
	 * Appele la methode de metier pour chercher une ressource
	 * @param nom d'une ressource
	 * @return Une "Ressource"
	 */
	public Ressource rechercheRessource(String nom){return this.metier.rechercheRessource(nom);}

	public List<Ressource> getLstRessource() {return this.metier.getLstRessource();}

	public String[] getNomRessources(          ) {return this.metier.getNomRessources(   );}
	public String[] getNomNotion    (String res) {return this.metier.getNomNotion    (res);}

	public static void main(String[] args) 
	{
		new Controlleur();
	}
}
