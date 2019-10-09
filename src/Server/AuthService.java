package Server;

import java.sql.*;

public class AuthService {
    //одно подключение сервера к базе, чтобы каждый клиентне подсоединялся
private static Connection connection;
private static Statement statement;

public static void connect(){
    try {
        //инициализировали драйвер
        Class.forName("org.sqlite.JDBC");
        //подключаемся к конкретной базе
        connection = DriverManager.getConnection("jdbc:sqlite:mainDB.db");
        //создание стейтмента для возможности обмена сообщениями на основе соединения
        statement = connection.createStatement();
    } catch (Exception e) {
        e.printStackTrace();
    }
}



    public static String getNickByLoginAndPass (String login, String password)throws SQLException{
    String sql = String.format("select nickname from main where login='%s' and password='%s'",
        login, password);

        ResultSet rs = statement.executeQuery(sql);
        if(rs.next()){
          return rs.getString("nickname");
        }
        return null;
    }


    public static void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
