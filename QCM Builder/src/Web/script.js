let answersContainer;
let nbPointEnleves  ;
let complementBtn   ;

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
	document.getElementById('retourQuestion').textContent = "Temps écoulé ! Réponse incorrecte.";
	afficherFeedback();
    document.getElementById('feedback').disabled = false;
	answersValidated[currentQuestionIndex] = true;
	updateValidateButton();
	updateNavigationButtons();
	disableAnswerButtons();
	highlightAnswers(false);
}

//difficulte
function remplirDifficulte(difficulte) {
    const difficulteElement = document.getElementById('difficulte');
    difficulteElement.textContent = difficulte.toUpperCase();

    // Supprimez toutes les classes de difficulté existantes
    difficulteElement.classList.remove('tres-facile','facile', 'moyen', 'difficile');

    // Ajoutez la classe appropriée
    switch (difficulte.toLowerCase()) {
        case 'tres facile':
            difficulteElement.classList.add('tres-facile');
            break;
        case 'facile':
            difficulteElement.classList.add('facile');
            break;
        case 'moyen':
            difficulteElement.classList.add('moyen');
            break;
        case 'difficile':
            difficulteElement.classList.add('difficile');
            break;
        default:
            // Aucune classe n'est ajoutée si la difficulté est inconnue
            break;
    }
}

//complement
// Fonction pour générer le chemin complet du fichier
function getComplementFilePath(fileName) {
    return `../complements/${fileName}`;
}

function initializeComplementButton() {
    complementBtn = document.getElementById('complement');
    complementBtn.style.display = "none";
    complementBtn.className = "complement-btn";

    complementBtn.onclick = () => {
        const filePath = complementBtn.dataset.filePath;
        if (filePath) {
            window.open(filePath, '_blank'); // Ouvre le fichier
        } else {
            console.error("Chemin du fichier complémentaire non défini.");
        }
    };
}

// Fonction pour mettre à jour le bouton
function updateComplementButton(fileName) {
    
    if (!complementBtn) {
        console.error("Bouton complémentaire non initialisé.");
        return;
    }

    if (fileName && fileName !== "null") {
        complementBtn.style.display = "block";
        complementBtn.dataset.filePath = getComplementFilePath(fileName); // Met à jour le chemin du fichier
    } else {
        complementBtn.style.display = "none"; // Masque le bouton si aucun fichier n'est fourni
    }
}

// Modification de la fonction afficheQuestionQCM pour gérer plusieurs réponses correctes
function afficheQuestionQCM() {
    selectedAnswers = []; // Stocke les réponses sélectionnées sous forme de tableau

    if (currentQuestionIndex >= quiz.length) {
        showResults();
        return;
    }

    if (hasChrono)
    {
        document.getElementById('feedback').disabled = true;
    }

    const questionObj = quiz[currentQuestionIndex];
    document.getElementById('question-number').textContent = `Question ${currentQuestionIndex + 1} / ${quiz.length}`;
    document.getElementById('question').textContent = questionObj.question;

    document.getElementById('ressource').textContent = ligne.ressource;
    document.getElementById('notion').textContent = questionObj.notion;
    remplirDifficulte(questionObj.difficulte);

    const responseLabel = document.getElementById('response-label');
    if (questionObj.correct.length === 1) {
        responseLabel.textContent = "Une seule réponse possible";
    } else {
        responseLabel.textContent = "Plusieurs réponses possibles";
    }

    let answersContainer = document.getElementById('answers');
    answersContainer.innerHTML = "";

    // Création des boutons pour chaque réponse
    questionObj.answers.forEach((answer, index) => {
        const button = document.createElement('button');
        button.textContent = answer;
        button.onclick = () => toggleAnswer(index);
        button.id = `answer-${index}`;
        answersContainer.appendChild(button);
    });

    document.getElementById('retourQuestion').textContent = "";

    updateComplementButton(questionObj.complement);

    updateNavigationButtons();
    updateValidateButton();

    if (hasChrono) {
        stopTimer(); // Arrête le timer précédent
        startTimer(questionObj.time || 30); // Démarre un nouveau timer
    } else {
        document.getElementById('timer').textContent = "";
    }
}


