precision mediump float;
uniform float uR;
varying vec3 vPosition;
varying vec4 vAmbient;
void main() {
  vec3 color = vec3(0.678, 0.231, 0.129);
  float n = 8.0
  float span = 2.0 * uR / n;
  int i = int((vPosition.x + uR)/span);
  int j = int((vPosition.y + uR)/span);
  int k = int((vPosition.z + uR)/span);
  int whichColor = int(mod(float(i + j + k), 2.0));
  if (whichColor == 1) {
    color = vec3(0.678, 0.231, 0.129);
  } else {
    color = vec3(1.0, 1.0, 1.0);
  }
  gl_FragColor = vec4(color, 0) * vAmbient;
}