/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package Metier;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
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
		File dossier = new File(emplacementRessources + File.separator + nomDossier);

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
		File dossier = new File( "./ressources"+ File.separator + ressource.getNom() );

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
		File dossier = new File( "./ressources"+ File.separator + ressource.getNom() + "" + notion.getNom() );

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
		return supprimerRecursivement(dossier);
	}
	
	private boolean supprimerRecursivement(File fichier) 
	{
		if (fichier.isDirectory()) {
			// Récupérer la liste des fichiers et dossiers dans ce dossier
			File[] fichiers = fichier.listFiles();
			if (fichiers != null) {
				for (File f : fichiers) {
					// Appel récursif pour chaque fichier/sous-dossier
					supprimerRecursivement(f);
				}
			}
		}
		// Supprimer le fichier ou dossier actuel
		return fichier.delete();
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

		File fichier = new File(emplacementRessources + File.separator + nomFichier);
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

	public void creerElimination(Enlevement enlv, String chemin)
	{
		File dossierVerif = new File(emplacementRessources + File.separator + chemin);
		String[] lstDos = dossierVerif.list();
		String nomQuestion = null;
		int numQuestion = 1;


		while(nomQuestion == null)
		{
			boolean exist = false;

			for(int i = 0; i < lstDos.length; i++)
			{
				if(lstDos[i].equals("Question " + numQuestion))
				{
					exist = true;
				}
			}

			if(exist)
				numQuestion++;
			else
			{
				nomQuestion = "Question " + numQuestion;
			}
		}

		creerDossierQuestion(nomQuestion,chemin);

		File Enlv = new File(emplacementRessources + File.separator + chemin + File.separator + nomQuestion + File.separator + (nomQuestion + ".csv"));
		try 
		{
			if (!Enlv.exists()) 
			{
				Enlv.createNewFile(); // Crée le fichier
			}

		} 
		catch (IOException e) 
		{
			System.err.println("Erreur lors de la création du fichier : " + e.getMessage());
		}

		try (FileWriter writer = new FileWriter(Enlv)) 
		{

			String ordre   = "";
			String nbPoint = "";
			String contenu;

			contenu = Enlv.getParentFile().getName() + "\t" + "E" + "\t" + enlv.getQuestion() + "\t" + enlv.getExplication() + "\t" + enlv.getDifficulte().getDifficulte() + "\t" +
					enlv.getPoint()  + "\t" + enlv.getTemps() + "\n";

			writer.append(contenu);


			for(int i = 0; i < enlv.getLstRep().size(); i++)
			{
				ordre = String.valueOf(enlv.getLstRep().get(i).getOrdreEnleve());
				nbPoint = String.valueOf(enlv.getLstRep().get(i).getNbPointEleve());
				
				contenu = enlv.getLstRep().get(i).getReponse() + "\t" + (enlv.getLstRep().get(i).getValeur() ? "vrai" : "faux") + "\t" + ordre + "\t" +
				nbPoint + "\n";
				writer.append(contenu);

			}

			contenu = "FIN";
			writer.append(contenu);

			if(enlv.getFilePath() != null)
			{
				System.out.println(enlv.getFilePath());
				String cheminImage = emplacementRessources  + File.separator + chemin + File.separator + nomQuestion + File.separator + "complement";
				creerDossierQuestion("complement", chemin + File.separator + nomQuestion);
				copierFichierDansDossier(enlv.getFilePath(), cheminImage, numQuestion);
				
			}


		} 
		catch (IOException e) 
		{
			System.out.println("Une erreur s'est produite : " + e.getMessage());
		}
	}

	public String getFileExtension(String filename) 
	{
        int lastIndex = filename.lastIndexOf('.');
        if (lastIndex > 0 && lastIndex < filename.length() - 1) 
		{
            return filename.substring(lastIndex + 1);
        }
        return ""; // Pas d'extension trouvée
    }

	public void copierFichierDansDossier(String sourcePath, String destinationDir, int numQuest) 
	{
		try 
		{
			// Vérifiez que le dossier existe, sinon créez-le
			File dir = new File(destinationDir);
			if (!dir.exists()) 
			{
				if (dir.mkdirs()) 
				{
					System.out.println("Dossier créé : " + destinationDir);
				} 
				else 
				{
					System.out.println("Impossible de créer le dossier.");
					return;
				}
			}

			// Préparez le fichier source et la destination
			File sourceFile = new File(sourcePath);
			if (!sourceFile.exists()) 
			{
				System.out.println("Le fichier source n'existe pas : " + sourcePath);
				return;
			}

			// Construisez le chemin du fichier de destination
			File destinationFile = new File(dir, sourceFile.getName());
			System.out.println(sourceFile.getPath() + " | " + destinationFile.getPath() + " | " + destinationFile.getParent());

			// Copiez le fichier
			Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

			destinationFile.renameTo(new File(destinationFile.getParent() + File.separator + "flc" + numQuest + "." + getFileExtension(sourceFile.getPath())));

			System.out.println("Fichier copiée dans : " + destinationFile.getAbsolutePath());
		} 
		catch (IOException e) 
		{
			System.out.println("Erreur lors de la copie du fichier : " + e.getMessage());
			e.printStackTrace();
		}
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

		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
			new FileOutputStream(QCM), "UTF-8"))) 
		{
			String contenu;

			contenu = QCM.getParentFile().getName() + "\t" + "Q" + "\t" + qst.getQuestion() + "\t" + qst.getExplication() + "\t" + qst.getDifficulte().getDifficulte() + "\t" +
					qst.getPoint()  + "\t" + qst.getTemps() + "\n";

			writer.append(contenu);


			for(int i = 0; i < qst.getLstRep().size(); i++)
			{
				contenu = qst.getLstRep().get(i).getReponse() + "\t" + (qst.getLstRep().get(i).getValeur() ? "vrai" : "faux") + "\n";
				writer.append(contenu);

			}

			contenu = "FIN";
			writer.append(contenu);

			if(qst.getFilePath() != null)
			{
				String cheminImage = emplacementRessources  + File.separator + chemin + File.separator + nomQCM + File.separator + "complement";
				creerDossierQuestion("complement", chemin + File.separator + nomQCM);
				copierFichierDansDossier(qst.getFilePath(), cheminImage, numQCM);
				
			}
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
	
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
			new FileOutputStream(associationFile), "UTF-8"))) {
	
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
			ArrayList<ReponseAsso> lstRep     = asso.getLstRep();
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
						lstRep.get(i).getAssocie());
				writer.append(contenu);
			}
	
			// Indiquer la fin du fichier
			writer.append("FIN");
	
			if(asso.getFilePath() != null)
			{
				System.out.println(asso.getFilePath());
				String cheminImage = emplacementRessources  + File.separator + chemin + File.separator + nomQCM + File.separator + "complement";
				creerDossierQuestion("complement", chemin + File.separator + nomQCM);
				copierFichierDansDossier(asso.getFilePath(), cheminImage, numQCM);
				
			}
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