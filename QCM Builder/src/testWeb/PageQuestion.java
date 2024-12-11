package testWeb;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PageQuestion {
    public PageQuestion(String chemin, ArrayList<Question> lstQuestions) throws IOException {
        try (FileWriter writer = new FileWriter(chemin)) {
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

            for (Question q : lstQuestions) {
                writer.write("                            {\n");
                writer.write("                                question: \"" + q.getQuestion() + "\",\n");
                writer.write("                                answers: [");
                for (int i = 0; i < q.getAnswers().size(); i++) {
                    writer.write("\"" + q.getAnswers().get(i) + "\"");
                    if (i < q.getAnswers().size() - 1) {
                        writer.write(", ");
                    }
                }
                writer.write("],\n");
                writer.write("                                correct: " + q.getCorrect() + ",\n");
                writer.write("                                time: " + q.getTime() + "\n");
                writer.write("                            },\n");
            }

            writer.write("""
                        ];
                        
                        let currentQuestionIndex = 0;
                        let timer;
                        let timeLeft = 0;
                        let score = 0;
                        let answeredQuestions = {};

                        function updateQuestion() {
                            clearInterval(timer);

                            if (currentQuestionIndex < quiz.length) {
                                const questionObj = quiz[currentQuestionIndex];
                                
                                // Affiche le numéro de la question actuelle
                                document.getElementById('question-number').textContent = `Question ${currentQuestionIndex + 1} / ${quiz.length}`;
                                document.getElementById('question').textContent = questionObj.question;

                                const answersContainer = document.getElementById('answers');
                                answersContainer.innerHTML = "";

                                questionObj.answers.forEach((answer, index) => {
                                    const button = document.createElement('button');
                                    button.textContent = answer;
                                    button.onclick = () => handleAnswer(index);
                                    button.id = "answer-" + index;

                                    if (answeredQuestions[currentQuestionIndex]?.selected === index) {
                                        button.style.backgroundColor = answeredQuestions[currentQuestionIndex].correct ? "lightgreen" : "lightcoral";
                                        button.disabled = true;
                                    }

                                    answersContainer.appendChild(button);
                                });

                                document.getElementById('feedback').textContent = answeredQuestions[currentQuestionIndex]
                                    ? (answeredQuestions[currentQuestionIndex].correct ? "Bonne réponse !" : "Mauvaise réponse !")
                                    : "";

                                document.getElementById('validate').disabled = !!answeredQuestions[currentQuestionIndex];
                                document.getElementById('validate').style.backgroundColor = answeredQuestions[currentQuestionIndex] ? "gray" : "";

                                if (answeredQuestions[currentQuestionIndex]) {
                                    timeLeft = 0;
                                    document.getElementById('countdown').textContent = timeLeft;
                                } else {
                                    timeLeft = questionObj.time;
                                    resetTimer(timeLeft);
                                }
                            } else {
                                showResults();
                            }
                        }

                        function resetTimer(time) {
                            timeLeft = time;
                            document.getElementById('countdown').textContent = timeLeft;

                            timer = setInterval(() => {
                                timeLeft--;
                                document.getElementById('countdown').textContent = timeLeft;
                                if (timeLeft <= 0) {
                                    clearInterval(timer);
                                    validateAnswer(false);
                                }
                            }, 1000);
                        }

                        function handleAnswer(index) {
                            const answerButtons = document.querySelectorAll("#answers button");
                            answerButtons.forEach(button => {
                                button.style.backgroundColor = "";
                            });
                            document.getElementById('answer-' + index).style.backgroundColor = "lightblue";
                            answeredQuestions[currentQuestionIndex] = { selected: index, correct: null };
                        }

                        function validateAnswer() {
                            if (!answeredQuestions[currentQuestionIndex]) {
                                alert("Veuillez sélectionner une réponse.");
                                return;
                            }

                            const questionObj = quiz[currentQuestionIndex];
                            const selected = answeredQuestions[currentQuestionIndex].selected;
                            const isCorrect = selected === questionObj.correct;

                            answeredQuestions[currentQuestionIndex].correct = isCorrect;
                            if (isCorrect) score++;

                            document.getElementById('feedback').textContent = isCorrect ? "Bonne réponse !" : "Mauvaise réponse !";
                            document.getElementById('validate').disabled = true;
                            document.getElementById('validate').style.backgroundColor = "gray";
                            clearInterval(timer);
                            updateQuestion();
                        }

                        function nextQuestion() {
                            currentQuestionIndex++;
                            updateQuestion();
                        }

                        function prevQuestion() {
                            if (currentQuestionIndex > 0) {
                                currentQuestionIndex--;
                                updateQuestion();
                            }
                        }

                        function showResults() {
                            const totalQuestions = quiz.length;
                            const finalScore = (score / totalQuestions) * 20;

                            document.body.innerHTML = `
                                <div class="centered">
                                    <h2>Résultats du Quiz</h2>
                                    <p>Total de questions : ${totalQuestions}</p>
                                    <p>Bonnes réponses : ${score}</p>
                                    <p>Score : ${score} / ${totalQuestions} (${finalScore.toFixed(2)} / 20)</p>
                                </div>
                            `;
                        }

                        window.onload = updateQuestion;
                    </script>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f4f4f4;
                            text-align: center;
                        }
                        .centered {
                            margin-top: 50px;
                        }
                        button {
                            margin: 5px;
                            padding: 10px 20px;
                            font-size: 16px;
                            cursor: pointer;
                            border: none;
                            border-radius: 5px;
                        }
                        #validate {
                            background-color: #4CAF50;
                            color: white;
                        }
                        #validate:disabled {
                            background-color: gray;
                        }
                        button:hover {
                            opacity: 0.8;
                        }
                        #prev, #next {
                            background-color: #007bff;
                            color: white;
                        }
                        button:disabled {
                            cursor: not-allowed;
                            background-color: #d3d3d3;
                        }
                    </style>
                </head>
                <body>
                    <div class="centered">
                        <p id="question-number"></p>
                        <p id="question"></p>
                        <div id="answers"></div>
                        <p>Temps restant : <span id="countdown"></span> secondes</p>
                        <div>
                            <button id="prev" onclick="prevQuestion()">Précédent</button>
                            <button id="validate" onclick="validateAnswer()">Valider</button>
                            <button id="next" onclick="nextQuestion()">Suivant</button>
                        </div>
                        <p id="feedback"></p>
                    </div>
                </body>
                </html>
            """);
        }
    }
}
