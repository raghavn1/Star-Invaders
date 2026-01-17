
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class SkinPanel extends JPanel{
    private SpringLayout layout = new SpringLayout();
    private JButton Skin1 = new JButton("Space Marine");
    private JButton Skin2 = new JButton("Doom Marine");
    private JButton cross= new JButton("X");
   

    public SkinPanel(){

        layout.putConstraint(SpringLayout.WEST, Skin1,50 , SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.NORTH, Skin1, 25, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, Skin2,150 , SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.NORTH, Skin2, 25, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, cross,150 , SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.NORTH, cross, 25, SpringLayout.NORTH, this);

        


        this.add(Skin1);
        this.add(Skin2);
        this.add(cross);

    }

    public JButton getskin1(){
        return Skin1;
    }
    public JButton getskin2(){
        return Skin2;
    }

    public JButton getexitButton(){
        return cross;
    }

}
