package ru.geekbrains.stargame.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.screen.GameScreen;

public class GameOver extends Sprite {

    private Rect worldBounds;
    private float maxHeight = 0.08f;
    private float size = 0.01f;

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
        setHeightProportion(0.01f);

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        pos.set(0f, worldBounds.getHalfHeight() * 0.5f);
    }

    @Override
    public void update(float delta) {
        if (size < maxHeight) {
            size += delta * 0.05f ;
            setHeightProportion(size);
        }
    }


}
