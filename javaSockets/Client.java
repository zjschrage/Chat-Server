import java.io.*;
import java.net.*;
import java.util.Optional;

public class Client {
	
	private Socket socket = null;
	private BufferedReader input = null;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	private ClientInformation info = null;
	private boolean running = true;

	public Client(String address, int port) {
		establishConnection(address, port);
		info = new ClientInformation();
		new Thread(new ClientReciever(in, info)).start();
		handleInput();
		close();
	}

	private void establishConnection(String address, int port) {
		try {
			socket = new Socket(address, port);
			System.out.println("Connected");
			input = new BufferedReader(new InputStreamReader(System.in));
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		} catch (UnknownHostException u) {
			System.out.println(u);
			return;
		} catch (IOException e) {
			System.out.println(e);
			return;
		}
	}

	private void handleInput() {
		String line = "";
		while (running) {
			try {
                if (input.ready()) {
                    line = input.readLine();
					String[] args = line.split(Constants.COMMAND_DELIMITER);
					String cmd = args[0];
					if (isCommand(cmd)) {
						handleCommands(args);
					} 
					else {
						formatError();
					}
                }
			} catch (IOException e) {
				System.out.println(e);
				break;
			}
		}
	}

	private void send(Message m) throws IOException {
		out.write(m.streamify(), 0, Message.MAX_MESSAGE_LEN);
	}

	private boolean isCommand(String s) {
		s = s.trim();
		for (Command c : Command.values) {
			if (c.toString().equals(s)) return true;
		}
		return false;
	}

	private void handleCommands(String[] args) throws IOException {
		Command cmd = Command.valueOf(args[0]);
		switch (cmd) {
            case ls -> cmdList();
            case send -> cmdSend(args);
			case close -> cmdClose();
			default -> throw new IllegalArgumentException("Unexpected value: " + cmd);
        }
	}

	private void cmdList() throws IOException {
		Message m = new Message();
		m.src = (byte) info.id;
		m.type = (byte) MessageType.LIST.ordinal();
		send(m);
	}

	private void cmdSend(String[] args) throws IOException {
		if (args.length < 2) {
			formatError();
			return;
		}
		Message m = new Message();
		m.src = (byte) info.id;
		try {
			MessageType mt = getMessageType(args[1]);
			m.type = (byte) mt.ordinal();
			int msgStartsAt = 2;
			String msg = "";
			switch(mt) {
				case UNICAST:
					m.dst = Byte.parseByte(args[1]);
					break;
				case BROADCAST:
					m.dst = (byte) -1;
					break;
				default:
					formatError();
					return;
			}
			for (int i = msgStartsAt; i < args.length; i++) {
				msg += args[i] + " ";
			}
			m.message = msg.getBytes();
			send(m);
		} catch (IllegalArgumentException e) {
			formatError();
			return;
		}
	}

	private void cmdClose() throws IOException {
		Message m = new Message();
		m.src = (byte) info.id;
		m.dst = (byte) info.id;
		m.type = (byte) MessageType.CLOSE.ordinal();
		send(m);
		running = false;
	}

	private void formatError() {
		System.out.println(Constants.MESSAGE_FORMAT_ERR);
		System.out.println(Constants.MESSAGE_FORMAT);
	}

	private MessageType getMessageType(String s) {
		if (s.trim().equals("all")) return MessageType.BROADCAST;
		else return MessageType.UNICAST;
	}

	private void close() {
		try {
			input.close();
			in.close();
			out.close();
			socket.close();
		}
		catch (IOException e) {
			System.out.println(e);
		}
	}

	public static void main(String args[]) {
		new Client(args[0], 5000);
	}
}
