package IHM;

import java.util.ArrayList;

import javax.swing.JFrame;

import Controlleur.Controlleur;
import Metier.Notion;
import Metier.Question;

public class FrameTestNot extends JFrame
{
	private PanelNotion panel;

	public FrameTestNot()
	{
		this.setTitle("Test");
		this.setSize(1000, 1000);

		ArrayList<Question> lstQ = new ArrayList<Question>();
		
		ArrayList<Notion> lstRes = new ArrayList<Notion>();
		lstRes.add(new Notion("Male le con 1",lstQ));
		lstRes.add(new Notion("Notion 1",lstQ));
		lstRes.add(new Notion("Notion 1",lstQ));
		lstRes.add(new Notion("Notion 1",lstQ));
		lstRes.add(new Notion("Notion 2",lstQ));
		lstRes.add(new Notion("Notion 3",lstQ));
		lstRes.add(new Notion("Notion 4",lstQ));
		lstRes.add(new Notion("Notion 5",lstQ));
		lstRes.add(new Notion("Notion 6",lstQ));



		this.panel = new PanelNotion(new Controlleur(),lstRes);
		this.add(this.panel);

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new FrameTestNot();
	}
}

