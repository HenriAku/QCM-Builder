/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package IHM;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Controlleur.Controlleur;
import Metier.Notion;
import Metier.Ressource;

public class FrameModification extends JFrame implements ActionListener 
{
	private Controlleur    ctrl      ;
	private JTextField     txtName   ;
	private JButton        btnValider;
	private JButton        btnAnnuler;
	private String         operation ;
	private Ressource      ressource ;
	private Notion         notion    ;
	private FramePrincipal frame     ;

	public FrameModification(Controlleur ctrl, FramePrincipal frame, String operation, Ressource ressource, Notion notion) 
	{
		this.setTitle ("Modification de " + operation);
		this.setLayout(new GridLayout(2, 1));
		this.setSize  (400, 200); 
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.ctrl      = ctrl;
		this.operation = operation;
		this.ressource = ressource;
		this.notion    = notion;
		this.frame     = frame;

		/****************************/
		/* Création des composants  */
		/****************************/

		this.txtName = new JTextField(this.operation.equals("Ressource") ? ressource.getNom() : notion.getNom());

		JPanel panelButtons = new JPanel();
		panelButtons.setLayout(new GridLayout(1, 2, 10, 0)); //Espacement des boutons
		
		this.btnValider = new JButton("Valider");
		this.btnValider.setBackground(new Color(201,80,46));
		this.btnValider.setForeground(Color.WHITE);

		this.btnAnnuler = new JButton("Annuler");
		this.btnAnnuler.setBackground(new Color(201,80,46));
		this.btnAnnuler.setForeground(Color.WHITE);	

		/*********************************/
		/* Positionnement des composants */
		/*********************************/

		this.add(this.txtName);

		panelButtons.add(this.btnValider);
		panelButtons.add(this.btnAnnuler);
		this.add(panelButtons);

		/*********************************/
		/* Activation des composants     */
		/*********************************/

		this.txtName   .addActionListener(this);
		this.btnValider.addActionListener(this);
		this.btnAnnuler.addActionListener(this);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.btnValider) 
		{
			if (this.txtName.getText().trim().isEmpty()) 
			{
				JOptionPane.showMessageDialog(
					this,
					"Le champ de texte ne peut pas être vide.",
					"Erreur",
					JOptionPane.WARNING_MESSAGE
				);
				return;
			}

			String name = this.txtName.getText().trim();
			if (this.operation.equals("Ressource")) 
			{
				if (this.ctrl.rechercheRessource(name) == null) 
				{
					this.ctrl.renommerDossier(this.ressource.getNom(), name);
					this.ressource.setNom(name);
				}
				this.frame.refreshRessource();
			} else {
				if (this.ressource.rechercheNotion(name) == null) 
				{
					this.ctrl.renommerDossier(
						this.ressource.getNom() + File.separator + this.notion.getNom(),
						this.ressource.getNom() + File.separator + name
					);
					this.notion.setNom(name);
				}
				this.frame.refreshNotion(ressource);
			}

			this.dispose();
		}

		if (e.getSource() == this.btnAnnuler) 
		{
			this.dispose();
		}
	}
}
