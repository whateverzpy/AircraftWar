package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.application.Main;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodProp;

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
    private int power = 30;

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();

        // 检查左右边界，实现左右移动
        int aircraftWidth = ImageManager.ELITE_ENEMY_IMAGE.getWidth(); // 假设敌机宽度为50像素，根据实际情况调整
        if (locationX <= aircraftWidth / 2 || locationX >= Main.WINDOW_WIDTH - aircraftWidth / 2) {
            // 到达边界时反转水平速度方向
            speedX = -speedX;
            // 确保敌机不会卡在边界外
            if (locationX <= aircraftWidth / 2) {
                locationX = aircraftWidth / 2;
            } else {
                locationX = Main.WINDOW_WIDTH - aircraftWidth / 2;
            }
            System.out.println("EliteEnemy changed direction, new speedX: " + speedX);
        }

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
        return 30; // 精英敌机得分为30
    }

    @Override
    public List<AbstractProp> mayDrop() {
        List<AbstractProp> props = new LinkedList<>();

        if (Math.random() < 0.5) {
            // 这里需要根据项目中实际的道具类来创建
            // 示例：假设有血包道具类 BloodProp
            AbstractProp prop = new BloodProp(this.getLocationX(), this.getLocationY(), 0, 5);
            props.add(prop);
            System.out.println("EliteEnemy dropped a BloodProp");
        }

        return props;
    }

}
