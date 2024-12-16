package IHM;

import Controlleur.Controlleur;
import Metier.Notion;
import Metier.Ressource;
import Metier.Notion;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelEvaluation extends JPanel implements ActionListener 
{
    private Controlleur ctrl;
    private FrameEvaluation frame;
    private Ressource ressourceActuel;

    private JComboBox<String> comboBoxRessource;
    private JComboBox<String> comboBoxNotion;

    private JButton btnAddNot ;
    private JButton btnDelNot ;
    private JButton btnGenerer;

    private ArrayList<Notion> lstNotions;
    private DefaultTableModel tableModel;
    private JTable jTab;

    private int nbQuestionsTF;
    private int nbQuestionsF ;
    private int nbQuestionsM ;
    private int nbQuestionsD ;

    private JLabel lblQuestionsTF;
    private JLabel lblQuestionsF ;
    private JLabel lblQuestionsM ;
    private JLabel lblQuestionsD ;

    public PanelEvaluation(Controlleur ctrl, FrameEvaluation frame) {
        this.ctrl = ctrl;
        this.frame = frame;
        this.lstNotions = new ArrayList<>();

        // Utilisation de BoxLayout pour plus de flexibilité
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Ressource ComboBox
        String[] nomRessource = new String[this.ctrl.getLstRessource().size()];
        for (int Rsc = 0; Rsc < this.ctrl.getLstRessource().size(); Rsc++) {
            nomRessource[Rsc] = this.ctrl.getLstRessource().get(Rsc).getNom();
        }
        this.comboBoxRessource = new JComboBox<>(nomRessource);
        this.comboBoxRessource.addActionListener(e -> changerNotion(this.comboBoxRessource));

        ressourceActuel = this.ctrl.getLstRessource().get(0);

        // Notion ComboBox
        String[] nomNot = new String[ressourceActuel.getNotions().size()];
        for (int not = 0; not < ressourceActuel.getNotions().size(); not++) {
            nomNot[not] = ressourceActuel.getNotions().get(not).getNom();
        }
        this.comboBoxNotion = new JComboBox<>(nomNot);

        // Bouton Ajouter Notion
        this.btnAddNot = new JButton("Ajouter Notion");
        this.btnAddNot.addActionListener(this);

        // Bouton Supprimer Notion
        this.btnDelNot = new JButton("Supprimer Notion");
        this.btnDelNot.addActionListener(this);

        // Modèle de tableau pour JTable
        String[] tableHeaders = {"Notion", "TF", "F", "M", "D"};
        Object[][] tableData = {}; // Initialement vide

        this.tableModel = new DefaultTableModel(tableData, tableHeaders) {
            public boolean isCellEditable(int row, int column) {
                return column > 0; // Colonnes éditables sauf la première
            }
        };
        
        this.tableModel.addTableModelListener(e -> updateNbQuestions());

        this.jTab = new JTable(tableModel);

        // Ajout d'une barre de défilement autour de la JTable
        JScrollPane scrollPane = new JScrollPane(this.jTab);
        scrollPane.setPreferredSize(new Dimension(350, 150)); // taille de la table

        // Composants ajoutés avec des marges
        addLabeledComponent("Ressource : ", this.comboBoxRessource);
        addLabeledComponent("Notion : ", this.comboBoxNotion);
        addComponent(this.btnAddNot);
        addComponent(this.btnDelNot);
        addComponent(new JLabel("Liste des Notions :"));
        addComponent(scrollPane);

        // Nombre de questions
        this.lblQuestionsTF = new JLabel(""+nbQuestionsTF);
        addLabeledComponent("Nombre de Questions très faciles : ", this.lblQuestionsTF);

        this.lblQuestionsF = new JLabel(""+nbQuestionsF);
        addLabeledComponent("Nombre de Questions faciles : ", this.lblQuestionsF);

        this.lblQuestionsM = new JLabel(""+nbQuestionsM);
        addLabeledComponent("Nombre de Questions moyennes : ", this.lblQuestionsM);

        this.lblQuestionsD = new JLabel(""+nbQuestionsD);
        addLabeledComponent("Nombre de Questions difficiles : ", this.lblQuestionsD);

        // Bouton Créer
        this.btnGenerer = new JButton("Créer");
        this.btnGenerer.addActionListener(this);
        addComponent(this.btnGenerer);
    }

    private void addLabeledComponent(String labelText, JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(labelText));
        panel.add(component);
        this.add(panel);
    }

    private void addComponent(JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(component);
        this.add(panel);
    }

    /**
     * Lors d'une action sur l'ajout et la suppression d'une notion
     * Lors de la génération d'une evaluation
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnAddNot) {
            for (Notion not : ressourceActuel.getNotions()) {
                if (not.getNom().equals(this.comboBoxNotion.getSelectedItem())) {
                    this.comboBoxRessource.setEnabled(false);
                    if (!this.lstNotions.contains(not)) {
                        this.lstNotions.add(not);
                        tableModel.addRow(new Object[]{not.getNom(), "", "", "", ""}); // Ajouter une ligne
                    }
                }
            }
        }

        if (e.getSource() == this.btnDelNot) {
            int selectedRow = this.jTab.getSelectedRow();
            if (selectedRow != -1) {
                String selectedNotionName = (String) tableModel.getValueAt(selectedRow, 0);
                for (Notion not : ressourceActuel.getNotions()) {
                    if (not.getNom().equals(selectedNotionName)) {
                        if (this.lstNotions.contains(not)) {
                            this.lstNotions.remove(not);
                            tableModel.removeRow(selectedRow); // Supprimer la ligne
                        }
                    }
                }
            }
        }

        if (e.getSource() == this.btnGenerer) {

            if ( getTotalNbQuestions() < 1)
                JOptionPane.showMessageDialog(null, "Il doit y avoir au moins une question (TF, F, M, D) choisie dans le tableau pour générer", "Erreur", JOptionPane.ERROR_MESSAGE);
            else
            {
                HashMap< String, int[] > mapQuestion = new HashMap < String, int[] >();
                
                for (Notion not : ressourceActuel.getNotions()) {
                    for (int row = 0; row < tableModel.getRowCount(); row++)
                    {
                        mapQuestion.put(not.getNom(), valeurLigne(row));
                    }
                }
                String verif = this.ctrl.getMetier().genererEvaluation(ressourceActuel.getNom(), true, mapQuestion );
                if(!verif.equals(""))
                {
                    JOptionPane.showMessageDialog(null, "Il y a une erreur dans la création\n" + verif, "Erreur", JOptionPane.ERROR_MESSAGE);
                }

            }

        }
    }

    /**
     * Change les notions dans la JComboBox en suivant la ressource choisie
     */
    private void changerNotion(JComboBox e) {
        for (int Rsc = 0; Rsc < this.ctrl.getLstRessource().size(); Rsc++) {
            if (this.ctrl.getLstRessource().get(Rsc).getNom().equals(e.getSelectedItem())) {
                this.ressourceActuel = this.ctrl.getLstRessource().get(Rsc);
                String[] nomNot = new String[ressourceActuel.getNotions().size()];
                for (int not = 0; not < ressourceActuel.getNotions().size(); not++) {
                    nomNot[not] = ressourceActuel.getNotions().get(not).getNom();
                }
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(nomNot);
                this.comboBoxNotion.setModel(model);
            }
        }
    }

    /**
     * Met a jour le nombre de questions TF F M et D dans le label
     */
    private void updateNbQuestions() {
        int[] valeurQuestions = {0,0,0,0};

        for (int row = 0; row < tableModel.getRowCount(); row++)
        {
            int[] valeur = valeurLigne(row);
            valeurQuestions[0] += valeur[0];
            valeurQuestions[1] += valeur[1];
            valeurQuestions[2] += valeur[2];
            valeurQuestions[3] += valeur[3];
        }

        // Mettre à jour les labels
        this.nbQuestionsTF = valeurQuestions[0];
        this.nbQuestionsF  = valeurQuestions[1] ;
        this.nbQuestionsM  = valeurQuestions[2] ;
        this.nbQuestionsD  = valeurQuestions[3] ;

        this.lblQuestionsTF.setText( String.valueOf(valeurQuestions[0]));
        this.lblQuestionsF.setText ( String.valueOf(valeurQuestions[1]) );
        this.lblQuestionsM.setText ( String.valueOf(valeurQuestions[2]) );
        this.lblQuestionsD.setText ( String.valueOf(valeurQuestions[3]) );
    }

    /**
     * Renvoie dans un tableau d'entier les valeurs de la ligne du tableau mise en paramètre
     * [0] questions TF, [1] questions F, [2] questions M, [3] questions D
     */
    public int[] valeurLigne(int ligne)
    {
        int[] valQuestions = new int[4];

        for (int col = 1; col <= 4; col++) { // Colonnes 1, 2, 3, 4
            Object value = tableModel.getValueAt(ligne, col);
            if (value != null && !value.toString().isEmpty()) {
                try {
                    switch(col)
                    {
                        case 1 : valQuestions[0] = Integer.parseInt(value.toString()); break;
                        case 2 : valQuestions[1] = Integer.parseInt(value.toString()); break;
                        case 3 : valQuestions[2] = Integer.parseInt(value.toString()); break;
                        case 4 : valQuestions[3] = Integer.parseInt(value.toString()); break;
                    }

                } catch (NumberFormatException ex) {
                    // Ignorer les valeurs non numériques
                }
            }
        }
        return valQuestions;
    }

    /**
     * Renvoie le total de questions du tableau
     */
    public int getTotalNbQuestions()
    {
        return this.nbQuestionsTF + this.nbQuestionsF + this.nbQuestionsM + this.nbQuestionsD;
    }
}
