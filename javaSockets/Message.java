import java.util.Arrays;

public class Message {

    public static final int HDR_ENTRIES = 3;
    public static final int MAX_MESSAGE_LEN = 128;

    public byte src;
    public byte dst;
    public byte type;
    public byte[] message;

    public Message() {
        message = new byte[0];
    }

    public Message(byte[] bstream) {
        this.src = bstream[0];
        this.dst = bstream[1];
        this.type = bstream[2];
        this.message = Arrays.copyOfRange(bstream, HDR_ENTRIES, bstream.length);
    }

    public byte[] streamify() {
        byte[] stream = new byte[MAX_MESSAGE_LEN];
        stream[0] = src;
        stream[1] = dst;
        stream[2] = type;
        for (int i = 0; i < Math.min(message.length, MAX_MESSAGE_LEN - HDR_ENTRIES); i++) {
            stream[i + HDR_ENTRIES] = message[i];
        }
        return stream;
    }

    public MessageType getType() {
        return MessageType.values[type];
    }
    
}
