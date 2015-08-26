package com.yojplex.moonrover;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.yojplex.moonrover.screens.GameScreen;

public class MyGdxGame extends Game {
	private SpriteBatch batch;
	public static float masterScale;

	@Override
	public void create () {
		batch = new SpriteBatch();
		if (Gdx.graphics.getHeight()<750){
			masterScale=0.55f;
		}
		else if (Gdx.graphics.getHeight()<1080){
			masterScale=0.65f;
		}
		else{
			masterScale=1f;
		}

		setScreen(new GameScreen(batch));
	}

	@Override
	public void render () {
		super.render();
	}

    @Override
    public void dispose(){
        batch.dispose();
    }
}
