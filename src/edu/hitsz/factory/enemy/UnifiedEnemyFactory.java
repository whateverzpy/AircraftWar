package edu.hitsz.factory.enemy;

import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

/**
 * 统一敌机工厂，通过传入类型创建不同敌机，减少多个类的重复代码
 */
public class UnifiedEnemyFactory implements EnemyFactory {

    private EnemyType type = EnemyType.MOB;

    // 可配置的默认属性
    private int mobHp = 30;
    private int mobSpeedY = 10;
    private int eliteHp = 60;
    private int eliteBaseSpeedY = 10;

    public UnifiedEnemyFactory() {
    }

    public UnifiedEnemyFactory(EnemyType type) {
        this.type = type;
    }

    public UnifiedEnemyFactory setType(EnemyType type) {
        this.type = type;
        return this;
    }

    public UnifiedEnemyFactory configMob(int hp, int speedY) {
        this.mobHp = hp;
        this.mobSpeedY = speedY;
        return this;
    }

    public UnifiedEnemyFactory configElite(int hp, int baseSpeedY) {
        this.eliteHp = hp;
        this.eliteBaseSpeedY = baseSpeedY;
        return this;
    }

    @Override
    public AbstractEnemy createEnemy() {
        switch (type) {
            case ELITE:
                return createElite();
            case MOB:
            default:
                return createMob();
        }
    }

    private AbstractEnemy createMob() {
        int x = (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()));
        int y = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05);
        return new MobEnemy(x, y, 0, mobSpeedY, mobHp);
    }

    private AbstractEnemy createElite() {
        int x = (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth()));
        int y = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05);
        int speedX = (int) (Math.random() * 20 - 10);
        return new EliteEnemy(x, y, speedX, eliteBaseSpeedY, eliteHp);
    }
}
