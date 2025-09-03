
uniform sampler2D bgl_RenderedTexture;
uniform float time;
uniform float power;

void main() {
    vec2 uv = vec2(gl_TexCoord[0]);

    vec4 tex = texture(bgl_RenderedTexture, uv).rgba;
    
    float col2 = 0.7 + 0.5*cos(time+uv.y+3.0);
    float col3 = 0.5 + 0.5*cos(time+uv.x+10.0);
    float add = tex.b * 0.2 + tex.g * 0.2;
	float unpower = 1.0 - power;
    gl_FragColor = vec4(pow(1.0, tex.r) + add, pow(0.1,col2 * tex.g) + add, col3 * tex.b + add, tex.a*0.85) * power + tex * unpower;
}