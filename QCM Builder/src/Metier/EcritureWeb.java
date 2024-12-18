//Cette classe s'occupe de récuperer les donnees d'une evaluation
//pour creer le site html/css/js de celle-ci

package Metier;

import java.io.*;
import java.util.ArrayList;

public class EcritureWeb 
{
	//attributs evaluation
	private Evaluation evaluation;
	private String emplacement;

	//constructeur
	public EcritureWeb(Evaluation evaluation, String nomDossier, String emplacement)
	{
		this.evaluation = evaluation;

		//this.emplacement = ".." + File.separator + "Evaluation" ;
		this.emplacement = emplacement + File.separator + nomDossier;
		this.creerDossier();

		this.ecrireFichier();
	}

	public boolean creerDossier()
	{
		File dossier = new File(this.emplacement);

		if (!dossier.exists()) 
			return dossier.mkdirs(); // mkdirs() crée le dossier et tous ses parents si nécessaires

		return false; // Le dossier existe déjà	
	}

	//methode qui appelle les methodes d'ecriture des fichiers html css js
	public void ecrireFichier()
	{
		//this.ecrireFichierHTML();
		this.acceuilHtml();
		this.testBien();
		this.ecrireFichierCSS();
	}

	//////////
	// HTML //
	//////////

	//methode qui ecrit le code html de l'evaluation
	/*public void ecrireFichierHTML()
	{
		
		ArrayList<Question> lst = this.evaluation.getQuestions();


		for (Question question : lst) 
		{
			if (question instanceof Association) 
			{
				questionAssoHtml((Association) question);
			} else if (question instanceof QCM) 
			{
				questionQCMHtml((QCM) question);
			} else if (question instanceof Enlevement) 
			{
				questionEnlevementHtml((Enlevement) question);
			}
		}
	}*/

