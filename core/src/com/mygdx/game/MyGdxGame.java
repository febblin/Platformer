package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	OrthographicCamera camera;
	float rotate;
	World world;
	Box2DDebugRenderer debugRenderer;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		img = new Texture("badlogic.jpg");

		world = new World( new Vector2(0, -9.81f), true);
		debugRenderer = new Box2DDebugRenderer();

		BodyDef bodyDef = new BodyDef();
		bodyDef.active = true; //активно ли тело
		bodyDef.allowSleep = true; //разрешать телу спать
		bodyDef.bullet = false;
		bodyDef.gravityScale = 4.0f;  //масштаб гравитации
		bodyDef.position.x = 0;
		bodyDef.position.y = 0;
		bodyDef.type = BodyDef.BodyType.DynamicBody;

		FixtureDef def = new FixtureDef();
		def.density = 1.f; //плотность
		def.friction = .0f; //шершавость
		def.restitution = 0.5f; //отталкивание
		PolygonShape shape = new PolygonShape(); //полигон
		shape.setAsBox(5, 5); //половины длинн сторон
		def.shape = shape;
		Body body = world.createBody(bodyDef); //создали физическое тело!
		body.createFixture(def);

		bodyDef.position.set(0, -200);
		bodyDef.type = BodyDef.BodyType.StaticBody;
		shape.setAsBox(100, 5);
		def.shape = shape;
		body = world.createBody(bodyDef); //создали физическое тело!
		body.createFixture(def);


	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();

		debugRenderer.render(world, camera.combined);
		world.step(1/60f, 6, 2);

//		if (Gdx.input.isKeyPressed(Input.Keys.D)) camera.position.x++;
//		if (Gdx.input.isKeyPressed(Input.Keys.A)) camera.position.x--;
//		if (Gdx.input.isKeyPressed(Input.Keys.W)) camera.position.y++;
//		if (Gdx.input.isKeyPressed(Input.Keys.S)) camera.position.y--;
//		if (Gdx.input.isKeyPressed(Input.Keys.P)) camera.zoom -= .01f;
//		if (Gdx.input.isKeyPressed(Input.Keys.O)) camera.zoom += .01f;
//		if (Gdx.input.isKeyPressed(Input.Keys.K)) rotate = -.5f;
//		if (Gdx.input.isKeyPressed(Input.Keys.L)) rotate = .5f;



		camera.rotate(rotate);
		camera.update();
		rotate = .0f;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
	}
}
