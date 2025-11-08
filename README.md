# 飞机大战 (Aircraft War)

这是一个基于 Java Swing 实现的经典纵向卷轴飞行射击游戏——飞机大战。作为软件构造课程的实验项目，它不仅实现了游戏的核心功能，还综合运用了多种设计模式来构建一个结构清晰、可扩展性强的软件系统。

## 核心技术栈

- **语言:** Java
- **GUI:** Java Swing
- **并发:** `ScheduledExecutorService` 实现游戏主循环，多线程处理音效播放与道具计时。

## 实验重点与设计模式应用

本项目通过应用多种设计模式，将游戏的不同模块解耦，提高了代码的可维护性和复用性。

### 1. 单例模式 (Singleton Pattern) - 英雄机

为了确保在整个游戏中只存在一个玩家操控的英雄机实例，`HeroAircraft` 类被设计为单例。通过静态方法 `getInstance()` 获取全局唯一的英雄机对象，便于在游戏各个模块中访问和控制。

- **相关代码:** HeroAircraft.java

### 2. 工厂模式 (Factory Pattern) - 敌机与道具生成

游戏中的敌机和道具种类繁多，为了将对象的创建与使用分离，我们采用了工厂模式。

- **敌机工厂 (`EnemyFactory`):** `UnifiedEnemyFactory` 能够根据指定的类型（普通、精英、Boss）或按设定的概率随机创建不同种类的敌机。这使得在不同难度下调整敌机生成策略变得非常简单。
- **道具工厂 (`PropFactory`):** `UnifiedPropFactory` 负责创建不同效果的道具（加血、炸弹、火力增强等），同样支持随机掉落。

### 3. 策略模式 (Strategy Pattern) - 子弹发射方式

为了实现多样化的射击方式（如直线、散射、环形射击），并允许在运行时动态切换（例如，英雄机拾取火力道具后），项目引入了策略模式。

- `ShootStrategy` 接口定义了射击行为。
- `DirectShoot`, `ScatterShoot`, `CircleShoot` 等类作为具体策略，实现了不同的射击算法。
- 飞机类持有一个 `ShootStrategy` 引用，将射击的具体实现委托给当前的策略对象。

- **相关代码:**
  - ShootStrategy.java
  - AbstractAircraft.java

### 4. 观察者模式 (Observer Pattern) - 炸弹道具效果

当玩家使用炸弹道具时，需要清除屏幕上的所有敌机和敌方子弹。为了避免炸弹道具与所有可能被影响的对象产生紧耦合，我们采用了观察者模式。

- **发布者 (`BombPublisher`):** 一个全局的事件中心。当炸弹被触发时，它会通知所有已注册的观察者。
- **订阅者 (`BombSubscriber`):** 屏幕上所有受炸弹影响的单位（如 `MobEnemy`, `EliteEnemy`, `EnemyBullet`）都实现了此接口。它们在被创建时向 `BombPublisher` 注册，在接收到通知时执行自我销毁等操作，并返回因此获得的分数。

- **相关代码:**
  - BombPublisher.java
  - BombSubscriber.java
  - BombProp.java

### 5. 数据访问对象模式 (DAO Pattern) - 排行榜

为了将游戏逻辑与数据持久化方式分离，排行榜功能采用了 DAO 模式。

- `ScoreDao` 接口定义了对得分记录的增、查、改等标准操作。
- `FileScoreDaoImpl` 是一个具体的实现，它负责将 `ScoreRecord` 对象序列化并存储到本地文件中。
- 如果未来需要将排行榜数据存储到数据库，只需提供一个新的 `ScoreDao` 实现类，而无需修改上层业务代码。

- **相关代码:**
  - ScoreDao.java
  - FileScoreDaoImpl.java
  - Score.java

### 6. 模板方法模式 (Template Method Pattern) - 游戏难度设计

游戏包含简单、普通、困难三种难度，它们在敌机生成逻辑、难度增长方式等方面存在差异，但总体流程相似。模板方法模式在此被用来构建难度框架。

- `DifficultyTemplate` 抽象类定义了游戏周期的骨架（`onCycle`），其中包含初始化 (`doInit`)、随时间调整难度 (`scaleOverTime`) 和生成敌人 (`spawnEnemies`) 等抽象步骤。
- `EasyDifficulty`, `NormalDifficulty`, `HardDifficulty` 等具体子类通过重写这些抽象方法，来定义各自难度下的具体行为。

- **相关代码:**
  - DifficultyTemplate.java
  - Game.java

## 界面与并发

- **Swing 界面:** 游戏包含开始菜单、游戏主界面和排行榜三个核心界面，全部使用 Java Swing 构建，实现了基本的 GUI 交互。
- **多线程设计:**
  - 游戏的主循环（包括画面刷新、碰撞检测、对象移动等）由 `ScheduledExecutorService` 在一个独立的后台线程中定时执行，避免了对 Swing 事件分发线程（EDT）的阻塞，保证了流畅的动画效果和UI响应。
  - 背景音乐、Boss 战音乐和各种音效均通过独立的 `MusicThread` 线程播放，与主逻辑分离。
  - 火力增强道具的持续时间通过启动一个短暂的计时器线程来实现。
