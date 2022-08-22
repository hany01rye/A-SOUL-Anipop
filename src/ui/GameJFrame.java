package ui;

import character.Hero;
import character.Enemy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import static java.lang.Thread.sleep;

public class GameJFrame extends JFrame implements MouseListener {
    Hero hero;
    Enemy enemy;
    int ED = 0;
    int[][] grid = new int[7][8];
    int lastX, lastY;
    Image bg = Toolkit.getDefaultToolkit().getImage("image/game/background.png");
    Image[] tile = new Image[8];
    ImageIcon[] tileIcon = new ImageIcon[8];
    ImageIcon heroIcon, enemyIcon, bgIcon;

    Random Rd = new Random();
    Font font = new Font("宋体", 0, 40);
    Color color = new Color(255, 255, 255);

    public GameJFrame() {

    }

    public GameJFrame(Hero hero, Enemy enemy) {
        for (int i = 0; i < 8; ++i) {
            tile[i] = Toolkit.getDefaultToolkit().getImage("image/game/tile/" + i + ".png");
            tileIcon[i] = new ImageIcon(tile[i]);
        }
        this.hero = hero;
        this.enemy = enemy;
        heroIcon = new ImageIcon(hero.getImg());
        enemyIcon = new ImageIcon(enemy.getImg());
        bgIcon = new ImageIcon(bg);
        initJFrame();
        initGrid();
        initButton();
        lastX = lastY = -1;
        while (clearable()) {
            clearup(false);
            if (ED == 1)
                break;
            push(false);
        }
        loadJButton();
    }

    boolean clearable() {
        for (int i = 0; i < 7; ++ i) {
            for (int j = 0; j < 8; ++j) {
                if (i + 2 < 7 && grid[i][j] == grid[i + 1][j] && grid[i][j] == grid[i + 2][j]) return true;
                if (j + 2 < 8 && grid[i][j] == grid[i][j + 1] && grid[i][j] == grid[i][j + 2]) return true;
            }
        }
        return false;
    }

    void clearup(boolean type) {
        int[] cnt3 = new int[4];
        int[] cnt5 = new int[4];
        boolean[][] flag = new boolean[7][8];

        for (int i = 0; i < 7; ++ i) {
            for (int j = 0; j < 8; ++ j) {
                if (i + 2 < 7 && grid[i][j] == grid[i + 1][j] && grid[i][j] == grid[i + 2][j]) {
                    flag[i][j] = flag[i + 1][j] = flag[i + 2][j] = true;
                    ++ cnt3[grid[i][j]];
                    if (i + 4 < 7 && grid[i][j] == grid[i + 3][j] && grid[i][j] == grid[i + 4][j])
                        ++ cnt5[grid[i][j]];
                }

                if (j + 2 < 8 && grid[i][j] == grid[i][j + 1] && grid[i][j] == grid[i][j + 2]) {
                    flag[i][j] = flag[i][j + 1] = flag[i][j + 2] = true;
                    ++ cnt3[grid[i][j]];
                    if (j + 4 < 8 && grid[i][j] == grid[i][j + 3] && grid[i][j] == grid[i][j + 4])
                        ++ cnt5[grid[i][j]];
                }
            }
        }

        for (int i = 0; i < 7; ++ i)
            for (int j = 0; j < 8; ++ j)
                if (flag[i][j] == true) grid[i][j] = -1;

        if (type) {
            int value1 = hero.getAttackValue1(cnt3[0], cnt5[0]);
            int value2 = hero.getAttackValue2(cnt3[1], cnt5[1]);
            hero.incHp(cnt3[2], cnt5[2]);
            hero.incRage(cnt3[3], cnt5[3]);
            enemy.beAttacked(value1, value2);

            refreshGraph(this.getGraphics());
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (enemy.getHp() <= 0) {
                new EndJFrame(1);
                ED = 1;
                this.setVisible(false);
            }
        }
    }

