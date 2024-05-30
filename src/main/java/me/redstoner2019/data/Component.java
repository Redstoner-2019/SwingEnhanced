package me.redstoner2019.data;

import me.redstoner2019.graphic.Renderer;

import java.util.ArrayList;
import java.util.List;

abstract class Component {
    private double width;
    private double height;
    private double x;
    private double y;
    private List<Component> components = new ArrayList<>();

    public Component(double width, double height, double x, double y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }
    abstract int draw(Renderer r);

    public void addComponent(Component c){
        components.add(c);
    }

    public void removeComponent(Component c){
        components.remove(c);
    }

    public List<Component> getComponents() {
        return components;
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
}
