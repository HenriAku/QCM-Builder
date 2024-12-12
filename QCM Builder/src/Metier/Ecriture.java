/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package Metier;

import java.io.*;

public class Ecriture
{

	private String emplacementRessources;

	public Ecriture(String emplacementRessources) {this.emplacementRessources = emplacementRessources;}

	//////////////////
	// DOSSIERS		//
	//////////////////

	/**
	 * crée un dossier dans 'ressources/nomFichier'
	 * @param nomDossier l'emplacement dans lequel le dossier va être créé
	 * @return true si le fichier à bien été crée
	 */
	public boolean creerDossier(String nomDossier)
	{
		File dossier = new File(emplacementRessources + nomDossier);

		if (!dossier.exists()) 
			return dossier.mkdirs(); // mkdirs() crée le dossier et tous ses parents si nécessaires

		return false; // Le dossier existe déjà	
	}

	/**
	 * crée un dossier dans 'ressources'
	 * @param ressource la ressource qui correspond au dossier
	 * @return true si le dossier à bien été crée
	 */
	public boolean creerDossierRessource(Ressource ressource)
	{
		File dossier = new File( "./ressources/" + ressource.getNom() );

		if (!dossier.exists())
			return dossier.mkdirs();

		return false;
	}

	/**
	 * crée un dossier dans 'ressources/ressource'
	 * @param ressource la ressource qui correspond au dossier
	 * @param notion  le notion qui correspond au dossier
	 * @return true si le dossier à bien été crée
	 */
	public boolean creerDossierNotion(Ressource ressource, Notion notion)
	{
		File dossier = new File( "./ressources/" + ressource.getNom() + "" + notion.getNom() );

		if (!dossier.exists())
			return dossier.mkdirs();

		return false;
	}

	/**
	 * Modifie le nom d'un dossier
	 * @param AncienNom du dossier
	 * @param NouveauNom du dossier
	 * @return true si il a etait créer
	 */
	public boolean renommerDossier(String ancienNom, String nouveauNom)
    {
        File dossier = new File(emplacementRessources + File.separator + ancienNom);

        if (dossier.exists()) 
        {
            File nouveauDossier = new File(emplacementRessources + File.separator + nouveauNom);

            if (!nouveauDossier.exists()) 
            {
                boolean success = dossier.renameTo(nouveauDossier);
                return success; 
            } 
            else
            {
                System.out.println("Le dossier avec le nouveau nom existe déjà.");
            }
        } 
        else 
            System.out.println("Le dossier à renommer n'existe pas.");

        return false; 
    }

	/**
	 * Supprime un dossier 
	 * @param nomDossier du dossier
	 * @return true si il a etait supprimer 
	 */
	public boolean supprimerDossier(String nomDossier)
    {
        File dossier = new File(emplacementRessources + File.separator + nomDossier);
		 return dossier.delete();
    }

	//////////////////
	// FICHIERS		//
	//////////////////

	/**
	 * crée un dossier dans 'ressources/nomFichier'
	 * @param nomFichier l'emplacement audans lequel le fichier va être créé, comprend son nom
	 * @return true si le fichier à bien été crée
	 */
	public boolean creerFichier(String nomFichier)
	{

		File fichier = new File(emplacementRessources + nomFichier);
		try 
		{
			if (!fichier.exists()) 
			{
				fichier.getParentFile().mkdirs(); // Crée les dossiers parents si nécessaires
				fichier.createNewFile(); // Crée le fichier
			}
		} 
		catch (IOException e) 
		{
			System.err.println("Erreur lors de la création du fichier : " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * crée un dossier dans 'ressources/ressource'
	 * @param ressource la ressource qui correspond au dossier
	 * @param notion  le notion qui correspond au dossier
	 * @param question  la question qui va être créée dans le fichier texte
	 * @return true si le dossier à bien été crée
	 */
	public boolean creerTxtQuestion(Ressource ressource, Notion notion,Question question)
	{
		return true;
	}
}