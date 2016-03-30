package com.example.root.signalmodulation.elements;

import android.graphics.Color;

import com.example.root.signalmodulation.R;

import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.AlphaMapTexture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Line3D;
import org.rajawali3d.primitives.Plane;

import java.util.Stack;

/**
 * Created by root on 27/3/16.
 */
public class ModulationModel {

    public static final float MAX_AMPLITUDE = 6.0f;
    public static final float MIN_AMPLITUDE = 0.2f;

    public static final int MIN_NO_OF_PERIODS = 10;
    public static final float MARGIN_VERTICAL = 1f;
    public static final float MARGIN_HORIZONTAL = 1f;
    public static final float LEFT_SECTION_WIDTH = 10f;
    public static final float SCREEN_WIDTH = 40;
    public static final float SCREEN_HEIGHT = 6*MARGIN_VERTICAL + 5*MAX_AMPLITUDE;

    public static final float AXIS_LENGTH = SCREEN_WIDTH - LEFT_SECTION_WIDTH - MARGIN_HORIZONTAL;

    Vector3 verticalSeperatorPoint_Top;
    Vector3 verticalSeperatorPoint_Middle;
    Vector3 verticalSeperatorPoint_Bottom;

    Vector3 carrierWaveVerticalPoint_Top;
    public Vector3 carrierWaveVerticalPoint_Middle;
    Vector3 carrierWaveVerticalPoint_Bottom;
    Vector3 carrierWaveHorizontalPoint_Left;
    Vector3 carrierWaveHorizontalPoint_Right;

    Vector3 signalWaveVerticalPoint_Top;
    public Vector3 signalWaveVerticalPoint_Middle;
    Vector3 signalWaveVerticalPoint_Bottom;
    Vector3 signalWaveHorizontalPoint_Left;
    Vector3 signalWaveHorizontalPoint_Right;

    Vector3 modulatedWaveVerticalPoint_Top;
    public Vector3 modulatedWaveVerticalPoint_Middle;
    Vector3 modulatedWaveVerticalPoint_Bottom;
    Vector3 modulatedWaveHorizontalPoint_Left;
    Vector3 modulatedWaveHorizontalPoint_Right;

    Stack<Vector3> verticalSeparatorPointStack;
    Stack<Vector3> carrierWaveVerticalPointStack;
    Stack<Vector3> carrierWaveHorizontalPointStack;

    Stack<Vector3> signalWaveVerticalPointStack;
    Stack<Vector3> signalWaveHorizontalPointStack;

    Stack<Vector3> modulatedWaveVerticalPointStack;
    Stack<Vector3> modulatedWaveHorizontalPointStack;

    public Line3D veritcalSeparatorLine;
    public Line3D carrierVerticalLine;
    public Line3D carrierHorizontalLine;

    public Line3D signalVerticalLine;
    public Line3D signalHorizontalLine;

    public Line3D modulatedVerticalLine;
    public Line3D modulatedHorizontalLine;
    public Plane carrierPlane;
    public Plane signalPlane;
    public Plane amPlane;
    public Plane fmPlane;
    public Plane pamPlane;

    public int color = Color.GREEN;
    public void initialize(){
        initPositions();
        initPointStacks();
        initLines();
        initLabels();
    }

    public void initPositions(){
        float leftX =  -SCREEN_WIDTH/2 + LEFT_SECTION_WIDTH ;
        float topY = SCREEN_HEIGHT/2 ;

        verticalSeperatorPoint_Top = new Vector3(leftX,topY,0);
        verticalSeperatorPoint_Middle = new Vector3(verticalSeperatorPoint_Top.x, 0, 0);
        verticalSeperatorPoint_Bottom = new Vector3(verticalSeperatorPoint_Top.x, -SCREEN_HEIGHT/2, 0);

        carrierWaveVerticalPoint_Top =  new Vector3(verticalSeperatorPoint_Top.x +MARGIN_HORIZONTAL,verticalSeperatorPoint_Top.y - MARGIN_VERTICAL,0);
        carrierWaveVerticalPoint_Middle =  new Vector3(carrierWaveVerticalPoint_Top.x,carrierWaveVerticalPoint_Top.y - MAX_AMPLITUDE,0);
        carrierWaveVerticalPoint_Bottom =  new Vector3(carrierWaveVerticalPoint_Middle.x,carrierWaveVerticalPoint_Middle.y - MAX_AMPLITUDE,0);

        carrierWaveHorizontalPoint_Left =  new Vector3(carrierWaveVerticalPoint_Middle);
        carrierWaveHorizontalPoint_Right = new Vector3(SCREEN_WIDTH/2,carrierWaveHorizontalPoint_Left.y,0);

        signalWaveVerticalPoint_Top =  new Vector3(carrierWaveVerticalPoint_Bottom.x ,carrierWaveVerticalPoint_Bottom.y - 2*MARGIN_VERTICAL,0);
        signalWaveVerticalPoint_Middle =  new Vector3(signalWaveVerticalPoint_Top.x ,signalWaveVerticalPoint_Top.y - MAX_AMPLITUDE/2,0);
        signalWaveVerticalPoint_Bottom =  new Vector3(signalWaveVerticalPoint_Top.x ,signalWaveVerticalPoint_Middle.y - MAX_AMPLITUDE/2,0);

        signalWaveHorizontalPoint_Left =  new Vector3(signalWaveVerticalPoint_Middle);
        signalWaveHorizontalPoint_Right = new Vector3(SCREEN_WIDTH/2,signalWaveHorizontalPoint_Left.y,0);

        //Allowed Amplitude is twice of that of signal and carrier
        modulatedWaveVerticalPoint_Top =  new Vector3(signalWaveVerticalPoint_Bottom.x ,signalWaveVerticalPoint_Bottom.y - 2*MARGIN_VERTICAL,0);
        modulatedWaveVerticalPoint_Middle =  new Vector3(modulatedWaveVerticalPoint_Top.x ,modulatedWaveVerticalPoint_Top.y - (MAX_AMPLITUDE),0);
        modulatedWaveVerticalPoint_Bottom =  new Vector3(modulatedWaveVerticalPoint_Middle.x ,modulatedWaveVerticalPoint_Middle.y - MAX_AMPLITUDE,0);

        modulatedWaveHorizontalPoint_Left =  new Vector3(modulatedWaveVerticalPoint_Middle);
        modulatedWaveHorizontalPoint_Right = new Vector3(SCREEN_WIDTH/2,modulatedWaveHorizontalPoint_Left.y,0);

    }

