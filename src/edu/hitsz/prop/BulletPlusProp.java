package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.strategy.CircleShoot;

public class BulletPlusProp extends AbstractProp {
    public BulletPlusProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void effect(HeroAircraft heroAircraft) {
        System.out.println("SuperFireSupply active! Change to CircleShoot.");
        heroAircraft.setShootStrategy(new CircleShoot());
        // 临时增加子弹数量以适配环射
        heroAircraft.setShootNum(12);
    }
}