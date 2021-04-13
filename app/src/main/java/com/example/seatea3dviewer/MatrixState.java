package com.example.seatea3dviewer;
import android.opengl.Matrix;

public class MatrixState {
    private static float[] mMMatrix = new float[16];
    private static float[] mVMatrix = new float[16];
    private static float[] mProjMatrix = new float[16];
    private static float[] mMVPMatrix;

    public static void setInitModel()
    {
        Matrix.setRotateM(mMMatrix, 0, 0, 1, 0, 0);
    }

    public static void translate(float x, float y, float z)
    {
        Matrix.translateM(mMMatrix, 0, x, y, z);
    }

    public static void rotate(float angle, float vx, float vy, float vz)
    {
        Matrix.rotateM(mMMatrix, 0, angle, vx, vy, vz);
    }

    public static void scale(float x, float y, float z)
    {
        Matrix.scaleM(mMMatrix, 0, x, y, z);
    }

    public static void setCamera(float cx, float cy, float cz,
                                 float tx, float ty, float tz,
                                 float upX, float upY, float upZ)
    {
        Matrix.setLookAtM(mVMatrix, 0, cx, cy, cz,
                tx, ty, tz, upX, upY, upZ);
    }

    public static void setOrthoProjection(float left, float right,
                                          float bottom, float top,
                                          float near, float far)
    {
        Matrix.orthoM(mProjMatrix, 0, left, right,
                bottom, top, near, far);
    }

    public static void setFrustrumProjection(float left, float right,
                                             float bottom, float top,
                                             float near, float far)
    {
        Matrix.frustumM(mProjMatrix, 0, left, right,
                bottom, top, near, far);
    }

    public static float[] getModelMatrix()
    {
        return mMMatrix;
    }

    public static float[] getFinalMatrix()
    {
        mMVPMatrix = new float[16];
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0,
                mMMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0,
                mMVPMatrix, 0);
        return mMVPMatrix;
    }
}
