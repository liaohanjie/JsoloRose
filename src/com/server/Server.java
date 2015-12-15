package com.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * netty5服务端
 * 
 * @author -琴兽-
 * 
 */
public class Server {

	public static void main(String[] args) {

		// 服务类
		ServerBootstrap b = new ServerBootstrap();

		// 创建boss和worker
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {

			// 设置循环线程组事例
			b.group(bossGroup, workerGroup);

			// 设置channel工厂
			b.channel(NioServerSocketChannel.class);

			// 设置管道
			b.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new StringDecoder());
					ch.pipeline().addLast(new StringEncoder());
					ch.pipeline().addLast(new ServerHandler());
				}
			});

			
			//bootstrap.setOption("backlog", 1024);
			//bootstrap.setOption("tcpNoDelay", true);
			//bootstrap.setOption("keepAlive", true);
			// 参数设置
			b.option(ChannelOption.SO_BACKLOG, 2048);//链接缓冲池队列大小
			b.childOption(ChannelOption.SO_KEEPALIVE, true);//维持活跃连接，清除死链接
			b.childOption(ChannelOption.TCP_NODELAY, true);//关闭延迟发送

			// 绑定端口
			ChannelFuture f = b.bind(10102);
			
			System.out.println("start!!!");
			
			// 等待服务器关闭
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 关闭释放资源
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

}