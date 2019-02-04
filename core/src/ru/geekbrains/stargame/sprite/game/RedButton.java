package ru.geekbrains.stargame.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprite.menu.ScaledTouchUpButton;

public class RedButton extends ScaledTouchUpButton {

    private MainShip mainShip;
    private boolean isPressed;
    private int pointer;

    public RedButton(TextureAtlas atlas, MainShip mainShip) {
        super(atlas.findRegion("redButton"));
        setHeightProportion(0.1f);
        this.mainShip = mainShip;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setRight(worldBounds.getRight());
        setBottom(worldBounds.getBottom());
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (isMe(touch)) {
            isPressed = true;
            mainShip.isShoot = true;
            this.pointer = pointer;
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (this.pointer == pointer) {
            isPressed = false;
            mainShip.isShoot = false;
        }
        return super.touchUp(touch, pointer);
    }

    @Override
    public void action() {

    }
}
