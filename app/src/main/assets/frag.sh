precision mediump float;
varying vec4 vColor;
varying vec3 vPosition;
void main() {
  vec4 finalColor = vColor;
  // rotate 20 degrees around z axis
  mat4 mm = mat4(
  0.9396926, -0.34202012, 0.0, 0.0,
  0.34202012, 0.9396926, 0.0, 0.0,
  0.0, 0.0, 1.0, 0.0,
  0.0, 0.0, 0.0, 1.0
  );
  vec4 tPosition = mm * vec4(vPosition, 1);
  if (mod(tPosition.x + 100.0, 0.4) > 0.3) {
    finalColor = vec4(0.4, 0.0, 0.0, 1.0) + finalColor;
  }
  gl_FragColor = vColor;
}