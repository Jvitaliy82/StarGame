package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.geekbrains.stargame.StarGame;
import ru.geekbrains.stargame.base.Base2DScreen;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.EnemyPool;
import ru.geekbrains.stargame.pool.ExplosionPool;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.Star;
import ru.geekbrains.stargame.sprite.game.ArrowLeft;
import ru.geekbrains.stargame.sprite.game.ArrowRight;
import ru.geekbrains.stargame.sprite.game.Bullet;
import ru.geekbrains.stargame.sprite.game.Enemy;
import ru.geekbrains.stargame.sprite.game.GameOver;
import ru.geekbrains.stargame.sprite.game.Live;
import ru.geekbrains.stargame.sprite.game.MainShip;
import ru.geekbrains.stargame.sprite.game.NewGame;
import ru.geekbrains.stargame.sprite.game.RedButton;
import ru.geekbrains.stargame.utils.EnemyEmitter;
import ru.geekbrains.stargame.utils.Font;

public class GameScreen extends Base2DScreen {

    private static final String FRAGS = "Frags: ";
    private static final String HP = "XP: ";
    private static final String LEVEL = "Level: ";
    private Music gameMusic;
    private Texture bg;
    private TextureAtlas lv;
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
    private EnemyEmitter enemyEmitter;
    private boolean stopGame = false;
    private GameOver gameOver;
    private NewGame newGame;
    private StarGame game;
    private Font font;
    private StringBuilder sbFrags = new StringBuilder();
    private StringBuilder sbXp = new StringBuilder();
    private StringBuilder sbLevel = new StringBuilder();
    private int frags;
    private Live live;

    public GameScreen(StarGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/gameMusic.mp3"));
        gameMusic.setVolume(0.5f);
        gameMusic.setLooping(true);
        gameMusic.play();
        bg = new Texture("textures/bg.png");
        lv = new TextureAtlas("statusBar/statusBar.tpack");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        atlasButton = new TextureAtlas("textures/moveButtons.pack");
        star = new Star[256];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        enemyPool = new EnemyPool(bulletPool, worldBounds, explosionPool);
        enemyEmitter = new EnemyEmitter(atlas, enemyPool, worldBounds, this);
        mainShip = new MainShip(atlas, bulletPool, explosionPool, this);
        arrowLeft = new ArrowLeft(atlasButton, mainShip);
        arrowRight = new ArrowRight(atlasButton, mainShip);
        redButton = new RedButton(atlasButton, mainShip);
        gameOver = new GameOver(atlas);
        this.font = new Font("font/font.fnt", "font/font.png");
        this.font.setSize(0.02f);
        newGame = new NewGame(atlas, game);
        live = new Live(lv, mainShip);




    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        if (!stopGame) checkCollision();
        deleteAllDestroyed();
        draw();

    }

    private void update(float delta) {
        for (int i = 0; i < star.length; i++) {
            star[i].update(delta);
        }
        if (!mainShip.isDestroyed()) {
            mainShip.update(delta);
        }
        bulletPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemyPool.freeAllDestroyedActiveSprites();
        enemyEmitter.generate(delta, frags);
        live.update(delta, arrowRight.getRight(),
                (worldBounds.getWidth() - redButton.getWidth() - arrowLeft.getWidth() * 2) / (100 / (float) mainShip.getHp()));
        if (isStopGame()) {
            gameOver.update(delta);
            newGame.update(delta);
        }
    }

    private void checkCollision() {
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) continue;
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst2(mainShip.pos) < minDist * minDist) {
                mainShip.damage(enemy.getDamege());
                mainShip.boom();
                mainShip.destroy();
                return;
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();

        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() == mainShip || bullet.isDestroyed()) {
                continue;
            }
            if (mainShip.isBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
            }
        }

        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(mainShip.getDamege());
                    bullet.destroy();
                    if (enemy.isDestroyed()) {
                        frags++;
                    }

                }
            }
        }

    }

    private void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);
        for (Star aStar : star) {
            aStar.draw(batch);
        }
        if (!mainShip.isDestroyed()) {
            mainShip.draw(batch);
        }
        if (!stopGame) {
            arrowLeft.draw(batch);
            arrowRight.draw(batch);
            redButton.draw(batch);
            live.draw(batch);
        }
        bulletPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        if (isStopGame()) {
            gameOver.draw(batch);
            newGame.draw(batch);
        }
        printInfo();
        batch.end();
    }

    public void printInfo() {
        sbFrags.setLength(0);
        sbXp.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft(), worldBounds.getTop());
        font.draw(batch, sbXp.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop(), Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight(), worldBounds.getTop(), Align.right);
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star aStar : star) {
            aStar.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        arrowLeft.resize(worldBounds);
        arrowRight.resize(worldBounds);
        redButton.resize(worldBounds);
        gameOver.resize(worldBounds);
        live.resize(worldBounds);
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
        arrowLeft.touchDown(touch, pointer);
        arrowRight.touchDown(touch, pointer);
        redButton.touchDown(touch, pointer);
        newGame.touchDown(touch, pointer);
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
        font.dispose();
        lv.dispose();
        super.dispose();
    }

    public boolean isStopGame() {
        return stopGame;
    }

    public void setStopGame(boolean stopGame) {
        this.stopGame = stopGame;
    }
}
