/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package IHM;

import Metier.*;
import java.util.ArrayList;
import javax.swing.JFrame;

public class FrameVisu extends JFrame
{
    private PanelVisu panel;
	
    public FrameVisu(ArrayList<Question> lstQuestions, boolean chrono)
	{
		this.setTitle    ("Visualisation");
		this.setSize     (900,450 );
		this.setLocation ( 350,200         );
		this.setResizable(false      ); 

        this.panel = new PanelVisu(lstQuestions, chrono);

		this.add(panel);

		this.setVisible(true);
	}
}