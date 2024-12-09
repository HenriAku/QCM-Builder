package Metier;

import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Lire 
{
	private String contenuFichier;
	private String emplacementRessources;

	public static void main(String[] args) 
	{
		Lire l = new Lire("./../ressources/ChapitreTest/");

		l.lireQuestion("./../ressources/ChapitreTest/");
	}

	public Lire(String emplacementRessources) 
	{
		this.emplacementRessources = emplacementRessources;
	}

	// Retourne un tableau qui contient dans chaque case le nom des sous dossiers de nomDossier
	public ArrayList<String> lireDossier(String nomDossier)
	{
		File dossier = new File(emplacementRessources + nomDossier);
		ArrayList<String> nomsSousDossiers = new ArrayList<>();
		
		if (dossier.exists() && dossier.isDirectory())
		{
			File[] sousDossiers = dossier.listFiles(File::isDirectory);
			if (sousDossiers != null)
			{
				for (File sousDossier : sousDossiers)
				{
					nomsSousDossiers.add(sousDossier.getName());
				}
			}
		}
		return nomsSousDossiers;
	}

	public String getFichier()
	{
		try
		{
			FileReader fl = new FileReader(this.emplacementRessources);
			Scanner sc = new Scanner(fl);

			while(sc.hasNextLine())
			{
				this.contenuFichier += sc.nextLine() + "\n"; 
			}

		}
		catch(Exception e) { e.getStackTrace();}

		return this.contenuFichier;
	}


	/*
		chapitre5	A	qst
			Reponse
			rep1 	
			rep2 	
			rep3	
			rep4
			Association
			rep1	rep2	rep4 	
			rep2 	rep4	rep1	rep3
			rep3	rep2	rep4
			rep4	rep1	rep2	rep3
		FIN

		chapitre7	Q	qst	
			rep1	vrai
			rep2	faux
			rep3	vrai
			rep4	vrai
			rep5 	faux
		FIN
	*/

	//Emplacement fichier doit etre une ../ressources/X/
	public ArrayList<Question> lireQuestion(String emplacementFichier)
	{
		ArrayList<Question> lstQuestion = new ArrayList<Question>();

        File dossierChapitre = new File(emplacementFichier);

		//vérifier que l'endroit indiqué est bien un dossier
        if (dossierChapitre.exists() && dossierChapitre.isDirectory()) 
		{
			//récupérer tout les dossiers questions dans le dossier chapitreX 
			File[] dossierQuestions = dossierChapitre.listFiles();

            if (dossierQuestions != null) 
			{
				//pour chaque dossier questions on va récupérer les .data (soit les questions)
                for (File dossierQuestion : dossierQuestions) 
				{
                    if (dossierQuestion.isDirectory()) 
					{
						//récupération des fichier 
                        File[] fichiers = dossierQuestion.listFiles();
                        if (fichiers != null) 
						{
                            for (File fichier : fichiers) 
							{
                                // Vérifier si c'est un fichier .data
                                if (fichier.isFile() && fichier.getName().endsWith(".data")) 
								{
                                    try 
									{
										String contenu = Files.readString(fichier.toPath());
										lstQuestion.add(creerQuestion(contenu));
										
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

		for(int z = 0; z < lstQuestion.size(); z++)
		{
			System.out.println(lstQuestion.get(z).getQuestion());
			for (Reponse question : lstQuestion.get(z).getLstRep()) 
			{
				System.out.println(question.afficherReponse());
			}
		}

		return lstQuestion;
	}

	private Question creerQuestion(String qst)
	{
		//première ligne
		String type = "";
		String question = "";
		String explication = "";
		String difficulte = "";
		int point  = -1;
		int timeMin  = -1;
		int timeSec  = -1;

		String[] lines = qst.split("\n");
		
		String[] premiereLigne = lines[0].split("\t");
		
		type = premiereLigne[1];
		question = premiereLigne[2];
		explication = premiereLigne[3];
		difficulte = premiereLigne[4]; 

		point = Integer.parseInt(premiereLigne[5]);
		timeMin = Integer.parseInt(premiereLigne[6].strip());
		timeSec = Integer.parseInt(premiereLigne[7].strip());

		ArrayList<Reponse> lstRep = new ArrayList<Reponse>();

		switch(type)
		{
			case "Q":
				for(int i = 1; i<lines.length; i++)
				{
					if(lines[i].equals("FIN"))
					{
						break;
					}
					String[] contenuLigne = lines[i].split("\t");
					lstRep.add(new Reponse(contenuLigne[0], (contenuLigne[1].equals("vrai")) ? true : false, Integer.parseInt(contenuLigne[2].strip())));
				}
				break;

			case "A":

				break;

			default:
				break;
		}

		Question qstRet = new Question(question, type, explication, difficulte, point, timeMin, timeSec, lstRep);
		
		return qstRet;
	}

	public void setEmplacementRessources(String e)
	{
		this.emplacementRessources = e;
	}

	public String getEmplacementRessources()
	{
		return this.emplacementRessources;
	}
	
	
}