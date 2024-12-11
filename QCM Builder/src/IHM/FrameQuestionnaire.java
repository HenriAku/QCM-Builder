/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package IHM;

import javax.swing.JFrame;

import Controlleur.Controlleur;

public class FrameQuestionnaire extends JFrame
{
	private PanelQuestionnaire   qst;
	private Controlleur    ctrl   ;

    public static void main(String[] args) 
    {
        new FrameQuestionnaire(new Controlleur());
    }

	public FrameQuestionnaire(Controlleur ctrl)
	{
		this.setTitle("Questionnaire");
		this.setSize(1000, 1000);

		this.ctrl = ctrl;
		

		this.qst = new PanelQuestionnaire(this.ctrl, this);
		this.add(this.qst);

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


    
}