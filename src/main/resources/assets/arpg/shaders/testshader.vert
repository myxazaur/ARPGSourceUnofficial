#version 120 // версия шейдера
varying vec2 texcoord; // текстурная координата
varying vec4 color; // цвет вершины

void main() {
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex; // находим позицию вертекса, перемножая его на матрицу проекции
    texcoord = vec2(gl_MultiTexCoord0);
    color = gl_Color;
}