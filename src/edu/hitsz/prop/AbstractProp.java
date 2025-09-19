package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.basic.AbstractFlyingObject;

public abstract class AbstractProp extends AbstractFlyingObject {

    public AbstractProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    /**
     * 道具向下飞行
     * 超出屏幕底部边界时消失
     */
    @Override
    public void forward() {
        super.forward();
        // 道具超出屏幕底部时消失
        if (locationY >= edu.hitsz.application.Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    /**
     * 道具生效的抽象方法
     * 具体道具类需要实现此方法定义道具效果
     *
     * @param heroAircraft 英雄机对象
     */
    public abstract void action(HeroAircraft heroAircraft);
}
