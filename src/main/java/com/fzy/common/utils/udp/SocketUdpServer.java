package com.fzy.common.utils.udp;

import com.fzy.common.thread.ExcutorProcessPool;
import com.fzy.common.utils.SerializableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 *  udp数据传输,服务接收端
 *
 * @author fuzhongyu
 * @date 2017/11/9
 */

public class SocketUdpServer {

    private Logger logger= LoggerFactory.getLogger(SocketUdpServer.class);

    /**
     * 服务端udp启动
     * @param serverPort 服务端udp端口
     * @param method    服务端业务逻辑(需要实现SocketUdpServerMethod接口)
     */
    public static void start(int serverPort,SocketUdpServerMethod method){
        new SocketUdpServer().startUp(serverPort,method);
    }


    /**
     * 启动udp服务端socket,接收数据
     * @param serverPort  端口
     * @throws IOException
     */
    private void startUp(int serverPort,SocketUdpServerMethod method){
        try {
            //设置服务端地址
            InetSocketAddress inetSocketAddress=new InetSocketAddress(serverPort);
            //创建服务端socket
            DatagramSocket datagramSocket=new DatagramSocket(inetSocketAddress);
            while (true){
                //设置缓冲区
                byte[] buf=new byte[1024*16];
                DatagramPacket datagramPacket=new DatagramPacket(buf,buf.length);
                //接收数据
                datagramSocket.receive(datagramPacket);
                //异步执行服务端业务逻辑
                ExcutorProcessPool.getInstance().excute(new ServerExecuteThread(datagramSocket,datagramPacket,method));
            }
        }catch (IOException e){
            e.printStackTrace();
            logger.error("---- 启动服务端 socket 失败 ");
        }

    }

    /**
     * 执行业务
     */
    private class ServerExecuteThread implements Runnable{

        private DatagramSocket socket;

        private DatagramPacket packet;

        private SocketUdpServerMethod method;

        public ServerExecuteThread(DatagramSocket socket,DatagramPacket packet,SocketUdpServerMethod method){
            this.socket=socket;
            this.packet=packet;
            this.method=method;
        }

        @Override
        public void run() {
            logger.debug("===有新的连接请求：ip:"+packet.getAddress()+"  port:"+packet.getPort()+"=====");
            Object resultData=null;
            if(packet.getData()!=null){
                resultData=method.service(SerializableUtils.unserialize(packet.getData()));
            }
            if(resultData!=null){
                byte[] bytes=SerializableUtils.serialize(resultData);
                DatagramPacket packetReturn=new DatagramPacket(bytes,bytes.length);
                packetReturn.setSocketAddress(packet.getSocketAddress());
                try {
                    socket.send(packetReturn);
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("==== server socket 返回数据失败 =====");
                }
            }


        }
    }

}
