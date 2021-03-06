package com.example.seatea3dviewer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import android.opengl.GLES20;

public class Ball {
    final float UNIT_SIZE = 1f;
    private int mProgram;
    private int muMVPMatrixHandle;
    private int maPositionHandle;
    private int maColorHandle;
    private int muRHandle;
    String mVertexShader;
    String mFragmentShader;
    private FloatBuffer mVertexBuffer;
    private FloatBuffer mColorBuffer;
    int vCount = 0;
    float yAngle = 0;
    float xAngle = 0;
    float zAngle = 0;
    float r = 0.8f;
    public Ball(OpenGLESView mv)
    {
        initVertexData();
        initShader(mv);
    }

    public void initVertexData() {
        ArrayList<Float> allVertix = new ArrayList<Float>();
        final int angleSpan = 10;
        for (int vAngle = -90; vAngle < 90; vAngle = vAngle + angleSpan) {
            for (int hAngle = 0; hAngle <= 360; hAngle = hAngle + angleSpan) {
                float x0 = (float)(r * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle))
                        * Math.cos(Math.toRadians(hAngle)));
                float y0 = (float)(r * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle))
                        * Math.sin(Math.toRadians(hAngle)));
                float z0 = (float)(r * UNIT_SIZE
                        * Math.sin(Math.toRadians(vAngle)));

                float x1 = (float)(r * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle))
                        * Math.cos(Math.toRadians(hAngle + angleSpan)));
                float y1 = (float)(r * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle))
                        * Math.sin(Math.toRadians(hAngle + angleSpan)));
                float z1 = (float)(r * UNIT_SIZE
                        * Math.sin(Math.toRadians(vAngle)));
                float x2 = (float)(r * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle + angleSpan))
                        * Math.cos(Math.toRadians(hAngle + angleSpan)));
                float y2 = (float)(r * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle + angleSpan))
                        * Math.sin(Math.toRadians(hAngle + angleSpan)));
                float z2 = (float)(r * UNIT_SIZE
                        * Math.sin(Math.toRadians(vAngle + angleSpan)));
                float x3 = (float)(r * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle + angleSpan))
                        * Math.cos(Math.toRadians(hAngle)));
                float y3 = (float)(r * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle + angleSpan))
                        * Math.sin(Math.toRadians(hAngle)));
                float z3 = (float)(r * UNIT_SIZE
                        * Math.sin(Math.toRadians(vAngle + angleSpan)));

                allVertix.add(x1);
                allVertix.add(y1);
                allVertix.add(z1);
                allVertix.add(x3);
                allVertix.add(y3);
                allVertix.add(z3);
                allVertix.add(x0);
                allVertix.add(y0);
                allVertix.add(z0);
                allVertix.add(x1);
                allVertix.add(y1);
                allVertix.add(z1);
                allVertix.add(x2);
                allVertix.add(y2);
                allVertix.add(z2);
                allVertix.add(x3);
                allVertix.add(y3);
                allVertix.add(z3);
            }
        }
        vCount = allVertix.size() / 3;
        float vertices[] = new float[vCount * 3];
        for (int i = 0; i < allVertix.size(); i++) {
            vertices[i] = allVertix.get(i);
        }
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);

        float colors[] = new float[vCount * 4];
        for (int i = 0; i < vCount / 6; i++) {
            colors[i * 4 * 6] = 1;
            colors[i * 4 * 6 + 1] = 1;
            colors[i * 4 * 6 + 2] = 1;

            colors[i * 4 * 6 + 4] = 1;
            colors[i * 4 * 6 + 5] = 0;
            colors[i * 4 * 6 + 6] = 0;

            colors[i * 4 * 6 + 8] = 1;
            colors[i * 4 * 6 + 9] = 0;
            colors[i * 4 * 6 + 10] = 0;

            colors[i * 4 * 6 + 12] = 1;
            colors[i * 4 * 6 + 13] = 1;
            colors[i * 4 * 6 + 14] = 1;

            colors[i * 4 * 6 + 16] = 1;
            colors[i * 4 * 6 + 17] = 1;
            colors[i * 4 * 6 + 18] = 1;

            colors[i * 4 * 6 + 20] = 1;
            colors[i * 4 * 6 + 21] = 1;
            colors[i * 4 * 6 + 22] = 1;
        };
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        mColorBuffer = cbb.asFloatBuffer();
        mColorBuffer.put(colors);
        mColorBuffer.position(0);
    }

    public void initShader(OpenGLESView mv) {
        mVertexShader = ShaderUtil.loadFromAssetsFile("vertex.sh", mv.getResources());
        mFragmentShader = ShaderUtil.loadFromAssetsFile("frag.sh", mv.getResources());
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        maColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        muRHandle = GLES20.glGetUniformLocation(mProgram, "uR");
    }

    public void drawSelf() {
        GLES20.glUseProgram(mProgram);
        MatrixState.setInitModel();
        MatrixState.rotate(xAngle, 1, 0, 0);
        MatrixState.rotate(yAngle, 0, 1,0);
        MatrixState.rotate(zAngle, 0, 0,1);
        GLES20.glUniform1f(muRHandle, r * UNIT_SIZE);

        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false,
                MatrixState.getFinalMatrix(), 0);

        GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT,
                false, 3 * 4, mVertexBuffer);
        GLES20.glVertexAttribPointer(maColorHandle, 4, GLES20.GL_FLOAT,
                false, 4 * 4, mColorBuffer);

        GLES20.glEnableVertexAttribArray(maPositionHandle);
        GLES20.glEnableVertexAttribArray(maColorHandle);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);
    }
}
