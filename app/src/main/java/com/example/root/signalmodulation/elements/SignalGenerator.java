package com.example.root.signalmodulation.elements;

import org.rajawali3d.curves.CatmullRomCurve3D;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Line3D;

import java.util.Stack;

/**
 * Created by root on 29/3/16.
 */
public class SignalGenerator {

    synchronized public static Line3D generateAnalogSignal(Analog signal, Vector3 position){
        Vector3 temp = new Vector3();
        float sWidth = signal.getScaledWidth();
        int noOfPeriods = (int)(ModulationModel.AXIS_LENGTH/sWidth);
        int noOfSegments = noOfPeriods*16;

        temp.setAll(position.x, position.y - 0.1f, position.z);
        CatmullRomCurve3D curve3D = new CatmullRomCurve3D();
        curve3D.addPoint(new Vector3(temp));
        temp.setAll(position);

        float amplitude = signal.getAmplitude();
        float d = sWidth/4;
        for(int i = 0; i <= noOfPeriods; i++){
            curve3D.addPoint(new Vector3(temp));
            temp.setAll(temp.x + d, temp.y + amplitude, position.z);
            curve3D.addPoint(new Vector3(temp));
            temp.setAll(temp.x + d, temp.y - amplitude, position.z);
            curve3D.addPoint(new Vector3(temp));
            temp.setAll(temp.x + d, temp.y - amplitude, position.z);
            curve3D.addPoint(new Vector3(temp));
            temp.setAll(temp.x + d, temp.y + amplitude, position.z);
        }
        curve3D.addPoint(new Vector3(temp));
        curve3D.addPoint(new Vector3(temp.x, temp.y + 0.1f, temp.z));

        Stack<Vector3> points = new Stack<>();
        Vector3 tmpVec3;
        for(int i=0; i < noOfSegments; i++){
            tmpVec3 = new Vector3();
            curve3D.calculatePoint(tmpVec3, i / (float) noOfSegments);
            points.push(tmpVec3);
        }


        Line3D line3D = new Line3D(points, 1,  signal.getColor());
        Material material = new Material();
        material.enableLighting(true);
        material.setDiffuseMethod(new DiffuseMethod.Lambert());
        line3D.setMaterial(material);

        return line3D;
    }

    synchronized public static Line3D generateAnalogCarrier(Analog signal, Vector3 position){
        Vector3 temp = new Vector3();
        float sWidth = signal.getScaledWidth();
        float cWidth = sWidth/Analog.CARRIER_WIDTH_FACTOR;
        int noOfPeriods = (int)(ModulationModel.AXIS_LENGTH/cWidth);
        int noOfSegments = noOfPeriods*16;

        temp.setAll(position.x, position.y - 0.1f, position.z);
        CatmullRomCurve3D curve3D = new CatmullRomCurve3D();
        curve3D.addPoint(new Vector3(temp));
        temp.setAll(position);

        float amplitude = signal.getAmplitude()*Analog.CARRIER_AMPLITUDE_FACTOR;
        float d = cWidth/4;
        for(int i = 0; i <= noOfPeriods; i++){
            curve3D.addPoint(new Vector3(temp));
            temp.setAll(temp.x + d, temp.y + amplitude, position.z);
            curve3D.addPoint(new Vector3(temp));
            temp.setAll(temp.x + d, temp.y - amplitude, position.z);
            curve3D.addPoint(new Vector3(temp));
            temp.setAll(temp.x + d, temp.y - amplitude, position.z);
            curve3D.addPoint(new Vector3(temp));
            temp.setAll(temp.x + d, temp.y + amplitude, position.z);
        }
        curve3D.addPoint(new Vector3(temp));
        curve3D.addPoint(new Vector3(temp.x, temp.y + 0.1f, temp.z));

        Stack<Vector3> points = new Stack<>();
        Vector3 tmpVec3;
        for(int i=0; i < noOfSegments; i++){
            tmpVec3 = new Vector3();
            curve3D.calculatePoint(tmpVec3, i / (float) noOfSegments);
            points.push(tmpVec3);
        }


        Line3D line3D = new Line3D(points, 1,  signal.getColor());
        Material material = new Material();
        material.enableLighting(true);
        material.setDiffuseMethod(new DiffuseMethod.Lambert());
        line3D.setMaterial(material);

        return line3D;
    }

