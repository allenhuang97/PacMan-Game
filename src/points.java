/**
 * @Description: points Class. spawn points and control points when collected
 * @author: Allen Huang
 * @version  v1.0
 * Date: June 13, 2015
 */

//import
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

//points class
public class points {
	//integer variables
	private int startX;
	private int Y;
	private int startY;
	//boolean variables
	private boolean boostColor = false;
	private boolean cherryOnMap = false;
	//Rectangle variables
	private Rectangle [] pointRect = new Rectangle[1];
	private Rectangle [] pacBoost = new Rectangle[4];
	private Rectangle pacSpawn = new Rectangle(381, 428, 40, 35);
	private Rectangle cherryRect;
	//point variables
	private Point[] cherryP = new Point[14];
	private Point cherry;
	//color variable
	private Color lightYellow = new Color(245, 250, 95);
	//BufferedImage variable
	private BufferedImage cherryBImg;
	//stage Object
	private stage map = new stage();
	//random class variable
	private Random rand;

	//points constructor. gets startX and startY and sets variables accordingly
	points(int startXA, int startYA){
		startX = startXA;
		startY = startYA;
		//call method to generate points on map
		createPoints();
		try {
			//set BufferedImage cherryBImg to the according image in resources
			cherryBImg = ImageIO.read(this.getClass().getResource("/cherrySprite/cherry.png"));
		} 
		catch (IOException e) {
		}
		//set possible points for cherries to spawn
		cherryP[0] = new Point(165,125);
		cherryP[1] = new Point(592,125);
		cherryP[2] = new Point(379,279);
		cherryP[3] = new Point(380,125);
		cherryP[4] = new Point(505,205);
		cherryP[5] = new Point(250,205);
		cherryP[6] = new Point(167,364);
		cherryP[7] = new Point(593,364);
		cherryP[8] = new Point(254,507);
		cherryP[9] = new Point(510,507);
		cherryP[10] = new Point(172,660);
		cherryP[11] = new Point(595,660);
		cherryP[12] = new Point(85,660);
		cherryP[13] = new Point(685,660);
	}
	
	//getX method. returns startX
	public int getX(){
		return startX;
	}//end of getX method

	//getPointRect method. returns pointRect rectangle used for collision detection and collection of points
	public Rectangle getPointRect(int N){
		return pointRect[N];
	}//end of getPointRect method

	//getBoostRect method. returns getBoostRect rectangle used for collision detection and collection of boosts
	public Rectangle getBoostRect(int N){
		return pacBoost[N];
	}//end of getBoostRect method

	//pointCollected method. erases points that are collected
	public void pointCollected(int N){
		pointRect[N] = null;
	}//end of pointCollectd method

	//boostCollected method. erases boosts that are collected
	public void boostCollected(int N){
		pacBoost[N] = null;
	}//end of boostCollected method

	//pointLength method. returns the length of pointRect array
	public int pointLength(){
		return pointRect.length;
	}//end of pointLength method

	//boostLength method. returns the length of pacBoost array
	public int boostLength(){
		return pacBoost.length;
	}//end of boostLength method
	
	//createPoints method. creates boosts and generates points
	public void createPoints(){
		pacBoost[0] = new Rectangle(37 ,33 , 12, 12);
		pacBoost[1] = new Rectangle(749, 33, 12, 12);
		pacBoost[2] = new Rectangle(37, 745, 12, 12);
		pacBoost[3] = new Rectangle(749, 745, 12, 12);
		int initPointInt = 0;
		//generate points throughout the map as long as they aren't too close to the walls
		for(int y = 0; y < 72; y++){
			Y =  + (startY+10*y);
			for(int i = 0; i < 100; i ++){
				Rectangle tempRect = new Rectangle(startX+10*i, Y, 6, 6);
				boolean test = false;
				for(int k = 0; k < map.getLengthStageRect(); k++){
					Rectangle tempRectColl = new Rectangle((map.getStageRect(k).x-18),(map.getStageRect(k).y-16), (int)(map.getStageRect(k).getWidth()+38), (int)(map.getStageRect(k).getHeight()+32));
					if(tempRectColl.intersects(tempRect))
						test = true;
					if(tempRect.intersects(pacSpawn)){
						test = true;
					}
				}
				if(test == false){
					pointRect =	(Rectangle[]) resizeArray(pointRect, initPointInt+1);
					pointRect[initPointInt] = new Rectangle(startX+10*i, Y, 3, 3);
					initPointInt++;
				}
			}
		}
	}//end of createPoints method

	//changing colors
	public void boostColorChange(){
		if(boostColor == false)
			boostColor = true;
		else if(boostColor == true)
			boostColor = false;
	}//end of boostColorChange method

	//spawnCherry method. used to spawn cherries in random spots
	public void spawnCherry(){
		if(cherryOnMap == false){
			rand = new Random();
			cherry = cherryP[rand.nextInt(cherryP.length-0)+0];
			cherryRect = new Rectangle(cherry.x, cherry.y, 35, 35);
			cherryOnMap = true;
		}
	}//end of spawnCherry method

	//cherryCollected method. erases cherry when it is collected
	public void cherryCollected(){
		cherry = null;
		cherryRect = null;
		cherryOnMap = false;
	}//end of cherryCollected method
	
	//getCherryRect method. used for collision detection and collection of cherries
	public Rectangle getCherryRect(){
		return cherryRect;
	}//end of getCherryRect method

	//resizeArray method. resizes and object array depending on the parameters set and returns the new array
	private Object resizeArray (Object oldArray, int newSize) {
		int oldSize = java.lang.reflect.Array.getLength(oldArray);
		Class elementType = oldArray.getClass().getComponentType();
		Object newArray = java.lang.reflect.Array.newInstance(elementType,newSize);
		int preserveLength = Math.min(oldSize,newSize);
		if (preserveLength > 0)
			System.arraycopy (oldArray,0,newArray,0,preserveLength);
		return newArray; 
	}//end of resizeArray

	//paintPoints method. paints points, cherries and boosts on map.
	public void paintPoints(Graphics g){
		g.setColor(Color.YELLOW);
		for(int i = 0; i < pointRect.length;i++){
			if(pointRect[i]!= null)
				g.fillRect(pointRect[i].x,pointRect[i].y,(int)pointRect[i].getWidth(),(int)pointRect[i].getHeight());
		}
		if(boostColor == true)
			g.setColor(lightYellow);
		for(int i = 0; i < pacBoost.length;i++){
			if(pacBoost[i]!= null)
				g.fillOval(pacBoost[i].x, pacBoost[i].y, (int) pacBoost[i].getWidth(), (int) pacBoost[i].getHeight());
		}
		if(cherryOnMap == true){
			g.drawImage(cherryBImg,cherry.x, cherry.y, null);
		}
	}//end of paintPoints method
}//end of class
