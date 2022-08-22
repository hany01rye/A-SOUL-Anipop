package ui;

import character.Enemy;
import character.Hero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static java.lang.Thread.sleep;

public class SelectJFrame extends JFrame implements MouseListener {
    Random Rd = new Random();
    int enemyID;

    Image bg = Toolkit.getDefaultToolkit().getImage("image/select/background.png");

    public SelectJFrame() {
        enemyID = Rd.nextInt(3);
        initJFrame();
        randomEnemy();
        selectView();
    }

    public void initJFrame() {
        this.setSize(1000, 630);//设置宽高
        this.setTitle("A-SOUL消消乐 V1.0 选择英雄");//设置标题
        this.setDefaultCloseOperation(3);//设置关闭模式
        this.setLocationRelativeTo(null);//居中
        //this.setAlwaysOnTop(true);//置顶
        this.setLayout(null);//取消内部默认布局
        this.setVisible(true);
    }

    int tmpEnemy;

    public static final Object monitor = new Object();

    public void randomEnemy() {
        for (int T = 0; T < 15; ++ T) {
            randomView(this.getGraphics());
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    Hero h0 = new Hero(0);
    Hero h1 = new Hero(1);
    Hero h2 = new Hero(2);
    Hero h3 = new Hero(3);
    Hero h4 = new Hero(4);
    Hero h5 = new Hero(5);
    Enemy e0 = new Enemy(0);
    Enemy e1 = new Enemy(1);
    Enemy e2 = new Enemy(2);

    void randomView(Graphics g) {
        tmpEnemy ++;
        tmpEnemy %= 3;
        int enemyID = tmpEnemy;
        Image offScreamImage = this.createImage(1000, 630);
        Graphics gImage = offScreamImage.getGraphics();
        int dx = 8, dy = 30;
        gImage.drawImage(bg, 0 + dx, 0 + dy, this);
        gImage.drawImage(h0.getImg_mini(), 0 + dx, 0 + dy, this);
        gImage.drawImage(h1.getImg_mini(), 200 + dx, 0 + dy, this);
        gImage.drawImage(h2.getImg_mini(), 0 + dx, 200 + dy, this);
        gImage.drawImage(h3.getImg_mini(), 200 + dx, 200 + dy, this);
        gImage.drawImage(h4.getImg_mini(), 0 + dx, 400 + dy, this);
        gImage.drawImage(h5.getImg_mini(), 200 + dx, 400 + dy, this);
        if (tmpEnemy == 0) gImage.drawImage(e0.getImg_mini(), 800 + dx, 200 + dy, this);
        if (tmpEnemy == 1) gImage.drawImage(e1.getImg_mini(), 800 + dx, 200 + dy, this);
        if (tmpEnemy == 2) gImage.drawImage(e2.getImg_mini(), 800 + dx, 200 + dy, this);
        g.drawImage(offScreamImage, 0, 0, this);
    }

    private JButton Hero0, Hero1, Hero2, Hero3, Hero4;

    public void selectView() {
        System.out.println("开始选择英雄");
        this.getContentPane().removeAll();
        Hero0 = new JButton();
        Hero0.setIcon(new ImageIcon("image\\select\\hero\\0.png"));
        Hero0.setBounds(0, 0, 200, 200);
        this.getContentPane().add(Hero0);
        Hero1 = new JButton();
        Hero1.setIcon(new ImageIcon("image\\select\\hero\\1.png"));
        Hero1.setBounds(200, 0, 200, 200);
        this.getContentPane().add(Hero1);
        Hero2 = new JButton();
        Hero2.setIcon(new ImageIcon("image\\select\\hero\\2.png"));
        Hero2.setBounds(0, 200, 200, 200);
        this.getContentPane().add(Hero2);
        Hero3 = new JButton();
        Hero3.setIcon(new ImageIcon("image\\select\\hero\\3.png"));
        Hero3.setBounds(200, 200, 200, 200);
        this.getContentPane().add(Hero3);
        Hero4 = new JButton();
        Hero4.setIcon(new ImageIcon("image\\select\\hero\\4.png"));
        Hero4.setBounds(0, 400, 200, 200);
        this.getContentPane().add(Hero4);
        JLabel Hero5 = new JLabel(new ImageIcon("image\\select\\hero\\5.png"));
        Hero5.setBounds(200, 400, 200, 200);
        this.getContentPane().add(Hero5);

        Hero0.addMouseListener(this);
        Hero1.addMouseListener(this);
        Hero2.addMouseListener(this);
        Hero3.addMouseListener(this);
        Hero4.addMouseListener(this);

        JLabel Enemy = new JLabel(new ImageIcon("image\\select\\enemy\\" + enemyID + ".jpg"));
        Enemy.setBounds(800, 200, 200, 200);
        this.getContentPane().add(Enemy);

        JLabel background = new JLabel(new ImageIcon("image\\select\\background1.png"));
        background.setBounds(0, 0, 1000, 600);
        this.getContentPane().add(background);

        this.getContentPane().repaint();
        this.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("选择完毕，开始战斗");
        if (e.getSource() == Hero0) {
            this.setVisible(false);
            new GameJFrame(new Hero(0), new Enemy(enemyID));
        } else if (e.getSource() == Hero1) {
            this.setVisible(false);
            new GameJFrame(new Hero(1), new Enemy(enemyID));
        } else if (e.getSource() == Hero2) {
            this.setVisible(false);
            new GameJFrame(new Hero(2), new Enemy(enemyID));
        } else if (e.getSource() == Hero3) {
            this.setVisible(false);
            new GameJFrame(new Hero(3), new Enemy(enemyID));
        } else if (e.getSource() == Hero4) {
            this.setVisible(false);
            new GameJFrame(new Hero(4), new Enemy(enemyID));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == Hero0) {
            Hero0.setIcon(new ImageIcon("image\\select\\hero\\x0.png"));
        } else if (e.getSource() == Hero1)
            Hero1.setIcon(new ImageIcon("image\\select\\hero\\x1.png"));
        else if (e.getSource() == Hero2)
            Hero2.setIcon(new ImageIcon("image\\select\\hero\\x2.png"));
        else if (e.getSource() == Hero3)
            Hero3.setIcon(new ImageIcon("image\\select\\hero\\x3.png"));
        else if (e.getSource() == Hero4)
            Hero4.setIcon(new ImageIcon("image\\select\\hero\\x4.png"));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == Hero0)
            Hero0.setIcon(new ImageIcon("image\\select\\hero\\0.png"));
        else if (e.getSource() == Hero1)
            Hero1.setIcon(new ImageIcon("image\\select\\hero\\1.png"));
        else if (e.getSource() == Hero2)
            Hero2.setIcon(new ImageIcon("image\\select\\hero\\2.png"));
        else if (e.getSource() == Hero3)
            Hero3.setIcon(new ImageIcon("image\\select\\hero\\3.png"));
        else if (e.getSource() == Hero4)
            Hero4.setIcon(new ImageIcon("image\\select\\hero\\4.png"));
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}