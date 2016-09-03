import java.awt.*;
import java.awt.event.KeyEvent;

public class Snake {
	
	Node head = null;
	Node tail = null;
	int number = 0;
	Yard yard;
	
	public Snake(Yard y) {
		head = new Node(25,25,Direction.LEFT);
		tail = head;
		number = 1;
		yard = y;
	}
	
	public void draw (Graphics g) {
		if(number <= 0) return;
		move();
		for(Node n = head; n != null; n = n.next) {
			n.draw(g);
		}
	}
	
	public void eat (Food food) {
		if(this.getRect().intersects(food.getRect())) {
			food.appear(this);
			this.addToHead();
			yard.setScore(yard.getScore() + 1);
		}
	}
	
	public Rectangle getRect() {
		return new Rectangle(head.y * head.w, head.x * head.h, head.w, head.h);
	}
	
	private void move() {
		addToHead();
		deleteTail();
		judgeFail();
	}
	
	private void judgeFail() {
		if(head.x < 2 || head.x > Yard.ROW-1 || head.y < 0 || head.y > Yard.COLUMN-1) {
			yard.stop();
		}
		boolean hit = false;
		for(Node n = head.next; n != null;n = n.next) {
			if(head.x == n.x && head.y == n.y) {
				hit = true;
				break;
			}
		}
		if(hit) {
			yard.stop();
		}
		
	}

	private void deleteTail() {
		if(number == 0) return;
		this.tail = tail.prev;
		tail.next = null;
		number--;
	}

	private void addToHead () {
		Node node = null;
		switch(head.direction) {
		case LEFT:
			node = new Node (head.x, head.y - 1, head.direction);
			break;
		case UP:
			node = new Node (head.x - 1, head.y, head.direction);
			break;
		case RIGHT:
			node = new Node (head.x, head.y + 1, head.direction);
			break;
		case DOWN:
			node = new Node (head.x + 1, head.y, head.direction);
			break;
		}
		node.next = head;
		head.prev = node;
		this.head = node;
		number++;
	}

	public void addToTail () {
		Node node = null;
		switch(tail.direction) {
		case LEFT:
			node = new Node (tail.x, tail.y + 1, tail.direction);
			break;
		case UP:
			node = new Node (tail.x + 1, tail.y, tail.direction);
			break;
		case RIGHT:
			node = new Node (tail.x, tail.y - 1, tail.direction);
			break;
		case DOWN:
			node = new Node (tail.x - 1, tail.y, tail.direction);
			break;
		}
		tail.next = node;
		node.prev = tail;
		this.tail = node;
		number++;
	}
	
	
	class Node {
		
		int w = Yard.SIZE;
		int h = Yard.SIZE;
		int x , y;
		Direction direction = null;
		Node next = null;
		Node prev = null;
		
		public Node (int row, int column, Direction dir) {
			x = row;
			y = column;
			this.direction = dir;
		}
		
		void draw (Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.BLUE);
			g.fillRect(y * w, x * h, w, h);
			g.setColor(c);
		}
		
	}


	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_LEFT :
			if(head.direction != Direction.RIGHT)
				head.direction = Direction.LEFT;
			break;
		case KeyEvent.VK_UP :
			if(head.direction != Direction.DOWN)
				head.direction = Direction.UP;
			break;
		case KeyEvent.VK_RIGHT :
			if(head.direction != Direction.LEFT)
				head.direction = Direction.RIGHT;
			break;
		case KeyEvent.VK_DOWN :
			if(head.direction != Direction.UP)
				head.direction = Direction.DOWN;
			break;
		}
	}
	
}
