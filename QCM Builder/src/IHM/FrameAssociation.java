/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package IHM;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FrameAssociation extends JFrame implements ActionListener 
{
    private JButton btnAdd;
    private JButton btnModif;
    private JButton btnEnregistrer;
    private JTextField txtQuestion;
    private ArrayList<PanelRep> lstRep;

    public FrameAssociation() 
	{
        this.setTitle("Création de question associatif");
        this.setSize(400, 500);
        this.setLayout(new GridLayout(4, 1, 5, 5));

        /******************************/
        /*  Création des composants    */
        /******************************/

        this.txtQuestion = new JTextField();
        this.lstRep      = new ArrayList<>();

        // Ajout initial d'un panel de réponse
        this.addNewPanelRep();

        JPanel panelBtn     = new JPanel (new GridLayout(1, 3, 5, 5));
        this.btnAdd         = new JButton("add"        );
        this.btnModif       = new JButton("Modif"      );
        this.btnEnregistrer = new JButton("Enregistrer");

        /**********************************/
        /*  Positionnement des composants */
        /**********************************/

        this.add(new JLabel("Question"));
        this.add(this.txtQuestion);
        for (PanelRep panel : this.lstRep) 
        {
            this.add(panel);
        }

        panelBtn.add(this.btnAdd);
        panelBtn.add(this.btnModif);
        panelBtn.add(this.btnEnregistrer);
        this.add(panelBtn);

        /**********************************/
        /*  Activation des composants     */
        /**********************************/
        this.btnAdd        .addActionListener(this);
        this.btnModif      .addActionListener(this);
        this.btnEnregistrer.addActionListener(this);

        this.setVisible(true);
    }

    private void addNewPanelRep() 
	{
        PanelRep panel = new PanelRep(this.lstRep,this);
        this.lstRep.add(panel);
        this.add(panel);
        this.revalidate();
        this.repaint();
    }

    public void majIHM()
    {
        this.revalidate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnAdd) 
		{
            addNewPanelRep();
			this.repaint();
        }
        // Autres actions...
    }

    public static void main(String[] args) 
	{
        new FrameAssociation();
    }

    /**************************************/
    /* Classe interne PanelRep            */
    /**************************************/
    private class PanelRep extends JPanel implements ActionListener
	{
		private static int nbPanel = 0;

        private JButton    btnSuppr1;
        private JTextField txtRep1;
        private JTextField txtRep2;
        private JButton    btnSuppr2;
		private ArrayList<PanelRep> lstRep;
        private FrameAssociation    frame;

        public PanelRep(ArrayList<PanelRep> lstRep, FrameAssociation frame) 
		{
            this.setLayout(new GridLayout(1, 4, 5, 5));

			PanelRep.nbPanel++;
			this.lstRep = lstRep;
            this.frame = frame;

            this.btnSuppr1 = new JButton("suppr");
            this.txtRep1 = new JTextField();
            this.txtRep2 = new JTextField();
            this.btnSuppr2 = new JButton("suppr");

            this.add(this.btnSuppr1);
            this.add(this.txtRep1);
            this.add(this.txtRep2);
            this.add(this.btnSuppr2);

            this.btnSuppr1.addActionListener(this);
			this.btnSuppr2.addActionListener(this);
        }

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (e.getSource() == this.btnSuppr1) 
			{
				this.lstRep.remove(nbPanel-1);
                this.frame.majIHM();
			}
		}

        
    }
}
