package zhoulk.tool.model;

import io.netty.buffer.ByteBuf;
import org.apache.log4j.Logger;

/**
 * Created by zlk on 2018/4/22.
 */
public abstract class CMDContent {

    private static Logger logger = Logger.getLogger(CMDContent.class);

    public void decode(ByteBuf in){
        if(in.readableBytes() >= len()){
            decode0(in);
        }else{
            logger.error("协议头长度和体长度不符!!!!!" + " 可读长度"+in.readableBytes() + " 目标长度" + len());
        }
    }

    public ByteBuf encode(){
        return encode0();
    }

    protected abstract void decode0(ByteBuf in);

    protected abstract ByteBuf encode0();

    public abstract int len();

}
