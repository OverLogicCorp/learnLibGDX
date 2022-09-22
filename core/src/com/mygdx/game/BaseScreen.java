package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public abstract class BaseScreen extends InputAdapter implements Screen {

    public static final float SCREEN_WIDTH = 800; // No son píxeles, son medidas para nuestro User Interface.
    public static final float SCREEN_HEIGHT = 480;

    public static final float WORLD_WIDTH = 8f; // Medidas para la librería de físicas Box2D.
    public static final float WORLD_HEIGHT = 4.8f;

    public MainLearn game;

    public Stage stage; // Escena donde colocar los elementos de nuestro User Interface.

    public OrthographicCamera oCamUI; // Cámara para nuestro User Interface.
    public OrthographicCamera oCamBox2D; // Cámara para la librería de físicas Box2D.

    public SpriteBatch spriteBatch;

    public BaseScreen(MainLearn game) {
        this.game = game;

        stage = new Stage(new StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT));

        oCamUI = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
        oCamUI.position.set(SCREEN_WIDTH / 2f, SCREEN_HEIGHT / 2f, 0);

        oCamBox2D = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        oCamBox2D.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0);

        spriteBatch = new SpriteBatch();

        InputMultiplexer inputMultiplexer = new InputMultiplexer(this, stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public abstract void update(float delta);
    public abstract void draw(float delta);

    @Override
    public void render(float delta) {
        update(delta);
        stage.act(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        draw(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.ESCAPE || keyCode == Input.Keys.BACK) {
            if (this instanceof MainMenuScreen) {
                Gdx.app.exit();
            } else {
                game.setScreen(new MainMenuScreen(game));
            }
        }
        return super.keyDown(keyCode);
    }

    @Override
    public void show() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}

}
