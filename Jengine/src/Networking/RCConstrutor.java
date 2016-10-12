/*
 * Copyright Notice
 * ================
 * This file contains proprietary information of Discovery Health.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2016
 */
package Networking;

import com.joshuabakerg.raincloud.serialization.RCDatabase;
import com.joshuabakerg.raincloud.serialization.RCObject;
import com.joshuabakerg.raincloud.serialization.RCString;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joshua23 on 21/09/2016.
 */
public class RCConstrutor {

    public static void main(String[] args){
        RCDatabase database = new RCConstrutor().makeCommandPacket("value","connected");
        byte[] data = new byte[database.getSize()];
        database.getBytes(data,0);
/*        RCDatabase rcDatabase = new RCDatabase("JEngine_packet");
        RCObject command = new RCObject("command");
        RCString value;
        value = RCString.Create("value", "connect");
        command.addString(value);
        rcDatabase.addObject(command);
        database.getBytes(data,0);*/
        System.out.println();
    }

    public RCDatabase constructDatabase(Map<String,String> packet){
        RCDatabase result = new RCDatabase("JEngine_packet");
        RCObject commandObject = new RCObject("command");
        result.addObject(commandObject);
        String params = "";
        for(String key:packet.keySet()) {
            if(key.equals("command")) {
                commandObject.addString(RCString.Create("value", packet.get("command")));
            }else{
                commandObject.addString(RCString.Create(key,packet.get(key)));
                if(params.equals("")){
                    params += key;
                }else {
                    params += ","+key;
                }
            }
        }
        commandObject.addString(RCString.Create("params",params));

        return result;

    }

    public Map<String,String> getPacket(RCDatabase database){

        Map<String,String> result = new HashMap<>();

        String command = getCommandString(database,"value");
        result.put("command",command);
        String tempParamNames = getCommandString(database,"params");
        String[] paramNames = null;
        if(tempParamNames!=null){
            paramNames = tempParamNames.split(",");
            for(String value:paramNames){
                result.put(value,getCommandString(database,value));
            }
        }
        return result;

    }

    public RCDatabase makeCommandPacket(String name,String value){
        RCDatabase database = new RCDatabase("JEngine_packet");
        RCObject commandObject = new RCObject("command");
        commandObject.addString(RCString.Create(name,value));
        database.addObject(commandObject);
        return database;
    }

    public String getCommandString(RCDatabase database,String string){
        RCObject commandObject = database.findObject("command");
        if(commandObject!=null){
            RCString command = commandObject.findString(string);
            if(command!=null){
                return command.getString();
            }
        }
        return null;
    }

}
