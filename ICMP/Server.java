import java.io.*;
import java.net.*;

public class Server {
	public static void main(String a[]) throws IOException {
		try {
			int number = 0;
			int bandwidth = 100;
			String input = null;
			ServerSocket s = new ServerSocket(6595);
			System.out.println("Server Waiting For The Switch2");
			Socket cs = s.accept();
			InetAddress ia = cs.getInetAddress();
			String cli = ia.getHostAddress();
			System.out.println("Connected to the Switch2 with IP:" + cli);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					cs.getInputStream()));
			PrintWriter out = new PrintWriter(cs.getOutputStream(), true);
			BufferedReader din = new BufferedReader(new InputStreamReader(
					System.in));

			do {

				String st = in.readLine();
				if (st == null)
					continue;

				if (st.equalsIgnoreCase("arvind")) {
					System.out.println("arvind");
					System.out.println("Enter required bandwidth(QoS):");
					input = din.readLine();
					number = Integer.parseInt(input);
					if (number < bandwidth) {
						System.out.println("RESV");
						out.println("RESV");
						ServerSocket s1 = new ServerSocket(70);
						Socket cs1 = s1.accept();
						BufferedReader in1 = new BufferedReader(
								new InputStreamReader(cs1.getInputStream()));
						String st1 = in1.readLine();
						while (st1 == null)
							st1 = in1.readLine();
						System.out.println(st1);

					} else {
						System.out.println("REJECT");
						out.println("REJECT");
					}

				}

			} while (true);

		} catch (IOException e) {
		}
	}
}