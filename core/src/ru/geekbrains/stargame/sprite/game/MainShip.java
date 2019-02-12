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
import ru.geekbrains.stargame.pool.ExplosionPool;
import ru.geekbrains.stargame.screen.GameScreen;


public class MainShip extends Ships {

    private GameScreen gameScreen;

    private final Vector2 v0 = new Vector2(0.5f, 0);


    public boolean isPressedLeft;
    public boolean isPressedRight;
    public boolean isShoot;






    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool, GameScreen gameScreen) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shootMainShip1.mp3"));
        this.gameScreen = gameScreen;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.reloadInterval = 0.1f;
        setHeightProportion(0.15f);
        this.bulletV = new Vector2(0, 0.7f);
        this.bulletHeight = 0.01f;
        this.damege = 1;
        this.hp = 10;
        this.maxHp = hp;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (reloadTimer < reloadInterval) reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {

            if (isShoot) {
                shoot();
                reloadTimer = 0.01f;
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
                isShoot = true;
//                shoot();
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
            case Input.Keys.SPACE:
                isShoot = false;
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

    public boolean isBulletCollision(Rect bullet) {

        return !(bullet.getRight() < getLeft() ||
                bullet.getLeft() > getRight() ||
                bullet.getBottom() > pos.y ||
                bullet.getTop() < getBottom()

        );
    }

    @Override
    public void destroy() {
        super.destroy();
        gameScreen.setStopGame(true);
    }
}
