package com.yojplex.moonrover.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.yojplex.moonrover.MyGdxGame;
import com.yojplex.moonrover.Player;
import com.yojplex.moonrover.Projectile;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Kent on 8/25/2015.
 */
public class GameScreen implements Screen{
    private static SpriteBatch batch;
    private static Player player;
    private ArrayList<Projectile> projectiles;
    private ArrayList<Integer> projectilesToRemove;
    private boolean makeProjectile;
    private long launchTime;
    private Random generator;
    private int projectileSpeed;
    private static ArrayList<Rectangle> projectileHitBox;
    private Texture ground;

    public GameScreen(SpriteBatch batch){
        this.batch=batch;
        player=new Player(new Vector2(100*MyGdxGame.masterScale, 0*MyGdxGame.masterScale));

        makeProjectile=false;
        projectiles=new ArrayList<Projectile>();
        projectilesToRemove=new ArrayList<Integer>();
        projectileHitBox=new ArrayList<Rectangle>();
        launchTime= TimeUtils.nanoTime();
        generator=new Random();
        projectileSpeed=20;
        ground=new Texture("gray.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        player.draw(batch);
        //launch a projectile every set number of milliseconds
        if (TimeUtils.timeSinceNanos(launchTime)>TimeUtils.millisToNanos(2000)){
            makeProjectile=true;
            launchTime=TimeUtils.nanoTime();
        }
        if (makeProjectile){
            launchProjectile();
        }
        for (Projectile projectile:projectiles){
            //draw projectiles in array
            projectile.draw(batch);
            if (projectile.getLoc().x<-100){
                //add projectiles that are off screen to "remove" array
                Integer integer=Integer.valueOf(projectiles.indexOf(projectile));
                projectilesToRemove.add(integer);
            }
            if (player.getHitProjectile()==projectiles.indexOf(projectile)){
                projectile.setHit(true);
                player.setHitProjectile(-1);
            }
        }
        for (Integer integer:projectilesToRemove){
            //remove projectiles and hitboxes in arrays because off screen
            projectiles.remove(integer.intValue());
            projectileHitBox.remove(integer.intValue());
        }
        projectilesToRemove.clear();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        player.dispose();
    }

    public void launchProjectile(){
        //initialize projectile
        Projectile projectile=new Projectile(Projectile.Lane.MID, projectileSpeed);
        switch (generator.nextInt(3)){
            //randomize lane of projectile
            case 0:
                projectile=new Projectile(Projectile.Lane.TOP, projectileSpeed);
                break;
            case 1:
                projectile=new Projectile(Projectile.Lane.MID, projectileSpeed);
                break;
            case 2:
                projectile=new Projectile(Projectile.Lane.BOT, projectileSpeed);
                break;
        }
        //add projectile to projectile array and projectile's hitbox to hitbox array
        projectiles.add(projectile);
        projectileHitBox.add(projectile.getHitBox());
        makeProjectile=false;
    }

    public static Player getPlayer(){
        return player;
    }

    public static ArrayList<Rectangle> getProjectileHitBox(){
        return projectileHitBox;

    }
}
