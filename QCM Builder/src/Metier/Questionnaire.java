/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Théo Wychowski
 * @date 09/12/2024
 */

package Metier;

import java.util.*;
import java.io.*;

public class Questionnaire 
{
    private int nbQuestion;

    //0 = très facile; 1 = facile; 2 = moyen; 3 = difficile
    private int[] nbQuesParDificulté;
    
    private ArrayList<Chapitre> lstChapitres;
    private ArrayList<Ressource> lstRessources;
    private ArrayList<Question> lstQuestions;
    
    public Questionnaire(ArrayList<Chapitre> lstC)
    {
        this.lstChapitres = lstC;
    }

    public ArrayList<Question> genererQuestionnaire(int nbQuestion)
    {

        String question = "";

        for(int i = 0; i<nbQuestion; i++)
        {
            
        }


        return null;
    }


}


