package edu.hitsz.factory.prop;

import edu.hitsz.prop.AbstractProp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 根据概率随机掉落一个或多个道具（当前实现：最多一个），内部使用统一工厂减少重复类
 */
public class RandomPropDropper {
    private final double dropRate; // 掉落概率
    private final UnifiedPropFactory unifiedFactory = new UnifiedPropFactory();
    private final Random random = new Random();
    private final PropType[] types = PropType.values();

    public RandomPropDropper(double dropRate) {
        this.dropRate = dropRate;
    }

    public List<AbstractProp> generate(int x, int y) {
        List<AbstractProp> res = new ArrayList<>();
        if (random.nextDouble() < dropRate) {
            PropType type = types[random.nextInt(types.length)];
            unifiedFactory.setType(type);
            res.add(unifiedFactory.create(x, y));
        }
        return res;
    }
}
