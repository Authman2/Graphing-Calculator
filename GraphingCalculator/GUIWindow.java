import javax.swing.*;
import java.awt.*;

public class GUIWindow
{
    public static void main(String[] args) {
        //CalcPanel, despite having the word 'Panel' in it, is actually a JFrame...
        CalcPanel cp = new CalcPanel("Graphing Calculator");
        cp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cp.setSize(700,600);
        cp.setLocationRelativeTo(null);
        
        cp.setVisible(true);
    }
}
