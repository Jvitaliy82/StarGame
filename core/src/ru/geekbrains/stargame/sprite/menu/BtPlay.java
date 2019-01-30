package ru.geekbrains.stargame.sprite.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.StarGame;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.screen.GameScreen;


public class BtPlay extends ScaledTouchUpButton{

    StarGame game;

    public BtPlay(TextureAtlas atlas, StarGame game) {
        super(atlas.findRegion("btPlay"));
        setHeightProportion(0.3f);
        this.game = game;


    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        pos.set(0,0);

    }


    @Override
    public void action() {
        System.out.println("нажата play");
        game.setScreen(new GameScreen());


    }
}
