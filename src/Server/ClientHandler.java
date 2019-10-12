package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class ClientHandler {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private MainServer server;
    protected String nick;

    public ClientHandler(MainServer server,Socket socket){

        try {
            this.socket = socket;
            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());


            new Thread(new Runnable() {
                @Override
                public void run() {
                   try{
                       while (true){
                           String str = in.readUTF();
                           if (str.startsWith("/auth")){
                               String[] tokens = str.split(" ");
                               String currentNick = AuthService.getNickByLoginAndPass(tokens[1],tokens[2]);
                               String have = server.watching(currentNick);
                               if((currentNick != null) && (have == null)) {
                                  sendMsg("/authOk");
                                  nick = currentNick;
                                  server.subscribe(ClientHandler.this);
                                  break;
                               } else {
                                   sendMsg("неверные логин/пароль");
                               }
                           }
                           if (str.startsWith("/w")){
                               String[] tokens = str.split(" ", 3);
                               server.privateMsg(tokens[1],tokens[2]);
                           }
                       }

                       while(true){
                           String str = in.readUTF();
                           if(str.equalsIgnoreCase("/end")){
                               sendMsg("/clientClose");
                               break;
                           }
                           server.broadcastMsg(nick + ": " + str);
                       }

                   }catch (SQLException e){
                       e.printStackTrace();
                   }catch (IOException e){
                       e.printStackTrace();
                   }finally {
                       try {
                           in.close();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                       try {
                           out.close();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                       try {
                           socket.close();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }

                   }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void sendMsg(String msg){
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getClient() {
        return this.nick;
    }

}
