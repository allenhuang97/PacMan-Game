/**
 * @Description: loadingPage Class. loadingPage between titlePage and game
 * @author: Allen Huang
 * @version  v1.0
 * Date: June 13, 2015
 */

//import
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JProgressBar;

//loadingPage class
public class loadingPage extends JFrame implements ActionListener{
	//JFrame variables
	private JPanel contentPane;
	private JLabel lblLoadingDots1 = new JLabel(". . . . ");
	private JLabel lblLoadingDots2 = new JLabel(". . . . ");
	private JButton btnStart = new JButton("Start");
	private JProgressBar progressBar = new JProgressBar();
	//Timer variable
	private Timer loadTimer = new Timer();
//integer variables
	private int seconds = 1;
	private int loading = 0;
	private int loadingAnimate = 0;
//string variables
	private String name;


	/**
	 * Launch the application.
	 */
	//main method. creates loadingPage frame
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loadingPage frame = new loadingPage();
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
	//loadingPage method
	public loadingPage() {
		//create GUI
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblLoading = new JLabel("Loading");
		lblLoading.setForeground(Color.YELLOW);
		lblLoading.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoading.setFont(new Font("PacFont", Font.PLAIN, 18));
		lblLoading.setBounds(154, 122, 115, 46);
		contentPane.add(lblLoading);
		lblLoadingDots1.setHorizontalAlignment(SwingConstants.RIGHT);

		lblLoadingDots1.setForeground(Color.YELLOW);
		lblLoadingDots1.setFont(new Font("PacFont", Font.PLAIN, 18));
		lblLoadingDots1.setBounds(92, 138, 52, 14);
		contentPane.add(lblLoadingDots1);

		lblLoadingDots2.setForeground(Color.YELLOW);
		lblLoadingDots2.setFont(new Font("PacFont", Font.PLAIN, 18));
		lblLoadingDots2.setBounds(284, 138, 52, 14);
		contentPane.add(lblLoadingDots2);

		JButton btnMenu = new JButton("Menu");
		btnMenu.setForeground(Color.BLACK);
		btnMenu.setFont(new Font("BN Elements", Font.PLAIN, 12));
		btnMenu.setBounds(60, 179, 113, 37);
		btnMenu.addActionListener(this);
		btnMenu.setActionCommand("menu");
		contentPane.add(btnMenu);

		btnStart.setForeground(Color.BLACK);
		btnStart.setFont(new Font("BN Elements", Font.PLAIN, 12));
		btnStart.setBounds(255, 180, 113, 37);
		contentPane.add(btnStart);
		btnStart.addActionListener(this);
		btnStart.setActionCommand("start");
		btnStart.setEnabled(false);

		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("BN Elements", Font.PLAIN, 11));
		btnExit.setBounds(170, 228, 89, 23);
		contentPane.add(btnExit);
		btnExit.addActionListener(this);
		btnExit.setActionCommand("exit");

		progressBar.setBounds(80, 86, 275, 25);
		contentPane.add(progressBar);
		loadTimer.schedule(new load(), seconds * 1);
	}//end of loadingPage method
	
	//actionPerformed method. tests when buttons are pressed
	public void actionPerformed(ActionEvent e) {
		//if menu button is pressed. open menu frame and close loadingPage frame
		if(e.getActionCommand().equals("menu")){
			titlePage titlePageFrame = new titlePage();
			titlePageFrame.setVisible(true);
			this.dispose();
		}
		//if start is pressed. open menu frame and start applet and close loadingPage frame
		else if(e.getActionCommand().equals("start")){
			JOptionPane.showMessageDialog(null, "For testing purposes [space] will end the game");
			titlePage titlePageFrame = new titlePage();
			titlePageFrame.setVisible(true);
			titlePageFrame.startApplet();
			titlePageFrame.receiveName(name);
			this.dispose();		
		}
		//if exit button is pressed. confirm, then exit game
		else if(e.getActionCommand().equals("exit")){
			int exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
			if(exit == 0)
				System.exit(0);
		}
	}//end of actionPerformed method
	
	//receiveName method. receives name and sets it to appropriate variable
	public void receiveName(String nameA){
		name = nameA;
	}//end of receiveName method
	
	//load class for Timer
	class load extends TimerTask {
		public void run() {
			//load progress bar
			progressBar.setValue(loading);
			//animate dots next to loading label
			switch(loadingAnimate){
			case 0:
				lblLoadingDots1.setText(". ");
				lblLoadingDots2.setText(". ");
				break;
			case 1:
				lblLoadingDots1.setText(". . ");
				lblLoadingDots2.setText(". . ");
				break;
			case 2:
				lblLoadingDots1.setText(". . . ");
				lblLoadingDots2.setText(". . . ");
				break;
			case 3:
				lblLoadingDots1.setText(". . . . ");
				lblLoadingDots2.setText(". . . . ");
				loadingAnimate = -1;
				break;			
			}
			//integers for counting and animating
			loading +=4;
			loadingAnimate++;
			if(loading<=100)
				//reschedule timer
				loadTimer.schedule(new load(), seconds * 100);
			else
				//when timer reaches 100 enable start button
				btnStart.setEnabled(true);
		}
	}//end of load class
}//end of loadingPage class
