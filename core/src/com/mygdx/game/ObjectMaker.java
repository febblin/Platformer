package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class ObjectMaker {
    private static final float K = 1;
    public static Body makeObj(World world, MapObject object, boolean isSensor){

        RectangleMapObject o = (RectangleMapObject) object;
        float dx = o.getRectangle().x+o.getRectangle().width/2;
        float dy = o.getRectangle().y+o.getRectangle().height/2;

        Vector2 tmpSize = new Vector2(o.getRectangle().width/2/K, o.getRectangle().height/2/K); // масштабировал размер
        Vector2 tmpPosition = new Vector2(dx/K, dy/K); // масштабировал положения

        BodyDef bodyDef = new BodyDef();
        bodyDef.active = true; //активно ли тело
        bodyDef.allowSleep = true; //разрешать телу спать
        bodyDef.bullet = false;
        bodyDef.gravityScale = (float)object.getProperties().get("gravityScale");  //масштаб гравитации
        bodyDef.position.set(tmpPosition); //позиция

        switch ((String)object.getProperties().get("bodyType")){
            case "StaticBody":
                bodyDef.type = BodyDef.BodyType.StaticBody;
                break;
            case "DynamicBody":
                bodyDef.type = BodyDef.BodyType.DynamicBody;
                break;
            default:
                bodyDef.type = BodyDef.BodyType.KinematicBody;
        }

        FixtureDef def = new FixtureDef();
        def.density = (float)object.getProperties().get("density"); //плотность
        def.friction = (float)object.getProperties().get("friction"); //шершавость
        def.restitution = (float)object.getProperties().get("restitution"); //отталкивание
        def.isSensor = (boolean)object.getProperties().get("isSensor"); //сенсор
        PolygonShape shape = new PolygonShape(); //полигон
        shape.setAsBox(tmpSize.x, tmpSize.y); //половины длинн сторон
        def.shape = shape;
        Body body = world.createBody(bodyDef); //создали физическое тело!
        body.createFixture(def);
        return body;
    }
}