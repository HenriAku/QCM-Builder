/**
 * @author Rougeolle Henri, Yachir Yanis, Vauthier Maël, Viez Remi, Wychowski Théo
 * @date 09/12/2024
 */
package IHM;

import javax.swing.JFrame;

public class FrameAddFile extends JFrame
{

    public static void main(String[] args) 
    {
        new FrameAddFile();    
    }

    private PanelAddFile panel;

	public FrameAddFile()
	{
		this.setTitle    ("Ajout Fichier");
		this.setSize     (600,300      );
		this.setLocation ( 350,200              );
		this.setResizable(false           ); 

        panel = new PanelAddFile();

		this.add(panel);

		this.setVisible(true);
	}

    public String getPath() 
    {
        return this.panel.getPath();
    }

}