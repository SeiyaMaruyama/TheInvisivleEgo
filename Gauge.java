import java.awt.Graphics;
import java.awt.Color;


//���C�t���̃Q�[�W��`�悷��N���X
public class Gauge {

	//���W��
  private int x;
	private int y;
	private int width;
  private int height;

	//�����ڕύX�p
	private int borderWidth;
	private int margin;
	private int a;

	//���x
	private int sleepTime;

	//�Q�[�W�̒l
	private int max;
	private int now;
	private int next;

	//�v�Z�p
	private double step;

	//�����\���̗L��
	private boolean messageMode;


    /**
     * �����Ȃ��R���X�g���N�^�i�X���b�h���g�p���Ȃ��j
     */
    public Gauge(int x, int y, int width, int height, int max, int now) {

			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;

			setStyle(1, 2, 5);

			this.now = now;
			this.next = now;
			setMax(max);

			messageMode = true;
    }

    /**
     * �X���b�h���g�p�i�҂����Ԃ��w��j
     */
    public Gauge(int x, int y, int width, int height, int max, int now, int sleepTime) {

		this(x, y, width, height, max, now);
		this.sleepTime = sleepTime;
        new AnimeThread().start();
    }

    /**
     * �`�揈��
     */
	public void draw(Graphics g) {

		//�g�ɂȂ�p�ێl�p�`��`��
		g.setColor(Color.WHITE);
		g.fillRoundRect(x, y, width, height, a, a);

		//�w�i�̍���`��
		g.setColor(Color.BLACK);
		g.fillRoundRect(x + borderWidth, y + borderWidth,
			width - borderWidth * 2, height - borderWidth * 2, a, a);

		//���݂̒l�ɉ����ăQ�[�W�̕����v�Z
		int nowWidth = (int)(step * now);
		int nextWidth = (int)(step * next);

		//max�̎��Ɍ덷�ŐԂ������Ȃ��悤��
		if(now == max) {
			//���m�ȍő�̕���ݒ�
			nowWidth = width - borderWidth * 2 - margin * 2;
		} else {
			//�����ς݂̕\���̂��ߐԂ�`��
			g.setColor(Color.RED);
			g.fillRoundRect(x + borderWidth + margin, y + borderWidth + margin,
			 width - borderWidth * 2 - margin * 2, height - borderWidth * 2 - margin * 2, a, a);
		}

		//�������㏸���ɂ���ăQ�[�W�̐F�╝��ς���
		if(now > next) {

			//�������̃Q�[�W�i���F�����ݒl�̕��Łj
			g.setColor(Color.YELLOW);
			g.fillRoundRect(x + borderWidth + margin, y + borderWidth + margin,
	 		nowWidth, height - borderWidth * 2 - margin * 2, a, a);

		} else if(now < next) {

			//�㏸���̃Q�[�W�i��ڕW�l�̕��Łj
			g.setColor(Color.BLUE);
			g.fillRoundRect(x + borderWidth + margin, y + borderWidth + margin,
	 		nextWidth, height - borderWidth * 2 - margin * 2, a, a);
		}

		//���ۂ̌��ݒl��\���Q�[�W
		if(next != 0) {

			g.setColor(Color.GREEN);

			//���������㏸���ɂ���ĕ���ς���
			if(now > next) {

				//�������̏ꍇ�i�ڕW�l�̕��Łj
				g.fillRoundRect(x + borderWidth + margin, y + borderWidth + margin,
	 				nextWidth, height - borderWidth * 2 - margin * 2, a, a);

			} else {

				//�㏸���̏ꍇ�i���ݒl�̕��Łj
				g.fillRoundRect(x + borderWidth + margin, y + borderWidth + margin,
	 				nowWidth, height - borderWidth * 2 - margin * 2, a, a);

			}
		}

		//������`�悷�邩
		if(messageMode) {

			g.setColor(Color.WHITE);
			g.drawString(now + "/" + max, x + width / 2, y + height * 2 + borderWidth + margin);
		}
    }

    /**
     * �ʒu�̕ύX
     */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

    /**
     * �ő�l�̕ύX
     */
	public void setMax(int max) {

		this.max = max;

		if(now > max) {
			now = max;
		}
		if(next > max) {
			next = max;
		}

		step = ((double)width - (double)borderWidth * 2.0 - (double)margin * 2.0) / (double)max;
	}

    /**
     * �X�^�C���̕ύX
     */
	public void setStyle(int borderWidth, int margin, int a) {

		this.borderWidth = borderWidth;
		this.margin = margin;
		this.a = a;
	}

    /**
     * �Q�[�W���w��̒l�܂ŏ��X�Ɍ��炷
     */
	public void down(int value) {

		next -= value;

		if(next < 0) {
			next = 0;
		} else if(next > max) {
			next = max;
		}
	}

    /**
     * mode��true�Ȃ�Q�[�W���w��̒l�܂ň�C�Ɍ��炷
     */
	public void down(int value, boolean mode) {
		down(value);
		if(mode) {
			now = next;
		}
	}

    /**
     * �Q�[�W���w��̒l�܂ŏ��X�ɑ��₷
     */
	public void up(int value) {
		down(-value);
	}

    /**
     * mode��true�Ȃ�Q�[�W���w��̒l�܂ň�C�ɑ��₷
     */
	public void up(int value, boolean mode) {
		down(-value, mode);
	}

    /**
     * �Q�[�W���ő�l�܂ŏ��X�ɑ��₷
     */
	public void toMax() {
		up(max);
	}

    /**
     * mode��true�Ȃ�Q�[�W���ő�l�܂ň�C�ɑ��₷
     */
	public void toMax(boolean mode) {
		up(max, mode);
	}

    /**
     * �Q�[�W��0�܂ŏ��X�ɑ��₷
     */
	public void toZero() {
		down(next);
	}

    /**
     * mode��true�Ȃ�Q�[�W��0�܂ň�C�ɑ��₷
     */
	public void toZero(boolean mode) {
		down(next, mode);
	}

    /**
     * ������\�����邩�w��
     */
	public void setMessage(boolean mode) {
		this.messageMode = mode;
	}

    /**
     * �����\���̗L����ؑ�
     */
	public void toggleMessage() {
		messageMode = !messageMode;
	}

    /**
     * ��Ԃ��X�V�i�X���b�h��g�p���͊O������Ăяo���K�v������j
     */
	public void updateState() {

		//now��next�̒l�ɋ߂Â���
		if(now > next) {
			now--;
		} else if(now < next) {
			now++;
		}
	}

	/**
	* �A�j���p�X���b�h�i�w�莞�Ԗ��ɍX�V�������Ăяo���j
	*/
    private class AnimeThread extends Thread {

        public void run() {

            while (true) {

				updateState();

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException IE) {
                    IE.printStackTrace();
                }
            }
        }
    }
}