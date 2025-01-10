package IHM;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

import Controlleur.Controlleur;
import Metier.Question;

public class PanelAddFile extends JPanel implements ActionListener
{
	private JLabel       previewLabel ;
	private JLabel       dropLabel    ;   
	private File         currentFile  ;   
	private JButton      btnEnregistre;
	private JButton      btnSupprimer ;
	private FrameAddFile frameAdd     ;
	private Controlleur  ctrl         ;
	private Question     question     ;
	private String       res          ;
	private String       not          ;
	private boolean      modification ;

	public PanelAddFile(FrameAddFile frameAdd, Controlleur ctrl, Question question, String res, String not, boolean modification) 
	{
		this.setLayout(new BorderLayout());

		this.frameAdd     = frameAdd;
		this.ctrl         = ctrl    ;
		this.question     = question;
		this.res          = res     ;
		this.not          = not     ;
		this.modification = modification;

		//Zone de drop du fichier
		dropLabel = new JLabel("Glissez un fichier ici (PNG, JPG, MP3, MP4, PDF)", SwingConstants.CENTER);
		dropLabel.setFont         (new Font("Arial", Font.BOLD, 16));
		dropLabel.setOpaque       (true);
		dropLabel.setBackground   (Color.LIGHT_GRAY);
		dropLabel.setPreferredSize(new Dimension(300, 300));
		this.add(dropLabel, BorderLayout.CENTER);

		//Zone de prévisualisation du fichier
		previewLabel = new JLabel("Prévisualisation", SwingConstants.CENTER);
		previewLabel.setPreferredSize(new Dimension(300, 300));
		previewLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.add(previewLabel, BorderLayout.EAST);

		//Configuration du support Drag-and-Drop
		setTransferHandler(new TransferHandler() 
		{
			public boolean canImport(TransferSupport support) 
			{
				//Vérifie si le contenu glissé est un fichier
				return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
			}

			public boolean importData(TransferSupport support) 
			{
				if (!canImport(support)) 
					return false;

				try {
					Transferable transferable = support.getTransferable();
					@SuppressWarnings("unchecked")
					List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);

					for (File file : files) 
					{
						if (isAcceptedFile(file)) 
						{
							displayFile(file);
							return true;
						}
					}
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
				return false;
			}
		});

		this.btnEnregistre = new JButton("Enregistrer");
		this.btnSupprimer  = new JButton("Supprimer");
		

		JPanel panelBtn = new JPanel();
		if (this.modification)
		{
			panelBtn.setLayout(new GridLayout(3,1));
			panelBtn.add(this.btnEnregistre);
			panelBtn.add(new JLabel("Le complément ne s'affiche pas mais vous pouvez le supprimer"));
			panelBtn.add(this.btnSupprimer);
			this.add(panelBtn,BorderLayout.SOUTH);
		}
		else
		{
			this.add(this.btnEnregistre, BorderLayout.SOUTH);
		}
			

		this.btnEnregistre.addActionListener(this);
		this.btnSupprimer .addActionListener(this);
	}

	public String getPath() 
	{
		if(this.currentFile!= null)
			return this.currentFile.getAbsolutePath();
		return null;
	}

	//Méthode pour vérifier les extensions acceptées
	private boolean isAcceptedFile(File file) 
	{
		String[] acceptedExtensions = {"png", "jpg", "jpeg", "mp3", "mp4", "pdf"};
		String fileName = file.getName().toLowerCase();

		for (String ext : acceptedExtensions) 
		{
			if (fileName.endsWith(ext)) 
				return true;
		}
		return false;
	}

	//Méthode pour afficher le fichier
	private void displayFile(File file) 
	{
		try {
			String fileName = file.getName().toLowerCase();

			if (fileName.endsWith("png") || fileName.endsWith("jpg") || fileName.endsWith("jpeg"))
			 {
				//Chargement et affichage d'une image
				BufferedImage img = ImageIO.read(file);
				if (img != null) 
				{
					Image scaledImg = img.getScaledInstance(previewLabel.getWidth(), previewLabel.getHeight(), Image.SCALE_SMOOTH);
					previewLabel.setIcon(new ImageIcon(scaledImg));
					previewLabel.setText(null);
				}
			} else if (fileName.endsWith("mp3") || fileName.endsWith("mp4") || fileName.endsWith("pdf"))
			{
				previewLabel.setIcon(null);
				previewLabel.setText("Fichier accepté : " + file.getName());
			} else 
			{
				previewLabel.setText("Format de fichier non pris en charge.");
			}

			//Met à jour le label principal
			dropLabel.setText("Fichier chargé : " + file.getName());
			currentFile = file;

		} catch (Exception e) 
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Erreur lors du traitement du fichier.", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	//Méthode pour copier un fichier dans un dossier donné
	public void copierFichierDansDossier(String sourcePath, String destinationDir) 
	{
		try {
			//Vérifie que le dossier existe, sinon le crée
			File dir = new File(destinationDir);
			if (!dir.exists()) 
			{
				if (dir.mkdirs()) {
				} else {
					return;
				}
			}

			//Prépare le fichier source et la destination
			File sourceFile = new File(sourcePath);
			if (!sourceFile.exists()) 
			{
				System.out.println("Le fichier source n'existe pas : " + sourcePath);
				return;
			}

			//Copie le fichier
			File destinationFile = new File(dir, sourceFile.getName());
			Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Fichier copié dans : " + destinationFile.getAbsolutePath());
		} catch (IOException e) 
		{
			System.out.println("Erreur lors de la copie du fichier : " + e.getMessage());
			e.printStackTrace();
		}
	}

	//Méthode pour créer un dossier
	public void creerDossierQuestion(String nomDossier, String cheminParent) 
	{
		File dossier = new File(cheminParent, nomDossier);
		if (!dossier.exists()) 
		{
			if (dossier.mkdirs()) {
			}
		}
	}

	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.btnEnregistre) 
		{
			this.frameAdd.fermerFenetre();
		}
		if (e.getSource() == this.btnSupprimer)
		{
			this.ctrl.supprimerComplement(question, res, not);
			JOptionPane.showMessageDialog(null, "Le complément à bien été supprimé", "Suppresion compléments", JOptionPane.INFORMATION_MESSAGE);
			this.frameAdd.dispose();
		}
	}
}

