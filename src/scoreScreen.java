/**
 * @Description: scoreScreen Class. score screen of the game to show the results of the game played
 * @author: Allen Huang
 * @version  v1.0
 * Date: June 13, 2015
 */

//import
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingConstants;
import javax.swing.JButton;

//scoreScreen class
public class scoreScreen extends JFrame implements MouseListener, ActionListener{
	//JFrame variables
	private JPanel contentPane;

	private JLabel lblScoreScreen = new JLabel("Score - Screen");
	private JLabel lblBreakdown = new JLabel("Breakdown");
	private JLabel lblPointsCollected = new JLabel("Points Collected:");
	private JLabel lblCherriesCollected = new JLabel("Cherries Collected:");
	private JLabel lblGhostsKilled = new JLabel("Ghosts Killed:");
	private JLabel lblPoints = new JLabel("");
	private JLabel lblCherry = new JLabel("");
	private JLabel lblGhost = new JLabel("");
	private JLabel lblTotalPoints = new JLabel("Total Points:");
	private JLabel lblTotalPointsValue = new JLabel("");
	private JLabel lblLivesLeft = new JLabel("Lives Left:");
	private JLabel lblLives = new JLabel("");
	//integer variables
	private int points;
	private int pointsShow = 0;
	private int cherries;
	private int cherriesShow = 0;
	private int ghosts;
	private int ghostsShow = 0;
	private int timeShow = 0;
	private int lives;
	private int livesShow = 0;
	private int totalPoints;
	private int seconds = 1;
	private int scoreStep = 0;
	//string variables
	private String name;
	private String fileName = "leaderboard.txt";
	//boolean variables
	private boolean timerRunning = false;
	private boolean temp = false;
	//timer variable
	private Timer scoreTimer = new Timer();
	//clip variable
	private Clip audioClip;
	//scorescreen object variable
	private static scoreScreen frame;

	/**
	 * Launch the application.
	 */
	//main method. creates scoreScreen frame
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				try {
					frame = new scoreScreen();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.addMouseListener((MouseListener)frame);

				} 
				catch (Exception e) {
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	//scoreScreen method.
	public scoreScreen(){
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
		//create GUI
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 626, 620);
		contentPane = new JPanel();
		contentPane.setForeground(Color.YELLOW);
		contentPane.setBackground(Color.BLACK);
		contentPane.addMouseListener(this);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblScoreScreen.setHorizontalAlignment(SwingConstants.CENTER);
		lblScoreScreen.setForeground(Color.YELLOW);
		lblScoreScreen.setFont(new Font("PacFont", Font.PLAIN, 21));
		lblScoreScreen.setBounds(10, 44, 590, 87);
		contentPane.add(lblScoreScreen);

		lblBreakdown.setFont(new Font("PacFont", Font.PLAIN, 16));
		lblBreakdown.setForeground(Color.YELLOW);
		lblBreakdown.setBounds(53, 142, 204, 24);
		contentPane.add(lblBreakdown);

		lblPointsCollected.setForeground(Color.YELLOW);
		lblPointsCollected.setFont(new Font("PacFont", Font.PLAIN, 14));
		lblPointsCollected.setBounds(78, 193, 223, 14);
		lblPointsCollected.setVisible(false);
		contentPane.add(lblPointsCollected);

		lblCherriesCollected.setForeground(Color.YELLOW);
		lblCherriesCollected.setFont(new Font("PacFont", Font.PLAIN, 14));
		lblCherriesCollected.setBounds(78, 250, 240, 14);
		lblCherriesCollected.setVisible(false);
		contentPane.add(lblCherriesCollected);

		lblGhostsKilled.setFont(new Font("PacFont", Font.PLAIN, 14));
		lblGhostsKilled.setForeground(Color.YELLOW);
		lblGhostsKilled.setBounds(78, 309, 223, 14);
		lblGhostsKilled.setVisible(false);
		contentPane.add(lblGhostsKilled);
		lblPoints.setForeground(Color.YELLOW);

		lblPoints.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPoints.setFont(new Font("BN Elements", Font.PLAIN, 14));
		lblPoints.setBounds(374, 194, 156, 14);
		contentPane.add(lblPoints);

		lblCherry.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCherry.setForeground(Color.YELLOW);
		lblCherry.setFont(new Font("BN Elements", Font.PLAIN, 14));
		lblCherry.setBounds(374, 251, 156, 14);
		contentPane.add(lblCherry);

		lblGhost.setForeground(Color.YELLOW);
		lblGhost.setFont(new Font("BN Elements", Font.PLAIN, 14));
		lblGhost.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGhost.setBounds(374, 310, 156, 14);
		contentPane.add(lblGhost);

		lblTotalPoints.setFont(new Font("PacFont", Font.PLAIN, 16));
		lblTotalPoints.setForeground(Color.YELLOW);
		lblTotalPoints.setBounds(53, 437, 176, 14);
		lblTotalPoints.setVisible(false);
		contentPane.add(lblTotalPoints);

		lblTotalPointsValue.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalPointsValue.setForeground(Color.YELLOW);
		lblTotalPointsValue.setFont(new Font("BN Elements", Font.PLAIN, 14));
		lblTotalPointsValue.setBounds(374, 437, 156, 14);
		contentPane.add(lblTotalPointsValue);

		lblLivesLeft.setForeground(Color.YELLOW);
		lblLivesLeft.setFont(new Font("PacFont", Font.PLAIN, 14));
		lblLivesLeft.setBounds(78, 364, 223, 14);
		lblLivesLeft.setVisible(false);
		contentPane.add(lblLivesLeft);

		lblLives.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLives.setForeground(Color.YELLOW);
		lblLives.setFont(new Font("BN Elements", Font.PLAIN, 14));
		lblLives.setBounds(374, 365, 156, 14);
		contentPane.add(lblLives);

		JButton btnLeaderboard = new JButton("Leaderboard");
		btnLeaderboard.setBounds(79, 489, 107, 47);
		contentPane.add(btnLeaderboard);
		btnLeaderboard.addActionListener(this);
		btnLeaderboard.setActionCommand("leaderboard");

		JButton btnMenu = new JButton("Menu");
		btnMenu.setBounds(254, 489, 107, 47);
		contentPane.add(btnMenu);
		btnMenu.addActionListener(this);
		btnMenu.setActionCommand("menu");

		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(423, 489, 107, 47);
		contentPane.add(btnExit);
		btnExit.addActionListener(this);
		btnExit.setActionCommand("exit");
	}//end of scoreScreen method

