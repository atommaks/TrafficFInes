package coursework.form;

import coursework.database.DatabaseTools;
import coursework.database.entity.Owner;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarsAddForm extends JDialog {
    private JFormattedTextField stateNumberFormattedTextField;
    private JComboBox modelComboBox, ownerСomboBox;
    private JTextField brandTextField, colorTextField;
    private JButton addBtn, cancelBtn;
    private JPanel mainPanel;
    private DatabaseTools databaseTools;
    private JFrame frame;
    private MainForm parentForm;

    private static final String TITLE = "Добавить автомобиль";
    private static final String WARNING_TITLE = "Неправильно введенные данные";
    private static final String STATE_NUMBER_WARNING_MSG = "Поле \"Гос. номер\" заполнено неверно!";
    private static final String STATE_NUMBER_EXISTS_MSG = "Такой гос.номе уже занят! Попробуйте другой.";
    private static final String BRAND_WARNING_MSG = "Заполните поле \"Марка\"!";
    private static final String COLOR_WARNING_MSG = "Заполните поле \"Цвет\"!";
    private static final String ERROR_TITLE = "SQL Exception";
    private static final String[] MODELS = new String[] {
            "Седан", "Универсал", "Хэтчбэк", "Купе", "Лимузин", "Микроавтобус", "Минивэн", "Хардтоп", "Таун-кар",
            "Комби", "Лифтбэк", "Фастбэк", "Кабриолет", "Родстер", "Фаэтон", "Ландо", "Брогам", "Тарга", "Спайдер",
            "Шутингбрейк", "Пикап", "Фургон"
    };

    public CarsAddForm(DatabaseTools databaseTools, JFrame frame, MainForm parentForm) {
        super(frame, TITLE, true);
        this.databaseTools = databaseTools;
        this.frame = frame;
        this.parentForm = parentForm;

        addButtonsListeners();

        getContentPane().add(mainPanel);
        pack();
        setResizable(false);
        setVisible(true);
    }

    private void addButtonsListeners() {
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String stateNumber = stateNumberFormattedTextField.getText();
                    if (stateNumber.length() < 8) {
                        JOptionPane.showMessageDialog(frame, STATE_NUMBER_WARNING_MSG, WARNING_TITLE,
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (databaseTools.containsStateNumber(stateNumber)) {
                        JOptionPane.showMessageDialog(frame, STATE_NUMBER_EXISTS_MSG, WARNING_TITLE,
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    String model = (String) modelComboBox.getSelectedItem();

                    String brand = brandTextField.getText();
                    if (brand.length() == 0) {
                        JOptionPane.showMessageDialog(frame, BRAND_WARNING_MSG, WARNING_TITLE,
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    String color = colorTextField.getText();
                    if (color.length() == 0) {
                        JOptionPane.showMessageDialog(frame, COLOR_WARNING_MSG, WARNING_TITLE,
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    String[] owner = ownerСomboBox.getSelectedItem().toString().split("-");
                    String drivingLicenceNumber = owner[1];

                    databaseTools.insertCar(stateNumber, model, brand, color, drivingLicenceNumber);
                    parentForm.updateTablesData();
                    dispose();
                } catch (java.sql.SQLException err) {
                    JOptionPane.showMessageDialog(frame, err.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void createUIComponents() throws Exception {
        MaskFormatter stateNumberFormatter = new MaskFormatter("L###LL###");
        stateNumberFormatter.setValidCharacters("йцукенгшщзхъфывапролджэячсмитьбю1234567890");
        stateNumberFormattedTextField = new JFormattedTextField(stateNumberFormatter);

        modelComboBox = new JComboBox(MODELS);

        String[] ownerNames = new String[databaseTools.getOwners().size()];
        int i = 0;
        for (Owner owner : databaseTools.getOwners().values())
            ownerNames[i++] = owner.toString() + "-" + owner.getDrivingLicence().getDrivingLicenceNumber();
        ownerСomboBox = new JComboBox(ownerNames);
    }
}