package protocol.messages;

import protocol.Message;
import protocol.objects.Car;

import java.io.Serializable;
import java.util.List;

public class RequestResponseMessage extends Message implements Serializable {
    public static final int protocolId = 3;
    private final String message;
    private final List<Car> cars;

    public RequestResponseMessage(String format, Object... args) {
        super(protocolId);
        this.message = String.format(format, args);
        this.cars = null;
    }

    public RequestResponseMessage(List<Car> cars, String format, Object... args) {
        super(protocolId);
        this.message = String.format(format, args);
        this.cars = cars;
    }

    public RequestResponseMessage(List<Car> cars) {
        super(protocolId);
        this.message = null;
        this.cars = cars;
    }

    public String getMessage() {
        return this.message;
    }

    public List<Car> getCars() {
        return this.cars;
    }
}
