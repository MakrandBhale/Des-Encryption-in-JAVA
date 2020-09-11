import java.io.Serializable;

public class MessagePacket implements Serializable {
    String message, key;

    public MessagePacket(String message, String key) {
        this.message = message;
        this.key = key;
    }
}
