package com.group9.partysnake.gameElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Snake {

    private Vector2 position;
    private int snakeX = 0, snakeY = 0;

    private Vector2 formerPosition = new Vector2(0,0);

    private int SNAKE_MOVEMENT = 32;
    private Texture snakeHead = new Texture("snakehead.png");
    private Array<BodyPart> bodyParts = new Array<BodyPart>();

    // For handling the direction changes
    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    private int snakeDirection = RIGHT;

    public class BodyPart {

        private Vector2 bodyPosition;
        private int x, y;

        private Texture texture = new Texture("snakebody.png");

        public BodyPart() {
        }

        public void updateBodyPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void updateBodyPosition1(Vector2 pos) {
            this.bodyPosition = pos;
        }

        public void draw(Batch batch) {
            if (!(x == (int) position.x && y == (int) position.y)) batch.draw(texture, x, y);
        }


        public void draw1(Batch batch) {
            if (!(bodyPosition.x == position.x && bodyPosition.y == position.y)) {
                batch.draw(texture, bodyPosition.x, bodyPosition.y);
            }
        }

    }

    public void increaseLength(){
        BodyPart bodyPart = new BodyPart();
        bodyPart.updateBodyPosition((int) position.x,(int) position.y);
        bodyParts.insert(0,bodyPart);
//        System.out.println("increaseLength");
//        System.out.println(bodyParts.size);


    }


    public void checkSnakeEat(SuperEatable eatable){
        if (eatable.isAvailable && position.equals(eatable.position)) {
            increaseLength();
//            System.out.println("You just got eaten");
//            System.out.println(position);
//            System.out.println(bodyParts.get(0).bodyPosition);
//
//            System.out.println(position.equals(bodyParts.get(0).bodyPosition));
            eatable.isAvailable = false;
        }
    }



    public Vector2 getPosition() {
        return position;
    }

    private void checkForOutBounds(){
        if (position.x >= Gdx.graphics.getWidth()){
            position.x = 0;
        }
        if (position.x < 0){
            position.x = Gdx.graphics.getWidth() - SNAKE_MOVEMENT;
        }
        if (position.y >= Gdx.graphics.getHeight()){
            position.y = 0;
        }
        if (position.y < 0){
            position.y = Gdx.graphics.getHeight() - SNAKE_MOVEMENT;
        }
    }


    public void setSnakeDirection(int direction){
        snakeDirection = direction;
    }


    public void moveSnake(){
        formerPosition.x = position.x;
        formerPosition.y = position.y;

        switch(snakeDirection){
            case RIGHT: {
                position.x += SNAKE_MOVEMENT;
                return;
            }
            case LEFT: {
                position.x -= SNAKE_MOVEMENT;
                return;
            }
            case UP: {
                position.y += SNAKE_MOVEMENT;
                return;
            }
            case DOWN: {
                position.y -= SNAKE_MOVEMENT;
                return;
            }
        }



    }

    public void updateBodyPartsPosition() {
        if (bodyParts.size > 0) {
            BodyPart bodyPart = bodyParts.removeIndex(0);
//            System.out.println("-----------------------");
//            System.out.println("former pos" +bodyPart.bodyPosition);
            bodyPart.updateBodyPosition((int) formerPosition.x, (int) formerPosition.y);
//            System.out.println("Currentpos" +bodyPart.bodyPosition);
//            System.out.println("-----------------------");

            bodyParts.add(bodyPart);
        }
    }

    public void draw(Batch batch){
        batch.draw(snakeHead, position.x, position.y);
        for (BodyPart bodyPart : bodyParts) {
            bodyPart.draw(batch);
        }

    }

    public void updateSnake(){
        moveSnake();
        checkForOutBounds();
        updateBodyPartsPosition();

    }


    public Snake(){
        System.out.print("it's alive!!");
        this.position = new Vector2(0,0);
        this.snakeX = 0;
        this.snakeY = 0;
    }
}
