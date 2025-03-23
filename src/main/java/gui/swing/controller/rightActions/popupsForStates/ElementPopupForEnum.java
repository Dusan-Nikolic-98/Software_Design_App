package gui.swing.controller.rightActions.popupsForStates;

import commands.implementation.AddAttributeCommand;
import gui.swing.view.DiagramView;
import gui.swing.view.MainFrame;
import repository.implementation.diagramElements.elements.Interclass;
import repository.implementation.diagramElements.elements.classContent.ClassContent;
import repository.implementation.diagramElements.elements.classContent.ClassMethod;
import repository.implementation.diagramElements.elements.classContent.EnumClassContent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ElementPopupForEnum {
    private ArrayList<ClassContent> elementList;
    private ArrayList<EnumClassContent> enumsList;
    private DefaultListModel<String> enumsListModel;
    private JList<String> enumsListPanel;

    public ElementPopupForEnum(Interclass ic, ArrayList<ClassContent> elementList, DiagramView dv) {
        this.elementList = elementList;
        this.enumsList = new ArrayList<>();
        this.enumsListModel = new DefaultListModel<>();

        for (ClassContent element : elementList) {
            if (element instanceof EnumClassContent) {
                enumsList.add((EnumClassContent) element);
                enumsListModel.addElement(element.getContent());
            }
        }

        this.enumsListPanel = new JList<>(enumsListModel);

        JDialog dialog = new JDialog();
        dialog.setTitle("Element Manipulation");
        dialog.setLayout(new GridLayout(1, 2));

        JPanel methodPanel = createListPanel("Enums", enumsListPanel);

        dialog.add(methodPanel);

        JButton addMButton = new JButton("Add Enum");
        JButton removeButton = new JButton("Remove");
        JButton okButton = new JButton("OK");
        JButton changeNameButton = new JButton("Change Name");


        addMButton.addActionListener(e -> {
            JPanel panel = new JPanel(new GridLayout(0, 2));


            panel.add(new JLabel("New enum name:"));
            JTextField nameField = new JTextField();
            panel.add(nameField);


            int result = JOptionPane.showConfirmDialog(null, panel, "Enter Enum Details",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String name = nameField.getText();


                if (!name.isEmpty()) {
                    ClassContent newElement = new EnumClassContent(name);
                    //todo ovde
                    //ic.addContent(newElement);
                    AddAttributeCommand aac = new AddAttributeCommand(ic, newElement, 0);
                    dv.getCommandManager().addCommand(aac);

                    enumsList.add((EnumClassContent) newElement);
                    enumsListModel.addElement(name);
                }
            }
        });

        removeButton.addActionListener(e -> {
            int selMethodIdx = enumsListPanel.getSelectedIndex();

            if(selMethodIdx != -1){
                //todo ovde
                //ic.removeContent(enumsList.get(selMethodIdx)); //prvo da izbrisem iz liste generalno pa i sa panela
                ClassContent ccTemp = enumsList.get(selMethodIdx);
                AddAttributeCommand aac = new AddAttributeCommand(ic, ccTemp,1);
                dv.getCommandManager().addCommand(aac);


                enumsList.remove(selMethodIdx);
                enumsListModel.remove(selMethodIdx);

            }
        });
        okButton.addActionListener(e -> {
            dialog.dispose();
        });

        changeNameButton.addActionListener(e -> {
            String newName = JOptionPane.showInputDialog(dialog, "Enter new class name:");
            if (newName != null && !newName.isEmpty()) {
                //todo ovde
//                ic.setName(newName);
//                ic.setIme(newName);
//                MainFrame.getInstance().getClassyTree().changeElementNames();
                AddAttributeCommand aac = new AddAttributeCommand(ic, newName,ic.getName(), 2);
                dv.getCommandManager().addCommand(aac);
            }
        });

        //delic koji kad se selektuje jedna lista deselektuje drugu

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addMButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(okButton);
        buttonPanel.add(changeNameButton);

        dialog.add(buttonPanel);

        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(null); // Center the dialog
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    private JPanel createListPanel(String title, JList<String> listPanel) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));

        JScrollPane scrollPane = new JScrollPane(listPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

}
