package com.oneplus.opcameratest;

/**
 * Created by Administrator on 2016/5/17.
 */
public class CamParameter {
    public String Name = null;
    public String Id = null;
    public String Type = null;
    public boolean State = false;
    public boolean Enable = false;

//    public CamParameter( String Name, String Id, String Type, boolean State ) {
//        this.Name = Name;
//        this.Id = Id;
//        this.Type = Type;
//        this.State = State;
//    }

    public String getName() {
        return Name;
    }

    public String getId() {
        return Id;
    }

    public String getType() {
        return Type;
    }

    public boolean getState() {
        return State;
    }

    public boolean isEnable() {
        return Enable;
    }
}
