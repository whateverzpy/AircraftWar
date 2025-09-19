package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.application.Main;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodProp;
import edu.hitsz.prop.BulletProp;
import edu.hitsz.prop.BombProp;

import java.util.LinkedList;
import java.util.List;


/**
 * 精英敌机
 * 继承AbstractEnemy，具有射击能力的敌机
 */
public class EliteEnemy extends AbstractEnemy {

    /**
     * 射击方向 (向下为正)
     */
    private int direction = 1;
    /**
     * 子弹伤害
     */
    private int power = 25;

    /**
     * 道具掉落概率
     */
    private double propDropRate = 0.5;

    /**
     * 精英敌机构造方法
     *
     * @param locationX X 坐标
     * @param locationY Y 坐标
     * @param speedX    X 轴速度
     * @param speedY    Y 轴速度
     * @param hp        生命值
     */
    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootCycle = 800;
    }

    @Override
    public void forward() {
        super.forward();

        // 检查是否飞出屏幕下方
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction * 2;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction * 5;
        BaseBullet bullet;
        // 精英敌机一次发射一颗子弹
        bullet = new EnemyBullet(x, y, speedX, speedY, power);
        res.add(bullet);
        return res;
    }

    @Override
    public int getScore() {
        return 30;
    }

    @Override
    public List<AbstractProp> mayDrop() {
        List<AbstractProp> props = new LinkedList<>();

        if (Math.random() < propDropRate) {
            int propType = (int) (Math.random() * 3);
            AbstractProp prop;
            switch (propType) {
                case 0:
                    prop = new BloodProp(this.getLocationX(), this.getLocationY(), 0, 5);
                    break;
                case 1:
                    prop = new BombProp(this.getLocationX(), this.getLocationY(), 0, 5);
                    break;
                case 2:
                default:
                    prop = new BulletProp(this.getLocationX(), this.getLocationY(), 0, 5);
                    break;
            }
            props.add(prop);
            System.out.println("EliteEnemy dropped a Prop: " + prop.getClass().getSimpleName());

        }

        return props;
    }

}
