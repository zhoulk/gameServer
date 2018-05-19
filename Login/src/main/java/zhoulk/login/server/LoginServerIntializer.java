package zhoulk.login.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Service;
import zhoulk.login.server.handler.AcceptorIdleStateTrigger;
import zhoulk.login.server.handler.DecodeServerHandler;
import zhoulk.login.server.handler.ParseCmdServerHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by zlk on 2018/4/22.
 */

@Service("loginServerIntializer")
public class LoginServerIntializer extends ChannelInitializer<SocketChannel> {

    public ParseCmdServerHandler parseCmdHandler;
    private DecodeServerHandler decodeServerHandler;

    private final AcceptorIdleStateTrigger idleStateTrigger = new AcceptorIdleStateTrigger();

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new IdleStateHandler(30, 30, 30, TimeUnit.SECONDS))
                .addLast(idleStateTrigger)
                .addLast(
                    decodeServerHandler,
                        parseCmdHandler);
    }

    public void setDecodeServerHandler(DecodeServerHandler decodeServerHandler) {
        this.decodeServerHandler = decodeServerHandler;
    }

    public DecodeServerHandler getDecodeServerHandler() {
        return decodeServerHandler;
    }

    public ParseCmdServerHandler getParseCmdHandler() {
        return parseCmdHandler;
    }

    public void setParseCmdHandler(ParseCmdServerHandler parseCmdHandler) {
        this.parseCmdHandler = parseCmdHandler;
    }
}
