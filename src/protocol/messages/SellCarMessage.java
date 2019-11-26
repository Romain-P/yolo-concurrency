package protocol.messages;

import protocol.Message;

import java.io.Serializable;

public class SellCarMessage extends Message implements Serializable {
    public static final int protocolId = 1;
    private final String reference;

    public SellCarMessage(String reference) {
        super(protocolId);
        this.reference = reference;

        System.out.println("Sell a Car");
    }

    public String getReference() {
        return this.reference;
    }
}
