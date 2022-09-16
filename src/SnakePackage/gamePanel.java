package SnakePackage;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class gamePanel extends JPanel implements ActionListener{

	
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 75;
	
	//now i am going to create 2 array's  its hold all of the coordinates all of the body parts include head
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	
	int bodyparts = 5;
	int appleEaten;
	
	int appleX=0;//this is the x coordinates where apple is located
	int appleY=0;//this is the y coordinates where apple is located

	char direction = 'R';//snake begin by going to Right .R = Right,L = Left,D = dowm,U = Up.
	boolean running = false;
	
	Timer timer;
	Random random;
	
	gamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	public void paintComponent(Graphics g) {//dont change this method name
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {

		if(running) {
//			for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) { // show the lines like graph paper
//				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
//				g.drawLine(0,i*UNIT_SIZE , SCREEN_WIDTH, i*UNIT_SIZE);
//			}
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
		
//			lets draw the head of the snake
//			x[0] and y[0] = head of the snake.
			
			for(int i=0;i<bodyparts;i++) {
				if(i==0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}else {
					g.setColor(new Color(45,180,0));
					g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));//its set the snake color randomly
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			
//			lets show the score
			g.setColor(Color.RED);
			g.setFont(new Font("Ink Free",Font.BOLD,30));
			FontMetrics matrix = getFontMetrics(g.getFont());
			g.drawString("Score : "+appleEaten, (SCREEN_WIDTH-matrix.stringWidth("Score : "+appleEaten))/2, g.getFont().getSize());
			
		}else {
			gameOver(g);
		}
		
	}
	
	public void newApple() {//here we create the new coordinate of apple every time.
		appleX = random.nextInt((int) SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
		appleY = random.nextInt((int) SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
	}
	
	public void move() {
		for(int i=bodyparts;i>0;i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		}
	}
	
	public void checkApple() {
		if(x[0] == appleX && y[0]==appleY) {
			appleX = random.nextInt((int) SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
			appleY = random.nextInt((int) SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
			bodyparts++;
			appleEaten++;
		}
		
	}
	public void checkCollision() {
		//game over if snake collide with this own body
		for(int i=bodyparts;i>0;i--) {
			if((x[0]==x[i]) && (y[0] == y[i]) ) {
				running = false;
			}
		}
		//check if head touch left border.
		if(x[0]<0) {
			running = false;
		}
		//check if head touch right border.
		if(x[0]>SCREEN_WIDTH) {
			running = false;
		}
		//check if head touch Up border.
		if(y[0]<0) {
			running = false;
		}
		//check if head touch Down border.
		if(y[0]>SCREEN_HEIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.start();
		}
	}
	public void gameOver(Graphics g) {
//		it show the score after the game over
		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free",Font.BOLD,30));
		FontMetrics matrix1 = getFontMetrics(g.getFont());
		g.drawString("Score : "+appleEaten, (SCREEN_WIDTH-matrix1.stringWidth("Score : "+appleEaten))/2, g.getFont().getSize());
		
		//Game Over text
		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free",Font.BOLD,75));
		FontMetrics matrix2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH-matrix2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);//it just put this text on the middle of the Frame just simple mathematics
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(running) {
			move();
			checkApple();
			checkCollision();
		}
		repaint();
		
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) { 
			//lets control the snake
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
			
		}
	}
	

}
