
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

public class SkinPanel extends JPanel{
    private SpringLayout layout = new SpringLayout();
    private ImageIcon skin1Icon = new ImageIcon(getClass().getResource("player1Sprite.png"));
    private ImageIcon skin2Icon = new ImageIcon(getClass().getResource("player2Sprite.png"));
    private JButton Skin1 = new JButton(skin1Icon);
    private JButton Skin2 = new JButton(skin2Icon);
    private JButton returnButton= new JButton("RETURN TO MENU");
   

    public SkinPanel(){

        this.setLayout(layout);
        this.setPreferredSize(new Dimension(800,600));

        layout.putConstraint(SpringLayout.WEST, Skin1,300, SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.NORTH, Skin1, 100, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, Skin2,300, SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.NORTH, Skin2, 250, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, returnButton,300, SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.NORTH, returnButton, 400, SpringLayout.NORTH, this);

        //changing size of the play button
        Skin1.setPreferredSize(new Dimension(200,100));
        Border normal = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        Border hover = BorderFactory.createLineBorder(Color.YELLOW, 5);
        Skin1.setBorder(normal);
        Skin1.setRolloverEnabled(true);
        Skin1.getModel().addChangeListener(e -> {
            if (Skin1.getModel().isRollover()) {
                Skin1.setBorder(hover);
            } else {
            Skin1.setBorder(normal);
            }
        });
        Skin1.setForeground(Color.WHITE);

        Skin2.setPreferredSize(new Dimension(200,100));
        Skin2.setBorder(normal);
        Skin2.setRolloverEnabled(true);
        Skin2.getModel().addChangeListener(e -> {
            if (Skin2.getModel().isRollover()) {
                Skin2.setBorder(hover);
            } else {
            Skin2.setBorder(normal);
            }
        });
        Skin2.setForeground(Color.WHITE);

        returnButton.setPreferredSize(new Dimension(200,100));
        returnButton.setBorder(normal);
        returnButton.setRolloverEnabled(true);
        returnButton.getModel().addChangeListener(e -> {
            if (returnButton.getModel().isRollover()) {
                returnButton.setBorder(hover);
            } else {
            returnButton.setBorder(normal);
            }
        });
        returnButton.setForeground(Color.WHITE);

        this.add(Skin1);
        this.add(Skin2);
        this.add(returnButton);

        this.setBackground(new Color(44, 95, 52));// setting the background as blue


    }

    public JButton getskin1(){
        return Skin1;
    }
    public JButton getskin2(){
        return Skin2;
    }

    public JButton getexitButton(){
        return returnButton;
    }

}
