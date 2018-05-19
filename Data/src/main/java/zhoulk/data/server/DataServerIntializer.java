package zhoulk.data.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.stereotype.Service;
import zhoulk.data.server.handler.ParseCmdServerHandler;

/**
 * Created by zlk on 2018/4/22.
 */

@Service("DataServerIntializer")
public class DataServerIntializer extends ChannelInitializer<SocketChannel> {

    private ParseCmdServerHandler parseCmdServerHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(
                parseCmdServerHandler);
    }

    public void setParseCmdServerHandler(ParseCmdServerHandler parseCmdServerHandler) {
        this.parseCmdServerHandler = parseCmdServerHandler;
    }

    public ParseCmdServerHandler getParseCmdServerHandler() {
        return parseCmdServerHandler;
    }
}
