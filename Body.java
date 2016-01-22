

/**
 * Created by Pontus on 2015-12-21.
 */
public class Body {
    private boolean head = false;
    private int direction = 0; //0 = still, 1 = left, 2 = right, 3 = up, 4 = down
    private int x, y;

    //Torso variables
    private Body connectedBody;

    public Body(boolean head, int direction, int x, int y) { //Head
        this.head = head;
        this.direction = direction;
        this.x = x;
        this.y = y;
    }

    public Body(Body connectedBody, int width, int height) { //Torso
        switch (connectedBody.getDirection()) {
            case 1:
                x = (connectedBody.getX() + 1) % width;
                y = connectedBody.getY();
                break;
            case 2:
                x = (connectedBody.getX() - 1);
                if(x < 0){
                    x += width;
                }
                y = connectedBody.getY();
                break;
            case 3:
                x = connectedBody.getX();
                y = (connectedBody.getY() + 1) % height;
                break;
            case 4:
                x = connectedBody.getX();
                y = connectedBody.getY() - 1;
                if(y < 0){
                    y += height;
                }
                break;
        }
        direction = connectedBody.getDirection();
        this.connectedBody = connectedBody;
    }

    public Body(int x, int y) { //Food
        this.x = x;
        this.y = y;
    }

    public void move(int width, int height) {

        switch (direction) {
            case 1: //Left
                x -= 1;
                if (x < 0) {
                    x += width;
                }
                break;
            case 2: //Right
                x = (x + 1) % width;
                break;
            case 3: //Up
                y -= 1;
                if (y < 0) {
                    y += height;
                }
                break;
            case 4: //Down
                y = (y + 1) % height;
                break;
        }

        //System.out.println("X: " + x + " Y: " + y);
    }

    public void setDirection(int direction) {
        if (head && ((direction < 3 && this.direction > 2) || (direction > 2 && this.direction < 3) ||
        this.direction == direction)) {
            this.direction = direction;
        }else if(connectedBody != null){
            this.direction = connectedBody.getDirection();
        }
    }

    public int getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
