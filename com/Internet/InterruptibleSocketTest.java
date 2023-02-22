package com_second.Internet;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.InterruptibleChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class InterruptibleSocketTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            InterruptibleSocketFrame frame = new InterruptibleSocketFrame();
            frame.setTitle("InterruptibleSocketTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
class InterruptibleSocketFrame extends JFrame{
    private Scanner in;
    private JButton interruptibleButton;
    private JButton blockingButton;
    private JButton cancelButton;
    private JTextArea messages;
    private TestServer server;
    private Thread connectThread;

    public InterruptibleSocketFrame() {
        var northPanel = new JPanel();
        add(northPanel, BorderLayout.NORTH);

        final int TEXT_ROWS = 20;
        final int TEXT_CLOUMNS = 60;
        messages = new JTextArea(TEXT_ROWS, TEXT_CLOUMNS);
        add(new JScrollPane(messages));

        interruptibleButton = new JButton("Interruptible");
        blockingButton = new JButton("Blocking");

        northPanel.add(interruptibleButton);
        northPanel.add(blockingButton);

        interruptibleButton.addActionListener(event -> {
            interruptibleButton.setEnabled(false);
            blockingButton.setEnabled(false);
            cancelButton.setEnabled(true);
            connectThread = new Thread(() -> {
                try {
                    connectInterruptibly();
                } catch (IOException e) {
                    messages.append("\nInterruptibleSocketTest.connectInterruptibly: " + e);
                }
            });

        });

        blockingButton.addActionListener(event -> {
            interruptibleButton.setEnabled(false);
            blockingButton.setEnabled(false);
            cancelButton.setEnabled(true);
            connectThread = new Thread(() -> {
                try {
                    connectBlocking();
                } catch (IOException e) {
                    messages.append("\nInterruptibleSocketTest.connectBlocking: " + e);
                }
            });
            connectThread.start();
        });

        cancelButton = new JButton("Cancel");
        cancelButton.setEnabled(false);
        northPanel.add(cancelButton);
        cancelButton.addActionListener(event -> {
            connectThread.interrupt();
            cancelButton.setEnabled(false);
        });
        server = new TestServer();
        new Thread(server).start();
        pack();
    }
    class TestServer implements Runnable{
        @Override
        public void run() {
            try (var s = new ServerSocket(8189)){
                while (true){
                    Socket accept = s.accept();
                    TestServerHandler r = new TestServerHandler(accept);
                    new Thread(r).start();
                }
            }catch (IOException e){
                messages.append("\nTestServer.run: " + e);
            }
        }
    }

    /**
     *  This class handlers the client input for one server socket connection
     */
    class TestServerHandler implements Runnable{
        private Socket incoming;
        private int counter;

        /**
         * Constructs a handler
         * @param incoming socket
         */
        public TestServerHandler(Socket incoming) {
            this.incoming = incoming;
        }

        @Override
        public void run() {
            try {
                try {
                    OutputStream outputStream = incoming.getOutputStream();
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);
                    while (counter < 100) {
                        counter++;
                        if (counter <= 10) out.println(counter);
                        Thread.sleep(1000);
                    }
                } finally {
                    incoming.close();
                    messages.append("Closing Server\n");
                }
            }catch (Exception e){
                messages.append("\nTestServerHandler.run: " + e);
            }
        }
    }

    public void connectInterruptibly() throws IOException {
        messages.append("Interruptible:\n");
        try (SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost",8189))) {
            in = new Scanner(channel,StandardCharsets.UTF_8);
            while (!Thread.currentThread().isInterrupted()) {
                messages.append("reading ");
                if (in.hasNextLine()) {
                    String s = in.nextLine();
                    messages.append(s);
                    messages.append("\n");
                }
            }
        }finally {
            EventQueue.invokeLater(() -> {
                messages.append("Channel closed\n");
                interruptibleButton.setEnabled(true);
                blockingButton.setEnabled(true);
            });
        }
    }

    public void connectBlocking() throws IOException {
        messages.append("Blocking:\n");
        try (var sock = new Socket("localhost",8189)){
            in = new Scanner(sock.getInputStream(),StandardCharsets.UTF_8);
            while (!Thread.currentThread().isInterrupted()) {
                messages.append("reading ");
                if (in.hasNextLine()) {
                    String s = in.nextLine();
                    messages.append(s);
                    messages.append("\n");
                }
            }
        }finally {
            EventQueue.invokeLater(()->{
                messages.append("Socket closed\n");
                interruptibleButton.setEnabled(true);
                blockingButton.setEnabled(true);
            });
        }
    }
}

