package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

/***********************************************************************/
/*                                                                     */
/* Classe com m�todos �teis para implementa��o de um jogo. Inclui:     */
/*                                                                     */
/* - M�todo para iniciar modo gr�fico.                                 */
/*                                                                     */
/* - M�todos para desenhos de formas geom�tricas.                      */
/*                                                                     */
/* - M�todo para atualizar o display.                                  */
/*                                                                     */
/* - M�todo para verificar o estado (pressionada ou n�o pressionada)   */
/*   das teclas usadas no jogo:                                        */
/*                                                                     */
/*   	- up, down, left, right: movimenta��o do player.               */
/*		- control: disparo de proj�teis.                               */
/*		- ESC: para sair do jogo.                                      */
/*                                                                     */
/***********************************************************************/

public class GameLib {

	public static final int WIDTH = 480;
	public static final int HEIGHT = 720;

	public static final int KEY_UP = 0;
	public static final int KEY_DOWN = 1;
	public static final int KEY_LEFT = 2;
	public static final int KEY_RIGHT = 3;
	public static final int KEY_CONTROL = 4;
	public static final int KEY_ESCAPE = 5;

	private static MyFrame frame = null;
	private static Graphics g = null;
	private static MyKeyAdapter keyboard = null;

	public static void initGraphics() {

		frame = new MyFrame("Projeto COO");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		keyboard = new MyKeyAdapter();
		frame.addKeyListener(keyboard);
		frame.requestFocus();

		frame.createBufferStrategy(2);
		g = frame.getBufferStrategy().getDrawGraphics();
	}

	public static void setColor(Color c) {
		g.setColor(c);
	}

	public static void drawLine(double x1, double y1, double x2, double y2) {
		g.drawLine((int) Math.round(x1), (int) Math.round(y1),
				(int) Math.round(x2), (int) Math.round(y2));
	}

	public static void drawCircle(double cx, double cy, double radius) {
		int x = (int) Math.round(cx - radius);
		int y = (int) Math.round(cy - radius);
		int width = (int) Math.round(2 * radius);
		int height = (int) Math.round(2 * radius);

		g.drawOval(x, y, width, height);
	}

	public static void drawDiamond(double x, double y, double radius) {

		int x1 = (int) Math.round(x);
		int y1 = (int) Math.round(y - radius);

		int x2 = (int) Math.round(x + radius);
		int y2 = (int) Math.round(y);

		int x3 = (int) Math.round(x);
		int y3 = (int) Math.round(y + radius);

		int x4 = (int) Math.round(x - radius);
		int y4 = (int) Math.round(y);

		drawLine(x1, y1, x2, y2);
		drawLine(x2, y2, x3, y3);
		drawLine(x3, y3, x4, y4);
		drawLine(x4, y4, x1, y1);
	}

	public static void drawPlayer(double player_X, double player_Y,
			double player_size) {

		GameLib.drawLine(player_X - player_size, player_Y + player_size,
				player_X, player_Y - player_size);
		GameLib.drawLine(player_X + player_size, player_Y + player_size,
				player_X, player_Y - player_size);
		GameLib.drawLine(player_X - player_size, player_Y + player_size,
				player_X, player_Y + player_size * 0.5);
		GameLib.drawLine(player_X + player_size, player_Y + player_size,
				player_X, player_Y + player_size * 0.5);
	}

	public static void drawExplosion(double x, double y, double alpha) {

		int p = 5;
		int r = (int) (255 - Math.pow(alpha, p) * 255);
		int g = (int) (128 - Math.pow(alpha, p) * 128);
		int b = 0;

		GameLib.setColor(new Color(r, g, b));
		GameLib.drawCircle(x, y, alpha * alpha * 40);
		GameLib.drawCircle(x, y, alpha * alpha * 40 + 1);
	}

	public static void fillRect(double cx, double cy, double width,
			double height) {

		int x = (int) Math.round(cx - width / 2);
		int y = (int) Math.round(cy - height / 2);

		g.fillRect(x, y, (int) Math.round(width), (int) Math.round(height));
	}

