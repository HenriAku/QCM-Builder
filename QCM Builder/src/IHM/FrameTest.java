package IHM;

import java.util.ArrayList;

import javax.swing.JFrame;

import Metier.Ressource;

public class FrameTest extends JFrame
{
	private PanelRessource panel;

	public FrameTest()
	{
		this.setTitle("Test");
		this.setSize(1000, 1000);

		
		ArrayList<Ressource> lstRes = new ArrayList<Ressource>();
		lstRes.add(new Ressource("Math"));
		lstRes.add(new Ressource("Algo"));
		lstRes.add(new Ressource("Francais"));
		lstRes.add(new Ressource("Communication"));
		lstRes.add(new Ressource("Anglais Technique"));
		lstRes.add(new Ressource("Anglais Technique"));
		lstRes.add(new Ressource("Anglais Technique"));
		lstRes.add(new Ressource("Anglais Technique"));
		lstRes.add(new Ressource("Anglais Technique"));



		this.panel = new PanelRessource(lstRes);
		this.add(this.panel);

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new FrameTest();
	}
}
