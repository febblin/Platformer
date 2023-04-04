package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TideMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	OrthographicCamera camera;
	float rotate;
	World world;
	Box2DDebugRenderer debugRenderer;
	Body player;
	TiledMap map;
	OrthogonalTiledMapRenderer mapRenderer;

	
	@Override
	public void create () {

		map = new TmxMapLoader().load("карта1.tmx");

		mapRenderer = new OrthogonalTiledMapRenderer(map);

		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.zoom = 0.1f;

		world = new World( new Vector2(0, -9.81f), true);
		debugRenderer = new Box2DDebugRenderer();
		ObjectMaker.makeObj(world, new Vector2(0,-200), new Vector2(100,5), BodyDef.BodyType.StaticBody, 0);
		ObjectMaker.makeObj(world, new Vector2(-150,-160), new Vector2(100,5), BodyDef.BodyType.StaticBody, -25);
		ObjectMaker.makeObj(world, new Vector2(150,-160), new Vector2(100,5), BodyDef.BodyType.StaticBody, 25);
		player = ObjectMaker.makeObj(world, new Vector2(0,-180), new Vector2(5,5), BodyDef.BodyType.DynamicBody, 0);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		mapRenderer.setView(camera);
		mapRenderer.render();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.end();

		debugRenderer.render(world, camera.combined);
		world.step(1/60f, 6, 2);

		camera.position.x = player.getPosition().x;
		camera.position.y = player.getPosition().y;
		//ObjectMaker.makeObj(world, new Vector2(0,0), new Vector2(5,5), BodyDef.BodyType.DynamicBody, 0);

		if (Gdx.input.isKeyPressed(Input.Keys.D)) player.applyForceToCenter(200, 0, true);
		if (Gdx.input.isKeyPressed(Input.Keys.A)) player.applyForceToCenter(-200, 0, true);
		if (Gdx.input.isKeyPressed(Input.Keys.W)) player.applyForceToCenter(0,500, true);
		if (Gdx.input.isKeyPressed(Input.Keys.S)) player.applyForceToCenter(0,-500, true);
		if (Gdx.input.isKeyPressed(Input.Keys.P)) camera.zoom -= .01f;
		if (Gdx.input.isKeyPressed(Input.Keys.O)) camera.zoom += .01f;
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
