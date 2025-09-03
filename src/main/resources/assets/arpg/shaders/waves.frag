
uniform sampler2D bgl_RenderedTexture;
uniform float time;
uniform float power;

void main() {
    vec2 texCoord = vec2(gl_TexCoord[0]);
    texCoord.x = texCoord.x+cos(texCoord.y*17.0+time*1.5)*power;
    texCoord.y = texCoord.y+sin(texCoord.x*10.0+time*1.5)*power;
    gl_FragColor = texture(bgl_RenderedTexture, texCoord);
}