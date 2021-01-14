package stem.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

import javax.swing.Timer;

import stem.characters.*;
import stem.characters.Character;
import stem.graphics.*;

@SuppressWarnings("serial")
public class Image extends JFrame implements ActionListener, KeyListener, MouseListener{
	
	//1.2.0
	private final String release = "";
	
	Timer tm = new Timer(5, this);
	
	private final int frameX = 900, frameY = 900, zero = 0;
	private int fails;
	
	private JButton start, instructions, backtobegin, backtobegin2, bwarrior, bninja, bmage, levelselect, level1, level2, level3, level4, level5, level6;
	private JButton customwarrior, customninja, custommage;
	private JLabel instructionTitle, controls, charMoves, selectTitle, timer, gameLength, specialReady, showLevel, healthtxt, showhealth, background, releaseVersion;
	private ImageIcon warriorpic, ninjapic, magepic, brickwallpic;
	private JLabel warriorlabel, ninjalabel, magelabel, brickwalllabel;

	private ImageIcon playerPic, ninjaPic;
	private JLabel playerLabel, ninjaLabel;
	
	private ImageIcon lavabackground, startscreen, levelbackground, logo, arrowRight, arrowLeft, arrowUp, arrowDown, cheezguide;
	private JLabel lavabackgroundLabel, startscreenLabel, levelbackgroundLabel, logoLabel, arrowRightLabel, arrowLeftLabel, arrowUpLabel, arrowDownLabel, cheezguideLabel;
	
	private JLabel lvl2directions, lvl4directions, cheezguidetxt;
	
	private Rectangle leftPlayer, rightPlayer, topPlayer, bottomPlayer;
	private Graphics g;
	
	private Stopwatch lvl2Timer, gameTimer, specialCooldown, invisTimer; 
	private Stopwatch walledTimer, teleportedTimer, rocketTimerL, rocketTimer, rocketTimerR, bossbutton1CD, bossbutton2CD;
	private Stopwatch lvl2HelpTimer, lvl4HelpTimer;
	
	private final int song1min = 3, song1sec = 41;
	private Stopwatch song1Timer, jumpTimer, walkTimer;
	
	private Sounds theme;
	
	private Character player;
	private boolean gameStarted, activateCheat, isLevelOne, isLevelTwo, isLevelThree, isLevelFour, isLevelFive, isLevelSix;
	private boolean lvl1selected, lvl2selected, lvl3selected, lvl4selected, lvl5selected, lvl6selected;
	private boolean isJumping = false, pressedA = false, pressedD = false, doubleJump = false, canRight = false, canLeft = false, onGravity, menuON = false;
	private boolean isInvisible = false, isWalled = false, isTeleported = false;
	private boolean lvl3CheckpointOne = false, lvl3CheckpointTwo = false;
	private boolean[] onPlatform = new boolean[3];
	
	private double gravity = 0, ballgravity = 1;
	private double increment = 1;
	
	private int x = 20, y = 718, velX, velY;
	//-----------------------------------------------------------------
	private int FloorX1 = 0, FloorY1 = 420, FloorVelX1;
	private int FloorX2 = 0, FloorY2 = 615, FloorVelX2;
	private int FloorX3 = 850, FloorY3 = 616, FloorVelX3;
	private int obstacleX1 = 250, obstacleY1 = 120, obstacleVelY1;
	private int obstacleX2 = 313, obstacleY2 = 225, obstacleVelY2;
	private int obstacleX3 = 376, obstacleY3 = 120, obstacleVelY3;
	private int obstacleX4 = 439, obstacleY4 = 225, obstacleVelY4;
	private int obstacleX5 = 502, obstacleY5 = 120, obstacleVelY5;
	private int obstacleX6 = 565, obstacleY6 = 225, obstacleVelY6;
	private int obstacleX7 = 628, obstacleY7 = 120, obstacleVelY7;
	private int obstacleX8 = 691, obstacleY8 = 225, obstacleVelY8;
	private int obstacleX9 = 754, obstacleY9 = 120, obstacleVelY9;
	private int obstacleX10 = 817, obstacleY10 = 225, obstacleVelY10;
	//-----------------------------------------------------------------
	private int ballX = 0, ballY = 0, ballVelX, ballVelY;
	//-----------------------------------------------------------------
	private double floor3X1 = 38, floor3Y1 = 220, floorVel3Y1;
	//-----------------------------------------------------------------
	private int ufoX = 1100, ufoY = 130, ufoVelX, numRockets;
	private int healthlengthX = 500;
	
	
	private static boolean[] refrigerator = new boolean[12];
	
