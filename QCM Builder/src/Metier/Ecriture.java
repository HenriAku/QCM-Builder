/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package Metier;

import java.io.*;
import java.util.ArrayList;
import Metier.QCM;

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

	public boolean creerDossierQuestion(String nomDossier,String chemin)
	{
		File dossier = new File(emplacementRessources  + File.separator + chemin + File.separator + nomDossier);

		if (!dossier.exists()) 
			return dossier.mkdirs(); // mkdirs() crée le dossier et tous ses parents si nécessaires

		return false; // Le dossier existe déjà	
	}

	public void creerQCM(QCM qst, String chemin)
	{
		File dossierVerif = new File(emplacementRessources + File.separator + chemin);
		String[] lstDos = dossierVerif.list();
		String nomQCM = null;
		int numQCM = 1;


		while(nomQCM == null)
		{
			boolean exist = false;

			for(int i = 0; i < lstDos.length; i++)
			{
				if(lstDos[i].equals("Question " + numQCM))
				{
					exist = true;
				}
			}

			if(exist)
				numQCM++;
			else
			{
				nomQCM = "Question " + numQCM;
			}
		}

		creerDossierQuestion(nomQCM,chemin);

		File QCM = new File(emplacementRessources + File.separator + chemin + File.separator + nomQCM + File.separator + (nomQCM + ".csv"));
		try 
		{
			if (!QCM.exists()) 
			{
				QCM.createNewFile(); // Crée le fichier
			}

		} 
		catch (IOException e) 
		{
			System.err.println("Erreur lors de la création du fichier : " + e.getMessage());
		}

		try (FileWriter writer = new FileWriter(QCM)) 
		{


        String contenu;

		contenu = QCM.getParentFile().getName() + "\t" + "Q" + "\t" + qst.getQuestion() + "\t" + qst.getExplication() + "\t" + qst.getDifficulte().getDifficulte() + "\t" +
				  qst.getPoint()  + "\t" + qst.getTemps() + "\n";

		writer.append(contenu);


		for(int i = 0; i < qst.getReponse().size(); i++)
		{
			contenu = qst.getReponse().get(i).getReponse() + "\t" + (qst.getReponse().get(i).getValeur() ? "vrai" : "faux") + "\n";
			writer.append(contenu);

		}

		contenu = "FIN";
		writer.append(contenu);


        } 
		catch (IOException e) 
		{
            System.out.println("Une erreur s'est produite : " + e.getMessage());
        }

	}
	
	public void creerAssociation(Association asso, String chemin) 
	{
		File dossierVerif = new File(emplacementRessources + File.separator + chemin);
		String[] lstDos = dossierVerif.list();
		String nomQCM = null;
		int numQCM = 1;


		while(nomQCM == null)
		{
			boolean exist = false;

			for(int i = 0; i < lstDos.length; i++)
			{
				if(lstDos[i].equals("Question " + numQCM))
				{
					exist = true;
				}
			}

			if(exist)
				numQCM++;
			else
			{
				nomQCM = "Question " + numQCM;
			}
		}

		// Créer le dossier pour la question d'association
		creerDossierQuestion(nomQCM, chemin);
	
		// Chemin du fichier CSV
		File associationFile = new File(emplacementRessources + File.separator + chemin + File.separator + nomQCM + File.separator + (nomQCM + ".csv"));
		try {
			if (!associationFile.exists()) {
				associationFile.createNewFile(); // Créer le fichier si inexistant
			}
		} catch (IOException e) {
			System.err.println("Erreur lors de la création du fichier : " + e.getMessage());
		}
	
		try (FileWriter writer = new FileWriter(associationFile)) {
	
			// Écrire les informations de la question principale
			String contenu = String.format("%s\t%s\t%s\t%s\t%s\t%.2f\t%.2f\n",
					associationFile.getParentFile().getName(),
					"A",
					asso.getQuestion(),
					asso.getExplication(),
					asso.getDifficulte().getDifficulte(),
					asso.getPoint(),
					asso.getTemps());
	
			writer.append(contenu);
			writer.append("Reponse\n");
	
			// Écrire les correspondances des réponses et leurs associés
			ArrayList<ReponseAsso> lstRep = asso.getLstRep();
			ArrayList<ReponseAsso> lstRepAsso = asso.getLstRepAsso();
			
			//affiche la list des rep
			for (int i = 0; i < lstRep.size(); i++) 
			{
				contenu = String.format("%s\n",
						lstRep.get(i).getReponse());
				writer.append(contenu);
			}

			writer.append("Association\n");


			//afficher les association
			for (int i = 0; i < lstRep.size(); i++) 
			{
				contenu = String.format("%s\t%s\n",
						lstRep.get(i).getReponse(),
						lstRepAsso.get(i).getReponse());
				writer.append(contenu);
			}
	
			// Indiquer la fin du fichier
			writer.append("FIN");
	
			System.out.println("Association créée avec succès dans le fichier : " + associationFile.getPath());
	
		} catch (IOException e) {
			System.out.println("Une erreur s'est produite : " + e.getMessage());
		}
	}


	public static void main(String[] args) 
	{
		ArrayList<ReponseQcm> QCM = new ArrayList<ReponseQcm>();

		QCM.add(new ReponseQcm("il",true));
		QCM.add(new ReponseQcm("va bien", false));

		Ecriture test = new Ecriture("QCM Builder" + File.separator + "ressources/");
		
		test.creerQCM(new QCM("il va bien ?", "humeur", Difficulte.TF, 2, 15, QCM),".");
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