package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class Assets {

    public static BitmapFont font;
    public static TextButton.TextButtonStyle textButtonStyle;
    public static Label.LabelStyle labelStyle;
    public static ScrollPane.ScrollPaneStyle scrollPaneStyle;

    public static void load() {
        font = new BitmapFont();

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("data/ui.txt"));

        NinePatchDrawable bt = new NinePatchDrawable(atlas.createPatch("bt"));
        NinePatchDrawable btDown = new NinePatchDrawable(atlas.createPatch("btDown"));
        textButtonStyle = new TextButton.TextButtonStyle(bt, btDown, null, font);

        labelStyle = new Label.LabelStyle(Assets.font, Color.GREEN);

        NinePatchDrawable knob = new NinePatchDrawable(atlas.createPatch("scroll"));
        scrollPaneStyle = new ScrollPane.ScrollPaneStyle(null, knob, knob, knob, knob);
    }

}
