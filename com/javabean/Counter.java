package com.javabean;

import java.io.Serializable;

/**
 * Created by bigwh on 2017/3/27.
 */
public class Counter implements Serializable {
    private int n;

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
