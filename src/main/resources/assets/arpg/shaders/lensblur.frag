
uniform sampler2D bgl_RenderedTexture;

uniform float power;
uniform vec2 iResolution;

void main()
{
    
    vec2 uv = vec2(gl_TexCoord[0]);
    
    float maini = 5.0;
    //bool enabled = true;
    vec4 color = texture(bgl_RenderedTexture, uv);
    vec2 newcoords = vec2(0.0);
    
    
    vec2 center = iResolution.xy/2.0;
    float circleRadius = float(1.1);
    //float minZoom = 0.6;
    //float maxZoom = 0.3;
    
    uv.x *= (iResolution.x/iResolution.y);
    vec2 aspect_center = vec2(0.0, 0.0);
    aspect_center.x = (center.x / iResolution.x) * (iResolution.x/iResolution.y);
    aspect_center.y = center.y / iResolution.y;
    float maxX = aspect_center.x + circleRadius;
    float minX = aspect_center.x - circleRadius;
    float maxY = aspect_center.y + circleRadius;
    float minY = aspect_center.y - circleRadius;
    if( uv.x > minX && uv.x < maxX && uv.y > minY && uv.y < maxY)
    {
    float relX = uv.x - aspect_center.x;
    float relY = uv.y - aspect_center.y;
    float ang = atan(relY, relX);
    float dist = sqrt(relX*relX + relY*relY);
    if( dist <= circleRadius )
    {
    float newRad = dist * ( (0.3 * dist/circleRadius) + (0.6-power/10.0) );
    float newX = aspect_center.x + cos( ang ) * newRad;
    newX *= (iResolution.y/iResolution.x);
    float newY = aspect_center.y + sin( ang ) * newRad;
    newcoords = vec2(newX, newY);
    color = texture(bgl_RenderedTexture, newcoords);
    }
    else
    {
    color = texture(bgl_RenderedTexture, uv);
    }
    }
    
    
    
    //if (enabled)
    //{
    for (float i = -maini ; i < maini ; i++)
    {
        
        vec2 uvb = vec2(newcoords.x*iResolution.x + i, newcoords.y*iResolution.y + i)/iResolution.xy;
        color.r = (color.r + texture(bgl_RenderedTexture, uvb).r);
        color.g = (color.g + texture(bgl_RenderedTexture, uvb).g);
        color.b = (color.b + texture(bgl_RenderedTexture, uvb).b);
        
    }
    
    for (float i = -maini ; i < maini ; i++)
    {
        
        vec2 uvb = vec2(newcoords.x*iResolution.x - i, newcoords.y*iResolution.y - i)/iResolution.xy;
        color.r = (color.r + texture(bgl_RenderedTexture, uvb).r);
        color.g = (color.g + texture(bgl_RenderedTexture, uvb).g);
        color.b = (color.b + texture(bgl_RenderedTexture, uvb).b);
        
    }
    
    for (float i = -maini ; i < maini ; i++)
    {
        
        vec2 uvb = vec2(newcoords.x*iResolution.x + i, newcoords.y*iResolution.y - i)/iResolution.xy;
        color.r = (color.r + texture(bgl_RenderedTexture, uvb).r);
        color.g = (color.g + texture(bgl_RenderedTexture, uvb).g);
        color.b = (color.b + texture(bgl_RenderedTexture, uvb).b);
        
    }
    
    for (float i = -maini ; i < maini ; i++)
    {
        
        vec2 uvb = vec2(newcoords.x*iResolution.x - i, newcoords.y*iResolution.y + i)/iResolution.xy;
        color.r = (color.r + texture(bgl_RenderedTexture, uvb).r);
        color.g = (color.g + texture(bgl_RenderedTexture, uvb).g);
        color.b = (color.b + texture(bgl_RenderedTexture, uvb).b);
        
    }
    color.rgb = color.rgb / (maini * 8.0);
    //}
    
    float dist = distance(newcoords, vec2(0.5,0.5))*(3.0+power);
    vec4 text = texture(bgl_RenderedTexture, newcoords);
    
    color.r = (color.r - text.r)*dist + text.r;
    color.g = (color.g - text.g)*dist + text.g;
    color.b = (color.b - text.b)*dist + text.b;
    
	gl_FragColor = color;
   }