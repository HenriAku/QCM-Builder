package Controlleur;

import java.util.List;

import IHM.FramePrincipal;
import Metier.Metier;
import Metier.Ressource;

public class Controlleur
{
	private Metier         metier;
	private FramePrincipal frame;
	public Controlleur()
	{
		this.metier = new Metier   		(this);
		this.frame  = new FramePrincipal(this);
	}

	public boolean creerDossier(String nomRes)
	{
		return this.metier.creerDossier(nomRes);
	}

	public void addRessource(Ressource ressource)
	{
		this.metier.addRessource(ressource);
	}

	/**
	 * Appele la methode de metier pour chercher une ressource
	 * @param nom d'une ressource
	 * @return Une "Ressource"
	 */
	public Ressource rechercheRessource(String nom){return this.metier.rechercheRessource(nom);}

	public List<Ressource> getLstRessource() {return this.metier.getLstRessource();}

	public static void main(String[] args) 
	{
		new Controlleur();
	}
}
