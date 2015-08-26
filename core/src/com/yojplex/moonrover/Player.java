package com.yojplex.moonrover;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Kent on 8/25/2015.
 */
public class Player {
    private Vector2 loc;
    private Texture body;
    private Animation feetAnimation;
    private TextureRegion[] feet;
    private TextureRegion feetFrame;
    private float feetStateTime;
    private MyGestureListener gestureListener;

    public Player(Vector2 loc){
        this.loc=loc;
        body=new Texture("player/body/body.png");
        feet=new TextureRegion[3];
        for (int i=0; i<feet.length; i++){
            feet[i]=new TextureRegion(new Texture("player/feet/feet"+(i+1)+".png"));
        }
        feetAnimation=new Animation(0.1f, feet[0], feet[1], feet[2]);
        feetStateTime=0f;
        gestureListener=new MyGestureListener();
        Gdx.input.setInputProcessor(new GestureDetector(gestureListener));
    }

    public void draw(SpriteBatch batch){
        batch.draw(body, loc.x, loc.y + 108 * MyGdxGame.masterScale, 492 * MyGdxGame.masterScale, 468 * MyGdxGame.masterScale);
        playFeetAnimation(batch);
    }

    public void playFeetAnimation(SpriteBatch batch){
        feetStateTime+= Gdx.graphics.getDeltaTime();
        feetFrame=feetAnimation.getKeyFrame(feetStateTime, true);
        batch.draw(feetFrame, loc.x, loc.y, 276 * MyGdxGame.masterScale, 108 * MyGdxGame.masterScale);
    }

    public void dispose(){
        body.dispose();
        for (int i=0; i<feet.length; i++){
            feet[i].getTexture().dispose();
        }
    }
}
