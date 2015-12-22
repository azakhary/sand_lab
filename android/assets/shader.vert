attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_texCoord;

uniform mat4 u_worldTrans;
uniform mat4 u_projViewTrans;

varying vec2 v_texCoord;
varying vec3 v_normal;
varying vec2 v_pos;
varying vec3 v_specular;

uniform sampler2D u_height_map_texture;
uniform sampler2D u_normal_map_texture;
uniform vec3 camera_dir;
uniform vec3 u_light;
uniform vec4 u_light_color;

void main() {
    v_texCoord = a_texCoord;
    float hM = 30.0;

    vec4 hC = texture2D(u_height_map_texture, a_texCoord);
    hC.a = hC.r;
    vec3 pC = vec3(a_position.x, hC.a*hM, a_position.z);

    v_normal = normalize(texture2D(u_normal_map_texture, a_texCoord) * 2.0 - 1.0);
    v_normal = vec3(v_normal.g, v_normal.b, -v_normal.r); //what the shet!

    // specular part
    vec3 light = normalize(u_light);

    vec3 reflectVec = normalize(reflect(-light, v_normal));
    vec3 specIntensity  = pow(max(dot(reflectVec, camera_dir), 0.0), 90.0);
    v_specular = specIntensity  * u_light_color.rgb * 0.5;

    v_pos = pC;
    gl_Position = u_projViewTrans * u_worldTrans * vec4(pC, 1.0);
}