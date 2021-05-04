package coursework.database;

import coursework.database.entity.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;

public class DatabaseTools {
    private Connection connection;
    private HashMap<Integer, Owner> owners;
    private HashMap<Integer, Car> cars;
    private HashMap<Integer, Fine> fines;
    private HashMap<String, DrivingLicence> drivingLicences;
    private static HashMap<Integer, FineType> fineTypes = new HashMap<>();

    public DatabaseTools(Connection connection) {
        this.connection = connection;
        owners = new HashMap<>();
        cars = new HashMap<>();
        fines = new HashMap<>();
        drivingLicences = new HashMap<>();
    }

    public void updateData() throws java.sql.SQLException {
        loadFineTypes();
        loadOwnersAndDrivingLicences();
        loadCars();
        loadFines();
    }

    private void loadFines() throws java.sql.SQLException{
        fines.clear();
        final String query = "SELECT * FROM Fines";
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery(query);

        while (res.next()) {
            int fineId = res.getInt(1);
            FineType fineType = fineTypes.get(res.getInt(2));
            Date fineDate = res.getDate(3);
            String drivingLicenceNumber = res.getString(4);
            DrivingLicence drivingLicence = drivingLicences.get(drivingLicenceNumber);
            String stateNumber = res.getString(5);
            boolean deprivation = res.getBoolean(6);
            boolean isPaid = res.getBoolean(7);

            Owner owner = findOwnerWithLicence(drivingLicenceNumber);
            Car car = findCarWithStateNumber(stateNumber);
            fines.put(fineId, new Fine(fineId, fineType, fineDate, drivingLicence, deprivation, isPaid, owner, car));
        }

        res.close();
        statement.close();
    }

    private void loadCars() throws java.sql.SQLException {
        cars.clear();
        final String query = "SELECT * FROM Cars";
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery(query);

        while (res.next()) {
            int carId = res.getInt(1);
            String model = res.getString(2);
            String brand = res.getString(3);
            String color = res.getString(4);
            String stateNumber = res.getString(5);
            cars.put(carId, new Car(carId, model, brand, color, stateNumber, findCarOwnerWithCarId(carId)));
        }
    }

    private void loadOwnersAndDrivingLicences() throws java.sql.SQLException{
        owners.clear();
        drivingLicences.clear();
        final String query = "SELECT * FROM Owners";
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery(query);

        while (res.next()) {
            int ownerId = res.getInt(1);
            String lastName = res.getString(2);
            String firstName = res.getString(3);
            String middleName = res.getString(4);
            Date birthday = res.getDate(5);
            String city = res.getString(6);
            String drivingLicenceNumber = res.getString(7);

            DrivingLicence drivingLicence = findDrivingLicenceWithNumber(drivingLicenceNumber);
            Owner owner = new Owner(ownerId, lastName, firstName, middleName, birthday, city, drivingLicence);
            owners.put(ownerId, owner);
            drivingLicences.put(drivingLicenceNumber, drivingLicence);
        }

        res.close();
        statement.close();
    }

    @Nullable
    private Car findCarWithStateNumber(String stateNumber) throws java.sql.SQLException{
        final String query = "SELECT * FROM Cars WHERE StateNumber = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, stateNumber);
        ResultSet res = statement.executeQuery();
        if (res.next()) {
            int carId = res.getInt(1);

            res.close();
            statement.close();
            return cars.get(carId);
        }

        res.close();
        statement.close();
        return null;
    }

    @Nullable
    private Owner findOwnerWithLicence(String drivingLicenceNumber) throws java.sql.SQLException{
        final String query = "SELECT * FROM Owners WHERE DrivingLicenceNumber = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, drivingLicenceNumber);
        ResultSet res = statement.executeQuery();
        if (res.next()) {
            int ownerId = res.getInt(1);

            res.close();
            statement.close();
            return owners.get(ownerId);
        }

        res.close();
        statement.close();
        return null;
    }

    @Nullable
    private Owner findCarOwnerWithCarId(int carId) throws java.sql.SQLException {
        final String query = "SELECT * FROM OwnerCarInt WHERE CarId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, carId);
        ResultSet res = statement.executeQuery();
        if (res.next()) {
            int ownerId = res.getInt(2);

            res.close();
            statement.close();
            return owners.get(ownerId);
        }

        res.close();
        statement.close();
        return null;
    }

