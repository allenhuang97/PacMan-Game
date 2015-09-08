/**
 * @Description: enemy Class. manages enemies. mainly their movement
 * @author: Allen Huang
 * @version  v1.0
 * Date: June 13, 2015
 */

//import
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

//enemy class
public class enemy{
	//integer variables
	private int enemyX;
	private int enemyY;
	private int color;
	private int direction;
	private int spriteNum = 1;
	private int speed = 1;
	private int approachIntersect = 0;
	private int level = 4;
	//boolean variables
	private boolean ghostOnMap = false;
	private boolean[] nextDirection = new boolean[4];
	private boolean pacBoost = false;
	private boolean isDead = false;
	//BufferedImage variables
	private BufferedImage enemyImg;
	//Rectangle variables
	private Rectangle enemyRect = new Rectangle(enemyX-3, enemyY-3, 41, 41);
	private Rectangle spawnRect = new Rectangle(380, 386, 35, 1);
	//stage object variable
	private stage map = new stage();
	//Random class variable
	private Random rand = new Random();

	//enemy constructor. receives data for enemy. then sets to the corresponding variable
	enemy(int enemyXA, int enemyYA, int colorA, int directionA){
		enemyX = enemyXA;
		enemyY = enemyYA;
		color = colorA;
		direction = directionA;
		//set Sprite of the ghosts
		setSprite();
		//clear variables to prepare for choosing next direction
		resetNextDirection();
	}//end of enemy constructor

	//getRect method. returns enemyRect rectangle for collision detection purposes
	public Rectangle getRect(){
		return enemyRect;
	}//end of getRect method

	//setSprite method. used to set the sprites for the ghost depending on what is happening
	public void setSprite(){
		try{
			//regular sprite or ghosts
			if((pacBoost == false)&&(isDead == false))
				enemyImg = ImageIO.read(this.getClass().getResource("/enemySprites/ghostc" + color + "d" + direction + "s" + spriteNum + ".png"));
			//if pacman has the boost.
			else if((pacBoost == true)&&(isDead == false))
				enemyImg = ImageIO.read(this.getClass().getResource("/enemySprites/ghostscare" + spriteNum + ".png"));
			//if the ghost is dead/eaten
			else if(isDead == true){
				enemyImg = ImageIO.read(this.getClass().getResource("/enemySprites/ghostDied" + direction + ".png"));
			}
		}
		catch (IOException e){
		}
	}//end of setSprite method

	//setLevel method. checks were the ghost is and sets the "level" for it
	public void setLevel(){
		if((enemyY > 0)&&(enemyY < 66))
			level = 1;
		else if((enemyY > 122)&&(enemyY < 168))
			level = 2;
		else if((enemyY > 195)&&(enemyY < 243))
			level = 3;
		else if((enemyY > 273)&&(enemyY < 321))
			level = 4;
		else if((enemyY > 348)&&(enemyY < 394))
			level = 5;
		else if((enemyY > 425)&&(enemyY < 471))
			level = 6;
		else if((enemyY > 501)&&(enemyY < 547))
			level = 7;
		else if((enemyY > 577)&&(enemyY < 625))
			level = 8;
		else if((enemyY > 652)&&(enemyY < 698))
			level = 9;
		else if((enemyY > 728)&&(enemyY < 776))
			level = 10;
	}//end of setLevel method

	//changeSprite method. counts spriteNum for changing sprites
	public void changeSprite(){
		spriteNum++;
		if(spriteNum == 3)
			spriteNum = 1;
		setSprite();
	}//end of changeSprite method

	//resetNextDirection method. resets nextDirection for choosing of next direction
	public void resetNextDirection(){
		for(int i = 0; i < nextDirection.length; i++)
			nextDirection[i] = false;
	}//end of resetNextDirection method

	//opposteDirection method. returns the opposite direction of ghosts current direction
	private int oppositeDirection(){
		int tempDirection = direction+2;
		if(tempDirection > 3)
			tempDirection-=4;
		return tempDirection;
	}//end of oppositeDirection method

	//pacBoostTrue method. sets pacBoost to true when pacman has the boost
	public void pacBoostTrue(){
		pacBoost = true;
	}//end of pacBoostTrue method

	//pacBoostFalse method. sets pacBoost to false when pacman doesn't have the boost
	public void pacBoostFalse(){
		pacBoost = false;
	}//end of pacBoostFalse method

	//died method. is called when the ghost is eaten. sets isDead to true
	public void died(){
		isDead = true;
	}//end of died method
	
	//isDie method. returns if the ghost is dead
	public boolean isDie(){
		return isDead;
	}//end of isDie method
	
