package com.fzy.modules.reptile;


import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Hikari连接池
 * @author fuzhongyu
 * @date 2017/12/18
 */

public class HikariConnectPool {


    private static HikariDataSource dataSource;

    private HikariConnectPool(){}


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
            synchronized (HikariConnectPool.class){
                if(dataSource==null){
                    dataSource=new HikariDataSource();
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
    private static void initDataSource(HikariDataSource dataSource){

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/taozhiyuan_new?characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setMinimumIdle(10);
        dataSource.setMaximumPoolSize(10000);
        dataSource.setIdleTimeout(100000);


    }

    public static void close(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
