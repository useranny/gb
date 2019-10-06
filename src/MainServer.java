import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MainServer{

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8189);
        System.out.println("сервер запущен");
        Socket socket = server.accept();
        System.out.println("Клиент подключен");

    Thread t1 = new Thread(new Runnable() {
    @Override
    public void run() {

        try{
//            PrintWriter out = new PrintWriter(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            while (true){
                String str = in.readUTF();
                if (str.equalsIgnoreCase("end")) break;
                System.out.println("Client: " + str);

            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
});
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {

                try{
                    Scanner in = new Scanner(System.in);
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                    while (true){
                        String str = in.nextLine();
                        if (str.equalsIgnoreCase("end")) break;
                        out.writeUTF("Server: " + str);

                    }

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();

    }
 }