	public Image() {
		initStopwatches();
		
		tm.start();
		Assets.init();
		
		gameStarted = false;
		setTitle("Cheezy Escape");
		setLayout(null);
		setResizable(false);
		setSize(frameX,frameY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		initAllImages();
		initAllLabels();
		initAllButtons();
		//initCollisionOne();
		//initCollisionTwo();
		beginScreen();
		
		addKeyListener(this);
		addMouseListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		initMenu();
		gameTimer.start();
		lvl2Timer.start();
		//specialCooldown.start();
		
		
		theme = new Sounds("themesong.wav");
	}
	
	public void initStopwatches() {
		invisTimer = new Stopwatch();
		invisTimer.start();
		walledTimer = new Stopwatch();
		walledTimer.start();
		teleportedTimer = new Stopwatch();
		teleportedTimer.start();
		specialCooldown = new Stopwatch();
		specialCooldown.start();
		specialCooldown.setTimer(30);
		rocketTimer = new Stopwatch();
		rocketTimer.startmilli();
		rocketTimerL = new Stopwatch();
		rocketTimerL.startmilli();
		rocketTimerR = new Stopwatch();
		rocketTimerR.startmilli();
		bossbutton1CD = new Stopwatch();
		bossbutton1CD.start();
		bossbutton2CD = new Stopwatch();
		bossbutton2CD.start();
		song1Timer = new Stopwatch();
		song1Timer.start();
		lvl2HelpTimer = new Stopwatch();
		lvl2HelpTimer.start();
		lvl4HelpTimer = new Stopwatch();
		lvl4HelpTimer.start();
		jumpTimer = new Stopwatch();
		jumpTimer.startFASTER();
		walkTimer = new Stopwatch();
		walkTimer.startFASTER();
	}
	
	public void startGame(String className) {
		removeVisible();
		gameStarted = true;
		isLevelOne = true; isLevelTwo = false; isLevelThree = false; isLevelFour = false; isLevelFive = false; isLevelSix = false;
		getContentPane().setBackground(Color.CYAN);
		
		//gameTimer.start();
		brickwalllabel.setVisible(true);
		gameTimer.resetTimer();
		gameLength.setVisible(true);
		specialReady.setVisible(true);
		levelbackgroundLabel.setVisible(true);
		showLevel.setVisible(true);
		showLevel.setText("Level: 1");
		showLevel.repaint();
		
		arrowRightLabel.setBounds(650, 50, 128, 32);
		arrowRightLabel.repaint();
		arrowRightLabel.setVisible(true);
		
		if(className.toLowerCase().equals("warrior")) {
			player = new Warrior(className.toUpperCase());
			
			playerPic = new ImageIcon(Assets.warriorplayer);
			playerLabel = new JLabel(playerPic);
			playerLabel.setBounds(x, y, 32, 32);
		} else if(className.toLowerCase().equals("ninja")) {
			player = new Ninja(className.toUpperCase());
			
			playerPic = new ImageIcon(Assets.ninjaplayer);
			playerLabel = new JLabel(playerPic);
			playerLabel.setBounds(x, y, 32, 32);
		} else if(className.toLowerCase().equals("mage")) {
			player = new Mage(className.toUpperCase());
			
			playerPic = new ImageIcon(Assets.mageplayer);
			playerLabel = new JLabel(playerPic);
			playerLabel.setBounds(x, y, 32, 32);
		}
		levelbackgroundLabel.add(playerLabel);
		playerLabel.setVisible(true);
		
		initCollisionOne();
		for(int i = 0; i < lFloor.length; i++) {
			lFloor[i].setVisible(true);
		}
		//lFloor[15].setBounds(zero, zero, zero, zero);
		//lFloor[15].repaint();
		//levelbackgroundLabel.remove(lFloor[15]);
		doorLabel.setVisible(true);
		
		levelbackgroundLabel.add(returnToGame);
		levelbackgroundLabel.add(returnToStart);
	}
	
	public void startLevelTwo() {
		isLevelOne = false; isLevelTwo = true; isLevelThree = false; isLevelFour = false; isLevelFive = false; isLevelSix = false;
		//Sounds.stopSound(theme);
		//lvl2Timer.start();
		lvl2Timer.resetTimer();
		
		removeVisible();
		removeCollisionOne();
		//doorLabel.setVisible(true);
		timer.setVisible(true);
		showLevel.setVisible(true);
		showLevel.setText("Level: 2");
		showLevel.repaint();
		levelbackgroundLabel.setVisible(true);
		
		lvl2directions.setVisible(true);
		lvl2HelpTimer.resetTimer();
		
		doorLabel.setBounds(zero, zero, zero, zero);
		doorLabel.repaint();
		
		initCollisionTwo();
		for(int i = 0; i < lFloor2.length; i++) {
			lFloor2[i].setVisible(true);
		}
		levelbackgroundLabel.add(playerLabel);
		levelbackgroundLabel.add(returnToGame);
		levelbackgroundLabel.add(returnToStart);
	}
	
	public void startLevelThree() {
		isLevelOne = false; isLevelTwo = false; isLevelThree = true; isLevelFour = false; isLevelFive = false; isLevelSix = false;
		
		removeVisible();
		removeCollisionTwo();
		
		doorLabel.setBounds(900, 900, 0, 0);
		doorLabel.repaint();
		doorLabel.setVisible(false);
		
		showLevel.setVisible(true);
		showLevel.setText("Level: 3");
		showLevel.repaint();
		
		levelbackgroundLabel.setVisible(true);
		
		arrowRightLabel.setBounds(670, 35, 128, 32);
		arrowRightLabel.repaint();
		arrowRightLabel.setVisible(true);
		
		initCollisionThree();
		for(int i = 0; i < lFloor3.length; i++) {
			lFloor3[i].setVisible(true);
		}
		for(int i = 0; i < lKill3.length; i++) {
			lKill3[i].setVisible(true);
		}
		levelbackgroundLabel.add(playerLabel);
		levelbackgroundLabel.add(returnToGame);
		levelbackgroundLabel.add(returnToStart);
	}
	
	private void startLevelFour() {
		isLevelOne = false; isLevelTwo = false; isLevelThree = false; isLevelFour = true; isLevelFive = false; isLevelSix = false;
		
		removeVisible();
		removeCollisionThree();
		
		remove(fakedoorLabel);
		
		x = 434; y = 718;
		resetBoss();
		
		doorLabel.setBounds(900, 900, 0, 0);
		doorLabel.repaint();
		doorLabel.setVisible(false);
		
		showLevel.setVisible(true);
		showLevel.setText("Level: 4");
		showLevel.repaint();
		
		initCollisionFour();
		lavabackgroundLabel.setVisible(true);
		lavabackgroundLabel.add(playerLabel);
		lavabackgroundLabel.add(returnToGame);
		lavabackgroundLabel.add(returnToStart);
		lvl4directions.setVisible(true);
		lvl4HelpTimer.resetTimer();
		repaint();
		
		rocketTimer.resetTimer();
		rocketTimerL.resetTimer();
		rocketTimerR.resetTimer();
	}
	
	private JLabel menubackground;
	private JButton returnToGame, returnToStart;
	
	public void initMenu() {
		//menubackground = new JLabel(" ");
		//menubackground.setBounds(200, 200, 500, 500);
		//menubackground.setFont(new Font("Serif", Font.BOLD, 50));
		//menubackground.setBackground(Color.BLACK);
		//menubackground.setVisible(false);
		
		returnToGame = new JButton("Return to Game");
		returnToGame.setBounds(900, 900, 0, 0);
		returnToGame.setBackground(Color.BLACK);
		returnToGame.setForeground(Color.WHITE);
		returnToGame.setVisible(false);
		returnToGame.addActionListener(this);
		
		returnToStart = new JButton("Main Menu");
		returnToStart.setBounds(900, 900, 0, 0);
		returnToStart.setBackground(Color.BLACK);
		returnToStart.setForeground(Color.WHITE);
		returnToStart.setVisible(false);
		returnToStart.addActionListener(this);
		
		//add(menubackground);
		add(returnToGame);
		add(returnToStart);
	}
	
	public void menuScreen() {
		
	}
	
	public void initAllButtons() {
		start = new JButton("START");
		start.setBounds(350, 425, 200, 50);
		start.addActionListener(this);
		start.setFont(new Font("Arial", Font.PLAIN, 20));
		start.setBackground(Color.CYAN);
		
		backtobegin = new JButton("BACK");
		backtobegin.setBounds(670, 800, 200, 50);
		backtobegin.addActionListener(this);
		backtobegin.setFont(new Font("Arial", Font.PLAIN, 20));
		backtobegin.setBackground(Color.PINK);
		
		backtobegin2 = new JButton("BACK");
		backtobegin2.setBounds(670, 800, 200, 50);
		backtobegin2.addActionListener(this);
		backtobegin2.setFont(new Font("Arial", Font.PLAIN, 20));
		backtobegin2.setBackground(Color.PINK);
		
		instructions = new JButton("HOW TO PLAY");
		instructions.setBounds(350, 475, 200, 50);
		instructions.addActionListener(this);
		instructions.setFont(new Font("Arial", Font.PLAIN, 20));
		instructions.setBackground(Color.ORANGE);
		
		levelselect = new JButton("LEVELS");
		levelselect.setBounds(350, 525, 200, 50);
		levelselect.addActionListener(this);
		levelselect.setFont(new Font("Arial", Font.PLAIN, 20));
		levelselect.setBackground(Color.YELLOW);
		
		level1 = new JButton("LEVEL 1");
		level1.setBounds(150, 100, 200, 50);
		level1.addActionListener(this);
		level1.setFont(new Font("Arial", Font.PLAIN, 20));
		level1.setBackground(Color.GREEN);
		
		level2 = new JButton("LEVEL 2");
		level2.setBounds(150, 350, 200, 50);
		level2.addActionListener(this);
		level2.setFont(new Font("Arial", Font.PLAIN, 20));
		level2.setBackground(Color.GREEN);
		
		level3 = new JButton("LEVEL 3");
		level3.setBounds(150, 600, 200, 50);
		level3.addActionListener(this);
		level3.setFont(new Font("Arial", Font.PLAIN, 20));
		level3.setBackground(Color.GREEN);
		
		level4 = new JButton("LEVEL 4");
		level4.setBounds(500, 100, 200, 50);
		level4.addActionListener(this);
		level4.setFont(new Font("Arial", Font.PLAIN, 20));
		level4.setBackground(Color.GREEN);
		
		level5 = new JButton("LEVEL 5");
		level5.setBounds(500, 350, 200, 50);
		level5.addActionListener(this);
		level5.setFont(new Font("Arial", Font.PLAIN, 20));
		level5.setBackground(Color.RED);
		level5.setToolTipText("Currently Unavailable");
		
		level6 = new JButton("LEVEL 6");
		level6.setBounds(500, 600, 200, 50);
		level6.addActionListener(this);
		level6.setFont(new Font("Arial", Font.PLAIN, 20));
		level6.setBackground(Color.RED);
		level6.setToolTipText("Currently Unavailable");
		
		bwarrior = new JButton("WARRIOR");
		bwarrior.setBounds(100, 700, 200, 50);
		bwarrior.addActionListener(this);
		bwarrior.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ninjalabel.setVisible(false);
				magelabel.setVisible(false);
				warriorlabel.setVisible(true);
			}
		});
		bwarrior.setFont(new Font("Arial", Font.PLAIN, 20));
		bwarrior.setBackground(Color.GREEN);
		
		bninja = new JButton("NINJA");
		bninja.setBounds(350, 700, 200, 50);
		bninja.addActionListener(this);
		bninja.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ninjalabel.setVisible(true);
				magelabel.setVisible(false);
				warriorlabel.setVisible(false);
			}
		});
		bninja.setFont(new Font("Arial", Font.PLAIN, 20));
		bninja.setBackground(Color.GREEN);
		
		bmage = new JButton("WIZARD");
		bmage.setBounds(600, 700, 200, 50);
		bmage.addActionListener(this);
		bmage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ninjalabel.setVisible(false);
				magelabel.setVisible(true);
				warriorlabel.setVisible(false);
			}
		});
		bmage.setFont(new Font("Arial", Font.PLAIN, 20));
		bmage.setBackground(Color.GREEN);
		
		customwarrior = new JButton("WARRIOR");
		customwarrior.setBounds(100, 700, 200, 50);
		customwarrior.addActionListener(this);
		customwarrior.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ninjalabel.setVisible(false);
				magelabel.setVisible(false);
				warriorlabel.setVisible(true);
			}
		});
		customwarrior.setFont(new Font("Arial", Font.PLAIN, 20));
		customwarrior.setBackground(Color.GREEN);
		
		customninja = new JButton("NINJA");
		customninja.setBounds(350, 700, 200, 50);
		customninja.addActionListener(this);
		customninja.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ninjalabel.setVisible(true);
				magelabel.setVisible(false);
				warriorlabel.setVisible(false);
			}
		});
		customninja.setFont(new Font("Arial", Font.PLAIN, 20));
		customninja.setBackground(Color.GREEN);
		
		custommage = new JButton("WIZARD");
		custommage.setBounds(600, 700, 200, 50);
		custommage.addActionListener(this);
		custommage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ninjalabel.setVisible(false);
				magelabel.setVisible(true);
				warriorlabel.setVisible(false);
			}
		});
		custommage.setFont(new Font("Arial", Font.PLAIN, 20));
		custommage.setBackground(Color.GREEN);
		
		
		startscreenLabel.add(start);
		startscreenLabel.add(instructions);
		startscreenLabel.add(backtobegin);
		add(backtobegin2);
		startscreenLabel.add(levelselect);
		startscreenLabel.add(bwarrior);
		startscreenLabel.add(bmage);
		startscreenLabel.add(bninja);
		startscreenLabel.add(customwarrior);
		startscreenLabel.add(custommage);
		startscreenLabel.add(customninja);
		startscreenLabel.add(level1); startscreenLabel.add(level2); startscreenLabel.add(level3); startscreenLabel.add(level4); //startscreenLabel.add(level5); startscreenLabel.add(level6);
	}
	
	public void initAllLabels() {
		instructionTitle = new JLabel("INSTRUCTIONS");
		instructionTitle.setBounds(50, 50, 400, 60);
		instructionTitle.setFont(new Font("Serif", Font.BOLD, 50));
		instructionTitle.setForeground(Color.RED);
		
		controls = new JLabel("<html>The goal of the game is to complete each obstacle course and reach the escape door.<br/>Do not touch the yellow spikes or you will be sent to the last checkpoint (or the start). <br/>You are given three classes to choose from, each having unique abilities to help in your survival. <br/> <br/>WASD: controls the player <br/>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp*Holding W (Jump) will cause you to jump higher as compared to pressing it once<br/>Left Mouse Click: special move <br/>Escape: open menu<html>");
		controls.setBounds(50, 150, 800, 230);
		controls.setFont(new Font("Serif", Font.PLAIN, 21));
		controls.setForeground(Color.WHITE);
		
		charMoves = new JLabel("<html>Special Moves: <br/>Warrior: places a perimeter of walls around your character for three seconds <br/> <br/>Ninja: goes invisible for three seconds <br/> <br/>Wizard: teleports yourself 100 pixels towards the direction of your mouse for two seconds, then returns to your original location<html>");
		charMoves.setBounds(50, 400, 800, 250);
		charMoves.setFont(new Font("Serif", Font.PLAIN, 21));
		charMoves.setForeground(Color.WHITE);
		
		selectTitle = new JLabel("Select a Class");
		selectTitle.setBounds(20, 20, 400, 60);
		selectTitle.setFont(new Font("Serif", Font.BOLD, 50));
		selectTitle.setForeground(Color.CYAN);
		
		lvl2Timer = new Stopwatch();
		timer = new JLabel("Time Survived: " + lvl2Timer.secPassed() );
		timer.setBounds(700, 55, 200, 50);
		timer.setFont(new Font("Arial", Font.PLAIN, 20));
		timer.setForeground(Color.WHITE);
		
		gameTimer = new Stopwatch();
		gameLength = new JLabel("Total Game Length: " + gameTimer.secPassed());
		gameLength.setBounds(15, 55, 300, 50);
		gameLength.setFont(new Font("Arial", Font.ITALIC, 20));
		gameLength.setForeground(Color.GREEN);
		gameLength.setVisible(false);
		
		specialReady = new JLabel("Special Cooldown: ");
		specialReady.setFont(new Font("Arial", Font.BOLD, 20));
		specialReady.setBounds(630, 10, 300, 50);
		specialReady.setVisible(false);
		
		showLevel = new JLabel("Level: ");
		showLevel.setFont(new Font("Arial", Font.BOLD, 25));
		showLevel.setBounds(15, 10, 300, 50);
		showLevel.setForeground(Color.WHITE);
		showLevel.setVisible(false);
		
		releaseVersion = new JLabel(release);
		releaseVersion.setFont(new Font("Serif", Font.PLAIN, 17));
		releaseVersion.setBounds(790, 835, 100, 22);
		releaseVersion.setForeground(Color.WHITE);
		releaseVersion.setVisible(false);
		
		lvl2directions = new JLabel("<html>You must survive for one minute without getting hit by the Cheez!<br/>If you do, the timer resets and you must try again.<html>");
		lvl2directions.setFont(new Font("Serif", Font.PLAIN, 20));
		lvl2directions.setBounds(175, 150, 550, 100);
		lvl2directions.setForeground(Color.BLACK);
		lvl2directions.setVisible(false);
		levelbackgroundLabel.add(lvl2directions);
		
		lvl4directions = new JLabel("<html>Do not get hit by the hotdogs!<br/>Step on the buttons to launch the almighty Cheez every three seconds.<br/>Defeat the boss to win!<html>");
		lvl4directions.setFont(new Font("Serif", Font.PLAIN, 20));
		lvl4directions.setBounds(175, 350, 550, 100);
		lvl4directions.setForeground(Color.WHITE);
		lvl4directions.setVisible(false);
		lavabackgroundLabel.add(lvl4directions);
		
		cheezguidetxt = new JLabel("The Almighty Cheez");
		cheezguidetxt.setFont(new Font("Arial", Font.PLAIN, 17));
		cheezguidetxt.setBounds(255, 750, 150, 30);
		cheezguidetxt.setForeground(Color.yellow);
		cheezguidetxt.setVisible(false);
		add(cheezguidetxt);

		
		add(instructionTitle);
		add(controls);
		add(charMoves);
		startscreenLabel.add(selectTitle);
		brickwalllabel.add(timer);
		brickwalllabel.add(gameLength);
		brickwalllabel.add(specialReady);
		brickwalllabel.add(showLevel);
		startscreenLabel.add(releaseVersion);
	}
	
	public void initAllImages() {
		startscreen = new ImageIcon(getClass().getResource("startscreen.jpg"));
		startscreenLabel = new JLabel(startscreen);
		startscreenLabel.setBounds(0, 0, frameX, frameY);
		add(startscreenLabel);
		
		logo = new ImageIcon(getClass().getResource("gamelogo.png"));
		logoLabel = new JLabel(logo);
		logoLabel.setBounds(240, 50, 420, 300);
		startscreenLabel.add(logoLabel);
		
		lavabackground = new ImageIcon(getClass().getResource("bossgearbackground.jpg"));
		lavabackgroundLabel = new JLabel(lavabackground);
		lavabackgroundLabel.setBounds(0, 0, frameX, 750);
		lavabackgroundLabel.setVisible(false);
		add(lavabackgroundLabel);
		
		levelbackground = new ImageIcon(getClass().getResource("gearsbackground.png"));
		levelbackgroundLabel = new JLabel(levelbackground);
		levelbackgroundLabel.setBounds(0, 0, frameX, 750);
		levelbackgroundLabel.setVisible(false);
		add(levelbackgroundLabel);
		
		warriorpic = new ImageIcon(getClass().getResource("warrior.png"));
		warriorlabel = new JLabel(warriorpic);
		warriorlabel.setBounds(80, 50, 800, 620);
		
		ninjapic = new ImageIcon(getClass().getResource("ninja.png"));
		ninjalabel = new JLabel(ninjapic);
		ninjalabel.setBounds(50, 50, 800, 620);
		
		magepic = new ImageIcon(getClass().getResource("mage.png"));
		magelabel = new JLabel(magepic);
		magelabel.setBounds(50, 90, 850, 600);
		
		brickwallpic = new ImageIcon(getClass().getResource("brickfloor.jpg"));
		brickwalllabel = new JLabel(brickwallpic);
		brickwalllabel.setBounds(0, frameY-150, frameX, 150);
		brickwalllabel.setVisible(false);
		
		playerPic = new ImageIcon(Assets.player);
		playerLabel = new JLabel(playerPic);
		playerLabel.setBounds(x, y, 32, 32);
		
		ninjaPic = new ImageIcon(Assets.ninjaplayer);
		ninjaLabel = new JLabel(ninjaPic);
		ninjaLabel.setBounds(x, y, 32, 32);
		
		doorPic = new ImageIcon(Assets.door);
		doorLabel = new JLabel(doorPic);
		//doorLabel.setBounds(800, 36, 64, 64);
		doorLabel.setVisible(false);
		
		cheezguide = new ImageIcon(Assets.ball);
		cheezguideLabel = new JLabel(cheezguide);
		cheezguideLabel.setBounds(30,  735, 64, 64);
		cheezguideLabel.setVisible(false);
		cheezguideLabel.setToolTipText("Game Created By: Ethan Chee");
		add(cheezguideLabel);
		
		arrowLeft = new ImageIcon(Assets.arrowleft);
		arrowLeftLabel = new JLabel(arrowLeft);
		arrowLeftLabel.setVisible(false);
		add(arrowLeftLabel);
		
		arrowRight = new ImageIcon(Assets.arrowright);
		arrowRightLabel = new JLabel(arrowRight);
		arrowRightLabel.setVisible(false);
		add(arrowRightLabel);
		levelbackgroundLabel.add(arrowRightLabel);
		
		arrowUp = new ImageIcon(Assets.arrowup);
		arrowUpLabel = new JLabel(arrowUp);
		arrowUpLabel.setVisible(false);
		add(arrowUpLabel);
		
		arrowDown = new ImageIcon(Assets.arrowdown);
		arrowDownLabel = new JLabel(arrowDown);
		arrowDownLabel.setVisible(false);
		levelbackgroundLabel.add(arrowDownLabel);
		
		lvl4Door = new ImageIcon(Assets.door);
		lvl4DoorLabel = new JLabel(lvl4Door);
		lvl4DoorLabel.setBounds(zero, zero, zero, zero);
		lvl4DoorLabel.setVisible(false);
		lavabackgroundLabel.add(lvl4DoorLabel);
		
		levelbackgroundLabel.add(doorLabel);
		startscreenLabel.add(warriorlabel);
		startscreenLabel.add(ninjalabel);
		startscreenLabel.add(magelabel);
		add(brickwalllabel);
		//add(playerLabel);
	}
	
	
	public void setNinjaSpecial() {
		new Sounds("disappear.wav");
		x = frameX; y = frameY;
		playerLabel.setBounds(frameX, frameY, 0, 0);
		playerLabel.setVisible(false);
		playerLabel.repaint();
	}
	
	private ImageIcon[] warriorWall = new ImageIcon[3];
	private JLabel[] warriorWallLabel = new JLabel[3];
	public void setWarriorSpecial() {
		new Sounds("walls.wav");
		for(int i = 0; i < warriorWall.length; i++) {
			warriorWall[i] = new ImageIcon(getClass().getResource("warriorwall.jpg"));
			warriorWallLabel[i] = new JLabel(warriorWall[i]);
		}
		warriorWallLabel[0].setBounds(x-20, y-20, 72, 10);
		warriorWallLabel[1].setBounds(x-20, y-10, 10, 52);
		warriorWallLabel[2].setBounds(x+20+32, y-20, 10, 52);
		
		if(isLevelFour) {
			for(int i = 0; i < warriorWall.length; i++) {
				lavabackgroundLabel.add(warriorWallLabel[i]);
			}
		} else {
			for(int i = 0; i < warriorWall.length; i++) {
				levelbackgroundLabel.add(warriorWallLabel[i]);
			}
		}
		repaint();
	}
	
	private int tempMageX, tempMageY;
	//private int mouseX, mouseY;
	public void setMageSpecial(int mouseX, int mouseY) {
		new Sounds("teleport.wav");
		tempMageX = x;
		tempMageY = y;
		
		if(mouseX > tempMageX) {
			x += 100;
			if(x > frameX - 32) {
				playerLabel.setBounds(frameX - 32, tempMageY, 32, 32);
			} else {
				playerLabel.setBounds(x, tempMageY, 32, 32);
			}
			playerLabel.repaint();
		} else if(mouseX < tempMageX) {
			x -= 100;
			if(x < 0) {
				playerLabel.setBounds(0, tempMageY, 32, 32);
			} else {
				playerLabel.setBounds(x, tempMageY, 32, 32);
			}
			playerLabel.repaint();
		}
	}
	
	public void updatePlayerCollision() {
		leftPlayer = new Rectangle(playerLabel.getX(), playerLabel.getY()+3, 1, playerLabel.getHeight()-6);
		rightPlayer = new Rectangle(playerLabel.getX()+playerLabel.getWidth()-1, playerLabel.getY()+3, 1, playerLabel.getHeight()-6);
		topPlayer = new Rectangle(playerLabel.getX()+1, playerLabel.getY(), playerLabel.getWidth()-2, 1);
		bottomPlayer = new Rectangle(playerLabel.getX()+1, playerLabel.getY()+playerLabel.getHeight()-1, playerLabel.getWidth()-2, 1);	
	}
	
	public Rectangle getBoundsLeft() {
		return new Rectangle(playerLabel.getX(), playerLabel.getY()+1, 1, playerLabel.getHeight()-1);
	}
	
	public Rectangle getBoundsRight() {
		return new Rectangle(playerLabel.getX()+playerLabel.getWidth()-1, playerLabel.getY()+1, 1, playerLabel.getHeight()-1);
	}
	
	public Rectangle getBoundsTop() {
		return new Rectangle(playerLabel.getX()+1, playerLabel.getY(), playerLabel.getWidth()-2, 1);
	}
	
	public Rectangle getBoundsBottom() {
		return new Rectangle(playerLabel.getX()+1, playerLabel.getY()+playerLabel.getHeight()-1, playerLabel.getWidth()-2, 1);
	}
	
	////////////////////////////////////////////////////////
	private ImageIcon[] floor = new ImageIcon[27];
	private JLabel[] lFloor = new JLabel[27];
	private ImageIcon doorPic;
	private JLabel doorLabel;
	
	public void initCollisionOne() {
		
		//doorPic = new ImageIcon(Assets.door);
		//doorLabel = new JLabel(doorPic);
		doorLabel.setBounds(800, 36, 64, 64);
		//doorLabel.setVisible(false);
		//add(doorLabel);
		doorLabel.repaint();
		
		floor[0] = new ImageIcon(getClass().getResource("brickwall.jpg"));
		lFloor[0] = new JLabel(floor[0]);
		lFloor[0].setBounds(50, 650, 200, 20);
		lFloor[0].setVisible(false);
		
		floor[1] = new ImageIcon(getClass().getResource("brickwall.jpg"));                  //Floor 1
		lFloor[1] = new JLabel(floor[1]);
		lFloor[1].setBounds(350, 650, 200, 20);
		lFloor[1].setVisible(false);
		
		floor[2] = new ImageIcon(getClass().getResource("brickwall.jpg"));
		lFloor[2] = new JLabel(floor[2]);
		lFloor[2].setBounds(650, 650, 200, 20);
		lFloor[2].setVisible(false);
		
		/*----------------------------------------------------------------------------*/
		
		floor[3] = new ImageIcon(getClass().getResource("brickwall.jpg"));
		lFloor[3] = new JLabel(floor[3]);
		lFloor[3].setBounds(200, 520, 200, 20);
		lFloor[3].setVisible(false);
		
		floor[4] = new ImageIcon(getClass().getResource("brickwall.jpg"));                  //Floor 2 (no obstacles)
		lFloor[4] = new JLabel(floor[4]);
		lFloor[4].setBounds(500, 520, 200, 20);
		lFloor[4].setVisible(false);
		
		/*-----------------------------------------------------------------------------*/
		
		floor[5] = new ImageIcon(getClass().getResource("brickwall.jpg"));
		lFloor[5] = new JLabel(floor[5]);
		lFloor[5].setBounds(FloorX1, FloorY1, 150, 20);                                     //Moving Floor 1
		lFloor[5].setVisible(false);
		
		/*----------------------------------------------------------------------------*/
		
		floor[6] = new ImageIcon(getClass().getResource("brickwall.jpg"));
		lFloor[6] = new JLabel(floor[6]);
		lFloor[6].setBounds(FloorX2, FloorY2, 50, 20); 										//Obstacle 1
		lFloor[6].setVisible(false);
		
		/*-----------------------------------------------------------------------------*/
		
		floor[7] = new ImageIcon(getClass().getResource("brickwall.jpg"));
		lFloor[7] = new JLabel(floor[7]);
		lFloor[7].setBounds(FloorX3, FloorY3, 50, 20); 										//Obstacle 2
		lFloor[7].setVisible(false);
		
		/*------------------------------------------------------------------------------*/
		
		floor[8] = new ImageIcon(getClass().getResource("brickwall.jpg"));
		lFloor[8] = new JLabel(floor[8]);
		lFloor[8].setBounds(0, 330, 800, 20); 									
		lFloor[8].setVisible(false);
		
		floor[9] = new ImageIcon(getClass().getResource("brickwall.jpg"));
		lFloor[9] = new JLabel(floor[9]);
		lFloor[9].setBounds(0, 280, 20, 15); 									
		lFloor[9].setVisible(false);
		
		floor[10] = new ImageIcon(getClass().getResource("brickwall.jpg"));                 //small boxes
		lFloor[10] = new JLabel(floor[10]);
		lFloor[10].setBounds(78, 210, 18, 15); 									
		lFloor[10].setVisible(false);
		
		floor[11] = new ImageIcon(getClass().getResource("brickwall.jpg"));
		lFloor[11] = new JLabel(floor[11]);
		lFloor[11].setBounds(0, 160, 18, 13); 									
		lFloor[11].setVisible(false);
		
		floor[12] = new ImageIcon(getClass().getResource("brickwall.jpg"));
		lFloor[12] = new JLabel(floor[12]);
		lFloor[12].setBounds(96, 90, 15, 170); 									
		lFloor[12].setVisible(false);
		
		/*------------------------------------------------------------------------------*/
		
		floor[13] = new ImageIcon(getClass().getResource("brickwall.jpg"));
		lFloor[13] = new JLabel(floor[13]);
		lFloor[13].setBounds(96, 225, 900-96, 25); 									
		lFloor[13].setVisible(false);
		
		floor[14] = new ImageIcon(getClass().getResource("brickwall.jpg"));
		lFloor[14] = new JLabel(floor[14]);
		lFloor[14].setBounds(150, 170, 710, 20); 									
		lFloor[14].setVisible(false);
		
		floor[15] = new ImageIcon(getClass().getResource("brickwall.jpg"));
		lFloor[15] = new JLabel(floor[15]);
		lFloor[15].setBounds(150, 0, 15, 170); 									
		lFloor[15].setVisible(false);
		
		floor[16] = new ImageIcon(getClass().getResource("brickwall.jpg"));
		lFloor[16] = new JLabel(floor[16]);
		lFloor[16].setBounds(200, 100, 700, 20); 									
		lFloor[16].setVisible(false);
		
		/*-------------------------------------------------------------------------------*/
		
		floor[17] = new ImageIcon(Assets.spike);
		lFloor[17] = new JLabel(floor[17]);
		lFloor[17].setBounds(obstacleX1, obstacleY1, 15, 15); 									
		lFloor[17].setVisible(false);
		
		floor[18] = new ImageIcon(Assets.spike);
		lFloor[18] = new JLabel(floor[18]);
		lFloor[18].setBounds(obstacleX2, obstacleY2, 15, 15); 									
		lFloor[18].setVisible(false);
		
		floor[19] = new ImageIcon(Assets.spike);
		lFloor[19] = new JLabel(floor[19]);
		lFloor[19].setBounds(obstacleX3, obstacleY3, 15, 15); 									
		lFloor[19].setVisible(false);
		
		floor[20] = new ImageIcon(Assets.spike);
		lFloor[20] = new JLabel(floor[20]);
		lFloor[20].setBounds(obstacleX4, obstacleY4, 15, 15); 									
		lFloor[20].setVisible(false);
		
		floor[21] = new ImageIcon(Assets.spike);
		lFloor[21] = new JLabel(floor[21]);
		lFloor[21].setBounds(obstacleX5, obstacleY5, 15, 15); 									
		lFloor[21].setVisible(false);
		
		floor[22] = new ImageIcon(Assets.spike);
		lFloor[22] = new JLabel(floor[22]);
		lFloor[22].setBounds(obstacleX6, obstacleY6, 15, 15); 									
		lFloor[22].setVisible(false);
		
		floor[23] = new ImageIcon(Assets.spike);
		lFloor[23] = new JLabel(floor[23]);
		lFloor[23].setBounds(obstacleX7, obstacleY7, 15, 15); 									
		lFloor[23].setVisible(false);
		
		floor[24] = new ImageIcon(Assets.spike);
		lFloor[24] = new JLabel(floor[23]);
		lFloor[24].setBounds(obstacleX8, obstacleY8, 15, 15); 									
		lFloor[24].setVisible(false);
		
		floor[25] = new ImageIcon(Assets.spike);
		lFloor[25] = new JLabel(floor[25]);
		lFloor[25].setBounds(obstacleX9, obstacleY9, 15, 15); 									
		lFloor[25].setVisible(false);
		
		floor[26] = new ImageIcon(Assets.spike);
		lFloor[26] = new JLabel(floor[23]);
		lFloor[26].setBounds(obstacleX10, obstacleY10, 15, 15); 									
		lFloor[26].setVisible(false);
		
		/*----------------------------------------------------------------------*/
		
		
		for(int i = 0; i < lFloor.length; i++) {
			levelbackgroundLabel.add(lFloor[i]);
		}
	}
	
	public void removeCollisionOne() {
		levelbackgroundLabel.remove(playerLabel);
		for(int i = 0; i < lFloor.length; i++) {
			lFloor[i].setVisible(false);
			lFloor[i].setBounds(zero, zero, zero, zero);
			lFloor[i].repaint();
			levelbackgroundLabel.remove(lFloor[i]);
		}
		doorLabel.setVisible(false);
	}
	//////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////
	private ImageIcon[] floor2 = new ImageIcon[1];
	private JLabel[] lFloor2 = new JLabel[1];
	private boolean doorvisible;
	public void initCollisionTwo() {
		doorvisible = false;
		ballgravity = 1;
		
		floor2[0] = new ImageIcon(Assets.ball);
		lFloor2[0] = new JLabel(floor2[0]);
		lFloor2[0].setBounds(ballX, ballY, 64, 64);                            //ball
		lFloor2[0].setVisible(false);
		
		for(int i = 0; i < lFloor2.length; i++) {
			levelbackgroundLabel.add(lFloor2[i]);
		}
	}
	
	public void removeCollisionTwo() {
		levelbackgroundLabel.remove(playerLabel);
		for(int i = 0; i < lFloor2.length; i++) {
			lFloor2[0].setBounds(zero, zero, zero, zero);
			lFloor2[0].repaint();
			levelbackgroundLabel.remove(lFloor2[i]);
		}
	}
	///////////////////////////////////////////////////////////
	
	///////////////////////////////////////////////////////////
	private ImageIcon[] floor3 = new ImageIcon[40];
	private JLabel[] lFloor3 = new JLabel[40];
	private ImageIcon[] kill3 = new ImageIcon[20];
	private JLabel[] lKill3 = new JLabel[20];
	private ImageIcon fakedoor;
	private JLabel fakedoorLabel;
	public void initCollisionThree() {
		fakedoor = new ImageIcon(Assets.door);
		fakedoorLabel = new JLabel(fakedoor);
		fakedoorLabel.setBounds(820, 20, 64, 64);
		fakedoorLabel.setVisible(true);
		levelbackgroundLabel.add(fakedoorLabel);
		
		for(int i = 0; i < floor3.length; i++) {
			floor3[i] = new ImageIcon(getClass().getResource("brickwall.jpg"));
			lFloor3[i] = new JLabel(floor3[i]);
			lFloor3[i].setVisible(false);
		}
		
		lFloor3[0].setBounds(40, 650, 20, 20);		
		lFloor3[1].setBounds(120, 620, 20, 20);
		lFloor3[2].setBounds(190, 590, 20, 20);
		lFloor3[3].setBounds(248, 565, 40, 20);
		lFloor3[4].setBounds(350, 540, 20, 20);
		lFloor3[5].setBounds(420, 515, 20, 20);
		lFloor3[6].setBounds(490, 630, 20, 20);
		lFloor3[7].setBounds(560, 600, 20, 20);
		lFloor3[8].setBounds(630, 570, 20, 20);
		lFloor3[9].setBounds(700, 530, 20, 20);
		lFloor3[10].setBounds(770, 480, 20, 20);
		lFloor3[11].setBounds(785, 600, 115, 20);
		lFloor3[12].setBounds(885, 550, 15, 15);
		lFloor3[13].setBounds(840, 490, 15, 15);
		lFloor3[14].setBounds(885, 420, 15, 15);
		lFloor3[15].setBounds(840, 330, 15, 15);
		lFloor3[16].setBounds(760, 330, 20, 20);
		lFloor3[17].setBounds(680, 330, 20, 20);
		lFloor3[18].setBounds(600, 330, 20, 20);
		lFloor3[19].setBounds(520, 330, 20, 20);
		lFloor3[20].setBounds(440, 330, 20, 20);
		lFloor3[21].setBounds(360, 330, 20, 20);
		lFloor3[22].setBounds(280, 330, 20, 20);
		lFloor3[23].setBounds(200, 330, 20, 20);
		lFloor3[24].setBounds(120, 330, 20, 20);
		lFloor3[25].setBounds(0, 300, 10, 210);
		lFloor3[26].setBounds(90, 330, 10, 180);
		lFloor3[27].setBounds((int)floor3X1, (int)floor3Y1, 50, 20);
		lFloor3[28].setBounds(150, 150, 20, 20);
		lFloor3[29].setBounds(230, 150, 20, 20);
		lFloor3[30].setBounds(310, 150, 20, 20);
		lFloor3[31].setBounds(390, 150, 20, 20);
		lFloor3[32].setBounds(470, 150, 20, 20);
		lFloor3[33].setBounds(550, 150, 20, 20);
		lFloor3[34].setBounds(630, 150, 20, 20);
		lFloor3[35].setBounds(710, 150, 20, 20);
		lFloor3[36].setBounds(790, 150, 20, 20);
		
		for(int i = 0; i < lFloor3.length; i++) {
			levelbackgroundLabel.add(lFloor3[i]);
		}
		
		for(int i = 0; i < kill3.length; i++) {
			kill3[i] = new ImageIcon(Assets.spike);
			lKill3[i] = new JLabel(kill3[i]);
			lKill3[i].setVisible(false);
		}
		
		lKill3[0].setBounds(0, 530, 125, 20);
		lKill3[1].setBounds(220, 540, 13, 20);
		lKill3[2].setBounds(100, 738, 256, 20);
		lKill3[3].setBounds(356, 738, 256, 20);
		lKill3[4].setBounds(612, 738, 256, 20);
		lKill3[5].setBounds(868, 738, 32, 20);
		kill3[6] = new ImageIcon(Assets.spikevertical);
		lKill3[6] = new JLabel(kill3[6]);
		lKill3[6].setBounds(830, 450, 10, 100);
		kill3[7] = new ImageIcon(Assets.spikevertical);
		lKill3[7] = new JLabel(kill3[7]);
		lKill3[7].setBounds(830, 350, 10, 100);
		lKill3[8].setBounds(644, 177, 256, 20);
		lKill3[9].setBounds(388, 177, 256, 20);
		lKill3[10].setBounds(134, 177, 256, 20);
		lKill3[11].setBounds(10, 490, 80, 20);
		lKill3[12].setBounds(0, 0, 256, 20);
		lKill3[13].setBounds(256, 0, 256, 20);
		lKill3[14].setBounds(512, 0, 256, 20);
		lKill3[15].setBounds(768, 0, 132, 20);
		
		for(int i = 0; i < lKill3.length; i++) {
			levelbackgroundLabel.add(lKill3[i]);
		}
	}
	
	public void removeCollisionThree() {
		levelbackgroundLabel.remove(playerLabel);
		levelbackgroundLabel.remove(fakedoorLabel);
		for(int i = 0; i < lFloor3.length; i++) {
			levelbackgroundLabel.remove(lFloor3[i]);
		}
		for(int i = 0; i < lKill3.length; i++) {
			levelbackgroundLabel.remove(lKill3[i]);
		}
	}
	
	private ImageIcon ufo, ufohealth, healthframe, bossbutton1, bossbutton2, burger1, burger2, lvl4Door;
	private JLabel ufoLabel, ufohealthLabel, healthframeLabel, bossbutton1Label, bossbutton2Label, burger1Label, burger2Label, lvl4DoorLabel;
	private boolean burger1Shot, burger2Shot;
	private int burger1Y = 670, burger2Y = 670;
	private ImageIcon[] rocket = new ImageIcon[5];
	private JLabel[] rocketLabel = new JLabel[5];
	private ImageIcon[] rocketLeft = new ImageIcon[5];
	private JLabel[] rocketLeftLabel = new JLabel[5];
	private ImageIcon[] rocketRight = new ImageIcon[5];
	private JLabel[] rocketRightLabel = new JLabel[5];
	private int[] rocketX = new int[5], rocketY = new int[5];
	private int[] rocketLeftX = new int[5], rocketLeftY = new int[5];
	private int[] rocketRightX = new int[5], rocketRightY = new int[5];
	public void initCollisionFour() {
		ufo = new ImageIcon(getClass().getResource("ufo.png"));
		ufoLabel = new JLabel(ufo);
		ufoLabel.setBounds(ufoX, ufoY, 200 , 85);
		ufoLabel.setVisible(true);
		lavabackgroundLabel.add(ufoLabel);
		
		healthframe = new ImageIcon(getClass().getResource("whitebackground.jpg"));
		healthframeLabel = new JLabel(healthframe);
		healthframeLabel.setBounds(350, 10, 510, 50);
		lavabackgroundLabel.add(healthframeLabel);
		healthframeLabel.setVisible(true);
		
		showhealth = new JLabel(healthlengthX + " / 500");
		showhealth.setBounds(400, 10, 100, 30);
		showhealth.setFont(new Font("Serif", Font.BOLD, 20));
		showhealth.setForeground(Color.BLACK);
		healthframeLabel.add(showhealth);
		showhealth.setVisible(true);
		
		ufohealth = new ImageIcon(getClass().getResource("healthbar.png"));
		ufohealthLabel = new JLabel(ufohealth);
		ufohealthLabel.setBounds(5, 5, healthlengthX, 40);
		healthframeLabel.add(ufohealthLabel);
		ufohealthLabel.setVisible(true);
		
		bossbutton1 = new ImageIcon(Assets.button);
		bossbutton1Label = new JLabel(bossbutton1);
		bossbutton1Label.setBounds(100, 734, 64, 16);
		lavabackgroundLabel.add(bossbutton1Label);
		bossbutton1Label.setVisible(true);
		
		bossbutton2 = new ImageIcon(Assets.button);
		bossbutton2Label = new JLabel(bossbutton2);
		bossbutton2Label.setBounds(736, 734, 64, 16);
		lavabackgroundLabel.add(bossbutton2Label);
		bossbutton2Label.setVisible(true);
		
		/*
		healthtxt = new JLabel("Health:");
		healthtxt.setBounds(40, 10, 100, 50);
		healthtxt.setFont(new Font("Serif", Font.BOLD, 30));
		healthtxt.setForeground(Color.WHITE);
		lavabackgroundLabel.add(healthtxt);
		healthtxt.setVisible(true);	
		*/	
	}
	
	public void removeCollisionFour() {
		lavabackgroundLabel.remove(playerLabel);
		lavabackgroundLabel.remove(ufoLabel);
		healthframeLabel.remove(ufohealthLabel);
		lavabackgroundLabel.remove(healthframeLabel);
		//lavabackgroundLabel.remove(healthtxt);
		healthframeLabel.remove(showhealth);
		lavabackgroundLabel.remove(bossbutton1Label);
		lavabackgroundLabel.remove(bossbutton2Label);
	}
	
	public void beginScreen() {
		removeVisible();
		startscreenLabel.setVisible(true);
		logoLabel.setVisible(true);
		releaseVersion.setVisible(true);
		start.setVisible(true);
		instructions.setVisible(true);
		levelselect.setVisible(true);
	}
	
	public void instructScreen() {
		removeVisible();
		getContentPane().setBackground(Color.BLACK);
		backtobegin2.setVisible(true);
		instructionTitle.setVisible(true);
		controls.setVisible(true);
		charMoves.setVisible(true);
		
		cheezguideLabel.setVisible(true);
		cheezguidetxt.setVisible(true);
		arrowLeftLabel.setBounds(110, 750, 128, 32);
		arrowLeftLabel.repaint();
		arrowLeftLabel.setVisible(true);
	}
	
	public void selectScreen() {
		removeVisible();
		startscreenLabel.setVisible(true);
		bwarrior.setVisible(true);
		bninja.setVisible(true);
		bmage.setVisible(true);
		backtobegin.setVisible(true);
		selectTitle.setVisible(true);
	}
	
	public void customSelectScreen() {
		removeVisible();
		startscreenLabel.setVisible(true);
		selectTitle.setVisible(true);
		backtobegin.setVisible(true);
		customwarrior.setVisible(true);
		customninja.setVisible(true);
		custommage.setVisible(true);
	}
	
	public void levelScreen() {
		removeVisible();
		startscreenLabel.setVisible(true);
		backtobegin.setVisible(true);
		level1.setVisible(true);
		level2.setVisible(true);
		level3.setVisible(true);
		level4.setVisible(true);
		level5.setVisible(true);
		level6.setVisible(true);
	}
	
	private JLabel congrats, timeTook, numFails, confetti, balloon1, balloon2, thanks, cheez;
	public void endScreen() {
		isLevelOne = false; isLevelTwo = false; isLevelThree = false; isLevelFour = false; isLevelFive = false; isLevelSix = false;
		new Sounds("applause.wav");
		int minsTook = gameTimer.minPassed();
		int secsTook = gameTimer.secPassed();
		removeVisible();
		gameStarted = false;
		brickwalllabel.setVisible(false);
		gameLength.setVisible(false);
		specialReady.setVisible(false);
		getContentPane().setBackground(Color.WHITE);
		lavabackgroundLabel.remove(playerLabel);
		remove(playerLabel);
		
		congrats = new JLabel("Congratulations!", SwingConstants.CENTER);
		congrats.setBounds(0, 50, 900, 100);
		congrats.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 80));
		congrats.setForeground(Color.ORANGE);
		congrats.setVisible(true);
		add(congrats);
		
		timeTook = new JLabel("", SwingConstants.CENTER);
		if(secsTook < 10) {
			timeTook.setText("Completion Time: " + minsTook + ":0" + secsTook);
		} else {
			timeTook.setText("Completion Time: " + minsTook + ":" + secsTook);
		}
		timeTook.setBounds(0, 600, 900, 50);
		timeTook.setFont(new Font("Arial", Font.PLAIN + Font.BOLD, 40));
		timeTook.setForeground(Color.MAGENTA);
		timeTook.setVisible(true);
		add(timeTook);
		
		numFails = new JLabel("Fails: " + fails, SwingConstants.CENTER);
		numFails.setBounds(0, 700, 900, 50);
		numFails.setFont(new Font("Arial", Font.PLAIN + Font.BOLD, 40));
		numFails.setForeground(Color.MAGENTA);
		numFails.setVisible(true);
		add(numFails);
		
		thanks = new JLabel("Thanks for Playing!", SwingConstants.CENTER);
		thanks.setBounds(0, 825, 900, 50);
		thanks.setFont(new Font("Arial", Font.ITALIC, 20));
		thanks.setForeground(Color.BLUE);
		thanks.setVisible(true);
		add(thanks);
		
		confetti = new JLabel(new ImageIcon(getClass().getResource("confetti.png")));
		confetti.setBounds(0, 0, frameX, frameY);
		confetti.setVisible(true);
		//add(confetti);
		
		balloon1 = new JLabel(new ImageIcon(getClass().getResource("balloon.png")));
		balloon1.setBounds(10, 200, 300, 622);
		balloon1.setVisible(true);
		add(balloon1);
		
		balloon2 = new JLabel(new ImageIcon(getClass().getResource("balloon2.png")));
		balloon2.setBounds(frameX - 300 - 10, 200, 300, 622);
		balloon2.setVisible(true);
		add(balloon2);
		
		cheez = new JLabel(new ImageIcon(Assets.ball), SwingConstants.CENTER);
		cheez.setBounds(418, 418, 64, 64);
		cheez.setToolTipText("Created by: Ethan Chee");
		cheez.setVisible(true);
		add(cheez);
		
		repaint();
	}
	
	public void removeVisible() {
		if(start.isVisible()) { start.setVisible(false); }
		if(instructions.isVisible()) { instructions.setVisible(false); }
		if(backtobegin.isVisible()) { backtobegin.setVisible(false); }
		if(backtobegin2.isVisible()) { backtobegin2.setVisible(false); }
		if(instructionTitle.isVisible()) { instructionTitle.setVisible(false); }
		if(controls.isVisible()) { controls.setVisible(false); }
		if(charMoves.isVisible()) { charMoves.setVisible(false); }
		if(bwarrior.isVisible()) { bwarrior.setVisible(false); }
		if(bninja.isVisible()) { bninja.setVisible(false); }
		if(bmage.isVisible()) { bmage.setVisible(false); }
		if(selectTitle.isVisible()) { selectTitle.setVisible(false); }
		if(warriorlabel.isVisible()) { warriorlabel.setVisible(false); }
		if(ninjalabel.isVisible()) { ninjalabel.setVisible(false); }
		if(magelabel.isVisible()) { magelabel.setVisible(false); }
		if(levelselect.isVisible()) { levelselect.setVisible(false); }
		if(level1.isVisible()) { level1.setVisible(false); }
		if(level2.isVisible()) { level2.setVisible(false); }
		if(level3.isVisible()) { level3.setVisible(false); }
		if(level4.isVisible()) { level4.setVisible(false); }
		if(level5.isVisible()) { level5.setVisible(false); }
		if(level6.isVisible()) { level6.setVisible(false); }
		if(customwarrior.isVisible()) { customwarrior.setVisible(false); }
		if(custommage.isVisible()) { custommage.setVisible(false); }
		if(customninja.isVisible()) { customninja.setVisible(false); }
		if(timer.isVisible()) { timer.setVisible(false); }
		if(startscreenLabel.isVisible()) { startscreenLabel.setVisible(false); }
		if(logoLabel.isVisible()) { logoLabel.setVisible(false); }
		if(lavabackgroundLabel.isVisible()) { lavabackgroundLabel.setVisible(false); }
		if(levelbackgroundLabel.isVisible()) { levelbackgroundLabel.setVisible(false); }
		if(showLevel.isVisible()) { showLevel.setVisible(false); }
		if(releaseVersion.isVisible()) { releaseVersion.setVisible(false); }
		if(lvl2directions.isVisible()) { lvl2directions.setVisible(false); }
		if(lvl4directions.isVisible()) { lvl4directions.setVisible(false); }
		if(lvl4DoorLabel.isVisible()) { lvl4DoorLabel.setVisible(false); }
		if(cheezguideLabel.isVisible()) { cheezguideLabel.setVisible(false); }
		if(cheezguidetxt.isVisible()) { cheezguidetxt.setVisible(false); }
		if(arrowLeftLabel.isVisible()) { arrowLeftLabel.setVisible(false); }
		if(arrowRightLabel.isVisible()) { arrowRightLabel.setVisible(false); }
		if(arrowUpLabel.isVisible()) { arrowUpLabel.setVisible(false); }
		if(arrowDownLabel.isVisible()) { arrowDownLabel.setVisible(false); }
		//if(playerLabel.isVisible()) { playerLabel.setVisible(false); }
	}

	private boolean isCYAN = true;
	public void actionPerformed(ActionEvent e) {
		if(song1Timer.minPassed() == song1min && song1Timer.secPassed() == song1sec) {
			new Sounds("themesong.wav");
			song1Timer.resetTimer();
		}
		
		if(gameStarted) {
			if(testRefrigerator()) {
				System.out.println("CHEAT ACTIVATED");
				specialCooldown.setTimer(30);
			}
			
			System.out.println("X: " + x + "    Y: " + y);
			if(gameTimer.secPassed() < 10) {
				gameLength.setText("Total Game Length: " + gameTimer.minPassed() + ":0" + gameTimer.secPassed());
			} else {
				gameLength.setText("Total Game Length: " + gameTimer.minPassed() + ":" + gameTimer.secPassed());
			}
			gameLength.repaint();
			
			if(isInvisible && invisTimer.secPassed() >= 3) {
				playerLabel.setBounds(tempPlayerX, tempPlayerY, 32, 32);
				playerLabel.repaint();
				playerLabel.setVisible(true);
				isInvisible = false;
				x = tempPlayerX;
				y = tempPlayerY;
			} else if(isInvisible) {
				x = frameX; y = frameY;
			}
			
			if(isWalled && walledTimer.secPassed() == 3) {
				isWalled = false;
				for(int i = 0; i < warriorWallLabel.length; i++) {
					if(isLevelFour) {
						lavabackgroundLabel.remove(warriorWallLabel[i]);
					} else {
						levelbackgroundLabel.remove(warriorWallLabel[i]);
					}
				}
				repaint();
			}
			if(isWalled) {
				if(playerLabel.getBounds().intersects(warriorWallLabel[0].getBounds())) {
					velY = 0;
					isJumping = false;
				}
				if(playerLabel.getBounds().intersects(warriorWallLabel[1].getBounds())) {
					velX = 0;
					x+=1;
				}
				if(playerLabel.getBounds().intersects(warriorWallLabel[2].getBounds())) {
					velX = 0;
					x-=1;
				}
			}
			
			if(isTeleported && teleportedTimer.secPassed() >= 2) {
				x = tempMageX;
				y = tempMageY;
				playerLabel.setBounds(x, y, 32, 32);
				playerLabel.repaint();
				isTeleported = false;
			}
			
			if(specialCooldown.secPassed() >= 30 || specialCooldown.minPassed() >= 1) {
				specialReady.setText("Special Cooldown: READY");
				specialReady.setForeground(Color.GREEN);
			} else {
				int cooldown = 30 - specialCooldown.secPassed();
				if(cooldown >= 10) {
				specialReady.setText("Special Cooldown: 0:" + cooldown);
				} else {
					specialReady.setText("Special Cooldown: 0:0" + cooldown);
				}
				specialReady.setForeground(Color.PINK);
			}
			
			onGravity = true;
			updatePlayerCollision();
			/*
			if(isCYAN) {
				getContentPane().setBackground(Color.BLUE);
				isCYAN = false;
			} else {
				getContentPane().setBackground(Color.CYAN);
				isCYAN = true;
			}
			*/
			
			if(pressedA && !isInvisible) {
				velX = -1;
				if((walkTimer.secPassed() >= 1 || walkTimer.minPassed() >= 1) && !isJumping) {
					new Sounds("walking.wav");
					walkTimer.resetTimer();
				}
			} else if(pressedD && !isInvisible) {
				velX = 1;
				if((walkTimer.secPassed() >= 1 || walkTimer.minPassed() >= 1) && !isJumping) {
					new Sounds("walking.wav");
					walkTimer.resetTimer();
				}
			} else {
				velX = 0;
			}
			
			if(isJumping && !isInvisible) {
				if(doubleJump) {
					velY = -7;
				} else {
					velY = -5;
				}
			} else {
				velY = 0;
			}
			
			
			/*
			if(y<750-32) {
				gravity += 0.012 * increment;
				velY += gravity;
				increment += 0.8;
			} else {
				gravity = 0;
				increment = 1;
			}
			*/
			if(x<0) {
				velX = 0;
				x = 0;
			}
			if(x>868) {
				velX = 0;
				x = 868;
			}
			if(y<0) {
				velY = 0;
				y = 0;
			}
			
			////////////////////////////
			
			
			
			//----------------------------------------------------------
			levelOneObstacles();		
			//----------------------------------------------------------
			levelTwoObstacles();
			//----------------------------------------------------------
			levelThreeObstacles();
			//----------------------------------------------------------
			levelFourObstacles();
			//----------------------------------------------------------
			
			if(playerLabel.getBounds().intersects(brickwalllabel.getBounds())) {
				velY = 0;
				y = frameY - brickwalllabel.getHeight() - playerLabel.getHeight();
				gravity = 0;
				increment = 1;
				onGravity = false;
				jumpTimer.resetTimer();
			} else {
				onGravity = true;
			}
			
			if(isLevelOne) {
				if(playerLabel.getBounds().intersects(doorLabel.getBounds())) {
					startLevelTwo();
					x = 20; y = 718;
					new Sounds("enterdoor.wav");
				}
			} else if(isLevelTwo) {
				if(playerLabel.getBounds().intersects(doorLabel.getBounds())) {
					startLevelThree();
					x = 20; y = 718;
					new Sounds("enterdoor.wav");
				}
			}
			
			if(!isLevelThree) {
				lvl3CheckpointOne = false;
			}
			
			if(onGravity && !isInvisible) {
				gravity += 0.007 * increment;
				velY += gravity;
				increment += 0.8;
			}
			
			x = x + velX;
			y = y + velY;
			playerLabel.setBounds(x, y, 32, 32);
			playerLabel.repaint();
			
			velX = 0;
			velY = 0;
			
		}
		if(e.getSource() == start) {
			selectScreen();
		}
		else if(e.getSource() == instructions) {
			instructScreen();
		}
		else if(e.getSource() == backtobegin || e.getSource() == backtobegin2) {
			beginScreen();
		}
		else if(e.getSource() == bmage) {
			startGame("mage");
		}
		else if(e.getSource() == bninja) {
			startGame("ninja");
		}
		else if(e.getSource() == bwarrior) {
			startGame("warrior");
		}
		else if(e.getSource() == levelselect) {
			levelScreen();
		}
		else if(e.getSource() == level1) {
			lvl1selected = true;
			customSelectScreen();
		} 
		else if(e.getSource() == level2) {
			lvl2selected = true;
			customSelectScreen();
		}
		else if(e.getSource() == level3) {
			lvl3selected = true;
			customSelectScreen();
		}
		else if(e.getSource() == level4) {
			lvl4selected = true;
			customSelectScreen();
		}
		else if(e.getSource() == customwarrior) {
			startGame("warrior");
			if(lvl2selected) {
				startLevelTwo();
			} else if(lvl3selected) {
				startLevelTwo();
				startLevelThree();
			} else if(lvl4selected) {
				startLevelTwo();
				startLevelThree();
				startLevelFour();
			}
			lvl1selected = false; lvl2selected = false; lvl3selected = false; lvl4selected = false; lvl5selected = false; lvl6selected = false;
		}
		else if(e.getSource() == customninja) {
			startGame("ninja");
			if(lvl2selected) {
				startLevelTwo();
			} else if(lvl3selected) {
				startLevelTwo();
				startLevelThree();
			} else if(lvl4selected) {
				startLevelTwo();
				startLevelThree();
				startLevelFour();
			}
			lvl1selected = false; lvl2selected = false; lvl3selected = false; lvl4selected = false; lvl5selected = false; lvl6selected = false;
		}
		else if(e.getSource() == custommage) {
			startGame("mage");
			if(lvl2selected) {
				startLevelTwo();
			} else if(lvl3selected) {
				startLevelTwo();
				startLevelThree();
			} else if(lvl4selected) {
				startLevelTwo();
				startLevelThree();
				startLevelFour();
			}
			lvl1selected = false; lvl2selected = false; lvl3selected = false; lvl4selected = false; lvl5selected = false; lvl6selected = false;
		}
		else if(e.getSource() == returnToGame) {
			returnToGame.setBounds(900, 900, 0, 0);
			returnToGame.repaint();
			returnToStart.setBounds(900, 900, 0, 0);
			returnToStart.repaint();
			//menubackground.setVisible(false);
			returnToGame.setVisible(false);
			returnToStart.setVisible(false);
			menuON = false;
		}
		else if(e.getSource() == returnToStart) {
			gameStarted = false;
			if(isLevelOne) {
				removeCollisionOne();
			} else if(isLevelTwo) {
				removeCollisionTwo();
			} else if(isLevelThree) {
				removeCollisionThree();
			} else if(isLevelFour) {
				removeCollisionFour();
			}
			if(isWalled) {
				isWalled = false;
				for(int i = 0; i < warriorWallLabel.length; i++) {
					if(isLevelFour) {
						lavabackgroundLabel.remove(warriorWallLabel[i]);
					} else {
						levelbackgroundLabel.remove(warriorWallLabel[i]);
					}
				}
				repaint();
			}
			beginScreen();
			returnToGame.setBounds(900, 900, 0, 0);
			returnToGame.repaint();
			returnToStart.setBounds(900, 900, 0, 0);
			returnToStart.repaint();
			//menubackground.setVisible(false);
			returnToGame.setVisible(false);
			returnToStart.setVisible(false);
			menuON = false;
			brickwalllabel.setVisible(false);
			gameLength.setVisible(false);
			specialReady.setVisible(false);
			getContentPane().setBackground(Color.WHITE);
			remove(playerLabel);
			x = 20; y = 718; ballX = 0; ballY = 0;
			tempPlayerX = 20; tempPlayerY = 718;
			tempMageX = 20; tempMageY = 718;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
				
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(gameStarted) {
			if(e.getKeyCode() == KeyEvent.VK_A) {
				pressedA = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_D) {
				pressedD = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_SPACE) {
				isJumping = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				if(!menuON) {
					returnToGame.setBounds(10, 10, 150, 50);
					returnToGame.repaint();
					returnToStart.setBounds(170, 10, 150, 50);
					returnToStart.repaint();
					//menubackground.setVisible(true);
					returnToGame.setVisible(true);
					returnToStart.setVisible(true);
					menuON = true;
					if(isLevelTwo) {
						if(!lvl2directions.isVisible()) {
							lvl2directions.setVisible(true);
						}
					} else if(isLevelFour) {
						if(!lvl4directions.isVisible()) {
							lvl4directions.setVisible(true);
						}
					}
				} else if(menuON) {
					returnToGame.setBounds(900, 900, 0, 0);
					returnToGame.repaint();
					returnToStart.setBounds(900, 900, 0, 0);
					returnToStart.repaint();
					//menubackground.setVisible(false);
					returnToGame.setVisible(false);
					returnToStart.setVisible(false);
					menuON = false;
					if(isLevelTwo) {
						if(lvl2directions.isVisible() && lvl2HelpTimer.secPassed() >= 8) {
							lvl2directions.setVisible(false);
						}
					} else if(isLevelFour) {
						if(lvl4directions.isVisible() && lvl4HelpTimer.secPassed() >= 8) {
							lvl4directions.setVisible(false);
						}
					}
				}
				repaint();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_R && refrigerator[10]) {refrigerator[11] = true; }
			else if(e.getKeyCode() == KeyEvent.VK_O && refrigerator[9]) {refrigerator[10] = true; }
			else if(e.getKeyCode() == KeyEvent.VK_T && refrigerator[8]) {refrigerator[9] = true; }
			else if(e.getKeyCode() == KeyEvent.VK_A && refrigerator[7]) {refrigerator[8] = true; }
			else if(e.getKeyCode() == KeyEvent.VK_R && refrigerator[6]) {refrigerator[7] = true; }
			else if(e.getKeyCode() == KeyEvent.VK_E && refrigerator[5]) {refrigerator[6] = true; }
			else if(e.getKeyCode() == KeyEvent.VK_G && refrigerator[4]) {refrigerator[5] = true; }
			else if(e.getKeyCode() == KeyEvent.VK_I && refrigerator[3]) {refrigerator[4] = true; }
			else if(e.getKeyCode() == KeyEvent.VK_R && refrigerator[2]) {refrigerator[3] = true; }
			else if(e.getKeyCode() == KeyEvent.VK_F && refrigerator[1]) {refrigerator[2] = true; }
			else if(e.getKeyCode() == KeyEvent.VK_E && refrigerator[0]) {refrigerator[1] = true; }
			else if(e.getKeyCode() == KeyEvent.VK_R) {refrigerator[0] = true;}
			else {resetRefrigerator();}
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_A) {
			pressedA = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			pressedD = false;
		}
		
		if( (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_SPACE)) {
			isJumping = false;
		}

	}

	private void typedRefrigerator(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_R) {refrigerator[0] = true;} else {resetRefrigerator();}
		if(e.getKeyCode() == KeyEvent.VK_E && refrigerator[0]) {refrigerator[1] = true; } else {resetRefrigerator();}
		if(e.getKeyCode() == KeyEvent.VK_F && refrigerator[1]) {refrigerator[2] = true; } else {resetRefrigerator();}
		if(e.getKeyCode() == KeyEvent.VK_R && refrigerator[2]) {refrigerator[3] = true; } else {resetRefrigerator();}
		if(e.getKeyCode() == KeyEvent.VK_I && refrigerator[3]) {refrigerator[4] = true; } else {resetRefrigerator();}
		if(e.getKeyCode() == KeyEvent.VK_G && refrigerator[4]) {refrigerator[5] = true; } else {resetRefrigerator();}
		if(e.getKeyCode() == KeyEvent.VK_E && refrigerator[5]) {refrigerator[6] = true; } else {resetRefrigerator();}
		if(e.getKeyCode() == KeyEvent.VK_R && refrigerator[6]) {refrigerator[7] = true; } else {resetRefrigerator();}
		if(e.getKeyCode() == KeyEvent.VK_A && refrigerator[7]) {refrigerator[8] = true; } else {resetRefrigerator();}
		if(e.getKeyCode() == KeyEvent.VK_T && refrigerator[8]) {refrigerator[9] = true; } else {resetRefrigerator();}
		if(e.getKeyCode() == KeyEvent.VK_O && refrigerator[9]) {refrigerator[10] = true; } else {resetRefrigerator();}
		if(e.getKeyCode() == KeyEvent.VK_R && refrigerator[10]) {refrigerator[11] = true; } else {resetRefrigerator();}
	}
	private void resetRefrigerator() {
		for(int i = 0; i < refrigerator.length; i++) {
			refrigerator[i] = false;
		}
	}
	private static boolean testRefrigerator() {
		for(int i = 0; i < refrigerator.length; i++) {
			if(refrigerator[i] == false) {
				return false;
			}
		}
		return true;
	}
	
	private int tempPlayerX, tempPlayerY;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(gameStarted) {
			if(e.getButton() == MouseEvent.BUTTON1 && (specialCooldown.secPassed() >= 30 || specialCooldown.minPassed() >= 1)) {
				if(player.getMove().equals("invisible") && !isInvisible) {
					tempPlayerX = x;
					tempPlayerY = y;
					setNinjaSpecial();
					isInvisible = true;
					invisTimer.resetTimer();
					specialCooldown.resetTimer();
				} else if (player.getMove().equals("wall") && !isWalled) {
					setWarriorSpecial();
					isWalled = true;
					walledTimer.resetTimer();
					specialCooldown.resetTimer();
				} else if(player.getMove().equals("teleport") && !isTeleported) {
					setMageSpecial(e.getX(), e.getY());
					isTeleported = true;
					teleportedTimer.resetTimer();
					specialCooldown.resetTimer();
				}
			}
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void levelOneObstacles() {
		if(isLevelOne) {
			
			if(bottomPlayer.getY() + 1 == lFloor[5].getY()) {
				if(FloorVelX1 == 1 && pressedA) {
					velX = 0;
				} else if(FloorVelX1 == 1 && pressedD) {
					velX = 2;
				} else if(FloorVelX1 == 1){
					velX = 1;
				} else if(FloorVelX1 == -1 && pressedA) {
					velX = -2;
				} else if(FloorVelX1 == -1 && pressedD) {
					velX = 0;
				} else if(FloorVelX1 == -1){
					velX = -1;
				}
			}
			if(bottomPlayer.getY() + 1 == lFloor[6].getY()) {
				if(FloorVelX2 == 1 && pressedA) {
					velX = 0;
				} else if(FloorVelX2 == 1 && pressedD) {
					velX = 2;
				} else if(FloorVelX2 == 1){
					velX = 1;
				} else if(FloorVelX2 == -1 && pressedA) {
					velX = -2;
				} else if(FloorVelX2 == -1 && pressedD) {
					velX = 0;
				} else if(FloorVelX2 == -1){
					velX = -1;
				}
			}
			if(bottomPlayer.getY() + 1 == lFloor[7].getY()) {
				if(FloorVelX3 == 1 && pressedA) {
					velX = 0;
				} else if(FloorVelX3 == 1 && pressedD) {
					velX = 2;
				} else if(FloorVelX3 == 1){
					velX = 1;
				} else if(FloorVelX3 == -1 && pressedA) {
					velX = -2;
				} else if(FloorVelX3 == -1 && pressedD) {
					velX = 0;
				} else if(FloorVelX3 == -1){
					velX = -1;
				}
			}
			
			
			for(int i = 0; i < lFloor.length; i++) {
				if(leftPlayer.getBounds().intersects(lFloor[i].getBounds())) {
					velX = 0;
					x += 1;
					canLeft = false;
				} else {
					canLeft = true;
				}
				if(rightPlayer.getBounds().intersects(lFloor[i].getBounds())) {
					velX = 0;
					x -= 1;
					canRight = false;
				} else {
					canRight = true;
				}
				if(topPlayer.getBounds().intersects(lFloor[i].getBounds())) {
					velY = 0;
				}
				if(bottomPlayer.getBounds().intersects(lFloor[i].getBounds())) {
					velY = 0;
					gravity = 0;
					increment = 1;
					y -= 1;
					onGravity = false;
				} else if(bottomPlayer.getBounds().intersects(brickwalllabel.getBounds())) {
					velY = 0;
					gravity = 0;
					increment = 1;
					onGravity = false;
				}
				else {
					onGravity = true;
				}
			}
			
			/*//////////////////////////////
			
			if(leftPlayer.getBounds().intersects(lFloor[6].getBounds())) {
				velX = 0;
				x += 1;
				canLeft = false;
			} else {
				canLeft = true;
			}
			if(rightPlayer.getBounds().intersects(lFloor[6].getBounds())) {
				velX = 0;
				x -= 1;
				canRight = false;
			} else {
				canRight = true;
			}
			if(topPlayer.getBounds().intersects(lFloor[6].getBounds())) {
				velY = 0;
				y += 1;
			}
			if(bottomPlayer.getBounds().intersects(lFloor[6].getBounds())) {
				velY = 0;
				gravity = 0;
				increment = 1;
				y -= 1;
				onGravity = false;
			} else if(bottomPlayer.getBounds().intersects(brickwalllabel.getBounds())) {
				velY = 0;
				gravity = 0;
				increment = 1;
				onGravity = false;
			}
			else {
				onGravity = true;
			}
			
			//---
			
			if(leftPlayer.getBounds().intersects(lFloor[7].getBounds())) {
				velX = 0;
				x += 1;
				canLeft = false;
			} else {
				canLeft = true;
			}
			if(rightPlayer.getBounds().intersects(lFloor[7].getBounds())) {
				velX = 0;
				x -= 1;
				canRight = false;
			} else {
				canRight = true;
			}
			if(topPlayer.getBounds().intersects(lFloor[7].getBounds())) {
				velY = 0;
				y += 1;
			}
			if(bottomPlayer.getBounds().intersects(lFloor[7].getBounds())) {
				velY = 0;
				gravity = 0;
				increment = 1;
				y -= 1;
				onGravity = false;
			} else if(bottomPlayer.getBounds().intersects(brickwalllabel.getBounds())) {
				velY = 0;
				gravity = 0;
				increment = 1;
				onGravity = false;
			}
			else {
				onGravity = true;
			}
			*/
			
			if(leftPlayer.getBounds().intersects(lFloor[6].getBounds()) && rightPlayer.getBounds().intersects(lFloor[7].getBounds())) {
				x = 20;
				y = 718;
				fails++; new Sounds("hurt.wav");
			} else if(rightPlayer.getBounds().intersects(lFloor[6].getBounds()) && leftPlayer.getBounds().intersects(lFloor[7].getBounds())) {
				x = 20;
				y = 718;
				fails++; new Sounds("hurt.wav");
			}
			
			for(int d = 17; d < 27; d++) {
				if(playerLabel.getBounds().intersects(lFloor[d].getBounds())) {
					x = 120;
					y = 100;
					fails++; new Sounds("hurt.wav");
				}
			}
			
			if(FloorX1 <= 0) {
				FloorVelX1 = 1;
			} else if(FloorX1+150 >= frameX) {
				FloorVelX1 = -1;
			}
			
			FloorX1 += FloorVelX1;
			
			lFloor[5].setBounds(FloorX1, FloorY1, 150, 20);
			lFloor[5].repaint();
			
			//----------------------------------------------------------
			
			if(FloorX2 <= 0) {
				FloorVelX2 = 1;
			} else if(FloorX2+50 >= frameX) {
				FloorVelX2 = -1;
			}
			
			FloorX2 += FloorVelX2;
			
			lFloor[6].setBounds(FloorX2, FloorY2, 50, 20);
			lFloor[6].repaint();
			//
			
			if(FloorX3 <= 0) {
				FloorVelX3 = 1;
			} else if(FloorX3+50 >= frameX) {
				FloorVelX3 = -1;
			}
			
			FloorX3 += FloorVelX3;
			
			lFloor[7].setBounds(FloorX3, FloorY3, 50, 20);
			lFloor[7].repaint();
			//
			
			if(obstacleY1 <= 120) {
				obstacleVelY1 = 1;
			} else if(obstacleY1 >= 225) {
				obstacleVelY1 = -1;
			}
			
			obstacleY1 += obstacleVelY1;
			
			lFloor[17].setBounds(obstacleX1, obstacleY1, 15, 15);
			lFloor[17].repaint();
			//
			
			if(obstacleY2 <= 120) {
				obstacleVelY2 = 1;
			} else if(obstacleY2 >= 225) {
				obstacleVelY2 = -1;
			}
			
			obstacleY2 += obstacleVelY2;
			
			lFloor[18].setBounds(obstacleX2, obstacleY2, 15, 15);
			lFloor[18].repaint();
			//
			
			if(obstacleY3 <= 120) {
				obstacleVelY3 = 1;
			} else if(obstacleY3 >= 225) {
				obstacleVelY3 = -1;
			}
			
			obstacleY3 += obstacleVelY3;
			
			lFloor[19].setBounds(obstacleX3, obstacleY3, 15, 15);
			lFloor[19].repaint();
			//
			
			if(obstacleY4 <= 120) {
				obstacleVelY4 = 1;
			} else if(obstacleY4 >= 225) {
				obstacleVelY4 = -1;
			}
			
			obstacleY4 += obstacleVelY4;
			
			lFloor[20].setBounds(obstacleX4, obstacleY4, 15, 15);
			lFloor[20].repaint();
			//
			
			if(obstacleY5 <= 120) {
				obstacleVelY5 = 1;
			} else if(obstacleY5 >= 225) {
				obstacleVelY5 = -1;
			}
			
			obstacleY5 += obstacleVelY5;
			
			lFloor[21].setBounds(obstacleX5, obstacleY5, 15, 15);
			lFloor[21].repaint();
			//
			
			if(obstacleY6 <= 120) {
				obstacleVelY6 = 1;
			} else if(obstacleY6 >= 225) {
				obstacleVelY6 = -1;
			}
			
			obstacleY6 += obstacleVelY6;
			
			lFloor[22].setBounds(obstacleX6, obstacleY6, 15, 15);
			lFloor[22].repaint();
			//
			
			if(obstacleY7 <= 120) {
				obstacleVelY7 = 1;
			} else if(obstacleY7 >= 225) {
				obstacleVelY7 = -1;
			}
			
			obstacleY7 += obstacleVelY7;
			
			lFloor[23].setBounds(obstacleX7, obstacleY7, 15, 15);
			lFloor[23].repaint();
			
			//
			
			if(obstacleY8 <= 120) {
				obstacleVelY8 = 1;
			} else if(obstacleY8 >= 225) {
				obstacleVelY8 = -1;
			}
			
			obstacleY8 += obstacleVelY8;
			
			lFloor[24].setBounds(obstacleX8, obstacleY8, 15, 15);
			lFloor[24].repaint();
			//
			
			if(obstacleY9 <= 120) {
				obstacleVelY9 = 1;
			} else if(obstacleY9 >= 225) {
				obstacleVelY9 = -1;
			}
			
			obstacleY9 += obstacleVelY9;
			
			lFloor[25].setBounds(obstacleX9, obstacleY9, 15, 15);
			lFloor[25].repaint();
			//
			
			if(obstacleY10 <= 120) {
				obstacleVelY10 = 1;
			} else if(obstacleY10 >= 225) {
				obstacleVelY10 = -1;
			}
			
			obstacleY10 += obstacleVelY10;
			
			lFloor[26].setBounds(obstacleX10, obstacleY10, 15, 15);
			lFloor[26].repaint();
			}
	}
	
	public void levelTwoObstacles() {
		if(isLevelTwo) {		
			
			if(lvl2directions.isVisible() && lvl2HelpTimer.secPassed() == 8) {
				lvl2directions.setVisible(false);
			}
			
			if(ballX <= 0) {
				ballVelX = (int)(Math.random() * 2) + 1;
			} else if(ballX >= frameX-64) {
				ballVelX = -((int)(Math.random() * 2) + 1);
			}
			if(ballY <= 0) {
				ballVelY = (int)(Math.random() * 2) + 1;
			} else if(ballY >= frameY-150-64) {
				ballVelY = -((int)(Math.random() * 2) + 1);
				double randomIncrement = Math.random() * 0.40;
				ballgravity += randomIncrement;
			}
			
			if(isWalled) {
				if(lFloor2[0].getBounds().intersects(warriorWallLabel[2].getBounds())) {
					ballVelX = 1;
				} else if(lFloor2[0].getBounds().intersects(warriorWallLabel[1].getBounds())) {
					ballVelX = -1;
				} else if(lFloor2[0].getBounds().intersects(warriorWallLabel[0].getBounds())) {
					ballVelY = -1;
				}
			}
			ballX += (int) (ballVelX*ballgravity*1.3);
			ballY += (int) (ballVelY*ballgravity);
			lFloor2[0].setBounds(ballX, ballY, 64, 64);
			lFloor2[0].repaint();
			
			if(lvl2Timer.secPassed() < 10) {
				timer.setText("Time Survived: " + lvl2Timer.minPassed() + ":0" + lvl2Timer.secPassed());
			} else {
				timer.setText("Time Survived: " + lvl2Timer.minPassed() + ":" + lvl2Timer.secPassed());
			}
			timer.repaint();
			
			if(lvl2Timer.minPassed() == 1 && !doorvisible) {
				int doorlocation = (int)(Math.random() * 800) + 1;
				doorLabel.setBounds(doorlocation, 750-64, 64, 64);
				doorLabel.repaint();
				doorLabel.setVisible(true);
				doorvisible = true;
			}
			
			if(playerLabel.getBounds().intersects(lFloor2[0].getBounds()) && !isInvisible) {
				lvl2Timer.resetTimer();
				ballX = 0; ballY = 0; ballgravity = 1;
				x = 20; y = 718;
				playerLabel.setBounds(x, y, 32, 32);
				playerLabel.repaint();
				fails++; new Sounds("hurt.wav");
			}
		}
	}

	public void levelThreeObstacles() {
		if(isLevelThree) {
			
			if(floor3Y1 <= 45) {
				floorVel3Y1 = 0.5;
			} else if(floor3Y1 >= 220) {
				floorVel3Y1 = -0.5;
			}
			floor3Y1 += floorVel3Y1;
			lFloor3[27].setBounds((int)floor3X1, (int)floor3Y1, 50, 20);
			lFloor3[27].repaint();
			
			if(playerLabel.getBounds().intersects(fakedoorLabel.getBounds())) {
				fakedoorLabel.setBounds(900, 900, 0, 0);
				fakedoorLabel.repaint();
				fakedoorLabel.setVisible(false);
				
				doorLabel.setBounds(18, 420, 64, 64);
				doorLabel.repaint();
				doorLabel.setVisible(true);
				
				arrowRightLabel.setVisible(false);
				arrowDownLabel.setBounds(32, 280, 32, 128);
				arrowDownLabel.repaint();
				arrowDownLabel.setVisible(true);
			}
			
			for(int i = 0; i < lFloor3.length; i++) {
				if(leftPlayer.getBounds().intersects(lFloor3[i].getBounds())) {
					velX = 0;
					x += 1;
					canLeft = false;
				} else {
					canLeft = true;
				}
				if(rightPlayer.getBounds().intersects(lFloor3[i].getBounds())) {
					velX = 0;
					x -= 1;
					canRight = false;
				} else {
					canRight = true;
				}
				if(topPlayer.getBounds().intersects(lFloor3[i].getBounds())) {
					velY = 0;
				}
				if(bottomPlayer.getBounds().intersects(lFloor3[i].getBounds())) {
					velY = 0;
					gravity = 0;
					increment = 1;
					y -= 1;
					onGravity = false;
				} else if(bottomPlayer.getBounds().intersects(brickwalllabel.getBounds())) {
					velY = 0;
					gravity = 0;
					increment = 1;
					onGravity = false;
				}
				else {
					onGravity = true;
				}
				if(bottomPlayer.getBounds().intersects(lFloor3[27].getBounds())) {
					
				}
				
			}
			
			if(bottomPlayer.getBounds().intersects(lFloor3[11].getBounds())) {
				lvl3CheckpointOne = true;
			}
			
			for(int i = 0; i < lKill3.length; i++) {
				if(playerLabel.getBounds().intersects(lKill3[i].getBounds())) {
					if(lvl3CheckpointTwo) {
						
					} else if(lvl3CheckpointOne) {
						x = 794;
						y = 568;
					} else {
						x = 20;
						y = 718;
					}
					fails++; new Sounds("hurt.wav");
				}
				/*
				if(leftPlayer.getBounds().intersects(lKill3[i].getBounds())) {
					velX = 0;
					x += 1;
					canLeft = false;
				} else {
					canLeft = true;
				}
				if(rightPlayer.getBounds().intersects(lKill3[i].getBounds())) {
					velX = 0;
					x -= 1;
					canRight = false;
				} else {
					canRight = true;
				}
				if(topPlayer.getBounds().intersects(lKill3[i].getBounds())) {
					velY = 0;
				}
				if(bottomPlayer.getBounds().intersects(lKill3[i].getBounds())) {
					velY = 0;
					gravity = 0;
					increment = 1;
					y -= 1;
					onGravity = false;
				} else if(bottomPlayer.getBounds().intersects(brickwalllabel.getBounds())) {
					velY = 0;
					gravity = 0;
					increment = 1;
					onGravity = false;
				}
				else {
					onGravity = true;
				}
				*/
			}
			
			if(playerLabel.getBounds().intersects(doorLabel.getBounds())) {
				startLevelFour();
				x = 20;
				y = 718;
				new Sounds("enterdoor.wav");
			}
		}
	}
	
	public void levelFourObstacles() {
		if(isLevelFour) {
			
			if(lvl4directions.isVisible() && lvl4HelpTimer.secPassed() == 8) {
				lvl4directions.setVisible(false);
			}
			
			if(ufoX <= -50) {
				ufoVelX = 1;
			} else if(ufoX >= frameX-150) {
				ufoVelX = -1;
			} 
			ufoX += ufoVelX;
			ufoLabel.setBounds(ufoX, ufoY, 200, 85);
			ufoLabel.repaint();
			
			ufohealthLabel.setBounds(5, 5, healthlengthX, 40);
			ufohealthLabel.repaint();
			
			showhealth.setText(healthlengthX + " / 500");
			showhealth.repaint();
			
			if(healthlengthX > 0) {
			for(int i = 0; i < rocketLabel.length; i++) {
				if(rocketLabel[i] == null && rocketTimer.secPassed() >= 1) {
					rocketTimer.resetTimer();
					rocket[i] = new ImageIcon(Assets.hotdog);
					rocketLabel[i] = new JLabel(rocket[i]);
					rocketX[i] = ufoLabel.getX() + 100;
					rocketY[i] = ufoLabel.getY() + 90;
					rocketLabel[i].setBounds(rocketX[i], rocketY[i] + 90, 32, 96);
					lavabackgroundLabel.add(rocketLabel[i]);
					new Sounds("pew.wav");
				}
			}
			for(int i = 0; i < rocketLabel.length; i++) {
				if(rocketLabel[i] != null) {
					rocketY[i] += 4;
					if(healthlengthX <= 150) rocketY[i] += 2;
					rocketLabel[i].setBounds(rocketX[i], rocketY[i], 32, 96);
					rocketLabel[i].repaint();
					if(isWalled) {
						for(int wall = 0; wall < warriorWallLabel.length; wall++) {
							if(warriorWallLabel[wall].getBounds().intersects(rocketLabel[i].getBounds())) {
								lavabackgroundLabel.remove(rocketLabel[i]);
								rocketLabel[i] = null;
								break;
							}
						}	
					} else if(rocketLabel[i].getY() >= 750) {
						lavabackgroundLabel.remove(rocketLabel[i]);
						rocketLabel[i] = null;
					} else if(playerLabel.getBounds().intersects(rocketLabel[i].getBounds())) {
						x = 434;
						y = 718;
						ufoX = 1100;
						resetBoss();
						fails++; new Sounds("hurt.wav");
					} 
				}
			}
			
			for(int i = 0; i < rocketLeftLabel.length; i++) {
				if(rocketLeftLabel[i] == null && rocketTimerL.secPassed() >= 2) {
					rocketTimerL.resetTimer();
					rocketLeft[i] = new ImageIcon(Assets.hotdog);
					rocketLeftLabel[i] = new JLabel(rocketLeft[i]);
					rocketLeftX[i] = ufoLabel.getX() + 10;
					rocketLeftY[i] = ufoLabel.getY() + 90;
					rocketLeftLabel[i].setBounds(rocketLeftX[i], rocketLeftY[i] + 90, 32, 96);
					lavabackgroundLabel.add(rocketLeftLabel[i]);
					new Sounds("pew.wav");
				}
			}
			for(int i = 0; i < rocketLeftLabel.length; i++) {
				if(rocketLeftLabel[i] != null) {
					rocketLeftX[i] -= 1;
					rocketLeftY[i] += 2;
					if(healthlengthX <= 150) rocketLeftY[i] += 2;
					rocketLeftLabel[i].setBounds(rocketLeftX[i], rocketLeftY[i], 32, 96);
					rocketLeftLabel[i].repaint();
					if(isWalled) {
						for(int wall = 0; wall < warriorWallLabel.length; wall++) {
							if(warriorWallLabel[wall].getBounds().intersects(rocketLeftLabel[i].getBounds())) {
								lavabackgroundLabel.remove(rocketLeftLabel[i]);
								rocketLeftLabel[i] = null;
								break;
							}
						}	
					} else if(rocketLeftLabel[i].getY() >= 750) {
						lavabackgroundLabel.remove(rocketLeftLabel[i]);
						rocketLeftLabel[i] = null;
					} else if(playerLabel.getBounds().intersects(rocketLeftLabel[i].getBounds())) {
						x = 434;
						y = 718;
						ufoX = 1100;
						resetBoss();
						fails++; new Sounds("hurt.wav");
					}
				}
			}
			
			for(int i = 0; i < rocketRightLabel.length; i++) {
				if(rocketRightLabel[i] == null && rocketTimerR.secPassed() >= 2) {
					rocketTimerR.resetTimer();
					rocketRight[i] = new ImageIcon(Assets.hotdog);
					rocketRightLabel[i] = new JLabel(rocketRight[i]);
					rocketRightX[i] = ufoLabel.getX() + 180;
					rocketRightY[i] = ufoLabel.getY() + 90;
					rocketRightLabel[i].setBounds(rocketRightX[i], rocketRightY[i] + 90, 32, 96);
					lavabackgroundLabel.add(rocketRightLabel[i]);
					new Sounds("pew.wav");
				}
			}
			for(int i = 0; i < rocketRightLabel.length; i++) {
				if(rocketRightLabel[i] != null) {
					rocketRightX[i] += 1;
					rocketRightY[i] += 2;
					if(healthlengthX <= 150) rocketRightY[i] += 2;
					rocketRightLabel[i].setBounds(rocketRightX[i], rocketRightY[i], 32, 96);
					rocketRightLabel[i].repaint();
					if(isWalled) {
						for(int wall = 0; wall < warriorWallLabel.length; wall++) {
							if(warriorWallLabel[wall].getBounds().intersects(rocketRightLabel[i].getBounds())) {
								lavabackgroundLabel.remove(rocketRightLabel[i]);
								rocketRightLabel[i] = null;
								break;
							}
						}	
					} else if(rocketRightLabel[i].getY() >= 750) {
						lavabackgroundLabel.remove(rocketRightLabel[i]);
						rocketRightLabel[i] = null;
					} else if(playerLabel.getBounds().intersects(rocketRightLabel[i].getBounds())) {
						x = 434;
						y = 718;
						ufoX = 1100;
						resetBoss();
						fails++; new Sounds("hurt.wav");
					}
				}
			}
			} else {
				for(int i = 0; i < rocketLabel.length; i++) {
					if(rocketLeftLabel[i] != null) {
						lavabackgroundLabel.remove(rocketLeftLabel[i]);
						rocketLeftLabel[i] = null;
					}
					if(rocketLabel[i] != null) {
						lavabackgroundLabel.remove(rocketLabel[i]);
						rocketLabel[i] = null;
					}
					if(rocketRightLabel[i] != null) {
						lavabackgroundLabel.remove(rocketRightLabel[i]);
						rocketRightLabel[i] = null;
					}
				}
				if(burger1Shot) {
					lavabackgroundLabel.remove(burger1Label);
					burger1Shot = false;
				}
				if(burger2Shot) {
					lavabackgroundLabel.remove(burger2Label);
					burger2Shot = false;
				}
			}
			
			if(burger1Shot) {
				burger1Y -= 3;
				burger1Label.setBounds(burger1Label.getX(), burger1Y, 64, 64);
				burger1Label.repaint();
				if(ufoLabel.getBounds().intersects(burger1Label.getBounds())) {
					healthlengthX -= 50;
					lavabackgroundLabel.remove(burger1Label);
					burger1Shot = false;
					new Sounds("electricshock.wav");
				} else if(burger1Label.getY() <= -64) {
					lavabackgroundLabel.remove(burger1Label);
					burger1Shot = false;
				}
			} else if(burger2Shot) {
				burger2Y -= 3;
				burger2Label.setBounds(burger2Label.getX(), burger2Y, 64, 64);
				burger2Label.repaint();
				if(ufoLabel.getBounds().intersects(burger2Label.getBounds())) {
					healthlengthX -= 50;
					lavabackgroundLabel.remove(burger2Label);
					burger2Shot = false;
					new Sounds("electricshock.wav");
				} else if(burger2Label.getY() <= -64) {
					lavabackgroundLabel.remove(burger2Label);
					burger2Shot = false;
				}
			}
			
			if(leftPlayer.getBounds().intersects(bossbutton1Label.getBounds())) {
				velX = 0;
				x += 1;
				canLeft = false;
			} else {
				canLeft = true;
			}
			if(rightPlayer.getBounds().intersects(bossbutton1Label.getBounds())) {
				velX = 0;
				x -= 1;
				canRight = false;
			} else {
				canRight = true;
			}
			if(topPlayer.getBounds().intersects(bossbutton1Label.getBounds())) {
				velY = 0;
			}
			if(bottomPlayer.getBounds().intersects(bossbutton1Label.getBounds())) {
				velY = 0;
				gravity = 0;
				increment = 1;
				y -= 1;
				onGravity = false;
				shootBurger(1);
			} else if(bottomPlayer.getBounds().intersects(brickwalllabel.getBounds())) {
				velY = 0;
				gravity = 0;
				increment = 1;
				onGravity = false;
			}
			else {
				onGravity = true;
			}
			if(leftPlayer.getBounds().intersects(bossbutton2Label.getBounds())) {
				velX = 0;
				x += 1;
				canLeft = false;
			} else {
				canLeft = true;
			}
			if(rightPlayer.getBounds().intersects(bossbutton2Label.getBounds())) {
				velX = 0;
				x -= 1;
				canRight = false;
			} else {
				canRight = true;
			}
			if(topPlayer.getBounds().intersects(bossbutton2Label.getBounds())) {
				velY = 0;
			}
			if(bottomPlayer.getBounds().intersects(bossbutton2Label.getBounds())) {
				velY = 0;
				gravity = 0;
				increment = 1;
				y -= 1;
				onGravity = false;
				shootBurger(2);
			} else if(bottomPlayer.getBounds().intersects(brickwalllabel.getBounds())) {
				velY = 0;
				gravity = 0;
				increment = 1;
				onGravity = false;
			}
			else {
				onGravity = true;
			}
			
			if(healthlengthX <= 0) {
				lvl4DoorLabel.setBounds(420, 686, 64, 64);
				lvl4DoorLabel.setVisible(true);
				ufoLabel.setVisible(false);
			}
			
			repaint();
			
			if(playerLabel.getBounds().intersects(lvl4DoorLabel.getBounds())) {
				new Sounds("enterdoor.wav");
				endScreen();
			}
		}
	}
	
	public void shootBurger(int butNum) {
		if(butNum == 1) {
			if(bossbutton1CD.secPassed() >= 3 && !burger1Shot) {
				new Sounds("cheezlaunch.wav");
				burger1Y = 670;
				bossbutton1CD.resetTimer();
				burger1 = new ImageIcon(Assets.ball);
				burger1Label = new JLabel(burger1);
				burger1Label.setBounds(100, burger1Y, 64, 64);
				burger1Label.setVisible(true);
				lavabackgroundLabel.add(burger1Label);
				burger1Shot = true;
			}
		} else if(butNum == 2) {
			if(bossbutton2CD.secPassed() >= 3 && !burger2Shot) {
				new Sounds("cheezlaunch.wav");
				burger2Y = 670;
				bossbutton2CD.resetTimer();
				burger2 = new ImageIcon(Assets.ball);
				burger2Label = new JLabel(burger2);
				burger2Label.setBounds(736, burger2Y, 64, 64);
				burger2Label.setVisible(true);
				lavabackgroundLabel.add(burger2Label);
				burger2Shot = true;
			}
		}
	}
	
	public void resetBoss() {
		healthlengthX = 500;
		for(int i = 0; i < rocketLabel.length; i++) {
			if(rocketLeftLabel[i] != null) {
				lavabackgroundLabel.remove(rocketLeftLabel[i]);
				rocketLeftLabel[i] = null;
			}
			if(rocketLabel[i] != null) {
				lavabackgroundLabel.remove(rocketLabel[i]);
				rocketLabel[i] = null;
			}
			if(rocketRightLabel[i] != null) {
				lavabackgroundLabel.remove(rocketRightLabel[i]);
				rocketRightLabel[i] = null;
			}
		}
		if(burger1Shot) {
			lavabackgroundLabel.remove(burger1Label);
			burger1Shot = false;
		}
		if(burger2Shot) {
			lavabackgroundLabel.remove(burger2Label);
			burger2Shot = false;
		}
	}
}
	

