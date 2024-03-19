import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

//��ʐݒ�p�̃N���X���\�b�h
public class Screen extends JFrame implements ActionListener, MouseListener,MouseMotionListener {

    JPanel cardPanel;
    CardLayout layout;

    public static void set() {//�E�B���h�E�쐬
        var frame = new Screen();
        frame.setTitle("������H(��)");//�E�B���h�E�̃^�C�g����ݒ肷��
        frame.setSize(1920,1040);//�E�B���h�E�̃T�C�Y��ݒ肷��
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);//�E�B���h�E�����Ƃ��ɁC����������悤�ɐݒ肷��
        frame.setVisible(true);
    }

    public Screen() {

        //�X�^�[�g�p�l��
        JPanel startPanel = new JPanel();
        JButton startBtn = new JButton("start");
        startPanel.add(startBtn);


        //�t�B�[���h�p�l��
        JPanel fieldPanel = new JPanel();
        JButton fieldBtn = new JButton("field");
        fieldPanel.add(fieldBtn);


        //CardLayout�p�p�l��
        cardPanel = new JPanel();
        layout = new CardLayout();
        cardPanel.setLayout(layout);

        cardPanel.add(startPanel, "start");
        cardPanel.add(fieldPanel, "field");


        //�J�[�h�؂�ւ��p�{�^��
        JButton firstButton = new JButton("start");
        firstButton.addActionListener(this);
        firstButton.setActionCommand("field");

        JButton secondButton = new JButton("field");
        secondButton.addActionListener(this);
        secondButton.setActionCommand("start");

        JPanel btnPanel = new JPanel();
        btnPanel.add(firstButton);
        btnPanel.add(secondButton);


        //cardPane�ƃJ�[�h�؂�ւ��p�{�^����JFrame�ɔz�u
        Container contentPane = getContentPane();//�t���[���̃y�C�����擾����
        contentPane.setLayout(null);//�������C�A�E�g�̐ݒ���s��Ȃ�
        contentPane.add(cardPanel);
        contentPane.add(btnPanel);


        //�{�^���̐���
		Play.buttonArray = new JButton[Play.row][Play.col];//�{�^���̔z����T�쐬����[0]����[7]�܂Ŏg����
		for(int i = 0; i < Play.col; i++) {
			for(int j = 0; j < Play.row; j++) {
				Play.buttonArray[j][i] = new JButton(Play.baseIcon);//�{�^���ɃA�C�R����ݒ肷��
				fieldPanel.add(Play.buttonArray[j][i]);//�y�C���ɓ\��t����
				Play.buttonArray[j][i].setBounds(i*45+10,j*45+10,45,45);//�{�^���̑傫���ƈʒu��ݒ肷��D(x���W�Cy���W,x�̕�,y�̕��j
				Play.buttonArray[j][i].addMouseListener(this);//�{�^�����}�E�X�ł�������Ƃ��ɔ�������悤�ɂ���
				Play.buttonArray[j][i].addMouseMotionListener(this);//�{�^�����}�E�X�œ��������Ƃ����Ƃ��ɔ�������悤�ɂ���
				Play.buttonArray[j][i].setActionCommand(Integer.toString(j*Play.row + i));//�{�^���ɔz��̏���t������i�l�b�g���[�N����ăI�u�W�F�N�g�����ʂ��邽�߁j
			}
			System.out.println();
		}

		//�{�[�h������
		Map.random(Play.buttonArray, Play.row, Play.col);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        layout.show(cardPanel, cmd);
    }

    public void mouseClicked(MouseEvent e) {//�{�^�����N���b�N�����Ƃ��̏���
		//
	}

	public void mouseEntered(MouseEvent e) {//�}�E�X���I�u�W�F�N�g�ɓ������Ƃ��̏���
		//System.out.println("�}�E�X��������");
	}

	public void mouseExited(MouseEvent e) {//�}�E�X���I�u�W�F�N�g����o���Ƃ��̏���
		//System.out.println("�}�E�X�E�o");
	}

	public void mousePressed(MouseEvent e) {//�}�E�X�ŃI�u�W�F�N�g���������Ƃ��̏����i�N���b�N�Ƃ̈Ⴂ�ɒ��Ӂj
		//System.out.println("�}�E�X��������");
	}

	public void mouseReleased(MouseEvent e) {//�}�E�X�ŉ����Ă����I�u�W�F�N�g�𗣂����Ƃ��̏���
		//System.out.println("�}�E�X�������");
	}

	public void mouseDragged(MouseEvent e) {//�}�E�X�ŃI�u�W�F�N�g�Ƃ��h���b�O���Ă���Ƃ��̏���
		//
	}

	public void mouseMoved(MouseEvent e) {//�}�E�X���I�u�W�F�N�g��ňړ������Ƃ��̏���
		//
	}
}