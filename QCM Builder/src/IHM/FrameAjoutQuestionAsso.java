package IHM;

import javax.swing.JFrame;

public class FrameAjoutQuestionAsso extends JFrame
{
	private PanelAjoutQuestionAsso panelAjoutQuestionAsso;

	public FrameAjoutQuestionAsso()
	{
		this.setTitle   ("Création question");
		this.setSize    (920,550);
		this.setLocation( 350,200);
		this.setResizable(false);

		this.panelAjoutQuestionAsso = new PanelAjoutQuestionAsso();
		this.add(this.panelAjoutQuestionAsso);


		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	public static void main(String[] args) {
		new FrameAjoutQuestionAsso();
	}

}
