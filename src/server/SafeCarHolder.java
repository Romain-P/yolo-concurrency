package server;

import protocol.objects.Car;
import protocol.objects.CarMake;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SafeCarHolder {
    private static SafeCarHolder instance;
    private final List<Car> cars;

    public SafeCarHolder() {
        this.cars = new ArrayList<>();
    }

    public synchronized void addCars(Car[] cars) {
        for (Car car: cars)
            this.cars.add(car);
    }

    public synchronized boolean sellCar(String registration) {
        Optional<Car> found = cars.stream().filter(x -> x.getRegistration().equalsIgnoreCase(registration)).findFirst();

        if (found.isPresent()) {
            Car car = found.get();

            if (car.isForSale()) {
                found.get().setForSale(false);
                return true;
            } else
                return false;
        } else {
            return false;
        }
    }

    public synchronized List<Car> carsForSale(CarMake make) {
        return cars.stream().filter(x -> x.isForSale() && x.getMake() == make).collect(Collectors.toList());
    }

    public synchronized List<Car> carsForSale() {
        return new ArrayList<>(cars);
    }

    public static SafeCarHolder instance() {
        if (instance == null)
            instance = new SafeCarHolder();
        return instance;
    }
}
