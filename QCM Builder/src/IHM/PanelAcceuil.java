/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package IHM;

import Controlleur.Controlleur;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PanelAcceuil extends JPanel implements ActionListener 
{
	private Controlleur    ctrl;
	private FramePrincipal frame;

	public PanelAcceuil(Controlleur ctrl, FramePrincipal frame) 
	{
		this.ctrl  = ctrl;
		this.frame = frame;

		GridBagLayout grid = new GridBagLayout();
		setLayout(grid);

		GridBagConstraints c = new GridBagConstraints();

		// Contenu entête
		c.fill    = GridBagConstraints.BOTH;
		c.weightx = 1  ;
		c.weighty = 0.1;
		c.gridx   = 0  ;
		c.gridy   = 0  ;
		
		PanelNavigation navigation = new PanelNavigation(frame, null);
		this.add(navigation, c);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1  ;
		c.weighty = 0.9;
		c.gridx   = 0  ;
		c.gridy   = 1  ;

		//Panel pour les éléments dans la zone centrale
		JPanel contenuMilieu = new JPanel();
		contenuMilieu.setLayout(new GridBagLayout());

		//Configuration des éléments dans contenuMilieu
		GridBagConstraints cmConstraints = new GridBagConstraints();
		cmConstraints.fill    = GridBagConstraints.HORIZONTAL;
		cmConstraints.insets  = new Insets(20, 20, 20, 20); // Espacement autour des composants
		cmConstraints.weightx = 1;
		cmConstraints.weighty = 1;


		cmConstraints.gridx = 0;
		cmConstraints.gridy = 0;
		JPanel creationQuestion = createPanel("Créer question");
		contenuMilieu.add(creationQuestion, cmConstraints);

		cmConstraints.gridx = 1;
		cmConstraints.gridy = 0;
		JPanel creationEvaluation = createPanel("Générer évaluation");
		contenuMilieu.add(creationEvaluation, cmConstraints);

		cmConstraints.gridx = 2; 
		cmConstraints.gridy = 0; 
		JPanel creationRessource = createPanel("Créer ressource");
		contenuMilieu.add(creationRessource, cmConstraints);

		this.add(contenuMilieu, c);

		frame.setVisible(true);
	}

	//Méthodes pour créé les panel
	private JPanel createPanel( String boutonTexte) 
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));

		JButton bouton = new JButton(boutonTexte);
		bouton.setFont(new Font("Arial", Font.BOLD, 14)); 
		bouton.setPreferredSize(new Dimension(150, 40)); 
		bouton.setForeground(Color.WHITE);
		

		bouton.setBackground(new Color(201,80,46)); 
		bouton.setFocusPainted     (false);
		bouton.setContentAreaFilled(true );

		bouton.addActionListener(this);

		panel.add(bouton);

		return panel;
	}

	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() instanceof JButton) 
		{
			JButton btn = (JButton) e.getSource();
			if("Créer question".equals(btn.getText()))
			{
				if (this.ctrl.getNomRessources().length == 0)
				{
					JOptionPane.showMessageDialog(null, 
					"Il doit y avoir au moins une ressource pour créer une question", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					new FrameCreationQuestion(this.frame, this.ctrl, false);
				}
			}

			if("Générer évaluation".equals( btn.getText() ) )
			{
				if (this.ctrl.getNomRessources().length == 0)
				{
					JOptionPane.showMessageDialog(null, 
					"Il doit y avoir au moins une ressource pour générer une évaluation", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				else 
				{
					new FrameEvaluation(ctrl);
				}
			}

			if("Créer ressource".equals(btn.getText()))
				this.frame.afficheRessource();
		}
	}
}
