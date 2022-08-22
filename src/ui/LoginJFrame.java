package ui;

import domain.User;
import test.Test;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class LoginJFrame extends JFrame implements MouseListener {

    static ArrayList<User> allUsers = new ArrayList<>();
    static {
        allUsers.add(new User("admin","admin"));
        allUsers.add(new User("a","a"));
    }


    JButton login = new JButton();
    JButton register = new JButton();

    JButton rules = new JButton();

    JButton tactic = new JButton();

    JTextField username = new JTextField();
    JPasswordField password = new JPasswordField();

    public LoginJFrame() {
        initJFrame();

        initView();

        this.setVisible(true);
    }

    public void initView() {
        JLabel usernameText = new JLabel(new ImageIcon("image\\login\\用户名.png"));
        int dx = -55, dy = 150;
        int dx1 = -55, dy1 = 100;
        usernameText.setBounds(116 + dx, 135 + dy + 5, 47, 17);
        this.getContentPane().add(usernameText);


        username.setBounds(195 + dx, 134 + dy, 200, 30);
        this.getContentPane().add(username);

        JLabel passwordText = new JLabel(new ImageIcon("image\\login\\密码.png"));
        passwordText.setBounds(130 + dx, 195 + dy + 5, 32, 16);
        this.getContentPane().add(passwordText);

        password.setBounds(195 + dx, 195 + dy, 200, 30);
        this.getContentPane().add(password);


        login.setBounds(123 + dx1, 310 + dy1, 128, 47);
        login.setIcon(new ImageIcon("image\\login\\登录按钮.png"));
        login.setBorderPainted(false);
        login.setContentAreaFilled(false);
        login.addMouseListener(this);
        this.getContentPane().add(login);

        register.setBounds(256 + dx1, 310 + dy1, 128, 47);
        register.setIcon(new ImageIcon("image\\login\\注册按钮.png"));
        register.setBorderPainted(false);
        register.setContentAreaFilled(false);
        register.addMouseListener(this);
        this.getContentPane().add(register);


        JLabel background = new JLabel(new ImageIcon("image\\login\\background.png"));
        background.setBounds(0, 0, 400, 500);
        this.getContentPane().add(background);

        rules.setBounds(400, 0, 400, 250);
        rules.setIcon(new ImageIcon("image\\login\\规则.png"));
        rules.addMouseListener(this);
        this.getContentPane().add(rules);

        tactic.setBounds(400, 250, 400, 250);
        tactic.setIcon(new ImageIcon("image\\login\\攻略.png"));
        tactic.addMouseListener(this);
        this.getContentPane().add(tactic);
    }

    public void initJFrame() {
        this.setSize(815, 530);//设置宽高
        this.setTitle("A-SOUL消消乐 V1.0 登录");//设置标题
        this.setDefaultCloseOperation(3);//设置关闭模式
        this.setLocationRelativeTo(null);//居中
        this.setLayout(null);//取消内部默认布局
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == login) {
            System.out.println("点击了登录按钮");
            String usernameInput = username.getText();
            String passwordInput = password.getText();

            User userInfo = new User(usernameInput, passwordInput);
            System.out.println("用户输入的用户名为" + usernameInput);
            System.out.println("用户输入的密码为" + passwordInput);

            if (usernameInput.length() == 0 || passwordInput.length() == 0) {
                System.out.println("用户名或者密码为空");
                showJDialog("用户名或者密码为空");
            } else if (contains(userInfo)) {
                System.out.println("用户名和密码正确可以开始玩游戏了");
                this.setVisible(false);
                this.removeAll();
                new SelectJFrame();
                return;
            } else {
                System.out.println("用户名或密码错误");
                showJDialog("用户名或密码错误");
            }
        } else if (e.getSource() == register) {
            showJDialog("暂不支持注册，请使用用户名\"admin\"密码\"admin\"登录");
            System.out.println("点击了注册按钮");
        } else if (e.getSource() == rules) {
            System.out.println("开启规则窗口");
            new RulesJFrame();
        } else if (e.getSource() == tactic) {
            System.out.println("开启攻略窗口");
            new TacticJFrame();
        }
    }

    public void showJDialog(String content) {
        JDialog jDialog = new JDialog();
        jDialog.setSize(350, 150);
        jDialog.setAlwaysOnTop(true);
        jDialog.setLocationRelativeTo(null);
        jDialog.setModal(true);
        jDialog.setTitle("Notice");

        JLabel warning = new JLabel(content);
        warning.setBounds(0, 0, 350, 150);
        jDialog.getContentPane().add(warning);

        jDialog.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == login) {
            login.setIcon(new ImageIcon("image\\login\\登录按下.png"));
        } else if (e.getSource() == register) {
            register.setIcon(new ImageIcon("image\\login\\注册按下.png"));
        } else if (e.getSource() == rules) {
            rules.setIcon(new ImageIcon("image\\login\\规则按下.png"));
        } else if (e.getSource() == tactic) {
            tactic.setIcon(new ImageIcon("image\\login\\攻略按下.png"));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == login) {
            login.setIcon(new ImageIcon("image\\login\\登录按钮.png"));
        } else if (e.getSource() == register) {
            register.setIcon(new ImageIcon("image\\login\\注册按钮.png"));
        } else if (e.getSource() == rules) {
            rules.setIcon(new ImageIcon("image\\login\\规则.png"));
        } else if (e.getSource() == tactic) {
            tactic.setIcon(new ImageIcon("image\\login\\攻略.png"));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public boolean contains(User userInput){
        for (int i = 0; i < allUsers.size(); i++) {
            User rightUser = allUsers.get(i);
            if(userInput.getUsername().equals(rightUser.getUsername()) && userInput.getPassword().equals(rightUser.getPassword())){
                return true;
            }
        }
        return false;
    }
}