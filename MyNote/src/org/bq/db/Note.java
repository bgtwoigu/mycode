package org.bq.db;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table NOTE.
 */
public class Note {

    private Long id;
    /** Not-null value. */
    private String text;
    private Integer type;
    private Integer widget;
    private java.util.Date date;

    public Note() {
    	this.date= new java.util.Date();
    }

    public Note(Long id) {
        this.id = id;
        this.date= new java.util.Date();
    }

    public Note(Long id, String text, Integer type, Integer widget, java.util.Date date) {
        this.id = id;
        this.text = text;
        this.type = type;
        this.widget = widget;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getText() {
        return text;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setText(String text) {
        this.text = text;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getWidget() {
        return widget;
    }

    public void setWidget(Integer widget) {
        this.widget = widget;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

}