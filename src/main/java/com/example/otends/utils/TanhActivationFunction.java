package com.example.otends.utils;

import com.example.otends.utils.skeletons.IActivationFunction;

import java.io.Serializable;

public class TanhActivationFunction implements IActivationFunction, Serializable {
    private static final long serialVersionUID = 4L;

    @Override
    public double func(double x) {
        return Math.tanh(x);
    }

    @Override
    public double dfunc(double y) {
        return 1 - (y * y);
    }
}
