attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_texCoord;

uniform mat4 u_worldTrans;
uniform mat4 u_projViewTrans;

uniform float u_time;
uniform vec4 u_ambientColor;

varying vec2 v_texCoord;
varying vec3 v_normal;

varying vec3 v_pos;

void main() {
    v_texCoord = a_texCoord;
    gl_Position = u_projViewTrans * u_worldTrans * vec4(a_position, 1.0);
}