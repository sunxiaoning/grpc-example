package io.grpc.examples.routeguide;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

/**
 * 功能：grpc 客户端测试类
 * @author sunxiaoning
 * @date 2017/7/11
 */
public class RouteGuideClient {

    private final ManagedChannel channel;
    private final RouteGuideGrpc.RouteGuideBlockingStub blockingStub;

    public RouteGuideClient(String host,int port){
        channel = ManagedChannelBuilder
                    .forAddress(host,port)
                    .usePlaintext(true)
                    .build();
        blockingStub = RouteGuideGrpc.newBlockingStub(channel);
    }

    public Feature getFeature(Point point){
        return blockingStub.getFeature(point);
    }
    public void shutdown() throws InterruptedException{
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException{
        RouteGuideClient client = new RouteGuideClient("127.0.0.1",8980);
        Point point = Point.newBuilder().setLatitude(407838351).setLongitude(-746143763).build();
        System.err.println(client.getFeature(point));
        client.shutdown();
    }
}
