import javax.swing.JFrame;
//JFrame works like the main window where components like labels, buttons, textfields are added to create a GUI.
public class BrickBreaker {
    public static void main(String[] args)
    {
        JFrame obj = new JFrame();
        Gameplay gameplay = new Gameplay();
        obj.setBounds(10,10,700,600);
        obj.setTitle("BrickBreaker");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // add gameplay object of the Gameplay class to jframe
        obj.add(gameplay);

    }
}