package com.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
/**
 * 消息处理
 * @author -琴兽-
 *
 */
public class ServerHandler extends SimpleChannelInboundHandler<String>{

	/**
	 * 接收消息
	 */
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {

		System.out.println("服务端消息:"+msg);
		
		ctx.writeAndFlush("收到");
	}

	/**
	 * 新会话激活
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelActive"+"新客户的连接");
		super.channelActive(ctx);
	}

	/**
	 * 会话断开
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelInactive"+"客户的断开连接");
		super.channelInactive(ctx);
	}
}
