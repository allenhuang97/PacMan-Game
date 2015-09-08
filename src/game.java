/**
 * @Description: game Class. used to manage all the classes relate to the game using object oriented programming
 * @author: Allen Huang
 * @version  v1.0
 * Date: June 14, 2015
 */

//import
import java.applet.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;

//class extends Applet
public class game extends Applet implements Runnable{
	//thread
	private Thread th;
	//boolean variables
	private boolean gameStart = true;
	private boolean playerMoveLeft;
	private boolean playerMoveRight;
	private boolean playerMoveUp;
	private boolean playerMoveDown;
	private boolean hasBoost = false;
	private boolean gameEnd = false;
	private boolean pause = false;
	private boolean displayGameOver = false;
	//Integer variables
	private int pacSpriteCount = 0;
	private int ghostSpriteCount = 0;
	private int ghostCounter = 0;
	private int score = 0;
	private int boostTimer = 0;
	private int hasBoostGhostMove = 2;
	private int countDown;
	private int cherryCollected = 0;
	private int lives = 3;
	private int ghostsKilled = 0;
	//Font variable
	private Font fontScore = new Font("SANS Serif",Font.BOLD,25);
	private Font fontCountDown = new Font("SANS Serif",Font.BOLD,50);
	//image/ graphic variables
	private Image dbImage;
	private Graphics dbg;
	//BufferedImage Variable
	private BufferedImage livesBImg;
	private BufferedImage cherryBImg;
	//object variables
	private player pac = new player(381, 428);
	private enemy[] ghost = new enemy[4];
	private points points = new points(-7, 39);

	private Clip audioClip;

