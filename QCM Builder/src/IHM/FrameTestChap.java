package IHM;

import java.util.ArrayList;

import javax.swing.JFrame;

import Controlleur.Controlleur;
import Metier.Chapitre;
import Metier.Question;

public class FrameTestChap extends JFrame
{
	private PanelChapitre panel;

	public FrameTestChap()
	{
		this.setTitle("Test");
		this.setSize(1000, 1000);

		ArrayList<Question> lstQ = new ArrayList<Question>();
		
		ArrayList<Chapitre> lstRes = new ArrayList<Chapitre>();
		lstRes.add(new Chapitre("Male le con 1",lstQ));
		lstRes.add(new Chapitre("Chapitre 1",lstQ));
		lstRes.add(new Chapitre("Chapitre 1",lstQ));
		lstRes.add(new Chapitre("Chapitre 1",lstQ));
		lstRes.add(new Chapitre("Chapitre 2",lstQ));
		lstRes.add(new Chapitre("Chapitre 3",lstQ));
		lstRes.add(new Chapitre("Chapitre 4",lstQ));
		lstRes.add(new Chapitre("Chapitre 5",lstQ));
		lstRes.add(new Chapitre("Chapitre 6",lstQ));



		this.panel = new PanelChapitre(new Controlleur(),lstRes);
		this.add(this.panel);

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new FrameTestChap();
	}
}

