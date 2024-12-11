package testWeb;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.IOException;

public class TestPage {

    public static void creerDossier(String chemin) {
        File dossier = new File(chemin);
        if (!dossier.exists()) {
            if (dossier.mkdirs()) {
                System.out.println("Dossier créé : " + chemin);
            } else {
                System.err.println("Erreur lors de la création du dossier : " + chemin);
            }
        }
    }

    public static void main(String[] args) {
        String dossier = "./questionnaire";
        String cheminAccueil = dossier + "/accueil.html";
        String cheminCss = dossier + "/style.css";
        String cheminPageQuestion = dossier + "/questions.html";

        ArrayList<Question> lstQuestions = new ArrayList<>();
        lstQuestions.add(new Question("Quelle est la capitale de la France ?", 
                                      Arrays.asList("Paris", "Londres", "Berlin", "Madrid"), 0, 60));
        lstQuestions.add(new Question("Combien font 5 x 6 ?", 
                                      Arrays.asList("25", "30", "35", "40"), 1, 30));
        lstQuestions.add(new Question("Quel est le symbole chimique de l'eau ?", 
                                      Arrays.asList("H2O", "O2", "CO2", "H2"), 0, 90));
        lstQuestions.add(new Question("En quelle année a eu lieu la Révolution française ?", 
                                      Arrays.asList("1789", "1776", "1804", "1815"), 0, 15));

        try {
            creerDossier(dossier);
            new PageQuestion(cheminPageQuestion, lstQuestions);
            new CreerCss(cheminCss);
            new CreerHtml(cheminAccueil);
            System.out.println("Fichiers HTML et CSS ont été créés avec succès !");
        } catch (IOException e) {
            System.err.println("Erreur lors de la création des fichiers : " + e.getMessage());
        }
    }
}
