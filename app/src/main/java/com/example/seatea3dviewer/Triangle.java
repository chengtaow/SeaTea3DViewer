package com.example.seatea3dviewer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class Triangle {
    private int mProgram;
    private int muMMatrixHandle;
    private int muMVPMatrixHandle;
    private int maPositionHandle;
    private int maColorHandle;

    private String mVertexShader;
    private String mFragmentShader;
    private FloatBuffer mVertexBuffer;
    private FloatBuffer mColorBuffer;

    int vCount = 0;
    float xAngle = 0;

    public Triangle(OpenGLESView mv) {
        initVertexData();
        initShader(mv);
    }

    private void initVertexData() {
        vCount = 3;
        final float UNIT_SIZE = 0.2f;
        float vertices[] = new float[] {
                -4 * UNIT_SIZE, 0, 0,
                0, -4 * UNIT_SIZE, 0,
                4 * UNIT_SIZE, 0, 0
        };
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);

        float colors[] = new float[] {
                1, 1, 1, 0,
                0, 0, 1, 0,
                0, 1, 0, 0
        };
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        mColorBuffer = cbb.asFloatBuffer();
        mColorBuffer.put(colors);
        mColorBuffer.position(0);
    }


    private void initShader(OpenGLESView mv) {
        mVertexShader = ShaderUtil.loadFromAssetsFile("vertex.sh", mv.getResources());
        mFragmentShader = ShaderUtil.loadFromAssetsFile("frag.sh", mv.getResources());
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        maColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        muMMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMMatrix");
    }

    public void drawSelf() {
        GLES20.glUseProgram(mProgram);
        MatrixState.setInitModel();
        MatrixState.rotate(xAngle, 1, 0, 0);

        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false,
                MatrixState.getFinalMatrix(), 0);
        GLES20.glUniformMatrix4fv(muMMatrixHandle, 1, false,
                MatrixState.getModelMatrix(), 0);

        GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT,
                false, 3 * 4, mVertexBuffer);

        GLES20.glVertexAttribPointer(maColorHandle, 4, GLES20.GL_FLOAT,
                false, 4 * 4, mColorBuffer);

        GLES20.glEnableVertexAttribArray(maPositionHandle);
        GLES20.glEnableVertexAttribArray(maColorHandle);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);
    }

}
