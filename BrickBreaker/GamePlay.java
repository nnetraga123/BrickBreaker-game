package BrickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener{
	private boolean play = false;
	private int score = 0;
	
	private int totalBricks = 21;
	
	private Timer timer;
	private int delay = 5;
	
	private int playerx = 310;
	
	private int ballposX = 120;
	private int ballposY = 350;
	
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	private MapGenerator map;
	
	public GamePlay() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay,this);
		timer.start();
		map = new MapGenerator(3,7);
	}
	
	public void paint(Graphics g) {
		//background
		g.setColor(Color.BLACK);
		g.fillRect(1, 1, 692, 592);
		
		//borders
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//score
		g.setColor(Color.WHITE);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString(""+score, 590, 30);
		//the bar
		g.setColor(Color.GREEN);
		g.fillRect(playerx, 550, 100, 8);
		
		//the ball
		g.setColor(Color.YELLOW);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		//draw blocks
		map.draw((Graphics2D)g);
		
		if(totalBricks<=0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("You Won!!! Score: "+score,260, 300);
			
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Press Enter to Restart",230, 350);
			
		}
		if(ballposY>570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Game Over, Score - "+score,190, 300);
			
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Press Enter to Restart",230, 350);
			
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Press ESC to Exit",270, 390);
		}
		g.dispose();
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		timer.start();
		
		if(play) {
			
			if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerx,550,100,8))) {
				ballYdir = -ballYdir;
			}
			
			//block and ball collision
			A:for(int i=0;i<map.map.length;i++) {
				for(int j=0;j<map.map[0].length;j++) {
					if(map.map[i][j]>0) {
						int brickX = j*map.brickwidth+80;
						int brickY = i*map.brickheight+50;
						int brickwidth = map.brickwidth;
						int brickheight = map.brickheight;
						
						Rectangle rect = new Rectangle(brickX,brickY,brickwidth,brickheight);
						Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;
							
							if(ballposX+19<=brickRect.x||ballposX+1>=brickRect.x+brickRect.width) {
								ballXdir =- ballXdir;
							}
							else {
								ballYdir = -ballYdir;
							}
							break A;
						}
						
					}
				}
			}
			
			ballposX += ballXdir;
			ballposY += ballYdir;
			
			if(ballposX<0) {
				ballXdir = -ballXdir;
			}
			if(ballposY<0) {
				ballYdir = -ballYdir;
			}
			if(ballposX>670) {
				ballXdir = -ballXdir;
			}
		}
		repaint();
	}



	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			if(playerx >=600) {
				playerx = 600;
			}else {
				moveRight();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerx < 10) {
				playerx = 10;
			}else {
				moveLeft();
			}
		}
		
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerx = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3,7);
				
				repaint();
			}
		}
		
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		
	}
	public void moveRight() {
		// TODO Auto-generated method stub
		play = true;
		playerx+= 20;
		
	}

	public void moveLeft() {
		// TODO Auto-generated method stub
		play = true;
		playerx-= 20;
	}

	@Override
	public void keyTyped(KeyEvent e) {	}

	@Override
	public void keyReleased(KeyEvent e) { }
	
}
