/**
 * @Description: titlePage Class. titlePage for the game. Manages game and navigation through other JFrame classes
 * @author: Allen Huang
 * @version  v1.0
 * Date: June 13, 2015
 */

//import
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

//titlePage class
public class titlePage extends JFrame implements ActionListener{
	//ImageIcon variables
	private ImageIcon [] pacMan = new ImageIcon[4];
	private ImageIcon [] ghost = new ImageIcon[12];
	private ImageIcon blackBack = new ImageIcon();
	//Jframe variables
	private JPanel contentPane;

	private JButton btnStart = new JButton("START");
	private JButton btnInstructions = new JButton("INSTRUCTIONS");
	private JButton btnExit = new JButton("EXIT");
	private JButton btnDispose = new JButton();
	private JButton btnLeaderBoard = new JButton("LEADERBOARD");

	private JLabel lblPacMan = new JLabel("Pac - Man");
	private JLabel lblPacSprite = new JLabel("");
	private JLabel lblGhost1 = new JLabel("");
	private JLabel lblGhost2 = new JLabel("");
	private JLabel lblGhost3 = new JLabel("");
	private JLabel lblBlackCover1 = new JLabel("");
	private JLabel lblBlackCover2 = new JLabel("");
	//Timer variable
	private Timer moveTimer = new Timer();
	//integer variables
	private int pacSpriteCounter = 0;
	private int direction = 1;
	private int seconds = 1;
	private int time = 0;
	//boolean variable
	private boolean pause = false;
	//String variable
	private String name = "";
	//object variables
	private game gameApp;
	private pauseScreen pauseFrame;
	private static titlePage titlePageFrame;
	//clip variable
	private Clip audioClip;

