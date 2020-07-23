package com.yaodingw.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @auther yaoding
 * @date 2020/7/23 下午11:40
 */
public class BioServer {
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);

        while(true){
            final Socket socket = serverSocket.accept();
            System.out.printf("服务器收到链接请求");
            executorService.execute(new Runnable() {
                public void run() {
                    System.out.println("开始处理请求，处理线程 " + Thread.currentThread().getName());
                    handler(socket);

                }
            });

        }
    }

    public static void handler(Socket socket) {
        byte[] bytes = new byte[1024];
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            while (true) {
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                }else{
                    System.out.println("客户端链接丢失，线程退出"+ Thread.currentThread().getName());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
