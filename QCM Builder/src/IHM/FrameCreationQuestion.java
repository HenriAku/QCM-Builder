/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package IHM;

import javax.swing.JFrame;
import Controlleur.Controlleur;
import IHM.FramePrincipal;


public class FrameCreationQuestion extends JFrame
{
	private FramePrincipal frame;
	private Controlleur    ctrl ;

	public FrameCreationQuestion(FramePrincipal frame, Controlleur ctrl)
	{
		this.setTitle    ("CrÃ©ation question");
		this.setSize     (920,350      );
		this.setLocation ( 350,200              );
		this.setResizable(false           ); 

		this.ctrl  = ctrl ;
		this.frame = frame;

		this.add(new PanelCreeQuestion(this.ctrl, this.frame));

		this.setVisible(true);
	}
}