function afficheQuestionAssociation() {
    selectedAnswers = {}; // Stocke les réponses sélectionnées sous forme de clé-valeur {left: selectedOption}

    if (currentQuestionIndex >= quiz.length) {
        showResults();
        return;
    }

    if (hasChrono) {
        document.getElementById('feedback').disabled = true;
    }

    const responseLabel = document.getElementById('response-label');
    responseLabel.textContent = "";

    const questionObj = quiz[currentQuestionIndex];
    document.getElementById('question-number').textContent = `Question ${currentQuestionIndex + 1} / ${quiz.length}`;
    document.getElementById('question').textContent = questionObj.question;

    document.getElementById('ressource').textContent = ligne.ressource;
    document.getElementById('notion').textContent = questionObj.notion;
    remplirDifficulte(questionObj.difficulte);

    const answersContainer = document.getElementById('answers');
    answersContainer.innerHTML = ""; // Nettoie le conteneur avant de remplir

    // Crée un élément pour chaque paire
    questionObj.pairs.forEach((pair, index) => {
        const row = document.createElement('div');
        row.classList.add('pair-row');

        // Ajoute le texte de gauche
        const paragraph = document.createElement('p');
        paragraph.textContent = pair.left;
        paragraph.classList.add('pair-left');
        row.appendChild(paragraph);

        // Mélange les options de droite
        const shuffledOptions = shuffleArray([...pair.rightOptions]);

        // Ajoute une liste déroulante pour les options de droite
        const select = document.createElement('select');
        select.id = `pair-${index}`;
        select.onchange = () => selectOption(pair.left, select.value);

        // Option par défaut
        const defaultOption = document.createElement('option');
        defaultOption.textContent = "--Choisir--";
        defaultOption.value = "";
        select.appendChild(defaultOption);

        // Ajout des options mélangées
        shuffledOptions.forEach(option => {
            const optionElement = document.createElement('option');
            optionElement.textContent = option;
            optionElement.value = option;
            select.appendChild(optionElement);
        });

        select.classList.add('pair-select');
        row.appendChild(select);

        answersContainer.appendChild(row);
    });

    document.getElementById('retourQuestion').textContent = "";

    updateComplementButton(questionObj.complement);

    updateNavigationButtons();
    updateValidateButton();

    if (hasChrono) {
        stopTimer(); // Arrête le timer précédent
        startTimer(questionObj.time || 30); // Démarre un nouveau timer
    } else {
        document.getElementById('timer').textContent = "";
    }
}

function shuffleArray(array) {
    for (let i = array.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]];
    }
    return array;
}



//Affiche les questions de type Elimination
function afficheQuestionElimination() {
	selectedAnswer = null;
    nbPointEnleves = 0;

	if (currentQuestionIndex >= quiz.length) {
		showResults();
		return;
	}

	if (hasChrono)
	{
		document.getElementById('feedback').disabled = true;
	}

    const responseLabel = document.getElementById('response-label');
    responseLabel.textContent = "";

	const questionObj = quiz[currentQuestionIndex];
	document.getElementById('question-number').textContent = `Question ${currentQuestionIndex + 1} / ${quiz.length}`;
	document.getElementById('question').textContent = questionObj.question;

	document.getElementById('ressource').textContent = ligne.ressource;
	document.getElementById('notion').textContent = questionObj.notion;
	remplirDifficulte(questionObj.difficulte);

	answersContainer = document.getElementById('answers');
	answersContainer.innerHTML = "";

	// Ajoute les boutons des réponses
	questionObj.answers.forEach((answer, index) => {
		const button = document.createElement('button');
		button.textContent = answer;
		button.onclick = () => selectAnswer(index);
		button.id = `answer-${index}`;
		answersContainer.appendChild(button);
	});

    document.getElementById('retourQuestion').textContent = "";

    updateComplementButton(questionObj.complement);

	let button = document.createElement('button');
	button.textContent = "Supprimer une réponse";
	button.onclick = () => supprimerRep();
	button.id = `supprimer`;
	answersContainer.appendChild(button);

	updateNavigationButtons();
	updateValidateButton();

	if (hasChrono) {
		stopTimer();
		startTimer(questionObj.time || 30);
	} else {
		document.getElementById('timer').textContent = "";
	}
}

