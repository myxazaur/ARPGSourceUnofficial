
uniform sampler2D bgl_RenderedTexture;
uniform float time;

void main() {
  
    gl_FragColor = texture(bgl_RenderedTexture, vec2(gl_TexCoord[0].x+cos(gl_TexCoord[0].y*40.0+time*1.5)*0.01 ,gl_TexCoord[0].y+sin(gl_TexCoord[0].x*40.0+time*1.5)*0.01 ));
}