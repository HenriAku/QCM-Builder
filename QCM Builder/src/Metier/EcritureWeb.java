/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
//Cette classe s'occupe de récuperer les donnees d'une evaluation
//pour creer le site html/css/js de celle-ci

package Metier;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class EcritureWeb 
{
	//attributs evaluation
	private Evaluation evaluation ;
	private String     emplacement;
	private static int numFlc     ;

	/**
	 * Constructeur de la classe EcritureWeb
	 * @param evaluation l'évaluation liée au questionnaire
	 * @param nomDossier le nom donné au questionnaire
	 * @param emplacement l'emplacement de la création du questionnaire
	 */
	public EcritureWeb(Evaluation evaluation, String nomDossier, String emplacement)
	{
		this.evaluation  = evaluation;
		this.emplacement = emplacement + File.separator + nomDossier;
		
		this.creerDossier();

		this.ecrireFichier();

		this.copierComplement();
	}

	/**
	 * Copie les compléments des questions de l'évaluation dans le dossier compléments du questionnaire.
	 */
	public void copierComplement()
	{
		File dossier = new File(this.emplacement + File.separator + "complements");
		Ecriture ecriture = new Ecriture("../ressources");

		if (!dossier.exists()) 
		{
			dossier.mkdirs(); 
		}

		for (Question qst : evaluation.getQuestions()) 
		{
			for (Notion n : evaluation.getRessource().getNotions()) 
			{
				if (ecriture.rechercherFichierQuestion(qst, evaluation.getRessource(), n) != "") 
				{
					File flcBase = new File(ecriture.rechercherFichierQuestion(qst, evaluation.getRessource(), n));
					try
					{
						File dosComp = new File(ecriture.getEmp());

						File flcArr = dosComp.listFiles()[0];
						
						File test = new File(flcBase.getParentFile() + File.separator + "complement");

						this.copierFichierDansDossier(test.listFiles()[0].getPath(), dossier.getPath(),  qst.getFileName());

					}
					catch(Exception e){e.getStackTrace();}
					
				}
			}
		}
	}

	/**
	 * Copie un fichier existant à un endroit donné, avec un nom spécifié
	 *
	 * @param sourcePath le chemin du fichier qui doit être copié
	 * @param destinationDir la destination de la copie
	 * @param nom le nom du dossier
	 */
	public void copierFichierDansDossier(String sourcePath, String destinationDir,String nom) 
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
			Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

			Ecriture ec = new Ecriture("");

			destinationFile.renameTo(new File(destinationFile.getParent() + File.separator + nom));

			System.out.println("Fichier copiée dans : " + destinationFile.getAbsolutePath());
		} 
		catch (IOException e) 
		{
			System.out.println("Erreur lors de la copie du fichier : " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Crée un dossier à l'emplacement de l'attribut emplacement d'une instance
	 *
	 * @return si le dossier à été créé
	 */
	public boolean creerDossier()
	{
		File dossier = new File(this.emplacement);

		if (!dossier.exists()) 
			return dossier.mkdirs(); // mkdirs() crée le dossier et tous ses parents si nécessaires

		return false; // Le dossier existe déjà	
	}

	/**
	 * Méthode qui permet de crée le questionnaire à l'aide des méthodes de copie et de création.
	 */
	public void ecrireFichier()
	{

		File dossier = new File(this.emplacement + File.separator + "questionnaire");
		if (!dossier.exists()) 
		{
			dossier.mkdirs(); 
		}
		
		//this.ecrireFichierHTML();
		this.acceuilHtml();

		//ecrireFichierJS;
		try{
			this.copierFichierJs(this.emplacement+File.separator+"questionnaire");
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		//ecrireFichierCSS;
		this.questionsHtml();
		try{
			this.copierFichierCSS(this.emplacement+File.separator+"questionnaire");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	////////////////
	// HTML et JS //
	////////////////

	/**
	 * Crée le fichier question.html qui contient des informations pour le javascript,
	 * ainsi que la structure des pages de questionnaire
	 */
	public void questionsHtml() {
		try (FileWriter writer = new FileWriter(this.emplacement + File.separator + "questionnaire" + File.separator + "questions.html")) {
			writer.write("""
				<!DOCTYPE html>
				<html lang="fr">
				<head>
					<meta charset="UTF-8">
					<meta name="viewport" content="width=device-width, initial-scale=1.0">
					<title>Evaluation</title>
					<link rel="stylesheet" href="style.css">
					<script src="script.js" defer></script>
					<script>
						const quiz = [
			""");
	
			ArrayList<Question> lstQuestion = this.evaluation.getQuestions();
	
			for (Question question : lstQuestion) 
			{
				if (question instanceof Association) 
				{
					writer.write(genereInfoAssociation((Association) question));
				} 
				else if (question instanceof QCM) 
				{
					writer.write(genereInfoQCM((QCM) question));
				} 
				else 
				{
					writer.write(genereInfoElimination((Elimination) question));
				}
			}
	
			writer.write("];\nconst info = [");
			writer.write(genereInfo());
			writer.write("}];\nconst hasChrono = " + this.evaluation.getChrono() + ";\n");
			writer.write("""
				let currentQuestionIndex = 0;
				let score = 0;
				let selectedAnswer = null;
				let answersValidated = Array(quiz.length).fill(false);
				let timer;
				let timeRemaining;
				let type;
				let niveau = 0;
				const ligne = info[0];
				</script>
				</head>
				<body>
					<div class="centered">
						<div class="haut">
							<p id="ressource"></p>
							<p id="notion"></p>
							<p id="difficulte"></p>
						</div>
						<p id="timer"></p>
						<p id="question-number"></p>
						<p id="question"></p>
						<div id="complement">Complément</div>
						<p id="response-label" style="font-weight: bold; margin-bottom: 10px;"></p>
						<div id="answers"></div>
						<div id="supprimer"></div>
							<div>
							<button class="buttonAction" id="prev">Précédent</button>
							<button class="buttonAction" id="feedback">Feedback</button>
							<button class="buttonAction" id="validate">Valider</button>
							<button class="buttonAction" id="next">Suivant</button>
						</div>
						<p id="retourQuestion"></p>

						<div id="customAlert" class="modal">
							<div class="modal-content">
								<p id="alertMessage"></p>
								<button onclick="fermerFeedback()">Valider</button>
							</div>
						</div>

						<div id="confirmationAlert" style="display: none;">
							<p id="confirmationMessage">Êtes-vous sûr de passer la question sans avoir validé ?</p>
							<button id="confirmYes">Oui</button>
							<button id="confirmNo">Non</button>
						</div>

					</div>
				</body>
				</html>
			""");
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * Méthode qui recupere la notion d'une question
	 *
	 * @param q la question
	 * @return le nom de la notion en String
	 */
	public String recupNotion(Question q)
	{
		String sRet = "";
		for (Notion n : this.evaluation.getNotions()) 
		{
			if (n.getLstQuestions().contains(q)) 
				sRet = n.getNom();
		}
		return sRet;
	}

	/**
	 * Méthode qui génère les informations relatives à l'évaluation
	 *
	 * @return un String qui contient les informations générales de l'évaluation
	 */
	public String genereInfo()
	{
		String sRet = "{\n" +
					  "ressource: `" + evaluation.getRessource().getNom() + "`,\n"+
					  "diffQuestion: [" + this.evaluation.getNbQuestionParDifficulte(Difficulte.TF) 
					   + ", " + this.evaluation.getNbQuestionParDifficulte(Difficulte.F)
					   + ", " + this.evaluation.getNbQuestionParDifficulte(Difficulte.M)
					   + ", " + this.evaluation.getNbQuestionParDifficulte(Difficulte.D)
					   +"],"
					   + "\ntotalPoint: [" + evaluation.getTotalPoints() + "]\n";;
		return sRet;
	}

	/**
	 * Méthode qui génère les informations relatives à une question Elimination
	 *
	 * @param q une question de type Elimination
	 * @return un String qui contient les informations de la question Elimination
	 */
	public String genereInfoElimination(Elimination q)
	{
		// Écriture de la question dans le fichier HTML
		String sRet = "{\n" +
					  "points:" + q.getPoint() + ",\n"+
					  "explication: `" + q.getExplication() + "`,\n"+
					  "type: \"Elimination\",\n"+
					  "difficulte: \"" + q.getDifficulte().getDifficulte() + "\",\n"+
					  "notion: `" + recupNotion(q) + "`,\n"+
					  "question: `" + q.getQuestion() + "`,\n"+
					  "complement: \"" + q.getFileName()+"\",\n"+
					  "answers: [";
		
		// Écriture des réponses possibles dans un tableau
		for (int i = 0; i < q.getLstRep().size(); i++) 
		{
			sRet += "`" + q.getLstRep().get(i).getReponse() + "`";
			if (i < q.getLstRep().size() - 1) 
			{
				sRet +=", ";
			}
		}
		sRet +="],\n"+
			 "correct: [";

		// Indices des réponses correctes dans un tableau
		boolean first = true;
		for (int i = 0; i < q.getLstRep().size(); i++) 
		{
			if (q.getLstRep().get(i).getValeur()) // Vérifie si la réponse est correcte
			{
				if (!first) sRet +=", ";
				sRet += String.valueOf(i); // Ajoute l'indice de la bonne réponse
				first = false;
			}
		}

		sRet +="],\n";

		first = true;
		sRet += "lstEnlever: [";
		for (int i = 0; i < q.getLstRep().size(); i++) 
		{
			if (!first) sRet +=", ";
			sRet += q.getLstRep().get(i).getOrdreEnleve();
			first = false;
		}

		sRet +="],\n";

		first = true;
		sRet += "lstPoints: [";
		for (int i = 0; i < q.getLstRep().size(); i++) 
		{
			if (!first) sRet +=", ";
			sRet += q.getLstRep().get(i).getNbPointEleve();
			first = false;
		}

		sRet +="],\n"+
			   "time: " + q.getTemps() + "\n"+
			   "},\n";

		return sRet;
	}
	
	/**
	 * Méthode qui génère les informations relatives à une question QCM
	 *
	 * @param q une question de type QCM
	 * @return un String qui contient les informations de la question QCM
	 */
	public String genereInfoQCM(QCM q)
	{
		//ecrit la question
		String sRet = "{\n" + 
					  "points:" + q.getPoint() + ",\n"+
					  "explication: `" + q.getExplication() + "`,\n"+
					  "type: \"QCM\",\n"+
					  "difficulte: \"" + q.getDifficulte().getDifficulte() + "\",\n"+
					  "notion: `" + recupNotion(q) + "`,\n"+
					  "question: `" + q.getQuestion() + "`,\n"+
					  "complement: \"" + q.getFileName()+"\",\n"+
					  "answers: [";

		//ecrit les réponses possibles dans un tableau
		for (int i = 0; i < q.getLstRep().size(); i++) 
		{
			sRet += "`" + q.getLstRep().get(i).getReponse() + "`";
			if (i < q.getLstRep().size() - 1) 
			{
				sRet +=", ";
			}
		}

		// Indices des réponses correctes dans un tableau
		sRet += "],\n" +
				"correct: [";
		
		boolean first = true;
		for (int i = 0; i < q.getLstRep().size(); i++) 
		{
			if (q.getLstRep().get(i).getValeur()) // Vérifie si la réponse est correcte
			{
				if (!first) sRet += ", ";
				sRet += String.valueOf(i); // Ajoute l'indice de la bonne réponse
				first = false;
			}
		}
		
		sRet += "],\n" +
				"time: " + q.getTemps() + "\n" +
				"},\n";
		
		return sRet;
	}

	/**
	 * Méthode qui génère les informations relatives à une question Association
	 *
	 * @param q une question de type Association
	 * @return un String qui contient les informations de la question Association
	 */
	public String genereInfoAssociation(Association q)
	{
		
		String sRet = "{\n" +
					  "points:" + q.getPoint() + ",\n"+
					  "explication: `" + q.getExplication() + "`,\n"+
					  "type: \"Association\",\n"+
					  "difficulte: \"" + q.getDifficulte().getDifficulte() + "\",\n"+
					  "notion: `" + recupNotion(q) + "`,\n"+
					  "question: `" + q.getQuestion() + "`,\n"+
					  "complement: \"" + q.getFileName()+"\",\n"+
					  "pairs: [";
	
		// Ajout des paires (colonne gauche et leurs associations possibles)
		for (int i = 0; i < q.getLstRep().size(); i++) 
		{
			sRet += "{ left: `" + q.getLstRep().get(i).getReponse() + "`,"+
					"rightOptions: [";

			for (int j = 0; j < q.getLstRepAsso().size(); j++)
			{ // Associer avec les options de droite
				sRet +="`" + q.getLstRep().get(j).getAssocie() + "`";
				
				if (j < q.getLstRepAsso().size() - 1) 
					sRet +=", ";
			}
			sRet +="], "+
			        "correct: `" + q.getLstRep().get(i).getAssocie() + "`},\n";

		}

		sRet += "],\n" +
		"time: " + q.getTemps() + "\n" +
		"},\n";
		
		return sRet;
	}


	/////////////
	// ACCUEIL //
	/////////////

	/**
	 * Crée la page index.html à partir de de l'emplacement de l'instance
	 */
	public void acceuilHtml()
	{


		try (FileWriter writer = new FileWriter(this.emplacement + File.separator + "index.html")) 
		{
			File chemin = new File (this.emplacement);
			String nomQcm = chemin.getName();

			StringBuilder notionsBuilder = new StringBuilder();

			List<Notion> notions = this.evaluation.getNotions();
			for (int i = 0; i < notions.size(); i++) {
				notionsBuilder.append(notions.get(i).getNom());
				if (i < notions.size() - 1) {
					notionsBuilder.append(", ");
				}
			}

			String chronometre = "";
			if (!evaluation.getChrono()) chronometre += "non ";

			writer.append(
				"<!DOCTYPE html>\n" +
				"<html lang=\"fr\">\n" +
				"<head>\n" +
				"<meta charset=\"UTF-8\">\n" +
				"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
				"<title>Acceuil</title>\n" +
				"<link rel=\"stylesheet\" href=\"questionnaire" + File.separator + "style.css\">\n" +
				"</head>\n" +
				"<body>\n" +
				"<div class=\"centered\">\n" +
				"<h1>"+ nomQcm +"</h1>\n" +
				"<p>Evaluation " + chronometre + "chronométrée.</p>\n" +
				"<p>Ressouce : " + this.evaluation.getRessource().getNom() + "</p>\n" +
				"<p>Notions présentes : " + notionsBuilder.toString() + "</p>\n" +
				"<p>Contient " + this.evaluation.getQuestions().size() + " questions.</p>\n");
				if (this.evaluation.getNbQuestionParDifficulte(Difficulte.TF)>0)
				{
				writer.append("<p class=\"difficulte tres-facile\">"+ 	
				this.evaluation.getNbQuestionParDifficulte(Difficulte.TF)+" TRES FACILE</p>\n");
				}
				if (this.evaluation.getNbQuestionParDifficulte(Difficulte.F)>0)
				{
					writer.append("<p class=\"difficulte facile\">"+
						this.evaluation.getNbQuestionParDifficulte(Difficulte.F)+ " FACILE</p>\n");
				}
				if (this.evaluation.getNbQuestionParDifficulte(Difficulte.M)>0)
				{
					writer.append("<p class=\"difficulte moyen\">"+
						this.evaluation.getNbQuestionParDifficulte(Difficulte.M)+ " MOYEN</p>");
				}
				if (this.evaluation.getNbQuestionParDifficulte(Difficulte.D)>0)
				{
					writer.append("<p class=\"difficulte difficile\">"+
						this.evaluation.getNbQuestionParDifficulte(Difficulte.D)+ " DIFFICILE</p>\n"); 
				}
				
				if (this.evaluation.getChrono())
				{
					writer.append("<p>La durée totale de l'évaluation est de :<br>\n");
				}
				else
				{
					writer.append("<p>La durée estimée de l'évaluation est de :<br>");
				}
				writer.append(this.evaluation.getMinutes() + " minutes et "+ this.evaluation.getSecondes() +" secondes.</p>\n");
				
				writer.append(
				"<a href=\"questionnaire" + File.separator + "questions.html\">Commencer l'évaluation</a>" +
				"</div>\n" +
				"</body>\n" +
				"</html>"
			);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	//////////
	// JS  //
	//////////

	/**
	 * Méthode qui copie le fichier JavaScript présent dans le dossier Web
	 *
	 * @param cheminDestination l'endroit ou il doit être créé
	 */
	public void copierFichierJs(String cheminDestination) throws IOException 
	{
        File fichierSource      = new File(".."+File.separator +"src"+File.separator + "Web" + File.separator+"script.js");
        File fichierDestination = new File(this.emplacement + File.separator + "questionnaire" + File.separator + "script.js");

        Files.copy(fichierSource.toPath(), fichierDestination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        
        System.out.println("Le fichier JS a été copié avec succès.");
    }

	//////////
	// CSS  //
	//////////

	/**
	 * Méthode qui copie le fichier CSS présent dans le dossier Web
	 *
	 * @param cheminDestination l'endroit ou il doit être créé
	 */
	public void copierFichierCSS(String cheminDestination) throws IOException 
	{
        File fichierSource      = new File(".."+File.separator +"src"+File.separator + "Web" + File.separator+"style.css");
        File fichierDestination = new File(this.emplacement + File.separator + "questionnaire" + File.separator + "style.css");

        Files.copy(fichierSource.toPath(), fichierDestination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        
        System.out.println("Le fichier CSS a été copié avec succès.");
    }
}
