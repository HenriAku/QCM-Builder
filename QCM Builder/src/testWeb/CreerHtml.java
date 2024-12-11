package testWeb;

import java.io.FileWriter;
import java.io.IOException;

public class CreerHtml
{

	public CreerHtml(String chemin) throws IOException {
		try (FileWriter writer = new FileWriter(chemin)) {
			writer.write("""
				<!DOCTYPE html>
				<html lang="fr">
				<head>
					<meta charset="UTF-8">
					<meta name="viewport" content="width=device-width, initial-scale=1.0">
					<title>Page avec Style</title>
					<link rel="stylesheet" href="style.css">
				</head>
				<body>
					<div class="centered">
						<a href="questions.html">Vous aller passer un test.<br>
											 Appuyez ici pour le commencer</a>
					</div>
				</body>
				</html>
			""");
		}
	}
	
}