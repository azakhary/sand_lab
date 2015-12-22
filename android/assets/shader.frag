#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoord;
varying vec2 v_pos;

uniform sampler2D u_height_map_texture;
uniform vec3 camera_dir;
uniform vec3 u_light;
uniform vec4 u_light_color;

varying vec3 v_normal;
varying vec3 v_specular;

void main() {
    vec4 color = vec4(192.0/255.0, 129.0/255.0, 112.0/255.0, 1.0);
    vec3 light = normalize(u_light);
    vec4 ambientColor = vec4(1.0, 1.0, 1.0, 0.6);

    // journey magic
    //v_normal.y *= 0.3;
    vec3 diffuse = (u_light_color.rgb * u_light_color.a) * max(3*dot(v_normal, light), 0.0);

    vec3 ambient = ambientColor.rgb * ambientColor.a;
    vec3 intensity = ambient + diffuse + v_specular;
    vec3 finalColor = color.rgb * intensity;
    gl_FragColor = vec4(finalColor, color.a);

    //gl_FragColor = vec4(v_normal.x, v_normal.y, v_normal.z, 1);
}