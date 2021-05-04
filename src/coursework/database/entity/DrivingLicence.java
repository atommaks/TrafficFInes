package coursework.database.entity;

import java.util.Date;

public class DrivingLicence {
    private String drivingLicenceNumber;
    private Date issueDate;
    private Date expirationDate;
    private String categories;
    private String issueDepartment;

    public DrivingLicence(String drivingLicenceNumber, Date issueDate, Date expirationDate, String categories,
                          String issueDepartment) {
        this.drivingLicenceNumber = drivingLicenceNumber;
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
        this.categories = categories;
        this.issueDepartment = issueDepartment;
    }

    public String getDrivingLicenceNumber() {
        return drivingLicenceNumber;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public String getCategories() {
        return categories;
    }

    public String getIssueDepartment() {
        return issueDepartment;
    }
}