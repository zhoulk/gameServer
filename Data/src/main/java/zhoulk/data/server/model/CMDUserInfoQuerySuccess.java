package zhoulk.data.server.model;

import io.netty.buffer.ByteBuf;
import zhoulk.tool.model.CMDContent;

/**
 * Created by zlk on 2018/4/22.
 */
public class CMDUserInfoQuerySuccess extends CMDContent{

    private CMDUserInfo cmdUserInfo;

    @Override
    protected void decode0(ByteBuf in) {
        if(cmdUserInfo == null)
            cmdUserInfo = new CMDUserInfo();
        cmdUserInfo.decode(in);
    }

    @Override
    protected ByteBuf encode0() {
        return cmdUserInfo.encode();
    }

    @Override
    public int len() {
        return cmdUserInfo == null ? 0 : cmdUserInfo.len();
    }

    public CMDUserInfo getCmdUserInfo() {
        return cmdUserInfo;
    }

    public void setCmdUserInfo(CMDUserInfo cmdUserInfo) {
        this.cmdUserInfo = cmdUserInfo;
    }
}
