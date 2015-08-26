package com.yojplex.moonrover;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Kent on 8/25/2015.
 */
public class Player {
    private Vector2 loc;
    private Animation feetAnimation;
    private TextureRegion[] feet;
    private TextureRegion feetFrame;
    private float feetStateTime;

    public Player(Vector2 loc){
        this.loc=loc;
        feet=new TextureRegion[3];
        for (int i=0; i<feet.length; i++){
            feet[i]=new TextureRegion(new Texture("player/feet/feet"+(i+1)+".png"));
        }
        feetAnimation=new Animation(0.1f, feet[0], feet[1], feet[2]);
        feetStateTime=0f;
        System.out.println(loc);
    }

    public void draw(SpriteBatch batch){
        playFeetAnimation(batch);
    }

    public void playFeetAnimation(SpriteBatch batch){
        feetStateTime+= Gdx.graphics.getDeltaTime();
        feetFrame=feetAnimation.getKeyFrame(feetStateTime, true);
        batch.draw(feetFrame, loc.x, loc.y, 345f*MyGdxGame.masterScale, 140.625f*MyGdxGame.masterScale);
    }
}
