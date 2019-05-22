import java.io.*;
import java.net.*;
import java.util.*;
public class client 
{
	public static void main(String[] args) throws Exception 
		{
			int PORT = 2015,rcvdack,sentack,i;
			byte[] buf = new byte[1000];
			DatagramPacket dgp = new DatagramPacket(buf, buf.length),out;
			DatagramSocket sk;
			
			sk = new DatagramSocket(PORT);
			rcvdack = sentack = -1;
			int num,inst;
			String outMessage;	
			while (true) 
			{
				buf=new byte[1000];
				inst=1;
				System.out.print("Client : ");
				Scanner in=new Scanner(System.in);
				num=in.nextInt();
				sentack+=1;
				outMessage = String.valueOf(sentack)+' '+String.valueOf(num)+' '+String.valueOf(inst);
				System.out.println(outMessage);
				buf = outMessage.getBytes();
				out = new DatagramPacket(buf, buf.length, InetAddress.getByName(args[0]),2014);
				sk.send(out);
				System.out.println("sent");				
 
				while(true)
				{
				sk.setSoTimeout(5000);
    				try {
        			sk.receive(dgp);
				String temp = new String(dgp.getData(), 0, dgp.getLength());
				String tempint="",rcvd="",ins="";
				for(i=0;temp.charAt(i)!=' ';i++)
					tempint+=temp.charAt(i);
				for(i++;temp.charAt(i)!=' ';i++)
					rcvd+=temp.charAt(i);
				for(i++;i<temp.length();i++)
					ins+=temp.charAt(i);
				if(Integer.parseInt(tempint)==rcvdack+1)
				{
					rcvdack=Integer.parseInt(tempint);
					System.out.println("Server : "+rcvd+" Instance No : "+ins);
				}
				else
				{
					System.out.println("Packet dropped");
					System.out.println(temp);
					continue;
				}
				break;
    				} catch (SocketTimeoutException e) {
				inst++;
				outMessage = String.valueOf(sentack)+' '+String.valueOf(num)+' '+String.valueOf(inst);
				buf = outMessage.getBytes();
				out = new DatagramPacket(buf, buf.length, InetAddress.getByName(args[0]),2014);
				sk.send(out);
       				System.out.println(outMessage);	
       				continue;
    				}
				}
   			}
  		}
}