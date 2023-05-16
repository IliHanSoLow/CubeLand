# shader vertex transparent_vert
#version 330 core

layout (location = 0) in vec2 position;
layout (location = 1) in vec4 color;

out vec4 v_Color;

void main()
{
    gl_Position = position;
    v_Color = color;
}

# shader fragment transparent_frag
#version 330 core

layout (location = 0) out vec4 color;

in vec4 v_Color;

void main()
{
    color = v_Color;
}
