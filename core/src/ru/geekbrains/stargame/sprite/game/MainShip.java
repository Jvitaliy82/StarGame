package ru.geekbrains.stargame.sprite.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;


public class MainShip extends Sprite {

    private Sound shootMainShip;
    private Rect worldBounds;
    private final Vector2 v0 = new Vector2(0.5f, 0);
    private Vector2 v = new Vector2();

    public boolean isPressedLeft;
    public boolean isPressedRight;
    public boolean isShoot;

    private float reloadInterval;
    private float reloadTimer;

    private BulletPool bulletPool;
    private TextureRegion bulletRegion;

    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        shootMainShip = Gdx.audio.newSound(Gdx.files.internal("sounds/shootMainShip1.mp3"));
        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.reloadInterval = 0.2f;
        setHeightProportion(0.15f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (reloadTimer < reloadInterval) reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {

            if (isShoot) {
                shoot();
                reloadTimer = 0.00f;
            }
        }
        if (pos.cpy().mulAdd(v, delta).x - halfWidth < worldBounds.getLeft() ||
            pos.cpy().mulAdd(v, delta).x + halfWidth > worldBounds.getRight()) {
            stop();

        } else {
            pos.mulAdd(v, delta);
        }


    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                isPressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                isPressedRight = true;
                moveRight();
                break;
            case Input.Keys.SPACE:
                System.out.println("стрельба по пробелу");
                shoot();
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                isPressedLeft = false;
                if (isPressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                isPressedRight = false;
                if (isPressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
        return false;
    }

    public void moveRight(){
            if (!checkBounds()) v.set(v0);
    }

    public void moveLeft(){
            if (!checkBounds()) v.set(v0).rotate(180);
    }

    public void stop(){
        v.setZero();
    }

    private boolean checkBounds(){
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            return true;
        }
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            return true;
        }
        return false;
    }

    public void shoot() {
        shootMainShip.play();
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, new Vector2(0, 0.5f), 0.01f, worldBounds, 1);
    }



}
