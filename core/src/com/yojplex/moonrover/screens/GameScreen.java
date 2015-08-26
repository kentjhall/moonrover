package com.yojplex.moonrover.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.yojplex.moonrover.MyGdxGame;
import com.yojplex.moonrover.Player;

/**
 * Created by Kent on 8/25/2015.
 */
public class GameScreen implements Screen{
    SpriteBatch batch;
    Player player;

    public GameScreen(SpriteBatch batch){
        this.batch=batch;
        player=new Player(new Vector2(50*MyGdxGame.masterScale, 0));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        player.draw(batch);
        batch.end();
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
