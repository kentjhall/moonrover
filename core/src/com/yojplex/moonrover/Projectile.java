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
        object[0]=new Texture("trash.png");
        generator=new Random();
        randObject=generator.nextInt(object.length);

        switch (lane){
            case TOP:
                loc=new Vector2(Gdx.graphics.getWidth(), GameScreen.getPlayer().getBodyHeight()*13*MyGdxGame.masterScale);
                break;
            case MID:
                loc=new Vector2(Gdx.graphics.getWidth(), GameScreen.getPlayer().getLocY() + (GameScreen.getPlayer().getBodyHeight() - 19) * 12 * MyGdxGame.masterScale + GameScreen.getPlayer().getFeetFrame().getTexture().getTextureData().getHeight() * 12 / 2);
                break;
            case BOT:
                loc=new Vector2(Gdx.graphics.getWidth(), GameScreen.getPlayer().getLocY());
                break;

        }
    }

    public void draw(SpriteBatch batch){
        if (loc.x>-100) {
            batch.draw(object[randObject], loc.x, loc.y, object[randObject].getTextureData().getWidth()*4*MyGdxGame.masterScale, object[randObject].getTextureData().getHeight()*4*MyGdxGame.masterScale);
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

    public Vector2 getLoc(){
        return loc;
    }
}
