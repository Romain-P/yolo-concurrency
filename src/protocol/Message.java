package protocol;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private final int protocolId;

    protected Message(int protocolId) {
        this.protocolId = protocolId;
    }

    public int protocolId() {
        return protocolId;
    }
}
