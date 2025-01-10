/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */

package Metier;

import java.util.*;
import java.io.*;
import java.nio.file.Files;

public class Lire 
{
	///////////////
	// ATTRIBUTS //
	///////////////

	private String contenuFichier       ;
	private String emplacementRessources;

	public Lire(String emplacementRessources) 
	{
		this.emplacementRessources = emplacementRessources;
	}

	//////////////
	// METHODES //
	//////////////

	// Retourne un tableau qui contient dans chaque case le nom des sous dossiers de nomDossier
	public ArrayList<String> lireDossier(String nomDossier)
	{
		File              dossier          = new File(emplacementRessources+ File.separator + nomDossier);
		ArrayList<String> nomsSousDossiers = new ArrayList<>();
		
		if (dossier.exists() && dossier.isDirectory())
		{
			File[] sousDossiers = dossier.listFiles(File::isDirectory);
			if (sousDossiers != null)
			{
				for (File sousDossier : sousDossiers)
					nomsSousDossiers.add(sousDossier.getName());
			}
		}
		return nomsSousDossiers;
	}

	/**
	 * Renvoie le contenu du fichier en String
	 * 
	 * @return un String qui contient le fichier
	 */
	public String getFichier()
	{
		try
		{
			FileReader fl = new FileReader(this.emplacementRessources);
			Scanner    sc = new Scanner(fl);

			while(sc.hasNextLine())
				this.contenuFichier += sc.nextLine() + "\n"; 

			sc.close();
		}
		catch(Exception e) { e.getStackTrace();}

		return this.contenuFichier;
	}

