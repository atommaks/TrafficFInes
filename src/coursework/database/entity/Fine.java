package coursework.database.entity;

import java.util.Date;

public class Fine {
    private int fineId;
    private FineType fineType;
    private Date fineDate;
    private DrivingLicence drivingLicence;
    private boolean deprivation;
    private boolean isPaid;
    private Owner owner;
    private Car car;

    public Fine(int fineId, FineType fineType, Date fineDate, DrivingLicence drivingLicence, boolean deprivation,
                boolean isPaid, Owner owner, Car car) {
        this.fineId = fineId;
        this.fineType = fineType;
        this.fineDate = fineDate;
        this.drivingLicence = drivingLicence;
        this.deprivation = deprivation;
        this.isPaid = isPaid;
        this.owner = owner;
        this.car = car;
    }

    public int getFineId() {
        return fineId;
    }

    public FineType getFineType() {
        return fineType;
    }

    public Date getFineDate() {
        return fineDate;
    }

    public DrivingLicence getDrivingLicence() {
        return drivingLicence;
    }

    public boolean isDeprivation() {
        return deprivation;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public Owner getOwner() {
        return owner;
    }

    public Car getCar() {
        return car;
    }
}
