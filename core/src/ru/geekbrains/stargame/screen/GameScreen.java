package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Base2DScreen;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.EnemyPool;
import ru.geekbrains.stargame.pool.ExplosionPool;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.Star;
import ru.geekbrains.stargame.sprite.game.ArrowLeft;
import ru.geekbrains.stargame.sprite.game.ArrowRight;
import ru.geekbrains.stargame.sprite.game.Explosion;
import ru.geekbrains.stargame.sprite.game.MainShip;
import ru.geekbrains.stargame.sprite.game.RedButton;
import ru.geekbrains.stargame.utils.EnemyBigEmitter;
import ru.geekbrains.stargame.utils.EnemyMediumEmitter;
import ru.geekbrains.stargame.utils.EnemySmallEmitter;

public class GameScreen extends Base2DScreen {

    private Music gameMusic;
    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private TextureAtlas atlasButton;
    private Star star[];
    private MainShip mainShip;
    private ArrowLeft arrowLeft;
    private ArrowRight arrowRight;
    private RedButton redButton;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private EnemySmallEmitter enemySmallEmitter;
    private EnemyMediumEmitter enemyMediumEmitter;
    private EnemyBigEmitter enemyBigEmitter;

    @Override
    public void show() {
        super.show();
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/gameMusic.mp3"));
        gameMusic.setVolume(0.5f);
        gameMusic.setLooping(true);
        gameMusic.play();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        atlasButton = new TextureAtlas("textures/moveButtons.pack");
        star = new Star[256];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        mainShip = new MainShip(atlas, bulletPool);
        arrowLeft = new ArrowLeft(atlasButton, mainShip);
        arrowRight = new ArrowRight(atlasButton, mainShip);
        redButton = new RedButton(atlasButton, mainShip);
        explosionPool = new ExplosionPool(atlas);
        enemyPool = new EnemyPool(bulletPool, worldBounds);
        enemySmallEmitter = new EnemySmallEmitter(atlas, enemyPool, worldBounds);
        enemyMediumEmitter = new EnemyMediumEmitter(atlas, enemyPool, worldBounds);
        enemyBigEmitter = new EnemyBigEmitter(atlas,enemyPool, worldBounds);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        deleteAllDestroyed();
        draw();

    }

    public void update(float delta) {
        for (int i = 0; i < star.length; i++) {
            star[i].update(delta);
        }
        mainShip.update(delta);
        bulletPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemyPool.freeAllDestroyedActiveSprites();
        enemySmallEmitter.generate(delta);
        enemyMediumEmitter.generate(delta);
        enemyBigEmitter.generate(delta);
    }

    public void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }

    public void draw() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);
        for (int i = 0; i < star.length; i++) {
            star[i].draw(batch);
        }
        mainShip.draw(batch);
        arrowLeft.draw(batch);
        arrowRight.draw(batch);
        redButton.draw(batch);
        bulletPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        arrowLeft.resize(worldBounds);
        arrowRight.resize(worldBounds);
        redButton.resize(worldBounds);
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        Explosion explosion = explosionPool.obtain();
        explosion.set(0.15f, touch);
        arrowLeft.touchDown(touch, pointer);
        arrowRight.touchDown(touch, pointer);
        redButton.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        arrowLeft.touchUp(touch, pointer);
        arrowRight.touchUp(touch, pointer);
        redButton.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        atlasButton.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        mainShip.dispose();
        enemyPool.dispose();
        super.dispose();
    }
}
