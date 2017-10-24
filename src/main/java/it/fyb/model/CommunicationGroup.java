package it.fyb.model;

import java.util.List;

public class CommunicationGroup {

    private List<Communication> messages;
    private String name;

    public List<Communication> getMessages() {
        return messages;
    }

    public void setMessages(List<Communication> messages) {
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
