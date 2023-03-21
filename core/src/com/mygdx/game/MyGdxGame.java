package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	OrthographicCamera camera;
	float rotate;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();

		if (Gdx.input.isKeyPressed(Input.Keys.D)) camera.position.x++;
		if (Gdx.input.isKeyPressed(Input.Keys.A)) camera.position.x--;
		if (Gdx.input.isKeyPressed(Input.Keys.W)) camera.position.y++;
		if (Gdx.input.isKeyPressed(Input.Keys.S)) camera.position.y--;
		if (Gdx.input.isKeyPressed(Input.Keys.P)) camera.zoom -= .01f;
		if (Gdx.input.isKeyPressed(Input.Keys.O)) camera.zoom += .01f;
		if (Gdx.input.isKeyPressed(Input.Keys.K)) rotate = -.5f;
		if (Gdx.input.isKeyPressed(Input.Keys.L)) rotate = .5f;


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
