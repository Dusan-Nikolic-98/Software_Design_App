package gui.swing.controller.rightActions.popupsForStates;

import commands.implementation.AddAttributeCommand;
import gui.swing.view.DiagramView;
import gui.swing.view.MainFrame;
import repository.implementation.diagramElements.elements.Interclass;
import repository.implementation.diagramElements.elements.classContent.ClassAttribute;
import repository.implementation.diagramElements.elements.classContent.ClassContent;
import repository.implementation.diagramElements.elements.classContent.ClassMethod;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ElementPopupBoth {
    private ArrayList<ClassContent> elementList;
    private ArrayList<ClassAttribute> attributeList;
    private ArrayList<ClassMethod> methodList;
    private DefaultListModel<String> attributeListModel;
    private DefaultListModel<String> methodListModel;
    private JList<String> attributeListPanel;
    private JList<String> methodListPanel;

    public ElementPopupBoth(Interclass ic, ArrayList<ClassContent> elementList, DiagramView dv) {
        this.elementList = elementList;
        this.attributeList = new ArrayList<>();
        this.methodList = new ArrayList<>();
        this.attributeListModel = new DefaultListModel<>();
        this.methodListModel = new DefaultListModel<>();

        for (ClassContent element : elementList) {
            if (element instanceof ClassAttribute) {
                attributeList.add((ClassAttribute) element);
                attributeListModel.addElement(element.getContent());
            } else if (element instanceof ClassMethod) {
                methodList.add((ClassMethod) element);
                methodListModel.addElement(element.getContent());
            }
        }

        this.attributeListPanel = new JList<>(attributeListModel);
        this.methodListPanel = new JList<>(methodListModel);

        JDialog dialog = new JDialog();
        dialog.setTitle("Element Manipulation");
        dialog.setLayout(new GridLayout(1, 2));

        JPanel attributePanel = createListPanel("Attributes", attributeListPanel);
        JPanel methodPanel = createListPanel("Methods", methodListPanel);

        dialog.add(attributePanel);
        dialog.add(methodPanel);

        JButton addButton = new JButton("Add Attribute");
        JButton addMButton = new JButton("Add Method");
        JButton removeButton = new JButton("Remove");
        JButton okButton = new JButton("OK");
        JButton changeNameButton = new JButton("Change Name");

        addButton.addActionListener(e -> {
            JPanel panel = new JPanel(new GridLayout(0, 2));
            panel.add(new JLabel("Operation:"));

            JComboBox<String> operationComboBox = new JComboBox<>(new String[]{"+", "-"});
            panel.add(operationComboBox);

            panel.add(new JLabel("Element Name:"));
            JTextField nameField = new JTextField();
            panel.add(nameField);

            panel.add(new JLabel("Data Type:"));
            JComboBox<String> dataTypeComboBox = new JComboBox<>(new String[]{"int", "string"});
            panel.add(dataTypeComboBox);

            int result = JOptionPane.showConfirmDialog(null, panel, "Enter Element Details",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String operation = (String) operationComboBox.getSelectedItem();
                String name = nameField.getText();
                String dataType = (String) dataTypeComboBox.getSelectedItem();

                if (!name.isEmpty()) {
                    String resultString = operation + name + ": " + dataType;

                    ClassAttribute newElement = new ClassAttribute(resultString);
                    newElement.setVisibility(operation);
                    newElement.setDataType(dataType);
                    newElement.setAttName(name);
                    //todo ovde!+
                    //ic.addContent(newElement);
                    AddAttributeCommand aac = new AddAttributeCommand(ic, newElement, 0);
                    dv.getCommandManager().addCommand(aac);
                    attributeList.add(newElement);
                    attributeListModel.addElement(resultString);
                }
            }

        });


        addMButton.addActionListener(e -> {
            JPanel panel = new JPanel(new GridLayout(0, 2));
            panel.add(new JLabel("Type:"));

            JComboBox<String> operationComboBox = new JComboBox<>(new String[]{" ","+", "-","#","~","/"});
            panel.add(operationComboBox);

            panel.add(new JLabel("Method Name:"));
            JTextField nameField = new JTextField();
            panel.add(nameField);

            panel.add(new JLabel(" Choose Input Types:"));
            String[] options = {"int", "string", "boolean", "char"};
            ArrayList<JCheckBox> checkBoxes = new ArrayList<>();

            for (String option : options) {
                JCheckBox checkBox = new JCheckBox(option);
                checkBoxes.add(checkBox);
                panel.add(checkBox);
            }

            int result = JOptionPane.showConfirmDialog(null, panel, "Enter Element Details",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String operation = (String) operationComboBox.getSelectedItem();
                String name = nameField.getText();
                //String dataType = (String) dataTypeComboBox.getSelectedItem();

                ArrayList<String> selectedOptionsList = checkBoxes.stream()
                        .filter(JCheckBox::isSelected)
                        .map(JCheckBox::getText)
                        .collect(Collectors.toCollection(ArrayList::new));

                String selectedOptions = String.join(", ", selectedOptionsList);

                if (!name.isEmpty()) {
                    String resultString = operation + name + "("+ selectedOptions +")";
                    ClassMethod newElement = new ClassMethod(resultString);
                    newElement.setMetName(name);
                    newElement.setVisibility(operation);
                    //todo ovde!+
                    AddAttributeCommand aac = new AddAttributeCommand(ic, newElement,0);
                    dv.getCommandManager().addCommand(aac);
                    methodList.add(newElement);
                    methodListModel.addElement(resultString);
                }
            }
        });

        removeButton.addActionListener(e -> {
            int selAttributeIdx = attributeListPanel.getSelectedIndex();
            int selMethodIdx = methodListPanel.getSelectedIndex();

            if(selMethodIdx != -1){
                //todo ovde!
                //ic.removeContent(methodList.get(selMethodIdx)); //prvo da izbrisem iz liste generalno pa i sa panela
                ClassContent ccTemp = methodList.get(selMethodIdx);
                AddAttributeCommand aac = new AddAttributeCommand(ic, ccTemp,1);
                dv.getCommandManager().addCommand(aac);

                methodList.remove(selMethodIdx);
                methodListModel.remove(selMethodIdx);

            }
            if(selAttributeIdx != -1){
                //todo ovde!
                //ic.removeContent(attributeList.get(selAttributeIdx));

                ClassContent ccTemp = attributeList.get(selAttributeIdx);
                AddAttributeCommand aac = new AddAttributeCommand(ic, ccTemp,1);
                dv.getCommandManager().addCommand(aac);

                attributeList.remove(selAttributeIdx);
                attributeListModel.remove(selAttributeIdx);
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

        //todo delic koji kad se selektuje jedna lista deselektuje drugu
        attributeListPanel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // da se osigura da se ne radi dva x
                int selectedIndex = attributeListPanel.getSelectedIndex();
                if(selectedIndex != -1)
                    methodListPanel.clearSelection();
            }
        });

        methodListPanel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = methodListPanel.getSelectedIndex();
                if(selectedIndex != -1)
                    attributeListPanel.clearSelection();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
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
