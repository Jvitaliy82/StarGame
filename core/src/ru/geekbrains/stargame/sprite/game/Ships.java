package ru.geekbrains.stargame.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.ExplosionPool;

public class Ships extends Sprite {

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected Vector2 v = new Vector2();
    protected Vector2 bulletV;
    protected float bulletHeight;
    protected int damege;
    protected int hp;
    protected TextureRegion bulletRegion;
    protected ExplosionPool explosionPool;
    protected float reloadInterval;
    protected float reloadTimer;

    private float damageInterval = 0.1f;
    private float damageTimer = damageInterval;

    protected Sound shootSound;


    public Ships() {
        super();
    }

    public Ships(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }


    @Override
    public void update(float delta) {
        super.update(delta);
        damageTimer += delta;
        if (damageTimer >= damageInterval) {
            frame = 0;
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;

    }

    public void shoot() {
        shootSound.play(1, 1, -1f);
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damege);
    }

    public void boom () {
        Explosion explosion = explosionPool.obtain();
        System.out.println("получен взрыв");
        explosion.set(getHeight(), pos);
    }

    public  void damage(int damege) {
        frame = 1;
        damageTimer = 0f;
        hp -= damege;
        if (hp <= 0) {
            hp = 0;
            destroy();
            boom();
        }
    }

    public int getHp() {
        return hp;
    }

    public int getDamege() {
        return damege;
    }

    public void dispose(){
        shootSound.dispose();
    }

}
