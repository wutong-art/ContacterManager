package main.Util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {
    private static DataSource ds;
    static {
        try {
            //加载配置文件
            Properties pro = new Properties();
            InputStream in = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            pro.load(in);
            //获取数据库连接池
            DruidDataSourceFactory dsf = new DruidDataSourceFactory();
            ds = dsf.createDataSource(pro);

        }catch (IOException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取数据库连接池
    public static DataSource getDataSource(){
        return ds;
    }


    //获取连接
    public static Connection getConnection() throws SQLException {
            return  ds.getConnection();
    }

}
