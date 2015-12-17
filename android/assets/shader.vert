attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_texCoord;

uniform mat4 u_worldTrans;
uniform mat4 u_projViewTrans;

uniform float hm_size;

varying vec2 v_texCoord;
varying vec3 v_normal;
varying vec2 v_pos;

uniform sampler2D u_height_map_texture;

void main() {
    v_texCoord = a_texCoord;
    float hM = 20.0;

    vec4 hC = texture2D(u_height_map_texture, a_texCoord);
    vec4 hL = texture2D(u_height_map_texture, vec2(a_texCoord.x-1.0/hm_size, a_texCoord.y));
    vec4 hR = texture2D(u_height_map_texture, vec2(a_texCoord.x+1.0/hm_size, a_texCoord.y));
    vec4 hT = texture2D(u_height_map_texture, vec2(a_texCoord.x, a_texCoord.y+1.0/hm_size));
    vec4 hB = texture2D(u_height_map_texture, vec2(a_texCoord.x, a_texCoord.y-1.0/hm_size));

    hC.a = hC.r;
    hL.a = hL.r;
    hR.a = hR.r;
    hT.a = hT.r;
    hB.a = hB.r;

    vec3 pC = vec3(a_position.x, hC.a*hM, a_position.z);
    vec3 pL = vec3(a_position.x-1.0, hL.a*hM, a_position.z);
    vec3 pR = vec3(a_position.x+1.0, hR.a*hM, a_position.z);
    vec3 pT = vec3(a_position.x, hT.a*hM, a_position.z+1.0);
    vec3 pB = vec3(a_position.x, hB.a*hM, a_position.z-1.0);

    //v_normal =  normalize(cross((pB-pL),(pC-pL)) + cross((pT-pR),(pC-pR)) + cross((pB-pR),(pC-pR)) + cross((pL-pT),(pC-pT)));
    v_normal =  normalize(cross((pB-pC),(pL-pC)) + cross((pL-pC),(pT-pC)) + cross((pT-pC),(pR-pC)) + cross((pR-pC),(pB-pC)));

    v_pos = pC;

    gl_Position = u_projViewTrans * u_worldTrans * vec4(pC, 1.0);
}