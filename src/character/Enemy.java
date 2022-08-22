package character;

import java.awt.*;
import java.util.Random;

public class Enemy {
    public static int[] HP = {1800, 1500, 2000};
    public static int[] ATK = {50, 50, 50};
    public static int[] STRICK = {0, 0, 1};
    public static int[] AVOID = {0, 1, 0};
    public static int[] IMMUNE1 = {0, 0, 1};
    public static int[] IMMUNE2 = {1, 0, 0};
    private int Hp, Atk, Strick, Avoid, Immune1, Immune2;
    private int successfulStrick, successfulAvoid;
    private int Type;

    private Random Rd;
    private Image  Img_mini, Img;

    public Enemy() {
    }
    public Enemy(int Type) {
        this.Type = Type;
        Hp = HP[Type];
        Atk = ATK[Type];
        Strick = STRICK[Type];
        Avoid = AVOID[Type];
        Immune1 = IMMUNE1[Type];
        Immune2 = IMMUNE2[Type];
        Rd = new Random();
        Img_mini = Toolkit.getDefaultToolkit().getImage("image/select/enemy/"+Type+".jpg");
        Img = Toolkit.getDefaultToolkit().getImage("image/game/enemy/"+Type+".jpg");
    }

    public int getType() {
        return Type;
    }

    public Image getImg_mini() {
        return Img_mini;
    }
    public Image getImg() {
        return Img;
    }

    public void setType(int type) {
        Type = type;
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

    public int beAttacked(int Value1, int Value2) {
        if (Avoid == 1) {
            if (Rd.nextInt(3) == 0) {
                successfulAvoid = 1;
                return 0;
            }
        }
        if (Immune1 == 1) Value1 = (int)(Value1 * 0.5);
        if (Immune2 == 1) Value2 = (int)(Value2 * 0.5);
        Hp -= Value1 + Value2;
        if (Hp < 0) Hp = 0;
        return 1;
    }

    public int getAttackValue() {
        int originalValue = Atk;
        if (Strick == 0) return originalValue;
        if (Rd.nextInt(3) == 0) {
            originalValue *= 2;
            successfulStrick = 1;
        }
        return originalValue;
    }
}