package ru.geekbrains.stargame.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.StarGame;
import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.screen.GameScreen;

public class NewGame extends Sprite {

    private Rect worldBounds;
    private GameScreen gameScreen;
    private StarGame game;

    public NewGame(TextureAtlas atlas, StarGame game) {
        super(atlas.findRegion("button_new_game"));
        this.game = game;
        setHeightProportion(0.05f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        pos.set(0f, worldBounds.getHalfHeight() * -0.5f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (isMe(touch)) {
            game.setScreen(new GameScreen(game));
        }
        System.out.println("запуск игры");
        return super.touchDown(touch, pointer);
    }
}
