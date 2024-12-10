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
import Metier.Chapitre;
import Metier.Ressource;

public class FrameModification extends JFrame implements ActionListener
{
	private Controlleur ctrl;
	private JTextField  txtName;
	private JButton     btnVald;
	private JButton     btnCancel;
	private String      operation;
	private Ressource   ressource;
	private Chapitre    chapitre ;

	public FrameModification(Controlleur ctrl, String operation, Ressource ressource, Chapitre chapitre)
	{
		this.setTitle("Modification de Ressource");
		this.setLayout(new GridLayout(2, 1));
		this.setSize(400, 500);

		this.ctrl      = ctrl     ;
		this.operation = operation;
		this.ressource = ressource;
		this.chapitre  = chapitre ;

		/****************************/
		/*	Création des composant  */
		/****************************/

		this.txtName = new JTextField(ressource.getNom());

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
					this.ressource.setNom(name);	
					//methodes pour changer le nom du dossier de la ressource
				}
			}
			else
			{
				if (this.ressource.rechercheChapitre(name) == null) 
				{
					this.chapitre.setNom(name);
					//methodes pour changer le nom du dossier de la ressource
				}
			}

			this.dispose();	
		}

		if (e.getSource() == this.btnCancel) 
		{
			this.dispose();	
		}
	}
}
