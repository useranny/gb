package client.sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    TextArea textArea;

    @FXML
    TextField textField;

    @FXML
    Button button1;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;


    final String IP_ADRESS = "localhost";
    final int PORT = 8189;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            socket = new Socket(IP_ADRESS , PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        while (true){
                            String str = in.readUTF();
                            System.out.println(str);
                            textArea.appendText(str + "\n");
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    //ждем ответа от сервера

                }
            }).start();



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMessage() {
        try {
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }



}