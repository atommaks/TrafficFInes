package coursework.database.entity;

import java.util.Date;

public class Owner {
    private int ownerId;
    private String lastName, firstName, middleName;
    private Date birthday;
    private String city;
    private DrivingLicence drivingLicence;

    public Owner(int ownerId, String lastName, String firstName, String middleName, Date birthday, String city,
                 DrivingLicence drivingLicence) {
        this.ownerId = ownerId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthday = birthday;
        this.city = city;
        this.drivingLicence = drivingLicence;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getCity() {
        return city;
    }

    public DrivingLicence getDrivingLicence() {
        return drivingLicence;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", lastName, firstName, middleName);
    }
}