    @Nullable
    private DrivingLicence findDrivingLicenceWithNumber(String drivingLicenceNumber) throws java.sql.SQLException {
        final String query = "SELECT * FROM DrivingLicences WHERE DrivingLicenceNumber = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, drivingLicenceNumber);
        ResultSet res = statement.executeQuery();
        if (res.next()) {
            Date issueDate = res.getDate(2);
            Date expirationDate = res.getDate(3);
            String categories = res.getString(4);
            String issueDepartment = res.getString(5);

            res.close();
            statement.close();
            return new DrivingLicence(drivingLicenceNumber, issueDate, expirationDate, categories, issueDepartment);
        }

        res.close();
        statement.close();
        return null;
    }

    private void loadFineTypes() throws java.sql.SQLException{
        final String query = "SELECT * FROM FinesType";
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery(query);
        while (res.next()) {
            int fineTypeId = res.getInt(1);
            String name = res.getString(2);
            int fineValue = res.getInt(3);
            fineTypes.putIfAbsent(fineTypeId, new FineType(fineTypeId, name, fineValue));
        }
        res.close();
        statement.close();
    }

    public void deleteFine(int fineId) throws java.sql.SQLException {
        final String query = "DELETE FROM Fines WHERE FineId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, fineId);
        statement.executeUpdate();
        statement.close();
    }

    public void setFinePaid(int fineId) throws java.sql.SQLException {
        final String query = "UPDATE Fines SET Paid = ? WHERE FineId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setBoolean(1, true);
        statement.setInt(2, fineId);
        statement.executeUpdate();
        statement.close();
    }

    public void deleteCar (String stateNumber) throws java.sql.SQLException {
        final String checkQuery = "SELECT * FROM Fines WHERE StateNumber = ? AND Paid = 0";
        PreparedStatement statement = connection.prepareStatement(checkQuery);
        statement.setString(1, stateNumber);
        ResultSet res = statement.executeQuery();
        int rowCount = 0;
        if (res.last())
            rowCount = res.getRow();
        res.close();
        statement.close();
        if (rowCount != 0)
            throw new java.sql.SQLException("Нельзя удалить данный автомобиль, т.к. на него записаны неоплаченные штрафы");

        final String query = "DELETE FROM Cars WHERE StateNumber = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1, stateNumber);
        statement.executeUpdate();
        statement.close();
    }

    public void deleteOwner(String drivingLicenceNumber) throws java.sql.SQLException {
        final String query = "DELETE FROM Owners WHERE DrivingLicenceNumber = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, drivingLicenceNumber);
        statement.executeUpdate();
        statement.close();
        deleteDrivingLicence(drivingLicenceNumber);
        Owner owner = getOwnerWithDrivingLicenceNumber(drivingLicenceNumber);
        if (owner != null) {
            deleteOwnersCars(owner);
        }
    }

    @Nullable
    private Owner getOwnerWithDrivingLicenceNumber(String drivingLicenceNumber) {
        for (int key : owners.keySet())
            if (owners.get(key).getDrivingLicence().getDrivingLicenceNumber().equals(drivingLicenceNumber))
                return owners.get(key);
        return null;
    }

    @Nullable
    private Car getCarWithStateNumber(String stateNumber) {
        for (int key : cars.keySet())
            if (cars.get(key).getStateNumber().equals(stateNumber))
                return  cars.get(key);
        return null;
    }

    public void insertCar(String stateNumber, String model, String brand, String color,
                           String drivingLicenceNumber) throws java.sql.SQLException {
        final String carQuery = "INSERT INTO Cars (Model, Brand, Color, StateNumber) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(carQuery);
        statement.setString(1, model);
        statement.setString(2, brand);
        statement.setString(3, color);
        statement.setString(4, stateNumber);
        statement.executeUpdate();
        statement.close();
        updateData();

        Owner owner = getOwnerWithDrivingLicenceNumber(drivingLicenceNumber);
        Car car = getCarWithStateNumber(stateNumber);
        final String intQuery = "INSERT INTO OwnerCarInt (CarId, OwnerId) VALUES (?, ?)";
        statement = connection.prepareStatement(intQuery);
        statement.setInt(1, car.getCarId());
        statement.setInt(2, owner.getOwnerId());
        statement.executeUpdate();
        statement.close();
    }

