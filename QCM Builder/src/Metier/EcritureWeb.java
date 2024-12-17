//Cette classe s'occupe de récuperer les donnees d'une evaluation
//pour creer le site html/css/js de celle-ci

package Metier;

import java.io.*;
import java.util.ArrayList;

public class EcritureWeb 
{
	//attributs evaluation
	private Evaluation evaluation;
	private String nomFichier;
	private String emplacement;

	//constructeur
	public EcritureWeb(Evaluation evaluation, String nomFichier, String emplacement)
	{
		this.evaluation = evaluation;
		this.nomFichier = nomFichier;
		this.emplacement = "QCM Builder"+ File.separator + "Evaluation";

		this.ecrireFichier();
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

			for (Question question : lstQuestion) {
				if (question instanceof Association) {
					writer.write(genereInfoAssociation((Association) question));
				} else if (question instanceof QCM) {
					writer.write(genereInfoQCM((QCM) question));
				} else {
					writer.write(genereInfoEnlevement((Enlevement) question));
				}
			}

			writer.write("""
				];

				let currentQuestionIndex = 0;
				let score = 0;
				let selectedAnswer = null;
				let answersValidated = Array(quiz.length).fill(false);

				function displayQuestion() {
					if (currentQuestionIndex >= quiz.length) {
						showResults();
						return;
					}

					const questionObj = quiz[currentQuestionIndex];
					document.getElementById('question-number').textContent = `Question ${currentQuestionIndex + 1} / ${quiz.length}`;
					document.getElementById('question').textContent = questionObj.question;

					const answersContainer = document.getElementById('answers');
					answersContainer.innerHTML = "";

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
				}

				function selectAnswer(index) {
					selectedAnswer = index;
					const answers = document.querySelectorAll("#answers button");
					answers.forEach((btn, idx) => {
						btn.style.backgroundColor = idx === index ? "lightblue" : "";
					});
				}

				function validateAnswer() {
					if (selectedAnswer === null) {
						document.getElementById('feedback').textContent = "Veuillez sélectionner une réponse avant de valider.";
						return;
					}

					const questionObj = quiz[currentQuestionIndex];
					const correct = questionObj.correct.includes(selectedAnswer);

					if (correct) {
						score++;
						document.getElementById('feedback').textContent = "Bonne réponse !";
					} else {
						document.getElementById('feedback').textContent = "Mauvaise réponse !";
					}

					highlightAnswers(correct);
					disableAnswerButtons();
					answersValidated[currentQuestionIndex] = true;
					updateValidateButton();
					updateNavigationButtons();
				}

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

				function disableAnswerButtons() {
					const answers = document.querySelectorAll("#answers button");
					answers.forEach(btn => btn.disabled = true);
				}

				function updateValidateButton() {
					const validateButton = document.getElementById('validate');
					validateButton.disabled = answersValidated[currentQuestionIndex];
				}

				function nextQuestion() {
					if (currentQuestionIndex < quiz.length - 1) {
						currentQuestionIndex++;
						displayQuestion();
					} else if (currentQuestionIndex === quiz.length - 1) {
						showResults();
					}
				}

				function prevQuestion() {
					if (currentQuestionIndex > 0) {
						currentQuestionIndex--;
						displayQuestion();
					}
				}

				function showResults() {
					document.body.innerHTML = `
						<div class="centered">
							<h1>Résultats</h1>
							<p>Votre score est de ${score} / ${quiz.length}</p>
						</div>
					`;
				}

				function updateNavigationButtons() {
					document.getElementById('prev').disabled = currentQuestionIndex === 0;
					const nextButton = document.getElementById('next');
					nextButton.textContent = currentQuestionIndex === quiz.length - 1 ? "Résultats" : "Suivant";
				}

				window.onload = () => {
					displayQuestion();
					document.getElementById('next').addEventListener('click', nextQuestion);
					document.getElementById('prev').addEventListener('click', prevQuestion);
					document.getElementById('validate').addEventListener('click', validateAnswer);
				};
				</script>
				<style>
					.centered {
						max-width: 600px;
						margin: 0 auto;
						text-align: center;
					}

					#answers {
						margin-bottom: 20px;
					}

					#answers button {
						display: block;
						margin: 5px auto;
						padding: 10px;
						width: 100%;
						max-width: 400px;
					}

					button {
						padding: 10px;
						font-size: 16px;
						cursor: pointer;
					}

					#prev, #validate, #next {
						background-color: #007BFF;
						color: white;
						border: none;
						margin: 5px;
						padding: 10px 20px;
					}

					#prev:disabled, #next:disabled {
						background-color: #ccc;
						cursor: not-allowed;
					}
				</style>
				</head>
				<body>
					<div class="centered">
						<p id="question-number"></p>
						<p id="question"></p>
						<div id="answers"></div>
						<div>
							<button id="prev">Précédent</button>
							<button id="validate">Valider</button>
							<button id="next">Suivant</button>
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
		String sRet = "{\\n" +
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
		return "ouais";
	}


	/////////////
	// ACCUEIL //
	/////////////

	//page d'acceuil html
	public void acceuilHtml()
	{
		try (FileWriter writer = new FileWriter(this.emplacement + File.separator + "index.html")) 
		{
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
				"<p>QCM ressouce : " + this.evaluation.getRessource().getNom() + "</p>\n" +
				"<p>Notions présentes : " + this.evaluation.getNotions() + "</p>\n" +
				"<p>Il contient " + this.evaluation.getQuestions().size() + " questions.</p>\n" +
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
					margin: 0;
					padding: 0;
					background: linear-gradient(135deg, #89f7fe, #66a6ff);
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
					background-color: #ff6600; /* Couleur de fond orange */
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
