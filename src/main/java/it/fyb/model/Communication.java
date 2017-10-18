package it.fyb.model;

import java.math.BigInteger;
import java.util.Date;

public class Communication {

    private BigInteger id;
    private BigInteger sentTo;
    private BigInteger sentFrom;
    private String text;
    private long date; // Date for the event
    private long sendDate;
    private Boolean read;
    private String group; // Used to identify from the url

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getSentTo() {
        return sentTo;
    }

    public void setSentTo(BigInteger sentTo) {
        this.sentTo = sentTo;
    }

    public BigInteger getSentFrom() {
        return sentFrom;
    }

    public void setSentFrom(BigInteger sentFrom) {
        this.sentFrom = sentFrom;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getSendDate() {
        return sendDate;
    }

    public void setSendDate(long sendDate) {
        this.sendDate = sendDate;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
