package ru.geekbrains.stargame.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;
import ru.geekbrains.stargame.pool.EnemyPool;
import ru.geekbrains.stargame.sprite.game.Enemy;

public class EnemySmallEmitter {

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.5f;
    private static final int ENEMY_SMALL_DAMEGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 2f;
    private static final int ENEMY_SMALL_HP = 1;
    private Vector2 enemySmallV = new Vector2(0, -0.2f);
    private float enemySmallVX;
    private TextureRegion[] enemySmallRegion;


    private TextureRegion bulletRegion;
    private float generateInterval = 4f;
    private float generateTimer;

    private EnemyPool enemyPool;

    private Rect worldBounds;

    public EnemySmallEmitter(TextureAtlas atlas, EnemyPool enemyPool, Rect worldBounds) {
        this.enemyPool = enemyPool;
        TextureRegion textureRegion = atlas.findRegion("enemy0");
        this.enemySmallRegion = Regions.split(textureRegion, 1, 2, 2);
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.worldBounds = worldBounds;
    }

    public void generate (float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            Enemy enemy = enemyPool.obtain();
            enemySmallVX = Rnd.nextFloat(-0.05f, 0.05f);
            enemySmallV.x = enemySmallVX;
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
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }

}
