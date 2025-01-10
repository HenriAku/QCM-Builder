/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */

package IHM;

import Metier.Question;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelVisu extends JPanel 
{
    private ArrayList<Question> lstQuestions;

    public PanelVisu(ArrayList<Question> lq, boolean chrono) 
    {
        this.lstQuestions = lq;

        this.setLayout(new BorderLayout());
        
        String[] nomCol;

        if (chrono) 
        {
            nomCol = new String[]{"Question", "Difficulté", "Point", "Temps"};
        } 
        else 
        {
            nomCol = new String[]{"Question", "Difficulté", "Point"};
        }

        DefaultTableModel model = new DefaultTableModel(nomCol, 0);

        float tempsTot = 0;

        if (chrono) 
        {
            for (Question q : lstQuestions) 
            {
                model.addRow(new Object[]{q.getQuestion(), q.getDifficulte().getDifficulte(), q.getPoint(), (int)(q.getTemps()) + "secondes"});
                tempsTot += q.getTemps(); //Additionne les temps
            }
            //Ajout ligne temps total
            model.addRow(new Object[]{"Temps total", "", "", tempsTot/60 + " minutes " + tempsTot%60+  " secondes"});
        } 
        else 
        {
            for (Question q : lstQuestions) 
            {
                model.addRow(new Object[]{q.getQuestion(), q.getDifficulte().getDifficulte(), q.getPoint()});
            }
        }

        JTable table = new JTable(model) 
        {
            public boolean isCellEditable(int row, int column) 
            {
                return false;
            }
        };

        table.getColumnModel().getColumn(0).setCellRenderer(new TextAreaRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        this.add(scrollPane, BorderLayout.CENTER);
    }

    static class TextAreaRenderer extends JTextArea implements javax.swing.table.TableCellRenderer 
    {
        public TextAreaRenderer() 
        {
            setLineWrap(true);
            setWrapStyleWord(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
        {
            setText(value != null ? value.toString() : "");
            setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
            if (table.getRowHeight(row) != getPreferredSize().height) 
            {
                table.setRowHeight(row, getPreferredSize().height);
            }
            return this;
        }
    }
}