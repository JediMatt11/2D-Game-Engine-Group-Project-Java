package gameframework.gamecharacters;


import gameframework.gameobjects.GameObject;

import java.awt.*;

public class HitBox
{
    /*private int x;
    private int y;
    private int width;
    private int height;*/
    private int range;
    private Rectangle hb;



    public HitBox(int x, int y, int width, int height)
    {


        setHb(new Rectangle(x,y,width,height));
    }


    //used to check if current hitbox overlaps another
    public boolean intersects(GameObject object){
        return hb.intersects(object.getBounds());
    }

    public void update(int x, int y, int width, int height){
        hb.x = x;
        hb.y = y;
        hb.width = width;
        hb.height = height;
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.BLUE);

    }



    //methods
    //initialize hit box based on character size
    //detect hurtbox

//make sure that character's box is not made outside of map


    //TODO
    //handle collision in other class
    //check if any rectangle intersects with hitbox rectangle and if its a hurtbox


    /*public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
*/
    public Rectangle getHb() {
        return hb;
    }

    public void setHb(Rectangle rectangle) {
        this.hb = rectangle;
    }
}
