attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_texCoord;

uniform mat4 u_worldTrans;
uniform mat4 u_projViewTrans;

varying vec2 v_texCoord;
varying vec3 v_normal;
varying vec2 v_pos;

uniform sampler2D u_height_map_texture;
uniform sampler2D u_normal_map_texture;

void main() {
    v_texCoord = a_texCoord;
    float hM = 30.0;

    vec4 hC = texture2D(u_height_map_texture, a_texCoord);
    hC.a = hC.r;
    vec3 pC = vec3(a_position.x, hC.a*hM, a_position.z);

    v_normal = normalize(texture2D(u_normal_map_texture, a_texCoord) * 2.0 - 1.0);

    v_pos = pC;
    gl_Position = u_projViewTrans * u_worldTrans * vec4(pC, 1.0);
}