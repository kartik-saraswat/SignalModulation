package com.example.root.signalmodulation.elements;

import org.rajawali3d.math.MathUtil;

/**
 * Created by root on 29/3/16.
 */
public class Analog {

    public static int MIN_AMPLITUDE = 1;
    public static int MAX_AMPLITUDE = 3;

    public static int BANDWIDTH = 10;
    public static int MIN_FREQUENCY = 1;
    public static int MAX_FREQUENCY = MIN_FREQUENCY + BANDWIDTH - 1;

    public static float SCALE_X = 4f;
    public static int CARRIER_WIDTH_FACTOR = 5;
    public static int CARRIER_WIDTH_FACTOR_FREQ = 8;
    public static float CARRIER_AMPLITUDE_FACTOR = 1.5f;

    private float amplitude;
    private int color;

    private float frequency;
    public Analog(float amplitude, float frequency ,int color) {
        setAmplitude(amplitude);
        setFrequency(frequency);
        this.color = color;
    }

    synchronized public float getAmplitude() {
        return amplitude;
    }

    synchronized public void setAmplitude(float amplitude) {
        this.amplitude = (float)MathUtil.clamp(amplitude, MIN_AMPLITUDE, MAX_AMPLITUDE);
    }

    synchronized public int getColor() {
        return color;
    }

    synchronized public void setColor(int color) {
        this.color = color;
    }

    synchronized public float getFrequency() {
        return frequency;
    }

    synchronized public void setFrequency(float frequency) {
        this.frequency = (float)MathUtil.clamp(frequency, MIN_FREQUENCY, MAX_FREQUENCY);
    }

    synchronized public float getScaledWidth(){
        return ((SCALE_X*BANDWIDTH)/this.getFrequency());
    }
}
