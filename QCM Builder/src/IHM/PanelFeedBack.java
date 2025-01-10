/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */

package IHM;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

import Metier.Question;

public class PanelFeedBack extends JPanel implements ActionListener
{
	private JTextArea     txtFeedBack   ;
	private FrameFeedBack frame         ;
	private JButton       btnEnregistrer;

	public PanelFeedBack(Question question, FrameFeedBack frame)
	{
		this.frame = frame;

		this.setLayout(new BorderLayout(25, 25));

		this.txtFeedBack = new JTextArea();
		if (question != null)
			this.txtFeedBack.setText(question.getExplication());

		JPanel panelBtn     = new JPanel (new FlowLayout(FlowLayout.CENTER));
		this.btnEnregistrer = new JButton("Enregistrer");


		panelBtn.add(this.btnEnregistrer);

		this.add(new JLabel("FeedBack"), BorderLayout.NORTH);
		this.add(this.txtFeedBack);
		this.add(panelBtn, BorderLayout.SOUTH);

		this.btnEnregistrer.addActionListener(this);
	}

	public String getFeedback()
	{
		return this.txtFeedBack.getText();
	}

	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnEnregistrer)
		{
			this.frame.Fermeture();
		}
	}
}