package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {

    private HeroAircraft hero;

    @BeforeEach
    void setUp() {
        hero = HeroAircraft.getInstance();
        // 重置以避免测试间相互影响
        hero.setShootNum(1);
        hero.setHp(hero.getMaxHp());
    }

    @AfterEach
    void tearDown() {
        hero.setShootNum(1);
        hero.setHp(hero.getMaxHp());
    }

    @Test
    void getInstance() {
        HeroAircraft a = HeroAircraft.getInstance();
        HeroAircraft b = HeroAircraft.getInstance();
        assertSame(a, b, "HeroAircraft 应为单例");
        assertEquals(100, a.getHp(), "初始 HP 应为 100");
        assertEquals(a.getMaxHp(), a.getHp(), "初始 HP 应等于 maxHp");
    }

    @Test
    void forward() {
        int x0 = hero.getLocationX();
        int y0 = hero.getLocationY();
        hero.forward();
        assertEquals(x0, hero.getLocationX(), "forward 不应改变 X");
        assertEquals(y0, hero.getLocationY(), "forward 不应改变 Y");
    }

    @Test
    void shoot_defaultSingleBullet() {
        hero.setShootNum(1);
        List<BaseBullet> bullets = hero.shoot();
        assertEquals(1, bullets.size(), "默认应发射 1 发子弹");
        BaseBullet b = bullets.get(0);
        assertTrue(b instanceof HeroBullet, "应为 HeroBullet");
        assertEquals(30, b.getPower(), "子弹伤害应为 30");
        assertEquals(hero.getLocationX(), b.getLocationX(), "单发 X 应与英雄机一致");
        assertEquals(hero.getLocationY() - 2, b.getLocationY(), "Y 应为英雄机位置向上 2 像素");
    }

    @Test
    void updateShootTimerControlsFireRate() {
        // 英雄机射击周期为 100ms
        assertFalse(hero.updateShootTimer(50), "50ms 不应触发射击");
        assertTrue(hero.updateShootTimer(50), "累计 100ms 应触发射击");
        assertFalse(hero.updateShootTimer(99), "再计 99ms 不应触发");
        assertTrue(hero.updateShootTimer(1), "累计到 100ms 再次触发");
    }
}