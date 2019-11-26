package protocol.messages;

import protocol.Message;
import protocol.objects.CarMake;

import java.io.Serializable;

public class RequestInformationMessage extends Message implements Serializable {
    public static final int protocolId = 2;
    private final CarMake make;
    private final RequestType type;

    public RequestInformationMessage(RequestType type, CarMake make) {
        super(protocolId);
        this.make = make;
        this.type = type;

        switch (type) {
            case SEARCH_ONE:
                System.out.println("Search By Make");
                break;
            case SEARCH_TOTAL:
                System.out.println("Search Total Value");
                break;
            case SEARCH_ALL:
                System.out.println("Cars for sale");
                break;
        }
    }

    public enum RequestType {
        SEARCH_ONE,
        SEARCH_ALL,
        SEARCH_TOTAL
    }

    public CarMake getMake() {
        return this.make;
    }

    public RequestType type() {
        return this.type;
    }
}
