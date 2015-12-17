#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoord;
varying vec2 v_pos;

uniform sampler2D u_height_map_texture;

varying vec3 v_normal;

void main() {
    vec4 color = vec4(0.85, 0.6, 0.484, 1.0);
    vec3 light = vec3(1, 0, 1);

    vec4 diffuse = clamp(color+dot(v_normal, light), 0, 1);
    gl_FragColor = diffuse;
    //gl_FragColor = vec4(v_normal.x, v_normal.y, v_normal.z, 1);
}