package ui;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EndJFrame extends JFrame implements MouseListener {
    public EndJFrame() {
    }

    public EndJFrame(int type) {
        initJFrame();
        Draw(type);
    }

    public void initJFrame() {
        this.setSize(1296, 648);//设置宽高
        this.setTitle("A-SOUL消消乐 V1.0");//设置标题
        this.setDefaultCloseOperation(3);//设置关闭模式
        this.setLocationRelativeTo(null);//居中
        //this.setAlwaysOnTop(true);//置顶
        this.setLayout(null);//取消内部默认布局
        this.setVisible(true);
    }

    public void Draw(int type) {
        JButton Replay = new JButton();
        Replay.setIcon(new ImageIcon("image\\end\\replay.png"));
        Replay.setBounds(700, 400, 300, 90);
        this.getContentPane().add(Replay);
        Replay.addMouseListener(this);

        JLabel background;
        if (type == 1) background = new JLabel(new ImageIcon("image\\end\\win.jpg"));
        else background = new JLabel(new ImageIcon("image\\end\\lose.jpg"));
        background.setBounds(0, 0, 1296, 648);
        this.getContentPane().add(background);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.setVisible(false);
        new SelectJFrame();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}