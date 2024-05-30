import me.redstoner2019.data.Frame;
import me.redstoner2019.data.Panel;

public class Main {
    public static void main(String[] args) {
        Frame frame = new Frame(.5,.5);

        Panel panel = new Panel();

        frame.addComponent(panel);
    }
}
