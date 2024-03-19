//スーパークラス
import javax.swing.JComponent;

//画面サイズ
import java.awt.Dimension;

//ダブルバッファリング
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Toolkit;

//文字描画
import java.awt.Color;

//画像描画
import javax.swing.ImageIcon;

//キーボードイベント
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

//テスト用
import javax.swing.JFrame;
import java.awt.Graphics2D;
import java.awt.GradientPaint;

/**
 * 全体をコントロールするクラス
 */
public class Test extends JComponent implements Runnable, KeyListener {

	//テスト用
	private static final int TEST = 0;
	private int mode = TEST;

	//デバッグモードON/OFF用
	private boolean DEBUG_MODE = false;

	//画面サイズの設定用
	private static final int WIDTH = 320;
	private static final int HEIGHT = 240;

	//ダブルバッファリング用
	private Image bufferImage = null;
	private Graphics bufferGraphics;

	//キー押下状態
   	private boolean zPressed = false;
   	private boolean xPressed = false;
    private boolean enterPressed = false;
  	private boolean spacePressed = false;
   	private boolean upPressed = false;
   	private boolean downPressed = false;
    private boolean leftPressed = false;
  	private boolean rightPressed = false;

	private boolean cPressed = false;
	private boolean anyPressed = false;
	private boolean anyPressing = false;

	private static final Color BGCOLOR = Color.BLACK;

	private Gauge gauge;

	public Test() {
		super();

		//画面サイズ設定
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

		//キーボードリスナー登録
        addKeyListener(this);

		//フォーカス
        setFocusable(true);

		//スレッド開始
        new Thread(this).start();
	}

    /**
     * ランメソッド（メインループ用）
     */
	public void run() {

		initialize();

		//メインループ
		while(true) {

			//状態更新
			updateState();

			//バッファ描画
			drawBuffer();

			//画面描画
			drawScreen();

		}
	}

    /**
     * 初期化
     */
	private void initialize() {

		//gauge = new Gauge(60, 110, 75, 10, 200, 100, 25);
		gauge = new Gauge(60, 110, 75, 10, 100, 50);

	}

  //状態更新メソッド
	private void updateState() {

		switch(mode) {

			case TEST:

				if(upPressed && !anyPressing) {
					gauge.toMax();
				}
				if(downPressed && !anyPressing) {
					gauge.toZero();
				}
				if(rightPressed && !anyPressing) {
					gauge.up(10);
				}
				if(leftPressed && !anyPressing) {
					gauge.down(10);
				}
				if(enterPressed && !anyPressing) {
					gauge.toZero(true);
				}
				if(spacePressed && !anyPressing) {
					gauge.toMax(true);
				}
				if(zPressed && !anyPressing) {
					gauge.down(10, true);
				}
				if(xPressed && !anyPressing) {
					gauge.up(10, true);
				}
				if(cPressed && !anyPressing) {
					gauge.toggleMessage();
				}

				if(anyPressed) {
					anyPressing = true;
				}

				gauge.updateState();

				break;
		}
	}


  //モード別描画メソッド
	public void draw(Graphics g) {

		switch(mode) {

			case TEST:

				gauge.draw(g);

				/*
				String[] help = {
					"操作方法", "",
					"・徐々に変更",
					" ←：10ダメージ", " →：10回復", " ↓：全ダメージ", " ↑：全回復",
					"", "・一気に変更",
					" z：10ダメージ", " x：10回復", " Enter：全ダメージ", " Space：全回復",
					"", "c：文字表示 ON/OFF"
				};
				*/

				break;
		}
	}

  //バッファ描画メソッド
	private void drawBuffer() {

		if(bufferImage == null) {

			bufferImage = createImage(WIDTH, HEIGHT);

		}else {

			if(bufferGraphics == null) {
				bufferGraphics = bufferImage.getGraphics();
			}

			bufferGraphics.setColor(BGCOLOR);
			bufferGraphics.fillRect(0, 0, WIDTH, HEIGHT);

			draw(bufferGraphics);
    }
	}

  //画面描画メソッド
	private void drawScreen() {

    Graphics g = getGraphics();

		if(g != null) {

			if(bufferImage != null) {
				g.drawImage(bufferImage, 0, 0, null);
			}
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		}
	}

    /**
     * キー押下時対応キーの変数をTrueに
     */
    public void keyPressed(KeyEvent e) {

   	    int keycode = e.getKeyCode();

        if (keycode == KeyEvent.VK_LEFT) {
   	        leftPressed = true;
       	}
       	if (keycode == KeyEvent.VK_RIGHT) {
           	rightPressed = true;
       	}
       	if (keycode == KeyEvent.VK_UP) {
           	upPressed = true;
       	}
       	if (keycode == KeyEvent.VK_DOWN) {
           	downPressed = true;
       	}
       	if (keycode == KeyEvent.VK_ENTER) {
           	enterPressed = true;
       	}
       	if (keycode == KeyEvent.VK_SPACE) {
           	spacePressed = true;
       	}
       	if (keycode == 90) {
           	zPressed = true;
       	}
       	if (keycode == 88) {
           	xPressed = true;
       	}
       	if (keycode == 67) {
           	cPressed = true;
       	}

		anyPressed = true;
    }

    /**
     * キー解放時対応キーの変数をFalseに
     */
    public void keyReleased(KeyEvent e) {

       	int keycode = e.getKeyCode();

        if (keycode == KeyEvent.VK_LEFT) {
   	        leftPressed = false;
       	}
       	if (keycode == KeyEvent.VK_RIGHT) {
           	rightPressed = false;
       	}
       	if (keycode == KeyEvent.VK_UP) {
           	upPressed = false;
       	}
       	if (keycode == KeyEvent.VK_DOWN) {
           	downPressed = false;
       	}
       	if (keycode == KeyEvent.VK_ENTER) {
           	enterPressed = false;
       	}
       	if (keycode == KeyEvent.VK_SPACE) {
           	spacePressed = false;
       	}
       	if (keycode == 90) {
           	zPressed = false;
       	}
       	if (keycode == 88) {
           	xPressed = false;
       	}
       	if (keycode == 67) {
           	cPressed = false;
       	}

		anyPressed = false;
		anyPressing = false;
    }

    /**
     * キータイプイベント（未使用）
     */
    public void keyTyped(KeyEvent e) {
    }

    /**
     * テスト用メインメソッド
     */
	public static void main(String[] args) {

		JFrame frame = new JFrame("ゲームパーツテスト");
		frame.getContentPane().add(new Test());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}