	//Emplacement fichier doit etre une ../ressources/X/
	public ArrayList<Question> lireQuestion(String emplacementFichier) 
	{
		ArrayList<Question> lstQuestion = new ArrayList<>();
	
		File dossierNotion = new File(emplacementFichier);
	
		// Vérifier que l'endroit indiqué est bien un dossier
		if (dossierNotion.exists() && dossierNotion.isDirectory()) 
		{
			// Récupérer tous les dossiers questions dans le dossier notionX
			File[] dossierQuestions = dossierNotion.listFiles();
			if (dossierQuestions != null) {
				// Pour chaque dossier questions, récupérer les .csv (soit les questions)
				for (File dossierQuestion : dossierQuestions) 
				{
					if (dossierQuestion.isDirectory()) 
					{
						// Récupération des fichiers
						File[] fichiers = dossierQuestion.listFiles();
						if (fichiers != null) {
							for (File fichier : fichiers) {
								// Vérifier si c'est un fichier .csv
								if (fichier.isFile() && fichier.getName().endsWith(".csv")) 
								{
									try 
									{
										String cheminComplement = null;
	
										// Vérifier s'il existe un dossier "complement" au même niveau
										File parentDir = fichier.getParentFile(); // Dossier parent contenant le .csv
										if (parentDir != null) 
										{
											File complementDir = new File(parentDir, "complement"); // Dossier "complement"
											if (complementDir.exists() && complementDir.isDirectory()) 
											{
												// Récupérer le premier fichier dans le dossier "complement"
												File[] fichiersComplement = complementDir.listFiles();
												if (fichiersComplement != null && fichiersComplement.length > 0) 
												{
													// Utiliser le premier fichier trouvé
													cheminComplement = fichiersComplement[0].getAbsolutePath();
												} 
											}
										}
	
										// Lire le contenu du fichier .csv
										String contenu = Files.readString(fichier.toPath());
	
										// Ajouter la question avec le chemin complément
										if(cheminComplement == null){cheminComplement = "";}

										lstQuestion.add(creerQuestion(contenu, cheminComplement));

									} 
									catch (IOException e) 
									{
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
		}
	
		return lstQuestion;
	}

	private Question creerQuestion(String qst, String cheminComplement)
	{
		//première ligne
		String type              = "";
		String question          = "";
		String explication       = "";
		String stringdifficulte  = "";

		double point = -1   ;
		Float  time  = 0.00f;

		String[] lines = qst.split("\n")     ;
		String[] ligne = lines[0].split("\t");
		type           = ligne[1];
	
		int cpt =2;
		while (!lines[cpt].equals("DebutExplication")) 
		{
			question += lines[cpt];
			cpt++;
			if (!lines[cpt].equals("DebutExplication")) 
			{
				question += "\n";
			}
		}

		int cpt2 =cpt+1;
		while (!lines[cpt2].equals("FinExplication")) 
		{
			explication += lines[cpt2];
			cpt2++;
			if (!lines[cpt2].equals("FinExplication")) 
			{
				explication += "\n";
			}
		}

		ligne             = lines[cpt2+1].split("\t");
		stringdifficulte  = ligne[0]; 

		point = Double.parseDouble(ligne[1].replace(",", "."));
		time = Float.parseFloat(ligne[2].strip().replace(",", "."));

		ArrayList<ReponseQcm> repQ = null;
		ArrayList<ReponseAsso> repA = null;
		ArrayList<ReponseElimination> repE = new ArrayList<ReponseElimination>();

		switch(type)
		{
			case "Q":
				ArrayList<ReponseQcm> lstRep = new ArrayList<ReponseQcm>();
				for(int i = cpt2+2; i<lines.length; i++)
				{
					if(lines[i].equals("FIN"))
					{
						break;
					}
					String[] contenuLigne = lines[i].split("\t");
					lstRep.add(new ReponseQcm(contenuLigne[0], (contenuLigne[1].equals("vrai")) ? true : false));
				}
				repQ = lstRep;
				break;

				case "A":
					ArrayList<ReponseAsso> lstReponsesAsso = new ArrayList<>();
					String etape = "";

					for (String line : lines) {
						String[] contenuLigne = line.split("\t");
						String   trimmedLine  = line.strip()    ;

						if (trimmedLine.equals("FIN"))
							break;

						if (trimmedLine.equals("Reponse")) {
							etape = "reponse";
						} else if (trimmedLine.equals("Association")) {
							etape = "association";
						} else {
							if (etape.equals("reponse")) {
								// Ajouter uniquement les réponses principales
								lstReponsesAsso.add(new ReponseAsso(contenuLigne[0].strip(), null));
							} else if (etape.equals("association")) {
								// Lire et associer les réponses (par paires uniquement)
								for (int i = 0; i < contenuLigne.length; i += 2) {
									String principale = contenuLigne[i].strip();
									String associe = contenuLigne[i + 1].strip();

									// Trouver la réponse principale existante
									ReponseAsso repPrincipale = null;
									for (ReponseAsso reponse : lstReponsesAsso) {
										if (reponse.getReponse().equals(principale)) {
											repPrincipale = reponse;
											break;
										}
									}

									// Si la réponse principale n'existe pas, ignorer (on ne crée pas ici)
									if (repPrincipale == null) {
										continue;
									}

									// Créer l'association uniquement si l'associé n'existe pas encore
									ReponseAsso repAssociee = repPrincipale.getAssocie();
									if (repAssociee == null) {
										repAssociee = new ReponseAsso(associe, null);
										repPrincipale.setAssocie(repAssociee);
									}
								}
							}
						}
					}

					repA = lstReponsesAsso;
					break;
			

			case "E":
				int i = cpt2+2;
				while (i < lines.length && ! lines[i].equals("FIN"))
				{
					Scanner scannerLine = new Scanner(lines[i]);
					scannerLine.useDelimiter("\t");
					String reponse = scannerLine.next();
					
					boolean valide = (scannerLine.next().equals("vrai")) ? true : false;
					int ordreElimination = Integer.parseInt(scannerLine.next());
					double nbPointPerdu = Double.parseDouble(scannerLine.next());

					repE.add(new ReponseElimination(reponse, ordreElimination, nbPointPerdu, valide));
					scannerLine.close();
					i++;
				}
				
			default:
				break;
		}

		Difficulte difficulte = Difficulte.TF;
		switch (stringdifficulte.toLowerCase()) 
		{
			case "tres facile": difficulte = Difficulte.TF;
				break;
				
			case "facile"     : difficulte = Difficulte.F ;
				break;

			case "moyen"      : difficulte = Difficulte.M ;
				break;

			case "difficile"  : difficulte = Difficulte.D ;
				break;
		
			default:
				break;
		}

		if(type.equals("Q"))
		{
			if (cheminComplement.length() !=0) 
				return new QCM(question, explication, difficulte, point, time, repQ, cheminComplement);
			else
				return new QCM(question, explication, difficulte, point, time, repQ);
		}

		if(type.equals("A"))
		{
			if (cheminComplement.length() !=0) 
				return new Association(question, explication, difficulte, point, time, repA, cheminComplement);
			else
				return new Association(question, explication, difficulte, point, time, repA);
		}

		if(type.equals("E"))
		{
			if (cheminComplement.length() !=0) 
				return new Elimination(question, explication, difficulte, point, time, repE,cheminComplement);
			else
				return new Elimination(question, explication, difficulte, point, time, repE);
		}
		return null;
	}

	/////////////
	// SETTERS //
	/////////////

	public void   setEmplacementRessources(String e){this.emplacementRessources = e   ;}

	/////////////
	// GETTERS //
	/////////////

	public String getEmplacementRessources(        ){return this.emplacementRessources;}
}