package com.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * netty5客户端
 * @author hanjie.l
 *
 */
public class MultiClient {
	
	/**
	 * 服务类
	 */
	private Bootstrap bootstrap = new Bootstrap();
	
	/**
	 * 缓存会话组
	 */
	private List<Channel> channels = new ArrayList<>();
	
	/**
	 * 计数器
	 */
	private AtomicInteger index = new AtomicInteger();

	/**
	 * 启动服务
	 * @param count
	 */
	public void start(int count) {
		
		// worker
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		// 设置循环线程组事例
		bootstrap.group(workerGroup);

		// 设置channel工厂
		bootstrap.channel(NioSocketChannel.class);

		// 设置管道
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new StringDecoder());
				ch.pipeline().addLast(new StringEncoder());
				ch.pipeline().addLast(new ClientHandler());
			}
		});

		// 发起异步连接操作
		for(int i=0; i<count; i++){
			ChannelFuture connect = bootstrap.connect("192.168.100.124", 10102);
			channels.add(connect.channel());
		}
	}
	
	/**
	 * 获取一个会话
	 * @return
	 */
	public Channel nextChannel(){
		return getFirstActive(0);
	}
	
	/**
	 * 尝试获取一个可用的Channel
	 * @param count
	 * @return
	 */
	private Channel getFirstActive(int count){
		
		Channel channel = channels.get(Math.abs(index.getAndIncrement() % channels.size()));
		if(!channel.isActive()){
			channel = reconnect(channel);
			if(!channel.isActive()){
				
				if(count>=channels.size()){
					throw new RuntimeException("no can user channel");
				}
				return getFirstActive(count + 1);
			}
		}
		return channel;
	}
	
	/**
	 * 重连
	 * @return
	 */
	public Channel reconnect(Channel channel){
		synchronized (channel) {
			if(channels.indexOf(channel)== -1){
				return channel;
			}
			Channel newCahnnel = bootstrap.connect("192.168.100.124", 10102).channel();
			channels.set(channels.indexOf(channel), newCahnnel);
			return newCahnnel;
		}
	}
	
}
