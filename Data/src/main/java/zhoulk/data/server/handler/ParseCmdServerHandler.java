package zhoulk.data.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import zhoulk.data.server.entities.MJUser;
import zhoulk.data.server.mapping.MJUserDao;
import zhoulk.data.server.model.CMDUserInfo;
import zhoulk.data.server.model.CMDUserInfoQuery;
import zhoulk.data.server.model.CMDUserInfoQueryFail;
import zhoulk.data.server.model.CMDUserInfoQuerySuccess;
import zhoulk.login.server.model.client.CMDLoginOtherPlatform;
import zhoulk.tool.model.CMDCmd;

import java.util.List;

/**
 * Created by zlk on 2018/4/17.
 */

@Service("DataDecodeServerHandler")
@Scope("prototype")
@ChannelHandler.Sharable
public class ParseCmdServerHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = Logger.getLogger(ParseCmdServerHandler.class);

    private MJUserDao mjUserDao;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf in = (ByteBuf) msg;

        CMDCmd cmd = new CMDCmd();
        cmd.decode(in);

        logger.info("解析指令 " + cmd);

        if(cmd.getMainCmdId() == 1){
            switch (cmd.getSubCmdId()){
                case 1:
                    CMDUserInfoQuery userInfoQuery = new CMDUserInfoQuery();
                    userInfoQuery.decode(in);

                    MJUser user = null;
                    if(userInfoQuery.getDwUserID() > 0 && userInfoQuery.getSzDynamicPassword().length() > 0){
                        List<MJUser> mjUsers = mjUserDao.getMJUserByUserIdAndPass(userInfoQuery.getDwUserID(), userInfoQuery.getSzDynamicPassword());
                        if(mjUsers != null && mjUsers.size() > 0){
                            user = mjUsers.get(0);
                        }
                    }else{
                        user = mjUserDao.getMJUserByUid(userInfoQuery.getSzUserUin());
                    }
                    if(user == null){
                        logger.info("未找到 uid = " + userInfoQuery.getSzUserUin());
                        CMDCmd resCmd = new CMDCmd();
                        resCmd.setMainCmdId(1);
                        resCmd.setSubCmdId(11);
                        ByteBuf resCmdBuf = resCmd.encode();
                        CMDUserInfoQueryFail userInfoQueryFail = new CMDUserInfoQueryFail();
                        userInfoQueryFail.setlResultCode(404);
                        userInfoQueryFail.setUid(userInfoQuery.getSzUserUin());
                        ByteBuf userInfoQueryFailBuf = userInfoQueryFail.encode();

                        CompositeByteBuf buff = Unpooled.compositeBuffer();
                        ctx.writeAndFlush(buff.addComponents(true, resCmdBuf, userInfoQueryFailBuf));

                    }else{
                        logger.info("找到 " + user);

                        CMDCmd resCmd = new CMDCmd();
                        resCmd.setMainCmdId(1);
                        resCmd.setSubCmdId(10);
                        ByteBuf resCmdBuf = resCmd.encode();
                        CMDUserInfoQuerySuccess userInfoQuerySuccess = new CMDUserInfoQuerySuccess();
                        CMDUserInfo userInfo = new CMDUserInfo();
                        userInfo.setUserId(user.getUserId());
                        userInfo.setNickName(user.getNickName());
                        userInfoQuerySuccess.setCmdUserInfo(userInfo);
                        ByteBuf userInfoQuerySuccessBuf = userInfoQuerySuccess.encode();

                        CompositeByteBuf buff = Unpooled.compositeBuffer();
                        ctx.writeAndFlush(buff.addComponents(true, resCmdBuf, userInfoQuerySuccessBuf));
                    }

                    break;
                case 2:
                    CMDLoginOtherPlatform loginOtherPlatform = new CMDLoginOtherPlatform();
                    loginOtherPlatform.decode(in);

                    MJUser mjUser = new MJUser();
                    mjUser.convertFromModel(loginOtherPlatform);
                    mjUser.setEnable(1);
                    mjUser.setUserId(100000);
                    mjUser.setPass("abcdefg");
                    int id = mjUserDao.insertMJUser(mjUser);

                    if(id> 0){
                        logger.info("插入成功  " + mjUser);

                        mjUser = mjUserDao.getMJUserByUid(mjUser.getUid());

                        CMDCmd resCmd = new CMDCmd();
                        resCmd.setMainCmdId(1);
                        resCmd.setSubCmdId(20);
                        ByteBuf resCmdBuf = resCmd.encode();

                        CMDUserInfoQuerySuccess userInfoQuerySuccess = new CMDUserInfoQuerySuccess();
                        CMDUserInfo userInfo = new CMDUserInfo();
                        userInfo.convertFromEntities(mjUser);
                        userInfoQuerySuccess.setCmdUserInfo(userInfo);
                        ByteBuf userInfoQuerySuccessBuf = userInfoQuerySuccess.encode();

                        CompositeByteBuf buff = Unpooled.compositeBuffer();
                        ctx.writeAndFlush(buff.addComponents(true, resCmdBuf, userInfoQuerySuccessBuf));
                    }else{
                        logger.error("插入失败  " );
                    }

                    break;
            }
        }else if(cmd.getMainCmdId() == 0){
            switch (cmd.getSubCmdId()){
                case 1:
                    // 心跳

                    break;
            }
        }
    }

    public MJUserDao getMjUserDao() {
        return mjUserDao;
    }

    public void setMjUserDao(MJUserDao mjUserDao) {
        this.mjUserDao = mjUserDao;
    }
}
