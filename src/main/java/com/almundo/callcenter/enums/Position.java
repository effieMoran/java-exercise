package com.almundo.callcenter.enums;

/**
 * Created by User on 6/4/2019.
 */
public enum Position {
    OPERATOR(0),
    SUPERVISOR(1),
    MANAGER(2);

    private int order;

    Position(int order){
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
