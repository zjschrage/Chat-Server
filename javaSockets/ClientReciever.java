import java.io.DataInputStream;
import java.io.IOException;

public class ClientReciever implements Runnable {

    private DataInputStream in;
    private ClientInformation info;

    public ClientReciever(DataInputStream in, ClientInformation info) {
        this.in = in;
        this.info = info;
    }

    @Override
    public void run() {
        byte[] stream;
        boolean running = true;
		while (running) {
			try {
                stream = in.readNBytes(Message.MAX_MESSAGE_LEN);
                running = interpret(stream);
			} catch (IOException e) {
				e.printStackTrace();
                break;
			}
		}
    }

    private boolean interpret(byte[] stream) throws IOException {
        Message m = new Message(stream);
        String prefix = "";
        switch (m.getType()) {
            case CLOSE:
                return false;
            case ASSIGN_ID:
                info.id = m.dst;
                break;
            case UNICAST:
                prefix += "PRIVATE MESSAGE from ";
            default:
                if (m.src < 0) prefix += "SERVER: ";
                else prefix += m.src + ": ";
                String msg = new String(m.message);
                System.out.println(prefix + msg);
        }
        return true;
    }
    
}
