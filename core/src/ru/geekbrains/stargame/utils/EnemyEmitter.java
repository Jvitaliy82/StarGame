package ru.geekbrains.stargame.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;
import ru.geekbrains.stargame.pool.EnemyPool;
import ru.geekbrains.stargame.screen.GameScreen;
import ru.geekbrains.stargame.sprite.game.Enemy;

public class EnemyEmitter {

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.5f;
    private static final int ENEMY_SMALL_DAMEGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 2f;
    private static final int ENEMY_SMALL_HP = 1;
    private Vector2 enemySmallV = new Vector2(0, -0.15f);
    private float enemyVX;
    private TextureRegion[] enemySmallRegion;

    private static final float ENEMY_MEDIUM_HEIGHT = 0.15f;
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.015f;
    private static final float ENEMY_MEDIUM_BULLET_VY = -0.5f;
    private static final int ENEMY_MEDIUM_DAMEGE = 2;
    private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 2f;
    private static final int ENEMY_MEDIUM_HP = 5;
    private Vector2 enemyMediumV = new Vector2(0, -0.1f);
    private TextureRegion[] enemyMediumRegion;

    private static final float ENEMY_BIG_HEIGHT = 0.18f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.02f;
    private static final float ENEMY_BIG_BULLET_VY = -0.5f;
    private static final int ENEMY_BIG_DAMEGE = 3;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 2f;
    private static final int ENEMY_BIG_HP = 3;
    private Vector2 enemyBigV = new Vector2(0, -0.05f);
    private TextureRegion[] enemyBigRegion;

    private TextureRegion bulletRegion;
    private float generateInterval = 4f;
    private float generateTimer;
    private GameScreen gameScreen;
    private EnemyPool enemyPool;

    private Rect worldBounds;

    public EnemyEmitter(TextureAtlas atlas, EnemyPool enemyPool, Rect worldBounds, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.enemyPool = enemyPool;
        TextureRegion textureRegion0 = atlas.findRegion("enemy0");
        this.enemySmallRegion = Regions.split(textureRegion0, 1, 2, 2);
        TextureRegion textureRegion1 = atlas.findRegion("enemy1");
        this.enemyMediumRegion = Regions.split(textureRegion1, 1, 2, 2);
        TextureRegion textureRegion2 = atlas.findRegion("enemy2");
        this.enemyBigRegion = Regions.split(textureRegion2, 1, 2, 2);
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.worldBounds = worldBounds;
    }

    public void generate (float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval && !gameScreen.isStopGame()) {
            generateTimer = 0f;
            Enemy enemy = enemyPool.obtain();
            enemyVX = Rnd.nextFloat(-0.05f, 0.05f);

            float type = (float) Math.random();
            if (type < 0.5f) {
                enemySmallV.x = enemyVX;
                enemy.set(
                        enemySmallRegion,
                        enemySmallV,
                        bulletRegion,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        ENEMY_SMALL_BULLET_VY,
                        ENEMY_SMALL_DAMEGE,
                        ENEMY_SMALL_RELOAD_INTERVAL,
                        ENEMY_SMALL_HEIGHT,
                        ENEMY_SMALL_HP
                );
            } else if (type < 0.7f) {
                enemyMediumV.x = enemyVX;
                enemy.set(
                        enemyMediumRegion,
                        enemyMediumV,
                        bulletRegion,
                        ENEMY_MEDIUM_BULLET_HEIGHT,
                        ENEMY_MEDIUM_BULLET_VY,
                        ENEMY_MEDIUM_DAMEGE,
                        ENEMY_MEDIUM_RELOAD_INTERVAL,
                        ENEMY_MEDIUM_HEIGHT,
                        ENEMY_MEDIUM_HP
                );
            } else {
                enemyBigV.x = enemyVX;
                enemy.set(
                        enemyBigRegion,
                        enemyBigV,
                        bulletRegion,
                        ENEMY_BIG_BULLET_HEIGHT,
                        ENEMY_BIG_BULLET_VY,
                        ENEMY_BIG_DAMEGE,
                        ENEMY_BIG_RELOAD_INTERVAL,
                        ENEMY_BIG_HEIGHT,
                        ENEMY_BIG_HP
                );
            }

            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }

}
