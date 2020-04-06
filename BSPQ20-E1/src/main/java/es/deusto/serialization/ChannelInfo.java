package es.deusto.serialization;

import es.deusto.server.data.Channel;

public class ChannelInfo {

    private String name;
    
    public ChannelInfo() {
        this.name = "";
    }

    public ChannelInfo(Channel c) {
        this.name = c.getName();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}