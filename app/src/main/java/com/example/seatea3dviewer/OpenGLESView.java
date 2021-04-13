package com.example.seatea3dviewer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLESView extends GLSurfaceView {
    final float ANGLE_SPAN = 0.375f;

    private SceneRenderer mRenderer;
    private RotateThread rthread;

    public OpenGLESView(Context context){
        super(context);

        // Create an OpenGL ES 2.0 context.
        this.setEGLContextClientVersion(2);

        // set the mRenderer member
        mRenderer = new SceneRenderer();
        this.setRenderer(mRenderer);

        // Render the view only when there is a change
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    private class SceneRenderer implements GLSurfaceView.Renderer {
        private Triangle tle;
        public void onDrawFrame(GL10 gl) {
            GLES20.glEnable(GLES20.GL_CULL_FACE);
            GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            tle.drawSelf();
        }
        public void onSurfaceCreated(GL10 unused, EGLConfig config) {
            GLES20.glClearColor(0, 0, 0, 1);
            tle = new Triangle(OpenGLESView.this);
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            rthread = new RotateThread();
            rthread.start();
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES20.glViewport(0, 0, width, height);
            float ratio = (float) width / height;
            MatrixState.setCamera(0,0,3,0,0,0,
                    0,1.0f,0);
            MatrixState.setFrustrumProjection(-ratio,ratio,-1,1,
                    1,10);
        }
    }

    public class RotateThread extends Thread {
        public boolean flag = true;

        @Override
        public void run() {
            while(flag) {
                mRenderer.tle.xAngle = mRenderer.tle.xAngle + ANGLE_SPAN;
                try {
                    Thread.sleep(20);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