    synchronized public static Line3D generateAnalogAmplitudeModulated(Analog signal, Vector3 position){
        Vector3 temp = new Vector3();
        float sWidth = signal.getScaledWidth();
        float cWidth = sWidth/Analog.CARRIER_WIDTH_FACTOR;
        int noOfPeriods = (int)(ModulationModel.AXIS_LENGTH/cWidth);
        int noOfSegments = noOfPeriods*16;

        temp.setAll(position.x, position.y - 0.1f, position.z);
        CatmullRomCurve3D curve3D = new CatmullRomCurve3D();
        curve3D.addPoint(new Vector3(temp));
        temp.setAll(position);

        float fixedAmplitude = signal.getAmplitude()*Analog.CARRIER_AMPLITUDE_FACTOR;
        float ampFactor = fixedAmplitude/Analog.CARRIER_WIDTH_FACTOR;
        float amplitude;
        float d = cWidth/4;
        boolean isCrustCycle = true;
        for(int i = 0; i <= noOfPeriods; i++){
            int x = (i+1)%Analog.CARRIER_WIDTH_FACTOR;

            if( x > (Analog.CARRIER_WIDTH_FACTOR/2) ){
                x = Analog.CARRIER_WIDTH_FACTOR - x;
            } else if( x == 0){
                isCrustCycle = !isCrustCycle;
            }
            float ampDelta = ampFactor*x;
            amplitude = fixedAmplitude + (isCrustCycle?+ampDelta:-ampDelta);
            curve3D.addPoint(new Vector3(temp));
            temp.setAll(temp.x + d, temp.y + amplitude, position.z);
            curve3D.addPoint(new Vector3(temp));
            temp.setAll(temp.x + d, temp.y - amplitude, position.z);
            curve3D.addPoint(new Vector3(temp));
            temp.setAll(temp.x + d, temp.y - amplitude, position.z);
            curve3D.addPoint(new Vector3(temp));
            temp.setAll(temp.x + d, temp.y + amplitude, position.z);
        }
        curve3D.addPoint(new Vector3(temp));
        curve3D.addPoint(new Vector3(temp.x, temp.y + 0.1f, temp.z));

        Stack<Vector3> points = new Stack<>();
        Vector3 tmpVec3;
        for(int i=0; i < noOfSegments; i++){
            tmpVec3 = new Vector3();
            curve3D.calculatePoint(tmpVec3, i / (float) noOfSegments);
            points.push(tmpVec3);
        }


        Line3D line3D = new Line3D(points, 1,  signal.getColor());
        Material material = new Material();
        material.enableLighting(true);
        material.setDiffuseMethod(new DiffuseMethod.Lambert());
        line3D.setMaterial(material);

        return line3D;
    }

    synchronized public static Line3D generateAnalogFrequencyModulated(Analog signal, Vector3 position){
        Vector3 temp = new Vector3();
        float sWidth = signal.getScaledWidth();
        float cWidth = sWidth/Analog.CARRIER_WIDTH_FACTOR_FREQ;
        float pWidth = cWidth/2;
        float nWidth = cWidth;
        int noOfPeriods = (int)(2*ModulationModel.AXIS_LENGTH/(pWidth+nWidth));
        int noOfSegments = noOfPeriods*16;

        temp.setAll(position.x, position.y - 0.1f, position.z);
        CatmullRomCurve3D curve3D = new CatmullRomCurve3D();
        curve3D.addPoint(new Vector3(temp));
        temp.setAll(position);

        float amplitude = signal.getAmplitude()*Analog.CARRIER_AMPLITUDE_FACTOR;
        float d;
        boolean isCrustCycle = true;
        for(int i = 0, k = 0; i <= noOfPeriods; i++){
            k++;
            if(isCrustCycle && (k%Analog.CARRIER_WIDTH_FACTOR_FREQ == 0)){
                k = 0;
                isCrustCycle = false;
            } else if((!isCrustCycle) && (k%(Analog.CARRIER_WIDTH_FACTOR_FREQ/2) == 0)){
                k = 0;
                isCrustCycle = true;
            }

            if(isCrustCycle){
                d = pWidth/4;
            } else {
                d = nWidth/4;
            }

            curve3D.addPoint(new Vector3(temp));
            temp.setAll(temp.x + d, temp.y + amplitude, position.z);
            curve3D.addPoint(new Vector3(temp));
            temp.setAll(temp.x + d, temp.y - amplitude, position.z);
            curve3D.addPoint(new Vector3(temp));
            temp.setAll(temp.x + d, temp.y - amplitude, position.z);
            curve3D.addPoint(new Vector3(temp));
            temp.setAll(temp.x + d, temp.y + amplitude, position.z);
        }
        curve3D.addPoint(new Vector3(temp));
        curve3D.addPoint(new Vector3(temp.x, temp.y + 0.1f, temp.z));

        Stack<Vector3> points = new Stack<>();
        Vector3 tmpVec3;
        for(int i=0; i < noOfSegments; i++){
            tmpVec3 = new Vector3();
            curve3D.calculatePoint(tmpVec3, i / (float) noOfSegments);
            points.push(tmpVec3);
        }


        Line3D line3D = new Line3D(points, 1,  signal.getColor());
        Material material = new Material();
        material.enableLighting(true);
        material.setDiffuseMethod(new DiffuseMethod.Lambert());
        line3D.setMaterial(material);

        return line3D;
    }

