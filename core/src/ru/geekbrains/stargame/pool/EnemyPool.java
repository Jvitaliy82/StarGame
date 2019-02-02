package ru.geekbrains.stargame.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.stargame.base.SpritesPool;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprite.game.Enemy;

public class EnemyPool extends SpritesPool<Enemy> {

    private Sound shotSound;
    private BulletPool bulletPool;
    private Rect worldBounds;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds) {
        this.shotSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shootEnemy.wav"));
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(shotSound, bulletPool, worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        shotSound.dispose();
    }
}