	/**
	 * Launch the application.
	 */
	//main method. runs first to create frame
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					titlePageFrame = new titlePage();
					titlePageFrame.setResizable(false);
					titlePageFrame.setVisible(true);
				} 
				catch (Exception e) {
				}
			}
		});		
	}

	/**
	 * Create the frame.
	 */
	//titlePage method
	public titlePage() {
		try{
			//set ImageIcon variables to their according image
			pacMan[0] = new ImageIcon(ImageIO.read(this.getClass().getResource("/pacManSprites/pacmanr1.png")));
			pacMan[1] = new ImageIcon(ImageIO.read(this.getClass().getResource("/pacManSprites/pacmanr2.png")));
			pacMan[2] = new ImageIcon(ImageIO.read(this.getClass().getResource("/pacManSprites/pacmanl1.png")));
			pacMan[3] = new ImageIcon(ImageIO.read(this.getClass().getResource("/pacManSprites/pacmanl2.png")));

			ghost[0] = new ImageIcon(ImageIO.read(this.getClass().getResource("/enemySprites/ghostscare1.png")));
			ghost[1] = new ImageIcon(ImageIO.read(this.getClass().getResource("/enemySprites/ghostscare2.png")));
			ghost[2] = new ImageIcon(ImageIO.read(this.getClass().getResource("/enemySprites/ghostc0d3s1.png")));
			ghost[3] = new ImageIcon(ImageIO.read(this.getClass().getResource("/enemySprites/ghostc0d3s2.png")));

			ghost[4] = new ImageIcon(ImageIO.read(this.getClass().getResource("/enemySprites/ghostscare1.png")));
			ghost[5] = new ImageIcon(ImageIO.read(this.getClass().getResource("/enemySprites/ghostscare2.png")));
			ghost[6] = new ImageIcon(ImageIO.read(this.getClass().getResource("/enemySprites/ghostc1d3s1.png")));
			ghost[7] = new ImageIcon(ImageIO.read(this.getClass().getResource("/enemySprites/ghostc1d3s2.png")));

			ghost[8] = new ImageIcon(ImageIO.read(this.getClass().getResource("/enemySprites/ghostscare1.png")));
			ghost[9] = new ImageIcon(ImageIO.read(this.getClass().getResource("/enemySprites/ghostscare2.png")));
			ghost[10] = new ImageIcon(ImageIO.read(this.getClass().getResource("/enemySprites/ghostc2d3s1.png")));
			ghost[11] = new ImageIcon(ImageIO.read(this.getClass().getResource("/enemySprites/ghostc2d3s2.png")));

			blackBack = new ImageIcon(ImageIO.read(this.getClass().getResource("/blackCover/blackCover.png")));
		}
		catch(IOException ioe){
		}
		//play music
		try{
			URL url = this.getClass().getResource("gameSound/menu music.wav");
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
		//create JFrame and GUI
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setBounds(100, 100, 715, 511);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblBlackCover1.setBackground(Color.BLACK);
		lblBlackCover1.setBounds(-14, 185, 197, 59);
		lblBlackCover1.setIcon(blackBack);
		contentPane.add(lblBlackCover1);

		lblBlackCover2.setBackground(Color.BLACK);
		lblBlackCover2.setBounds(510, 185, 197, 59);
		lblBlackCover2.setIcon(blackBack);
		contentPane.add(lblBlackCover2);

		btnStart.setToolTipText("Start Game");
		btnStart.setFont(new Font("PacFont", Font.PLAIN, 11));
		btnStart.setBounds(54, 337, 150, 59);
		contentPane.add(btnStart);
		btnStart.addActionListener(this);
		btnStart.setActionCommand("start");

		btnInstructions.setToolTipText("View Instructions");
		btnInstructions.setFont(new Font("PacFont", Font.PLAIN, 11));
		btnInstructions.setBounds(269, 337, 150, 59);
		contentPane.add(btnInstructions);
		btnInstructions.addActionListener(this);
		btnInstructions.setActionCommand("instructions");

		btnExit.setFont(new Font("PacFont", Font.PLAIN, 11));
		btnExit.setBounds(303, 427, 89, 23);
		contentPane.add(btnExit);
		btnExit.addActionListener(this);
		btnExit.setActionCommand("exit");

		lblPacMan.setForeground(Color.YELLOW);
		lblPacMan.setHorizontalAlignment(SwingConstants.CENTER);
		lblPacMan.setFont(new Font("PacFont", Font.PLAIN, 40));
		lblPacMan.setBounds(10, 79, 679, 74);
		contentPane.add(lblPacMan);

		lblPacSprite.setBounds(-10, 197, 35, 35);
		lblPacSprite.setIcon(pacMan[0]);
		contentPane.add(lblPacSprite);

		lblGhost1.setBounds(57, 197, 35, 35);
		lblGhost1.setIcon(ghost[0]);
		contentPane.add(lblGhost1);

		lblGhost2.setBounds(102, 197, 35, 35);
		lblGhost2.setIcon(ghost[4]);
		contentPane.add(lblGhost2);

		lblGhost3.setBounds(148, 197, 35, 35);
		lblGhost3.setIcon(ghost[8]);
		contentPane.add(lblGhost3);

		btnLeaderBoard.setToolTipText("View Instructions");
		btnLeaderBoard.setFont(new Font("PacFont", Font.PLAIN, 11));
		btnLeaderBoard.addActionListener(this);
		btnLeaderBoard.setActionCommand("leaderboard");
		btnLeaderBoard.setBounds(481, 337, 161, 59);

		btnDispose.addActionListener(this);
		btnDispose.setActionCommand("dispose");

		contentPane.add(btnLeaderBoard);
		//create new timer and schedule it.
		moveTimer = new Timer();
		moveTimer.schedule(new move(), seconds * 1);
	}//end of titlePage method

	//startPress method. presses start button
	public void startPress(){
		btnStart.doClick();
	}//end of startPress method

	//receiveName method. receives name and sets it to a variable
	public void receiveName(String nameA){
		name = nameA;
	}//end of receiveName method

	//actionPerformed method. buttons are pressed
	public void actionPerformed(ActionEvent e) {
		//if the start button is pressed.
		if(e.getActionCommand().equals("start")){
			final JFrame parent = new JFrame();
			//ask for name
			name = JOptionPane.showInputDialog(parent,"What is your name?", null);
			if(name!=null){
				if(name.length()>0){
					//open loading page
					loadingPage loadingPageFrame = new loadingPage();
					loadingPageFrame.setVisible(true);
					loadingPageFrame.receiveName(name);
					//stop audio and close this frame
					audioClip.stop();
					this.dispose();
				}
				else if(name.length()==0){
					JOptionPane.showMessageDialog(null, "Your name must contain at least 1 character");
				}
			}
		}
		//if exit button is pressed. confirm then exit
		else if(e.getActionCommand().equals("exit")){
			int exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
			if(exit == 0)
				System.exit(0);
		}
		//if instructions button is pressed
		else if(e.getActionCommand().equals("instructions")){
			//open instructions frame
			instructions instructionsFrame = new instructions();
			instructionsFrame.setVisible(true);
			//stop audio and close titlePage frame
			audioClip.stop();
			this.dispose();
		}
		//if leaderboard button is pressed
		else if(e.getActionCommand().equals("leaderboard")){
			//open leaderboard frame
			leaderBoard leaderBoardFrame = new leaderBoard();
			leaderBoardFrame.setVisible(true);
			//stop audio and close titlePage frame
			audioClip.stop();
			this.dispose();
		}
		//if dispose button is pressed
		else if(e.getActionCommand().equals("dispose")){
			//stop audio and close titlePage frame
			this.dispose();
			audioClip.stop();
		}
	}//end of actionPerformed method

	//startApplet method. starts game applet and hides all GUI 
	public void startApplet(){
		btnStart.setVisible(false);
		btnInstructions.setVisible(false);
		btnExit.setVisible(false);
		btnLeaderBoard.setVisible(false);
		lblPacMan.setVisible(false);
		lblPacSprite.setVisible(false);
		lblGhost1.setVisible(false);
		lblGhost2.setVisible(false);
		lblGhost3.setVisible(false);
		lblBlackCover1.setVisible(false);
		lblBlackCover2.setVisible(false);
		this.setBounds(0,0,816,875);
		audioClip.stop();
		gameApp = new game();
		getContentPane().add(gameApp);
		gameApp.init();
		gameApp.start();
	}//end of startApplet method

	//moveLeft method. moves ghosts and pacman left
	private void moveLeft(){
		lblPacSprite.setLocation(lblPacSprite.getX()-1,lblPacSprite.getY());
		lblGhost1.setLocation(lblGhost1.getX()-1,lblGhost1.getY());
		lblGhost2.setLocation(lblGhost2.getX()-1,lblGhost2.getY());
		lblGhost3.setLocation(lblGhost3.getX()-1,lblGhost3.getY());
		if(lblPacSprite.getX() == -20){
			direction = 1;
			lblPacSprite.setIcon(pacMan[0]);
			lblGhost1.setIcon(ghost[0]);
			lblGhost2.setIcon(ghost[4]);
			lblGhost3.setIcon(ghost[8]);
		}
	}//end of moveLeft method

	//moveRight method moves ghosts and pacman right
	private void moveRight(){
		lblPacSprite.setLocation(lblPacSprite.getX()+1,lblPacSprite.getY());
		lblGhost1.setLocation(lblGhost1.getX()+1,lblGhost1.getY());
		lblGhost2.setLocation(lblGhost2.getX()+1,lblGhost2.getY());
		lblGhost3.setLocation(lblGhost3.getX()+1,lblGhost3.getY());
		if(lblPacSprite.getX() == 520){
			direction = 2;
			lblPacSprite.setIcon(pacMan[2]);
			lblGhost1.setIcon(ghost[2]);
			lblGhost2.setIcon(ghost[6]);
			lblGhost3.setIcon(ghost[10]);
		}
	}//end of moveRight method

	//changeSprite method. used to change sprites of ghosts and pacman to animate titlePage
	private void changeSprite(){
		if(direction == 1){
			if(lblPacSprite.getIcon()==pacMan[0])
				lblPacSprite.setIcon(pacMan[1]);
			else if(lblPacSprite.getIcon()==pacMan[1])
				lblPacSprite.setIcon(pacMan[0]);	
			if(lblGhost1.getIcon()==ghost[0]){
				lblGhost1.setIcon(ghost[1]);
				lblGhost2.setIcon(ghost[5]);
				lblGhost3.setIcon(ghost[9]);
			}
			else if(lblGhost1.getIcon()==ghost[1]){
				lblGhost1.setIcon(ghost[0]);
				lblGhost2.setIcon(ghost[4]);
				lblGhost3.setIcon(ghost[8]);
			}
		}
		else if(direction == 2){
			if(lblPacSprite.getIcon()==pacMan[2])
				lblPacSprite.setIcon(pacMan[3]);
			else if(lblPacSprite.getIcon()==pacMan[3])
				lblPacSprite.setIcon(pacMan[2]);
			if(lblGhost1.getIcon()==ghost[2]){
				lblGhost1.setIcon(ghost[3]);
				lblGhost2.setIcon(ghost[7]);
				lblGhost3.setIcon(ghost[11]);
			}
			else if(lblGhost1.getIcon()==ghost[3]){
				lblGhost1.setIcon(ghost[2]);
				lblGhost2.setIcon(ghost[6]);
				lblGhost3.setIcon(ghost[10]);
			}
		}
	}//end of changeSprite method

	//move class for Timer
	class move extends TimerTask {
		public void run() {
			//move the ghosts and pacman acrossthe screen
			if(direction == 1)
				moveRight();
			else if(direction == 2)
				moveLeft();
			if(pacSpriteCounter == 21){
				pacSpriteCounter = 0;
				changeSprite();
			}
			pacSpriteCounter++;
			//if the applet is started and the game ends
			if(gameApp!=null){
				if(gameApp.isGameOver()==true){
					gameApp.gameEnded();
					//open score screen frame
					scoreScreen scoreScreenFrame = new scoreScreen();
					scoreScreenFrame.setVisible(true);
					//send scores and variable data to the score screen frame
					scoreScreenFrame.receiveScoreBreakdown(gameApp.getPoints(),gameApp.getGhostKilled(),gameApp.getCherriesCollected(),gameApp.getLives(), name);
					//stop audio and close titlePage frame
					btnDispose.doClick();
					gameApp.stopAudio();

				}
				//if the game is paused
				if(gameApp.isPaused()==true){
					if(pause == false){
						//create pause menu and open
						pauseFrame = new pauseScreen();
						pauseFrame.setVisible(true);
						pause = true;
					}
					//if the game is resumed then resume game
					if(pauseFrame.isResumed() == true){
						pauseFrame.dispose();
						gameApp.resumeGame();
						pause = false;
					}
					//if menu is selected on pause menu. close everything. and stop applet. then open a new titlePage
					if(pauseFrame.isMenu() == true){
						pauseFrame.gotMenu();
						pauseFrame.dispose();
						btnDispose.doClick();
						gameApp.stopAudio();
						titlePage titlePageFrame = new titlePage();
						titlePageFrame.setVisible(true);
					}
				}
			}
			//reschedule timer
			moveTimer.schedule(new move(), seconds * 10);
		}
	}//end of move class
}//end of titlePage class
