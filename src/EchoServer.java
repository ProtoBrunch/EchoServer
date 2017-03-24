import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by berberatr on 22.03.2017.
 */
public class EchoServer extends Thread{
    private Socket client;
    public static ArrayList<Socket> clientList = new ArrayList<>();

    public EchoServer(Socket client){
        this.client = client;
    }

    public void run(){
        try(
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(), true)
        ){
            out.println("Hallo, ich bin der Echoserver");

            String input;
            while((input = in.readLine()) != null){
                out.println(input);
            }
        }catch (IOException e){
            System.err.println(e);
        }
    }

    public static void main(String[] args) {
        int port = 50000;

        try(ServerSocket server = new ServerSocket(port)){
            System.out.println("EchoServer auf " + port + " gestartet ...");
            while(true){
                Socket client = server.accept();
                clientList.add(client);
                new EchoServerListener(client).start();

            }
        }catch (IOException e){
            System.err.println(e);
        }
    }
}
