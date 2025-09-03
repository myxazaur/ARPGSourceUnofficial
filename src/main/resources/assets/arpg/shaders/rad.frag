
uniform sampler2D bgl_RenderedTexture;

uniform float weg;

void main() {
    vec2 uv = vec2(gl_TexCoord[0]);
    vec2 oneTexel = vec2(0.01, 0.01);
    vec4 c  = texture(bgl_RenderedTexture, uv);
    vec4 maxVal = c;
	float Radius = 3.0;
    for(float u = 0.0; u <= Radius; u += 0.5) {
        for(float v = 0.0; v <= Radius; v += 0.5) {
            float weight = (((sqrt(u * u + v * v) / (Radius)) > 1.0) ? 0.0 : 1.0);
			weight *= weg;
            vec4 s0 = texture(bgl_RenderedTexture, uv + vec2(-u * oneTexel.x, -v * oneTexel.y));
            vec4 s1 = texture(bgl_RenderedTexture, uv + vec2( u * oneTexel.x,  v * oneTexel.y));
            vec4 s2 = texture(bgl_RenderedTexture, uv + vec2(-u * oneTexel.x,  v * oneTexel.y));
            vec4 s3 = texture(bgl_RenderedTexture, uv + vec2( u * oneTexel.x, -v * oneTexel.y));

            vec4 o0 = max(s0, s1);
            vec4 o1 = max(s2, s3);
            vec4 tempMax = max(o0, o1);
            maxVal = mix(maxVal, max(maxVal, tempMax), weight);
        }
    }
    float r2 = Radius * weg * 6.5;
    float pattern = cos(uv.x*0.75*3.94159-1.45)*cos(uv.y*1.2*3.14159+1.25)*0.65 + 0.25;
    gl_FragColor = vec4(maxVal.r, maxVal.g + max(pattern, 0.1) * r2, maxVal.b + 0.17 * r2, 1.0);
}