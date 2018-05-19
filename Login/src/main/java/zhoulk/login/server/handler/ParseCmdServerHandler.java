package zhoulk.login.server.handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import zhoulk.data.server.model.CMDUserInfo;
import zhoulk.data.server.model.CMDUserInfoQuery;
import zhoulk.data.server.model.CMDUserInfoQueryFail;
import zhoulk.data.server.model.CMDUserInfoQuerySuccess;
import zhoulk.login.server.model.client.CMDLoginOtherPlatform;
import zhoulk.login.server.model.client.CMDLoginSuccess;
import zhoulk.login.server.model.client.CMDServerList;
import zhoulk.tool.model.CMDCmd;
import zhoulk.tool.model.CMDHead;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zlk on 2018/4/15.
 */

@Service("LoginParseCmdServerHandler")
@Scope("prototype")
@ChannelHandler.Sharable
public class ParseCmdServerHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = Logger.getLogger(ParseCmdServerHandler.class);

    @Value("#{config['gate.port']?:'2222'}")
    private int gatePort;

    ChannelFuture connectFuture;

    String DATA_SERVER_KEY = "dataServer";
    Map<String, ChannelHandlerContext> cacheContexts = new HashMap<String, ChannelHandlerContext>();

    Map<String, CMDLoginOtherPlatform> cacheLoginInfos = new HashMap<String, CMDLoginOtherPlatform>();

    public ParseCmdServerHandler() {
        super();
        logger.info("ParseCmdServerHandler initialize -----");

        connectToDataServer();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        logger.info("ParseCmdServerHandler channelRegistered -----");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        logger.info("ParseCmdServerHandler added -----");

        connectToDataServer();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("Login Active ----" + ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf in = (ByteBuf) msg;

        CMDCmd cmd = new CMDCmd();
        cmd.decode(in);

        logger.info("解析指令 " + cmd);

        if(cmd.getMainCmdId() == 100){
            switch (cmd.getSubCmdId()){
                case 4:
                    CMDLoginOtherPlatform loginOtherPlatform = new CMDLoginOtherPlatform();
                    loginOtherPlatform.decode(in);

                    cacheContexts.put(loginOtherPlatform.getSzUserUin(), ctx);
                    cacheLoginInfos.put(loginOtherPlatform.getSzUserUin(), loginOtherPlatform);

                    CMDCmd queryCmd = new CMDCmd();
                    queryCmd.setMainCmdId(1);
                    queryCmd.setSubCmdId(1);
                    ByteBuf queryBuf = queryCmd.encode();
                    CMDUserInfoQuery userInfoQuery = new CMDUserInfoQuery();
                    userInfoQuery.setSzUserUin(loginOtherPlatform.getSzUserUin());
                    ByteBuf userInfoBuf = userInfoQuery.encode();

                    CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
                    queryData(compositeByteBuf.addComponents(true, queryBuf, userInfoBuf));

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

    // 向数据服务请求数据
    void queryData(ByteBuf in){
        if(cacheContexts.containsKey(DATA_SERVER_KEY)){
            ChannelHandlerContext dataContext = cacheContexts.get(DATA_SERVER_KEY);
            dataContext.writeAndFlush(in);
        }
    }

    void connectToDataServer(){
        if(cacheContexts.containsKey(DATA_SERVER_KEY)){

        }else{
            final Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class)
                    .handler(
                            new SimpleChannelInboundHandler<ByteBuf>() {

                                @Override
                                public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

                                }

                                @Override
                                protected void channelRead0( ChannelHandlerContext ctx, ByteBuf in) throws Exception
                                {
                                    logger.info("从数据服务获得数据 " + ByteBufUtil.hexDump(in));

                                    CMDCmd cmd = new CMDCmd();
                                    cmd.decode(in);

                                    if(cmd.getMainCmdId() == 1){
                                        parseUserInfo(ctx, cmd, in);
                                    }
                                }

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    cacheContexts.put(DATA_SERVER_KEY, ctx);
                                    logger.info("缓存数据服务 " + ctx.channel());
                                }
                            });
            bootstrap.group(new NioEventLoopGroup());
            connectFuture = bootstrap.connect(new InetSocketAddress("127.0.0.1", 8888));
            connectFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(channelFuture.isSuccess()){
                        logger.info("连接数据服务成功 " + channelFuture.channel());
                    }else{
                        logger.error("连接数据服务 失败" + channelFuture.channel());
                    }
                }
            });
        }
    }

    void parseUserInfo(ChannelHandlerContext ctx, CMDCmd cmd, ByteBuf in){
        switch (cmd.getSubCmdId()){
            case 10:
                break;
            case 11:
                CMDUserInfoQueryFail userInfoQueryFail = new CMDUserInfoQueryFail();
                userInfoQueryFail.decode(in);
                if(cacheLoginInfos.containsKey(userInfoQueryFail.getUid())){

                    CMDCmd queryCmd = new CMDCmd();
                    queryCmd.setMainCmdId(1);
                    queryCmd.setSubCmdId(2);
                    ByteBuf queryBuf = queryCmd.encode();

                    CMDLoginOtherPlatform loginOtherPlatform = cacheLoginInfos.get(userInfoQueryFail.getUid());
                    ByteBuf loginInfoBuf = loginOtherPlatform.encode();

                    CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
                    queryData(compositeByteBuf.addComponents(true, queryBuf, loginInfoBuf));
                }
                break;
            case 20:
                CMDUserInfoQuerySuccess querySuccess = new CMDUserInfoQuerySuccess();
                querySuccess.decode(in);
                CMDUserInfo userInfo = querySuccess.getCmdUserInfo();

                logger.info(userInfo.getUid());

                if(cacheContexts.containsKey(userInfo.getUid())){
                    logger.info(userInfo.getUid());

                    ChannelHandlerContext clientContext = cacheContexts.get(userInfo.getUid());

                    CMDHead head = new CMDHead();

                    CMDCmd resCmd = new CMDCmd();
                    resCmd.setMainCmdId(100);
                    resCmd.setSubCmdId(100);

                    CMDLoginSuccess loginSuccess = new CMDLoginSuccess();
                    loginSuccess.setUid(userInfo.getUid());
                    loginSuccess.setUserId(userInfo.getUserId());
                    loginSuccess.setNickName(userInfo.getNickName());
                    loginSuccess.setPass(userInfo.getPass());

                    head.setwPacketSize(loginSuccess.len() + resCmd.len() + head.len());

                    ByteBuf headBuf = head.encode();
                    ByteBuf resCmdBuf = resCmd.encode();
                    ByteBuf loginSuccessBuf = loginSuccess.encode();

                    CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
                    compositeByteBuf.addComponents(true, headBuf, resCmdBuf, loginSuccessBuf);
                    logger.info(ByteBufUtil.hexDump(compositeByteBuf));
                    clientContext.writeAndFlush(compositeByteBuf);


                    CMDHead head1 = new CMDHead();

                    CMDCmd resCmd1 = new CMDCmd();
                    resCmd1.setMainCmdId(101);
                    resCmd1.setSubCmdId(101);

                    CMDServerList serverList = new CMDServerList();
                    serverList.setwServerPort(gatePort);
                    serverList.setSzServerAddr("localhost");
                    serverList.setwKindID(315);

                    head1.setwPacketSize(serverList.len() + resCmd1.len() + head1.len());

                    CompositeByteBuf compositeByteBuf1 = Unpooled.compositeBuffer();
                    compositeByteBuf1.addComponents(true, head1.encode(), resCmd1.encode(), serverList.encode());
                    clientContext.writeAndFlush(compositeByteBuf1);
                }

                break;
            case 21:

                break;
        }
    }
}
