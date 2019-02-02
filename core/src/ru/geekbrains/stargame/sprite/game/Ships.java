package ru.geekbrains.stargame.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;

public class Ships extends Sprite {

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected Vector2 v = new Vector2();
    protected Vector2 bulletV;
    protected float bulletHeight;
    protected int damege;
    protected int hp;
    protected TextureRegion bulletRegion;

    protected float reloadInterval;
    protected float reloadTimer;

    protected Sound shootSound;


    public Ships() {
        super();
    }

    public Ships(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;

    }

    public void shoot() {
        shootSound.play();
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damege);
    }

    public void dispose(){
        shootSound.dispose();
    }

}
