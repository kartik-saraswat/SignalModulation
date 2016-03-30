package com.example.root.signalmodulation.elements;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;

import org.rajawali3d.cameras.Camera2D;
import org.rajawali3d.primitives.Line3D;
import org.rajawali3d.primitives.Plane;
import org.rajawali3d.renderer.RajawaliRenderer;

public class SignalRenderer extends RajawaliRenderer {

    Context context;
    ModulationModel modulationModel;

    Analog analogSignal;

    Line3D analogSignalLine;
    Line3D carrierLine;
    Line3D modulatedLine;

    Line3D oldAnalogSignalLine;
    Line3D oldCarrierLine;
    Line3D oldModulatedLine;

    Plane oldModePlane, newModePlane;
    private boolean isSignalDirty = false;
    private boolean isModeDirty = false;
    private boolean isCarrierDirty = false;
    private boolean isAnalogDirty = false;
    private boolean isModulatedDirty = false;

    private enum Mode{
        AM, FM, PAM
    }

    int currentColorIndex = 0;
    int colorPool[] = new int[]{
            Color.WHITE, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.YELLOW, Color.CYAN, Color.RED
    };

    private Mode mode;

    public SignalRenderer(Context context) {
        super(context);
        this.context = context;
        this.modulationModel = new ModulationModel();
        this.mode = Mode.PAM;
        analogSignal = new Analog(2,Analog.MAX_FREQUENCY/2, modulationModel.color);
    }

    private void initializeCamera(){
        Camera2D camera2D = new Camera2D();
        float cameraWidth = ModulationModel.SCREEN_WIDTH + ModulationModel.MARGIN_HORIZONTAL*2;
        float cameraHeight = ModulationModel.SCREEN_HEIGHT + ModulationModel.MARGIN_VERTICAL*2;
        camera2D.setWidth(cameraWidth);
        camera2D.setHeight(cameraHeight);
        getCurrentScene().switchCamera(camera2D);
    }

    private void addModelToScene(){
        modulationModel.initialize();
        getCurrentScene().addChild(modulationModel.carrierHorizontalLine);
        getCurrentScene().addChild(modulationModel.signalHorizontalLine);
        getCurrentScene().addChild(modulationModel.modulatedHorizontalLine);

        getCurrentScene().addChild(modulationModel.carrierVerticalLine);
        getCurrentScene().addChild(modulationModel.signalVerticalLine);
        getCurrentScene().addChild(modulationModel.modulatedVerticalLine);

        getCurrentScene().addChild(modulationModel.carrierPlane);
        getCurrentScene().addChild(modulationModel.signalPlane);
        if(mode == Mode.AM) {
            getCurrentScene().addChild(modulationModel.amPlane);
        } else if(mode == Mode.FM) {
            getCurrentScene().addChild(modulationModel.fmPlane);
        }else if(mode == Mode.PAM) {
            getCurrentScene().addChild(modulationModel.pamPlane);
        }
        isModeDirty = false;
    }

    synchronized public void createAnalogSignal(){
            oldAnalogSignalLine = analogSignalLine;
            analogSignalLine = SignalGenerator.generateAnalogSignal(analogSignal, modulationModel.signalWaveVerticalPoint_Middle);
    }

    synchronized public void createAnalogCarrier(){
            oldCarrierLine = carrierLine;
            carrierLine = SignalGenerator.generateAnalogCarrier(analogSignal, modulationModel.carrierWaveVerticalPoint_Middle);
    }

    synchronized public void createAnalogAmplitudeModulated(){
            oldModulatedLine = modulatedLine;
            modulatedLine = SignalGenerator.generateAnalogAmplitudeModulated(analogSignal, modulationModel.modulatedWaveVerticalPoint_Middle);
    }

    synchronized public void createAnalogFrequencyModulated(){
        oldModulatedLine = modulatedLine;
        modulatedLine = SignalGenerator.generateAnalogFrequencyModulated(analogSignal, modulationModel.modulatedWaveVerticalPoint_Middle);
    }

