package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class BulletProp extends AbstractProp {
    public BulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void forward() {
        super.forward();
    }

    @Override
    public void action(HeroAircraft heroAircraft) {
        // TODO: Implement bullet effect
        System.out.println("BulletProp activated!");
    }
}
