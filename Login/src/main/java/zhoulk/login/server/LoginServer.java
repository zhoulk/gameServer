package zhoulk.login.server;

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

@Service("LoginServer")
public class LoginServer {

    private static Logger logger = Logger.getLogger(LoginServer.class);

    @Value("#{config['login.port']?:'2222'}")
    private int port;

    private zhoulk.login.server.LoginServerIntializer loginServerIntializer;

    public void start() throws Exception{

        EventLoopGroup group = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(group, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(loginServerIntializer);
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

    public zhoulk.login.server.LoginServerIntializer getLoginServerIntializer() {
        return loginServerIntializer;
    }

    public void setLoginServerIntializer(LoginServerIntializer loginServerIntializer) {
        this.loginServerIntializer = loginServerIntializer;
    }
}
