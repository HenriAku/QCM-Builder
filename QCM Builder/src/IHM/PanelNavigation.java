package IHM;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.File;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Metier.Ressource;

public class PanelNavigation extends JPanel implements ActionListener
{
	private JButton btnR;
	private JButton btnN;
	private JButton btnA;

	private FramePrincipal frame    ;
	private Ressource 	   ressource;
	
	public PanelNavigation(FramePrincipal frame, Ressource ressource)
	{
		this.setLayout(new FlowLayout(3, 200, 0));
		this.setBackground(new Color(201, 80, 46));
		this.setPreferredSize(new Dimension(200,30));
	
		this.frame     = frame    ;
		this.ressource = ressource;
	
		JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelBtn.setOpaque(false);
	
		// Style de la police soulignée
		Font font = new Font("Arial", Font.PLAIN, 15); 
		@SuppressWarnings("unchecked")
		Map<TextAttribute, Object> attributes = (Map<TextAttribute, Object>) font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		Font underlinedFont = new Font(attributes);
	
		// Création des boutons
		this.btnA = new JButton("Acceuil");
		this.btnA.setContentAreaFilled(false);
		this.btnA.setBorderPainted    (false);
		this.btnA.setFocusPainted     (false);
		this.btnA.setFont(underlinedFont);
		this.btnA.setForeground(Color.WHITE);
	
		this.btnR = new JButton("Ressource");
		this.btnR.setContentAreaFilled(false);
		this.btnR.setBorderPainted    (false);
		this.btnR.setFocusPainted     (false);
		this.btnR.setFont(underlinedFont);
		this.btnR.setForeground(Color.WHITE);
	
		this.btnN = new JButton("Notion");
		this.btnN.setContentAreaFilled(false);
		this.btnN.setBorderPainted    (false);
		this.btnN.setFocusPainted     (false);
		this.btnN.setFont      (underlinedFont);
		this.btnN.setForeground(Color.WHITE);
	
		// Condition sur le bouton Notion
		if (ressource == null) 
			this.btnN.setEnabled(false);


		Font fontTitre = new Font("Arial", Font.PLAIN, 25); 

		JLabel titre = new JLabel("QCM Builder");
		titre.setFont(fontTitre);
		titre.setForeground(Color.WHITE);

	
		panelBtn.add(btnA);
		panelBtn.add(btnR);
		panelBtn.add(btnN);
	
		this.add(panelBtn);
		this.add(titre   );
		this.add(new JLabel(new ImageIcon(".." + File.separator + "img" + File.separator + "IUT.png")));

		this.btnA.addActionListener(this);
		this.btnR.addActionListener(this);
		this.btnN.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.btnA) 
		{
			this.frame.afficheAcceuil();
		}

		if (e.getSource() == this.btnR) 
		{
			this.frame.afficheRessource();
		}

		if (e.getSource() == this.btnN) 
		{
			this.frame.afficheNotion(this.ressource);
		}
	}
	
}
