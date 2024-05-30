package me.redstoner2019.data;

import me.redstoner2019.graphic.Color;
import me.redstoner2019.graphic.Renderer;
import me.redstoner2019.math.Vector2f;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Panel extends Component{
    public Panel(double width, double height, double x, double y) {
        super(width, height, x, y);
    }
    public Panel() {
        super(1 ,1, 0, 0);
    }

    @Override
    int draw(Renderer r) {
        /*float[] vertices = {
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f,  0.5f, 0.0f
        };

        // Generate VAO and VBO
        int vao = glGenVertexArrays();
        int vbo = glGenBuffers();

        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);*/
        //r.drawTriangle(new Vector2f(0,0),new Vector2f(0,1),new Vector2f(1,1),new Color(1f,0f,0f),0);
        return 1;
    }
}