	//actionPerformed class. test for buttons pressed
	public void actionPerformed(ActionEvent e) {
		//if leaderboard button is pressed. open leaderboard frame and close scorescreen frame
		if(e.getActionCommand().equals("leaderboard")){
			leaderBoard leaderBoardFrame = new leaderBoard();
			leaderBoardFrame.setVisible(true);
			audioClip.stop();
			this.dispose();
		}
		//if menu button is pressed. open menu frame and close scorescreen frame
		else if(e.getActionCommand().equals("menu")){
			titlePage titlePageFrame = new titlePage();
			titlePageFrame.setVisible(true);
			audioClip.stop();
			this.dispose();
		}
		//if exit button is pressed. confirm, then exit
		else if(e.getActionCommand().equals("exit")){
			System.exit(0);
		}
	}//end of actionPerformed method

	//mouseClicked method. tests if mouse if clicked
	public void mouseClicked(MouseEvent arg0){
		//if mouse if clicked then skip the current score countup
		if(timerRunning == true){
			switch(scoreStep){
			case 0:
				pointsShow = points;
				break;
			case 1:
				cherriesShow = cherries;
				break;
			case 2:
				ghostsShow = ghosts;
				break;
			case 3:
				livesShow = lives;
				break;
			}
		}
	}//end of mouseClicked method
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}

	//reveiveScoreBreakdown method. receives score from titlePage class and sets it to variables accordingly
	public void receiveScoreBreakdown(int pointsA, int ghostsA, int cherriesA, int livesA, String nameA){
		points = pointsA; 
		ghosts = ghostsA * 50;
		cherries = cherriesA * 100;
		lives = livesA * 100;
		name = nameA;
		totalPoints = points + ghosts + cherries + lives;
		lblPointsCollected.setVisible(true);
		timerRunning = true;
		//append these scores onto the file 
		BufferedWriter bw = null;
		try{
			bw = new BufferedWriter(new FileWriter(fileName, true));
			bw.write(name + "\t" + points + "\t" + ghosts + "\t" + cherries + "\t" + lives + "\t"+ totalPoints);
			bw.newLine();
			bw.flush();
		}
		catch(IOException e){
		}
		finally{
			if(bw !=null){
				try{
					bw.close();
				}
				catch(IOException e2){
				}
			}
		}
		//schedule score timer
		scoreTimer.schedule(new score(), seconds * 1);
	}//end of receiveScoreBreakdown method

	//score class for Timer
	class score extends TimerTask {
		public void run(){
			//countup by 10 and display points
			if(pointsShow != points){
				pointsShow+=10;
				lblPoints.setText(String.valueOf(pointsShow));
			}
			else if(cherriesShow!= cherries){
				cherriesShow+=10;
				lblCherry.setText(String.valueOf(cherriesShow));
			}

			else if(ghostsShow != ghosts){
				ghostsShow+=10;
				lblGhost.setText(String.valueOf(ghostsShow));
			}

			else if(livesShow != lives){
				livesShow+=10;
				lblLives.setText(String.valueOf(livesShow));
			}
			//display labels for each point type when appropriate
			if((pointsShow >= points)&&(scoreStep == 0)){
				lblPoints.setText(String.valueOf(points));
				scoreStep = 1;
				lblCherriesCollected.setVisible(true);
			}
			if((cherriesShow >= cherries)&&(scoreStep == 1)){
				lblCherry.setText(String.valueOf(cherries));
				scoreStep = 2;
				lblGhostsKilled.setVisible(true);
			}
			if((ghostsShow >= ghosts)&&(scoreStep == 2)){
				lblGhost.setText(String.valueOf(ghosts));
				scoreStep = 3;
				lblLivesLeft.setVisible(true);
			}
			if((livesShow >= lives)&&(scoreStep == 3)){
				lblLives.setText(String.valueOf(lives));
				scoreStep = 4;
				lblTotalPoints.setVisible(true);
				lblTotalPointsValue.setText(String.valueOf(totalPoints));
			}
			if(scoreStep!=4)
				//reschedule scoreTimer
				scoreTimer.schedule(new score(), seconds * 10);
		}//end of score class
	}//end of scoreScreen class/

}