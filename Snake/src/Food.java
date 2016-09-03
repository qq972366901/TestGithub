import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Food {

	int x , y;
	int w = Yard.SIZE;
	int h = Yard.SIZE;
	
	private static Random r = new Random();
	
	private Color color = Color.GREEN;
	
	public Food(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Food() {
		this(r.nextInt(Yard.ROW-2)+2, r.nextInt(Yard.COLUMN));
	}
	
	public Rectangle getRect() {
		return new Rectangle(y * w, x * h, w, h);
	}
	
	public void appear(Snake snake) {
		this.x = r.nextInt(Yard.ROW-2) + 2;
		this.y = r.nextInt(Yard.COLUMN);
		for(Snake.Node n = snake.head;n != null; n = n.next) {
			
		}
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(color);
		if(color == Color.GREEN) {
			color = Color.RED;
		} else {
			color = Color.GREEN;
		}
		g.fillOval(y * h, x * w, w, h);
		g.setColor(c);
	}

}
