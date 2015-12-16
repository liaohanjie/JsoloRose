package com.heart;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
/**
 * 消息处理类
 * @author -琴兽-
 *
 */
public class MessageHandler extends SimpleChannelInboundHandler<String> {

	/**
	 * 打印消息
	 */
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println("收到信息:" + msg);
	}
	
	
	/**
	 * 特别关注IdleStateEvent事件
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			System.out.println(event.state());
			
		}else{
			super.userEventTriggered(ctx, evt);
		}
	}

}
