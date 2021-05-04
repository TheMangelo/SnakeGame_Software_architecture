package com.group9.partysnake.gameElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private int snakeX, snakeY;
    private boolean hasHit = false;

    private Vector2 formerPosition = new Vector2(0,0);

    private int SNAKE_MOVEMENT = 32;
    private Texture snakeHead;
    private Texture super_snakeBody;

    private Array<BodyPart> bodyParts = new Array<>();

    private List<List<Integer>> allPositions =new ArrayList<>();

    private Rectangle rectangle;

    // For handling the direction changes
    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    //Initial movement
    private int snakeDirection = RIGHT;

    //For stoppping doublebacks
    private boolean directionSet = false;

    public class BodyPart {

        private int x, y;
        protected int positionIndex;

        //Uses the length of allPositions to determine its index position
        public BodyPart() {
            positionIndex = allPositions.size();
        }

        public int getPositionIndex(){
            return this.positionIndex;
        }


        public void updateBodyPosition(int x, int y) {
            this.x = x;
            this.y = y;

            //Have to update the list of position related to bodies.
            allPositions.get(getPositionIndex() - 1).set(0,x);
            allPositions.get(getPositionIndex() - 1).set(1,y);


        }

        public void draw(Batch batch) {
            if (!(x == snakeX && y == snakeY)) batch.draw(super_snakeBody, x, y);
        }

        public void dispose(){
            super_snakeBody.dispose();
        }
    }

    public Snake(String headSkinPath, String bodySkinPath) {
        snakeX = 0;
        snakeY = 0;

        snakeHead = new Texture(headSkinPath);
        super_snakeBody = new Texture(bodySkinPath);

        rectangle = new Rectangle(0,0, snakeHead.getWidth(),snakeHead.getHeight());
        //This head will be its first position sent to the socket
        List<Integer> headPosition = new ArrayList<>();
        headPosition.add(snakeX);
        headPosition.add(snakeY);
        allPositions.add(headPosition);
    }

    public Snake(Texture snakeHead, Texture snakeBody, int positionX, int positionY){
        this.snakeHead = snakeHead;
        super_snakeBody = snakeBody;
        snakeX = positionX;
        snakeY = positionY;
        rectangle = new Rectangle(
                positionX, positionY, snakeHead.getWidth(), snakeHead.getHeight()
        );
    }

    public boolean isHasHit() {
        return hasHit;
    }

    public void updateAllPosition( List<List<Integer>> sentPosition){
        this.allPositions = sentPosition;
    }


    public void increaseLength() {
        BodyPart bodyPart = new BodyPart();
        bodyPart.updateBodyPosition(snakeX, snakeY);
        bodyParts.insert(0,bodyPart);
    }


    public void checkSnakeEat(SuperEatable eatable) {
        if (eatable.isAvailable &&
                eatable.position.x == snakeX && eatable.position.y == snakeY)
        {
            increaseLength();
            eatable.isAvailable = false;
        }
    }

    public void checkSnakeEat(Apple apple){
        if (apple.isAvailable && rectangle.overlaps(apple.getRectangle()))
        {
            increaseLength();
            apple.isAvailable = false;
        }
    }


    private void checkForOutBounds(){
        if (snakeX >= Gdx.graphics.getWidth()){
           snakeX = 0;
        }
        if (snakeX < 0){
           snakeX = Gdx.graphics.getWidth() - SNAKE_MOVEMENT;
        }
        if (snakeY >= Gdx.graphics.getHeight()){
           snakeY= 0;
        }
        if (snakeY < 0){
           snakeY= Gdx.graphics.getHeight() - SNAKE_MOVEMENT;
        }
    }

    private void checkSnakeBodyCollision() {
        for (BodyPart bodyPart : bodyParts) {
            if (bodyPart.x == snakeX && bodyPart.y == snakeY) {
                hasHit = true;
                break;
            }
        }
    }


    public void setSnakeDirection(int direction){
        //snakeDirection = direction;
        updateDirection(direction);
    }

    private void updateIfNotOppositeDirection(int newSnakeDirection, int
            oppositeDirection) {
        if (snakeDirection != oppositeDirection || bodyParts.size == 0) snakeDirection =
                newSnakeDirection;
    }

    private void updateDirection(int newSnakeDirection) {
        if (!directionSet && snakeDirection != newSnakeDirection) {
            directionSet = true;
            switch (newSnakeDirection) {
                case LEFT: {
                    updateIfNotOppositeDirection(newSnakeDirection, RIGHT);
                }
                break;
                case RIGHT: {
                    updateIfNotOppositeDirection(newSnakeDirection, LEFT);
                }
                break;
                case UP: {
                    updateIfNotOppositeDirection(newSnakeDirection, DOWN);
                }
                break;
                case DOWN: {
                    updateIfNotOppositeDirection(newSnakeDirection, UP);
                }
                break;
            }
        }
    }


    public void moveSnake(){
        formerPosition.x =snakeX;
        formerPosition.y =snakeY;

        updateRectanglePosition();

        switch(snakeDirection){
            case RIGHT: {
               snakeX += SNAKE_MOVEMENT;
                break;
            }
            case LEFT: {
               snakeX -= SNAKE_MOVEMENT;
                break;
            }
            case UP: {
               snakeY+= SNAKE_MOVEMENT;
                break;
            }
            case DOWN: {
               snakeY-= SNAKE_MOVEMENT;
                break;
            }
        }
    }

    public void updateBodyPartsPosition() {
        if (bodyParts.size > 0) {
            BodyPart bodyPart = bodyParts.removeIndex(0);
            bodyPart.updateBodyPosition((int) formerPosition.x, (int) formerPosition.y);

            bodyParts.add(bodyPart);
        }
    }

    public void draw(Batch batch){
        batch.draw(snakeHead,snakeX,snakeY);
        for (BodyPart bodyPart : bodyParts) {
            bodyPart.draw(batch);
        }
    }

    public void updateSnake(){
        if (!hasHit){
            moveSnake();
            updateHeadPosition();
            checkForOutBounds();
            updateBodyPartsPosition();
            checkSnakeBodyCollision();
            directionSet = false;
        }
    }

    private void updateRectanglePosition(){
        rectangle.setX(snakeX);
        rectangle.setY(snakeY);
    }

    public void updateHeadPosition(){
        int headPosition = 0;
        this.allPositions.get(0).set(headPosition,getSnakeX());
        this.allPositions.get(0).set(headPosition,getSnakeY());
    }

    public int getSnakeX() {
        return snakeX;
    }

    public int getSnakeY() {
        return snakeY;
    }

    public JSONArray getAllPositions() {
        JSONArray listOfLists = new JSONArray();

        for (int i = 0; i < allPositions.size(); i++) {
            List<Integer> subPos = allPositions.get(i);
            JSONArray temp = new JSONArray();

            temp.put(subPos.get(0));
            temp.put(subPos.get(1));

            listOfLists.put(temp);
        }

        return listOfLists;
    }

    public void dispose(){
        snakeHead.dispose();
        super_snakeBody.dispose();
        for (BodyPart bodyPart : bodyParts) {
            bodyPart.dispose();
        }


    }
}

