package com.yojplex.moonrover;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.yojplex.moonrover.screens.GameScreen;

/**
 * Created by Kent on 8/26/2015.
 */
public class Laser {
    private Texture laser;
    private Vector2 loc;
    private float vel;
    private float width;
    private float height;
    private boolean hit;
    private int hitProjectile;
    private Rectangle hitBox;

    public Laser(Vector2 loc, float vel){
        this.loc=loc;
        this.vel=vel;
        laser=new Texture("player/laser.png");
        width=laser.getTextureData().getWidth() * 6 * MyGdxGame.masterScale;
        height=laser.getTextureData().getHeight() * 6 * MyGdxGame.masterScale;
        hitBox=new Rectangle(loc.x, loc.y, width, height);
    }

    public void draw(SpriteBatch batch){
        if (loc.x<Gdx.graphics.getWidth()) {
            batch.draw(laser, loc.x, loc.y, width, height);
            loc.x += vel;
            hitBox.setPosition(loc);
        }
        else{
            dispose();
        }

        //checks for every hitbox in projectile hitbox array
        for (Rectangle hitBox: GameScreen.getProjectileHitBox()){
            //checks if overlap with laser hitbox
            if (Intersector.overlaps(this.hitBox, hitBox)){
                hit=true;
                hitProjectile=GameScreen.getProjectileHitBox().indexOf(hitBox);
            }
        }
    }

    public void dispose(){
        laser.dispose();
    }

    public Vector2 getLoc(){
        return loc;
    }

    public boolean isHit(){
        return hit;
    }

    public int getHitProjectile(){
        return hitProjectile;
    }
}
