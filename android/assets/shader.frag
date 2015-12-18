#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoord;
varying vec2 v_pos;

uniform sampler2D u_height_map_texture;

varying vec3 v_normal;

void main() {
    vec4 color = vec4(192.0/255.0, 129.0/255.0, 112.0/255.0, 1.0);
    vec3 light = normalize(vec3(1, 1, 1));
    vec4 lightColor = vec4(1.0, 1.0, 1.0, 1.0);
    vec4 ambientColor = vec4(1.0, 1.0, 1.0, 0.8);

    vec3 diffuse = (lightColor.rgb * lightColor.a) * max(dot(v_normal, light), 0.0);
    vec3 ambient = ambientColor.rgb * ambientColor.a;
    vec3 intensity = ambient + diffuse;
    vec3 finalColor = color.rgb * intensity;
    gl_FragColor = vec4(finalColor, color.a);

    //gl_FragColor = vec4(v_normal.x, v_normal.y, v_normal.z, 1);
}