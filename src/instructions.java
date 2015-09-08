/**
 * @Description: instructions Class. displays instructions
 * @author: Allen Huang
 * @version  v1.0
 * Date: June 13, 2015
 */

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
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class instructions extends JFrame implements ActionListener{

	private JPanel contentPane;
	private ImageIcon keyBoardImg;

	private Clip audioClip;
	/**
	 * Launch the application.
	 */
	//main method. creates instructions frame
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					instructions frame = new instructions();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	//instructions method.
	public instructions() {
		//set BufferedImage to appropriate image in resources
		try {
			keyBoardImg = new ImageIcon(ImageIO.read(this.getClass().getResource("/instructions/arrowKeys.png")));
		} 
		catch (IOException e) {
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
		//create GUI
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 644, 486);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblInstructions = new JLabel("Instructions");
		lblInstructions.setForeground(Color.YELLOW);
		lblInstructions.setHorizontalAlignment(SwingConstants.CENTER);
		lblInstructions.setFont(new Font("PacFont", Font.PLAIN, 18));
		lblInstructions.setBounds(10, 38, 608, 43);
		contentPane.add(lblInstructions);

		JLabel lblKeyBoardImg = new JLabel("New label");
		lblKeyBoardImg.setBounds(46, 108, 115, 75);
		contentPane.add(lblKeyBoardImg);
		lblKeyBoardImg.setIcon(keyBoardImg);

		JLabel lblInstuctions1 = new JLabel("<html>Use the arrow keys on your keyboard <br>to move Pac man</html>");
		lblInstuctions1.setForeground(Color.YELLOW);
		lblInstuctions1.setFont(new Font("BN Elements", Font.PLAIN, 14));
		lblInstuctions1.setBounds(197, 121, 321, 43);
		contentPane.add(lblInstuctions1);

		JLabel lblInstructions2 = new JLabel("<html>Collect all the points to complete the game <br><br>Point Breakdown: Point grants 10, Ghosts grant 50, Cherry grants 100<br><br>If you lose all your lives. The game will end.<br><br>[p] will pause the game.</html>");
		lblInstructions2.setForeground(Color.YELLOW);
		lblInstructions2.setFont(new Font("BN Elements", Font.PLAIN, 14));
		lblInstructions2.setBounds(46, 205, 572, 142);
		contentPane.add(lblInstructions2);

		JButton btnStart = new JButton("Start");
		btnStart.setToolTipText("Start Game");
		btnStart.setFont(new Font("BN Elements", Font.PLAIN, 16));
		btnStart.setBounds(50, 363, 138, 50);
		contentPane.add(btnStart);
		btnStart.addActionListener(this);
		btnStart.setActionCommand("start");

		JButton btnMenu = new JButton("Menu");			
		btnMenu.setToolTipText("Go to Menu");
		btnMenu.setFont(new Font("BN Elements", Font.PLAIN, 16));
		btnMenu.setBounds(252, 363, 138, 50);
		contentPane.add(btnMenu);
		btnMenu.addActionListener(this);
		btnMenu.setActionCommand("menu");

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//if exit button is pressed. confirm, then exit
				int exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
				if(exit == 0)
					System.exit(0);
			}
		});
		btnExit.setToolTipText("Exit Game");
		btnExit.setFont(new Font("BN Elements", Font.PLAIN, 16));
		btnExit.setBounds(445, 363, 138, 50);
		contentPane.add(btnExit);
	}//end of instructions method
	
	//actionPerformed method. tests if a button is pressed
	public void actionPerformed(ActionEvent e) {
		//if menu button is pressed, open menu frame and close instructions frame
		if(e.getActionCommand().equals("menu")){
			titlePage titlePageFrame = new titlePage();
			titlePageFrame.setVisible(true);
			audioClip.stop();
			this.dispose();
		}
		//if start button is pressed, open menu and start game and close instructions frame.
		else if(e.getActionCommand().equals("start")){
			titlePage titlePageFrame = new titlePage();
			titlePageFrame.setVisible(true);
			audioClip.stop();
			this.dispose();
			titlePageFrame.startPress();
		}
	}//end of actionPerformed method
}//end of class
