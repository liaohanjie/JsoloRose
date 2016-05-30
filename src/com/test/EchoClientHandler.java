package com.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.Delimiters;

/**
 * Created by hadoop on 2016/1/27.
 */
public class EchoClientHandler extends ChannelHandlerAdapter {
    private final byte[] req = ("Hi, Lilinfeng. Welcome to Netty.$_").getBytes();
    private int counter;

    public EchoClientHandler(){
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("TimeClient channelActive");
        ByteBuf message;
        for (int i = 0; i <100;i++){
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.write(message);
            ctx.write(Delimiters.nulDelimiter());
            ctx.flush();
        }
    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
////    	super.channelReadComplete(ctx);
//        ctx.flush();
//    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Now is:" + msg + " ; counter : " + ++counter);
    }
    
    
}