	//testNextDirection method. used to test which direction is available for the ghost to move next
	private void testNextDirection(){
		//depending on which rectangle it intersects with at the intersection
		for(int N = 0; N < map.getLengthRectL();N++){
			if(enemyRect.intersects(map.getIntersectRectR(N))){
				nextDirection[1] = true;
			}
			if(enemyRect.intersects(map.getIntersectRectL(N))){
				nextDirection[3] = true;
			}
		}
		for(int N = 0; N < map.getLengthRectD();N++){
			if(enemyRect.intersects(map.getIntersectRectU(N))){
				nextDirection[0] = true;
			}
			if(enemyRect.intersects(map.getIntersectRectD(N))){
				nextDirection[2] = true;
			}
		}
		//if the ghost is alive it cannot go in the opposite direction when at an intersection
		if(isDead==false)
			nextDirection[oppositeDirection()] = false;
	}//end of testNextDirection method7
	
	//isDeadNextDirection method. tests for which direction to move when it is dead to return it to the spawn box
	private int isDeadNextDirection(){
		//if the ghost is below the spawn box
		if(level > 5){
			//move up if possible
			if(nextDirection[0] == true){
				return 0;
			}
			//if move up is not possible and both left and right are available then move the direction that will bring it closer to the middle/spawn box X-coordinate
			else if(nextDirection[0] == false){
				if((nextDirection[1]==true)&&(nextDirection[3]==true)){
					if(enemyX < 386)
						return 1;
					else if(enemyX > 386)
						return 3;
				}
				//if there is only one choice then move that way
				else if((nextDirection[1]==true)&&(nextDirection[3]==false))
					return 1;
				else if((nextDirection[1]==false)&&(nextDirection[3]==true))
					return 3;
				else if((nextDirection[1]==false)&&(nextDirection[3]==false))
					return 2;
			}
		}
		//if the ghost is above the spawn box
		else if(level < 4){
			//do not allow opposite direction movement on level 2. for special case on map.
			if(level == 2)
				nextDirection[oppositeDirection()] = false;
			//if move down is possible the move down
			if(nextDirection[2] == true){
				return 2;
			}
			//if move down is not possible and both left and right are available then move the direction that will bring it closer to the middle/spawn box X-coordinate
			else if(nextDirection[2] == false){
				if((nextDirection[1]==true)&&(nextDirection[3]==true)){
					if(enemyX < 386)
						return 1;
					else if(enemyX > 386)
						return 3;
				}
				//if there is only one choice then move that way
				else if((nextDirection[1]==true)&&(nextDirection[3]==false))
					return 1;
				else if((nextDirection[1]==false)&&(nextDirection[3]==true))
					return 3;
				else if((nextDirection[1]==false)&&(nextDirection[3]==false))
					return 0;
			}
		}
		//if ghost is specifically on level 5.
		else if(level == 5){
			//if the ghost is on the right of the spawn box and it can move left then move left
			if(enemyX > 386){
				if(nextDirection[3]==true){
					return 3;
				}
				//if it can't move left then move up.
				else if(nextDirection[3]==false){
					return 0;
				}
			}
			//if the ghost is on the left of the spawn box and it can move right then move right
			else if(enemyX < 386){
				if(nextDirection[1]==true){
					return 1;
				}
				//if it can't move right then move up.
				else if(nextDirection[1]==false){
					return 0;
				}	
			}
		}
		//if the ghost is on level 4
		else if(level == 4){
			//move towards the center of the spawn box's X-coordinate
			if(enemyX < 386)
				return 1;
			else if(enemyX > 386)
				return 3;
		}
		return 10;
	}//end of isDeadNextDirection method

	//chooseNextDirection method. Tests the amount of possible directions and randomizes which direction to go next
	private int chooseNextDirection(){
		int directionsClear = 0;
		//counting how many directions are possible
		for(int i = 0; i < nextDirection.length; i++){
			if(nextDirection[i] == true)
				directionsClear++;
		}
		//randomizing which direction
		int tempNextDirection = rand.nextInt((directionsClear - 0) + 0);
		//set next direction to the randomized direction
		for(int i = 0; i < nextDirection.length; i++){
			if(nextDirection[i] == true){
				if(tempNextDirection == 0)
					return i;

				else
					tempNextDirection--;
			}
		}
		return 10;
	}//end of chooseNextDirection

	//isGhostOnMap method. returns whether or not the ghost is outside of the spawn box
	public boolean isGhostOnMap(){
		return ghostOnMap;
	}//end of isGhostOnMap method

	//getLevel method. returns the ghosts level (Y-coordinate range)
	public int getLevel(){
		return level;
	}//end of getLevel method
	
