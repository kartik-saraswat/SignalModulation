package com.example.root.signalmodulation;

import android.support.v4.view.GestureDetectorCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.example.root.signalmodulation.elements.SignalRenderer;

import org.rajawali3d.surface.IRajawaliSurface;
import org.rajawali3d.surface.RajawaliSurfaceView;

public class MainActivity extends AppCompatActivity {

    SignalRenderer signalRenderer;
    private GestureDetectorCompat mDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RajawaliSurfaceView surface = new RajawaliSurfaceView(this);
        surface.setFrameRate(60.0);
        surface.setRenderMode(IRajawaliSurface.RENDERMODE_WHEN_DIRTY);
        signalRenderer = new SignalRenderer(this);
        surface.setSurfaceRenderer(signalRenderer);
        mDetector = new GestureDetectorCompat(this, new GestureListener(signalRenderer));
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.main_layout);
        relativeLayout.addView(surface);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
