/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package IHM;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Controlleur.Controlleur;
import Metier.Notion;
import Metier.Ressource;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FrameCreation extends JFrame implements ActionListener
{
	private Controlleur ctrl;
	private JTextField  txtName;
	private JButton     btnVald;
	private JButton     btnCancel;
	private String      operation;
	private String      ressource;
	private FramePrincipal   frame;

	//operation = soit notion soit ressource
	public FrameCreation(Controlleur ctrl, String operation, String ressource, FramePrincipal frame)
	{
		this.setTitle("Creation de " + operation);
		this.setLayout(new GridLayout(2, 1));
		this.setSize(400, 500);

		this.ctrl 	   = ctrl;
		this.frame     = frame;
		this.operation = operation;
		this.ressource = ressource;

		/****************************/
		/*	Création des composant  */
		/****************************/

		this.txtName = new JTextField();

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
				Ressource ressource = new Ressource(name);
				this.ctrl.creerDossierRessource(ressource); 
				this.ctrl.addRessource(ressource);
				this.frame.refreshRessource();
			}
			else 
			{
				Notion    not = new Notion(name, null);
				Ressource res = this.ctrl.rechercheRessource(this.ressource);
				this.ctrl.creerDossierNotion(res , not); 
				res.addNotion(not);
				this.frame.afficheNotion(res);
			}
			this.dispose();	
		}

		if (e.getSource() == this.btnCancel) 
		{
			this.dispose();	
		}
	}
}
