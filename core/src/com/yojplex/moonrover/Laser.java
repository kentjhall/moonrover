package com.yojplex.moonrover;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Kent on 8/26/2015.
 */
public class Laser {
    private Texture laser;
    private Vector2 loc;
    private float vel;

    public Laser(Vector2 loc, float vel){
        this.loc=loc;
        this.vel=vel;
        laser=new Texture("player/laser.png");
    }

    public void draw(SpriteBatch batch){
        if (loc.x<Gdx.graphics.getWidth()) {
            batch.draw(laser, loc.x, loc.y, laser.getTextureData().getWidth() * 6 * MyGdxGame.masterScale, laser.getTextureData().getHeight() * 6 * MyGdxGame.masterScale);
            loc.x += vel;
        }
        else{
            dispose();
        }
    }

    public void dispose(){
        laser.dispose();
    }

    public Vector2 getLoc(){
        return loc;
    }
}
