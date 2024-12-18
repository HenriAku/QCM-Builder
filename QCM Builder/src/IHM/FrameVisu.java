/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package IHM;

import javax.swing.JFrame;

import java.util.ArrayList;

import Metier.*;

public class FrameVisu extends JFrame
{

    public static void main(String[] args) 
    {
        ArrayList<Question> lstQ = new ArrayList<Question>();
        ArrayList<ReponseQcm> lstR = new ArrayList<ReponseQcm>();

        lstR.add(new ReponseQcm("res1", true));
        lstR.add(new ReponseQcm("res2", false));
        lstR.add(new ReponseQcm("res3", false));
        lstR.add(new ReponseQcm("res4", false));

        for (int i = 0; i <= 50; i++) {
            String question = "qst" + i;
            String explication = "exp" + i;
            Difficulte difficulte;
        
            // Cycle entre les difficultés (D, F, TF, M)
            switch (i % 4) {
                case 0:
                    difficulte = Difficulte.D;
                    break;
                case 1:
                    difficulte = Difficulte.F;
                    break;
                case 2:
                    difficulte = Difficulte.TF;
                    break;
                case 3:
                default:
                    difficulte = Difficulte.M;
                    break;
            }
        
            lstQ.add(new QCM(question, explication, difficulte, 5.0, 4.5f, lstR));
        }


        new FrameVisu(lstQ);    
    }

    private PanelVisu panel;
	
    public FrameVisu(ArrayList<Question> lstQuestions)
	{
		this.setTitle    ("Visualisation");
		this.setSize     (900,450      );
		this.setLocation ( 350,200              );
		this.setResizable(false           ); 


        this.panel = new PanelVisu(lstQuestions);

		this.add(panel);

		this.setVisible(true);
	}


}