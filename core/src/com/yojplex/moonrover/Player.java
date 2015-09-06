package com.yojplex.moonrover;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.yojplex.moonrover.screens.GameScreen;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Kent on 8/25/2015.
 */
public class Player {
    private Vector2 loc;
    private final Vector2 initLoc;
    private float vel;
    private float initVel;
    private Texture body;
    private Animation bodyShrinkAnimation;
    private TextureRegion[] bodyShrink;
    private TextureRegion bodyShrinkFrame;
    private float bodyShrinkStateTime;
    private Animation feetAnimation;
    private TextureRegion[] feet;
    private TextureRegion feetFrame;
    private float feetStateTime;
    private MyGestureListener gestureListener;
    private boolean jumping;
    private boolean jumpClimax;
    private boolean jumpDone;
    private boolean playBodyShrinkAnimation;
    private ArrayList<Laser> lasers;
    private ArrayList<Integer> lasersToRemove;
    private boolean makeLaser;
    private int hitProjectile;
    private float bodyWidth;
    private float bodyHeight;
    private float bodyShrinkHeight;
    private float feetWidth;
    private float feetHeight;
    private Rectangle hitBox;
    private Rectangle midHitBox;

    public Player(Vector2 loc){
        this.loc=loc;
        initLoc=new Vector2(loc);
        vel=11*MyGdxGame.masterScale;
        initVel=new Float(vel);
        body=new Texture("player/body/body.png");
        bodyShrink=new TextureRegion[4];
        for (int i=0; i<bodyShrink.length; i++){
            bodyShrink[i]=new TextureRegion(new Texture("player/body/bodyshrink"+(i+1)+".png"));
        }
        bodyShrinkAnimation=new Animation(0.07f, bodyShrink[0], bodyShrink[1], bodyShrink[2], bodyShrink[3], bodyShrink[3], bodyShrink[3], bodyShrink[3], bodyShrink[3],  bodyShrink[2], bodyShrink[1], bodyShrink[0]);
        bodyShrinkStateTime=0f;
        feet=new TextureRegion[3];
        for (int i=0; i<feet.length; i++){
            feet[i]=new TextureRegion(new Texture("player/feet/feet"+(i+1)+".png"));
        }
        feetAnimation=new Animation(0.1f, feet[0], feet[1], feet[2]);
        feetStateTime=0f;
        gestureListener=new MyGestureListener();
        Gdx.input.setInputProcessor(new GestureDetector(gestureListener));
        jumping=false;
        jumpClimax=false;
        jumpDone=false;
        playBodyShrinkAnimation=false;
        lasers=new ArrayList<Laser>();
        lasersToRemove=new ArrayList<Integer>();
        makeLaser=false;
        hitProjectile=-1;
        bodyWidth=body.getTextureData().getWidth() * 12 * MyGdxGame.masterScale;
        bodyHeight=body.getTextureData().getHeight() * 12 * MyGdxGame.masterScale;
        hitBox=new Rectangle(loc.x, loc.y, bodyWidth*0.58f, feetHeight+bodyHeight);
        midHitBox=new Rectangle(loc.x, loc.y, bodyWidth, bodyHeight*0.3f);
    }

    public void draw(SpriteBatch batch){
        if (makeLaser){
            shootLaser();
        }
        if (!playBodyShrinkAnimation){
            hitBox.setHeight(feetHeight+bodyHeight);
        }
        hitBox.setPosition(loc);
        for (Laser laser:lasers){
            laser.draw(batch);
            if (laser.isHit()){
                //gets the index of the hit projectile
                hitProjectile=laser.getHitProjectile();
            }
            if (laser.getLoc().x > Gdx.graphics.getWidth()) {
                Integer integer = Integer.valueOf(lasers.indexOf(laser));
                lasersToRemove.add(integer);
            }
        }
        for (Integer integer:lasersToRemove){
            lasers.remove(integer.intValue());
        }
        lasersToRemove.clear();
        for (Rectangle hitBox: GameScreen.getProjectileHitBox()){
            if (Intersector.overlaps(this.hitBox, hitBox)){
                System.out.println("hit");
                hitProjectile=GameScreen.getProjectileHitBox().indexOf(hitBox);
            }
        }
        if (!playBodyShrinkAnimation) {
            batch.draw(body, loc.x, loc.y + 108 * MyGdxGame.masterScale, bodyWidth, bodyHeight);
        }
        playFeetAnimation(batch);
        if (jumping){
            jump();
        }
        if (playBodyShrinkAnimation){
            playShrinkAnimation(batch);
        }
    }

