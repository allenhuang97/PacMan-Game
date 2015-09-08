/**
 * @Description: scoreScreen Class. score screen of the game to show the results of the game played
 * @author: Allen Huang
 * @version  v1.0
 * Date: June 13, 2015
 */

//import
import java.awt.Color;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;

//leaderBoard class
public class leaderBoard extends JFrame implements ActionListener{
	//JFrame variables
	private JPanel contentPane;
	private JTable tableLeaderboard;
	//private JScrollPane scrollPane;
	private JComboBox cbxSortSelect = new JComboBox();
	private JComboBox cbxSearch = new JComboBox();
	private JButton btnExit = new JButton("Exit");
	private JButton btnMenu = new JButton("Menu");
	private JButton btnSearch = new JButton("Search");
	private JButton btnViewOriginal = new JButton("View Original");
	private JLabel lblSearch = new JLabel("Search:");
	private JTextField txtFldSearch;
	//DefaultTableModel variable
	private DefaultTableModel model = new DefaultTableModel();
	//integer variable
	private int entryNum = 0;
	private int sortNum = 0;
	//String variable
	private String [] leaderBoardHeaders = new String[7];
	private String[][] leaderBoard = new String [entryNum][8];
	private String[][] searchString = new String[0][7];
	private String fileName = "leaderboard.txt";
	private String inputText;
	private String tempData[];
	//Clip variable
	private Clip audioClip;
	/**
	 * Launch the application.
	 */
	//main method. creates leaderboard frame
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					leaderBoard frame = new leaderBoard();
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
	//leaderBoard method.
	public leaderBoard(){
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
		//set leaderBoardHeader values 
		leaderBoardHeaders[0] = "Rank";
		leaderBoardHeaders[1] = "Player";
		leaderBoardHeaders[2] = "Points";
		leaderBoardHeaders[3] = "Ghosts";
		leaderBoardHeaders[4] = "Cherries";
		leaderBoardHeaders[5] = "Lives";
		leaderBoardHeaders[6] = "Total Points";

		//create GUI
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 797, 671);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSortLeaderboardBy = new JLabel("Sort Leaderboard By:");
		lblSortLeaderboardBy.setForeground(Color.BLACK);
		lblSortLeaderboardBy.setFont(new Font("BN Elements", Font.PLAIN, 12));
		lblSortLeaderboardBy.setBounds(10, 536, 154, 27);
		contentPane.add(lblSortLeaderboardBy);

		JLabel lblLeaderboards = new JLabel("Leaderboards");
		lblLeaderboards.setHorizontalAlignment(SwingConstants.CENTER);
		lblLeaderboards.setFont(new Font("BN Elements", Font.BOLD, 18));
		lblLeaderboards.setBounds(10, 34, 761, 37);
		contentPane.add(lblLeaderboards);

		btnExit.setFont(new Font("BN Elements", Font.PLAIN, 12));
		btnExit.setBounds(572, 552, 142, 56);
		contentPane.add(btnExit);
		btnExit.addActionListener(this);
		btnExit.setActionCommand("Exit");

		btnMenu.setFont(new Font("BN Elements", Font.PLAIN, 12));
		btnMenu.setBounds(381, 552, 142, 56);
		contentPane.add(btnMenu);
		btnMenu.addActionListener(this);
		btnMenu.setActionCommand("Menu");

		cbxSortSelect.setModel(new DefaultComboBoxModel(new String[] {"Points", "Ghosts", "Cherries", "Lives", "TotalPoints"}));
		cbxSortSelect.setBounds(171, 538, 125, 20);
		contentPane.add(cbxSortSelect);
		cbxSortSelect.setSelectedIndex(-1);
		cbxSortSelect.addActionListener(this);
		cbxSortSelect.setActionCommand("Sort Select");

		lblSearch.setForeground(Color.BLACK);
		lblSearch.setFont(new Font("BN Elements", Font.PLAIN, 12));
		lblSearch.setBounds(11, 574, 54, 27);
		contentPane.add(lblSearch);

