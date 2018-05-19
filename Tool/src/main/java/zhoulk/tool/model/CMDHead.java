package zhoulk.tool.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by zlk on 2018/4/22.
 */
public class CMDHead extends CMDContent {

    @Override
    public void decode0(ByteBuf in) {
        cbDataKind = in.readByte();
        cbCheckCode = in.readByte();
        wPacketSize = in.readShortLE();
    }

    @Override
    public ByteBuf encode0() {
        ByteBuf buf = Unpooled.buffer(len());
        buf.writeByte(cbDataKind);
        buf.writeByte(cbCheckCode);
        buf.writeShortLE(wPacketSize);
        return buf;
    }

    @Override
    public int len() {
        return 4;
    }

    private short cbDataKind;
    private short cbCheckCode;
    private int wPacketSize;

    public short getCbDataKind() {
        return cbDataKind;
    }

    public void setCbDataKind(short cbDataKind) {
        this.cbDataKind = cbDataKind;
    }

    public short getCbCheckCode() {
        return cbCheckCode;
    }

    public void setCbCheckCode(short cbCheckCode) {
        this.cbCheckCode = cbCheckCode;
    }

    public int getwPacketSize() {
        return wPacketSize;
    }

    public void setwPacketSize(int wPacketSize) {
        this.wPacketSize = wPacketSize;
    }
}
