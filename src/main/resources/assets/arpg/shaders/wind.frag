
uniform sampler2D bgl_RenderedTexture;
uniform float time;
uniform float power;

void main() {
    vec2 texCoord = vec2(gl_TexCoord[0]);
    texCoord.x = texCoord.x+cos(texCoord.y*8.0+time)*0.03;
    texCoord.y = texCoord.y+cos(texCoord.x*25.0+time)*0.06;
    gl_FragColor = texture(bgl_RenderedTexture, texCoord);
}