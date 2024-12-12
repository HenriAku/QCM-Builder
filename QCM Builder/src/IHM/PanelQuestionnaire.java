package IHM;

import Controlleur.Controlleur;
import Metier.Notion;
import Metier.Ressource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelQuestionnaire extends JPanel implements ActionListener 
{
    private Controlleur ctrl;
    private FrameQuestionnaire frame;
    private Ressource ressourceActuel;

    private JComboBox<String> comboBoxRessource;
    private JComboBox<String> comboBoxNotion;

    private JButton btnAddNot;
    private JButton btnDelNot;
    private JButton btnCreer ;

    private ArrayList<Notion> lstNotions;
    private DefaultTableModel tableModel;
    private JTable jTab;

    private JLabel nbQuestions;

    public PanelQuestionnaire(Controlleur ctrl, FrameQuestionnaire frame) {
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
        scrollPane.setPreferredSize(new Dimension(350, 150)); // Fixer une taille pour la table

        // Composants ajoutés avec des marges
        addLabeledComponent("Ressource : ", this.comboBoxRessource);
        addLabeledComponent("Notion : ", this.comboBoxNotion);
        addComponent(this.btnAddNot);
        addComponent(this.btnDelNot);
        addComponent(new JLabel("Liste des Notions :"));
        addComponent(scrollPane);

        // Nombre de questions
        this.nbQuestions = new JLabel("0");
        addLabeledComponent("Nombre de Questions : ", this.nbQuestions);

        // Bouton Créer
        this.btnCreer = new JButton("Créer");
        this.btnCreer.addActionListener(this);
        addComponent(this.btnCreer);
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

        if (e.getSource() == this.btnCreer) {
            // Action lors de la création (à personnaliser)
            System.out.println("Création des questions...");
        }
    }

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

    private void updateNbQuestions() {
        int total = 0;
    
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            for (int col = 1; col <= 4; col++) { // Colonnes 2, 3, 4, 5
                Object value = tableModel.getValueAt(row, col);
                if (value != null && !value.toString().isEmpty()) {
                    try {
                        total += Integer.parseInt(value.toString());
                    } catch (NumberFormatException ex) {
                        // Ignorer les valeurs non numériques
                    }
                }
            }
        }
    
        // Mettre à jour le JLabel
        this.nbQuestions.setText(String.valueOf(total));
    }
}
