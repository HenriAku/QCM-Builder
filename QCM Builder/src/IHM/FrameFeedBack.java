package IHM;

import javax.swing.JFrame;

public class FrameFeedBack extends JFrame
{
	private PanelFeedBack panelFeedBack;

	public FrameFeedBack()
	{
		this.setTitle   ("Création question");
		this.setSize    (920,550);
		this.setLocation( 350,200);
		this.setResizable(false);

		this.panelFeedBack = new PanelFeedBack();
		this.add(this.panelFeedBack);

		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

}
