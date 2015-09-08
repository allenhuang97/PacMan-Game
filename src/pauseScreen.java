/**
 * @Description: pauseScreen Class. pause screen when game is paused
 * @author: Allen Huang
 * @version  v1.0
 * Date: June 13, 2015
 */

//import
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//pauseScreen class
public class pauseScreen extends JFrame {
	//JFrame variables
	private JPanel contentPane;
	//boolean variables
	private boolean resume = false;
	private boolean menu = false;

	/**
	 * Launch the application.
	 */
	//main method. creates pauseScreen frame
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					pauseScreen frame = new pauseScreen();
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
	//pauseScreen method
	public pauseScreen() {
		//create GUI
		setBackground(Color.BLACK);
		setForeground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 445, 324);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPause = new JLabel("Pause");
		lblPause.setHorizontalAlignment(SwingConstants.CENTER);
		lblPause.setFont(new Font("PacFont", Font.PLAIN, 18));
		lblPause.setForeground(new Color(255, 255, 0));
		lblPause.setBounds(10, 29, 409, 48);
		contentPane.add(lblPause);
		
		JButton btnResumeGame = new JButton("Resume Game");
		btnResumeGame.addActionListener(new ActionListener() {
			//if the resume game button is pressed resume the game
			public void actionPerformed(ActionEvent arg0) {
				resume = true;
			}
		});
		btnResumeGame.setFont(new Font("BN Elements", Font.PLAIN, 16));
		btnResumeGame.setBounds(88, 105, 253, 23);
		contentPane.add(btnResumeGame);
		
		JButton btnMenu = new JButton("Menu");
		btnMenu.addActionListener(new ActionListener() {
			//if the menu button is pressed. exit game and open menu
			public void actionPerformed(ActionEvent e) {
				menu = true;
			}
		});
		btnMenu.setFont(new Font("BN Elements", Font.PLAIN, 16));
		btnMenu.setBounds(88, 158, 253, 23);
		contentPane.add(btnMenu);
		
		JButton btnExitGame = new JButton("Exit Game");
		btnExitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if exit button is pressed. confirm, then exit
				int exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
				if(exit == 0)
					System.exit(0);
			}
		});
		btnExitGame.setFont(new Font("BN Elements", Font.PLAIN, 16));
		btnExitGame.setBounds(88, 212, 253, 23);
		contentPane.add(btnExitGame);
	}
	
	//isResumed method. returns resume boolean
	public boolean isResumed(){
		return resume;
	}//end of isResumed method
	
	//isMenu method. returns menu boolean
	public boolean isMenu(){
		return menu;
	}//end of isMenu method
	
	//gotMenu method. sets menu to false. to prevent creating multiple menus due to timer
	public void gotMenu(){
		menu = false;
	}//end of gotMenu method
}//end of class