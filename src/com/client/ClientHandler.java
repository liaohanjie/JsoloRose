package com.client;

import java.net.SocketAddress;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<String> {

	public static ClientPool clientPool = new ClientPool();

	@Override
	protected void messageReceived(ChannelHandlerContext arg0, String arg1) throws Exception {
		System.out.println(arg1);

		arg0.channel().writeAndFlush("flash");
		arg0.writeAndFlush("hi");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelActive");
		super.channelActive(ctx);
	}

	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
		System.out.println("connect");
		super.connect(ctx, remoteAddress, localAddress, promise);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelRegistered");
		super.channelRegistered(ctx);
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		System.out.println("disconnect");
		super.disconnect(ctx, promise);
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		System.out.println("close");
		super.close(ctx, promise);
	}
	
	
}
