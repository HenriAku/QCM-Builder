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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ecriture
{
	private int    numFichier           ;
	private String emplacementRessources;

	///////////////////
	// CONSTRUCTEURS //
	///////////////////
	/**
	 * Constructeur de la classe Ecriture
	 */
	public Ecriture(String emplacementRessources) 
	{
		this.emplacementRessources = emplacementRessources;
	}

	//////////////
	// DOSSIERS //
	//////////////

	public String getEmp()
	{
		return this.emplacementRessources;
	}

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
		File dossier = new File( ".."+ File.separator +"ressources"+ File.separator + ressource.getNom() );

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
		File dossier = new File( ".."+File.separator+"ressources"+ File.separator + ressource.getNom() + File.separator + notion.getNom() );

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

	public boolean supprimerDossierQuestion(String chemin) 
	{
		File dossier = new File(chemin);
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
	 * @return true si le fichier a bien été créé
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

	/**
	 * crée un dossier dans 'ressources/chemin/nomDossier'
	 * 
	 * @param nomDossier le nom du dossier qui doit être créé
	 * @param chemin la ressource et la notion 
	 * @return true si le dossier a bien été créé
	 */
	public boolean creerDossierQuestion(String nomDossier,String chemin)
	{
		File dossier = new File(emplacementRessources  + File.separator + chemin + File.separator + nomDossier);

		if (!dossier.exists()) 
			return dossier.mkdirs(); // mkdirs() crée le dossier et tous ses parents si nécessaires

		return false; // Le dossier existe déjà	
	}

	/**
	 * crée un fichier csv qui contient une question Elimination
	 * 
	 * @param enlv la question Elimination
	 * @param chemin l'endroit une le fichier csv va être créé
	 */
	public void creerElimination(Elimination enlv, String chemin)
	{
		File     dossierVerif = new File(emplacementRessources + File.separator + chemin);
		String[] lstDos       = dossierVerif.list();
		String   nomQuestion  = null;
		int      numQuestion  = 1   ;


		while(nomQuestion == null)
		{
			boolean exist = false;

			for(int i = 0; i < lstDos.length; i++)
			{
				if(lstDos[i].equals("Question " + numQuestion))
					exist = true;
			}

			if(exist)
				numQuestion++;
			else
				nomQuestion = "Question " + numQuestion;
		}

		creerDossierQuestion(nomQuestion,chemin);

		File Enlv = new File(emplacementRessources + File.separator + chemin + File.separator + nomQuestion + File.separator + (nomQuestion + ".csv"));
		try 
		{
			if (!Enlv.exists()) 
				Enlv.createNewFile(); // Crée le fichier
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

			contenu = Enlv.getParentFile().getName() + "\t" + "E" + "\t\nDebutQuestion\n" + enlv.getQuestion() + "\nDebutExplication\n" + enlv.getExplication() + "\nFinExplication\n" + enlv.getDifficulte().getDifficulte() + "\t" +
					enlv.getPoint()  + "\t" + enlv.getTemps() + "\n";

			writer.append(contenu);


			for(int i = 0; i < enlv.getLstRep().size(); i++)
			{
				ordre   = String.valueOf(enlv.getLstRep().get(i).getOrdreEnleve ());
				nbPoint = String.valueOf(enlv.getLstRep().get(i).getNbPointEleve());
				
				contenu = enlv.getLstRep().get(i).getReponse() + "\t" + (enlv.getLstRep().get(i).getValeur() ? "vrai" : "faux") + "\t" + ordre + "\t" +
				nbPoint + "\n";
				writer.append(contenu);
			}

			contenu = "FIN";
			writer.append(contenu);

			if(enlv.getFilePath() != null)
			{
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

	/**
	 * Renvoie l'extension du nom de fichier en paramètre
	 * 
	 * @param filename le nom du fichier
	 * @return un String qui contient le nom de l'extension
	 */
	public String getFileExtension(String filename) 
	{
		int lastIndex = filename.lastIndexOf('.');
		if (lastIndex > 0 && lastIndex < filename.length() - 1) 
			return filename.substring(lastIndex + 1);
			
		return "";
	}

	/**
	 * Copie un fichier dans un dossier spécifié et le renomme avec un numéro de question.
	 * 
	 * @param sourcePath le chemin complet du fichier source à copier
	 * @param destinationDir le chemin du dossier de destination
	 * @param numQuest le numéro de la question utilisé pour renommer le fichier
	 */
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

			//System.out.println(sourceFile.getPath() + " | " + destinationFile.getPath() + " | " + destinationFile.getParent());

			// Copiez le fichier
			System.out.println(destinationDir + " le  dir");
			System.out.println(destinationFile + " le  File");

			Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

			numQuest = verifRessource((destinationFile.getParentFile().getParentFile().getParentFile().getParent()));

			destinationFile.renameTo(new File(destinationFile.getParent() + File.separator + "fic" + numQuest + "." + getFileExtension(sourceFile.getPath())));

			System.out.println("Fichier copiée dans : " + destinationFile.getParent());
		} 
		catch (IOException e) 
		{
			System.out.println("Erreur lors de la copie du fichier : " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 *Vérifie les ressources dans un répertoire donné et retourne le plus petit numéro de fichier manquant.
	 *
	 * @param ressourcePath le chemin vers le répertoire des ressources à vérifier
	 * @return le plus petit numéro de fichier manquant
	 */
	public int verifRessource(String ressourcePath)
	{
		int numFic = 1;
		ArrayList<Integer> lstNumFic = new ArrayList<>();

		String[] lstNot = new File(ressourcePath).list();

		for (String not : lstNot) 
		{
			lstNumFic.addAll(verifNotion((ressourcePath + File.separator + not)));
		}

		for (int intFic : lstNumFic) 
		{
			if(!lstNumFic.contains(numFic))
			{
				return numFic;
			}
			numFic++;
		}

		return numFic;
	}

	/**
	 * Vérifie les notions dans le répertoire en argument et retourne une liste des numéros de fichiers détectés.
	 *
	 * @param notionPath le chemin vers le répertoire d'une notion spécifique
	 * @return une liste des numéros de fichiers trouvés dans le répertoire notion
	 */
	public ArrayList<Integer> verifNotion(String notionPath)
	{
		ArrayList<Integer> numFic = new ArrayList<Integer>();
	
		String[] lstQuest = new File(notionPath).list();

		if(lstQuest == null)
		{
			return numFic;
		}

		File compPath;

		System.out.println("nb qst : " + lstQuest.length);

		for (String qst : lstQuest) 
		{
			System.out.println("question : " + qst);

			compPath = new File(notionPath + File.separator + qst + File.separator + "complement");

			if(compPath.exists())
			{
				System.out.println(compPath.list()[0]);

				String numero = null;
	
				Pattern pattern = Pattern.compile("fic(\\d+)\\.\\w+"); 
				Matcher matcher = pattern.matcher(compPath.list()[0]);
				
				if (matcher.matches()) 
				{
					numero = matcher.group(1);
				}
	
				System.out.println("num : " + numero);
	
				if(numero != null)
					numFic.add(Integer.parseInt(numero));
			}
			
		}

		return numFic;
	}

	/**
	 * Recherche une question dans le dossier des ressources
	 * 
	 * @param question la question
	 * @param ressource la ressource
	 * @param notion la notion
	 * @return un String qui contient le chemin de la question
	 */
	public String rechercherFichierQuestion(Question question, Ressource ressource, Notion notion)
	{
		File dossier = new File(this.emplacementRessources);

		if (dossier.exists() && dossier.isDirectory())
		{
			File[] fichiers = dossier.listFiles();
			if (fichiers != null)
			{
				for (File fichier : fichiers) 
				{
					if (fichier.isDirectory() && fichier.getName().equals(ressource.getNom()))
					{

						File dossierRessource = new File(fichier.getPath());
						File[] fichiersDossierRessources = dossierRessource.listFiles();
						for (File fichierRessources:fichiersDossierRessources)
						{

							if (fichierRessources.isDirectory() && fichierRessources.getName().equals(notion.getNom()))
							{

								File dossierNotion = new File(fichierRessources.getPath());
								File[] fichiersDossierNotions = dossierNotion.listFiles();
								for (File fichierNotions:fichiersDossierNotions)
								{
									if (fichierNotions.isDirectory())
									{

										File dossierQuestion = new File(fichierNotions.getPath());
										File[] fichiersDossierQuestions = dossierQuestion.listFiles();
										for (File fichierQuestions:fichiersDossierQuestions)
										{
											if (fichierQuestions.isFile())
											{
												// Parcours des fichiers .csv et compare les infos pour savoir lequels est celui de la question
												try
												{
													Scanner sc = new Scanner(new FileInputStream(fichierQuestions.getPath()), "UTF-8");

													Scanner scLine = new Scanner(sc.nextLine());
													scLine.useDelimiter("\t");
													scLine.next(); // Question + son numéro
													scLine.next(); // Type
													

													while (!sc.nextLine().equals("DebutQuestion")) ;


													String q = "";
													String tmp =sc.nextLine();
													while ((!tmp.equals("DebutExplication")) )
													{														
														q += tmp;
														tmp = sc.nextLine();
														if (!tmp.equals("DebutExplication"))
															q += "\n";
													}

													while (!sc.nextLine().equals("FinExplication")) ;

													System.out.println("\n----------------------------------\n");
													System.out.println("Test comparaison "+ q.equals(question.getQuestion()));
													System.out.println(q);
													System.out.println( question.getQuestion());

													String questionNettoyee = nettoyerString(question.getQuestion());
													String qNettoyee = nettoyerString(q);

													
													if (qNettoyee.equals(questionNettoyee))
													{
														Scanner line = new Scanner(sc.nextLine());
														line.useDelimiter("\t");
														if (line.next().equals(question.getDifficulte().getDifficulte()))
														{
															System.out.println("test 1");
															if (Double.parseDouble(line.next().replace(',', '.')) == question.getPoint())
															{
																System.out.println("test 2");

																if (Float.parseFloat(line.next().replace(',', '.')) == question.getTemps())
																{
																	System.out.println("test 3");

																	scLine.close();
																	sc.close();
																	return fichierQuestions.getPath();
																}
															}
														}
													}
													scLine.close();
													

													sc.close();
												}
												catch (Exception e){ e.printStackTrace(); }
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return "";
	}

	private String nettoyerString(String input) {
		return input.replace("\r", "").trim(); // Supprime les \r et les espaces inutiles
	}
	

	/**
	 * Crée une question QCM
	 * 
	 * @param qst la question QCM
	 * @param chemin le chemin où elle doit être créé
	 */
	public void creerQCM(QCM qst, String chemin)
	{
		File     dossierVerif = new File(emplacementRessources + File.separator + chemin);
		String[] lstDos       = dossierVerif.list();
		String   nomQCM       = null;
		int      numQCM       = 1   ;


		System.out.println("test ecriture  " + qst.getFilePath());


		while(nomQCM == null)
		{
			boolean exist = false;

			for(int i = 0; i < lstDos.length; i++)
			{
				if(lstDos[i].equals("Question " + numQCM))
					exist = true;
			}

			if(exist)
				numQCM++;
			else
				nomQCM = "Question " + numQCM;
		}

		creerDossierQuestion(nomQCM,chemin);

		File QCM = new File(emplacementRessources + File.separator + chemin + File.separator + nomQCM + File.separator + (nomQCM + ".csv"));
		try 
		{
			if (!QCM.exists()) 
				QCM.createNewFile(); // Crée le fichier
		} 
		catch (IOException e) 
		{
			System.err.println("Erreur lors de la création du fichier : " + e.getMessage());
		}

		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
			new FileOutputStream(QCM), "UTF-8"))) 
		{
			String contenu;

			contenu = QCM.getParentFile().getName() + "\t" + "Q" + "\t\nDebutQuestion\n" + qst.getQuestion() + "\nDebutExplication\n" + qst.getExplication() + "\nFinExplication\n" + qst.getDifficulte().getDifficulte() + "\t" +
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
	
	/**
	 * Crée une question Association
	 * 
	 * @param qst la question Association
	 * @param chemin le chemin où elle doit être créé
	 */
	public void creerAssociation(Association asso, String chemin) 
	{
		File     dossierVerif = new File(this.emplacementRessources + File.separator + chemin);
		String[] lstDos       = dossierVerif.list();
		String   nomQCM       = null;
		int      numQCM       = 1   ;


		while(nomQCM == null)
		{
			boolean exist = false;

			for(int i = 0; i < lstDos.length; i++)
			{
				if(lstDos[i].equals("Question " + numQCM))
					exist = true;
			}

			if(exist)
				numQCM++;
			else
				nomQCM = "Question " + numQCM;
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
			String contenu =
					associationFile.getParentFile().getName()+"\t"+
					"A"+
					"\nDebutQuestion\n"+
					asso.getQuestion()+
					"\nDebutExplication\n"+
					asso.getExplication()+
					"\nFinExplication\n"+
					asso.getDifficulte().getDifficulte()+ "\t"+
					asso.getPoint()+"\t"+
					asso.getTemps();
	
			writer.append(contenu);
			writer.append("\nReponse\n");
	
			// Écrire les correspondances des réponses et leurs associés
			ArrayList<ReponseAsso> lstRep     = asso.getLstRep();
			
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
				String cheminImage = emplacementRessources  + File.separator + chemin + File.separator + nomQCM + File.separator + "complement";
				creerDossierQuestion("complement", chemin + File.separator + nomQCM);
				copierFichierDansDossier(asso.getFilePath(), cheminImage, numQCM);
				
			}
			//System.out.println("Association créée avec succès dans le fichier : " + associationFile.getPath());
	
		} catch (IOException e) {
			System.out.println("Une erreur s'est produite : " + e.getMessage());
		}
	}

	/**
	 * crée un dossier dans 'ressources/ressource'
	 * 
	 * @param ressource la ressource qui correspond au dossier
	 * @param notion  le notion qui correspond au dossier
	 * @param question  la question qui va être créée dans le fichier texte
	 * @return true si le dossier à bien été crée
	 */
	public boolean creerTxtQuestion(Ressource ressource, Notion notion,Question question) {return true;}
}