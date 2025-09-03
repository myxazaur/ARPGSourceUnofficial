
#version 120
//varying vec2 texcoord;
//varying vec4 color;
uniform sampler2D bgl_RenderedTexture;
uniform sampler2D texture;
uniform sampler2D DiffuseSampler;

void main() {
    //////////vec4 original = texture2D(bgl_RenderedTexture, texcoord); // получаем оригинальный цвет текстуры
	
	vec2 texcoord = vec2(gl_TexCoord[0]);
	
	vec4 thiscolor = texture2D(bgl_RenderedTexture, texcoord) ;//* color
	vec4 thiscolortex = texture2D(DiffuseSampler, texcoord) ;//* color
	
	thiscolor.b = 0.6;
	
	gl_FragColor = thiscolor;
	
}