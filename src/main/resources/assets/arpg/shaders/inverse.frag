
uniform sampler2D bgl_RenderedTexture;

void main() {
    gl_FragColor = vec4(vec3(1.0)-texture(bgl_RenderedTexture , vec2(gl_TexCoord[0])).rgb,1.0);
}