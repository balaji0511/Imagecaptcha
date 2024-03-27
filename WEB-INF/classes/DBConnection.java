
package honey;
import java.io.FileInputStream;
import java.sql.*;
import java.util.*;

public class DBConnection {

    private Connection con;
    private String driverClass;
    private String driverURL;
    private String userName;
    private String passWord;

    public DBConnection() throws Exception {
ResourceBundle resource=ResourceBundle.getBundle("application");


        driverClass = resource.getString("DriverClass");
        driverURL = resource.getString("DriverURL");
        userName = resource.getString("UserName");
        passWord = resource.getString("PassWord");
    }

    public Connection getConnection() throws Exception {
        Class.forName(driverClass);
        con = DriverManager.getConnection(driverURL, userName, passWord);

        return this.getCon();
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
//set/get methods for all properties like username,....
    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getDriverURL() {
        return driverURL;
    }

    public void setDriverURL(String driverURL) {
        this.driverURL = driverURL;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}