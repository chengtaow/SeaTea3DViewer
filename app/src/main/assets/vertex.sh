uniform mat4 uMVPMatrix;
uniform mat4 uMMatrix;
attribute vec3 aPosition;
attribute vec4 aColor;
varying vec4 vColor;
varying vec3 vPosition;
void main() {
  gl_Position = uMVPMatrix * vec4(aPosition, 1);
  gl_PointSize = 10.0;
  vColor = aColor;
  vPosition = (uMMatrix * vec4(aPosition, 1)).xyz;
}