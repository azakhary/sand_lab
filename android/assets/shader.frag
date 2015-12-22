#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoord;
varying vec2 v_pos;

uniform sampler2D u_noise_texture;
uniform sampler2D u_height_map_texture;
uniform vec3 camera_dir;
uniform vec3 u_light;
uniform vec4 u_light_color;

varying vec3 v_normal;

void main() {
    vec4 color = vec4(192.0/255.0, 129.0/255.0, 112.0/255.0, 1.0);
    vec3 light = normalize(u_light);
    vec4 ambientColor = vec4(1.0, 1.0, 1.0, 0.6);

    // normal grains
    vec3 final_normal;
    vec4 noise = texture2D(u_noise_texture, v_texCoord/256.0);
    if(noise.r > 2) {
        final_normal = vec3(noise.r, noise.r, noise.r);
    } else {
        final_normal = v_normal;
    }


    // journey magic
    //final_normal.y *= 0.3;
    vec3 diffuse = (u_light_color.rgb * u_light_color.a) * max(3*dot(final_normal, light), 0.0);

    // specular part
    vec3 reflectVec = normalize(reflect(-light, final_normal));
    vec3 specIntensity  = pow(max(dot(reflectVec, camera_dir), 0.0), 90.0);
    vec3 specular = specIntensity  * u_light_color.rgb * 0.5;

    vec3 ambient = ambientColor.rgb * ambientColor.a;
    vec3 intensity = ambient + diffuse + specular;
    vec3 finalColor = color.rgb * intensity;
    gl_FragColor = vec4(finalColor, color.a);
}