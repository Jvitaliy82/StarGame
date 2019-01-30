package ru.geekbrains.stargame.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprite.menu.ScaledTouchUpButton;

public class ArrowLeft extends ScaledTouchUpButton {

    private MainShip mainShip;
    private int isTouch;

    public ArrowLeft(TextureAtlas atlas, MainShip mainShip) {
        super(atlas.findRegion("arrowLeft"));
        setHeightProportion(0.1f);
        this.mainShip = mainShip;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setLeft(worldBounds.getLeft());
        setBottom(worldBounds.getBottom());
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (isMe(touch)) {
            isTouch = pointer;
            mainShip.isPressedLeft = true;
            mainShip.moveLeft();
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (isTouch == pointer) {
            mainShip.isPressedLeft = false;
            if (mainShip.isPressedRight) {
                mainShip.moveRight();
            } else {
                mainShip.stop();
            }
        }
        return super.touchUp(touch, pointer);
    }

    @Override
    public void action() {

    }
}
