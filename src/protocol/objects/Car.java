package protocol.objects;

import java.io.Serializable;
import java.util.UUID;

public class Car implements Serializable {
    private String registration;
    private CarMake make;
    private int price;
    private int mileage;
    private boolean forSale;

    public Car(String registration, CarMake make, int price, int mileage, boolean forSale) {
        this.registration = registration;
        this.make = make;
        this.price = price;
        this.mileage = mileage;
        this.forSale = forSale;
    }

    public Car(CarMake make, int price, int mileage) {
        this.make = make;
        this.price = price;
        this.mileage = mileage;
    }

    @Override
    public String toString() {
        return "[registration = " + registration +
                ", make = " + make.name() +
                ", price = " + price +
                ", mileage = " + mileage +
                ", forSale = " + forSale +
                "]";
    }

    public String getRegistration() {
        return registration;
    }

    public CarMake getMake() {
        return make;
    }

    public int getPrice() {
        return price;
    }

    public int getMileage() {
        return mileage;
    }

    public boolean isForSale() {
        return forSale;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public void setMake(CarMake make) {
        this.make = make;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void setForSale(boolean forSale) {
        this.forSale = forSale;
    }
}
