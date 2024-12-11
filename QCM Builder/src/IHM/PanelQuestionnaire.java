/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package IHM;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Controlleur.Controlleur;
import Metier.Ressource;
import Metier.Notion;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelQuestionnaire extends JPanel implements ActionListener
{
    private Controlleur ctrl;
    private FrameQuestionnaire frame;
    private Ressource ressourceActuel;
    private JComboBox<String> comboBoxRessource;
    private JComboBox<String> comboBoxNotion;
    private JButton btnAddNot;
    private JButton btnDelNot;
    private ArrayList<Notion> lstNotions;
    private DefaultListModel<String> listModel;
    private JList<String> notionList;
    private JSpinner nbQuestion;
    private JButton btnCreer;
    private int notSelect = -1; // Par défaut, aucune sélection

    public PanelQuestionnaire(Controlleur ctrl, FrameQuestionnaire frame)
    {
        this.ctrl = ctrl;
        this.frame = frame;
        this.lstNotions = new ArrayList<>();

        // Utilisation de BoxLayout pour plus de flexibilité
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Ressource ComboBox
        String[] nomRessource = new String[this.ctrl.getLstRessource().size()];
        for (int Rsc = 0; Rsc < this.ctrl.getLstRessource().size(); Rsc++)
        {
            nomRessource[Rsc] = this.ctrl.getLstRessource().get(Rsc).getNom();
        }
        this.comboBoxRessource = new JComboBox<>(nomRessource);
        this.comboBoxRessource.addActionListener(e -> changerNotion(this.comboBoxRessource));

        ressourceActuel = this.ctrl.getLstRessource().get(0);

        // Notion ComboBox
        String[] nomNot = new String[ressourceActuel.getNotions().size()];
        for (int not = 0; not < ressourceActuel.getNotions().size(); not++)
        {
            nomNot[not] = ressourceActuel.getNotions().get(not).getNom();
        }
        this.comboBoxNotion = new JComboBox<>(nomNot);

        // Bouton Ajouter Notion
        this.btnAddNot = new JButton("Ajouter Notion");
        this.btnAddNot.addActionListener(this);

        // Bouton Supprimer Notion
        this.btnDelNot = new JButton("Supprimer Notion");
        this.btnDelNot.addActionListener(this);

        // Liste des notions sélectionnés avec Scroll Pane
        this.listModel = new DefaultListModel<>();
        this.notionList = new JList<>(listModel);
        this.notionList.setVisibleRowCount(5); // Nombre de lignes visibles avant de scroller
        JScrollPane scrollPane = new JScrollPane(this.notionList);

        // Fixer une taille fixe pour le scroll pane
        scrollPane.setPreferredSize(new Dimension(250, 100));  // Largeur fixe, hauteur fixe à 100px

        // Action listener pour détecter la sélection d'une notion
        this.notionList.addListSelectionListener(e -> 
        {
            if (!e.getValueIsAdjusting()) // Vérifie que la sélection est terminée
            {
                notSelect = notionList.getSelectedIndex();
            }
        });

        // Nombre de questions
        this.nbQuestion = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));

        // Bouton Créer
        this.btnCreer = new JButton("Créer");
        this.btnCreer.addActionListener(this);

        // Composants ajoutés avec des marges
        addLabeledComponent("Ressource : ", this.comboBoxRessource);
        addLabeledComponent("Notion : ", this.comboBoxNotion);
        addComponent(this.btnAddNot);
        addComponent(this.btnDelNot);
        addComponent(new JLabel("Liste des Notions :"));
        addComponent(scrollPane);
        addLabeledComponent("Nombre de Questions : ", this.nbQuestion);
        addComponent(this.btnCreer);
    }

    private void addLabeledComponent(String labelText, JComponent component)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(labelText));
        panel.add(component);
        this.add(panel);
    }

    private void addComponent(JComponent component)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(component);
        this.add(panel);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnAddNot)
        {
            for (Notion not : ressourceActuel.getNotions())
            {
                if (not.getNom().equals(this.comboBoxNotion.getSelectedItem()))
                {
                    this.comboBoxRessource.setEnabled(false);
                    if (!this.lstNotions.contains(not))
                    {
                        this.lstNotions.add(not);
                        this.listModel.addElement(not.getNom());
                    }
                }
            }
        }

        if (e.getSource() == this.btnDelNot)
        {
            if (notSelect != -1)
            {
                String selectedNotionName = listModel.getElementAt(notSelect);
                for (Notion not : ressourceActuel.getNotions())
                {
                    if (not.getNom().equals(selectedNotionName))
                    {
                        if (this.lstNotions.contains(not))
                        {
                            this.lstNotions.remove(not);
                            this.listModel.removeElement(not.getNom());
                        }
                    }
                }
            }
        }

        if (e.getSource() == this.btnCreer)
        {
            // Action lors de la création (à personnaliser)
            System.out.println("Création des questions...");
        }
    }

    private void changerNotion(JComboBox e)
    {
        for (int Rsc = 0; Rsc < this.ctrl.getLstRessource().size(); Rsc++)
        {
            if (this.ctrl.getLstRessource().get(Rsc).getNom().equals(e.getSelectedItem()))
            {
                this.ressourceActuel = this.ctrl.getLstRessource().get(Rsc);
                String[] nomNot = new String[ressourceActuel.getNotions().size()];
                for (int not = 0; not < ressourceActuel.getNotions().size(); not++)
                {
                    nomNot[not] = ressourceActuel.getNotions().get(not).getNom();
                }
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(nomNot);
                this.comboBoxNotion.setModel(model);
            }
        }
    }
}
