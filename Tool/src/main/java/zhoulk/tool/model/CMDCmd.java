package zhoulk.tool.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import zhoulk.tool.convert.ConvertUtils;

/**
 * Created by zlk on 2018/4/22.
 */
public class CMDCmd extends CMDContent{
    @Override
    public ByteBuf encode0() {
        ByteBuf buf = Unpooled.buffer(len());
        buf.writeShortLE(mainCmdId);
        buf.writeShortLE(subCmdId);
        return buf;
    }

    @Override
    public void decode0(ByteBuf in) {
        mainCmdId = in.readShortLE();
        subCmdId = in.readShortLE();
    }

    @Override
    public int len() {
        return 4;
    }

    private int mainCmdId;
    private int subCmdId;

    public int getMainCmdId() {
        return mainCmdId;
    }

    public void setMainCmdId(int mainCmdId) {
        this.mainCmdId = mainCmdId;
    }

    public int getSubCmdId() {
        return subCmdId;
    }

    public void setSubCmdId(int subCmdId) {
        this.subCmdId = subCmdId;
    }

    @Override
    public String toString() {
        return "CMDCmd{" +
                "mainCmdId=" + mainCmdId +
                ", subCmdId=" + subCmdId +
                '}';
    }
}
