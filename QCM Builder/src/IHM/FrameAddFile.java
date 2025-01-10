/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package IHM;

import javax.swing.JFrame;

import Controlleur.Controlleur;
import Metier.Question;

public class FrameAddFile extends JFrame
{
	private PanelAddFile panel;

	public FrameAddFile(Controlleur ctrl, Question question, String res, String not, boolean modification)
	{
		this.setTitle    ("Ajout Fichier");
		this.setSize     (600,300 );
		this.setLocation ( 350,200         );
		this.setResizable(false      ); 

		panel = new PanelAddFile(this, ctrl, question, res, not, modification);

		this.add(panel);

		this.setVisible(true);
	}

	public String getPath() 
	{
		return this.panel.getPath();
	}

	public void fermerFenetre()
	{
		this.dispose();
	}

}