    private void deleteOwnersCars(@NotNull Owner owner) throws java.sql.SQLException {
        int ownerId = owner.getOwnerId();
        for (int key : cars.keySet()) {
            if (cars.get(key).getOwner().getOwnerId() == ownerId) {
                Car car = cars.get(key);
                deleteCar(car.getCarId());
            }
        }
    }

    private void deleteCar(int carId) throws java.sql.SQLException {
        final String query = "DELETE FROM Cars WHERE CarId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, carId);
        statement.executeUpdate();
        statement.close();
    }

    private void deleteDrivingLicence(String drivngLicenceNumber) throws java.sql.SQLException {
        final String query = "DELETE FROM DrivingLicences WHERE DrivingLicenceNumber = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, drivngLicenceNumber);
        statement.executeUpdate();
        statement.close();
    }

    public void insertDrivingLicence(String drivingLicenceNumber, Date issueDate, Date expirationDate,
                                      String categories, String issueDepartment) throws java.sql.SQLException {
        final String query = "INSERT INTO DrivingLicences (DrivingLicenceNumber, IssueDate, ExpirationDate, " +
                "Categories, IssueDepartment) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, drivingLicenceNumber);
        statement.setDate(2, new java.sql.Date(issueDate.getTime()));
        statement.setDate(3, new java.sql.Date(expirationDate.getTime()));
        statement.setString(4, categories);
        statement.setString(5, issueDepartment);
        statement.executeUpdate();
        statement.close();
    }

    public void insertOwner(String lastName, String firstName, String middleName, Date birthday, String city,
                             String drivingLicenceNumber) throws java.sql.SQLException {
        final String query = "INSERT INTO Owners (LastName, FirstName, MiddleName, Birthday, City, " +
                "DrivingLicenceNumber) VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, lastName);
        statement.setString(2, firstName);
        statement.setString(3, middleName);
        statement.setDate(4, new java.sql.Date(birthday.getTime()));
        statement.setString(5, city);
        statement.setString(6, drivingLicenceNumber);
        statement.executeUpdate();
        statement.close();
    }

    public void insertFine(FineType type, Date fineDate, String licenceNumber, String stateNumber, boolean deprivation)
            throws java.sql.SQLException {
        final String query = "INSERT INTO Fines (FineTypeId, FineDate, DriverLicenceNumber, StateNumber, Deprivation)" +
                " VALUES(?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, type.getTypeId());
        statement.setDate(2, new java.sql.Date(fineDate.getTime()));
        statement.setString(3, licenceNumber);
        statement.setString(4, stateNumber);
        statement.setBoolean(5, deprivation);
        statement.executeUpdate();
        statement.close();

        Owner owner = getOwnerWithDrivingLicenceNumber(licenceNumber);
        int fineId = getLastInsertedFineId();
        final String intQuery = "INSERT INTO OwnerFineInt (FineId, OwnerId) VALUES(?, ?)";
        statement = connection.prepareStatement(intQuery);
        statement.setInt(1, fineId);
        statement.setInt(2, owner.getOwnerId());
        statement.executeUpdate();
        statement.close();
    }

    private int getLastInsertedFineId() throws java.sql.SQLException {
        final String query = "select LAST_INSERT_ID()";
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery(query);
        if (res.next())
            return res.getInt(1);
        return -1;
    }

    public boolean containsDrivingLicenceNumber(String drivingLicenceNumber) throws java.sql.SQLException {
        final String query = "SELECT * FROM DrivingLicences WHERE DrivingLicenceNumber = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, drivingLicenceNumber);
        ResultSet res = statement.executeQuery();
        int rowCount = 0;
        if (res.last())
            rowCount = res.getRow();
        res.close();
        statement.close();

        return rowCount != 0;
    }

    public boolean containsStateNumber(String stateNumber) throws java.sql.SQLException {
        final String query = "SELECT * FROM Cars WHERE StateNumber = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, stateNumber);
        ResultSet res = statement.executeQuery();
        int rowCount = 0;
        if (res.last())
            rowCount = res.getRow();
        res.close();
        statement.close();

        return rowCount != 0;
    }

    public HashMap<Integer, Owner> getOwners() {
        return owners;
    }

    public HashMap<Integer, Car> getCars() {
        return cars;
    }

    public HashMap<Integer, Fine> getFines() {
        return fines;
    }

    public HashMap<String, DrivingLicence> getDrivingLicences() {
        return drivingLicences;
    }

    @Contract(pure = true)
    public static HashMap<Integer, FineType> getFineTypes() {
        return fineTypes;
    }
}