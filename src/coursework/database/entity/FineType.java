package coursework.database.entity;

public class FineType {
    private int typeId;
    private String name;
    private int fineValue;

    public FineType(int typeId, String name, int fineValue) {
        this.typeId = typeId;
        this.name = name;
        this.fineValue = fineValue;
    }

    public String getName() {
        return name;
    }

    public int getFineValue() {
        return fineValue;
    }

    public int getTypeId() {
        return typeId;
    }

    @Override
    public String toString() {
        return name;
    }
}
