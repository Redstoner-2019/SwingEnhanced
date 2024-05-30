package me.redstoner2019.data;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import me.redstoner2019.graphic.*;
import me.redstoner2019.graphic.Color;
import me.redstoner2019.math.Vector2f;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static java.awt.Font.MONOSPACED;
import static java.awt.Font.PLAIN;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Frame {
    private double width;
    private double height;
    private double x;
    private double y;
    private String title;
    private List<Component> components = new ArrayList<>();
    private GLFWErrorCallback errorCallback;
    private boolean running;
    private Renderer renderer;
    private boolean showDebug = true;

    private final int vaoId;

    private final int vboId;

    private final int eboId;

    public Frame(double width, double height, double x, double y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        vaoId = GL30.glGenVertexArrays();

        vboId = GL30.glGenBuffers();

        eboId = GL30.glGenBuffers();


        renderer = new Renderer();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
        //t.start();
        init();
    }

    public Frame(double width, double height) {
        this(width,height,0,0);
    }

    private void init(){

        errorCallback = GLFWErrorCallback.createPrint(System.err);
        glfwSetErrorCallback(errorCallback);

        // Init GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        // Create the window
        long window = glfwCreateWindow((int) (width * Toolkit.getDefaultToolkit().getScreenSize().width), (int) (height * Toolkit.getDefaultToolkit().getScreenSize().height), "Empty Frame", NULL, NULL);

        glfwSetWindowPos(window,(int) (x * Toolkit.getDefaultToolkit().getScreenSize().width) + 20, (int) (y * Toolkit.getDefaultToolkit().getScreenSize().height) + 50);

        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Setup a key callback
        glfwSetKeyCallback(window, new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                    glfwSetWindowShouldClose(window, true);
                }
                if (key == GLFW_KEY_F3 && action == GLFW_RELEASE) {
                    showDebug = !showDebug;
                }
            }
        });

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);

        // Initialize OpenGL
        GL.createCapabilities();

        // Set the clear color
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

        glfwShowWindow(window);

        renderer.init();

        me.redstoner2019.font.Font font = new me.redstoner2019.font.Font();

        long lastUpdate = System.currentTimeMillis();
        int frames = 0;
        int fps = 0;
        double lastFrameTime = 0;

        Texture texture = Texture.loadTexture("C:\\Users\\l.paepke\\Projects\\SwingEnhanced\\src\\main\\resources\\optadata.jpg");
        texture.bind();

        Shader shader = Shader.loadShader(GL20.GL_FRAGMENT_SHADER,"default.frag");
        ShaderProgram shaderProgram = new ShaderProgram();
        shaderProgram.attachShader(shader);

        // Loop until the window should close
        while (!glfwWindowShouldClose(window)) {
            double start = glfwGetTime();
            glClear(GL_COLOR_BUFFER_BIT);

            int componentsDrawn = 0;

            //show debug info if necessary
            if(showDebug) {
                renderer.drawText("FPS: " + fps, 10, 0, new Color(0, 0, 0), font);
                renderer.drawText("Last Frame Time: " + String.format("%.2f ms",lastFrameTime*1000), 10, 20, new Color(0, 0, 0), font);
                renderer.drawText("Time: " + String.format("%.4fs",glfwGetTime()), 10, 40, new Color(0, 0, 0), font);
                renderer.drawText("Components Drawn: " + componentsDrawn, 10, 60, new Color(0, 0, 0), font);
                renderer.drawTriangle(new Vector2f(0,0),new Vector2f(0,1),new Vector2f(1,1),new Color(1f,0f,0f));

                shaderProgram.use();

                glBegin(GL_QUADS);
                glColor3f(1.0f, 0.0f, 0.0f);
                glVertex2f(-0.5f, -0.5f); // Vertex 1
                glVertex2f(0.5f, -0.5f);  // Vertex 2
                glVertex2f(0.0f, 0.5f);   // Vertex 3
                glVertex2f(0.0f, 0.5f);   // Vertex 3
                glEnd();
            }

            glfwSwapBuffers(window);
            glfwPollEvents();
            frames++;
            if(System.currentTimeMillis() - lastUpdate >= 1000){
                fps = frames;
                frames = 0;
                lastUpdate = System.currentTimeMillis();
            }
            lastFrameTime = glfwGetTime() - start;
        }
        glfwTerminate();
    }

    public boolean isShowDebug() {
        return showDebug;
    }

    public void setShowDebug(boolean showDebug) {
        this.showDebug = showDebug;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addComponent(Component c){
        components.add(c);
    }

    public void removeComponent(Component c){
        components.remove(c);
    }

    public List<Component> getComponents() {
        return components;
    }
}
