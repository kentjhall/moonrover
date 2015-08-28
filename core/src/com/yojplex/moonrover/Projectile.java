package com.yojplex.moonrover;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.yojplex.moonrover.screens.GameScreen;

import java.util.Random;

/**
 * Created by Kent on 8/28/2015.
 */
public class Projectile {
    public enum Lane{
        TOP, MID, BOT
    }
    private Vector2 loc;
    private float vel;
    private Texture[] object;
    private Random generator;
    private int randObject;

    public Projectile(Lane lane, float vel){
        this.vel=vel;
        object=new Texture[1];
        randObject=generator.nextInt(object.length);

        switch (lane){
            case TOP:
                loc=new Vector2(Gdx.graphics.getWidth(), GameScreen.getPlayer().getFeetHeight()*12+GameScreen.getPlayer().getBodyHeight()*12);
                break;
            case MID:
                loc=new Vector2();
                break;
            case BOT:
                loc=new Vector2();
                break;

        }
    }

    public void draw(SpriteBatch batch){
        if (loc.x<0) {
            batch.draw(object[randObject], loc.x, loc.y);
            loc.x-=vel;
        }
        else{
            dispose();
        }
    }

    public void dispose(){
        for (int i=0; i<object.length; i++){
            object[i].dispose();
        }
    }
}
