package server;

import common.Data;

public class DataImpl implements Data {

    private String data;
    @Override
    public String getData() {
        return data;
    }

    @Override
    public void setData(String value) {
        System.out.println(this + ": receveid " + value);
        this.data = value;
    }
}
