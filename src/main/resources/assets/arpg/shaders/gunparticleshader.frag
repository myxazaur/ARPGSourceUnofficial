
uniform sampler2D bgl_RenderedTexture;

void main()
{
    
    vec2 uv = vec2(gl_TexCoord[0]);

    vec2 coord = vec2(0.5,0.5);
    
    vec4 col = texture(bgl_RenderedTexture, uv);
    
    col.r = max( col.r ,(col.r + 0.95 - distance(coord, uv) * 9.0));

    
    gl_FragColor = vec4(col.rgb,0.4);
}