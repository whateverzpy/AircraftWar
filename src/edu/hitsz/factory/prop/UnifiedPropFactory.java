package edu.hitsz.factory.prop;

import edu.hitsz.prop.*;

/**
 * 统一道具工厂，根据类型创建具体道具
 */
public class UnifiedPropFactory implements PropFactory {

    private PropType type = PropType.BLOOD;
    private int speedY = 5;

    public UnifiedPropFactory() {
    }

    public UnifiedPropFactory(PropType type) {
        this.type = type;
    }

    public UnifiedPropFactory setType(PropType type) {
        this.type = type;
        return this;
    }

    public UnifiedPropFactory setSpeedY(int speedY) {
        this.speedY = speedY;
        return this;
    }

    @Override
    public AbstractProp create(int x, int y) {
        switch (type) {
            case BOMB:
                return new BombProp(x, y, 0, speedY);
            case BULLET:
                return new BulletProp(x, y, 0, speedY);
            case BLOOD:
            default:
                return new BloodProp(x, y, 0, speedY);
        }
    }
}
