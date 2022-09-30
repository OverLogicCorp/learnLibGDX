package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.tutoriales.*;
import com.mygdx.game.utils.Learn;

public class MainMenuScreen extends BaseScreen {

    ScrollPane scroll;

    public MainMenuScreen(MainLearn game) {
        super(game);

        Table menu = new Table();
        menu.setFillParent(true);
        menu.defaults().uniform().fillY();

        for (final Learn learn : Learn.values()) {
            TextButton bt = new TextButton(learn.name, Assets.textButtonStyle);
            bt.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    MainMenuScreen.this.game.setScreen(getScreen(learn));
                }
            });
            menu.row().padTop(20).height(50);
            menu.add(bt).fillX();
        }

        scroll = new ScrollPane(menu, Assets.scrollPaneStyle);
        scroll.setSize(500, SCREEN_HEIGHT);
        scroll.setPosition(150, 0);
        stage.addActor(scroll);
    }

    private BaseScreen getScreen(Learn learn) {
        switch (learn) {
            case LEARN_1:
                return new Learn1(game);
            case LEARN_2:
                return new Learn2(game);
            case LEARN_3:
                return new Learn3(game);
            case LEARN_4:
                return new Learn4(game);
            case LEARN_5:
                return new Learn5(game);
            case LEARN_6:
                return new Learn6(game);
            case LEARN_7:
                return new Learn7(game);
            default:
                return new Learn1(game);
        }
    }

    @Override
    public void update(float delta) {}

    @Override
    public void draw(float delta) {}

}
