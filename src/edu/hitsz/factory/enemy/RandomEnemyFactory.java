package edu.hitsz.factory.enemy;

import edu.hitsz.aircraft.AbstractEnemy;

/**
 * 根据精英概率随机生成敌机，内部复用统一工厂，通过切换类型避免多个子类工厂
 */
public class RandomEnemyFactory implements EnemyFactory {

    private final UnifiedEnemyFactory unifiedFactory = new UnifiedEnemyFactory();
    private double eliteProbability = 0.3; // 默认概率

    public RandomEnemyFactory(double eliteProbability) {
        this.eliteProbability = eliteProbability;
    }

    public void setEliteProbability(double eliteProbability) {
        this.eliteProbability = eliteProbability;
    }

    @Override
    public AbstractEnemy createEnemy() {
        double r = Math.random();
        if (r < eliteProbability) {
            unifiedFactory.setType(EnemyType.ELITE);
        } else {
            unifiedFactory.setType(EnemyType.MOB);
        }
        return unifiedFactory.createEnemy();
    }
}
