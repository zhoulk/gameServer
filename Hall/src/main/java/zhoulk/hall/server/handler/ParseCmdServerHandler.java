package zhoulk.hall.server.handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import zhoulk.data.server.model.CMDUserInfo;
import zhoulk.data.server.model.CMDUserInfoQuery;
import zhoulk.data.server.model.CMDUserInfoQuerySuccess;
import zhoulk.gate.server.model.GateHallModel;
import zhoulk.hall.server.model.client.CMDLoginMobile;
import zhoulk.hall.server.model.client.CMDUserEnter;
import zhoulk.tool.model.CMDCmd;
import zhoulk.tool.model.CMDHead;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zlk on 2018/4/17.
 */

@Service("HallDecodeServerHandler")
@Scope("prototype")
@ChannelHandler.Sharable
public class ParseCmdServerHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = Logger.getLogger(ParseCmdServerHandler.class);

    String DATA_SERVER_KEY = "dataServer";
    String GATE_SERVER_KEY = "gateServer";

    Map<String, ChannelHandlerContext> cacheContexts = new HashMap<String, ChannelHandlerContext>();

    Map<String, ChannelHandlerContext> userContexts = new HashMap<String, ChannelHandlerContext>();

    public ParseCmdServerHandler() {
        super();
        logger.info("ParseCmdServerHandler initialize -----");

        connectToDataServer();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        logger.info("channel add ---");

        connectToDataServer();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channel active ---");
        cacheContexts.put(GATE_SERVER_KEY, ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf in = (ByteBuf) msg;

        GateHallModel gateHallModel = new GateHallModel();
        gateHallModel.decode(in);

        //userContexts.put(String.valueOf(gateHallModel.getUserId()), ctx);

        CMDCmd cmd = new CMDCmd();
        cmd.decode(in);

        logger.info("解析指令 " + cmd);

        if(cmd.getMainCmdId() == 1){
            switch (cmd.getSubCmdId()){
                case 1:

                    break;
                case 2:
                    CMDLoginMobile cmdLoginMobile = new CMDLoginMobile();
                    cmdLoginMobile.decode(in);

                    CMDCmd queryCmd = new CMDCmd();
                    queryCmd.setMainCmdId(1);
                    queryCmd.setSubCmdId(1);
                    ByteBuf queryBuf = queryCmd.encode();
                    CMDUserInfoQuery userInfoQuery = new CMDUserInfoQuery();
                    userInfoQuery.setDwUserID(cmdLoginMobile.getDwUserID());
                    userInfoQuery.setSzDynamicPassword(cmdLoginMobile.getSzDynamicPassword());
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
            ChannelFuture connectFuture = bootstrap.connect(new InetSocketAddress("127.0.0.1", 8888));
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
                CMDUserInfoQuerySuccess userInfoQuerySuccess = new CMDUserInfoQuerySuccess();
                userInfoQuerySuccess.decode(in);
                int userId = userInfoQuerySuccess.getCmdUserInfo().getUserId();
                //if(userContexts.containsKey(String.valueOf(userId))){
                //    ChannelHandlerContext userContext = userContexts.get(String.valueOf(userId));

                    CMDHead head1 = new CMDHead();

                    CMDCmd resCmd1 = new CMDCmd();
                    resCmd1.setMainCmdId(1);
                    resCmd1.setSubCmdId(102);

                    head1.setwPacketSize(resCmd1.len() + head1.len());

                    CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
                    responseGate(compositeByteBuf.addComponents(true, head1.encode(), resCmd1.encode()),
                            String.valueOf(userId));
                    //userContext.writeAndFlush(compositeByteBuf.addComponents(true, head1.encode(), resCmd1.encode()));

                    CMDHead head2 = new CMDHead();

                    CMDCmd resCmd2 = new CMDCmd();
                    resCmd2.setMainCmdId(3);
                    resCmd2.setSubCmdId(100);

                    CMDUserEnter userEnter = new CMDUserEnter();

                    CMDUserInfo userInfo = userInfoQuerySuccess.getCmdUserInfo();
                    userEnter.setDwUserID(userInfo.getUserId());
                    userEnter.setSzNickname(userInfo.getNickName());

                    head2.setwPacketSize(resCmd2.len() + head2.len() + userEnter.len());

                    logger.info(userEnter);

                    CompositeByteBuf compositeByteBuf2 = Unpooled.compositeBuffer();
                    responseGate(compositeByteBuf2.addComponents(true, head2.encode(), resCmd2.encode(), userEnter.encode()),
                            String.valueOf(userId));
                    //userContext.writeAndFlush(compositeByteBuf2.addComponents(true, head2.encode(), resCmd2.encode(), userEnter.encode()));
                //}

                break;
            case 11:


                break;
        }
    }

    void responseGate(ByteBuf in, String userId){
        logger.info("responseGate  userId = " + userId);
        ChannelHandlerContext gateContext = cacheContexts.get(GATE_SERVER_KEY);

        GateHallModel gateHallModel = new GateHallModel();
        gateHallModel.setUserId(Long.valueOf(userId));

        CompositeByteBuf resBuf = Unpooled.compositeBuffer();
        resBuf.addComponents(true, gateHallModel.encode(), in);

        gateContext.writeAndFlush(resBuf);
    }
}
