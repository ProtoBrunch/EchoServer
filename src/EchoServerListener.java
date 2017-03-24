import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ListIterator;

/**
 * Created by berberatr on 23.03.2017.
 */
public class EchoServerListener extends Thread {
    public Socket client;

    public EchoServerListener(Socket client){
        this.client = client;
    }
    public void run(){
        try(BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))){
            while(true){
                try{
                String input = in.readLine();
                for (ListIterator<Socket> iter = EchoServer.clientList.listIterator(); iter.hasNext();) {
                    Socket client = iter.next();
                    if(client.isClosed()) {
                        EchoServer.clientList.remove(client);
                    }else{
                        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                        out.println(input);
                    }
                }
            }catch(SocketException e){
                    this.interrupt();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
