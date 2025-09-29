package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.factory.prop.PropType;
import edu.hitsz.factory.prop.UnifiedPropFactory;
import edu.hitsz.prop.AbstractProp;

import edu.hitsz.application.ImageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Boss 敌机
 * 悬浮于界面上方，左右移动，环形射击
 */
public class BossEnemy extends AbstractEnemy {

    /**
     * 子弹伤害
     */
    private final int power = 40;
    /**
     * 射击方向 (环形)
     */
    private final int shootNum = 20;
    /**
     * 道具掉落数量范围
     */
    private final int minDrop = 1;
    private final int maxDrop = 3;

    private final UnifiedPropFactory propFactory = new UnifiedPropFactory();
    private final Random random = new Random();

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootCycle = 1000; // 射击周期
    }

    /**
     * 环形射击，同时发射多颗子弹
     */
    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new ArrayList<>();
        int x = this.getLocationX();
        int y = this.getLocationY();
        int bulletSpeed = 6;
        double angleStep = 360.0 / shootNum;

        for (int i = 0; i < shootNum; i++) {
            double angle = Math.toRadians(i * angleStep);
            int speedX = (int) (bulletSpeed * Math.sin(angle));
            int speedY = (int) (bulletSpeed * Math.cos(angle));
            res.add(new EnemyBullet(x, y, speedX, speedY, power));
        }
        return res;
    }

    @Override
    public int getScore() {
        return 100;
    }

    @Override
    public List<AbstractProp> mayDrop() {
        List<AbstractProp> props = new ArrayList<>();
        int dropCount = random.nextInt(maxDrop - minDrop + 1) + minDrop;
        PropType[] propTypes = PropType.values();

        // 计算最大道具宽度与安全间距，确保横向不重叠
        int maxPropWidth = Math.max(
                ImageManager.PROP_BLOOD_IMAGE.getWidth(),
                Math.max(ImageManager.PROP_BOMB_IMAGE.getWidth(), ImageManager.PROP_BULLET_IMAGE.getWidth())
        );
        int spacing = Math.max(40, maxPropWidth + 10);   // 额外留 10px 缝隙
        int halfMax = maxPropWidth / 2;

        int baseX = this.getLocationX();
        int y = this.getLocationY();
        double mid = (dropCount - 1) / 2.0;

        for (int i = 0; i < dropCount; i++) {
            int offset = (int) Math.round((i - mid) * spacing);
            int dropX = baseX + offset;
            // 以中心点约束在屏幕内，避免出界
            dropX = Math.max(halfMax, Math.min(Main.WINDOW_WIDTH - halfMax, dropX));

            PropType randomType = propTypes[random.nextInt(propTypes.length)];
            props.add(propFactory.setType(randomType).create(dropX, y));
        }
        return props;
    }
}