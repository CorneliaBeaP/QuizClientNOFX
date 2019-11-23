package quiz;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SwingSetup {

    public static JButton createButton() {
        JButton button = new JButton("answer");
        button.setForeground(Color.WHITE);
        Border line = new LineBorder(Color.WHITE);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        button.setBorder(compound);
        button.setPreferredSize(new Dimension(150,100));
        button.setFont(applyFont());
        return button;
    }

    public static JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(32, 150, 255));
        return panel;
    }

    public static JLabel createLabel(String text) {
        JLabel jLabel = new JLabel(text,JLabel.CENTER);
        jLabel.setForeground(Color.WHITE);
        jLabel.setFont(applyFont());
        jLabel.setVerticalAlignment(JLabel.TOP);
        return jLabel;
    }

    private static Font applyFont() {
        File font_file = new File("src/quiz/Resources/coolveticarg.ttf");
        Font font=null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, font_file);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        Font sizedFont = font.deriveFont(18f);
        return sizedFont;
    }

    public static JLabel createLogo() {
        String logoPath = "src/quiz/Resources/logoSmall.png";
        JLabel logoGame = new JLabel(new ImageIcon(logoPath));
        logoGame.setBorder(new EmptyBorder(10, 10, 10, 10));
        return logoGame;
    }

    public static Border getEmptyBorder() {
        return new EmptyBorder(10, 10, 10, 10);
    }

    public static JLabel getLoaderLabel () {
        ImageIcon imageIcon = new ImageIcon(new ImageIcon("src/quiz/Resources/loading_spinner.gif").getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
        JLabel loader = new JLabel(imageIcon);
        loader.setVerticalAlignment(JLabel.BOTTOM);
        loader.setBorder(getEmptyBorder());
        return loader;
    }

}
