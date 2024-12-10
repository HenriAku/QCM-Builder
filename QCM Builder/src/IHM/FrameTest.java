package IHM;

import java.util.ArrayList;

import javax.swing.JFrame;

import Controlleur.Controlleur;
import Metier.Chapitre;
import Metier.Question;
import Metier.Ressource;

public class FrameTest extends JFrame
{
	private PanelRessource panel  ;
	private PanelAcceuil   acceuil;
	private Controlleur    ctrl   ;

	public FrameTest(Controlleur ctrl)
	{
		this.setTitle("Test");
		this.setSize(1000, 1000);

		this.ctrl = ctrl;
		

		this.acceuil = new PanelAcceuil(this.ctrl, this);
		this.add(this.panel);

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void majIhm()
	{
		this.remove(this.panel);
		this.add(new PanelRessource(ctrl, this));
		this.revalidate();
		this.repaint();
	}

	public void ressourceToChapitre(Ressource ressource)
	{
		this.remove(this.panel);
		this.add(new PanelChapitre(this.ctrl, ressource,this));
		this.revalidate();
		this.repaint();
	}
}
