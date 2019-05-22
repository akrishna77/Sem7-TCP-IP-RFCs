import java.io.*;
import java.net.*;

public class Client {
	public static void main(String a[]) throws IOException {
		try {
			Socket con = new Socket("localHost", 95);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			PrintWriter out = new PrintWriter(con.getOutputStream(), true);
			BufferedReader din = new BufferedReader(new InputStreamReader(
					System.in));
			String tocl = din.readLine();
			out.println(tocl);
			while (true) {
				String s1 = in.readLine();
				if (s1 == null)
					continue;
				System.out.println(s1);
				if (s1.equalsIgnoreCase("REJECT")) {
					try {
						Socket con1 = new Socket("localHost", 70);
						PrintWriter out1 = new PrintWriter(
								con1.getOutputStream(), true);
						out1.println("REJECT");
						Socket con2 = new Socket("localHost", 170);
						PrintWriter out2 = new PrintWriter(
								con2.getOutputStream(), true);
						out2.println("REJECT");
						break;
					} catch (ConnectException e) {
					}
				}
				if (s1.equalsIgnoreCase("RESV"))
					System.out.println("SUCCESS");
			}
			in.close();
			out.close();
			con.close();
		} catch (UnknownHostException e) {
		}
	}
}