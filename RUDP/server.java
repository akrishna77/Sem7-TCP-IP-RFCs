import java.io.*;
import java.net.*;
import java.util.*;
public class server 
{
	public static void main(String[] args) throws Exception 
		{
			int seq=0,num,ins=0;
			int PORT = 2014,rcvdack,sentack,i;
			byte[] buf = new byte[1000];
			DatagramPacket dgp = new DatagramPacket(buf, buf.length),out;
			DatagramSocket sk;
			int buffer[]=new int[200];
			String outMessage;
			sk = new DatagramSocket(PORT);
			rcvdack= sentack = -1;
			
			while (true) 
			{
				
				while(true)
				{
				sk.setSoTimeout(5000);
    				try {
        			sk.receive(dgp);
				String temp = new String(dgp.getData());
				String tempint="",rcvd="";
				
				for(i=0;temp.charAt(i)!=' ';i++)
					tempint+=temp.charAt(i);
				seq=Integer.parseInt(tempint);
				for(i++;temp.charAt(i)!=' ';i++)
					rcvd+=temp.charAt(i);
				num=Integer.parseInt(rcvd);
				rcvd="";
				ins=1;
				Scanner in=new Scanner(System.in);
				in.nextLine();
				if(Integer.parseInt(tempint)==rcvdack+1)
				{
					rcvdack=Integer.parseInt(tempint);
					System.out.println("Client : "+temp);
					buffer[seq]=9999-num;
					sentack+=1;
					outMessage=String.valueOf(sentack)+' '+String.valueOf(buffer[seq])+' '+String.valueOf(ins);
					System.out.print("Server : "+outMessage);
					buf = (outMessage).getBytes();
					out = new DatagramPacket(buf, buf.length, dgp.getAddress(), dgp.getPort());
					sk.send(out);
					System.out.println("sent");
					if(seq%4==0)
					{
						ins++;
						outMessage=String.valueOf(sentack)+' '+String.valueOf(buffer[seq])+' '+String.valueOf(ins);
					System.out.println("Server : "+outMessage);
					buf = (outMessage).getBytes();
					out = new DatagramPacket(buf, buf.length, dgp.getAddress(), dgp.getPort());
					sk.send(out);
					System.out.println("Duplicate message sent");
					}
				}
				else
				{
					outMessage=tempint+' '+String.valueOf(buffer[seq])+' '+String.valueOf(ins);
					System.out.println("Server (Duplicte message): "+outMessage);
					buf = (outMessage).getBytes();
					out = new DatagramPacket(buf, buf.length, dgp.getAddress(), dgp.getPort());
					sk.send(out);
					System.out.println("sent");
				}
				break;
    				} catch (SocketTimeoutException e) {
				if(sentack!=-1)
				{
					ins++;
					outMessage=String.valueOf(sentack)+' '+String.valueOf(buffer[seq])+' '+String.valueOf(ins);
					buf = (outMessage).getBytes();
					out = new DatagramPacket(buf, buf.length, dgp.getAddress(), dgp.getPort());
					sk.send(out);
					System.out.println("Retransmitting message : "+outMessage);
				}
       				continue;
    				}
				}
				
    			}
  		}
}