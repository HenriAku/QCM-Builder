package IHM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Pan extends JPanel implements ActionListener
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
