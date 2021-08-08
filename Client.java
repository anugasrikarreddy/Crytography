import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import java.nio.file.Path; 
import java.nio.file.Paths;
import java.util.stream.*;
import java.util.Arrays;
import java.util.Collections;
/**
 *
 * @author SRIKAR REDDY ANUGA
 */

public class Client {
    public static String file()
    {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return "NO _file";
    }
    static int a[]=new int[5];
    public static void main(String[] args) throws SocketException, IOException, InterruptedException {
        
         BufferedReader clientRead =new BufferedReader(new InputStreamReader(System.in));
      
      InetAddress IP = InetAddress.getByName("127.0.0.1");
     //int c=5;
      DatagramSocket clientSocket = new DatagramSocket();
      while(true)    //true
      {
      Scanner sc = new Scanner(System.in);
      byte[] sendbuffer = new byte[1024];
      byte[] receivebuffer = new byte[1024];
      String clientData;
      System.out.print("\nEnter \"y\" if you want Client sever Model or \"n\" for Elgamal Digital Signature [y/n]");
      String read = sc.next();
      if(read.equalsIgnoreCase("y")){
          System.out.print("\nAlice: ");
          clientData=clientRead.readLine();
          ProgressBar bar = new ProgressBar();

        System.out.println("Sending Message!");

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
      else {
        System.out.println("Enter \"y\" to read message and secret values from file else \"n\" for input message ");
          String input2 = sc.next();
          String  first="";
        if(input2.equalsIgnoreCase("y"))
        {
          String m = file();
                        Path path1 = Paths.get(m); 
                  
                            // call getFileName() and get FileName path object 
                        Path fileName = path1.getFileName(); 
                        FileReader reader = new FileReader(fileName.toString());
                        Scanner inScanner = new Scanner(reader);

                        String line = inScanner.nextLine();
                        Scanner lineScanner = new Scanner(line);
                        
           try {
                        for (int i = 0; i < 5; i++) {
                            // update information for vertex with index 
                            first =first + lineScanner.next()+" ";
                        }
                            first="["+first.trim()+"]";
                            
                            a= Arrays.stream(first.substring(1, first.length()-1).split(" "))
    .map(String::trim).mapToInt(Integer::parseInt).toArray();
                    }
                    catch (Exception e) {
                        System.err.println("Exception: " + e.getMessage());
                    }
             signAlgo sign = new signAlgo(a[0],a[1],a[2],a[3]);
                sign.setz(a[4]);
              System.out.print("\nAlice: ");
              List<Long> list = new ArrayList<>( 
                    Arrays.asList(sign.p,sign.alpha,sign.beta,sign.m,sign.r,sign.s));
              System.out.println(list.toString());
              clientData = list.toString();
                ProgressBar bar = new ProgressBar();
                System.out.println("Signing the Document Digitally");
                bar.update(0, 1000);
                for(int i=0;i<1000;i++) {
                                // do something!
                    for(int j=0;j<100000;j++)
                        for(int c=0;c<100000;c++);
                    // update the progress bar
                    bar.update(i, 1000);
                }
                System.out.println("Message Sent and Signed!");
        }
        else{
        System.out.println("Enter the value of p");
        long p=sc.nextLong();
        System.out.println("Enter the value of alpha(E1)");
        long alpha=sc.nextLong();
        System.out.println("Enter the value of m(Message)");
        long m=sc.nextLong();
        System.out.println("Enter the value of k(r)");
        long k=sc.nextLong();
        System.out.println("Enter the value of secret d");
        long z=sc.nextLong();
        signAlgo sign = new signAlgo(p,alpha,m,k);
        sign.setz(z);
      System.out.print("\nAlice: ");
      List<Long> list = new ArrayList<>( 
            Arrays.asList(sign.p,sign.alpha,sign.beta,sign.m,sign.r,sign.s));
      System.out.println(list.toString());
      clientData = list.toString();
        ProgressBar bar = new ProgressBar();
        System.out.println("Signing the Document Digitally");
        bar.update(0, 1000);
        for(int i=0;i<1000;i++) {
                        // do something!
            for(int j=0;j<100000;j++)
                for(int c=0;c<100000;c++);
            // update the progress bar
            bar.update(i, 1000);
        }
        System.out.println("Message Sent and Signed!");
      }
      }
      clientData=clientData.trim();
      sendbuffer = clientData.getBytes();        
      DatagramPacket sendPacket =
      new DatagramPacket(sendbuffer, sendbuffer.length, IP, 9876);
      clientSocket.send(sendPacket);
      if(clientData.equalsIgnoreCase("bye"))
      {
          System.out.println("Connection ended by Alice");
          break;
      }


      DatagramPacket receivePacket =
      new DatagramPacket(receivebuffer, receivebuffer.length);
      clientSocket.receive(receivePacket);
      String serverData = new String(receivePacket.getData());
      serverData=serverData.trim();
      System.out.print("\nBob: " + serverData);
      
      //checking condition for equals with serverData which is my string
      //c--;
      }
      clientSocket.close();
    }
    
}
