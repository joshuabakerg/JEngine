package Networking.Client;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by joshua23 on 21/09/2016.
 */
public class ClientManager {

    private Map<String, Client> hashedClients = new HashMap<String, Client>();

    private String getIndex(DatagramPacket packet) {
        return getIndex(packet.getAddress().toString(),packet.getPort());
    }

    private String getIndex(String address,int port){
        return address+port;
    }

    public Client getClient(DatagramPacket packet) {
        String index = getIndex(packet);
        return hashedClients.get(index);
    }

    public boolean addClient(DatagramPacket packet) {
        return addClient(packet, "user_" + (hashedClients.size() + 1));
    }

    public boolean addClient(DatagramPacket packet, String name) {
        return addClient(packet.getAddress(),packet.getPort(),name);
    }

    public boolean addClient(InetAddress address, int port, String name) {
        String index = getIndex(address.toString(),port);
        Client tempClient = hashedClients.get(index);
        if (tempClient == null) {
            tempClient = new Client(name, address, port);
            hashedClients.put(index, tempClient);
            return true;
        } else {
            return false;
        }
    }

    public boolean addClient(Client client){
        String index = getIndex(client.address.toString(),client.port);
        Client tempClient = hashedClients.get(index);
        if(tempClient==null){
            hashedClients.put(index,client);
            return true;
        }else{
            return false;
        }
    }

    public void removeClient(Client client){
        removeClient(client.address.toString(),client.port);
    }

    public void removeClient(String address, int port){
        String index = getIndex(address,port);
        hashedClients.remove(index);
    }

    public boolean updateClient(Client client){
        String index = getIndex(client.address.toString(),client.port);
        Client tempClient = hashedClients.get(index);
        if(tempClient != null){
            hashedClients.put(index,client);
            return true;
        }else{
            return false;
        }
    }

}
