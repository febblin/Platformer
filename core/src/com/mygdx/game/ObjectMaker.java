package com.mygdx.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class ObjectMaker {
    private static final float K = 10;
    public static Body makeObj(World world, Vector2 position, Vector2 size, BodyDef.BodyType bodyType, float angle){
        Vector2 tmpSize = new Vector2(size.x/K, size.y/K); // масштабировал размер
        Vector2 tmpPosition = new Vector2(position.x/K, position.y/K); // масштабировал положения

        BodyDef bodyDef = new BodyDef();
        bodyDef.active = true; //активно ли тело
        bodyDef.allowSleep = true; //разрешать телу спать
        bodyDef.bullet = false;
        bodyDef.gravityScale = 1.f;  //масштаб гравитации
        bodyDef.position.set(tmpPosition); //позиция
        bodyDef.type = bodyType;
        bodyDef.angle = MathUtils.degreesToRadians*angle;

        FixtureDef def = new FixtureDef();
        def.density = 0.f; //плотность
        def.friction = 1.0f; //шершавость
        def.restitution = 0.1f; //отталкивание
        PolygonShape shape = new PolygonShape(); //полигон
        shape.setAsBox(tmpSize.x, tmpSize.y); //половины длинн сторон
        def.shape = shape;
        Body body = world.createBody(bodyDef); //создали физическое тело!
        body.createFixture(def);
        return body;
    }

}
