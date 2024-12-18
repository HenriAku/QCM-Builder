package IHM;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class PanelAddFile extends JPanel {

    private JLabel previewLabel; // Pour afficher une prévisualisation ou un message
    private JLabel dropLabel;   // Zone principale pour le glisser-déposer
    private File currentFile;   // Fichier actuellement chargé

    public PanelAddFile() 
    {
        setLayout(new BorderLayout());

        // Zone de drop
        dropLabel = new JLabel("Glissez un fichier ici (PNG, JPG, MP3, MP4, PDF)", SwingConstants.CENTER);
        dropLabel.setFont(new Font("Arial", Font.BOLD, 16));
        dropLabel.setOpaque(true);
        dropLabel.setBackground(Color.LIGHT_GRAY);
        dropLabel.setPreferredSize(new Dimension(300, 300));
        add(dropLabel, BorderLayout.CENTER);

        // Prévisualisation ou messages
        previewLabel = new JLabel("Prévisualisation", SwingConstants.CENTER);
        previewLabel.setPreferredSize(new Dimension(300, 300));
        previewLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(previewLabel, BorderLayout.EAST);

        // Configuration du support Drag-and-Drop
        setTransferHandler(new TransferHandler() {
            @Override
            public boolean canImport(TransferSupport support) {
                // Vérifie si le contenu glissé est un fichier
                return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
            }

            @Override
            public boolean importData(TransferSupport support) {
                if (!canImport(support)) {
                    return false;
                }

                try {
                    // Récupère les fichiers glissés
                    Transferable transferable = support.getTransferable();
                    @SuppressWarnings("unchecked")
                    List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);

                    // Traite les fichiers
                    for (File file : files) {
                        if (isAcceptedFile(file)) {
                            displayFile(file);
                            return true; // Traite le premier fichier valide
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

    public String getPath() 
    {
        if(this.currentFile!= null)
            return this.currentFile.getAbsolutePath();
        return null;
    }

    // Méthode pour vérifier les extensions acceptées
    private boolean isAcceptedFile(File file) {
        String[] acceptedExtensions = {"png", "jpg", "jpeg", "mp3", "mp4", "pdf"};
        String fileName = file.getName().toLowerCase();

        for (String ext : acceptedExtensions) {
            if (fileName.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    // Méthode pour afficher le fichier ou un message
    private void displayFile(File file) {
        try {
            String fileName = file.getName().toLowerCase();

            if (fileName.endsWith("png") || fileName.endsWith("jpg") || fileName.endsWith("jpeg")) {
                // Chargement et affichage d'une image
                BufferedImage img = ImageIO.read(file);
                if (img != null) {
                    Image scaledImg = img.getScaledInstance(previewLabel.getWidth(), previewLabel.getHeight(), Image.SCALE_SMOOTH);
                    previewLabel.setIcon(new ImageIcon(scaledImg));
                    previewLabel.setText(null); // Supprime le texte si une image est affichée
                }
            } else if (fileName.endsWith("mp3") || fileName.endsWith("mp4") || fileName.endsWith("pdf")) {
                // Affiche un message pour les autres formats
                previewLabel.setIcon(null);
                previewLabel.setText("Fichier accepté : " + file.getName());
            } else {
                // Fichier non pris en charge
                previewLabel.setText("Format de fichier non pris en charge.");
            }

            // Met à jour le label principal
            dropLabel.setText("Fichier chargé : " + file.getName());
            currentFile = file; // Enregistre le fichier chargé

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du traitement du fichier.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Méthode pour copier un fichier dans un dossier donné
    public void copierFichierDansDossier(String sourcePath, String destinationDir) {
        try {
            // Vérifie que le dossier existe, sinon le crée
            File dir = new File(destinationDir);
            if (!dir.exists()) {
                if (dir.mkdirs()) {
                    System.out.println("Dossier créé : " + destinationDir);
                } else {
                    System.out.println("Impossible de créer le dossier.");
                    return;
                }
            }

            // Prépare le fichier source et la destination
            File sourceFile = new File(sourcePath);
            if (!sourceFile.exists()) {
                System.out.println("Le fichier source n'existe pas : " + sourcePath);
                return;
            }

            // Copie le fichier
            File destinationFile = new File(dir, sourceFile.getName());
            Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Fichier copié dans : " + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Erreur lors de la copie du fichier : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Méthode pour créer un dossier
    public void creerDossierQuestion(String nomDossier, String cheminParent) {
        File dossier = new File(cheminParent, nomDossier);
        if (!dossier.exists()) {
            if (dossier.mkdirs()) {
                System.out.println("Dossier créé avec succès : " + dossier.getAbsolutePath());
            } else {
                System.out.println("Erreur lors de la création du dossier : " + dossier.getAbsolutePath());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("MultiFile Drop Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            PanelAddFile panelAddFile = new PanelAddFile();
            frame.add(panelAddFile);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Exemple d'utilisation pour copier un fichier
            panelAddFile.addPropertyChangeListener(evt -> {
                if ("currentFile".equals(evt.getPropertyName()) && panelAddFile.currentFile != null) {
                    String cheminDestination = "dossierDestination"; // Remplacez par le chemin cible
                    panelAddFile.copierFichierDansDossier(panelAddFile.currentFile.getAbsolutePath(), cheminDestination);
                }
            });
        });
    }
}
