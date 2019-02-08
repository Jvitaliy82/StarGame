package ru.geekbrains.stargame.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Interpolation;

import ru.geekbrains.stargame.base.SpritesPool;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprite.game.Enemy;

public class EnemyPool extends SpritesPool<Enemy> {

    private Sound shotSound;
    private BulletPool bulletPool;
    private Rect worldBounds;
    private ExplosionPool explosionPool;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds, ExplosionPool explosionPool) {
        this.shotSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shootEnemy.wav"));
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(shotSound, bulletPool, explosionPool, worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        shotSound.dispose();
    }
}
