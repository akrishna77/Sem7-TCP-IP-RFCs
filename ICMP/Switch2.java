import java.io.*;
import java.net.*;

public class Switch2 {
	public static void main(String a[]) throws IOException {
		try {
			int bandwidth = 100, flag = 0, number = 0, i = 1;
			String input = null;
			ServerSocket s = new ServerSocket(595);
			System.out.println("Switch2 Waiting For The Switch1");
			Socket cs = s.accept();
			InetAddress ia = cs.getInetAddress();
			String cli = ia.getHostAddress();
			System.out.println("Connected to the Switch1 with IP:" + cli);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					cs.getInputStream()));
			PrintWriter out = new PrintWriter(cs.getOutputStream(), true);
			BufferedReader din = new BufferedReader(new InputStreamReader(
					System.in));

			Socket con = new Socket("localHost", 6595);
			BufferedReader in1 = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			PrintWriter out1 = new PrintWriter(con.getOutputStream(), true);
			String ready = in.readLine();
			while (!ready.equalsIgnoreCase("arvind"))
				ready = in.readLine();
			System.out.println("arvind");
			out1.println("arvind");
			do {

				String st = in1.readLine();
				if (st == null)
					continue;
				System.out.println(st);
				if (st.equalsIgnoreCase("RESV")) {
					flag = 2;
					break;
				}
				if (st.equalsIgnoreCase("REJECT")) {
					flag = 1;
					break;
				}
			} while (true);

			if (flag == 2) {
				System.out.println("Enter required bandwidth(QoS):");
				input = din.readLine();
				number = Integer.parseInt(input);
				if (number < bandwidth) {
					System.out.println("RESV");
					out.println("RESV");
					ServerSocket s1 = new ServerSocket(170);
					Socket cs1 = s1.accept();
					BufferedReader in2 = new BufferedReader(
							new InputStreamReader(cs1.getInputStream()));
					String st1 = in2.readLine();
					while (st1 == null)
						st1 = in2.readLine();
					System.out.println(st1);
				} else {
					System.out.println("REJECT");
					out.println("REJECT");
				}

			}
			if (flag == 1) {
				System.out.println("REJECT");
				out.println("REJECT");
			}
			in.close();
			out.close();
			cs.close();
			in1.close();
			out1.close();
			con.close();

		} catch (UnknownHostException e) {
		} catch (IOException e) {
		}
	}
}