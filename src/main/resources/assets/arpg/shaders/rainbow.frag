
uniform sampler2D bgl_RenderedTexture;
uniform float time;
uniform float power;

void main() {
    vec2 uv = vec2(gl_TexCoord[0]);

    vec2 mid = vec2(0.5,0.5);
    
    vec3 col = 0.5 + 0.5*cos(time*(0.5+power)+uv.xyx+vec3(0,2,4));

    vec3 tex = texture(bgl_RenderedTexture, uv).rgb;
    
    float coof = (distance(uv,mid) + 0.1 + power / 2.0);
    
    tex.r = tex.r * (col.r + (col.r * coof * coof * 1.5))*coof*2.0 + tex.r*(0.4 + power)/coof;
    tex.g = tex.g * (col.g + (col.g * coof * coof * 1.5))*coof*2.0 + tex.g*(0.4 + power)/coof;
    tex.b = tex.b * (col.b + (col.b * coof * coof * 1.5))*coof*2.0 + tex.b*(0.4 + power)/coof;
    
    gl_FragColor = vec4(tex,1.0);
}