package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.StarGame;
import ru.geekbrains.stargame.base.Base2DScreen;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.Star;
import ru.geekbrains.stargame.sprite.menu.BtExit;
import ru.geekbrains.stargame.sprite.menu.BtPlay;


public class MenuScreen extends Base2DScreen {

    private StarGame game;
    private Music menuMusic;
    private TextureAtlas atlas;
    private Texture bg;
    private Background background;
    private Star star[];
    private BtPlay play;
    private BtExit exit;


    public MenuScreen(StarGame game) {
        this.game = game;
    }



    @Override
    public void show() {
        super.show();
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/menuMusic.mp3"));
        menuMusic.setVolume(0.5f);
        menuMusic.setLooping(true);
        menuMusic.play();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        star = new Star[256];
        play = new BtPlay(atlas, game);
        exit = new BtExit(atlas);
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(atlas);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    public void update(float delta) {
        for (int i = 0; i < star.length; i++) {
            star[i].update(delta);
        }
    }

    public void draw() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);
        for (int i = 0; i < star.length; i++) {
            star[i].draw(batch);
        }
        play.draw(batch);
        exit.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
        play.resize(worldBounds);
        exit.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        play.touchDown(touch, pointer);
        exit.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        play.touchUp(touch, pointer);
        exit.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }



    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        menuMusic.dispose();
        super.dispose();
    }
}
