package coursework.database.entity;

public class Car {
    private int carId;
    private String model;
    private String brand;
    private String color;
    private String stateNumber;
    private Owner owner;

    public Car(int carId, String model, String brand, String color, String stateNumber, Owner owner) {
        this.carId = carId;
        this.model = model;
        this.brand = brand;
        this.color = color;
        this.stateNumber = stateNumber;
        this.owner = owner;
    }

    public int getCarId() {
        return carId;
    }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public String getStateNumber() {
        return stateNumber;
    }

    public Owner getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return stateNumber;
    }
}
