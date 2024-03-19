//�X�[�p�[�N���X
import javax.swing.JComponent;

//��ʃT�C�Y
import java.awt.Dimension;

//�_�u���o�b�t�@�����O
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Toolkit;

//�����`��
import java.awt.Color;

//�摜�`��
import javax.swing.ImageIcon;

//�L�[�{�[�h�C�x���g
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

//�e�X�g�p
import javax.swing.JFrame;
import java.awt.Graphics2D;
import java.awt.GradientPaint;

/**
 * �S�̂��R���g���[������N���X
 */
public class Test extends JComponent implements Runnable, KeyListener {

	//�e�X�g�p
	private static final int TEST = 0;
	private int mode = TEST;

	//�f�o�b�O���[�hON/OFF�p
	private boolean DEBUG_MODE = false;

	//��ʃT�C�Y�̐ݒ�p
	private static final int WIDTH = 320;
	private static final int HEIGHT = 240;

	//�_�u���o�b�t�@�����O�p
	private Image bufferImage = null;
	private Graphics bufferGraphics;

	//�L�[�������
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

		//��ʃT�C�Y�ݒ�
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

		//�L�[�{�[�h���X�i�[�o�^
        addKeyListener(this);

		//�t�H�[�J�X
        setFocusable(true);

		//�X���b�h�J�n
        new Thread(this).start();
	}

    /**
     * �������\�b�h�i���C�����[�v�p�j
     */
	public void run() {

		initialize();

		//���C�����[�v
		while(true) {

			//��ԍX�V
			updateState();

			//�o�b�t�@�`��
			drawBuffer();

			//��ʕ`��
			drawScreen();

		}
	}

    /**
     * ������
     */
	private void initialize() {

		//gauge = new Gauge(60, 110, 75, 10, 200, 100, 25);
		gauge = new Gauge(60, 110, 75, 10, 100, 50);

	}

  //��ԍX�V���\�b�h
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


  //���[�h�ʕ`�惁�\�b�h
	public void draw(Graphics g) {

		switch(mode) {

			case TEST:

				gauge.draw(g);

				/*
				String[] help = {
					"������@", "",
					"�E���X�ɕύX",
					" ���F10�_���[�W", " ���F10��", " ���F�S�_���[�W", " ���F�S��",
					"", "�E��C�ɕύX",
					" z�F10�_���[�W", " x�F10��", " Enter�F�S�_���[�W", " Space�F�S��",
					"", "c�F�����\�� ON/OFF"
				};
				*/

				break;
		}
	}

  //�o�b�t�@�`�惁�\�b�h
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

  //��ʕ`�惁�\�b�h
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
     * �L�[�������Ή��L�[�̕ϐ���True��
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
     * �L�[������Ή��L�[�̕ϐ���False��
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
     * �L�[�^�C�v�C�x���g�i���g�p�j
     */
    public void keyTyped(KeyEvent e) {
    }

    /**
     * �e�X�g�p���C�����\�b�h
     */
	public static void main(String[] args) {

		JFrame frame = new JFrame("�Q�[���p�[�c�e�X�g");
		frame.getContentPane().add(new Test());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}

