package zhoulk.gate.server.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import zhoulk.tool.model.CMDContent;

/**
 * Created by zlk on 2018/5/6.
 */
public class GateHallModel extends CMDContent {

    private long userId;

    @Override
    protected void decode0(ByteBuf in) {
        userId = in.readUnsignedIntLE();
    }

    @Override
    protected ByteBuf encode0() {
        ByteBuf buf = Unpooled.buffer(len());
        buf.writeIntLE((int)userId);
        return buf;
    }

    @Override
    public int len() {
        return 4;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
