package com.mygdx.game.tutoriales;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Assets;
import com.mygdx.game.BaseScreen;
import com.mygdx.game.MainLearn;

public class Learn4 extends BaseScreen {

    private World world;
    private Box2DDebugRenderer renderer;
    private Body[] arrayBalls;
    private Vector2 speed;

    public Learn4(MainLearn game) {
        super(game);
        Vector2 gravity = new Vector2(0, -9.8f);
        world = new World(gravity, true);
        renderer = new Box2DDebugRenderer();
        arrayBalls = new Body[3];
        speed = new Vector2(0, 8);
        createFloor();
        arrayBalls[0] = createBall(2.5f, .8f);
        arrayBalls[1] = createBall(4f, .8f);
        arrayBalls[2] = createBall(5.5f, .8f);
        arrayBalls[0].applyForceToCenter(speed, true);
        arrayBalls[1].applyLinearImpulse(speed, arrayBalls[1].getWorldCenter(), true);
        arrayBalls[2].setLinearVelocity(speed);
    }

    private void createFloor() {
        BodyDef bd = new BodyDef();
        bd.position.set(0, 0.5f);
        bd.type = BodyDef.BodyType.StaticBody;

        EdgeShape shape = new EdgeShape();
        shape.set(0, 0, WORLD_WIDTH, 0);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = shape;

        Body body = world.createBody(bd);
        body.createFixture(fixDef);

        shape.dispose();
    }

    private Body createBall(float positionX, float positionY) {
        BodyDef bd = new BodyDef();
        bd.position.set(positionX, positionY);
        bd.type = BodyDef.BodyType.DynamicBody;

        CircleShape shape = new CircleShape();
        shape.setRadius(.25f);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = shape;
        fixDef.density = 15;
        fixDef.friction = .5F;
        fixDef.restitution = .5F;

        Body body = world.createBody(bd);
        body.createFixture(fixDef);

        shape.dispose();
        return body;
    }

    @Override
    public void update(float delta) {
        world.step(delta, 8, 6);
    }

    @Override
    public void draw(float delta) {
        oCamUI.update();
        spriteBatch.setProjectionMatrix(oCamUI.combined);

        spriteBatch.begin();
        Assets.font.draw(spriteBatch, "Fps: " + Gdx.graphics.getFramesPerSecond(), 0, 20);
        spriteBatch.end();

        oCamBox2D.update();;
        renderer.render(world, oCamBox2D.combined);
    }

    @Override
    public void dispose() {
        super.dispose();
        world.dispose();
    }

}