    public void playFeetAnimation(SpriteBatch batch){
        feetStateTime+= Gdx.graphics.getDeltaTime();
        feetFrame=feetAnimation.getKeyFrame(feetStateTime, true);
        feetWidth=feetFrame.getTexture().getTextureData().getWidth()* 12 * MyGdxGame.masterScale;
        feetHeight=feetFrame.getTexture().getTextureData().getHeight()* 12 * MyGdxGame.masterScale;
        batch.draw(feetFrame, loc.x, loc.y, feetWidth, feetHeight);
    }

    public void playShrinkAnimation(SpriteBatch batch){
        bodyShrinkStateTime+=Gdx.graphics.getDeltaTime();
        bodyShrinkFrame=bodyShrinkAnimation.getKeyFrame(bodyShrinkStateTime, false);
        bodyShrinkHeight=bodyShrinkFrame.getTexture().getTextureData().getHeight() * 12 * MyGdxGame.masterScale;
        batch.draw(bodyShrinkFrame, loc.x, loc.y + 108 * MyGdxGame.masterScale, bodyWidth, bodyShrinkHeight);
        hitBox.setHeight(feetHeight + bodyShrinkHeight);
        if (bodyShrinkAnimation.isAnimationFinished(bodyShrinkStateTime)){
            bodyShrinkStateTime=0f;
            hitBox.setHeight(feetHeight+bodyHeight);
            playBodyShrinkAnimation=false;
        }
    }

    public void jump(){
        int jumpHeight=75;
        if (loc.y<initLoc.y+jumpHeight && !jumpClimax){
            jumpDone=false;
        }
        else if (vel<=0){
            jumpDone=true;
            jumpClimax=true;
        }

        if (!jumpDone){
            if (loc.y>=initLoc.y+jumpHeight) {
                vel-=0.6;
            }
            loc.y+=vel;
        }
        else{
            if (loc.y>initLoc.y){
                if (vel<initVel) {
                    vel += 0.6;
                }
                loc.y -= vel;
            }
            else{
                jumpClimax=false;
                jumping=false;
                vel=initVel;
            }
        }
    }

    public void shootLaser(){
        Laser laser=new Laser(new Vector2(0, 0), 0);
        if (!playBodyShrinkAnimation) {
            laser = new Laser(new Vector2(loc.x + body.getTextureData().getWidth() * 9 * MyGdxGame.masterScale, loc.y + (body.getTextureData().getHeight() - 19) * 12 * MyGdxGame.masterScale + feetFrame.getTexture().getTextureData().getHeight() * 12*MyGdxGame.masterScale / 2), 40);
        }
        else{
            laser = new Laser(new Vector2(loc.x + bodyShrinkFrame.getTexture().getTextureData().getWidth() * 9 * MyGdxGame.masterScale, loc.y + (bodyShrinkFrame.getTexture().getTextureData().getHeight() - 19) * 12 * MyGdxGame.masterScale + feetFrame.getTexture().getTextureData().getHeight() * 12*MyGdxGame.masterScale / 2), 40);
        }
        lasers.add(laser);
        makeLaser=false;
    }

    public void dispose(){
        body.dispose();
        for (int i=0; i<feet.length; i++){
            feet[i].getTexture().dispose();
        }
        for (int i=0; i<bodyShrink.length; i++){
            bodyShrink[i].getTexture().dispose();
        }
    }

    public boolean getJumping(){
        return jumping;
    }

    public void setJumping(boolean jumping){
        this.jumping=jumping;
    }

    public void setVel(float vel){
        this.vel=vel;
    }

    public float getInitVel(){
        return initVel;
    }

    public void setJumpDone(boolean jumpDone){
        this.jumpDone=jumpDone;
    }

    public void setJumpClimax(boolean jumpClimax){
        this.jumpClimax=jumpClimax;
    }

    public void setPlayBodyShrinkAnimation(boolean playBodyShrinkAnimation){
        this.playBodyShrinkAnimation=playBodyShrinkAnimation;
    }

    public void setMakeLaser(boolean makeLaser){
        this.makeLaser=makeLaser;
    }

    public float getBodyHeight(){
        return body.getTextureData().getHeight();
    }

    public float getFeetHeight(){
        return feetFrame.getTexture().getTextureData().getHeight();
    }

    public float getLocY(){
        return loc.y;
    }

    public TextureRegion getBodyShrinkFrame(){
        return bodyShrinkFrame;
    }

    public TextureRegion getFeetFrame(){
        return feetFrame;
    }

    public int getHitProjectile(){
        return hitProjectile;
    }

    public void setHitProjectile(int hitProjectile){
        this.hitProjectile=hitProjectile;
    }

    public Rectangle getHitBox(){
        return hitBox;
    }
}