// Gère la sélection d'une option dans une liste déroulante
function selectOption(left, selectedOption) {
    if (selectedOption) {
        selectedAnswers[left] = selectedOption;
    } else {
        delete selectedAnswers[left]; // Supprime l'entrée si aucun choix
    }
}



// Met à jour les couleurs pour les réponses de type Association
function highlightAnswersAssociation(questionObj) {
    questionObj.pairs.forEach((pair, index) => {
        const select = document.getElementById(`pair-${index}`);
        if (selectedAnswers[pair.left] === pair.correct) {
            select.style.backgroundColor = "lightgreen"; // Bonne réponse
        } else {
            select.style.backgroundColor = "lightcoral"; // Mauvaise réponse
        }
    });
}

// Désactive toutes les listes déroulantes après validation
function disableAnswerInputs() {
    const selects = document.querySelectorAll("#answers select");
    selects.forEach(select => select.disabled = true);
}


//Supprimer les réponses pour une question elimination si possible
function supprimerRep() {
	niveau++;
	let numRep = 0;
	let question = quiz[currentQuestionIndex];
	question.lstEnlever.forEach((i) => 
	{
		if (i === niveau) 
		{	
			let btn = answersContainer.children[numRep];
			
			const questionObj = quiz[currentQuestionIndex];
            nbPointEnleves += questionObj.lstPoints[numRep];

			btn.disabled = true; // Désactivation du bouton
			btn.id = "btnGrise"; // Changement de l'ID du bouton
		}
		numRep +=1;
	
	});

    if (selectedAnswer !== null && selectedAnswer !== undefined) {
        selectedAnswer = null;

        const buttons = document.querySelectorAll("#answers button");
        buttons.forEach(btn => btn.style.backgroundColor = "");
    }
}
//fonction pour gérer la sélection ou la désélection de réponses
function toggleAnswer(index) {
    const questionObj = quiz[currentQuestionIndex];

    const button = document.getElementById(`answer-${index}`);

    //Si une seule réponse à la question
    if (questionObj.correct.length === 1) {
        selectedAnswers = [index];

        const buttons = document.querySelectorAll("#answers button");
        buttons.forEach(btn => btn.style.backgroundColor = "");

        button.style.backgroundColor = "lightblue";
    } else {
        // Si plusieurs réponses sont possibles
        if (selectedAnswers.includes(index)) {

            selectedAnswers = selectedAnswers.filter(i => i !== index);
            button.style.backgroundColor = "";
        } else {

            selectedAnswers.push(index);
            button.style.backgroundColor = "lightblue";
        }
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


// Modification de la fonction validateAnswer pour gérer plusieurs réponses correctes
function validateAnswer() {
    // Si aucune réponse n'est sélectionnée, affiche un message d'erreur
    if (selectedAnswers.length === 0) {
        document.getElementById('retourQuestion').textContent = "Veuillez sélectionner au moins une réponse avant de valider.";
        return;
    }

    document.getElementById('feedback').disabled = false;

    // Arrête le chronomètre lors de la validation
    stopTimer();

    const questionObj = quiz[currentQuestionIndex];
    const correctAnswers = questionObj.correct;

    // Vérifie si toutes les réponses sélectionnées sont correctes et si aucune réponse incorrecte n'est sélectionnée
    const isCorrect = selectedAnswers.every(idx => correctAnswers.includes(idx)) &&
                      correctAnswers.every(idx => selectedAnswers.includes(idx));

    //Si bonne réponse
	if (isCorrect && questionObj.type === "QCM") {
		score+=questionObj.points;
		document.getElementById('retourQuestion').textContent = "Bonne réponse !";
	} else {
		document.getElementById('retourQuestion').textContent = "Mauvaise réponse !";
	}
	
    afficherFeedback();

    // Met en surbrillance les réponses correctes et incorrectes
    highlightAnswers(isCorrect);
    disableAnswerButtons();
    answersValidated[currentQuestionIndex] = true;
    updateValidateButton();
    updateNavigationButtons();
}

// Fonction de validation pour les questions de type Elimination
function validateAnswerElimination() {
    const questionObj = quiz[currentQuestionIndex];

    // Vérifier si `questionObj` et la propriété `correct` sont définis
    if (!questionObj || questionObj.correct === undefined) {
        console.error("La réponse correcte n'est pas définie pour cette question");
        document.getElementById('retourQuestion').textContent = "Erreur : La réponse correcte n'est pas définie.";
        return;
    }

    // Assurez-vous qu'une réponse est sélectionnée
    if (selectedAnswer === null) {
        document.getElementById('retourQuestion').textContent = "Veuillez sélectionner une réponse avant de valider.";
        return;
    }

    document.getElementById('feedback').disabled = false;

    // Logique de validation des réponses
    stopTimer();
    
    const correctAnswer = questionObj.correct[0]; // L'indice correct dans la question d'elimination
    const isCorrect = selectedAnswer === correctAnswer; // Vérifie si la réponse sélectionnée est correcte
    points = questionObj.points;
    // Mise à jour du score et du retour
    
    if (isCorrect && questionObj.type === "Elimination") {
        if (points + nbPointEnleves < 0) {
            points = 0
        }
        else {
            score+=points + nbPointEnleves;
            document.getElementById('retourQuestion').textContent = "Bonne réponse !";
        }
        
    } else {
        document.getElementById('retourQuestion').textContent = "Mauvaise réponse !";
    }

    afficherFeedback();

    // Désactiver les boutons après la validation
    disableAnswerButtons();
    answersValidated[currentQuestionIndex] = true; // Indiquer que la question est validée
    updateValidateButton(); // Mettre à jour le bouton "Valider"
    updateNavigationButtons(); // Mettre à jour les boutons de navigation

    // Surligner les réponses après la validation
    highlightAnswersElimination(isCorrect);
}


// Valide les réponses pour une question de type Association
function validateAnswerAssociation() {
    const questionObj = quiz[currentQuestionIndex];

    // Vérifie si toutes les réponses sont remplies
    if (Object.keys(selectedAnswers).length !== questionObj.pairs.length) {
        document.getElementById('retourQuestion').textContent = "Veuillez associer toutes les paires avant de valider.";
        return;
    }

    // Arrête le chronomètre
    stopTimer();

    let allCorrect = true; // Indique si toutes les réponses sont correctes

    // Vérifie chaque paire et met à jour l'état
    questionObj.pairs.forEach((pair, index) => {
        const selectedOption = selectedAnswers[pair.left];
        const selectElement = document.getElementById(`pair-${index}`);

        if (selectedOption === pair.correct) {
            // Réponse correcte
            selectElement.style.backgroundColor = "lightgreen";
        } else {
            // Réponse incorrecte
            selectElement.style.backgroundColor = "lightcoral";
            allCorrect = false; // Marque qu'au moins une réponse est incorrecte
        }
    });

    const retourElement = document.getElementById('retourQuestion');
    if (allCorrect && questionObj.type === "Association") {
        score += questionObj.points || 1; // Ajoute les points
        retourElement.textContent = "Bonne réponse !";
        
    } else {
        retourElement.textContent = "Certaines réponses sont incorrectes.\n Les bonnes réponses sont :\n";
        questionObj.pairs.forEach((pair) => {
            const correctAnswer = pair.correct || "Inconnue";
            retourElement.textContent += `${pair.left} -> ${correctAnswer}\n`;
        });
    }
    afficherFeedback();

    // Met à jour l'état des boutons
    answersValidated[currentQuestionIndex] = true;
    disableAnswerInputs();
    updateValidateButton();
    updateNavigationButtons();
}

// Fonction pour surligner les réponses après la validation d'une question d'elimination
function highlightAnswersElimination(isCorrect) {
    const buttons = document.querySelectorAll("#answers button");
    const questionObj = quiz[currentQuestionIndex]; // Récupère les données de la question actuelle
    const correctAnswerIndex = questionObj.correct[0]; // Indice de la bonne réponse

    buttons.forEach((button, index) => {
        if (index === selectedAnswer) {
            // Si la réponse sélectionnée est correcte ou incorrecte, change la couleur
            button.style.backgroundColor = isCorrect ? "lightgreen" : "lightcoral";
        }

        // Affiche la bonne réponse en vert, même si elle n'a pas été sélectionnée
        if (index === correctAnswerIndex) {
            button.style.backgroundColor = "lightgreen";
        }
    });
}

// Met à jour les couleurs pour plusieurs réponses
function highlightAnswers(isCorrect) {
    const answers = document.querySelectorAll("#answers button");
    answers.forEach((btn, idx) => {
        if (quiz[currentQuestionIndex].correct.includes(idx)) {
            btn.style.backgroundColor = "lightgreen"; // Bonne réponse
        } else if (selectedAnswers.includes(idx)) {
            btn.style.backgroundColor = "lightcoral"; // Mauvaise réponse sélectionnée
        }
    });
}

// désactive les boutons du haut
function disableAnswerButtons() {
	answers = document.querySelectorAll("#answers button");
	answers.forEach(btn => btn.disabled = true);
}

//Desactive valider si on a deja repondu
function updateValidateButton() {
	const boutonValider = document.getElementById('validate');
    const boutonSupprimer = document.getElementById('supprimer');
	boutonValider.disabled = answersValidated[currentQuestionIndex];
    boutonSupprimer.disabled = answersValidated[currentQuestionIndex];
    
}

function afficherFeedback() {
    let question = quiz[currentQuestionIndex];
	let explication = question.explication;
    let scoreQuestion = question.points;

    const alertBox = document.getElementById('customAlert');
    const alertMessage = document.getElementById('alertMessage');

    text = "Score de la question : " + scoreQuestion + "\nScore actuel : " + score ;
    if(explication != "") text += "\nFeedback : " + explication;

    alertMessage.textContent = text;
    alertBox.style.display = 'block';
}

function fermerFeedback() {
    const alertBox = document.getElementById('customAlert');
    alertBox.style.display = 'none';
}


function feedbackos() {
	let quest = quiz[currentQuestionIndex];
	let explication = quest.explication;
	alert("Score actuel : " + score + 
          "\nFeedback : " +explication  );
}

//Va a la question suivante
function verifQuestionSuivante() {

    if (!answersValidated[currentQuestionIndex]) {
        const confirmationAlert = document.getElementById('confirmationAlert');
        confirmationAlert.style.display = 'block';

        document.getElementById('confirmYes').onclick = () => {
            confirmationAlert.style.display = 'none';
            questionSuivante();
        };

        document.getElementById('confirmNo').onclick = () => {
            confirmationAlert.style.display = 'none';
        };

        return;
    }
    questionSuivante();
}

function questionSuivante() {
    if (currentQuestionIndex < quiz.length - 1) 
    {
        currentQuestionIndex++;
        let quest = quiz[currentQuestionIndex];
        let type = quest.type;

        if (type == "QCM") {
            afficheQuestionQCM();
        }
        if (type == "Elimination") {
            afficheQuestionElimination();
        }
        if (type == "Association") {
            afficheQuestionAssociation();
        }

        // Mettez à jour l'event listener du bouton de validation en fonction du type de question
        const validateButton = document.getElementById('validate');
        validateButton.removeEventListener('click', validateAnswer); // Enlevez l'ancien écouteur
        validateButton.removeEventListener('click', validateAnswerAssociation);
        validateButton.removeEventListener('click', validateAnswerElimination);

        // Ajoutez l'écouteur pour le bon type de validation
        if (type === "QCM") {
            validateButton.addEventListener('click', validateAnswer); // Validation pour QCM
        } else if (type === "Association") {
            validateButton.addEventListener('click', validateAnswerAssociation); // Validation pour Association
        } else if (type === "Elimination") {
            validateButton.addEventListener('click', validateAnswerElimination); // Validation pour Elimination
        }
    } else {
        showResults();
    }

}

//Retourne a la question precedente
function prevQuestion() {
	if (!hasChrono && currentQuestionIndex > 0) {
		currentQuestionIndex--;
		let quest = quiz[currentQuestionIndex];
		let type = quest.type;
		
		if (type == "QCM") {
			afficheQuestionQCM();
		}
		if (type == "Elimination") {
			afficheQuestionElimination();
		}
		if (type == "Association") {
			afficheQuestionAssociation();
		}

                // Mettez à jour l'event listener du bouton de validation en fonction du type de question
        const validateButton = document.getElementById('validate');
        validateButton.removeEventListener('click', validateAnswer); // Enlevez l'ancien écouteur
        validateButton.removeEventListener('click', validateAnswerAssociation);
        validateButton.removeEventListener('click', validateAnswerElimination);

        // Ajoutez l'écouteur pour le bon type de validation
        if (type === "QCM") {
            validateButton.addEventListener('click', validateAnswer); // Validation pour QCM
        } else if (type === "Association") {
            validateButton.addEventListener('click', validateAnswerAssociation); // Validation pour Association
        } else if (type === "Elimination") {
            validateButton.addEventListener('click', validateAnswerElimination); // Validation pour Elimination
        }
	}
}

// Affiche la partie résultats
function showResults() {
	let NbQuestTF = ligne.diffQuestion[0];
	let NbQuestF  = ligne.diffQuestion[1];
	let NbQuestM  = ligne.diffQuestion[2];
	let NbQuestD  = ligne.diffQuestion[3];
	let ressource = ligne.ressource;
	let totalP    = ligne.totalPoint[0];
	let notions	  = "";

    let uniqueNotions = [];

    quiz.forEach((element) => {
        if (!uniqueNotions.includes(element.notion)) {
            uniqueNotions.push(element.notion); // Ajouter seulement si non présent
        }
    });

    // Créer une chaîne de caractères avec les notions uniques
    notions = uniqueNotions.join(", ");

    let diffS = "";
    if (NbQuestTF > 0)
    {
        diffS += "<p class=\"difficulte tres-facile\">" + NbQuestTF + " TRES FACILE</p>\n";
    }
    if (NbQuestF>0)
    {
        diffS += ("<p class=\"difficulte facile\">"+NbQuestF + " FACILE</p>\n");
    }
    if (NbQuestM>0)
    {
        diffS +=("<p class=\"difficulte moyen\">"+ NbQuestM + " MOYEN</p>\n");
    }
    if (NbQuestD>0)
    {
        diffS +=("<p class=\"difficulte difficile\">"+ NbQuestD+ " DIFFICILE</p>\n"); 
    }
    
    let scoreEval = "";
    scoreEval += "<p>Votre score est de "+ score + "/" + totalP + "</p>";


	document.body.innerHTML = `
		<div class="centered">
			<h1>Ressource : ${ressource}</h1>
			<h2>Notion    : ${notions}</h2>
			<h2>Nombre de questions ${quiz.length}</h2>
            ${diffS}
            ${scoreEval}
            <a href="../index.html">Retourner à l'accueil</a>
		</div>
	`;
}


//Met a jour les boutons precedent et suivant
function updateNavigationButtons() {
    // Active ou désactive les boutons en fonction des indices
    const prevButton = document.getElementById('prev');
    const nextButton = document.getElementById('next');

    prevButton.disabled = hasChrono || currentQuestionIndex === 0;

    if (currentQuestionIndex === quiz.length - 1) {
        // Met à jour le texte pour indiquer les résultats
        nextButton.textContent = "Voir les résultats";
    } else {
        nextButton.textContent = "Suivant";
    }
}


function updateProgress(current, total) {
	const progressBar = document.querySelector('.progress-bar');
	const percentage = (current / total) * 100;
	progressBar.style.width = `${percentage}%`;
}

//Ajoute les eventListener
window.onload = () => {

    initializeComplementButton();

	const questionObj = quiz[currentQuestionIndex];
	let type = questionObj.type;

	if (type == "QCM") {
		afficheQuestionQCM();
	}
	if (type == "Elimination") {
		afficheQuestionElimination();
	}
	if (type == "Association") {
		afficheQuestionAssociation();
	}

	// Mettez à jour l'event listener du bouton de validation en fonction du type de question
	const validateButton = document.getElementById('validate');
	validateButton.removeEventListener('click', validateAnswer); // Enlevez l'ancien écouteur
    validateButton.removeEventListener('click', validateAnswerAssociation);
    validateButton.removeEventListener('click', validateAnswerElimination);

	// Ajoutez l'écouteur pour le bon type de validation
	if (type === "QCM") {
		validateButton.addEventListener('click', validateAnswer); // Validation pour QCM
	} else if (type === "Association") {
		validateButton.addEventListener('click', validateAnswerAssociation); // Validation pour Association
	} else if (type === "Elimination") {
		validateButton.addEventListener('click', validateAnswerElimination); // Validation pour Elimination
	}

	document.getElementById('next').addEventListener('click', verifQuestionSuivante);
	document.getElementById('prev').addEventListener('click', prevQuestion);
	document.getElementById('feedback').addEventListener('click', afficherFeedback);
};
