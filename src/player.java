/**
 * @Description: player Class. manages the player object. Using OOP
 * @author: Allen Huang
 * @version  v1.0
 * Date: June 13, 2015
 */

//import
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

//player class
public class player {
	//Buffered Image variable
	private BufferedImage playerImg;
	//integer variables
	private int playerX;
	private int playerY;
	private int speed = 1;
	private int directionInt = 1;
	private int spriteNum = 1;
	//object stage. call constructor
	private stage map = new stage();
	//String variable
	private String directionStr = "R";
	//Rectangle Variable
	private Rectangle [] pacRect = new Rectangle[4];
	private Rectangle pacRectInit;

	//player constructor. receives player X and Y then creates the player
	player(int playerXA, int playerYA){
		playerX = playerXA;
		playerY = playerYA;
		//create new rectangles for pacman. used for collision detection
		pacRect[0] = new Rectangle(playerX+1, playerY, 32, 1);
		pacRect[1] = new Rectangle(playerX+35, playerY+1, 1, 32);
		pacRect[2] = new Rectangle(playerX+1, playerY+35, 32, 1);
		pacRect[3] = new Rectangle(playerX-1, playerY+1, 1, 32);
	}//end of constructor

	//setdirection of directionStr according to the player's direction
	public void setDirection(int directionA){
		directionInt = directionA;
		switch(directionInt){
		//up
		case 0:
			directionStr = "U";
			break;
			//right
		case 1:
			directionStr = "R";
			break;
			//down
		case 2:
			directionStr = "D";
			break;
			//left
		case 3:
			directionStr = "L";
			break;
		}	
	}//end of setDirection method

	//setSprite method. used to animate pacman depending on his direction and spriteNum
	public void setSprite(){
		try {
			playerImg = ImageIO.read(this.getClass().getResource("/pacManSprites/pacman" + directionStr + spriteNum + ".png"));
		} 
		catch (IOException e) {
		}
		//spriteNum continuously counts to change sprites
		spriteNum++;
		if(spriteNum == 3){
			spriteNum = 1;
		}
	}//end of setSprite method

	//getRect method. returns the selected rectangle around pacman. for collision detection
	public Rectangle getRect(int N){
		//return pacRect rectangle
		return pacRect[N];
	}//end of getRect method

	//getRectInit method. get the rectangle where pacman spawns on the map. used for point generation
	public Rectangle getRectInit(){
		//return pacRectInit rectangle
		return pacRectInit;
	}//end of getRectInit method

	//wallCollision method. returns which direction pacman intersects with a wall.
	private int wallCollision(int N){
		//test which rectangle around pacman intersects with the rectangle on stage.
		for(int i = 0; i < pacRect.length;i++){
			if(pacRect[i].intersects(map.getStageRect(N))){
				switch(i){
				case 0:
					return 1;
				case 1:
					return 2;
				case 2:
					return 3;
				case 3:
					return 4;
				}
			}
		}
		if(N == 0)
			return 0;
		//loop through to test all walls using recursion
		return wallCollision(N-1);
	}//end of wallCollision method

	//rePosition method. used to reposition pacman away from the wall if it collides with the wall
	public void rePosition(){
		int temp = 1;
		//test with direction pac is colliding with the wall
		temp = wallCollision(41);
		//depending on which direction. move pacman in the according direction
		if(temp == 1)
			playerY += speed;
		else if(temp == 2)
			playerX -= speed;
		else if(temp == 3)
			playerY -= speed;
		else if(temp == 4)
			playerX += speed;
	}//end of rePosition method

	//moveLeft method. moves player left
	public void moveLeft(){
		playerX -= speed;
		//if the player intersects with the teleporting rectangle then move pacman to the teleported area
		if(pacRect[3].intersects(map.getTeleRect(0)))
			playerX = 800;
	}//end of moveLeft method

	//moveRight method. moves player right
	public void moveRight(){
		playerX += speed;
		if(pacRect[1].intersects(map.getTeleRect(1)))
			playerX = -40;
	}

	//moveUp method. moves player up
	public void moveUp(){
		playerY -= speed;
	}//end of moveUp method

	//moveDown method. moves player down
	public void moveDown(){
		playerY += speed;
	}//end of moveDown method

	//paintPac method. paints pacman and set the new locations of pacRect rectangles for accurate collision detection
	public void paintPac(Graphics g){
		//draw player
		g.drawImage(playerImg, playerX, playerY, null);
		//set rectangle bounds to new location
		pacRect[0].setBounds(playerX+1, playerY, 32, 1);
		pacRect[1].setBounds(playerX+35, playerY+1, 1, 32);
		pacRect[2].setBounds(playerX+1, playerY+35, 32, 1);
		pacRect[3].setBounds(playerX-1, playerY+1, 1, 32);
		//call paintStage method in map class. to paint the rectangles on the map for collision detection
		map.paintStage(g);
	}//end of paintPac method
}//end of class
