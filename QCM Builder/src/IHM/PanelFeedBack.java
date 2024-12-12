package IHM;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PanelFeedBack extends JPanel
{
	private JTextArea txtFeedBack;

	public PanelFeedBack()
	{
		this.setLayout(new BorderLayout(25, 25));

		this.txtFeedBack = new JTextArea();
		
		this.add(new JLabel("FeedBack"), BorderLayout.NORTH);
		this.add(this.txtFeedBack);
	}
}