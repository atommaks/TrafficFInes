package coursework.form;

import coursework.database.DatabaseTools;
import coursework.table.CarsTableModel;
import coursework.table.FinesTableModel;
import coursework.table.OwnersTableModel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;


public class MainForm {
    private JTabbedPane tabbedPane;
    private JPanel mainPanel, finesTab, carsTab, ownersTab, finesTopPanel, carsTopPanel, ownersTopPanel;
    private JTable finesTable, carsTable, ownersTable;
    private JTextField finesSearchField, ownersSearchField, carsSearchField;
    private JButton ownersAddBtn, ownersUpdBtn, carsUpdBtn, carsAddBtn, carsDeleteBtn ,finesAddBtn, finesPayedBtn,
            finesDeleteBtn, finesUpdBtn, ownersDeleteBtn;
    private FinesTableModel finesTableModel;
    private CarsTableModel carsTableModel;
    private OwnersTableModel ownersTableModel;
    private DatabaseTools databaseTools;
    private Connection connection;
    private JFrame frame;

    private static final String APP_TITLE = "Штрафы";
    private static final String ERROR_TITLE = "SQL Error";

    public MainForm(Connection connection) {
        this.connection =  connection;
        frame = new JFrame(APP_TITLE);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListeners();
        addButtonsListeners();
        addSearchFieldsListeners();
    }

    private void addSearchFieldsListeners() {
        ownersSearchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableRowSorter<OwnersTableModel> tr = new TableRowSorter<>(ownersTableModel);
                ownersTable.setRowSorter(tr);
                String text = ownersSearchField.getText();
                if (text.trim().length() == 0)
                    tr.setRowFilter(null);
                else
                    tr.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
        });

        carsSearchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableRowSorter<CarsTableModel> tr = new TableRowSorter<>(carsTableModel);
                carsTable.setRowSorter(tr);
                String text = carsSearchField.getText();
                if (text.trim().length() == 0)
                    tr.setRowFilter(null);
                else
                    tr.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
        });

        finesSearchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableRowSorter<FinesTableModel> tr = new TableRowSorter<>(finesTableModel);
                finesTable.setRowSorter(tr);
                String text = finesSearchField.getText();
                if (text.trim().length() == 0)
                    tr.setRowFilter(null);
                else
                    tr.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
        });
    }

    private void addButtonsListeners() {
        ownersAddBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OwnersAddForm(databaseTools, frame, MainForm.this);
            }
        });

        ownersUpdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTablesData();
            }
        });

        ownersDeleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = ownersTable.getSelectedRow();
                    if (row != -1) {
                        databaseTools.deleteOwner((String) ownersTableModel.getValueAt(row, 3));
                        updateTablesData();
                    }
                } catch (java.sql.SQLException err) {
                    JOptionPane.showMessageDialog(frame, err.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        carsAddBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CarsAddForm(databaseTools, frame, MainForm.this);
            }
        });

        carsUpdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTablesData();
            }
        });

        carsDeleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = carsTable.getSelectedRow();
                    if (row != -1) {
                        databaseTools.deleteCar((String)carsTable.getValueAt(row, 0));
                        updateTablesData();
                    }
                } catch (java.sql.SQLException err) {
                    JOptionPane.showMessageDialog(frame, err.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        finesAddBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FineAddForm(databaseTools, frame, MainForm.this);
            }
        });

        finesUpdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTablesData();
            }
        });

        finesDeleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = finesTable.getSelectedRow();
                    if (row != -1) {
                        databaseTools.deleteFine((int) finesTable.getValueAt(row, 0));
                        updateTablesData();
                    }
                } catch (java.sql.SQLException err) {
                    JOptionPane.showMessageDialog(frame, err.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        finesPayedBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = finesTable.getSelectedRow();
                    if (row != -1) {
                        databaseTools.setFinePaid((int)finesTable.getValueAt(row, 0));
                        updateTablesData();
                    }
                } catch (java.sql.SQLException err) {
                    JOptionPane.showMessageDialog(frame, err.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void addWindowListeners() {
        frame.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    connection.close();
                } catch (java.sql.SQLException err) {
                    JOptionPane.showMessageDialog(frame, err.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void createUIComponents() {
        databaseTools = new DatabaseTools(connection);
        createFinesTable();
        createCarsTable();
        createOwnersTable();
        updateTablesData();
    }

    public void updateTablesData() {
        try {
            databaseTools.updateData();
            fillFinesTable();
            fillOwnersTable();
            fillCarsTable();
        }
        catch (java.sql.SQLException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createOwnersTable() {
        ownersTableModel = new OwnersTableModel();
        ownersTable = new JTable(ownersTableModel);
        ownersTable.setDragEnabled(false);
        ownersTable.getTableHeader().setReorderingAllowed(false);
        ownersTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ownersTable.setAutoCreateRowSorter(true);
    }

    private void fillOwnersTable() {
        ownersTableModel.repaint();
        databaseTools.getOwners().forEach((k, v) -> ownersTableModel.addRow(new Object[]{
                v.toString(),
                v.getBirthday(),
                v.getCity(),
                v.getDrivingLicence().getDrivingLicenceNumber(),
                v.getDrivingLicence().getIssueDate(),
                v.getDrivingLicence().getExpirationDate(),
                v.getDrivingLicence().getCategories(),
                v.getDrivingLicence().getIssueDepartment()
        }));
    }

    private void createCarsTable() {
        carsTableModel = new CarsTableModel();
        carsTable = new JTable(carsTableModel);
        carsTable.setDragEnabled(false);
        carsTable.getTableHeader().setReorderingAllowed(false);
        carsTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carsTable.setAutoCreateRowSorter(true);
    }

    private void fillCarsTable() {
        carsTableModel.repaint();
        databaseTools.getCars().forEach((k, v) -> carsTableModel.addRow(new Object[] {
                v.getStateNumber(),
                v.getModel(),
                v.getBrand(),
                v.getColor(),
                v.getOwner().toString()
        }));
    }

    private void createFinesTable() {
        finesTableModel = new FinesTableModel();
        finesTable = new JTable(finesTableModel);
        finesTable.setDragEnabled(false);
        finesTable.getTableHeader().setReorderingAllowed(false);
        finesTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        finesTable.setAutoCreateRowSorter(true);
    }

    private void fillFinesTable() {
        finesTableModel.repaint();
        databaseTools.getFines().forEach((k, v) -> finesTableModel.addRow(new Object[]{
                v.getFineId(),
                v.getOwner().toString(),
                v.getDrivingLicence().getDrivingLicenceNumber(),
                v.getCar().getStateNumber(),
                v.getFineDate(),
                v.getFineType().getName(),
                v.getFineType().getFineValue(),
                v.isDeprivation(),
                v.isPaid()
        }));
    }
}