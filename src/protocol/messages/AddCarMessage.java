package protocol.messages;

import protocol.Message;
import protocol.objects.Car;

import java.io.Serializable;

public class AddCarMessage extends Message implements Serializable {
    public  static final int protocolId = 0;
    private final Car[] car;

    public AddCarMessage(Car... car) {
        super(protocolId);
        this.car = car;

        System.out.println("Add a Car");
    }

    public Car[] getCars() {
        return car;
    }
}
