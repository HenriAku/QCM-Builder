/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package IHM;

import Controlleur.Controlleur;
import javax.swing.JFrame;

public class FrameEvaluation extends JFrame
{
	private PanelEvaluation   qst ;
	private Controlleur       ctrl;

    public static void main(String[] args) 
    {
        new FrameEvaluation(new Controlleur());
    }

	public FrameEvaluation(Controlleur ctrl)
	{
		this.setTitle    ("Evaluation");
		this.setSize     (400, 600);
		this.setLocation ( 350,200   );
		this.setResizable(false);

		this.ctrl = ctrl;
		

		this.qst = new PanelEvaluation(this.ctrl);
		this.add(this.qst);

		this.setVisible(true);
	}
}