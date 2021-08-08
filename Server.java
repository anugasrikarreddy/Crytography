import java.util.*;
import java.util.regex.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author SRIKAR REDDY ANUGA
 */
public class Server {

   
    public static void main(String[] args) throws SocketException, IOException, InterruptedException {
        
         DatagramSocket serverSocket = new DatagramSocket(9876);
         boolean bye=false;
      //int c=5;
      while(true) //instead of c i want to use true
        {
          Scanner in = new Scanner(System.in);
          byte[] receivebuffer = new byte[1024];
          byte[] sendbuffer  = new byte[1024];
          DatagramPacket recvdpkt = new DatagramPacket(receivebuffer, receivebuffer.length);
          serverSocket.receive(recvdpkt);
          InetAddress IP = recvdpkt.getAddress();
          int portno = recvdpkt.getPort();
          String clientdata = new String(recvdpkt.getData());
          clientdata=clientdata.trim();
          System.out.println("\nAlice : "+ clientdata);
          BufferedReader serverRead = new BufferedReader(new InputStreamReader (System.in) );
          String serverdata;
          System.out.print("\nEnter \"y\" if you want Client sever Model or \"n\" for Elgamal Digital Signature [y/n]");
          String read = in.next();
          if(read.equalsIgnoreCase("y")){
            System.out.print("\nBob : ");
            serverdata = serverRead.readLine();
              System.out.println("Sending Message!");
                ProgressBar bar = new ProgressBar();
                bar.update(0, 1000);
                for(int i=0;i<1000;i++) {
                                // do something!
                    for(int j=0;j<100000;j++)
                        for(int p=0;p<100000;p++);
                    // update the progress bar
                    bar.update(i, 1000);
                }
                System.out.println("Message Sent!");
          }
          else{
            System.out.print("\nBob : ");
            ProgressBar bar = new ProgressBar();
            System.out.println("Verifying Message!");

            bar.update(0, 1000);
            for(int i=0;i<1000;i++) {
                            // do something!
                for(int j=0;j<100000;j++)
                    for(int p=0;p<100000;p++);
                // update the progress bar
                bar.update(i, 1000);
            }
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(clientdata);
            
            List<Long> list1 = new ArrayList<Long>();
            
            while (matcher.find()) {
            
            list1.add(Long.parseLong(matcher.group())); // Add the value to the list
            }
            

            verify v1 = new verify(list1.get(0),list1.get(1),list1.get(2),list1.get(3),list1.get(4),list1.get(5));
            boolean s=v1.verified();

        System.out.println("Message Sent!");
            if(s){
            serverdata="Digitally Verified";
            }
            else
            {
              serverdata="Digitally Not Verified";
            }
            
          }
          serverdata=serverdata.trim();
          sendbuffer = serverdata.getBytes();
          DatagramPacket sendPacket = new DatagramPacket(sendbuffer, sendbuffer.length, IP,portno);
          serverSocket.send(sendPacket); 
          //here the check condition for serverdata which must be bye
          if(serverdata.equalsIgnoreCase("bye"))
          {
              System.out.println("connection ended by Bob");
              break;
          }
          
          
      }
        serverSocket.close();
    }
            
    }

