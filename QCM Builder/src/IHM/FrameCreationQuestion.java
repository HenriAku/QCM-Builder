/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package IHM;

import Controlleur.Controlleur;
import javax.swing.JFrame;


public class FrameCreationQuestion extends JFrame
{
	private FramePrincipal    frame;
	private Controlleur       ctrl ;
	private PanelCreeQuestion panelCreeQuestion;

	public FrameCreationQuestion(FramePrincipal frame, Controlleur ctrl, boolean estCreeDepuisRessource)
	{
		this.setTitle    ("Création question");
		this.setSize     (920,350     );
		this.setLocation ( 350,200             );
		this.setResizable(false          ); 

		this.ctrl  = ctrl ;
		this.frame = frame;
		
		this.panelCreeQuestion = new PanelCreeQuestion(this.ctrl, this.frame, this, estCreeDepuisRessource);
		this.add(this.panelCreeQuestion);

		this.setVisible(true);
	}

	public void setRessourceNotion(String ressource, String notion)
	{
		this.panelCreeQuestion.setRessourceNotion(ressource, notion);
	}
}