	//moveUp method. moves the ghost up.
	public void moveUp(){
		enemyY -= speed;
		//if the ghost is still in the spawn box. then when it leaves. randomize direction and set ghostOnMap to true
		if(ghostOnMap == false){
			if(enemyRect.intersects(map.getGhostExtRect())){
				ghostOnMap = true;
				switch(rand.nextInt(3-1) + 1){
				case 1:
					direction = 3;
					break;
				case 2:
					direction = 1;
					break;
				}
			}
		}
		//if the ghost intersects with rectangle signaling intersection.
		for(int i = 0; i < map.getLengthRectD();i++){
			if(enemyRect.intersects(map.getIntersectRectD(i))){
				//approach the intersection
				approachIntersect++;
				//when the intersection is reached. test which directions are available then randomize the next direction
				if(((enemyY+33) == map.getIntersectRectD(i).y)&&(approachIntersect >25)){
					testNextDirection();
					if(isDead== false)
						direction = chooseNextDirection();
					else if(isDead == true)
						direction = isDeadNextDirection();
					approachIntersect = 0;
					resetNextDirection();
				}
				i = map.getLengthRectD();
			}
		}
	}//end of moveUp method

	//moveDown method. moves the ghost down.
	public void moveDown(){
		enemyY += speed;
		//if the ghost reaches the spawn box and is dead then respawn it.
		if((enemyRect.intersects(spawnRect)&&(isDead == true))){
			isDead = false;
			direction = 0;
		}
		//if the ghost intersects with rectangle signaling intersection.
		for(int i = 0; i < map.getLengthRectU();i++){
			if(enemyRect.intersects(map.getIntersectRectU(i))){
				//approach the intersection
				approachIntersect++;
				//when the intersection is reached. test which directions are available then randomize the next direction
				if(((enemyY-2) == map.getIntersectRectU(i).y)&&(approachIntersect >25)){
					testNextDirection();
					if(isDead== false)
						direction = chooseNextDirection();
					else if(isDead == true)
						direction = isDeadNextDirection();
					approachIntersect = 0;
					resetNextDirection();
				}
				i = map.getLengthRectU();
			}
		}
	}//end of moveDown method

	public void moveLeft(){
		enemyX -= speed;
		//if the ghost is dead and it reaches the door to the spawn box. set direction to down to enter the spawn box
		if(isDead == true){
			if((enemyX == 377)&&(enemyRect.intersects(map.getGhostExtRect()))){
				ghostOnMap = false;
				direction = 2;
			}
		}
		//if the ghost intersects with rectangle signaling intersection.
		for(int i = 0; i < map.getLengthRectR();i++){
			if(enemyRect.intersects(map.getIntersectRectR(i))){
				//approach the intersection
				approachIntersect++;
				//when the intersection is reached. test which directions are available then randomize the next direction
				if(((enemyX+33) == map.getIntersectRectR(i).x)&&(approachIntersect >25)){
					testNextDirection();
					if(isDead== false)
						direction = chooseNextDirection();
					else if(isDead == true)
						direction = isDeadNextDirection();
					approachIntersect = 0;
					resetNextDirection();
				}
				i = map.getLengthRectR();
			}
		}
		if(enemyRect.intersects(map.getTeleGhostRect(0)))
			enemyX = 800;
	}//end of moveLeft method

	public void moveRight(){
		enemyX += speed;
		//if the ghost is dead and it reaches the door to the spawn box. set direction to down to enter the spawn box
		if(isDead == true){
			if((enemyX == 377)&&(enemyRect.intersects(map.getGhostExtRect()))){
				ghostOnMap = false;
				direction = 2;
			}
		}
		//if the ghost intersects with rectangle signaling intersection.
		for(int i = 0; i < map.getLengthRectL();i++){
			if(enemyRect.intersects(map.getIntersectRectL(i))){
				//approach the intersection
				approachIntersect++;
				//when the intersection is reached. test which directions are available then randomize the next direction
				if(((enemyX-2) == map.getIntersectRectL(i).x)&&(approachIntersect >25)){
					testNextDirection();
					if(isDead== false)
						direction = chooseNextDirection();
					else if(isDead == true)
						direction = isDeadNextDirection();
					approachIntersect = 0;
					resetNextDirection();
				}
				i = map.getLengthRectL();
			}
		}
		if(enemyRect.intersects(map.getTeleGhostRect(1)))
			enemyX = -35;
	}//end of moveRight method

	//getDirection method. returns the ghosts direction
	public int getDirection(){
		return direction;
	}//end of getDirection method
	
	//paintEnemy method. paints the ghost and sets new bounds to the enemyRect Rectangle
	public void paintEnemy(Graphics g){
		g.drawImage(enemyImg,enemyX, enemyY, null);
		enemyRect.setBounds(enemyX-3, enemyY-3, 41, 41);
	}//end of paintEnemy method
}//end of class
