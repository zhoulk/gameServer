package zhoulk.hall.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Service;
import zhoulk.hall.server.handler.ParseCmdServerHandler;
import zhoulk.login.server.handler.AcceptorIdleStateTrigger;
import zhoulk.login.server.handler.DecodeServerHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by zlk on 2018/4/22.
 */

@Service("HallServerIntializer")
public class HallServerIntializer extends ChannelInitializer<SocketChannel> {

    private DecodeServerHandler decodeServerHandler;
    private ParseCmdServerHandler parseCmdServerHandler;

    private final AcceptorIdleStateTrigger idleStateTrigger = new AcceptorIdleStateTrigger();

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                //.addLast(new IdleStateHandler(30, 30, 30, TimeUnit.SECONDS))
                //.addLast(idleStateTrigger)
                .addLast(
                //decodeServerHandler,
                parseCmdServerHandler);
    }

    public void setParseCmdServerHandler(ParseCmdServerHandler parseCmdServerHandler) {
        this.parseCmdServerHandler = parseCmdServerHandler;
    }

    public ParseCmdServerHandler getParseCmdServerHandler() {
        return parseCmdServerHandler;
    }

    public DecodeServerHandler getDecodeServerHandler() {
        return decodeServerHandler;
    }

    public void setDecodeServerHandler(DecodeServerHandler decodeServerHandler) {
        this.decodeServerHandler = decodeServerHandler;
    }
}
