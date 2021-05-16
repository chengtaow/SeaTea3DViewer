uniform mat4 uMVPMatrix;
attribute vec3 aPosition;
varying vec3 vPosition;
varying vec4 vAmbient;
void main() {
  gl_Position = uMVPMatrix * vec4(aPosition, 1);
  vPosition = aPosition;
  vAmbient = vec4(0.15, 0.15, 0.15, 1.0);
}