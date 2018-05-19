package zhoulk.hall.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;

/**
 * Created by zlk on 2018/4/15.
 */

@Service("HallServer")
public class HallServer {

    private static Logger logger = Logger.getLogger(HallServer.class);

    @Value("#{config['hall.port']?:'2222'}")
    private int port;

    private HallServerIntializer hallServerIntializer;

    public void start() throws Exception{

        EventLoopGroup group = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(group, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(hallServerIntializer);
            ChannelFuture f = b.bind().sync();
            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(future.isSuccess()){
                        logger.info("启动成功 连接在 " + future.channel().localAddress());
                    }
                }
            });

            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
        }
    }

    public HallServerIntializer getHallServerIntializer() {
        return hallServerIntializer;
    }

    public void setHallServerIntializer(HallServerIntializer hallServerIntializer) {
        this.hallServerIntializer = hallServerIntializer;
    }
}
