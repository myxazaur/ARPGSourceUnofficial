
uniform sampler2D bgl_RenderedTexture;
uniform float time;


void main()
{
    vec2 uv = gl_TexCoord[0];
    vec2 texCoord = gl_TexCoord[0];
    texCoord.x = texCoord.x+sin((texCoord.y*20.0-time)*1.5)*0.04;
    texCoord.y = texCoord.y+sin((texCoord.x*24.0+time)*1.5)*0.05;
    
    uv.y = texCoord.y+sin((time)*0.35)*0.03;
    
    vec3 color = texture(bgl_RenderedTexture, uv).xyz;
    float cp = color.r + color.g + color.b * 0.1;
    
    color.r += texture(bgl_RenderedTexture, texCoord).r * cp;
    color.g += texture(bgl_RenderedTexture, texCoord).g * cp;
    color.b += texture(bgl_RenderedTexture, texCoord).b * cp;
    
    gl_FragColor = vec4(color, 1.);
    
    
}