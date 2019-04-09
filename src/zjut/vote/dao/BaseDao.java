package zjut.vote.dao;


import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BaseDao {
//    protected DataSource dataSource;
//    public BaseDao(){
//    try{
//        Context context = new InitialContext();
//        //Context envContext = (Context)context.lookup("java:/comp/env");      
//        //DataSource dataSource = (DataSource)envContext.lookup("jdbc/eis"); 
//        dataSource = (DataSource)context.lookup("java:comp/env/jdbc/eis");
//    }catch(NamingException e){
//        System.out.println("Exception:" + e);
//    }
//}
//    public Connection getConnection()throws Exception{
//        return dataSource.getConnection();
//    }
	 public static Connection getConnection(){
	        Connection conn = null;
	        try {
//	            Context c = new InitialContext();
//	            DataSource dataSource = (DataSource) c.lookup("java:/comp/env/jdbc/eis");//这里的jdbc/login_register和篇配置文件中的name属性一致
	        	Context initContext = new InitialContext();   
	        	 Context envContext = (Context)initContext.lookup("java:/comp/env");      
	        	DataSource ds = (DataSource)envContext.lookup("jdbc/ei");  
	        	conn = ds.getConnection();
	            return conn;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } catch (NamingException e) {
	            e.printStackTrace();
	        }

	        return conn;
	    }
}