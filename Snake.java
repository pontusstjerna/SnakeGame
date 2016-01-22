

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Pontus on 2015-12-21.
 */
public class Snake {
    private ArrayList<Body> bodies = new ArrayList<>();
    private Random rand = new Random();
    private final int WIDTH = 20;
    private final int HEIGHT = 20;
    private final int start = 3;
    private int speed;
    private double scale;
    private ArrayList<Integer> snakeTurn = new ArrayList<>();
    private boolean gameOver = false;

    public Snake(int width, int height, int speed){
        scale = Math.min(width, height)/Math.min(WIDTH, HEIGHT);
        this.speed = speed;

        int x = rand.nextInt(WIDTH);
        int y = rand.nextInt(HEIGHT);

        bodies.add(new Body(true, rand.nextInt(3) + 1, x, y));
        for(int i = 0; i < start; i++){
            bodies.add(new Body(bodies.get(i), WIDTH, HEIGHT));
        }

        //Add food
        bodies.add(new Body(rand.nextInt(WIDTH), rand.nextInt(HEIGHT)));
    }

    public void update(){
        for(int i = 0; i < bodies.size(); i++){
            bodies.get(i).move(WIDTH, HEIGHT); //Move all of the snake parts
        }

        int removeTurn = 0;

        for(int j = 0; j < snakeTurn.size(); j++){ //Loop through all turns in the snake

            //The index of snakeTurn is which turn and the value is which body is turning!
            if(snakeTurn.get(j) < bodies.size()){
                //Setting direction to 0 means setting direction to the connected body
                bodies.get(snakeTurn.get(j)).setDirection(0);
                //Go to the next body part
                snakeTurn.set(j, snakeTurn.get(j) + 1);
            }else{
                removeTurn = j;
            }
        }
        if(removeTurn != 0){
            snakeTurn.remove(removeTurn);
        }

        checkCollision();
    }

    public Body getBody(int i){
        if(i < bodies.size()){
            return bodies.get(i);
        }else{
            return null;
        }
    }

    public void setDirection(int direction){
        if(direction != getDirection()){
            bodies.get(0).setDirection(direction);
            snakeTurn.add(1);
        }
    }

    private void checkCollision(){
        for(int i = 1; i < bodies.size(); i++){
            if(bodies.get(0).getX() == bodies.get(i).getX() &&
                    bodies.get(0).getY() == bodies.get(i).getY()){
                if(bodies.get(i).getDirection() == 0){ //Eat
                    bodies.remove(i);
                    bodies.add(new Body(bodies.get(bodies.size() - 1), WIDTH, HEIGHT));

                    //Spawn new food but not in tail of snake
                    int x = rand.nextInt(WIDTH);
                    int y = rand.nextInt(HEIGHT);
                    for(int j = 0; j < bodies.size(); j++){
                        while(x == bodies.get(j).getX() && y == bodies.get(j).getY()){
                            x = rand.nextInt(WIDTH);
                            y = rand.nextInt(HEIGHT);
                        }
                    }
                    bodies.add(new Body(x, y));
                }else{ //Die
                    gameOver = true;
                }
            }
        }
    }

    public int nBodies(){return bodies.size();}

    public int getDelay(){
        return 500/speed;
    }

    public int getWidth(){
        return (int)scale;
    }

    public int getHeight(){
        return (int)scale;
    }

    public int getX(int i){
        return (int)(bodies.get(i).getX()*scale);
    }

    public int getY(int i){
        return (int)(bodies.get(i).getY()*scale);
    }

    public int getDirection(){
        return bodies.get(0).getDirection();
    }

    public boolean checkGameOver(){return gameOver;}

    public int getScore(){
        //The score is the number of bodies minus the head, the food and the initial torso parts
        return nBodies() - (start + 2);
    }

    public int getSidePx(){return (int)((Math.min(WIDTH, HEIGHT))*scale);}

    public int getSpeed(){return speed;}


}