    public void initPointStacks(){

        verticalSeparatorPointStack =  new Stack<>();
        verticalSeparatorPointStack.push(verticalSeperatorPoint_Top);
        verticalSeparatorPointStack.push(verticalSeperatorPoint_Middle);
        verticalSeparatorPointStack.push(verticalSeperatorPoint_Bottom);

        carrierWaveVerticalPointStack =  new Stack<>();
        carrierWaveVerticalPointStack.push(carrierWaveVerticalPoint_Top);
        carrierWaveVerticalPointStack.push(carrierWaveVerticalPoint_Middle);
        carrierWaveVerticalPointStack.push(carrierWaveVerticalPoint_Bottom);

        carrierWaveHorizontalPointStack =  new Stack<>();
        carrierWaveHorizontalPointStack.push(carrierWaveHorizontalPoint_Left);
        carrierWaveHorizontalPointStack.push(carrierWaveHorizontalPoint_Right);

        signalWaveVerticalPointStack =  new Stack<>();
        signalWaveVerticalPointStack.push(signalWaveVerticalPoint_Top);
        signalWaveVerticalPointStack.push(signalWaveVerticalPoint_Middle);
        signalWaveVerticalPointStack.push(signalWaveVerticalPoint_Bottom);

        signalWaveHorizontalPointStack =  new Stack<>();
        signalWaveHorizontalPointStack.push(signalWaveHorizontalPoint_Left);
        signalWaveHorizontalPointStack.push(signalWaveHorizontalPoint_Right);

        modulatedWaveVerticalPointStack =  new Stack<>();
        modulatedWaveVerticalPointStack.push(modulatedWaveVerticalPoint_Top);
        modulatedWaveVerticalPointStack.push(modulatedWaveVerticalPoint_Middle);
        modulatedWaveVerticalPointStack.push(modulatedWaveVerticalPoint_Bottom);

        modulatedWaveHorizontalPointStack =  new Stack<>();
        modulatedWaveHorizontalPointStack.push(modulatedWaveHorizontalPoint_Left);
        modulatedWaveHorizontalPointStack.push(modulatedWaveHorizontalPoint_Right);
    }


    public void initLines(){
        veritcalSeparatorLine =  new Line3D(verticalSeparatorPointStack,1, color);
        Material sepMat = new Material();
        sepMat.enableLighting(true);
        sepMat.setDiffuseMethod(new DiffuseMethod.Lambert());
        veritcalSeparatorLine.setMaterial(sepMat);

        carrierVerticalLine =  new Line3D(carrierWaveVerticalPointStack,1.2f,color);
        Material carVertMat = new Material();
        carVertMat.enableLighting(true);
        carVertMat.setDiffuseMethod(new DiffuseMethod.Lambert());
        carrierVerticalLine.setMaterial(carVertMat);

        carrierHorizontalLine =  new Line3D(carrierWaveHorizontalPointStack,1.2f,color);
        Material carHorzMat = new Material();
        carHorzMat.enableLighting(true);
        carHorzMat.setDiffuseMethod(new DiffuseMethod.Lambert());
        carrierHorizontalLine.setMaterial(carHorzMat);

        signalVerticalLine =  new Line3D(signalWaveVerticalPointStack,1.2f,color);
        Material sigVertMat = new Material();
        sigVertMat.enableLighting(true);
        sigVertMat.setDiffuseMethod(new DiffuseMethod.Lambert());
        signalVerticalLine.setMaterial(sigVertMat);

        signalHorizontalLine =  new Line3D(signalWaveHorizontalPointStack,1.2f,color);
        Material sigHorzMat = new Material();
        sigHorzMat.enableLighting(true);
        sigHorzMat.setDiffuseMethod(new DiffuseMethod.Lambert());
        signalHorizontalLine.setMaterial(sigHorzMat);

        modulatedVerticalLine =  new Line3D(modulatedWaveVerticalPointStack,1.2f,color);
        Material modVertMat = new Material();
        modVertMat.enableLighting(true);
        modVertMat.setDiffuseMethod(new DiffuseMethod.Lambert());
        modulatedVerticalLine.setMaterial(modVertMat);

        modulatedHorizontalLine =  new Line3D(modulatedWaveHorizontalPointStack,1.2f,color);
        Material modHorzMat = new Material();
        modHorzMat.enableLighting(true);
        modHorzMat.setDiffuseMethod(new DiffuseMethod.Lambert());
        modulatedHorizontalLine.setMaterial(modHorzMat);
    }


