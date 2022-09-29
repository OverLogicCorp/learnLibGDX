package com.mygdx.game.tutoriales;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Assets;
import com.mygdx.game.BaseScreen;
import com.mygdx.game.MainLearn;

public class Learn6 extends BaseScreen {

    private World world;
    private Box2DDebugRenderer renderer;

    private Array<Body> arrayBodies;
    private Array<GameObject> arrayGameObjects;
    private TextureRegion ball, box;

    public Learn6(MainLearn game) {
        super(game);
        Vector2 gravity = new Vector2(0, -9.8f);
        world = new World(gravity, true);
        world.setContactListener(new CollisionListener());
        renderer = new Box2DDebugRenderer();
        arrayBodies = new Array<>();
        arrayGameObjects = new Array<>();
        ball = new TextureRegion(new Texture(Gdx.files.internal("data/ball.png")));
        box = new TextureRegion(new Texture(Gdx.files.internal("data/box.png")));
        createFloor();
        createBall();
        createBox();
    }

    private void createFloor() {
        BodyDef bd = new BodyDef();
        bd.position.set(0, 0.6f);
        bd.type = BodyDef.BodyType.StaticBody;

        EdgeShape shape = new EdgeShape();
        shape.set(0, 0, WORLD_WIDTH, 0);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = shape;
        fixDef.friction = .7f;

        Body body = world.createBody(bd);
        body.createFixture(fixDef);

        shape.dispose();
    }

    private void createBall() {
        GameObject obj = new GameObject((float) Math.random() * WORLD_WIDTH, 5, GameObject.BALL);

        BodyDef bd = new BodyDef();
        bd.position.set(obj.position.x, obj.position.y);
        bd.type = BodyDef.BodyType.DynamicBody;

        CircleShape shape = new CircleShape();
        shape.setRadius(.15f);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = shape;
        fixDef.density = 15;
        fixDef.friction = .5f;
        fixDef.restitution = .5f;

        Body body = world.createBody(bd);
        body.createFixture(fixDef);
        body.setUserData(obj);

        arrayGameObjects.add(obj);

        shape.dispose();
    }

    private void createBox() {
        GameObject obj = new GameObject((float) Math.random() * WORLD_WIDTH, 5, GameObject.BOX);

        BodyDef bd = new BodyDef();
        bd.position.set(obj.position.x, obj.position.y);
        bd.type = BodyDef.BodyType.DynamicBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(.15f, .15f);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = shape;
        fixDef.density = 15;
        fixDef.friction = .5f;
        fixDef.restitution = .1f;

        Body body = world.createBody(bd);
        body.createFixture(fixDef);
        body.setUserData(obj);

        arrayGameObjects.add(obj);

        shape.dispose();
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.justTouched()) {
            if (MathUtils.randomBoolean()) {
                createBox();
            } else {
                createBall();
            }
        }
        world.step(delta, 8, 6);
        world.getBodies(arrayBodies);
        for (Body body : arrayBodies) {
            if (world.isLocked()) continue;
            if (body.getUserData() instanceof GameObject) {
                GameObject obj = (GameObject) body.getUserData();
                if (obj.state == GameObject.STATE_HIT) {
                    arrayGameObjects.removeValue(obj, true);
                    world.destroyBody(body);
                }
            }
        }
        world.getBodies(arrayBodies);
        for (Body body : arrayBodies) {
            if (body.getUserData() instanceof GameObject) {
                GameObject obj = (GameObject) body.getUserData();
                obj.update(body);
            }
        }
    }

    @Override
    public void draw(float delta) {
        oCamUI.update();
        spriteBatch.setProjectionMatrix(oCamUI.combined);

        spriteBatch.begin();
        Assets.font.draw(spriteBatch, "Touch the screen to create more objects", 0, 470);
        Assets.font.draw(spriteBatch, "Fps: " + Gdx.graphics.getFramesPerSecond(), 0, 20);
        spriteBatch.end();

        oCamBox2D.update();
        spriteBatch.setProjectionMatrix(oCamBox2D.combined);

        spriteBatch.begin();
        drawGameObjects();
        spriteBatch.end();

        renderer.render(world, oCamBox2D.combined);
    }

    private void drawGameObjects() {
        for (GameObject obj : arrayGameObjects) {
            TextureRegion keyframe;
            if (obj.type == GameObject.BOX) {
                keyframe = box;
            } else {
                keyframe = ball;
            }
            spriteBatch.draw(keyframe, obj.position.x - .15f, obj.position.y - .15f, .15f, .15f, .3f, .3f, 1, 1, obj.angleDeg);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        world.dispose();
    }

    static public class GameObject {
        static final int BALL = 0;
        static final int BOX = 1;
        static final int STATE_NORMAL = 0;
        static final int STATE_HIT = 1;
        int state;
        final int type;
        float angleDeg;
        Vector2 position;

        public GameObject(float x, float y, int type) {
            this.position = new Vector2(x, y);
            this.state = STATE_NORMAL;
            this.type = type;
        }

        public void update(Body body) {
            position.x = body.getPosition().x;
            position.y = body.getPosition().y;
            angleDeg = (float) Math.toDegrees(body.getAngle());
        }

        public void hit() {
            state = STATE_HIT;
        }
    }

    static private class CollisionListener implements ContactListener {

        @Override
        public void beginContact(Contact contact) {
            Body bodyA = contact.getFixtureA().getBody();
            Body bodyB = contact.getFixtureB().getBody();
            if (bodyA.getUserData() instanceof GameObject && bodyB.getUserData() instanceof GameObject) {
                GameObject gameObjectA = (GameObject) bodyA.getUserData();
                GameObject gameObjectB = (GameObject) bodyB.getUserData();
                if (gameObjectA.type == gameObjectB.type) {
                    gameObjectA.hit();
                    gameObjectB.hit();
                }
            }
        }

        @Override
        public void endContact(Contact contact) {

        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {

        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {

        }
    }

}
