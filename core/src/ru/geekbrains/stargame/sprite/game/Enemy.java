package ru.geekbrains.stargame.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.ExplosionPool;

public class Enemy extends Ships {

    private enum State {DESCENT, FIGHT}

    private Vector2 v0 = new Vector2();
    private State state;
    private Vector2 descentV = new Vector2(0, -0.15f);



    public Enemy(Sound shotSound, BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        super();
        this.shootSound = shotSound;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.v.set(v0);
        this.bulletV = new Vector2();
        this.worldBounds = worldBounds;

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        this.pos.mulAdd(v, delta);
        switch (state) {
            case DESCENT:
                if (getTop() <= worldBounds.getTop()) {
                    v.set(v0);
                    state = State.FIGHT;
                    break;
                }
            case FIGHT:
                if (getBottom() < worldBounds.getBottom()) {
                    destroy();
                    boom();
                }
                reloadTimer += delta;
                if (reloadTimer >= reloadInterval) {
                    reloadTimer = 0;
                    shoot();
                }
                break;
        }

     }

    public void set (
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int bulletDamege,
            float reloadInterval,
            float height,
            int hp
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damege = bulletDamege;
        this.reloadInterval = reloadInterval;
        setHeightProportion(height);
        this.hp = hp;
        v.set(descentV);
        state = State.DESCENT;
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft() ||
                bullet.getLeft() > getRight() ||
                bullet.getBottom() > getTop() ||
                bullet.getTop() < pos.y

        );
    }
}