    synchronized public static Line3D generateDigitalCarrier(Analog signal, Vector3 position){
        Vector3 temp = new Vector3();
        float sWidth = signal.getScaledWidth();
        float cWidth = sWidth/Analog.CARRIER_WIDTH_FACTOR;
        int noOfPeriods = (int)(2*ModulationModel.AXIS_LENGTH/cWidth);

        temp.setAll(position);

        float amplitude = signal.getAmplitude();
        float d = cWidth/4;

        Stack<Vector3> points = new Stack<>();
        Vector3 tmpVec3;
        tmpVec3 = new Vector3(temp);
        points.push(tmpVec3);
        for(int i=0; i < noOfPeriods; i++){

            tmpVec3 = new Vector3(temp.x + d, temp.y, temp.z);
            points.push(tmpVec3);

            tmpVec3 = new Vector3(temp.x + d, temp.y + amplitude, temp.z);
            points.push(tmpVec3);

            tmpVec3 = new Vector3(temp.x + 2*d, temp.y + amplitude, temp.z);
            points.push(tmpVec3);

            tmpVec3 = new Vector3(temp.x + 2*d, temp.y, temp.z);
            points.push(tmpVec3);
            temp.setAll(tmpVec3);
        }

        Line3D line3D = new Line3D(points, 1,  signal.getColor());
        Material material = new Material();
        material.enableLighting(true);
        material.setDiffuseMethod(new DiffuseMethod.Lambert());
        line3D.setMaterial(material);

        return line3D;
    }

    synchronized public static Line3D generateDigitalModulated(Analog signal, Vector3 position){
        Vector3 temp = new Vector3();
        float sWidth = signal.getScaledWidth();
        float cWidth = sWidth/Analog.CARRIER_WIDTH_FACTOR;
        int noOfPeriods = (int)(2*ModulationModel.AXIS_LENGTH/cWidth);

        temp.setAll(position);

        float fixedAmplitude = signal.getAmplitude();
        float ampFactor = fixedAmplitude/Analog.CARRIER_WIDTH_FACTOR;
        float amplitude;
        boolean isCrustCycle = true;
        float d = cWidth/4;

        Stack<Vector3> points = new Stack<>();
        Vector3 tmpVec3;
        tmpVec3 = new Vector3(temp);
        points.push(tmpVec3);
        for(int i=0; i < noOfPeriods; i++){

            int x = (i+1)%Analog.CARRIER_WIDTH_FACTOR;

            if( x > (Analog.CARRIER_WIDTH_FACTOR/2) ){
                x = Analog.CARRIER_WIDTH_FACTOR - x;
            } else if( x == 0){
                isCrustCycle = !isCrustCycle;
            }
            float ampDelta = ampFactor*x;
            amplitude = fixedAmplitude + (isCrustCycle?+ampDelta:-ampDelta);

            tmpVec3 = new Vector3(temp.x + d, temp.y, temp.z);
            points.push(tmpVec3);

            tmpVec3 = new Vector3(temp.x + d, temp.y + amplitude, temp.z);
            points.push(tmpVec3);

            tmpVec3 = new Vector3(temp.x + 2*d, temp.y + amplitude, temp.z);
            points.push(tmpVec3);

            tmpVec3 = new Vector3(temp.x + 2*d, temp.y, temp.z);
            points.push(tmpVec3);
            temp.setAll(tmpVec3);
        }

        Line3D line3D = new Line3D(points, 1,  signal.getColor());
        Material material = new Material();
        material.enableLighting(true);
        material.setDiffuseMethod(new DiffuseMethod.Lambert());
        line3D.setMaterial(material);

        return line3D;
    }
}

