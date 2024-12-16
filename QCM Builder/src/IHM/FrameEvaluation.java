/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package IHM;

import javax.swing.JFrame;

import Controlleur.Controlleur;

public class FrameEvaluation extends JFrame
{
	private PanelEvaluation   qst;
	private Controlleur    ctrl   ;

    public static void main(String[] args) 
    {
        new FrameEvaluation(new Controlleur());
    }

	public FrameEvaluation(Controlleur ctrl)
	{
		this.setTitle("Evaluation");
		this.setSize(400, 600);

		this.ctrl = ctrl;
		

		this.qst = new PanelEvaluation(this.ctrl, this);
		this.add(this.qst);

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


    
}