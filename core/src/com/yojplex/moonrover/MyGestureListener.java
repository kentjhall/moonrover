package com.yojplex.moonrover;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.yojplex.moonrover.screens.GameScreen;

/**
 * Created by Kent on 8/26/2015.
 */
public class MyGestureListener implements GestureDetector.GestureListener{

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        GameScreen.getPlayer().setMakeLaser(true);
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if (velocityY>1500){
            if (GameScreen.getPlayer().getJumping()) {
                GameScreen.getPlayer().setJumpDone(true);
                GameScreen.getPlayer().setJumpClimax(true);
                GameScreen.getPlayer().setVel(GameScreen.getPlayer().getInitVel() * 2);
                GameScreen.getPlayer().setPlayBodyShrinkAnimation(true);
            }
            else{
                GameScreen.getPlayer().setPlayBodyShrinkAnimation(true);
            }
        }
        else if (velocityY<-1500 && !GameScreen.getPlayer().getJumping()){
            GameScreen.getPlayer().setJumping(true);
        }
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
