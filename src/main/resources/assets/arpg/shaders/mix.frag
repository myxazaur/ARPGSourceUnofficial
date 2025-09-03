
uniform sampler2D bgl_RenderedTexture;
uniform sampler2D mix;

void main() {
    vec2 texCoord = vec2(gl_TexCoord[0]);
    
    gl_FragColor = texture(mix, texCoord);
}