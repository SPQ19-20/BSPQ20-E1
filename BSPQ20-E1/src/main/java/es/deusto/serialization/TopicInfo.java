package es.deusto.serialization;

import es.deusto.server.data.Topic;

/**
 * DTO equivalent class of the {@link Topic} class from the server.
 * This class is used to transport all the relevant information of a
 * topic through the network.
 * 
 * @since Sprint 1
 */
public class TopicInfo {

    private String name;
    
    public TopicInfo() {
        this.name = "";
    }
    public TopicInfo(String name) {
        this.name = name;
    }

    public TopicInfo(Topic c) {
        this.name = c.getName();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    /**
     * Creates a {@link Topic} object from a {@link TopicInfo} one
     * @param topicInfo the source of the information
     * @return an Object {@link Topic}
     */
    public static Topic parseTopic(TopicInfo topicInfo){
        Topic t = new Topic();
        t.setName(topicInfo.getName());
        return t;
    }

    @Override
    public String toString() {
        return this.name;
    }

}