package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

public class MainServer{
    private Vector<ClientHandler> clients;

        public MainServer() throws SQLException {
            clients = new Vector<>();
            ServerSocket server = null;
            Socket socket = null;

            try{
                AuthService.connect();
                server = new ServerSocket(8189);
                System.out.println("запустили сервер");

                while (true){
                    socket = server.accept();
                    System.out.println("клиент подключился");
                    new ClientHandler(this,socket);

                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
            //сначала закроем сокет, а затем сервер
            finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            AuthService.disconnect();
        }
    public void broadcastMsg(String msg) {
        for (ClientHandler o: clients) {
            o.sendMsg(msg);
        }
    }
    public void privateMsg(String who, String what) {
        for (ClientHandler o: clients) {
            if(o.getClient().equals(who)){
            o.sendMsg(what);
            return;
            }
        }
    }
    public String watching(String name) {
        for (ClientHandler o: clients) {
            if(o.getClient().equals(name)) {
                return o.nick;            }

        }return null;

    }
    public void subscribe(ClientHandler client) {
        clients.add(client);

    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }


}


