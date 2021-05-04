package coursework.form;

import coursework.database.DatabaseTools;
import coursework.database.entity.Car;
import coursework.database.entity.DrivingLicence;
import coursework.database.entity.FineType;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FineAddForm extends JDialog {
    private JPanel mainPanel;
    private JComboBox<FineType> fineTypeСomboBox;
    private JComboBox licencesComboBox;
    private JComboBox<Car> carsComboBox;
    private JFormattedTextField fineDateFormattedTextField;
    private JCheckBox deprivationCheckBox;
    private JButton addBtn, cancelBtn;
    private DatabaseTools databaseTools;
    private JFrame frame;
    private MainForm parentForm;

    private static final String TITLE = "Добавить штраф";
    private static final String WARNING_TITLE = "Неправильно введенные данные";
    private static final String DATE_DAY_WARNING_MSG = "Неправильное значение дня даты рождения!";
    private static final String DATE_MONTH_WARNING_MSG = "Неправильное значение месяца даты рождения!";
    private static final String DATE_LENGTH_WARNING_MSG = "Заполните поле \"Дата нарушения\"!";
    private static final String ERROR_TITLE = "SQL Error";

    public FineAddForm(DatabaseTools databaseTools, JFrame frame, MainForm parentForm) {
        super(frame, TITLE, true);
        this.databaseTools = databaseTools;
        this.frame = frame;
        this.parentForm = parentForm;

        addButtonListeners();

        getContentPane().add(mainPanel);
        pack();
        setResizable(false);
        setVisible(true);
    }

    private void addButtonListeners() {
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
                    FineType type = (FineType)fineTypeСomboBox.getSelectedItem();

                    if (fineDateFormattedTextField.getText().length() != 10) {
                        JOptionPane.showMessageDialog(frame, DATE_LENGTH_WARNING_MSG, WARNING_TITLE,
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    String date[] = fineDateFormattedTextField.getText().split("\\.");
                    int month = Integer.parseInt(date[1]);
                    int day = Integer.parseInt(date[0]);
                    if (month > 12 || month < 1) {
                        JOptionPane.showMessageDialog(frame, DATE_MONTH_WARNING_MSG, WARNING_TITLE,
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if ((month == 2 && (day > 28 || day < 1)) ||
                            ((month == 4 || month == 6 || month == 9 || month == 11 ) && (day > 30 || day < 1)) ||
                            (day > 31 || day < 1)) {
                        JOptionPane.showMessageDialog(frame, DATE_DAY_WARNING_MSG, WARNING_TITLE,
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                    Date fineDate = dateFormat.parse(fineDateFormattedTextField.getText());

                    String licenceNumber = (String)licencesComboBox.getSelectedItem();

                    Car car = (Car)carsComboBox.getSelectedItem();

                    boolean deprivation = deprivationCheckBox.isSelected();

                    databaseTools.insertFine(type, fineDate, licenceNumber, car.getStateNumber(), deprivation);
                    parentForm.updateTablesData();
                    dispose();
                } catch (Exception err) {
                    JOptionPane.showMessageDialog(frame, err.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void createUIComponents() throws Exception{
        fineTypeСomboBox = new JComboBox<>();
        for (FineType type : databaseTools.getFineTypes().values())
            fineTypeСomboBox.addItem(type);

        MaskFormatter dateFormatter = new MaskFormatter("##.##.####");
        fineDateFormattedTextField = new JFormattedTextField(dateFormatter);

        String[] licences = new String[databaseTools.getDrivingLicences().size()];
        int i = 0;
        for (DrivingLicence licence : databaseTools.getDrivingLicences().values())
            licences[i++] = licence.getDrivingLicenceNumber();
        licencesComboBox = new JComboBox(licences);

        carsComboBox =  new JComboBox<>();
        for (Car car : databaseTools.getCars().values())
            carsComboBox.addItem(car);
    }
}