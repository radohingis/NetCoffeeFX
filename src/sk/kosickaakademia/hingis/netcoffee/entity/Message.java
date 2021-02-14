package sk.kosickaakademia.hingis.netcoffee.entity;

import java.sql.Date;

public class Message {
    private int id;
    private String from;
    private String to;
    private Date date;
    private String text;

    public Message(int id, String from, String to, Date date, String text) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.date = date;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }
}