	public static void display() {

		g.dispose();
		frame.getBufferStrategy().show();
		Toolkit.getDefaultToolkit().sync();
		g = frame.getBufferStrategy().getDrawGraphics();

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, frame.getWidth() - 1, frame.getHeight() - 1);
		g.setColor(Color.WHITE);
	}

	public static boolean iskeyPressed(int index) {

		return keyboard.isKeyPressed(index);
	}

	public static void debugKeys() {

		keyboard.debug();
	}

	// COMECO DOS METODOS CRIADOS POR NOS PARA DESENHOS.

	// Implementacao de um novo desenho para boss. Inserido na classe GameLib
	// pois � onde est� todo o restante do c�digo de desenhar formatos.

	public static void drawHourglass(double x, double y, double radius) {

		int x1 = (int) Math.round(x);
		int y1 = (int) Math.round(y);

		int x2 = (int) Math.round(x + radius);
		int y2 = (int) Math.round(y);

		int x3 = (int) Math.round(x);
		int y3 = (int) Math.round(y + radius);

		int x4 = (int) Math.round(x + radius);
		int y4 = (int) Math.round(y + radius);

		drawLine(x1, y1, x2, y2);
		drawLine(x2, y2, x3, y3);
		drawLine(x3, y3, x4, y4);
		drawLine(x4, y4, x1, y1);
	}

	public static void fillCircle(double cx, double cy, double radius) {

		int x = (int) Math.round(cx - radius);
		int y = (int) Math.round(cy - radius);
		int width = (int) Math.round(2 * radius);
		int height = (int) Math.round(2 * radius);

		g.fillOval(x, y, width, height);
	}

	public static void drawString(String text, int x, int y) {

		g.setFont(new Font("Verdana", Font.PLAIN, 20));

		setColor(Color.WHITE);
		g.drawString(text, x, y);
	}

	public static void drawLifeBar(int x, int y, int width, int height,
			double lifePointsPercent, String character) {

		g.setFont(new Font("Verdana", Font.PLAIN, 30));
		g.drawString(character, x, y - 10);
		g.drawRect(x, y, width, height);
		g.fillRect(x + 5, y + 5, (int) ((width - 10) * lifePointsPercent),
				height - 10);

	}

	public static void writeInCircle(float x, float y, String text,
			double radius) {
		// Find the size of string s in font f in the current Graphics context
		// g.

		FontMetrics fm = g.getFontMetrics();
		java.awt.geom.Rectangle2D rect = fm.getStringBounds(text, g);

		int textHeight = (int) (rect.getHeight());
		int textWidth = (int) (rect.getWidth());

		// Find the top left and right corner
		int cornerX = (int) (x - (textWidth / 2));
		int cornerY = (int) (y - (textHeight / 2) + fm.getAscent());

		g.drawString(text, cornerX, cornerY); // Draw the string.
	}

	public static void drawTime(int x, int y, int secs) {

		g.setFont(new Font("Arial", Font.PLAIN, 30));
		g.drawString("Tempo", x, y - 10);
		g.drawString(Integer.toString(secs), x, y + 20);
	}

	public static void drawPowerUp(String text, float x, float y, Font f) {
		g.drawString(text, (int) x, (int) y);
	}

	public static void drawPowerUpActive(String text, float x, float y, Font f) {
		g.drawString(text, (int) x, (int) y);
	}

	public static void drawSquare(double x, double y, double radius) {

		int x1 = (int) Math.round(x);
		int y1 = (int) Math.round(y);

		int x2 = (int) Math.round(x + radius);
		int y2 = (int) Math.round(y);

		int x3 = (int) Math.round(x + radius);
		int y3 = (int) Math.round(y + radius);

		int x4 = (int) Math.round(x);
		int y4 = (int) Math.round(y + radius);

		drawLine(x1, y1, x2, y2);
		drawLine(x2, y2, x3, y3);
		drawLine(x3, y3, x4, y4);
		drawLine(x4, y4, x1, y1);
	}

}

@SuppressWarnings("serial")
class MyFrame extends JFrame {

	public MyFrame(String title) {
		super(title);
	}

	public void paint(Graphics g) {
	}

	public void update(Graphics g) {
	}

	public void repaint() {
	}
}

class MyKeyAdapter extends KeyAdapter {

	private int[] codes = { KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT,
			KeyEvent.VK_RIGHT, KeyEvent.VK_CONTROL, KeyEvent.VK_ESCAPE };

	private boolean[] keyStates = null;
	private long[] releaseTimeStamps = null;

	public MyKeyAdapter() {
		keyStates = new boolean[codes.length];
		releaseTimeStamps = new long[codes.length];
	}

	public int getIndexFromKeyCode(int keyCode) {
		for (int i = 0; i < codes.length; i++) {
			if (codes[i] == keyCode)
				return i;
		}
		return -1;
	}

	public void keyPressed(KeyEvent e) {
		int index = getIndexFromKeyCode(e.getKeyCode());
		if (index >= 0) {
			keyStates[index] = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		int index = getIndexFromKeyCode(e.getKeyCode());
		if (index >= 0) {
			keyStates[index] = false;
			releaseTimeStamps[index] = System.currentTimeMillis();
		}
	}

	public boolean isKeyPressed(int index) {
		boolean keyState = keyStates[index];
		long keyReleaseTime = releaseTimeStamps[index];
		if (keyState == false) {
			if (System.currentTimeMillis() - keyReleaseTime > 5)
				return false;
		}
		return true;
	}

	public void debug() {
		System.out.print("Key states = {");
		for (int i = 0; i < codes.length; i++) {
			System.out.print(" " + keyStates[i]
					+ (i < (codes.length - 1) ? "," : ""));
		}
		System.out.println(" }");
	}
}
