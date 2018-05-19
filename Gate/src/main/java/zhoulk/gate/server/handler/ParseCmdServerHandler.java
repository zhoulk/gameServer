package zhoulk.gate.server.handler;

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
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import zhoulk.data.server.model.CMDUserInfo;
import zhoulk.data.server.model.CMDUserInfoQuery;
import zhoulk.data.server.model.CMDUserInfoQueryFail;
import zhoulk.data.server.model.CMDUserInfoQuerySuccess;
import zhoulk.gate.server.model.GateHallModel;
import zhoulk.hall.server.model.client.CMDLoginMobile;
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

@Service("GateParseCmdServerHandler")
@Scope("prototype")
@ChannelHandler.Sharable
public class ParseCmdServerHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = Logger.getLogger(ParseCmdServerHandler.class);

    ChannelFuture connectFuture;

    String DATA_SERVER_KEY = "dataServer";
    String HALL_SERVER_KEY = "hallServer";
    String GAME_SERVER_KEY = "gameServer";

    @Value("#{config['data.ip']?:'127.0.0.1'}")
    private String dataIp;
    @Value("#{config['data.port']?:'0000'}")
    private int dataPort;

    @Value("#{config['hall.ip']?:'127.0.0.1'}")
    private String hallIp;
    @Value("#{config['hall.port']?:'0000'}")
    private int hallPort;

    Map<String, String> cacheIpUserIds = new HashMap<String, String>();
    Map<String, ChannelHandlerContext> cacheContexts = new HashMap<String, ChannelHandlerContext>();

    Map<String, CMDLoginOtherPlatform> cacheLoginInfos = new HashMap<String, CMDLoginOtherPlatform>();

    public void start(){
        logger.info("ParseCmdServerHandler initialize -----");

        connectToDataServer();
        connectToHallServer();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        logger.info("ParseCmdServerHandler channelRegistered -----");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        logger.info("ParseCmdServerHandler added -----");

        connectToDataServer();
        connectToHallServer();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("Gate Active ----" + ctx.channel());
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
        ByteBuf origin = in.copy();

        CMDCmd cmd = new CMDCmd();
        cmd.decode(in);

        logger.info("解析指令 " + cmd + ctx.channel().remoteAddress().toString());

        String userId = cacheIpUserIds.get(ctx.channel().remoteAddress().toString());

        if(cmd.getMainCmdId() == 1){

            if (cmd.getSubCmdId() == 2) {
                CMDLoginMobile cmdLoginMobile = new CMDLoginMobile();
                cmdLoginMobile.decode(in);

                cacheIpUserIds.put(ctx.channel().remoteAddress().toString(), String.valueOf(cmdLoginMobile.getDwUserID()));
                cacheContexts.put(String.valueOf(cmdLoginMobile.getDwUserID()), ctx);

                userId = cacheIpUserIds.get(ctx.channel().remoteAddress().toString());
            }

            if(userId.isEmpty()){
                logger.error("用户id为空   !!!!!!! ");
            }else{
                queryHall(origin, userId);
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

    // 向数据服务请求数据
    void queryHall(ByteBuf in, String userId){
        if(cacheContexts.containsKey(HALL_SERVER_KEY)){
            ChannelHandlerContext dataContext = cacheContexts.get(HALL_SERVER_KEY);

            GateHallModel gateHallModel = new GateHallModel();
            gateHallModel.setUserId(Long.valueOf(userId));

            CompositeByteBuf resBuf = Unpooled.compositeBuffer();
            resBuf.addComponents(true, gateHallModel.encode(), in);

            dataContext.writeAndFlush(resBuf);
        }
    }

    // 向数据服务请求数据
    void queryGame(ByteBuf in){
        if(cacheContexts.containsKey(GAME_SERVER_KEY)){
            ChannelHandlerContext dataContext = cacheContexts.get(GAME_SERVER_KEY);
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
                                }

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    cacheContexts.put(DATA_SERVER_KEY, ctx);
                                    logger.info("缓存数据服务 " + ctx.channel());
                                }
                            });
            bootstrap.group(new NioEventLoopGroup());
            connectFuture = bootstrap.connect(new InetSocketAddress(dataIp, dataPort));
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

    void connectToHallServer(){
        if(cacheContexts.containsKey(HALL_SERVER_KEY)){

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
                                    logger.info("从大厅服务获得数据 " + ByteBufUtil.hexDump(in));

                                    GateHallModel gateHallModel = new GateHallModel();
                                    gateHallModel.decode(in);

                                    ChannelHandlerContext userContext = cacheContexts.get(String.valueOf(gateHallModel.getUserId()));
                                    if (userContext != null){
                                        userContext.writeAndFlush(in.copy());
                                    }
                                }

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    cacheContexts.put(HALL_SERVER_KEY, ctx);
                                    logger.info("缓存大厅服务 " + ctx.channel());
                                }
                            });
            bootstrap.group(new NioEventLoopGroup());
            connectFuture = bootstrap.connect(new InetSocketAddress(hallIp, hallPort));
            connectFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(channelFuture.isSuccess()){
                        logger.info("连接大厅服务成功 " + channelFuture.channel());
                    }else{
                        logger.error("连接大厅服务 失败" + channelFuture.channel());
                    }
                }
            });
        }
    }

    void connectToGameServer(){
        if(cacheContexts.containsKey(GAME_SERVER_KEY)){

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
                                    logger.info("从游戏服务获得数据 " + ByteBufUtil.hexDump(in));

                                    CMDCmd cmd = new CMDCmd();
                                    cmd.decode(in);
                                }

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    cacheContexts.put(GAME_SERVER_KEY, ctx);
                                    logger.info("缓存游戏服务 " + ctx.channel());
                                }
                            });
            bootstrap.group(new NioEventLoopGroup());
            connectFuture = bootstrap.connect(new InetSocketAddress("127.0.0.1", 8888));
            connectFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(channelFuture.isSuccess()){
                        logger.info("连接游戏服务成功 " + channelFuture.channel());
                    }else{
                        logger.error("连接游戏服务 失败" + channelFuture.channel());
                    }
                }
            });
        }
    }

    public String getDataIp() {
        return dataIp;
    }

    public void setDataIp(String dataIp) {
        this.dataIp = dataIp;
    }
}
