/*
 * Copyright Notice
 * ================
 * This file contains proprietary information of Discovery Health.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2016
 */
package Networking;

import Networking.Client.Client;
import com.joshuabakerg.raincloud.serialization.RCDatabase;
import com.joshuabakerg.raincloud.serialization.RCObject;
import com.joshuabakerg.raincloud.serialization.RCString;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by joshua23 on 21/09/2016.
 */
public class NetworkHandler {

    private Client receiver;
    private DatagramSocket socket;
    private Thread receive;
    private boolean expectingServerResponse = false;
    private boolean connectedToServer = false;
    private boolean running = false;

    public NetworkHandler(InetAddress receiverAddress, int receiverPort) {
        receiver = new Client("receiver", receiverAddress, receiverPort);
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public boolean connect(String name) {
        receive();
        RCDatabase rcDatabase = new RCDatabase("JEngine_packet");
        RCObject command = new RCObject("command");
        RCString value;
        value = RCString.Create("value", "connect");
        command.addString(value);
        command.addString(RCString.Create("name", "joshua"));
        rcDatabase.addObject(command);
        expectingServerResponse = true;
        send(rcDatabase);
        double startTime = System.currentTimeMillis();
        while (true) {
            if (connectedToServer) return true;
            if ((System.currentTimeMillis() - startTime) / 1000 > 5) {
                running = false;
                return false;
            }
        }
    }

    public boolean disconnect() {
        RCConstrutor rcc = new RCConstrutor();
        if (!connectedToServer) return false;
        expectingServerResponse = true;
        send(rcc.makeCommandPacket("value","disconnect"));
        double startTime = System.currentTimeMillis();
        while (true) {
            if (!connectedToServer){
                running = false;
                return true;
            }
            if ((System.currentTimeMillis() - startTime) / 1000 > 5) return false;

        }
    }

    private final void receive() {
        running = true;
        receive = new Thread("Receive") {
            public void run() {
                System.out.println("Listening ...");
                byte[] buffer = new byte[2048];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                while (running) {
                    try {
                        socket.receive(packet);
                        preProcess(packet);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        receive.start();
    }

    private final void preProcess(DatagramPacket packet) {
        byte[] data = packet.getData();
        if (new String(data, 0, 4).equals("RCDB")) {
            RCDatabase database = RCDatabase.Deserialize(data);
            if (expectingServerResponse) {
                if (isConnectedResponse(database, packet)){
                    expectingServerResponse = false;
                    connectedToServer = true;
                }
                else if(isDisonnectedResponse(database,packet)){
                    expectingServerResponse = false;
                    connectedToServer = false;
                }

            } else {
                process(database);

            }
        } else {

        }
    }

    public void process(RCDatabase database) {

    }

    private boolean isConnectedResponse(RCDatabase database, DatagramPacket packet) {
        if (receiver.address.equals(packet.getAddress()) && receiver.port == packet.getPort()) {
            if(response(database).equals("connected"))return true;
        }
        return false;
    }

    private boolean isDisonnectedResponse(RCDatabase database, DatagramPacket packet) {
        if (receiver.address.equals(packet.getAddress()) && receiver.port == packet.getPort()) {
            if(response(database).equals("disconnected"))return true;
        }
        return false;
    }

    private String response(RCDatabase database){
        RCObject commandObject = database.findObject("command");
        if (commandObject != null) {
            RCString command = commandObject.findString("value");
            if (command != null) {
                return  command.getString();
            }
        }
        return null;
    }

    public void send(RCDatabase database) {
        byte[] data = new byte[database.getSize()];
        database.getBytes(data, 0);
        send(data);
    }

    public void send(String message) {
        byte[] data = message.getBytes();
        send(data);
    }

    public void send(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length, receiver.address, receiver.port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
