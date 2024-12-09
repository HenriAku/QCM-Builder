/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Théo Wychowski
 * @date 09/12/2024
 */
package IHM;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Controlleur.Controlleur;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameCreation extends JFrame implements ActionListener
{
	private Controlleur ctrl;
	private JTextField  txtName;
	private JButton     btnVald;
	private JButton     btnCancel;
	private String      operation;

	//operation = soit chapitre soit ressource
	public FrameCreation(Controlleur ctrl, String operation)
	{
		this.setTitle("Creation de " + operation);
		this.setLayout(new GridLayout(2, 1));
		this.setSize(400, 500);

		this.ctrl 	   = ctrl;
		this.operation = operation;

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
			System.out.println("ressource");
			// Création d'une ressource
		}
		else 
		{
			System.out.println("chp");
			// Création d'un chapitre
		}

		this.dispose();	
		}

		if (e.getSource() == this.btnCancel) 
		{
			this.dispose();	
		}
	}
}
