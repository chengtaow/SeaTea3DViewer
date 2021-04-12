uniform mat4 uMVPMatrix;
attribute vec3 aPosition;
attribute vec4 aColor;
varying vec4 vColor;
void main() {
  gl_Position = uMVPMatrix * vec4(aPosition, 1);
  gl_PointSize = 10.0;
  vColor = aColor;
}