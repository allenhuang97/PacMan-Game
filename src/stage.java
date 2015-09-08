/**
 * @Description: stage Class. create walls of stage for collision detection
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

import javax.imageio.ImageIO;

//stage class
public class stage {
	//BufferedImage variable
	private BufferedImage stageSprite;
	//rectangle variables
	private Rectangle[] stageRect = new Rectangle[42];
	private Rectangle[] teleportRect = new Rectangle[2];
	private Rectangle[] teleportGhostRect = new Rectangle[2];
	private Rectangle ghostExitRect;
	private Rectangle[] intersectRectR = new Rectangle[44];
	private Rectangle[] intersectRectL = new Rectangle[44];
	private Rectangle[] intersectRectU = new Rectangle[40];
	private Rectangle[] intersectRectD = new Rectangle[40];
	
	//stage constructor
	stage(){
		//set stageSprite to image in package in resources
		try {
			stageSprite = ImageIO.read(this.getClass().getResource("/stageSprite/pacmanMap.png"));
		} 
		catch (IOException e) {
		}
		//call method to create rectangles for collision detection
		createStageRect();
	}
	public void createStageRect(){
		//rectangles for stage/ walls
		stageRect[0] = new Rectangle(0, 0, 799, 18);
		stageRect[1] = new Rectangle(0, 0, 19, 261);
		stageRect[2] = new Rectangle(73, 66, 87, 56);
		stageRect[3] = new Rectangle(213, 66, 118, 56);
		stageRect[4] = new Rectangle(71, 168, 91, 27);
		stageRect[5] = new Rectangle(385, 0, 30, 119);
		stageRect[6] = new Rectangle(299, 168, 203, 26);
		stageRect[7] = new Rectangle(470, 66, 116, 55);
		stageRect[8] = new Rectangle(639, 66, 92, 55);
		stageRect[9] = new Rectangle(638, 168, 94, 29);
		stageRect[10] = new Rectangle(782, 0, 17, 242);
		stageRect[11] = new Rectangle(-41, 243, 203, 105);
		stageRect[12] = new Rectangle(214, 169, 31, 179);
		stageRect[13] = new Rectangle(214, 244, 117, 29);
		stageRect[14] = new Rectangle(384, 169, 32, 100);
		stageRect[15] = new Rectangle(470, 243, 117, 30);
		stageRect[16] = new Rectangle(554, 168, 33, 180);
		stageRect[17] = new Rectangle(637, 242, 203, 106);;
		stageRect[18] = new Rectangle(298, 320, 205, 105);
		stageRect[19] = new Rectangle(-41, 394, 203, 109);
		stageRect[20] = new Rectangle(211, 394, 35, 107);
		stageRect[21] = new Rectangle(299, 471, 204, 30);
		stageRect[22] = new Rectangle(554, 394, 33, 109);
		stageRect[23] = new Rectangle(637, 394, 203, 107);
		stageRect[24] = new Rectangle(0, 482, 19, 317);
		stageRect[25] = new Rectangle(71, 548, 91, 26);
		stageRect[26] = new Rectangle(214, 547, 118, 31);
		stageRect[27] = new Rectangle(384, 472, 33, 104);
		stageRect[28] = new Rectangle(470, 548, 119, 30);
		stageRect[29] = new Rectangle(638, 548, 95, 30);
		stageRect[30] = new Rectangle(782, 482, 17, 317);
		stageRect[31] = new Rectangle(0, 623, 74, 29);
		stageRect[32] = new Rectangle(129, 549, 33, 102);
		stageRect[33] = new Rectangle(638, 549, 36, 102);
		stageRect[34] = new Rectangle(728, 624, 71, 29);
		stageRect[35] = new Rectangle(212, 625, 35, 102);
		stageRect[36] = new Rectangle(299, 623, 202, 29);
		stageRect[37] = new Rectangle(556, 624, 32, 101);
		stageRect[38] = new Rectangle(73, 698, 251, 30);
		stageRect[39] = new Rectangle(383, 625, 33, 99);
		stageRect[40] = new Rectangle(470, 700, 257, 28);
		stageRect[41] = new Rectangle(0, 776, 799, 17);

		//teleporting walls for ghosts and pacman
		teleportRect[0] = new Rectangle(-35, 331, 1, 84);
		teleportRect[1] = new Rectangle(835, 331, 1, 84);

		teleportGhostRect[0] = new Rectangle(-38, 331, 1, 84);
		teleportGhostRect[1] = new Rectangle(838, 331, 1, 84);
		
		//rectangle for when ghsots exit starting box
		ghostExitRect = new Rectangle(386, 283, 30, 1);

		//rectangles for testing which direction ghosts can move. Left
		intersectRectL[0] = new Rectangle(173, 29, 1, 30);
		intersectRectL[1] = new Rectangle(346, 29, 1, 30);
		intersectRectL[2] = new Rectangle(597, 29, 1, 30);
		intersectRectL[3] = new Rectangle(743, 29, 1, 30);

		intersectRectL[4] = new Rectangle(173, 131, 1, 30);
		intersectRectL[5] = new Rectangle(255, 131, 1, 30);
		intersectRectL[6] = new Rectangle(346, 131, 1, 30);
		intersectRectL[7] = new Rectangle(426, 131, 1, 30);
		intersectRectL[8] = new Rectangle(516, 131, 1, 30);
		intersectRectL[9] = new Rectangle(597, 131, 1, 30);
		intersectRectL[10] = new Rectangle(743, 131, 1, 30);

		intersectRectL[11] = new Rectangle(173, 206, 1, 30);
		intersectRectL[12] = new Rectangle(346, 206, 1, 30);
		intersectRectL[13] = new Rectangle(516, 206, 1, 30);
		intersectRectL[14] = new Rectangle(743, 206, 1, 30);

		intersectRectL[15] = new Rectangle(346, 283, 1, 30);
		intersectRectL[16] = new Rectangle(426, 283, 1, 30);
		intersectRectL[17] = new Rectangle(516, 283, 1, 30);

		intersectRectL[18] = new Rectangle(173, 356, 1, 30);
		intersectRectL[19] = new Rectangle(255, 356, 1, 30);
		intersectRectL[20] = new Rectangle(597, 356, 1, 30);

		intersectRectL[21] = new Rectangle(516, 435, 1, 30);

		intersectRectL[22] = new Rectangle(173, 511, 1, 30);
		intersectRectL[23] = new Rectangle(255, 511, 1, 30);
		intersectRectL[24] = new Rectangle(346, 511, 1, 30);
		intersectRectL[25] = new Rectangle(516, 511, 1, 30);
		intersectRectL[26] = new Rectangle(597, 511, 1, 30);
		intersectRectL[27] = new Rectangle(743, 511, 1, 30);

		intersectRectL[28] = new Rectangle(88, 585, 1, 30);
		intersectRectL[29] = new Rectangle(255, 585, 1, 30);
		intersectRectL[30] = new Rectangle(346, 585, 1, 30);
		intersectRectL[31] = new Rectangle(426, 585, 1, 30);
		intersectRectL[32] = new Rectangle(516, 585, 1, 30);
		intersectRectL[33] = new Rectangle(597, 585, 1, 30);
		intersectRectL[34] = new Rectangle(743, 585, 1, 30);

		intersectRectL[35] = new Rectangle(88, 660, 1, 30);
		intersectRectL[36] = new Rectangle(173, 660, 1, 30);
		intersectRectL[37] = new Rectangle(346, 660, 1, 30);
		intersectRectL[38] = new Rectangle(516, 660, 1, 30);
		intersectRectL[39] = new Rectangle(684, 660, 1, 30);
		intersectRectL[40] = new Rectangle(743, 660, 1, 30);

		intersectRectL[41] = new Rectangle(346, 739, 1, 30);
		intersectRectL[42] = new Rectangle(426, 739, 1, 30);
		intersectRectL[43] = new Rectangle(743, 739, 1, 30);

		//-----------------------------------------------------------------------------------------------------7
		//rectangles for testing which direction ghosts can move. Right
		intersectRectR[0] = new Rectangle(60, 29, 1, 30);
		intersectRectR[1] = new Rectangle(203, 29, 1, 30);
		intersectRectR[2] = new Rectangle(456, 29, 1, 30);
		intersectRectR[3] = new Rectangle(627, 29, 1, 30);

		intersectRectR[4] = new Rectangle(60, 131, 1, 30);
		intersectRectR[5] = new Rectangle(203, 131, 1, 30);
		intersectRectR[6] = new Rectangle(285, 131, 1, 30);
		intersectRectR[7] = new Rectangle(376, 131, 1, 30);
		intersectRectR[8] = new Rectangle(456, 131, 1, 30);
		intersectRectR[9] = new Rectangle(546, 131, 1, 30);
		intersectRectR[10] = new Rectangle(627, 131, 1, 30);

		intersectRectR[11] = new Rectangle(60, 206, 1, 30);
		intersectRectR[12] = new Rectangle(285, 206, 1, 30);
		intersectRectR[13] = new Rectangle(456, 206, 1, 30);
		intersectRectR[14] = new Rectangle(627, 206, 1, 30);

		intersectRectR[15] = new Rectangle(285, 283, 1, 30);
		intersectRectR[16] = new Rectangle(376, 283, 1, 30);
		intersectRectR[17] = new Rectangle(456, 283, 1, 30);

		intersectRectR[18] = new Rectangle(203, 356, 1, 30);
		intersectRectR[19] = new Rectangle(546, 356, 1, 30);
		intersectRectR[20] = new Rectangle(627, 356, 1, 30);

		intersectRectR[21] = new Rectangle(285, 435, 1, 30);

		intersectRectR[22] = new Rectangle(60, 511, 1, 30);
		intersectRectR[23] = new Rectangle(203, 511, 1, 30);
		intersectRectR[24] = new Rectangle(285, 511, 1, 30);
		intersectRectR[25] = new Rectangle(456, 511, 1, 30);
		intersectRectR[26] = new Rectangle(546, 511, 1, 30);
		intersectRectR[27] = new Rectangle(627, 511, 1, 30);

		intersectRectR[28] = new Rectangle(60, 585, 1, 30);
		intersectRectR[29] = new Rectangle(203, 585, 1, 30);
		intersectRectR[30] = new Rectangle(285, 585, 1, 30);
		intersectRectR[31] = new Rectangle(376, 585, 1, 30);
		intersectRectR[32] = new Rectangle(456, 585, 1, 30);
		intersectRectR[33] = new Rectangle(546, 585, 1, 30);
		intersectRectR[34] = new Rectangle(714, 585, 1, 30);

		intersectRectR[35] = new Rectangle(60, 660, 1, 30);
		intersectRectR[36] = new Rectangle(118, 660, 1, 30);
		intersectRectR[37] = new Rectangle(285, 660, 1, 30);
		intersectRectR[38] = new Rectangle(456, 660, 1, 30);
		intersectRectR[39] = new Rectangle(627, 660, 1, 30);
		intersectRectR[40] = new Rectangle(714, 660, 1, 30);

		intersectRectR[41] = new Rectangle(60, 739, 1, 30);
		intersectRectR[42] = new Rectangle(376, 739, 1, 30);		
		intersectRectR[43] = new Rectangle(456, 739, 1, 30);
		//-----------------------------------------------------------------------------------------------------
		//rectangles for testing which direction ghosts can move. Up
		intersectRectU[0] = new Rectangle(30, 131, 30, 1);
		intersectRectU[1] = new Rectangle(173, 131, 30, 1);
		intersectRectU[2] = new Rectangle(346, 131, 30, 1);
		intersectRectU[3] = new Rectangle(426, 131, 30, 1);
		intersectRectU[4] = new Rectangle(597, 131, 30, 1);
		intersectRectU[5] = new Rectangle(743, 131, 30, 1);

		intersectRectU[6] = new Rectangle(30, 206, 30, 1);
		intersectRectU[7] = new Rectangle(173, 206, 30, 1);	
		intersectRectU[8] = new Rectangle(255, 206, 30, 1);
		intersectRectU[9] = new Rectangle(516, 206, 30, 1);
		intersectRectU[10] = new Rectangle(597, 206, 30, 1);
		intersectRectU[11] = new Rectangle(743, 206, 30, 1);

		intersectRectU[12] = new Rectangle(346, 283, 30, 1);
		intersectRectU[13] = new Rectangle(426, 283, 30, 1);

		intersectRectU[14] = new Rectangle(173, 356, 30, 1);
		intersectRectU[15] = new Rectangle(255, 356, 30, 1);
		intersectRectU[16] = new Rectangle(516, 356, 30, 1);
		intersectRectU[17] = new Rectangle(597, 356, 30, 1);

		intersectRectU[18] = new Rectangle(255, 435, 30, 1);
		intersectRectU[19] = new Rectangle(516, 435, 30, 1);

		intersectRectU[20] = new Rectangle(173, 511, 30, 1);	
		intersectRectU[21] = new Rectangle(255, 511, 30, 1);
		intersectRectU[22] = new Rectangle(516, 511, 30, 1);
		intersectRectU[23] = new Rectangle(597, 511, 30, 1);

		intersectRectU[24] = new Rectangle(30, 585, 30, 1);
		intersectRectU[25] = new Rectangle(173, 585, 30, 1);
		intersectRectU[26] = new Rectangle(346, 585, 30, 1);
		intersectRectU[27] = new Rectangle(426, 585, 30, 1);
		intersectRectU[28] = new Rectangle(597, 585, 30, 1);
		intersectRectU[29] = new Rectangle(743, 585, 30, 1);

		intersectRectU[30] = new Rectangle(88, 660, 30, 1);
		intersectRectU[31] = new Rectangle(173, 660, 30, 1);	
		intersectRectU[32] = new Rectangle(255, 660, 30, 1);
		intersectRectU[33] = new Rectangle(516, 660, 30, 1);
		intersectRectU[34] = new Rectangle(597, 660, 30, 1);
		intersectRectU[35] = new Rectangle(684, 660, 30, 1);

		intersectRectU[36] = new Rectangle(30, 739, 30, 1);
		intersectRectU[37] = new Rectangle(346, 739, 30, 1);
		intersectRectU[38] = new Rectangle(426, 739, 30, 1);
		intersectRectU[39] = new Rectangle(743, 739, 30, 1);
		//-----------------------------------------------------------------------------------------------------
		//rectangles for testing which direction ghosts can move. Down
		intersectRectD[0] = new Rectangle(30, 59, 30, 1);
		intersectRectD[1] = new Rectangle(173, 59, 30, 1);
		intersectRectD[2] = new Rectangle(346, 59, 30, 1);
		intersectRectD[3] = new Rectangle(426, 59, 30, 1);
		intersectRectD[4] = new Rectangle(597, 59, 30, 1);
		intersectRectD[5] = new Rectangle(743, 59, 30, 1);

		intersectRectD[6] = new Rectangle(30, 161, 30, 1);
		intersectRectD[7] = new Rectangle(173, 161, 30, 1);
		intersectRectD[8] = new Rectangle(255, 161, 30, 1);
		intersectRectD[9] = new Rectangle(516, 161, 30, 1);
		intersectRectD[10] = new Rectangle(597, 161, 30, 1);
		intersectRectD[11] = new Rectangle(743, 161, 30, 1);

		intersectRectD[12] = new Rectangle(173, 236, 30, 1);
		intersectRectD[13] = new Rectangle(346, 236, 30, 1);
		intersectRectD[14] = new Rectangle(426, 236, 30, 1);
		intersectRectD[15] = new Rectangle(597, 236, 30, 1);

		intersectRectD[16] = new Rectangle(255, 313, 30, 1);
		intersectRectD[17] = new Rectangle(516, 313, 30, 1);

		intersectRectD[18] = new Rectangle(173, 386, 30, 1);
		intersectRectD[19] = new Rectangle(255, 386, 30, 1);
		intersectRectD[20] = new Rectangle(516, 386, 30, 1);
		intersectRectD[21] = new Rectangle(597, 386, 30, 1);

		intersectRectD[22] = new Rectangle(255, 465, 30, 1);
		intersectRectD[23] = new Rectangle(516, 465, 30, 1);

		intersectRectD[24] = new Rectangle(30, 541, 30, 1);
		intersectRectD[25] = new Rectangle(173, 541, 30, 1);
		intersectRectD[26] = new Rectangle(346, 541, 30, 1);
		intersectRectD[27] = new Rectangle(426, 541, 30, 1);
		intersectRectD[28] = new Rectangle(597, 541, 30, 1);
		intersectRectD[29] = new Rectangle(743, 541, 30, 1);

		intersectRectD[30] = new Rectangle(88, 615, 30, 1);
		intersectRectD[31] = new Rectangle(173, 615, 30, 1);
		intersectRectD[32] = new Rectangle(255, 615, 30, 1);
		intersectRectD[33] = new Rectangle(516, 615, 30, 1);
		intersectRectD[34] = new Rectangle(597, 615, 30, 1);
		intersectRectD[35] = new Rectangle(684, 615, 30, 1);

		intersectRectD[36] = new Rectangle(30, 690, 30, 1);
		intersectRectD[37] = new Rectangle(346, 690, 30, 1);
		intersectRectD[38] = new Rectangle(426, 690, 30, 1);
		intersectRectD[39] = new Rectangle(743, 690, 30, 1);	
	}//end of createStageRect method

	//getStageRect method. returns stageRect rectangle. used for collision detection between walls and pacman
	public Rectangle getStageRect(int num){
		return stageRect[num];
	}//end of getStageRect method

	//getTeleRect method. returns teleportRect rectangle. used for movement of pacman
	public Rectangle getTeleRect(int num){
		return teleportRect[num];
	}//end of getTeleRect method

	//getTeleGhostRect method. returns teleportGhostRect rectangle. used for movement of ghosts
	public Rectangle getTeleGhostRect(int num){
		return teleportGhostRect[num];
	}//end of getTeleGhostRect method
	
	//getIntersectRectL method. returns intersectRectL rectangle. used for movement of ghosts
	public Rectangle getIntersectRectL(int N){
		return intersectRectL[N];
	}//end of getIntersectRectL method

	//getIntersectRectR method. returns intersectRectR rectangle. used for movement of ghosts
	public Rectangle getIntersectRectR(int N){
		return intersectRectR[N];
	}//end of getIntersectRectR method

	//getIntersectRectU method. returns intersectRectU rectangle. used for movement of ghosts
	public Rectangle getIntersectRectU(int N){
		return intersectRectU[N];
	}//end of getIntersectRectU method

	//getIntersectRectD method. returns intersectRectD rectangle. used for movement of ghosts
	public Rectangle getIntersectRectD(int N){
		return intersectRectD[N];
	}//end of getIntersectRectD method

	//getGhostExitRect method. returns rectangle where ghosts exit
	public Rectangle getGhostExtRect(){
		return ghostExitRect;
	}//end of getGhostExtRect method
	
	//getLengthStageRect method. returns the length of stageRect array
	public int getLengthStageRect(){
		return stageRect.length;
	}//end of stageRect method

	//getLengthRectL method. returns the length of intersectRectL array
	public int getLengthRectL(){
		return intersectRectL.length;
	}//end of getLengthRectL method

	//getLengthRectR method. returns the length of intersectRectR array
	public int getLengthRectR(){
		return intersectRectR.length;
	}//end of getLengthRectR method

	//getLengthRectU method. returns the length of intersectRectU array
	public int getLengthRectU(){
		return intersectRectU.length;
	}//end of getLengthRectU method

	//getLengthRectD method. returns the length of intersectRectD array
	public int getLengthRectD(){
		return intersectRectD.length;
	}//end of getLengthRectD method
	
	//paintStage method. to paint background
	public void paintStage(Graphics g){
		//paint the image for background
		g.drawImage(stageSprite, 0, 0, null);
	}//end of paintStage method
}//end of class
