/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package IHM;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private Controlleur ctrl;
	private JTextField  txtName;
	private JButton     btnVald;
	private JButton     btnCancel;
	private String      operation;
	private Ressource   ressource;
	private Notion    	notion ;
	private FramePrincipal frame;

	public FrameModification(Controlleur ctrl, FramePrincipal frame, String operation, Ressource ressource, Notion notion)
	{
		this.setTitle("Modification de " + operation);
		this.setLayout(new GridLayout(2, 1));
		this.setSize(400, 500);

		this.ctrl      = ctrl     ;
		this.operation = operation;
		this.ressource = ressource;
		this.notion    = notion   ;
		this.frame     = frame    ;

		/****************************/
		/*	Création des composant  */
		/****************************/

		if (this.operation.equals("Ressource")) 
			this.txtName = new JTextField(ressource.getNom());
		else
			this.txtName = new JTextField(notion.getNom());

		JPanel panelBtn = new JPanel();
		panelBtn.setLayout(new GridLayout(1,2));

		this.btnVald   = new JButton("Valider");
		this.btnCancel = new JButton("Annuler");

		/*********************************/
		/*	positionnement des composant */
		/*********************************/

		this.add(this.txtName);

		panelBtn.add(this.btnVald  );
		panelBtn.add(this.btnCancel);
		this.add(panelBtn);


		/*********************************/
		/*	  Activation des composant   */
		/*********************************/

		this.txtName  .addActionListener(this);
		this.btnVald  .addActionListener(this);
		this.btnCancel.addActionListener(this);


		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.btnVald) 
		{
			if (this.txtName.getText().length() == 0) 
	   	 	{
		   		JOptionPane.showMessageDialog(
					this, 
					"Le champ de texte ne peut pas être vide.",
					"Erreur", 
					JOptionPane.WARNING_MESSAGE 
				);
				return; 
			}

			String name = this.txtName.getText();
			if (this.operation.equals("Ressource")) 
			{
				if (this.ctrl.rechercheRessource(name) == null) 
				{
					this.ctrl.renommerDossier(this.ressource.getNom(), name);
					this.ressource.setNom(name);
				}
				this.frame.refreshRessource();
			}
			else
			{
				if (this.ressource.rechercheNotion(name) == null) 
				{
					this.ctrl.renommerDossier(this.ressource.getNom() + "/" + this.notion.getNom(), this.ressource.getNom() + "/" + name);
					this.notion.setNom(name);
				}
				this.frame.refreshNotion(ressource);
			}

			this.dispose();	
		}

		if (e.getSource() == this.btnCancel) 
		{
			this.dispose();	
		}
	}
}
