package Client.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;




public class Controller{
    //подписка на интерфейс инициализации позволит соединиться при запуске
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

    private boolean isAutorized;

    @FXML
    HBox bottomPanel;

    @FXML
    HBox upperPanel;

    @FXML
    TextField loginField;

    @FXML
    TextField passwordField;

    private void setAutorized(boolean isAutorized){
    this.isAutorized = isAutorized;
    if(!isAutorized){
        upperPanel.setVisible(true);
        upperPanel.setManaged(true);
        bottomPanel.setVisible(false);
        bottomPanel.setManaged(false);
    }
    else {
        upperPanel.setVisible(false);
        upperPanel.setManaged(false);
        bottomPanel.setVisible(true);
        bottomPanel.setManaged(true);
    }

}
    public void connect(){
        try {
            socket = new Socket(IP_ADRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true){
                            String str = in.readUTF();
                            if(str.startsWith("/authOk")){
                                setAutorized(true);
                                break;
                            }else {
                                textArea.appendText(str + "\n");
                            }
                        }

//                        while (true){
//                            String str = in.readUTF();
//                            if(str.startsWith("/private")){
//                                textArea.appendText("успех" + "\n");
//break;
//                            }else {
//                                textArea.appendText(str + "\n");
//                            }
//                        }

                        while (true) {
                            String str = in.readUTF();
                            if(str.equalsIgnoreCase("/clientClose")){
                                break;
                            }
                            textArea.appendText(str + "\n");
                        }
                        } catch(IOException e){
                            e.printStackTrace();
                        }finally{
                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        setAutorized(false);
                        }
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

    public void tryToAuth(ActionEvent actionEvent) {
    if(socket == null || socket.isClosed()){
        connect();

    }
        try {
            out.writeUTF("/auth " + loginField.getText() + " " + passwordField.getText());
            loginField.clear();
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}