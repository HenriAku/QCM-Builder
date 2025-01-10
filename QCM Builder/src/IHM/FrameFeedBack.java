package IHM;

import javax.swing.JFrame;

import Metier.Question;

public class FrameFeedBack extends JFrame
{
	private PanelFeedBack panelFeedBack;

	public FrameFeedBack(Question question)
	{
		this.setTitle    ("FeedBack");
		this.setSize     (920,550);
		this.setLocation (350,200);
		this.setResizable(false);

		this.panelFeedBack = new PanelFeedBack(question, this);
		this.add(this.panelFeedBack);

		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	public String getFeedback()
	{
		return this.panelFeedBack.getFeedback();
	}

	public void Fermeture()
	{
		this.setVisible(false);
	}

}