	public void testBien() {
		try (FileWriter writer = new FileWriter(this.emplacement + File.separator + "questions.html")) {
			writer.write("""
				<!DOCTYPE html>
				<html lang="fr">
				<head>
					<meta charset="UTF-8">
					<meta name="viewport" content="width=device-width, initial-scale=1.0">
					<title>Questionnaire</title>
					<link rel="stylesheet" href="style.css">
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
					writer.write(genereInfoEnlevement((Enlevement) question));
				}
			}
	
			writer.write("];\n");
			writer.write("const hasChrono = " + this.evaluation.getChrono() + ";\n");
			writer.write("""
				let currentQuestionIndex = 0;
				let score = 0;
				let selectedAnswer = null;
				let answersValidated = Array(quiz.length).fill(false);
				let timer;
				let timeRemaining;
	
				//Lance le timer avec le temps correspondant
				function startTimer(duration) {
					const timerElement = document.getElementById('timer');
					timeRemaining = duration;
					timerElement.textContent = `Temps restant : ${timeRemaining}s`;
					timer = setInterval(() => {
						timeRemaining--;
						timerElement.textContent = `Temps restant : ${timeRemaining}s`;
						if (timeRemaining <= 0) {
							clearInterval(timer);
							handleTimeout();
						}
					}, 1000);
				}
	
				//Si timer est faux rien sinon jsp
				function stopTimer() {
					if (timer) {
						clearInterval(timer);
					}
				}
	
				//Temps
				function handleTimeout() {
					//Affiche un message, valide la question, met a jour les  btns, empeche d'appuyer en haut
					document.getElementById('feedback').textContent = "Temps écoulé ! Réponse incorrecte.";
					answersValidated[currentQuestionIndex] = true;
					updateValidateButton();
					updateNavigationButtons();
					disableAnswerButtons();
					highlightAnswers(false);
				}
	
				//Affiche les questions de type QCM
				function afficheQuestionQCM() {
					if (currentQuestionIndex >= quiz.length) {
						showResults();
						return;
					}
	
					const questionObj = quiz[currentQuestionIndex];
					document.getElementById('question-number').textContent = `Question ${currentQuestionIndex + 1} / ${quiz.length}`;
					document.getElementById('question').textContent = questionObj.question;
	
					const answersContainer = document.getElementById('answers');
					answersContainer.innerHTML = "";
	
					//Fait un bouton pour chaque reponses
					questionObj.answers.forEach((answer, index) => {
						const button = document.createElement('button');
						button.textContent = answer;
						button.onclick = () => selectAnswer(index);
						button.id = `answer-${index}`;
						answersContainer.appendChild(button);
					});
	
					document.getElementById('feedback').textContent = "";
					selectedAnswer = null;
	
					updateNavigationButtons();
					updateValidateButton();
	
					if (hasChrono) {
						stopTimer(); // Arrêter le timer précédent
						startTimer(questionObj.time || 30); // Démarrer un nouveau timer
					} else {
						document.getElementById('timer').textContent = "";
					}
				}

				//Affiche les questions de type QCM
				function afficheQuestionAssociati() {
					if (currentQuestionIndex >= quiz.length) {
						showResults();
						return;
					}
	
					const questionObj = quiz[currentQuestionIndex];
					document.getElementById('question-number').textContent = `Question ${currentQuestionIndex + 1} / ${quiz.length}`;
					document.getElementById('question').textContent = questionObj.question;
	
					const answersContainer = document.getElementById('answers');
					answersContainer.innerHTML = "";
	
					//Fait un bouton pour chaque reponses
					questionObj.answers.forEach((answer, index) => {
						const button = document.createElement('button');
						button.textContent = answer;
						button.onclick = () => selectAnswer(index);
						button.id = `answer-${index}`;
						answersContainer.appendChild(button);
					});
	
					document.getElementById('feedback').textContent = "";
					selectedAnswer = null;
	
					updateNavigationButtons();
					updateValidateButton();
	
					if (hasChrono) {
						stopTimer(); // Arrêter le timer précédent
						startTimer(questionObj.time || 30); // Démarrer un nouveau timer
					} else {
						document.getElementById('timer').textContent = "";
					}
				}

				//Affiche les questions de type QCM
				function afficheQuestionEnlevement() {
					if (currentQuestionIndex >= quiz.length) {
						showResults();
						return;
					}
	
					const questionObj = quiz[currentQuestionIndex];
					document.getElementById('question-number').textContent = `Question ${currentQuestionIndex + 1} / ${quiz.length}`;
					document.getElementById('question').textContent = questionObj.question;
	
					const answersContainer = document.getElementById('answers');
					answersContainer.innerHTML = "";
	
					//Fait un bouton pour chaque reponses
					questionObj.answers.forEach((answer, index) => {
						const button = document.createElement('button');
						button.textContent = answer;
						button.onclick = () => selectAnswer(index);
						button.id = `answer-${index}`;
						answersContainer.appendChild(button);
					});
	
					document.getElementById('feedback').textContent = "";
					selectedAnswer = null;
	
					updateNavigationButtons();
					updateValidateButton();
	
					if (hasChrono) {
						stopTimer(); // Arrêter le timer précédent
						startTimer(questionObj.time || 30); // Démarrer un nouveau timer
					} else {
						document.getElementById('timer').textContent = "";
					}
				}
	
				//Surligne le bouton sur lequel on a cliqué
				function selectAnswer(index) {
					selectedAnswer = index;
					const answers = document.querySelectorAll("#answers button");
					answers.forEach((btn, idx) => {
						btn.style.backgroundColor = idx === index ? "lightblue" : "";
					});
				}
	
				//Le bouton valider
				function validateAnswer() {
					//Si on appuie dessus sans avoir choisi, affiche qqchose
					if (selectedAnswer === null) {
						document.getElementById('feedback').textContent = "Veuillez sélectionner une réponse avant de valider.";
						return;
					}
	
					// Arrêter le chronomètre lors de la validation
					stopTimer(); 
	
					const questionObj = quiz[currentQuestionIndex];
					const correct = questionObj.correct.includes(selectedAnswer);
	
					//Si bonne réponse +1 sinon 0
					if (correct) {
						score++;
						document.getElementById('feedback').textContent = "Bonne réponse !";
					} else {
						document.getElementById('feedback').textContent = "Mauvaise réponse !";
					}
	
					//Met la couleur, Désactive les boutons du haut, met que la question a été validée
					highlightAnswers(correct);
					disableAnswerButtons();
					answersValidated[currentQuestionIndex] = true;
					//met a jour les boutons du bas
					updateValidateButton();
					updateNavigationButtons();
				}
	
				//Met en rouge ou vert si la réponse est bonne ou mauvaise
				function highlightAnswers(isCorrect) {
					const answers = document.querySelectorAll("#answers button");
					answers.forEach((btn, idx) => {
						if (quiz[currentQuestionIndex].correct.includes(idx)) {
							btn.style.backgroundColor = "lightgreen";
						} else if (idx === selectedAnswer && !isCorrect) {
							btn.style.backgroundColor = "lightcoral";
						}
					});
				}
	
				// désactive les boutons quand jsp
				function disableAnswerButtons() {
					const answers = document.querySelectorAll("#answers button");
					answers.forEach(btn => btn.disabled = true);
				}
	
				//Desactive valider si on a deja repondu
				function updateValidateButton() {
					const validateButton = document.getElementById('validate');
					validateButton.disabled = answersValidated[currentQuestionIndex];
				}
	
				//Va a la question suivante
				function nextQuestion() {
					if (answersValidated[currentQuestionIndex] || !hasChrono) {
						if (currentQuestionIndex < quiz.length - 1) {
							currentQuestionIndex++;
							afficheQuestionQCM();
						} else {
							showResults();
						}
					}
					
				}
	
				//Retourne a la question precedente
				function prevQuestion() {
					if (!hasChrono && currentQuestionIndex > 0) {
						currentQuestionIndex--;
						displayQuestion();
					}
				}
	
				// Affiche la partie résultats
				function showResults() {
					document.body.innerHTML = `
						<div class="centered">
							<h1>Résultats</h1>
							<p>Votre score est de ${score} / ${quiz.length}</p>
						</div>
					`;
				}
	
				//Met a jour les boutons precedent et suivant
				function updateNavigationButtons() {
					const prevButton = document.getElementById('prev');
					prevButton.disabled = hasChrono || currentQuestionIndex === 0;
					const nextButton = document.getElementById('next');
					nextButton.textContent = currentQuestionIndex === quiz.length - 1 ? "Résultats" : "Suivant";
				}
	
				//Ajoute les eventListener
				window.onload = () => {
					afficheQuestionQCM();
					document.getElementById('next').addEventListener('click', nextQuestion);
					document.getElementById('prev').addEventListener('click', prevQuestion);
					document.getElementById('validate').addEventListener('click', validateAnswer);
				};
				</script>

				</head>
				<body>
					<div class="centered">
						<p id="timer"></p>
						<p id="question-number"></p>
						<p id="question"></p>
						<div id="answers"></div>
						<div>
							<button class="buttonAction" id="prev">Précédent</button>
							<button class="buttonAction" id="validate">Valider</button>
							<button class="buttonAction" id="next">Suivant</button>
						</div>
						<p id="feedback"></p>
					</div>
				</body>
				</html>
			""");
		} catch (IOException e) {
			System.out.println(e);
		}
	}






	public String genereInfoEnlevement(Enlevement q)
	{
		// Écriture de la question dans le fichier HTML
		String sRet = "{\n" +
					  "question: \"" + q.getQuestion() + "\",\n"+
					  "answers: [";
		
		// Écriture des réponses possibles dans un tableau
		for (int i = 0; i < q.getLstRep().size(); i++) 
		{
			sRet += "\"" + q.getLstRep().get(i).getReponse() + "\"";
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
		sRet +="],\n"+
			   "time: " + q.getTemps() + "\n"+
			   "},\n";

		return sRet;
	}
	
	public String genereInfoQCM(QCM q)
	{
		//ecrit la question
		String sRet = "{\n" + 
		"question: \"" + q.getQuestion() + "\",\n"+
		"answers: [";

		//ecrit les réponses possibles dans un tableau
		for (int i = 0; i < q.getLstRep().size(); i++) 
		{
			sRet += "\"" + q.getLstRep().get(i).getReponse() + "\"";
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

	public String genereInfoAssociation(Association q)
	{
		String sRet = "{\n" + 
					  "question : \"" + q.getQuestion() + "\"\n" +
					  "pairs: [";

	
		// Ajout des paires (colonne gauche et leurs associations possibles)
		for (int i = 0; i < q.getLstRep().size(); i++) 
		{
			sRet += "{ left: \"" + q.getLstRep().get(i).getReponse() + "\","+
					"rightOptions: [";

			for (int j = 0; j < q.getLstRepAsso().size(); j++)
			{ // Associer avec les options de droite
				sRet +="\"" + q.getLstRepAsso().get(j).getReponse() + "\"";
				
				if (j < q.getLstRepAsso().size() - 1) 
					sRet +=", ";
			}
			sRet +="], "+
			        "correct: \"" + q.getLstRepAsso().get(i).getReponse() + "\" },";
		}

		sRet += "],\n" +
		"time: " + q.getTemps() + "\n" +
		"},\n";
	
		sRet = "]"; 
		return sRet;
	}


	/////////////
	// ACCUEIL //
	/////////////

	//page d'acceuil html
	public void acceuilHtml()
	{
		try (FileWriter writer = new FileWriter(this.emplacement + File.separator + "index.html")) 
		{
			StringBuilder notionsBuilder = new StringBuilder();

			for (Notion not : this.evaluation.getNotions()) 
				notionsBuilder.append(not.getNom()).append(", ");

			String chronometre = "";
			if (!evaluation.getChrono()) chronometre += "non ";

			writer.append(
				"<!DOCTYPE html>\n" +
				"<html lang=\"fr\">\n" +
				"<head>\n" +
				"<meta charset=\"UTF-8\">\n" +
				"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
				"<title>Acceuil</title>\n" +
				"<link rel=\"stylesheet\" href=\"style.css\">\n" +
				"</head>\n" +
				"<body>\n" +
				"<div class=\"centered\">\n" +
				"<h1>QCM Builder</h1>\n" +
				"<p>Evaluation " + chronometre + "chronométrée.</p>\n" +
				"<p>Ressouce : " + this.evaluation.getRessource().getNom() + "</p>\n" +
				"<p>Notions présentes : " + notionsBuilder.toString() + "</p>\n" +
				"<p>Il contient " + this.evaluation.getQuestions().size() + " questions.</p>\n" +
				"<p>Dont : questions très faciles, questions faciles, questions moyennes, questions difficiles.</p>\n" +
				"<p>La durée totale de l'évaluation est de :  secondes.</p>\n" +
				"<a href=\"questions.html\">Vous aller passer un test.<br>\n" +
				"Appuyez ici pour le commencer</a>\n" +
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
	// CSS  //
	//////////

	//methode qui ecrit le code css de l'evaluation
	public void ecrireFichierCSS()
	{
		try (FileWriter writer = new FileWriter(this.emplacement + File.separator + "style.css")) {
			writer.append("""
				body {
					background-image: url("../background.png");
					margin: 0;
					padding: 0;
					background: White;
					display: flex;
					justify-content: center;
					align-items: center;
					height: 100vh;
					font-family: 'Roboto', Arial, sans-serif;
					color: #ffffff;
				}

				.centered {
					text-align: center;
					font-size: 32px;
					font-weight: 700;
					text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.5);
					padding: 20px;
					border: 3px solid rgba(255, 255, 255, 0.8);
					border-radius: 10px;
					background-color: rgba(0, 0, 0, 0.3);
				}

				a {
					text-decoration: none;
					color: #ffffff;
					transition: color 0.3s ease;
				}

				a:hover {
					color: #ffdd57;
				}

				/* Style général pour les boutons */
				button {
					background-color: rgb(58, 138, 249) ; /* Couleur de fond orange */
					color: white;
					font-size: 18px;
					font-weight: bold;
					padding: 15px 30px;
					border: none;
					border-radius: 8px;
					cursor: pointer;
					transition: all 0.3s ease;
					margin-top: 10px; /* Ajoute un espacement entre les boutons */
					width: 100%; /* Les boutons prennent toute la largeur */
				}

				.buttonAction {
					background-color:rgb(233, 168, 76); /* Couleur de fond orange */
					color: white;
					font-size: 18px;
					font-weight: bold;
					padding: 15px 30px;
					border: none;
					border-radius: 8px;
					cursor: pointer;
					transition: all 0.3s ease;
					margin-top: 10px; /* Ajoute un espacement entre les boutons */
					width: 100%; /* Les boutons prennent toute la largeur */
				}

				/* Effet au survol du bouton */
				button:hover {
					background-color: #e65c00; /* Couleur plus foncée au survol */
					transform: scale(1.1); /* Agrandissement léger */
				}

				/* Style pour le conteneur des réponses */
				#answers {
					display: flex;
					flex-direction: column; /* Affichage vertical des boutons */
					align-items: center; /* Centrer les boutons horizontalement */
					gap: 10px; /* Espacement entre chaque bouton */
				}

				/* Style pour le texte centré */
				.centered {
					text-align: center;
					font-size: 32px;
					font-weight: 700;
					text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.5); /* Ombre portée */
					padding: 20px;
					border: 3px solid rgba(255, 255, 255, 0.8); /* Bordure blanche translucide */
					border-radius: 10px;
					background-color: rgba(0, 0, 0, 0.3); /* Fond translucide pour le texte */
				}

			""");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
