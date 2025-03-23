    package gui.swing.controller;

    import gui.swing.view.DiagramView;
    import gui.swing.view.MainFrame;
    import gui.swing.view.PackageView2;
    import painters.ElementPainter;
    import repository.implementation.diagramElements.elements.Interclass;

    import javax.imageio.ImageIO;
    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.KeyEvent;
    import java.awt.image.BufferedImage;
    import java.io.File;

    public class SaveAsAction extends AbstractClassyAction {
        public SaveAsAction(){
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
            putValue(SMALL_ICON, loadIcon("/snapshot.png"));
            putValue(NAME, "Save as snapshot");
            putValue(SHORT_DESCRIPTION, "Save as snapshot");
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            PackageView2 packageView = MainFrame.getInstance().getPv();
            DiagramView diagramView = packageView.getCurrentDiagramView();
            if (diagramView != null) {
                try {
                    // Get all painters in the diagram view
                    ElementPainter[] allPainters = diagramView.getPainters().toArray(new ElementPainter[0]);
                    // Calculate the bounding box that encompasses all elements
                    Rectangle diagramBounds = calculateInterclassBounds(diagramView);
                    // Create a BufferedImage with dimensions based on the bounding box
                    BufferedImage screenshot = new BufferedImage(
                            diagramBounds.width, diagramBounds.height, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g = screenshot.createGraphics();
                    // Set the background color
                    Color backgroundColor = new Color(120, 120, 120); // Dark gray color
                    g.setColor(backgroundColor);
                    g.fillRect(0, 0, screenshot.getWidth(), screenshot.getHeight());
                    g.translate(-diagramBounds.x, -diagramBounds.y); // Adjust the origin
                    // Render all elements onto the BufferedImage
                    for (ElementPainter ep : allPainters) {
                        ep.draw(g);
                    }
                    g.dispose();
                    // Open a file chooser dialog
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Save As");
                    String diagramName = MainFrame.getInstance().getClassyTree().getSelectedNode().toString();
                    fileChooser.setSelectedFile(new File(diagramName + ".png"));
                    int userSelection = fileChooser.showSaveDialog(null);

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        // Ensure the file has .png extension
                        if (!fileToSave.getName().toLowerCase().endsWith(".png")) {
                            fileToSave = new File(fileToSave.getAbsolutePath() + ".png");
                        }
                        // Save the screenshot as a PNG file
                        ImageIO.write(screenshot, "png", fileToSave);
                        JOptionPane.showMessageDialog(
                                null,
                                "File saved successfully as: " + fileToSave.getName() + "\nLocation: " + fileToSave.getAbsolutePath(),
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error saving the file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No diagram selected. Please select the diagram you want to save in project explorer", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private Rectangle calculateInterclassBounds(DiagramView diagramView) {
            // Get the bounding box that encompasses all instances of Interclass elements
            Rectangle diagramBounds = new Rectangle();
            for (ElementPainter ep : diagramView.getPainters()) {
                if (ep.getModel() instanceof Interclass) {
                    Interclass interclass = (Interclass) ep.getModel();
                    Rectangle elementBounds = new Rectangle(interclass.getPosition(), interclass.getSize());
                    diagramBounds = diagramBounds.union(elementBounds); // Expand the diagram bounds
                }
            }
            // Offset the diagram to be in the centre of the picture
            int bottomShift = 50; // Shift the bounds downwards
            int rightShift = 100; // Shift the bounds to the right
            int topExpansion = 50;
            int leftExpansion = 50;
            diagramBounds.translate(rightShift, bottomShift); // Shifting the bounds to the right and down
            diagramBounds.grow(leftExpansion, topExpansion); // Expanding the left and top

            return diagramBounds;
        }
    }
