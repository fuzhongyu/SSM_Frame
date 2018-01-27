package com.fzy.modules.reptile;

import com.alibaba.druid.pool.DruidDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * druid连接池
 * @author fuzhongyu
 * @date 2017/12/18
 */
public class DruidConnectPool {


    private static DruidDataSource dataSource;

    private DruidConnectPool(){}


    /**
     * 获取连接
     * @return
     */
    public static Connection getConnection(){
        try {
            return getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static DataSource getInstance(){
        if (dataSource==null){
            synchronized (DruidConnectPool.class){
                if(dataSource==null){
                    dataSource=new DruidDataSource();
                    initDataSource(dataSource);
                }
            }
        }
        return dataSource;
    }


    /**
     * 连接初始化设置
     * @param dataSource
     */
    private static void initDataSource(DruidDataSource dataSource){
        dataSource.setDbType("mysql");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/taozhiyuan_new?characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setInitialSize(10);
        dataSource.setMaxActive(1000);
        dataSource.setMaxWait(100000);

    }

    public static void close(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