    public void push(boolean type) {
        while (!isFull()) {
            for (int i = 6; i >= 0; -- i) {
                for (int j = 0; j < 8; ++ j) {
                    if (grid[i][j] == -1) {
                        if (i == 0) grid[i][j] = Rd.nextInt(4);
                        else {
                            grid[i][j] = grid[i - 1][j];
                            grid[i - 1][j] = -1;
                        }
                    }
                }
            }

            if (type) {
                refreshGraph(this.getGraphics());
                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("计时结束");
            }
        }
    }

    public boolean isFull() {
        for (int i = 0; i < 7; ++ i)
            for (int j = 0; j < 8; ++ j)
                if (grid[i][j] == -1) return false;
        return true;
    }

    JButton[][] button = new JButton[7][8];
    void initButton() {
        for (int i = 0; i < 7; ++ i)
            for (int j = 0; j < 8; ++ j) {
                button[i][j] = new JButton();
                button[i][j].setBounds(j * 100 + 300, i * 100, 100, 100);
                button[i][j].addMouseListener(this);
            }
    }

    public void loadJButton() {
        System.out.println("绘制游戏界面");
        this.getContentPane().removeAll();

        System.out.println("加载button之前: " + lastX + "," + lastY);
        for (int i = 0; i < 7; ++ i) for (int j = 0; j < 8; ++ j) {
            if (lastX == i && lastY == j) button[i][j].setIcon(tileIcon[grid[i][j] + 4]);
            else button[i][j].setIcon(tileIcon[grid[i][j]]);
            button[i][j].setBorderPainted(false);
            button[i][j].setContentAreaFilled(false);
            this.getContentPane().add(button[i][j]);
        }

        JLabel Hero = new JLabel(heroIcon);
        Hero.setBounds(0, 0, 300, 300);
        this.getContentPane().add(Hero);
        JLabel Enemy = new JLabel(enemyIcon);
        Enemy.setBounds(1100, 0, 300, 300);
        this.getContentPane().add(Enemy);

        int TX = 50 - 8, TY = 300 - 30 - 8;
        JLabel heroHp = new JLabel("HP: " + hero.getHp());
        heroHp.setFont(font);
        heroHp.setForeground(color);
        heroHp.setBounds(TX, TY, 400, 200);
        this.getContentPane().add(heroHp);

        JLabel heroRage = new JLabel("RAGE: " + hero.getRage());
        heroRage.setFont(font);
        heroRage.setForeground(color);
        heroRage.setBounds(TX, TY + 100, 400, 200);
        this.getContentPane().add(heroRage);

        JLabel enemyHp = new JLabel("HP: " + enemy.getHp());
        enemyHp.setFont(font);
        enemyHp.setForeground(color);
        enemyHp.setBounds(TX + 1100 + 30 + 16, TY, 400, 200);
        this.getContentPane().add(enemyHp);

        JLabel Bg = new JLabel(bgIcon);
        Bg.setBounds(0, 0, 1400, 700);
        this.getContentPane().add(Bg);

        this.getContentPane().repaint();
        this.setVisible(true);
        System.out.println("结束绘制");
    }

    public void refreshGraph(Graphics g) {
        int dx = 8, dy = 30;
        System.out.println("开始绘制");
        Image offScreamImage = this.createImage(1400 + dx, 700 + dy);
        Graphics gImage = offScreamImage.getGraphics();
        gImage.drawImage(bg, 0 + dx, 0 + dy, this);
        gImage.drawImage(hero.getImg(), 0 + dx, 0 + dy, this);
        gImage.drawImage(enemy.getImg(), 1100 + dx, 0 + dy, this);
        for (int i = 0; i < 7; ++ i)
            for (int j = 0; j < 8; ++ j)
                if (grid[i][j] > -1) gImage.drawImage(tile[grid[i][j]], j * 100 + dx + 300, i * 100 + dy, this);

        gImage.setFont(font);
        gImage.setColor(color);
        gImage.drawString("HP: " + hero.getHp(), 50, 400);
        gImage.drawString("RAGE: " + hero.getRage(), 50, 500);
        gImage.drawString("HP: " + enemy.getHp(), 1200, 400);

        if (hero.getSuccessfulAvoid() == 1) {
            hero.setSuccessfulAvoid(0);
            gImage.drawString("成功闪避！", 50, 600);
        }
        if (hero.getSuccessfulStrick() == 1) {
            hero.setSuccessfulStrick(0);
            gImage.drawString("成功暴击！", 50, 600);
        }
        if (enemy.getSuccessfulAvoid() == 1) {
            enemy.setSuccessfulAvoid(0);
            gImage.drawString("成功闪避！", 1200, 600);
        }
        if (enemy.getSuccessfulStrick() == 1) {
            enemy.setSuccessfulStrick(0);
            gImage.drawString("成功暴击！", 1200, 600);
        }

        g.drawImage(offScreamImage, 0, 0, this);
        System.out.println("结束绘制");
    }

