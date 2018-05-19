package zhoulk.gate.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Service;
import zhoulk.gate.server.handler.AcceptorIdleStateTrigger;
import zhoulk.gate.server.handler.DecodeServerHandler;
import zhoulk.gate.server.handler.ParseCmdServerHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by zlk on 2018/4/22.
 */

@Service("gateServerIntializer")
public class GateServerIntializer extends ChannelInitializer<SocketChannel> {

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
