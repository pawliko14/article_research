package Objetcs;

import java.util.Objects;

public class Machine_Structure_Detail {

    private String ID;
    private String MACHINEMUBER;
    private String PARENTARTICLE;
    private String CHILDARTICLE;
    private String Quantity;
    private String type;
    private  int level;

    public Machine_Structure_Detail(String ID, String MACHINEMUBER, String PARENTARTICLE, String CHILDARTICLE, String quantity, String type, int level) {
        this.ID = ID;
        this.MACHINEMUBER = MACHINEMUBER;
        this.PARENTARTICLE = PARENTARTICLE;
        this.CHILDARTICLE = CHILDARTICLE;
        this.Quantity = quantity;
        this.type = type;
        this.level = level;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMACHINEMUBER() {
        return MACHINEMUBER;
    }

    public void setMACHINEMUBER(String MACHINEMUBER) {
        this.MACHINEMUBER = MACHINEMUBER;
    }

    public String getPARENTARTICLE() {
        return PARENTARTICLE;
    }

    public void setPARENTARTICLE(String PARENTARTICLE) {
        this.PARENTARTICLE = PARENTARTICLE;
    }

    public String getCHILDARTICLE() {
        return CHILDARTICLE;
    }

    public void setCHILDARTICLE(String CHILDARTICLE) {
        this.CHILDARTICLE = CHILDARTICLE;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    @Override
    public String toString() {
        return "Machine_Structure_Detail{" +
                "ID='" + ID + '\'' +
                ", MACHINEMUBER='" + MACHINEMUBER + '\'' +
                ", PARENTARTICLE='" + PARENTARTICLE + '\'' +
                ", CHILDARTICLE='" + CHILDARTICLE + '\'' +
                ", Quantity='" + Quantity + '\'' +
                ", type='" + type + '\'' +
                ", level=" + level +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Machine_Structure_Detail that = (Machine_Structure_Detail) o;
        return level == that.level &&
                Objects.equals(ID, that.ID) &&
                Objects.equals(MACHINEMUBER, that.MACHINEMUBER) &&
                Objects.equals(PARENTARTICLE, that.PARENTARTICLE) &&
                Objects.equals(CHILDARTICLE, that.CHILDARTICLE) &&
                Objects.equals(Quantity, that.Quantity) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, MACHINEMUBER, PARENTARTICLE, CHILDARTICLE, Quantity, type, level);
    }
}
