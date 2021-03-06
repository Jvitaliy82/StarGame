package ru.geekbrains.stargame.sprite.game;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;

public class Live extends Sprite {

    private Ships T;

    public Live(TextureAtlas atlas, Ships T) {
        super(atlas.findRegion("live"), 1, 3, 3);
        setHeight(0.02f);
        this.T = T;
        frame = 1;


    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + getHeight());

    }

    public void update(float delta, float left, float width) {
        super.update(delta);
        setWidth(width);
        setLeft(left);
        if ((float) T.getHp() / T.getMaxHp() < 0.25f) {
            frame = 0;
        } else if ((float) T.getHp() / T.getMaxHp() < 0.75f) {
            frame = 2;
        }

    }
}
