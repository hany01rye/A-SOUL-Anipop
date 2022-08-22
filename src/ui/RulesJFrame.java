package ui;

import javax.swing.*;

public class RulesJFrame extends JFrame {
    public RulesJFrame() {
        initJFrame();
        initView();
        this.setVisible(true);
    }

    public void initJFrame() {
        //this.setSize(800, 500);//设置宽高
        this.setSize(1247, 701);//设置宽高
        this.setTitle("A-SOUL消消乐 V1.0 规则");//设置标题
        this.setDefaultCloseOperation(2);//设置关闭模式
        this.setLocationRelativeTo(null);//居中
        this.setAlwaysOnTop(true);//置顶
        this.setLayout(null);//取消内部默认布局
    }

    public void initView() {
        JLabel background = new JLabel(new ImageIcon("image\\login\\rules.png"));
        background.setBounds(0, 0, 1247, 701);
        this.getContentPane().add(background);
    }
}