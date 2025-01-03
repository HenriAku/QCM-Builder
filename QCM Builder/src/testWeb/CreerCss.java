package testWeb;

import java.io.FileWriter;
import java.io.IOException;

public class CreerCss {
	public CreerCss(String chemin) throws IOException {
		try (FileWriter writer = new FileWriter(chemin)) {
			writer.write("""
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
		}
	}
}