    synchronized public void createDigitalCarrier(){
        oldCarrierLine = carrierLine;
        carrierLine = SignalGenerator.generateDigitalCarrier(analogSignal, modulationModel.carrierWaveVerticalPoint_Middle);
    }

    synchronized public void createDigitalModulated(){
        oldModulatedLine = modulatedLine;
        modulatedLine = SignalGenerator.generateDigitalModulated(analogSignal, modulationModel.modulatedWaveVerticalPoint_Middle);
    }

    synchronized private void initSignals(){
        if(!isSignalDirty) {
            createAnalogSignal();
            initModeSignals();
            isSignalDirty = true;
        }
    }

    synchronized private void initModeSignals(){
        if(!isSignalDirty) {
            if (mode == Mode.PAM) {
                createDigitalCarrier();
                createDigitalModulated();
            } else if (mode == Mode.AM) {
                createAnalogCarrier();
                createAnalogAmplitudeModulated();
            } else if (mode == Mode.FM) {
                createAnalogCarrier();
                createAnalogFrequencyModulated();
            }
            isSignalDirty = true;
        }
    }

    @Override
    protected void initScene() {
        getCurrentScene().setBackgroundColor(Color.BLACK);
        initializeCamera();
        addModelToScene();
        initSignals();
        getCurrentScene().addChild(carrierLine);
        getCurrentScene().addChild(analogSignalLine);
        getCurrentScene().addChild(modulatedLine);
        isSignalDirty = false;
        isModeDirty = false;
    }

    @Override
    public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {

    }

    @Override
    public void onTouchEvent(MotionEvent event) {

    }


    @Override
    protected void onRender(long ellapsedRealtime, double deltaTime) {

        if(isModeDirty){
            getCurrentScene().replaceChild(oldModePlane, newModePlane);
            isModeDirty = false;
        }

        if(isSignalDirty){
            getCurrentScene().replaceChild(oldCarrierLine, carrierLine);
            getCurrentScene().replaceChild(oldAnalogSignalLine, analogSignalLine);
            getCurrentScene().replaceChild(oldModulatedLine, modulatedLine);
            isSignalDirty = false;
        }

        super.onRender(ellapsedRealtime, deltaTime);
    }

    public void handleModeChange(){
        if(!isModeDirty){
            if(this.mode == Mode.AM){
                this.mode = Mode.FM;
                oldModePlane = modulationModel.amPlane;
                newModePlane =  modulationModel.fmPlane;
            } else if(this.mode == Mode.FM){
                this.mode = Mode.PAM;
                oldModePlane = modulationModel.fmPlane;
                newModePlane =  modulationModel.pamPlane;
            } else if(this.mode == Mode.PAM){
                this.mode = Mode.AM;
                oldModePlane = modulationModel.pamPlane;
                newModePlane =  modulationModel.amPlane;
            }
            initModeSignals();
            isSignalDirty = true;
            isModeDirty = true;
        }
    }

    public void handleFrequencyChange(boolean increase){
        if(!isSignalDirty) {
            if (increase) {
                analogSignal.setFrequency(analogSignal.getFrequency() + 0.5f);
            } else {
                analogSignal.setFrequency(analogSignal.getFrequency() - 0.5f);
            }
            initSignals();
            isSignalDirty = true;
        }
    }

    public void handleAmplitudeChange(boolean increase){
        if(!isSignalDirty) {
            if (increase) {
                analogSignal.setAmplitude(analogSignal.getAmplitude() + 0.2f);
            } else {
                analogSignal.setAmplitude(analogSignal.getAmplitude() - 0.2f);
            }
            initSignals();
            isSignalDirty = true;
        }
    }

    public void handleColorChange(){
        if(!isModeDirty && !isSignalDirty){
            currentColorIndex = (currentColorIndex+1)%colorPool.length;
            carrierLine.setColor(colorPool[currentColorIndex]);
            analogSignalLine.setColor(colorPool[currentColorIndex]);
            modulatedLine.setColor(colorPool[currentColorIndex]);
        }
    }
}