    public void initGrid() {
        for (int i = 0; i < 7; ++ i)
            for (int j = 0; j < 8; ++ j)
                grid[i][j] = Rd.nextInt(4);
    }

    public void initJFrame() {
        this.setSize(1400 + 8 + 8, 700 + 30 + 8);//设置宽高
        this.setTitle("A-SOUL消消乐 V1.0");//设置标题
        this.setDefaultCloseOperation(3);//设置关闭模式
        this.setLocationRelativeTo(null);//居中
        //this.setAlwaysOnTop(true);//置顶
        this.setLayout(null);//取消内部默认布局
        this.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("鼠标点击" + Rd.nextInt());
        System.out.println(lastX + "," + lastY);
        for (int i = 0; i < 7; ++ i)
            for (int j = 0; j < 8; ++ j) {
                if (e.getSource() == button[i][j]) {
                    if (Math.abs(lastX - i) + Math.abs(lastY - j) == 1) {
                        int t = grid[i][j];
                        grid[i][j] = grid[lastX][lastY];
                        grid[lastX][lastY] = t;
                        refreshGraph(this.getGraphics());
                        try {
                            sleep(800);
                        } catch (InterruptedException e_) {
                            throw new RuntimeException(e_);
                        }
                        if (clearable()) {
                            lastX = -1;
                            lastY = -1;
                            while (clearable()) {
                                clearup(true);
                                if (ED == 1) break;
                                push(true);
                            }
                            hero.intervalWork();
                            int value = enemy.getAttackValue();
                            hero.beAttacked(value);
                            if (ED == 0 && hero.getHp() <= 0) {
                                if (hero.getType() == 2) new GameJFrame(new Hero(5), enemy);
                                else new EndJFrame(0);
                                ED = 1;
                                this.setVisible(false);
                            } else {
                                if (ED == 0) {
                                    refreshGraph(this.getGraphics());
                                    try {
                                        sleep(1000);
                                    } catch (InterruptedException e_) {
                                        throw new RuntimeException(e_);
                                    }
                                    loadJButton();
                                }
                            }
                        } else {
                            t = grid[i][j];
                            grid[i][j] = grid[lastX][lastY];
                            grid[lastX][lastY] = t;
                            lastX = -1;
                            lastY = -1;
                            if (ED == 0)
                                loadJButton();
                        }
                    } else {
                        lastX = i;
                        lastY = j;
                        if (ED == 0)
                            loadJButton();
                    }
                }
            }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("鼠标按下");
        for (int i = 0; i < 7; ++ i)
            for (int j = 0; j < 8; ++ j) {
                if (e.getSource() == button[i][j]) {
                    button[i][j].setIcon(tileIcon[grid[i][j] + 4]);
                    break;
                }
            }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("鼠标释放");
        for (int i = 0; i < 7; ++ i)
            for (int j = 0; j < 8; ++ j) {
                if (e.getSource() == button[i][j]) {
                    if (i != lastX || j != lastY)
                        button[i][j].setIcon(tileIcon[grid[i][j]]);
                    break;
                }
            }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}