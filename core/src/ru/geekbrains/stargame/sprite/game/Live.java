package ru.geekbrains.stargame.sprite.game;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;

public class Live extends Sprite {

    private MainShip mainShip;

    public Live(TextureAtlas atlas, MainShip mainShip) {
        super(atlas.findRegion("live"), 1, 3, 3);
        setHeight(0.02f);
        this.mainShip = mainShip;
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
        if (mainShip.getHp() < 25) {
            frame = 0;
        } else if (mainShip.getHp() < 75) {
            frame = 2;
        }

    }
}
