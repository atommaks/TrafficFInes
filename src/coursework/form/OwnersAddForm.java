package coursework.form;

import coursework.database.DatabaseTools;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OwnersAddForm extends JDialog{
    private JPanel mainPanel;
    private JButton cancelBtn, addBtn;
    private JFormattedTextField drivingLicenceNumberFormattedTextField, birthdayFormattedTextField,
            issueDepartmentFormattedTextField;
    private JTextField lastNameTextField, firstNameTextField, middleNameTextField, cityTextField;
    private JCheckBox aCheckBox, a1CheckBox, bCheckBox, b1CheckBox, cCheckBox, c1CheckBox, dCheckBox, d1CheckBox,
            CECheckBox, c1ECheckBox, DECheckBox, d1ECheckBox, tmCheckBox, tbCheckBox, BECheckBox, mCheckBox;
    private JFrame frame;
    private DatabaseTools databaseTools;
    private MainForm parentForm;

    private static final String TITLE = "Добавить права";
    private static final String WARNING_TITLE = "Неправильно введенные данные";
    private static final String LICENCE_LENGTH_WARNING_MSG = "Заполните поле \"Номер прав\"!";
    private static final String LICENCE_EXISTENCE_WARNING_MSG = "Номер прав занят! Введите другой номер.";
    private static final String CATEGORIES_WARNING_MSG = "Выберете категории!";
    private static final String DEPARTMENT_WARNING_MSG = "Заполните поле \"Код подразделения ГИБДД\"!";
    private static final String LAST_NAME_WARNING_MSG = "Неправильная фамилия!\n" + "\n" +
            "Фамилия должна начинаться с большой буквы и может содержать только русские буквы.";
    private static final String FIRST_NAME_WARNING_MSG = "Неправильное имя!\n\n" +
            "Имя должно начинаться с большой буквы и может содержать только русские буквы.";
    private static final String MIDDLE_NAME_WARNING_MSG = "Нерпавильное отчество!\n\n" +
            "Отчество должно начинаться с большой буквы и может содержать только русские буквы.";
    private static final String DATE_LENGTH_WARNING_MSG = "Заполните поле \"Дата рождения\"!";
    private static final String DATE_DAY_WARNING_MSG = "Неправильное значение дня даты рождения!";
    private static final String DATE_MONTH_WARNING_MSG = "Неправильное значение месяца даты рождения!";
    private static final String AGE_WARNING_MSG = "Лицу меньше 18 лет. Он пока не может иметь права и управлять ТС.";
    private static final String CITY_EMPTY_WARNING_MSG = "Заполните поле \"Город\"!";
    private static final String CITY_NAME_WARNING_MSG = "Неправильное название города! Название города должно" +
            "начинаться с большой буквы и может сожержать только русские буквы.";
    private static final String ERROR_TITLE = "SQL Error";

    public OwnersAddForm(DatabaseTools databaseTools, JFrame frame, MainForm parentForm) {
        super(frame, TITLE, true);
        this.databaseTools = databaseTools;
        this.frame = frame;
        this.parentForm = parentForm;

        addButtonsListeners();
        addCheckBoxesListeners();

        mCheckBox.setSelected(true);
        mCheckBox.setEnabled(false);

        getContentPane().add(mainPanel);
        pack();
        setResizable(false);
        setVisible(true);
    }

    private void addCheckBoxesListeners() {
        aCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (aCheckBox.isSelected()) {
                    a1CheckBox.setSelected(true);
                    a1CheckBox.setEnabled(false);
                } else {
                    a1CheckBox.setEnabled(true);
                    a1CheckBox.setSelected(false);
                }
            }
        });

        bCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bCheckBox.isSelected()) {
                    b1CheckBox.setSelected(true);
                    b1CheckBox.setEnabled(false);
                } else {
                    b1CheckBox.setSelected(false);
                    b1CheckBox.setEnabled(true);
                }
            }
        });

        cCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cCheckBox.isSelected()) {
                    c1CheckBox.setSelected(true);
                    c1CheckBox.setEnabled(false);
                } else {
                    c1CheckBox.setSelected(false);
                    c1CheckBox.setEnabled(true);
                }
            }
        });

        dCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dCheckBox.isSelected()) {
                    d1CheckBox.setSelected(true);
                    d1CheckBox.setEnabled(false);
                } else {
                    d1CheckBox.setSelected(false);
                    d1CheckBox.setEnabled(true);
                }
            }
        });

        CECheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CECheckBox.isSelected()) {
                    c1ECheckBox.setSelected(true);
                    c1ECheckBox.setEnabled(false);
                } else {
                    c1ECheckBox.setSelected(false);
                    c1ECheckBox.setEnabled(true);
                }
            }
        });

        DECheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (DECheckBox.isSelected()) {
                    d1ECheckBox.setSelected(true);
                    d1ECheckBox.setEnabled(false);
                } else {
                    d1ECheckBox.setSelected(false);
                    d1ECheckBox.setEnabled(true);
                }
            }
        });

        BECheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (BECheckBox.isSelected()) {
                    b1CheckBox.setSelected(true);
                    b1CheckBox.setEnabled(false);
                    bCheckBox.setSelected(true);
                    bCheckBox.setEnabled(false);
                } else {
                    b1CheckBox.setSelected(false);
                    b1CheckBox.setEnabled(true);
                    bCheckBox.setSelected(false);
                    bCheckBox.setEnabled(true);
                }
            }
        });
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
                    String drivingLicenceNumber = drivingLicenceNumberFormattedTextField.getText().
                            replaceAll(" ", "");
                    if (drivingLicenceNumber.length() != 10) {
                        JOptionPane.showMessageDialog(frame, LICENCE_LENGTH_WARNING_MSG, WARNING_TITLE,
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (databaseTools.containsDrivingLicenceNumber(drivingLicenceNumber)) {
                        JOptionPane.showMessageDialog(frame, LICENCE_EXISTENCE_WARNING_MSG, WARNING_TITLE,
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    Date issueDate = new Date();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(issueDate);
                    calendar.add(Calendar.YEAR, 10);
                    Date expirationDate = calendar.getTime();

                    StringBuilder categories = new StringBuilder();
                    categories.append("M ");
                    if (aCheckBox.isSelected())
                        categories.append("A ");
                    if (a1CheckBox.isSelected())
                        categories.append("A1 ");
                    if (bCheckBox.isSelected())
                        categories.append("B ");
                    if (b1CheckBox.isSelected())
                        categories.append("B1 ");
                    if (cCheckBox.isSelected())
                        categories.append("C ");
                    if (c1CheckBox.isSelected())
                        categories.append("C1 ");
                    if (dCheckBox.isSelected())
                        categories.append("D ");
                    if (d1CheckBox.isSelected())
                        categories.append("D1 ");
                    if (CECheckBox.isSelected())
                        categories.append("CE ");
                    if (c1ECheckBox.isSelected())
                        categories.append("C1E ");
                    if (DECheckBox.isSelected())
                        categories.append("DE ");
                    if (d1ECheckBox.isSelected())
                        categories.append("D1E ");
                    if (tmCheckBox.isSelected())
                        categories.append("Tm ");
                    if (tbCheckBox.isSelected())
                        categories.append("Tb ");
                    if (BECheckBox.isSelected())
                        categories.append("BE");
                    if (categories.toString().equals("M ")) {
                        JOptionPane.showMessageDialog(frame, CATEGORIES_WARNING_MSG, WARNING_TITLE,
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    String issueDepartment = issueDepartmentFormattedTextField.getText();
                    if (issueDepartment.length() != 10) {
                        JOptionPane.showMessageDialog(frame, DEPARTMENT_WARNING_MSG, WARNING_TITLE,
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    String lastName = lastNameTextField.getText();
                    if (!isLastOrMiddleNameValid(lastName)) {
                        JOptionPane.showMessageDialog(frame, LAST_NAME_WARNING_MSG, WARNING_TITLE,
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    String firstName = firstNameTextField.getText();
                    if (!isFirstNameValid(firstName)) {
                        JOptionPane.showMessageDialog(frame, FIRST_NAME_WARNING_MSG, WARNING_TITLE,
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    String middleName = middleNameTextField.getText();
                    if (!isLastOrMiddleNameValid(middleName)) {
                        JOptionPane.showMessageDialog(frame, MIDDLE_NAME_WARNING_MSG, WARNING_TITLE,
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    if (birthdayFormattedTextField.getText().length() != 10) {
                        JOptionPane.showMessageDialog(frame, DATE_LENGTH_WARNING_MSG, WARNING_TITLE,
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    String[] date = birthdayFormattedTextField.getText().split("\\.");
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
                    Date birthday = dateFormat.parse(birthdayFormattedTextField.getText());
                    if (calculateAge(birthday) < 18) {
                        JOptionPane.showMessageDialog(frame, AGE_WARNING_MSG, WARNING_TITLE,
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    String city = cityTextField.getText();
                    if (city.replaceAll(" ", "").length() == 0) {
                        JOptionPane.showMessageDialog(frame, CITY_EMPTY_WARNING_MSG, WARNING_TITLE,
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (!isCityNameValid(city)) {
                        JOptionPane.showMessageDialog(frame, CITY_NAME_WARNING_MSG, WARNING_TITLE,
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    databaseTools.insertDrivingLicence(drivingLicenceNumber, issueDate, expirationDate,
                            categories.toString(), issueDepartment);
                    databaseTools.insertOwner(lastName, firstName, middleName, birthday, city, drivingLicenceNumber);
                    parentForm.updateTablesData();
                    dispose();
                } catch (Exception err) {
                    JOptionPane.showMessageDialog(frame, err.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

    private void createUIComponents() throws Exception {
        MaskFormatter dateFormatter = new MaskFormatter("##.##.####");
        birthdayFormattedTextField = new JFormattedTextField(dateFormatter);

        MaskFormatter drivingLicenceNumberFormatter = new MaskFormatter("#### ######");
        drivingLicenceNumberFormattedTextField = new JFormattedTextField(drivingLicenceNumberFormatter);

        MaskFormatter issueDepartmentMaskFormatter = new MaskFormatter("ГИБДД ####");
        issueDepartmentFormattedTextField = new JFormattedTextField(issueDepartmentMaskFormatter);
    }

    private int calculateAge(Date birthday) {
        int years = 0;
        int months = 0;

        Calendar birthDay = Calendar.getInstance();
        birthDay.setTimeInMillis(birthday.getTime());

        long currentTime = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTime);
        years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int currMonth = now.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;

        months = currMonth - birthMonth;
        if (months < 0) {
            years--;
            months = 12 - birthMonth + currMonth;
            if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
                months--;
            }
        } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            years--;
            months = 11;
        }

        return years;
    }

    private boolean isFirstNameValid (String name) {
        return name.matches("[А-Я][а-я]*");
    }

    private boolean isLastOrMiddleNameValid (String name) {
        return name.matches("[А-Я][а-я]*([ '-][А-Я][а-я]+)*");
    }

    private boolean isCityNameValid (String city) {
        return city.matches("[А-Я][а-я]*([ '-][А-Я][а-я]+)*");
    }
}