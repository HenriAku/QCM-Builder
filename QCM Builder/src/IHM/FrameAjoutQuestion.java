package IHM;

import Controlleur.Controlleur;

import javax.swing.JFrame;

public class FrameAjoutQuestion extends JFrame
{
	private Controlleur ctrl;

	private PanelAjoutQuestion panelAjoutQuestion;

	public FrameAjoutQuestion(Controlleur ctrl)
	{
		this.ctrl = ctrl;

		this.setTitle   ("Création question");
		this.setSize    (920,550);
		this.setLocation( 350,200);
		this.setResizable(false);

		this.panelAjoutQuestion = new PanelAjoutQuestion(this.ctrl);
		this.add(this.panelAjoutQuestion);

		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

}
