package character;

import java.awt.*;
import java.util.Random;

public class Hero {

    public static int[] HP = {1300, 1000, 700, 900, 900, 300};
    public static int[] ATK1 = {30, 40, 30, 30, 30, 50};
    public static int[] ATK2 = {40, 30, 30, 30, 30, 50};
    public static int[] AUTO_REMEDY = {0, 0, 0, 0, 1, 0};
    public static int[] STRICK = {0, 1, 0, 0, 0, 0};
    public static int[] AVOID = {0, 0, 0, 1, 0, 0};
    public static int REMEDY = 20;
    public static int DELTA_RAGE = 4;
    private int Hp, Rage;
    private int maxHp, maxRage;
    private int Atk1, Atk2;
    private int autoRemedy, Strick, Avoid;
    private int successfulStrick, successfulAvoid;
    private int Type;
    private Image Img_mini;
    private Image Img;


    private Random Rd;

    public Hero() {
    }

    public Hero(int Type) {
        this.Type = Type;
        maxHp = Hp = HP[Type];
        Rage = 0;
        maxRage = 100;
        Atk1 = ATK1[Type];
        Atk2 = ATK2[Type];
        autoRemedy = AUTO_REMEDY[Type];
        Strick = STRICK[Type];
        Avoid = AVOID[Type];
        successfulAvoid = 0;
        successfulStrick = 0;
        Img_mini = Toolkit.getDefaultToolkit().getImage("image/select/hero/"+Type+".png");
        Img = Toolkit.getDefaultToolkit().getImage("image/game/hero/"+Type+".png");
        Rd = new Random();
    }

    public Image getImg_mini() {
        return Img_mini;
    }

    public Image getImg() {
        return Img;
    }

    public int getType() {
        return Type;
    }

    public int getRage() {
        return Rage;
    }

    public int getHp() {
        return Hp;
    }

    public int getSuccessfulStrick() {
        return successfulStrick;
    }

    public void setSuccessfulStrick(int successfulStrick) {
        this.successfulStrick = successfulStrick;
    }

    public int getSuccessfulAvoid() {
        return successfulAvoid;
    }

    public void setSuccessfulAvoid(int successfulAvoid) {
        this.successfulAvoid = successfulAvoid;
    }

    public int beAttacked(int Value) {
        if (Avoid == 1) {
            if (Rd.nextInt(5) == 0) {
                successfulAvoid = 1;
                return 0;
            }
        }
        Hp -= Value;
        if (Hp < 0) Hp = 0;
        return 1;
    }

    //三连个数/五连个数
    //getAttackValue2()之前必须先getAttackValue1()
    public int getAttackValue1(int x, int X) {
        int value = (int)((x * Atk1 + X * Atk1 * 3) * ((200. + Rage) / 300));
        if (Strick == 0) return value;
        if (Rd.nextInt(5) == 0) {
            value *= 2;
            successfulStrick = 1;
        }
        return value * 2;
    }

    public int getAttackValue2(int x, int X) {
        int value = (int)((x * Atk2 + X * Atk2 * 3) * ((200. + Rage) / 300));
        if (successfulStrick == 1) value *= 2;
        return value;
    }

    public void incHp(int x, int X) {
        int delta = x * REMEDY + X * REMEDY * 3;
        Hp += delta;
        if (Hp > maxHp) Hp = maxHp;
    }

    public void incRage(int x, int X) {
        int delta = x * DELTA_RAGE + X * DELTA_RAGE * 3;
        Rage += delta;
        if (Rage > maxRage) Rage = maxRage;
    }

    public void intervalWork() {
        Rage -= 3;
        if (Rage < 0) Rage = 0;
        if (autoRemedy == 1) incHp(1, 0);
    }
}