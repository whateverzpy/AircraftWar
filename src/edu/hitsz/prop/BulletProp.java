package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.strategy.ScatterShoot;

public class BulletProp extends AbstractProp {
    public BulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void effect(HeroAircraft heroAircraft) {
        System.out.println("FireSupply active! Change to ScatterShoot.");
        heroAircraft.setShootStrategy(new ScatterShoot());
        // 临时增加子弹数量以适配散射
        heroAircraft.setShootNum(3);
    }
}
