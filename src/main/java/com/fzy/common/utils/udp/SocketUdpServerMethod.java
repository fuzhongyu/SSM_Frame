package com.fzy.common.utils.udp;

/**
 * 实现udp 服务端业务逻辑接口类
 * @author fuzhongyu
 * @date 2017/11/9
 */

public interface SocketUdpServerMethod {

    /**
     * 服务端udp业务实现接口
     * @param receiveData 客户端传输过来的数据
     * @return 服务端返回给客户端的数据
     */
    Object service(Object receiveData);
}
