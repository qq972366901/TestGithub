import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Yard extends JFrame {
	
	Image offScreamImage = null;
	
	PaintThread paintThread = new PaintThread();
	
	private int score = 0;
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	boolean game = true;
	public static final int ROW = 30;
	public static final int COLUMN = 30;
	public static final int SIZE = 15;
	
	Snake snake = new Snake(this);
	Food food = new Food();
	
	public static void main(String[] args) {
		
		new Yard().launchFrame();
		
	}
	
	public void launchFrame() {
		this.setLocation(200, 200);
		this.setSize(ROW * SIZE, COLUMN * SIZE);
		this.setTitle("Snake");
		
		this.setVisible(true);
		this.setResizable(false);
		
		this.addKeyListener(new KeyAdapter() {
	
			public void keyPressed(KeyEvent e) {
				snake.keyPressed(e);
			}
			
		});
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		new Thread(paintThread).start();
	}
	
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, ROW * SIZE, COLUMN * SIZE);
		g.setColor(Color.BLACK);
		for(int i=1;i<COLUMN;i++) {
			g.drawLine(0, i * SIZE, ROW * SIZE, i * SIZE);
		}
		for(int i=1;i<ROW;i++) {
			g.drawLine(i * SIZE, 0, i * SIZE, COLUMN * SIZE);
		}
		
		g.setColor(Color.YELLOW);
		g.drawString("Score: " + score, 10, 50);
		
		if(false == this.game) {
			g.setFont(new Font("Game Over", Font.CENTER_BASELINE, 30));
			g.drawString("Game Over", COLUMN*SIZE/2-80, ROW*SIZE/2-10);
			paintThread.gameOver();
		}
		
		g.setColor(c);
		
		snake.eat(food);
		food.draw(g);
		snake.draw(g);
	}
	
	public void update(Graphics g) {
		if(offScreamImage == null) {
			offScreamImage = this.createImage(ROW * SIZE, COLUMN * SIZE);
		}
		Graphics gOff = offScreamImage.getGraphics();
		this.paint(gOff);
		g.drawImage(offScreamImage, 0, 0, null);
	}
	
	public class PaintThread implements Runnable {

		private boolean gameRun = true;
		
		public void run() {
			while(gameRun) {
				repaint();
				try {
					Thread.sleep(120);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void gameOver() {
			gameRun = false;
		}
		
	}

	public void stop() {
		game = false;
	}

	
}