	//init method, runs the beginning code in the program	
	public void init(){
		setBackground(Color.BLACK);
		//set the boundaries of the screen to 800 x 835
		this.setBounds(0,0, 800, 835);//800,794
		//create the first enemy ghost
		ghost[ghostCounter] = new enemy(380, 351, ghostCounter, 0);
		//play music
		try{
			URL url = this.getClass().getResource("gameSound/Pacman Music.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			audioClip = AudioSystem.getClip();
			audioClip.open(audioIn);
			audioClip.start();
			audioClip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		catch(IOException e){
		}
		catch(UnsupportedAudioFileException e){
		}
		catch(LineUnavailableException e){
		}
		//initialize BufferedImages for lives and cherries
		try {
			livesBImg = ImageIO.read(this.getClass().getResource("/pacManSprites/pacmanr2.png"));
			cherryBImg = ImageIO.read(this.getClass().getResource("/cherrySprite/cherry.png"));
		} 
		catch (IOException e) {
		}
		//gameEnd = true;
	}//end of init method

	//start method, to start thread
	public void start (){
		th = new Thread(this);
		th.start();	
	}//end of start

	//stop method, to stop thread
	public void stop(){
		th.stop();
	}//end of stop method

	//destroy method, to destroy thread
	public void destroy(){
		th.stop();
	}//end of destroy method

	//run method, constantly runs to refresh paint
	public void run(){
		//continue looping as long as game is not over
		while (true){
			//testing movement boolean variables and moves pacman accordingly
			if(playerMoveLeft)
				pac.moveLeft();
			if(playerMoveRight)
				pac.moveRight();
			if(playerMoveUp)
				pac.moveUp();
			if(playerMoveDown)
				pac.moveDown();
			//if a score reaches a certain points. then spawn a cherry
			if((score - ghostsKilled*50 - cherryCollected*100)%2110 == 0){
				if(score!=0){
					points.spawnCherry();
				}
			}
			//if a cherry and pacman exists then test if pacman has eaten the cherry
			if((points.getCherryRect()!=null)&&(pac!=null)){
				for(int i = 0; i < 4; i++){
					try{
						//if the cherry is eaten. then increase score and cherryCollected
						if(points.getCherryRect().intersects(pac.getRect(i))){
							points.cherryCollected();
							score+=100;
							cherryCollected++;
						}
					}
					catch(Exception e){
					}
				}	
			}
			//constantly refreshing "level" for ghosts later used for movement
			for (int ghosts = 0; ghosts < ghost.length; ghosts++)
				if(ghost[ghosts]!=null)
					ghost[ghosts].setLevel();
			//test if pacman intersects with any points. if he does. then collect the point and increase score.
			for(int point = 0; point < points.pointLength(); point++){
				for(int playerRect = 0; playerRect < 4; playerRect++){
					if(points.getPointRect(point)!=null){
						if(pac.getRect(playerRect).intersects(points.getPointRect(point))){
							score+=10;
							//if all points are collected then end the game.
							if(score - ghostsKilled*50 - cherryCollected*100 >= 8440){
								gameEnd = true;
							}
							points.pointCollected(point);
						}
					}
				}
			}
			//if pacman intersects with a boost. Collect the boost, set ghosts to frighten sprites, and set other variables accordingly.
			for(int pacBoost = 0; pacBoost < points.boostLength();pacBoost++){
				for(int playerRect = 0; playerRect < 4; playerRect++){
					if(points.getBoostRect(pacBoost)!=null){
						if(pac.getRect(playerRect).intersects(points.getBoostRect(pacBoost))){
							//set variables accordingly for boosts
							hasBoost = true;
							hasBoostGhostMove = 0;
							points.boostCollected(pacBoost);
							boostTimer = 0;
							//notify each ghost object that pacman has the boost
							for (int ghosts = 0; ghosts < ghost.length; ghosts++){
								if(ghost[ghosts]!= null)
									ghost[ghosts].pacBoostTrue();
							}
						}
					}
				}
			}
			//if pacman intersects with an enemy
			for(int pacBox = 0; pacBox < 4; pacBox++){
				if(pac!=null){
					for(int enemy = 0; enemy < ghost.length; enemy++){
						if(ghost[enemy]!=null){
							//if pacman doesnt have a ghost run hitGhost method.
							if(ghost[enemy].getRect().intersects(pac.getRect(pacBox))){
								if((hasBoost == false)&&(ghost[enemy].isDie()==false))
									hitGhost();
								//if pacman has a ghost run eatGhost method.
								else if((hasBoost==true)&&(ghost[enemy]).isDie()==false)
									eatGhost(enemy);
							}
						}
					}
				}
			}
			//if a pacMan has a boost and the boostTimer variable reaches 1000. Stop the boost and change variables accordingly
			if(boostTimer == 1000){
				hasBoost = false;
				hasBoostGhostMove = 2;
				boostTimer = 0;
				//notify each ghost object pacman does not have the boost anymore
				for (int ghosts = 0; ghosts < ghost.length; ghosts++){
					if(ghost[ghosts]!= null)
						ghost[ghosts].pacBoostFalse();
				}
			}
			//if ghosts alive is less than 4. then spawn more until there are 4 on the map
			if((ghostCounter+1) < ghost.length)
				if(ghost[ghostCounter]!=null)
					if(ghost[ghostCounter].isGhostOnMap() == true){
						ghostCounter++;
						ghost[ghostCounter] = new enemy(380, 351, ghostCounter, 0);
					}
			//monitor ghosts movements. depending on the direction of each ghost
			if(hasBoostGhostMove == 2){
				for(int i = 0; i < ghost.length; i++){
					if(ghost[i] != null){
						switch(ghost[i].getDirection()){
						case 0:
							ghost[i].moveUp();
							break;
						case 1:
							ghost[i].moveRight();
							break;
						case 2:
							ghost[i].moveDown();
							break;
						case 3:
							ghost[i].moveLeft();
							break;
						}
					}
				}
				//if pacman has the boost then set hasBoostGhostMove equal to 0
				if(hasBoost == true)
					hasBoostGhostMove = 0;
			}
			//control movement for ghosts that are scared
			else if((hasBoost == true)&&(hasBoostGhostMove!=2)){
				for(int i = 0; i < ghost.length; i++){
					if((ghost[i] != null)&&(ghost[i].isDie() == true)){
						switch(ghost[i].getDirection()){
						case 0:
							ghost[i].moveUp();
							break;
						case 1:
							ghost[i].moveRight();
							break;
						case 2:
							ghost[i].moveDown();
							break;
						case 3:
							ghost[i].moveLeft();
							break;
						}
					}
				}
			}
			//counter used to animate ghosts
			if(ghostSpriteCount == 30){
				for (int i = 0; i < ghost.length; i++){
					if(ghost[i]!=null)
						ghost[i].changeSprite();
				}
				ghostSpriteCount = 0;
			}
			//counter used to animate pacman
			if(pacSpriteCount == 30){
				pac.setSprite();
				pacSpriteCount = 0;
				points.boostColorChange();
			}
			//add to counter variables
			pacSpriteCount++;
			ghostSpriteCount++;
			if(hasBoost == true){
				boostTimer++;
				hasBoostGhostMove++;
			}
			//rePosition pacman. used for collision detection
			pac.rePosition();
			//repaint
			repaint();
			//pause thread for 7 ms
			try{
				Thread.sleep(7);
			}
			catch (InterruptedException ex){
			}
			if(gameStart == true){
				//if pacman has died and lives equal 0 then set displayGameOver to true. then end game
				if((lives == 0)&&(displayGameOver == false)){
					displayGameOver = true;
					try {
						repaint();
						Thread.sleep(1000);
						gameStart = false;
						gameEnd = true;
					} 
					catch (InterruptedException e) {
					}
				}
				//otherwise restart all positions start the countdown
				else{
					try{
						//if the gameStart equals true then set countDown values to print
						for(countDown = 3; countDown > 0; countDown--){
							//repaint every 1 second
							repaint();
							Thread.sleep(1000);
						}
						gameStart = false;
					}
					catch(InterruptedException ex){
					}
				}
			}
		}
	}//end of run method

	//isGameOver method. returns gameEnd variable
	public boolean isGameOver(){
		return gameEnd;
	}//end of isGameOver method

	//getPoints method. return the amount of points collected
	public int getPoints(){
		return (score - ghostsKilled*50 - cherryCollected*100);
	}//end of getPoints method

	//getGhostKilled method. returns ghostKilled Variable
	public int getGhostKilled(){
		return ghostsKilled;
	}//end of getGhostKilled method

	//end of getCherriesCollected
	public int getCherriesCollected(){
		return cherryCollected;
	}//end of getCherriesCollected method

	//getLives method. returns lives variable
	public int getLives(){
		return lives;
	}//end of getLives method

	//stopAudio method. used to stop the audio
	public void stopAudio(){
		audioClip.stop();
	}//end of stopAudio method

	//gameEnded used to set gameEnd equal to false
	public void gameEnded(){
		gameEnd = false;
	}//end of gameEnded method

	//hitGhost method. adjusts variables for when pacman hits a ghost
	private void hitGhost(){
		//subtract lives
		lives--;
		//reset position
		pac = new player(381, 428);
		//reset ghosts
		ghostCounter = 0;
		for(int ghostNum = 0; ghostNum < ghost.length; ghostNum++)
			ghost[ghostNum] = null;
		ghost[ghostCounter] = new enemy(380, 351, ghostCounter, 0);
		gameStart = true;
	}//end of hitGhosts method

	//eatGhosts method. adjusts variables when pacman eats a ghost
	private void eatGhost(int N){
		score+=50;
		ghostsKilled++;
		ghost[N].died();
	}//end of eatGhost method

	//when keys are pressed down. keyDown method
	public boolean keyDown(Event e, int key){
		//spacebar
		if(key==32){
			//end the game for testing purposes
			gameEnd = true;
		}

		//if the left key is pressed set playerMoveleft to true
		if(key == Event.LEFT){
			playerMoveLeft = true;
			playerMoveRight = false;
			playerMoveUp = false;
			playerMoveDown = false;
			//set direction accordingly
			pac.setDirection(3);
			repaint();
		}   
		//if the right key is pressed set playerMoveRight to true
		else if(key == Event.RIGHT){
			playerMoveRight = true;
			playerMoveLeft = false;
			playerMoveUp = false;
			playerMoveDown = false;
			//set direction accordingly
			pac.setDirection(1);
			repaint();
		}  
		//if the up key is pressed set playerMoveUp to true
		else if(key == Event.UP){
			playerMoveUp = true;
			playerMoveRight = false;
			playerMoveLeft = false;
			playerMoveDown = false;
			//set direction accordingly
			pac.setDirection(0);
			repaint();
		}
		//if the down key is pressed set playerMoveDown to true
		else if(key == Event.DOWN){
			playerMoveDown = true;
			playerMoveRight = false;
			playerMoveUp = false;
			playerMoveLeft = false;
			//set direction accordingly
			pac.setDirection(2);
			repaint();
		}
		//[p]
		else if(key == 112){
			//pause the game.
			if(countDown == 0){
				th.stop();
				pause = true;
			}
		} 
		return true;
	}//end of keyDown method

	//resumeGame method. resumes game
	public void resumeGame(){
		th = new Thread(this);
		th.start();
		pause = false;
	}//end of resumeGame method

	//isPaused method. returns pause method
	public boolean isPaused(){
		return pause;
	}//end of isPaused method
	
	//if a key not pressed. keyUp method
	public boolean keyUp(Event e, int key){
		//if the left key is pressed set playerMoveLeft to false
		if(key == Event.LEFT){
			playerMoveLeft = false;
		}
		//if the right key is pressed set playerMoveRight to false
		else if(key == Event.RIGHT){
			playerMoveRight = false;
		}
		//if the up key is pressed set playerMoveUp to false
		else if(key == Event.UP){
			playerMoveUp = false;
		}
		//if the down key is pressed set playerMoveDown to false
		else if(key == Event.DOWN){
			playerMoveDown = false;
		}
		return true;
	}//end of keyUp method

	//update method to update backGround and screen
	public void update (Graphics g){
		if (dbImage == null){
			dbImage = createImage (this.getSize().width, this.getSize().height);
			dbg = dbImage.getGraphics ();
		}

		dbg.setColor (getBackground ());
		dbg.fillRect (0, 0, this.getSize().width, this.getSize().height);

		dbg.setColor (getForeground());
		paint (dbg);

		g.drawImage (dbImage, 0, 0, this);
	}//end of update method

	//paint method, paints all the objects
	public void paint (Graphics g){
		//set screen to 800 x 835 size
		this.resize(800, 835);
		//paint pacman
		pac.paintPac(g);
		//paint points
		points.paintPoints(g);
		//draw gameover if displayGameOver variable is true
		if(displayGameOver == true){
			g.drawString("Game Over!", 360, 400);
		}
		//paint all enemies
		for(int i = 0; i < ghost.length; i++){
			if(ghost[i]!= null)
				ghost[i].paintEnemy(g);
		}
		//draw lives remaining
		for(int i = 0; i < lives; i++)
			g.drawImage(livesBImg, (760-40*i), 795, null);
		//draw cherries collected
		for(int i = 0; i < cherryCollected;i++){
			g.drawImage(cherryBImg, (560-40*i), 795, null);
		}
		//if pacman has boost. then draw how much time the boost has left
		if(hasBoost == true){
			g.drawString("Boost Time: " + (1000-boostTimer)/100, 200, 818);
		}
		//draw countdown when gameStart eqauls true
		if((gameStart == true)&&(displayGameOver == false)){
			g.setFont(fontCountDown);
			g.setColor(Color.WHITE);
			g.drawString(String.valueOf(countDown), 385, 400);
		}
		//draw score
		g.setFont(fontScore);
		g.drawString("Score: " + String.valueOf(score), 20, 818);
	}//end of paint method
}//end of class