		cbxSearch.setModel(new DefaultComboBoxModel(new String[] {"Name", "Points", "Ghosts", "Cherries", "Lives", "TotalPoints"}));
		cbxSearch.setSelectedIndex(-1);
		cbxSearch.setActionCommand("Sort Select");
		cbxSearch.setBounds(75, 574, 125, 20);
		contentPane.add(cbxSearch);
		cbxSearch.addActionListener(this);
		cbxSearch.setActionCommand("Search Type");

		txtFldSearch = new JTextField();
		txtFldSearch.setBounds(75, 602, 125, 20);
		contentPane.add(txtFldSearch);
		txtFldSearch.setColumns(10);

		btnSearch.setFont(new Font("BN Elements", Font.PLAIN, 12));
		btnSearch.setBounds(220, 578, 89, 46);
		contentPane.add(btnSearch);
		btnSearch.addActionListener(this);
		btnSearch.setActionCommand("Search");
		btnSearch.setEnabled(false);

		btnViewOriginal.setFont(new Font("BN Elements", Font.PLAIN, 12));
		btnViewOriginal.setBounds(220, 578, 131, 46);
		contentPane.add(btnViewOriginal);
		btnViewOriginal.setVisible(false);
		btnViewOriginal.addActionListener(this);
		btnViewOriginal.setActionCommand("View Original");
		for(int i = 0; i < leaderBoardHeaders.length;i++)
			model.addColumn(leaderBoardHeaders[i]);
		//display the JTable on the scroll pane
		displayLeaderBoard();
		//call method to read data from file
		inputFromFile();
	}//end of leaderBoard method

	//inputFromFile method. usedread data from file and set it to an array
	public void inputFromFile(){
		entryNum = 0;
		try {
			FileReader inputFile = new FileReader(fileName);
			BufferedReader bufferReader = new BufferedReader(inputFile);
			//Loop to continue reading each line of text in file until there is none left
			while ((inputText = bufferReader.readLine()) != null) {
				//Prevents errors when splitting string
				try {
					//Removes the tabs from each line, and puts it into an array
					tempData = inputText.split("\t");
					entryNum++;
					//set data to array
					leaderBoard = resizeLeaderBoard(leaderBoard, entryNum);
					leaderBoard[entryNum-1][0] = "";
					leaderBoard[entryNum-1][1] = tempData[0];
					leaderBoard[entryNum-1][2] = tempData[1];
					leaderBoard[entryNum-1][3] = tempData[2];
					leaderBoard[entryNum-1][4] = tempData[3];
					leaderBoard[entryNum-1][5] = tempData[4];
					leaderBoard[entryNum-1][6] = tempData[5];
				}
				catch (Exception e) {
				}
			}
			bufferReader.close();
			sortBy(6, leaderBoard);
		}
		//Prints error if try can't be run
		catch (IOException e) {
			e.printStackTrace();
		}
	}//end of inputFromFile method

	//actionPerformed method. test if buttons are pressed
	public void actionPerformed(ActionEvent e){
		//if sortSelected combobox is pressed.
		if(e.getActionCommand().equals("Sort Select")){
			//sort the JTable by the selected sort method
			sortBy(cbxSortSelect.getSelectedIndex()+2,leaderBoard);
		}
		//if menu button is pressed. open menu frame and close leaderBoard frame
		else if(e.getActionCommand().equals("Menu")){
			titlePage titlePageFrame = new titlePage();
			titlePageFrame.setVisible(true);
			audioClip.stop();
			this.dispose();
		}
		//if exit button is pressed. confirm, then exit
		else if(e.getActionCommand().equals("Exit")){
			int exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
			if(exit == 0)
				System.exit(0);
		}
		//if search type combobox is pressed. enable search button
		else if(e.getActionCommand().equals("Search Type")){
			if(cbxSearch.getSelectedIndex()!=-1)
				btnSearch.setEnabled(true);
		}
		//if search button is pressed. search through data to see if there are any matches. then display matches on JTable
		else if(e.getActionCommand().equals("Search")){
			btnSearch.setVisible(false);
			btnViewOriginal.setVisible(true);
			txtFldSearch.setEnabled(false);
			cbxSearch.setEnabled(false);
			search();
		}
		//if view orignal button is pressed. view the original data. previously read from the file
		else if(e.getActionCommand().equals("View Original")){
			btnSearch.setVisible(true);
			btnSearch.setEnabled(false);
			btnViewOriginal.setVisible(false);
			txtFldSearch.setEnabled(true);
			cbxSearch.setEnabled(true);
			txtFldSearch.setText("");
			sortBy(6, leaderBoard);
			cbxSearch.setSelectedIndex(-1);
		}
	}//end of actionPerformed method

	//resizeLeaderBoard method. resizes the array sent in as a parameter.
	private String[][] resizeLeaderBoard(String[][] leaderBoard, int newSize) {
		String tempString[][] = new String[newSize][8];
		for(int c = 0; c < (newSize-1); c++){
			for(int r = 0; r < 8;r ++){
				tempString[c][r] = leaderBoard[c][r];
			}
		}
		return tempString;
	}//end of resizeLeaderBoard method

	//sortBy method. sorts the inputed array depending on the method selected
	public void sortBy(int method, String[][] leaderBoardArray){
		//clear JTable for new data
		if(sortNum!=0)
			for(int i = 0; i < sortNum; i++){
				model.removeRow(0);
			}
		sortNum = 0;
		//sort data by selected method
		for (int i = 0; i < leaderBoardArray.length;i++){
			for (int k = 0; k < leaderBoardArray.length;k++){
				if (Integer.parseInt(leaderBoardArray[i][method]) > Integer.parseInt(leaderBoardArray[k][method])){
					String Temp1[] = new String[8];
					String Temp2[] = new String[8];
					for(int c = 0; c < leaderBoardArray[0].length;c++){
						Temp1[c] = leaderBoardArray[i][c];
						Temp2[c] = leaderBoardArray[k][c];
					}
					for(int c = 0; c < leaderBoardArray[0].length;c++){
						leaderBoardArray[k][c] = Temp1[c];
						leaderBoardArray[i][c] = Temp2[c];
					}
				}
			}
		}
		//display data on JTable
		for(int i = 0; i < leaderBoardArray.length; i++){
			leaderBoardArray[i][0] = String.valueOf(i+1);
			model.addRow(new Object[]{leaderBoardArray[i][0], leaderBoardArray[i][1], leaderBoardArray[i][2], leaderBoardArray[i][3], leaderBoardArray[i][4], leaderBoardArray[i][5], leaderBoardArray[i][6], leaderBoardArray[i][7]});
			sortNum++;
		}
	}//end of sortBy method

	//search method
	public void search(){
		searchString = new String[0][8];
		int tempEntryNum = 0;
		//searches through original data and take any matches into the searchString array
		for(int i = 0; i < leaderBoard.length; i++){
			if(leaderBoard[i][cbxSearch.getSelectedIndex()+1].equalsIgnoreCase(txtFldSearch.getText())){
				searchString = resizeLeaderBoard(searchString, tempEntryNum+1);
				for(int r = 0; r < leaderBoard[0].length; r++){
					searchString[tempEntryNum][r] = leaderBoard[i][r];
				}
				tempEntryNum++;
			}
		}
		//if there are results from the search. sort the searched data by the method chosen 
		if(tempEntryNum!=0){
			if(cbxSearch.getSelectedIndex()==0)
				sortBy(6, searchString);
			else
				sortBy(cbxSearch.getSelectedIndex()+1, searchString);
		}
		//if no results turn up. then inform user and show original data.
		else{
			JOptionPane.showMessageDialog(null, "There is nothing in the Leaderboards that matched your search.");
			btnViewOriginal.setVisible(false);
			btnSearch.setVisible(true);
			btnSearch.setEnabled(true);
			txtFldSearch.setText("");
			cbxSearch.setSelectedIndex(-1);
		}
	}//end of search method

	//displayLeaderBoard method. creates JTable and scroll pane
	public void displayLeaderBoard(){
		tableLeaderboard = new JTable(model){
			public boolean isCellEditable(int row,int column){
				return false;
			}
		};
		tableLeaderboard.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollPane = new JScrollPane(tableLeaderboard, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 95, 761, 430);
		contentPane.add(scrollPane);
	}//end of displayLeaderBoard method
}//end of class
