package Snake;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {

	static final int SCREEN_WIDHT = 800;
	static final int SCREEN_HEIGHT = 800;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDHT * SCREEN_HEIGHT) / UNIT_SIZE;
	static final int DELAY = 75;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyparts = 6;
	int applesEaten;
	int appleX = 0;
	int appleY = 0;
	char directions = 'R';
	boolean running = false;
	Timer timer;
	Random random;

	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDHT, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}

	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		draw(g);
	}

	public void draw(Graphics g) {
		if (running) {
			for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
				g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i * UNIT_SIZE, SCREEN_HEIGHT, i * UNIT_SIZE);
			}
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

			for (int i = 0; i < bodyparts; i++) {
				if (i == 0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				} else {
					g.setColor(new Color(45, 180, 0));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free", Font.BOLD, 75));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("SCORE: " + applesEaten, (SCREEN_WIDHT - metrics.stringWidth("SCORE: " + applesEaten)) / 2, g.getFont().getSize());
		
		} else {
			gameOver(g);
		}
	}

	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDHT / UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
		
	}

	public void move() {
		for (int i = bodyparts; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		switch (directions) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}

	public void checkApple() {
		if ((x[0] == appleX) && (y[0] == appleY)) {
			bodyparts++;
			applesEaten++;

		}
	}

	public void checkCollisions() {
		// checks if snakehead collides with body
		for (int i = bodyparts; i > 0; i--) {
			if ((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		// check if head touches left bordeer
		if (x[0] < 0) {
			running = false;
		}
		// Check if head touches right border
		if (x[0] > SCREEN_WIDHT) {
			running = false;
		}
		// CHeck if head touces top border
		if (y[0] < 0) {
			running = false;
		}
		if (y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		if (!running) {
			timer.stop();
		}
	}

	public void gameOver(Graphics g) {
		//displaySOcre
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("SCORE: " + applesEaten, (SCREEN_WIDHT - metrics1.stringWidth("SCORE: " + applesEaten)) / 2, g.getFont().getSize());
	
		
		// Game Over text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDHT - metrics.stringWidth("Gane Over")) / 2, SCREEN_HEIGHT);
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();

	}

	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {

			case KeyEvent.VK_LEFT:
				if (directions != 'R') {
					directions = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (directions != 'L') {
					directions = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if (directions != 'D') {
					directions = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if (directions != 'U') {
					directions = 'D';
				}
				break;
			}
		}
	}

}
