package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.factory.prop.UnifiedPropFactory;
import edu.hitsz.prop.AbstractProp;

import java.util.LinkedList;
import java.util.List;

/**
 * 超级精英敌机
 * 具备扇形射击能力，移动轨迹为向下和横向结合
 */
public class ElitePlusEnemy extends AbstractEnemy {

    /**
     * 射击方向 (向下为正)
     */
    private final int direction = 1;
    /**
     * 子弹伤害
     */
    private final int power = 30;

    /**
     * 道具掉落概率
     */
    private final double propDropRate = 0.5;
    private final UnifiedPropFactory propFactory = new UnifiedPropFactory().enableRandomDrop(propDropRate);

    /**
     * @param locationX X 坐标
     * @param locationY Y 坐标
     * @param speedX    X 轴速度
     * @param speedY    Y 轴速度
     * @param hp        生命值
     */
    public ElitePlusEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootCycle = 800; // 射击周期
    }

    @Override
    public void forward() {
        super.forward();
        // 检查是否飞出屏幕下方
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    /**
     * 扇形射击，同时发射三颗子弹
     */
    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction * 2;
        int speedY = this.getSpeedY() + direction * 5;

        // 左侧子弹
        res.add(new EnemyBullet(x, y, -2, speedY, power));
        // 中间子弹
        res.add(new EnemyBullet(x, y, 0, speedY, power));
        // 右侧子弹
        res.add(new EnemyBullet(x, y, 2, speedY, power));

        return res;
    }

    @Override
    public int getScore() {
        return 50;
    }

    @Override
    public List<AbstractProp> mayDrop() {
        return propFactory.generate(this.getLocationX(), this.getLocationY());
    }
}