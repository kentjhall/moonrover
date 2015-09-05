package com.yojplex.moonrover;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
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
    private boolean hit;
    private Rectangle hitBox;
    private float width;
    private float height;

    public Projectile(Lane lane, float vel){
        this.vel=vel;
        object=new Texture[1];
        object[0]=new Texture("trash.png");
        generator=new Random();
        randObject=generator.nextInt(object.length);
        hit=false;
        width=object[randObject].getTextureData().getWidth()*4*MyGdxGame.masterScale;
        height=object[randObject].getTextureData().getHeight()*4*MyGdxGame.masterScale;


        switch (lane){
            //set projectile starting position based on chosen lane
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
        hitBox=new Rectangle(loc.x-width, loc.y, width, height);
    }

    public void draw(SpriteBatch batch){
        if (loc.x>-100 && !hit) {
            //draw projectile moving left and update hitbox to its position while on screen
            batch.draw(object[randObject], loc.x, loc.y, width, height);
            loc.x-=vel;
            hitBox.setPosition(loc);
        }
        else{
            //dispose projectile when off screen
            dispose();
        }
    }

    public void dispose(){
        //dispose all object textures
        for (int i=0; i<object.length; i++){
            object[i].dispose();
        }
        hitBox.setPosition(-100, -100);
    }

    public Vector2 getLoc(){
        return loc;
    }

    public Rectangle getHitBox(){
        return hitBox;
    }

    public void setHit(boolean hit){
        this.hit=hit;
    }
}
