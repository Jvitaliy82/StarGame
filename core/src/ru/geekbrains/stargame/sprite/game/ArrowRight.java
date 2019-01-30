package ru.geekbrains.stargame.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprite.menu.ScaledTouchUpButton;

public class ArrowRight extends ScaledTouchUpButton {

    private MainShip mainShip;
    private int isTouch;

    public ArrowRight(TextureAtlas atlas, MainShip mainShip) {
        super(atlas.findRegion("arrowRight"));
        setHeightProportion(0.1f);
        this.mainShip = mainShip;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setLeft(worldBounds.getLeft() + getWidth());
        setBottom(worldBounds.getBottom());
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (isMe(touch)) {
            isTouch = pointer;
            mainShip.isPressedRight = true;
            mainShip.moveRight();
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (isTouch == pointer) {
            mainShip.isPressedRight = false;
            if (mainShip.isPressedLeft) {
                mainShip.moveLeft();
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