    private void initLabels(){

        float leftX = -ModulationModel.SCREEN_WIDTH/2 + ModulationModel.LEFT_SECTION_WIDTH/2;
        float planeSize = ModulationModel.LEFT_SECTION_WIDTH - 2*MARGIN_HORIZONTAL;
        Vector3 tempV =  new Vector3(0);

        carrierPlane = new Plane(planeSize, planeSize,1,1);
        carrierPlane.setDoubleSided(true);

        Material carrierPlaneMaterial = new Material();
        carrierPlaneMaterial.setColorInfluence(1f);
        AlphaMapTexture texture = new AlphaMapTexture("carrier", R.drawable.carrier);
        try {
            carrierPlaneMaterial.addTexture(texture);
        } catch (ATexture.TextureException e) {
            e.printStackTrace();
        }

        tempV.setAll(carrierWaveVerticalPoint_Middle);
        carrierPlane.setColor(color);
        carrierPlane.setPosition(leftX, tempV.y, tempV.z);
        carrierPlane.setMaterial(carrierPlaneMaterial);
        carrierPlane.rotate(Vector3.Axis.Y, 180);

        signalPlane= new Plane( planeSize, planeSize,1,1);
        signalPlane.setDoubleSided(true);

        Material signalPlaneMaterial = new Material();
        signalPlaneMaterial.setColorInfluence(1);
        AlphaMapTexture signalTexture = new AlphaMapTexture("signal", R.drawable.signal);
        try {
            signalPlaneMaterial.addTexture(signalTexture);
        } catch (ATexture.TextureException e) {
            e.printStackTrace();
        }

        tempV.setAll(signalWaveVerticalPoint_Middle);
        signalPlane.setColor(color);
        signalPlane.setPosition(leftX, tempV.y, tempV.z);
        signalPlane.setMaterial(signalPlaneMaterial);
        signalPlane.rotate(Vector3.Axis.Y, 180);

        amPlane = new Plane( planeSize, planeSize,1,1);
        amPlane.setDoubleSided(true);

        Material amPlaneMaterial = new Material();
        amPlaneMaterial.setColorInfluence(1);
        AlphaMapTexture amPlaneTexture = new AlphaMapTexture("modulation_am", R.drawable.am);

        try {
            amPlaneMaterial.addTexture(amPlaneTexture);
        } catch (ATexture.TextureException e) {
            e.printStackTrace();
        }
        tempV.setAll(modulatedWaveVerticalPoint_Middle);
        amPlane.setColor(color);
        amPlane.setPosition(leftX, tempV.y, tempV.z);
        amPlane.setMaterial(amPlaneMaterial);
        amPlane.rotate(Vector3.Axis.Y, 180);

        fmPlane = new Plane( planeSize, planeSize,1,1);
        fmPlane.setDoubleSided(true);

        Material fmPlaneMaterial = new Material();
        fmPlaneMaterial.setColorInfluence(1);
        AlphaMapTexture fmPlaneTexture = new AlphaMapTexture("modulation_am", R.drawable.fm);

        try {
            fmPlaneMaterial.addTexture(fmPlaneTexture);
        } catch (ATexture.TextureException e) {
            e.printStackTrace();
        }
        tempV.setAll(modulatedWaveVerticalPoint_Middle);
        fmPlane.setColor(color);
        fmPlane.setPosition(leftX, tempV.y, tempV.z);
        fmPlane.setMaterial(fmPlaneMaterial);
        fmPlane.rotate(Vector3.Axis.Y, 180);

        pamPlane = new Plane( planeSize, planeSize,1,1);
        pamPlane.setDoubleSided(true);

        Material pamPlaneMaterial = new Material();
        pamPlaneMaterial.setColorInfluence(1);
        AlphaMapTexture pamPlaneTexture = new AlphaMapTexture("modulation_pam", R.drawable.pam);

        try {
            pamPlaneMaterial.addTexture(pamPlaneTexture);
        } catch (ATexture.TextureException e) {
            e.printStackTrace();
        }
        tempV.setAll(modulatedWaveVerticalPoint_Middle);
        pamPlane.setColor(color);
        pamPlane.setPosition(leftX, tempV.y, tempV.z);
        pamPlane.setMaterial(pamPlaneMaterial);
        pamPlane.rotate(Vector3.Axis.Y, 180);
    }

}
