import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;

public class Server {

	private List<Socket> sockets = null;
	private Map<Byte, User> users = null;
	private ServerSocket server = null;
	private DataInputStream serverIn = null;
	private DataOutputStream serverOut = null;
	private boolean accepting = true;
	private static byte idCounter = 0;

	public Server(int port) {

		sockets = new ArrayList<>();
		users = new HashMap<>();
		try {
			InetAddress ip = InetAddress.getLocalHost();
			server = new ServerSocket(port, 0, ip);
			System.out.println("Server started at " + ip.getHostAddress());

			while (accepting) {
				System.out.println("Waiting for a client ...");
				Socket socket = server.accept();
				sockets.add(socket);
				
				System.out.println("Client accepted");
				serverIn = new DataInputStream(socket.getInputStream());
				serverOut = new DataOutputStream(socket.getOutputStream());
				
				User user = new User(serverIn, serverOut, users, idCounter);
				users.put(idCounter++, user);
				new Thread(user).start();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {
		new Server(5000);
	}
}
