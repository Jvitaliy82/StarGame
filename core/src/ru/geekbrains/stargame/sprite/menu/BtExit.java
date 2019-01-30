package ru.geekbrains.stargame.sprite.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.stargame.math.Rect;


public class BtExit extends ScaledTouchUpButton {


    public BtExit(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
        setHeightProportion(0.07f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setTop(worldBounds.getTop());
        setRight(worldBounds.getRight());
    }

    @Override
    public void action() {
        System.exit(0);
    }
}
