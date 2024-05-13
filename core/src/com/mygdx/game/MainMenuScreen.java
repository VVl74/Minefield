package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    //@Override

    final MainGame game;
    OrthographicCamera camera;
    Texture background;

    public MainMenuScreen(final MainGame pr) {
        game = pr;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 540, 1000);

        background = new Texture("tekstura-gazon-trava-a04f94a.png");
    }
    public void show() {

    }

    @Override
    public void render(float d) {
        ScreenUtils.clear(0, 0, 0, 1);
        //batch.draw(img, 0, 0);
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        game.batch.draw(background, 0, 0);

         game.font.draw(game.batch, "Minefield", 250, 700);
        game.font.draw(game.batch, "Tap to start the game", 150, 500);

        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
