/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */

package Metier;

import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Lire 
{
	private String contenuFichier;
	private String emplacementRessources;

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
		notion5	A	qst
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

		notion7	Q	qst	
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

        File dossierNotion = new File(emplacementFichier);

		//vérifier que l'endroit indiqué est bien un dossier
        if (dossierNotion.exists() && dossierNotion.isDirectory()) 
		{
			//récupérer tout les dossiers questions dans le dossier notionX 
			File[] dossierQuestions = dossierNotion.listFiles();

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
                                if (fichier.isFile() && fichier.getName().endsWith(".csv")) 
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
//				System.out.println((question.getLstRep() == null) ? question.afficherReponse() : "rep : " + question.getReponseAsso() );
//				System.out.println(question.afficherReponse());
			}
		}

		return null;
	}

	private Question creerQuestion(String qst)
	{
		//première ligne
		String type              = "";
		String question          = "";
		String explication       = "";
		String stringdifficulte  = "";
		int   point    = -1;
		Float time     = 0.00f;


		String[] lines = qst.split("\n");
		
		String[] premiereLigne = lines[0].split("\t");
		      
		type              = premiereLigne[1];
		question          = premiereLigne[2];
		explication       = premiereLigne[3];
		stringdifficulte  = premiereLigne[4]; 

		point   = Integer.parseInt(premiereLigne[5]        );
		time    = Float.parseFloat(premiereLigne[6].strip());

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
				
				int indiceTabAsso = -1;
				int indiceTabTest = -1;
				String etape = "";
				ArrayList<Reponse> lstReponsesAsso = new ArrayList<Reponse>();
				
				for(int i = 0; i<lines.length; i++)
				{
					String[] contenuLigne = lines[i].split("\t");
					if(lines[i].equals("FIN"))								
						break;
				
					if(lines[i].strip().equals("Reponse".strip()))
						etape = "reponse";

					else
					{
						if(lines[i].strip().equals("Association".strip()))
							etape = "Association";
						else
						{
							if(etape.equals("reponse"))
								lstReponsesAsso.add(new Reponse(contenuLigne[0]));
							else
							{
								if(etape.equals("Association"))
								{
									for (Reponse reponse : lstReponsesAsso) 
									{
										if (reponse.getReponse().strip().equals(contenuLigne[0].strip())) 
											indiceTabAsso = lstRep.indexOf(reponse);
									}

									for(int asso = 1; asso < contenuLigne.length; asso++)
									{
										for (Reponse reponse : lstReponsesAsso) 
										{
											if (reponse.getReponse().strip().equals(contenuLigne[asso].strip())) 
												lstReponsesAsso.get(indiceTabAsso).associerReponse(reponse);
										}
									}
								}								
							}
						}
					}
					lstRep = lstReponsesAsso;
				}
				break;
			default:
				break;
		}

		Difficulte difficulte = Difficulte.TF;
		switch (stringdifficulte.toLowerCase()) 
        {
            case "très facile": difficulte = Difficulte.TF;
                break;
                
            case "facile"     : difficulte = Difficulte.F;
                break;

            case "moyen"      : difficulte = Difficulte.M;
                break;

            case "difficile"  : difficulte = Difficulte.D;
                break;
        
            default:
                break;
        }

		Question qstRet = new Question(question, type, explication, difficulte, point, time, lstRep);
		
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