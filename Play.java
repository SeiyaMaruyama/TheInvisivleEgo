import java.net.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.Graphics;
import java.awt.Color;
import java.io.File;//���y�Đ����ɕK�v
import javax.sound.sampled.AudioFormat;//���y�Đ����ɕK�v
import javax.sound.sampled.AudioSystem;//���y�Đ����ɕK�v
import javax.sound.sampled.Clip;//���y�Đ����ɕK�v
import javax.sound.sampled.DataLine;//���y�Đ����ɕK�v

public class Play extends JFrame implements MouseListener,MouseMotionListener, KeyListener, ActionListener {

	String myName;
	int myNumberInt;
	int roomNum;//�ΐ킷�镔���̕����ԍ�
	int campNum;//�ΐ핔�����ł́A�����̐w�c��ԍ�
	int myCamp;//�G�����̋��
	private String myColor;
	private int turn = 0;
	int myMemberNum = 3;
	int enemiesNum = 3;
	int myAlive = 1;

	//��ʐݒ�p
	private static int windowWidth = 1290;
	private static int windowHeight = 680;
	private JPanel cardPanel;
	private CardLayout layout;
	private Container contentPane;
	public static JButton buttonArray[][];//�{�^���p�̔z��
	private JButton exitButton;
	private Gauge gauge;
	JLabel startLabel;
	JLabel ruleLabel;
	JLabel backLabel;
	JLabel swordLabel;
	JLabel rodLabel;
	String turnSt;
	JLabel turnLabel;
	//�_�u���o�b�t�@�����O�p
	private Image bufferImage = null;
	private Graphics bufferGraphics;
	//�A�C�R���̐ݒ�
	public static ImageIcon footIcon = new ImageIcon("foot.png");
	public static ImageIcon fieldIcon = new ImageIcon("field.png");
	public static ImageIcon whiteIcon = new ImageIcon("White.jpg");
	public static ImageIcon blackIcon = new ImageIcon("Black.jpg");
	public static ImageIcon mountainIcon = new ImageIcon("mountain.png");
	private ImageIcon myIcon, yourIcon;
	private static ImageIcon jackImg;
	public static ImageIcon moveIcon;
	public static ImageIcon swordIcon = new ImageIcon("sword.png");
	public static ImageIcon swordIconM = new ImageIcon("Msword.png");
	public static ImageIcon swordIconY = new ImageIcon("Ysword.png");
	public static ImageIcon swordIconC = new ImageIcon("Csword.png");
	public static ImageIcon swordIconR = new ImageIcon("Rsword.png");
	public static ImageIcon swordIconG = new ImageIcon("Gsword.png");
	public static ImageIcon swordIconB = new ImageIcon("Bsword.png");
	public static ImageIcon rodIcon = new ImageIcon("rod.png");
	public static ImageIcon rodIconM = new ImageIcon("Mrod.png");
	public static ImageIcon rodIconY = new ImageIcon("Yrod.png");
	public static ImageIcon rodIconC = new ImageIcon("Crod.png");
	public static ImageIcon rodIconR = new ImageIcon("Rrod.png");
	public static ImageIcon rodIconG = new ImageIcon("Grod.png");
	public static ImageIcon rodIconB = new ImageIcon("Brod.png");
	//�}�b�v�̍s��
	public static int row = 12;//���R
	public static int col = 15;//�u��v�Őݒ肷�邱�ƁI
	private int x, y;

	//�����̍��W
	private int myX;
	private int myY;
	private int locationNum;
	private int moveNum;
	int nowBnumRow;
	int nowBnumCol;

	SoundPlayer themeMusic;//�ǂ�����ł��A�N�Z�X�ł���悤�ɁC�N���X�̃����o�Ƃ��Đ錾
	SoundPlayer selectMusic;//�ǂ�����ł��A�N�Z�X�ł���悤�ɁC�N���X�̃����o�Ƃ��Đ錾
	SoundPlayer gameoverMusic;

	private PrintWriter out;//�o�͗p�̃��C�^�[

	//�L�����X�e�[�^�X
	String name = "No Name";
	int maxHP = 200;
	int hp = 200;
	int attack;
	int defense;
	String soulColor;
	String myChara;
	int heal = 2;
	int revive = 0;
	int bom = 1;

	/*
	//�e�L�����f�[�^
	String name0,name1,name2,name3,name4,name5;
	int maxHP0,maxHP1,maxHP2,maxHP3,maxHP4,maxHP5;
	int hp0,hp1,hp2,hp3,hp4,hp5;
	int attack0,attack1,attack2,attack3,attack4,attack5;
	int defense0,defense1,defense2,defense3,defense4,defense5;
	String soulColor0,soulColor1,soulColor2,soulColor3,soulColor4,soulColor5;
	int heal0,heal1,heal2,heal3,heal4,heal5;
	*/


	public Play() {
		//���O�̓��̓_�C�A���O���J��
		myName = JOptionPane.showInputDialog(null,"���O����͂��Ă�������","���O�̓���",JOptionPane.QUESTION_MESSAGE);
		if(myName.equals("")){
			myName = "No name";//���O���Ȃ��Ƃ��́C"No name"�Ƃ���
		}

		//IP�A�h���X���̓_�C�A���O
		String myAddress = JOptionPane.showInputDialog(null,"IP�A�h���X����͂��Ă�������","IP�A�h���X�̓���",JOptionPane.QUESTION_MESSAGE);
		if(myAddress.equals("")){
			myAddress = "localhost";//IP�A�h���X���Ȃ��Ƃ��́C"localhost"�Ƃ���
		}

		//�E�B���h�E���쐬�E�ݒ肷��
		setTitle("The Invisible Ego");//�E�B���h�E�̃^�C�g����ݒ肷��
		setSize(windowWidth, windowHeight);//�E�B���h�E�̃T�C�Y��ݒ肷��
		//setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);//�E�B���h�E�����Ƃ��ɁC����������悤�ɐݒ肷��

		//�X�^�[�g�p�l��
		JPanel startPanel = new JPanel();
		startPanel.setLayout(null);
		startPanel.setBackground(Color.BLACK);
		var titleImage = new ImageIcon("title.png");
		var titleLabel = new JLabel(titleImage);
		startPanel.add(titleLabel);
		titleLabel.setBounds(295,200,700,70);//�{�^���̑傫���ƈʒu��ݒ肷��D(x���W�Cy���W,x�̕�,y�̕��j
		var startImage = new ImageIcon("start.png");
		startLabel = new JLabel(startImage);
		startPanel.add(startLabel);
		startLabel.setBounds(565,400,160,32);
		startLabel.addMouseListener(this);
		var ruleImage = new ImageIcon("rule.png");
		ruleLabel = new JLabel(ruleImage);
		startPanel.add(ruleLabel);
		ruleLabel.setBounds(565,480,160,32);
		ruleLabel.addMouseListener(this);

		//�V�ѕ��p�l��
		var rulePanel = new JPanel();
		rulePanel.setLayout(null);
		rulePanel.setBackground(Color.BLACK);
		var explanation1 = new JLabel("���[������");
		explanation1.setForeground(Color.WHITE);
		explanation1.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 24));
		explanation1.setBounds(550, 50, 140, 30);
		rulePanel.add(explanation1);
		var explanation2 = new JLabel("1. �N���A�����͊J�n�n�_�Ɛ^���΂̍s�ւ̓��B�B");
		explanation2.setForeground(Color.WHITE);
		explanation2.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 24));
		explanation2.setBounds(125, 150, 1000, 30);
		rulePanel.add(explanation2);
		var explanation3 = new JLabel("2. �����ɋߐڂ���ƐZ�H���A�G�̏ꍇ�͐F���z������B");
		explanation3.setForeground(Color.WHITE);
		explanation3.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 24));
		explanation3.setBounds(125, 225, 1000, 30);
		rulePanel.add(explanation3);
		var explanation4 = new JLabel("  �� �F�̎O���F�ɏ�����B");
		explanation4.setForeground(Color.WHITE);
		explanation4.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 24));
		explanation4.setBounds(125, 300, 500, 30);
		rulePanel.add(explanation4);
		var explanation5 = new JLabel("3. �u���v�ɂȂ�ƃQ�[���I�[�o�[�B");
		explanation5.setForeground(Color.WHITE);
		explanation5.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 24));
		explanation5.setBounds(125, 375, 500, 30);
		rulePanel.add(explanation5);
		var explanation6 = new JLabel("4. �ʂ����ꏊ���g���Ȃ��Ȃ邱�Ƃ�����B");
		explanation6.setForeground(Color.WHITE);
		explanation6.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 24));
		explanation6.setBounds(125, 450, 520, 30);
		rulePanel.add(explanation6);
		var backImage = new ImageIcon("back.png");
		backLabel = new JLabel(backImage);
		rulePanel.add(backLabel);
		backLabel.setBounds(565,520,160,32);
		backLabel.addMouseListener(this);

		//�L�����I���p�l��
		JPanel selectPanel = new JPanel();
		selectPanel.setLayout(null);
		selectPanel.setBackground(Color.BLACK);
		var comment = new JLabel("�����̃W���u��I��ł�������");
		comment.setForeground(Color.WHITE);
		comment.setBounds(440, 100, 600, 30);
		comment.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 30));
		selectPanel.add(comment);
		swordLabel = new JLabel(swordIcon);
		swordLabel.setBounds(450, 270, 90, 90);
		swordLabel.addMouseListener(this);
		selectPanel.add(swordLabel);
		rodLabel = new JLabel(rodIcon);
		rodLabel.setBounds(740, 270, 90, 90);
		rodLabel.addMouseListener(this);
		selectPanel.add(rodLabel);
		var swordExplanation = new JLabel("�@��m�F�s���͈͂�2�ŁA�U���͈͂�1�ł��B");
		swordExplanation.setForeground(Color.WHITE);
		swordExplanation.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 24));
		swordExplanation.setBounds(410, 400, 500, 30);
		selectPanel.add(swordExplanation);
		var rodExplanation = new JLabel("���p�t�F�s���͈͂�1�ŁA�U���͈͂�2�ł��B");
		rodExplanation.setForeground(Color.WHITE);
		rodExplanation.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 24));
		rodExplanation.setBounds(410, 450, 500, 30);
		selectPanel.add(rodExplanation);

		//�t�B�[���h�p�l��
		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout(null);
		fieldPanel.setBackground(Color.BLACK);
		//gauge = new Gauge(800,800,300,300,200,100);


		//�Q�[���I�[�o�[�p�l��
		JPanel endPanel = new JPanel();
		endPanel.setLayout(null);
		endPanel.setBackground(Color.BLACK);
		//�摜�t�����x���̍쐬
		ImageIcon gameoverIcon = new ImageIcon("gameover.png");//�Ȃɂ��摜�t�@�C�����_�E�����[�h���Ă���
		JLabel gameoverLabel = new JLabel(gameoverIcon);
		endPanel.add(gameoverLabel);
		gameoverLabel.setBounds(0,0,windowWidth,windowHeight);//�{�^���̑傫���ƈʒu��ݒ肷��D(x���W�Cy���W,x�̕�,y�̕��j
		gameoverLabel.addMouseListener(this);//�{�^�����}�E�X�ł�������Ƃ��ɔ�������悤�ɂ���
		gameoverLabel.setForeground(Color.WHITE); //�����F�̐ݒ�DColor�̐ݒ�́C���̃y�[�W�����ĉ������@http://www.javadrive.jp/tutorial/color/

		//�l�����߂ɂ��I���p�l��
		JPanel exitPanel = new JPanel();
		exitPanel.setLayout(null);
		exitPanel.setBackground(Color.GRAY);
		var over = new JLabel("�Q���l������𒴂��܂����B");
		over.setForeground(Color.BLACK);
		over.setBounds(440, 300, 600, 30);
		over.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 30));
		exitPanel.add(over);

		//CardLayout�p�p�l��
		cardPanel = new JPanel();
		layout = new CardLayout();
		cardPanel.setLayout(layout);

		cardPanel.add(startPanel, "start");
		cardPanel.add(rulePanel, "rule");
		cardPanel.add(selectPanel, "select");
		cardPanel.add(fieldPanel, "field");
		cardPanel.add(endPanel, "end");
		cardPanel.add(exitPanel, "exit");

		//�J�[�h�؂�ւ��p�{�^��
		/*
		JButton firstButton = new JButton("start");
		firstButton.setBounds(500, 500, 60, 30);
		firstButton.addActionListener(this);
		firstButton.setActionCommand("select");
		startPanel.add(firstButton);
		*/

		JButton secondButton = new JButton("select");
		secondButton.addActionListener(this);
		secondButton.setActionCommand("field");

		JButton thirdButton = new JButton("field");
		thirdButton.addActionListener(this);
		thirdButton.setActionCommand("end");

		JButton fourthButton = new JButton("end");
		fourthButton.addActionListener(this);
		fourthButton.setActionCommand("start");

		//�I���{�^��
		exitButton = new JButton("exit");
		exitButton.addActionListener(this);
		exitButton.setBounds(600, 500, 60, 36);
		exitPanel.add(exitButton);

		/*
		//�J�ڊm�F�p
		JPanel btnPanel = new JPanel();
		btnPanel.add(firstButton);
		firstButton.setBounds(500, 670, 50, 30);
		btnPanel.add(secondButton);
		secondButton.setBounds(600, 670, 50, 30);
		btnPanel.add(thirdButton);
		thirdButton.setBounds(700, 670, 50, 30);
		btnPanel.add(fourthButton);
		fourthButton.setBounds(800, 670, 50, 30);
		btnPanel.add(exitButton);
		exitButton.setBounds(800, 670, 50, 30);
		*/

		//cardPane�ƃJ�[�h�؂�ւ��p�{�^����JFrame�ɔz�u
		contentPane = getContentPane();//�t���[���̃y�C�����擾����
		contentPane.add(cardPanel);
		//contentPane.add(btnPanel, BorderLayout.SOUTH);


		//�{�^���̐���
		buttonArray = new JButton[row][col];//�{�^���̔z����쐬����
		for(int j = 0; j < row; j++) {
			for(int i = 0; i < col; i++) {
				buttonArray[j][i] = new JButton(fieldIcon);//�{�^���ɃA�C�R����ݒ肷��
				fieldPanel.add(buttonArray[j][i]);//�y�C���ɓ\��t����
				buttonArray[j][i].setBounds(i*45+30, j*45+50, 45, 45);//�{�^���̑傫���ƈʒu��ݒ肷��D(x���W�Cy���W,x�̕�,y�̕��j
				buttonArray[j][i].addMouseListener(this);//�{�^�����}�E�X�ł�������Ƃ��ɔ�������悤�ɂ���
				buttonArray[j][i].addMouseMotionListener(this);//�{�^�����}�E�X�œ��������Ƃ����Ƃ��ɔ�������悤�ɂ���
				buttonArray[j][i].setActionCommand(Integer.toString(j*col + i));//�{�^���ɔz��̏���t������i�l�b�g���[�N����ăI�u�W�F�N�g�����ʂ��邽�߁j
			}
			System.out.println();
		}


		//�{�[�h������
		Map.random(buttonArray, row, col);

		//�L�����C���[�W�쐬
		jackImg = new ImageIcon("jack.gif");
		var jackLabel = new JLabel(jackImg);
		//fieldPanel.add(jackLabel);
		jackLabel.setBounds(1000,300,80,80);//�{�^���̑傫���ƈʒu��ݒ肷��D(x���W�Cy���W,x�̕�,y�̕��j
		jackLabel.addMouseListener(this);//�{�^�����}�E�X�ł�������Ƃ��ɔ�������悤�ɂ���
		jackLabel.setForeground(Color.WHITE);


		//�T�[�o�ɐڑ�����
		Socket socket = null;
		try {
			//"localhost"�́C���������ւ̐ڑ��Dlocalhost��ڑ����IP Address�i"133.42.155.201"�`���j�ɐݒ肷��Ƒ���PC�̃T�[�o�ƒʐM�ł���
			//10000�̓|�[�g�ԍ��DIP Address�Őڑ�����PC�����߂āC�|�[�g�ԍ��ł���PC�㓮�삷��v���O��������肷��
			socket = new Socket(myAddress, 10000);
		} catch (UnknownHostException e) {
			System.err.println("�z�X�g�� IP �A�h���X������ł��܂���: " + e);
		} catch (IOException e) {
				System.err.println("�G���[���������܂���: " + e);
		}

		MesgRecvThread mrt = new MesgRecvThread(socket, myName);//��M�p�̃X���b�h���쐬����
		mrt.start();//�X���b�h�𓮂����iRun�������j
	}

	//���b�Z�[�W��M�̂��߂̃X���b�h
	public class MesgRecvThread extends Thread {

		Socket socket;
		String myName;

		public MesgRecvThread(Socket s, String n){
			socket = s;
			myName = n;
		}

		//�ʐM�󋵂��Ď����C��M�f�[�^�ɂ���ē��삷��
		public void run() {
			try{
				InputStreamReader sisr = new InputStreamReader(socket.getInputStream());
				BufferedReader br = new BufferedReader(sisr);
				out = new PrintWriter(socket.getOutputStream(), true);
				out.println(myName);//�ڑ��̍ŏ��ɖ��O�𑗂�
				String myNumberStr = br.readLine();//�R�}�̐F�Ɛ�����ݒ�
				myNumberInt = Integer.parseInt(myNumberStr) - 1;
				roomNum = myNumberInt / 6;
				campNum = myNumberInt / 3;
				//7�l�ȏオ�Q�����悤�Ƃ�����ߏ�Ȑl��e��
				if(roomNum != 0) {
					layout.show(cardPanel, "exit");
				}

				//�X�^�[�g��ʂ̉��y�𗬂�
				themeMusic = new SoundPlayer("theme.wav");
				themeMusic.SetLoop(true);//�a�f�l�Ƃ��čĐ����J��Ԃ�
				themeMusic.playSound();

				//myNumberInt�̒l�Őw�c����
				if (campNum == 0) {
					myCamp = 0;
				}else if(campNum == 1) {
					myCamp = 1;
				}
				//���̐F�ݒ�
				if(myNumberInt == 0 | myNumberInt == 3) {
					soulColor = "M";
				}else if(myNumberInt == 1 | myNumberInt == 4) {
					soulColor = "Y";
				}else if(myNumberInt == 2 | myNumberInt == 5) {
					soulColor = "C";
				}
				//�ŏ��̎����̍��W
				switch(myNumberInt) {
					case 0:
						myX = (col-1)/2 - 1;
						myY = row - 1;
						locationNum = myY * col + myX;
						break;
					case 1:
						myX = (col-1)/2;
						myY = row - 1;
						locationNum = myY * col + myX;
						break;
					case 2:
						myX = (col-1)/2 + 1;
						myY = row - 1;
						locationNum = myY * col + myX;
						break;
					case 3:
						myX = (col-1)/2 - 1;
						myY = 0;
						locationNum = myY * col + myX;
						break;
					case 4:
						myX = (col-1)/2;
						myY = 0;
						locationNum = myY * col + myX;
						break;
					case 5:
						myX = (col-1)/2 + 1;
						myY = 0;
						locationNum = myY * col + myX;
						break;
				}
				while(true) {
					String inputLine = br.readLine();//�f�[�^����s�������ǂݍ���ł݂�
					if (inputLine != null) {//�ǂݍ��񂾂Ƃ��Ƀf�[�^���ǂݍ��܂ꂽ���ǂ������`�F�b�N����
						System.out.println(inputLine);//�f�o�b�O�i����m�F�p�j�ɃR���\�[���ɏo�͂���
						String[] inputTokens = inputLine.split(" ");	//���̓f�[�^����͂��邽�߂ɁA�X�y�[�X�Ő؂蕪����
						String cmd = inputTokens[0];//�R�}���h�̎��o���D�P�ڂ̗v�f�����o��
						if(cmd.equals("end")){//���肩��Q�[���I���̒m�点���󂯂Č��ʕ\��
							themeMusic.stop();
							layout.show(cardPanel, cmd);
							gameoverMusic = new SoundPlayer("gameover3.wav");
							gameoverMusic.playSound();
							break;
						}
						if(cmd.equals("MOVE")){//cmd�̕�����"MOVE"�����������ׂ�D��������true�ƂȂ�
							//MOVE�̎��̏���(�R�}�̈ړ��̏���)
							String theBName = inputTokens[1];//�{�^���̖��O�i�ԍ��j�̎擾
							int theBnumRow = Integer.parseInt(theBName) / col;//�{�^���̖��O�𐔒l�ɕϊ�����
							int theBnumCol = Integer.parseInt(theBName) % col;//�{�^���̖��O�𐔒l�ɕϊ�����
							String nowBName = inputTokens[2];
							nowBnumRow = Integer.parseInt(nowBName) / col;//�{�^���̖��O�𐔒l�ɕϊ�����
							nowBnumCol = Integer.parseInt(nowBName) % col;//�{�^���̖��O�𐔒l�ɕϊ�����
							String moveChara = inputTokens[3];
							String moveColor = inputTokens[4];
							if(moveChara.equals("sword")) {
								if(moveColor.equals("M")) {
									moveIcon = swordIconM;
								}else if(moveColor.equals("Y")) {
									moveIcon = swordIconY;
								}else if(moveColor.equals("C")) {
									moveIcon = swordIconC;
								}else if(moveColor.equals("R")) {
									moveIcon = swordIconR;
								}else if(moveColor.equals("G")) {
									moveIcon = swordIconG;
								}else if(moveColor.equals("B")) {
									moveIcon = swordIconB;
								}else if(moveColor.equals("Bl")) {
									moveIcon = swordIcon;
								}
							}else if(moveChara.equals("rod")) {
								if(moveColor.equals("M")) {
									moveIcon = rodIconM;
								}else if(moveColor.equals("Y")) {
									moveIcon = rodIconY;
								}else if(moveColor.equals("C")) {
									moveIcon = rodIconC;
								}else if(moveColor.equals("R")) {
									moveIcon = rodIconR;
								}else if(moveColor.equals("G")) {
									moveIcon = rodIconG;
								}else if(moveColor.equals("B")) {
									moveIcon = rodIconB;
								}else if(moveColor.equals("Bl")) {
									moveIcon = rodIcon;
								}
							}
							buttonArray[nowBnumRow][nowBnumCol].setIcon(footIcon);
							buttonArray[theBnumRow][theBnumCol].setIcon(moveIcon);
							moveNum = theBnumRow * col + theBnumCol;

							String msg = "set" + " " + moveNum + " " + moveChara + " " + moveColor;
							out.println(msg);
							out.flush();
							judgeGame();
							nextTurn();
							checkAlive();//�����̃R�}�������Ă��邩(����ł�����p�X)
							/*
							int x = Integer.parseInt(inputTokens[2]);//���l�ɕϊ�����
							int y = Integer.parseInt(inputTokens[3]);//���l�ɕϊ�����
							buttonArray[theBnumRow][theBnumCol].setLocation(x,y);//�w��̃{�^�����ʒu��x,y�ɐݒ肷��
							*/
						}/*else if(cmd.equals("PLACE")) {//cmd�̕�����"PLACE"�����������ׂ�D��������true�ƂȂ�
							//PLACE�̎��̏���(�R�}��u������)
							String theBName = inputTokens[1];//�{�^���̖��O�i�ԍ��j�̎擾
							int theBnumRow = Integer.parseInt(theBName) / col;//�{�^���̖��O�𐔒l�ɕϊ�����
							int theBnumCol = Integer.parseInt(theBName) % col;//�{�^���̖��O�𐔒l�ɕϊ�����
							int theColor = Integer.parseInt(inputTokens[2]);//���l�ɕϊ�����
							//if(theColor ==  myColor.equals("M")){
								buttonArray[theBnumRow][theBnumCol].setIcon(myIcon);//blackIcon�ɐݒ肷��
								//�Q�[�������s�\�����ׂ�
									if(judgeGame() == 0){//���肪�p�X�ƂȂ�ꍇ�Ɏ������łĂ邩���ׂ�
										continue;
									}else if(judgeGame() == 1){//} {
										String msg = "end";//����ɃQ�[���I���̒ʒm
										//�T�[�o�ɏ��𑗂�
										out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
										out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����
										break;
									}else if(judgeGame() == 2) {
										String msg = "end";//����ɃQ�[���I���̒ʒm
										//�T�[�o�ɏ��𑗂�
										out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
										out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����
										break;
									}
								}else {
									String msg = "change";//�^�[�������p�̃��b�Z�[�W�쐬
									//�T�[�o�ɏ��𑗂�
									out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
									out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����
								}
						}*/else if(cmd.equals("initSet")) {
							String initLocation = inputTokens[1];
							int initRow = Integer.parseInt(initLocation) / col;
							int initCol = Integer.parseInt(initLocation) % col;
							String initChara = inputTokens[2];
							String initSoul = inputTokens[3];
							if(initChara.equals("sword")) {
								if(initSoul.equals("M")){
									buttonArray[initRow][initCol].setIcon(swordIconM);
								}else if(initSoul.equals("Y")) {
									buttonArray[initRow][initCol].setIcon(swordIconY);
								}else if(initSoul.equals("C")){
									buttonArray[initRow][initCol].setIcon(swordIconC);
								}
							}else if(initChara.equals("rod")) {
								if(initSoul.equals("M")){
									buttonArray[initRow][initCol].setIcon(rodIconM);
								}else if(initSoul.equals("Y")) {
									buttonArray[initRow][initCol].setIcon(rodIconY);
								}else if(initSoul.equals("C")){
									buttonArray[initRow][initCol].setIcon(rodIconC);
								}
							}
						}else if(cmd.equals("set")) {
							String someLocation = inputTokens[1];
							int someRow = Integer.parseInt(someLocation) / col;
							int someCol = Integer.parseInt(someLocation) % col;
							String someChara = inputTokens[2];
							String someSoul = inputTokens[3];
							if(someChara.equals("sword")) {
								if(someSoul.equals("M")){
									buttonArray[someRow][someCol].setIcon(swordIconM);
								}else if(someSoul.equals("Y")) {
									buttonArray[someRow][someCol].setIcon(swordIconY);
								}else if(someSoul.equals("C")){
									buttonArray[someRow][someCol].setIcon(swordIconC);
								}else if(someSoul.equals("R")) {
									buttonArray[someRow][someCol].setIcon(swordIconR);
								}else if(someSoul.equals("G")) {
									buttonArray[someRow][someCol].setIcon(swordIconG);
								}else if(someSoul.equals("B")) {
									buttonArray[someRow][someCol].setIcon(swordIconB);
								}else if(someSoul.equals("Bl")) {
									buttonArray[someRow][someCol].setIcon(swordIcon);
									System.out.println("bls");
									try {
										Thread.sleep(3 * 1000);//�~���b
									} catch (InterruptedException e) {
										System.out.println("sleep�̃G���[");
									}
									String msg = "end";
									out.println(msg);
									out.flush();
								}
							}else if(someChara.equals("rod")) {
								if(someSoul.equals("M")){
									buttonArray[someRow][someCol].setIcon(rodIconM);
								}else if(someSoul.equals("Y")) {
									buttonArray[someRow][someCol].setIcon(rodIconY);
								}else if(someSoul.equals("C")){
									buttonArray[someRow][someCol].setIcon(rodIconC);
								}else if(someSoul.equals("R")) {
									buttonArray[someRow][someCol].setIcon(rodIconR);
								}else if(someSoul.equals("G")) {
									buttonArray[someRow][someCol].setIcon(rodIconG);
								}else if(someSoul.equals("B")) {
									buttonArray[someRow][someCol].setIcon(rodIconB);
								}else if(someSoul.equals("Bl")) {
									buttonArray[someRow][someCol].setIcon(rodIcon);
									System.out.println("blr");
									try {
										Thread.sleep(3 * 1000);//�~���b
									} catch (InterruptedException e) {
										System.out.println("sleep�̃G���[");
									}
									String msg = "end";
									out.println(msg);
									out.flush();
								}
							}
						}else if(cmd.equals("kill")) {
							String theEnemy = inputTokens[1];
							int enemyRow = Integer.parseInt(theEnemy) / col;
							int enemyCol = Integer.parseInt(theEnemy) % col;
							String M = inputTokens[2];
							int enemyM = Integer.parseInt(M);
							String N = inputTokens[3];
							int enemyN = Integer.parseInt(N);
							buttonArray[enemyRow + enemyM][enemyCol + enemyN].setIcon(fieldIcon);
						}else if(cmd.equals("turn")) {
							nextTurn();
						}
						/*else if(cmd.equals("healMax")) {
							gauge.toMax();
						}else if(cmd.equals("destroy")){
							gauge.toZero();
						}else if(cmd.equals("heal")) {
							gauge.up(10);
						}else if(cmd.equals("damage")) {
							gauge.down(10);
						*/
					}else {
						break;
					}
				}
				socket.close();
			} catch (IOException e) {
				System.err.println("�G���[���������܂���: " + e);
			}
		}
	}

	public static void main(String[] args) {
		Play net = new Play();
		net.setVisible(true);
	}

	public void mouseClicked(MouseEvent e) {//�{�^�����N���b�N�����Ƃ��̏���
		System.out.println("�N���b�N");

		Object theObj = e.getComponent();
		String theClass = theObj.getClass().getName();

		//JButton�N���X�̎��̓���
		if(theClass.equals("javax.swing.JButton")) {
			JButton theButton = (JButton)e.getComponent();//�N���b�N�����I�u�W�F�N�g�𓾂�D�^���Ⴄ�̂ŃL���X�g����
			String theArrayIndex = theButton.getActionCommand();//�{�^���̔z��̔ԍ������o��
			System.out.println(theArrayIndex);
			int temp = Integer.parseInt(theArrayIndex);//���W�ɕϊ�
			x = temp % col;
			y = temp / col;
			System.out.println(x);
			System.out.println(y);
			int s = locationNum % col;//�����̍��W
			int t = locationNum / col;
			System.out.println(s);
			System.out.println(t);

			Icon theIcon = theButton.getIcon();//theIcon�ɂ́C���݂̃{�^���ɐݒ肳�ꂽ�A�C�R��������
			System.out.println(theIcon);//�f�o�b�O�i�m�F�p�j�ɁC�N���b�N�����A�C�R���̖��O���o�͂���

			if(((turn % 6) == myNumberInt) && (theIcon == fieldIcon)) {
				//if(((myChara == "sword") && ) || (myChara == "rod"))
				if(myChara == "sword") {
					System.out.println("ok");
					for(int a = -2; a <= 2; a++) {
						for(int b = -2; b <= 2; b++){
							if(((x + a) == s) && ((y + b) == t)) {
								if(judgeGame() == 0){
									//�퓬(soulColor�X�V)
									for(int m = -1; m <= 1; m++) {
										if((y + m) >= 0 && (y + m) < row) {
											for(int n = -1; n <= 1; n++) {
												if((x + n) >= 0 && (x + n) < col) {
													if(!(m == 0 && n == 0)) {
														Icon checkIcon = buttonArray[y + m][x + n].getIcon();
														System.out.println(checkIcon);
														if(soulColor.equals("M")) {
															if(checkIcon == swordIconY || checkIcon == rodIconY) {
																soulColor = "R";
																//buttonArray[y + m][x + n].setIcon(fieldIcon);
															}else if(checkIcon == swordIconC || checkIcon == rodIconC) {
																soulColor = "B";
															}
														}else if(soulColor.equals("Y")) {
															if(checkIcon == swordIconC || checkIcon == rodIconC) {
																soulColor = "G";
															}else if(checkIcon == swordIconM || checkIcon == rodIconM) {
																soulColor = "R";
															}
														}else if(soulColor.equals("C")) {
															if(checkIcon == swordIconM || checkIcon == rodIconM) {
																soulColor = "B";
															}else if(checkIcon == swordIconY || checkIcon == rodIconY) {
																soulColor = "G";
															}
														}else if(soulColor.equals("R")) {
															if(checkIcon == swordIconC || checkIcon == swordIconB || checkIcon == swordIconG || checkIcon == rodIconC || checkIcon == rodIconB || checkIcon == rodIconG) {
																soulColor = "Bl";
															}
														}else if(soulColor.equals("G")) {
															if(checkIcon == swordIconM || checkIcon == swordIconR || checkIcon == swordIconB || checkIcon == rodIconM || checkIcon == rodIconR || checkIcon == rodIconB) {
																soulColor = "Bl";
															}
														}else if(soulColor.equals("B")) {
															if(checkIcon == swordIconY || checkIcon == swordIconR || checkIcon == swordIconG || checkIcon == rodIconY || checkIcon == rodIconR || checkIcon == rodIconG) {
																soulColor = "Bl";
															}
														}
														System.out.println("x:" + x);
														System.out.println("y:" + y);
														System.out.println("m:" + m);
														System.out.println("n:" + n);

														String msg = "kill" + " " + theArrayIndex + " " + m + " " + n;
														out.println(msg);
														out.flush();
													}
												}
											}
										}
									}
									//���M�����쐬����i��M���ɂ́C���̑��������ԂɃf�[�^�����o���D�X�y�[�X���f�[�^�̋�؂�ƂȂ�j
									String msg = "MOVE" + " " + theArrayIndex + " " + locationNum + " " + myChara + " " + soulColor;
									//�T�[�o�ɏ��𑗂�
									out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
									out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����

									locationNum = temp;

									repaint();//��ʂ̃I�u�W�F�N�g��`�悵����
								}
							}
						}
					}
				}else if(myChara == "rod") {
					for(int a = -1; a <= 1; a++) {
						for(int b = -1; b <= 1; b++){
							if(((x + a) == s) && ((y + b) == t)) {
								if(judgeGame() == 0){
									//�퓬(soulColor�X�V)
									for(int m = -1; m <= 1; m++) {
										if((y + m) >= 0 && (y + m) < row) {
											for(int n = -1; n <= 1; n++) {
												if((x + n) >= 0 && (x + n) < col) {
													if(!(m == 0 && n == 0)) {
														Icon checkIcon = buttonArray[y + m][x + n].getIcon();
														System.out.println(checkIcon);
														if(soulColor.equals("M")) {
															if(checkIcon == swordIconY || checkIcon == rodIconY) {
																soulColor = "R";
																//buttonArray[y + m][x + n].setIcon(fieldIcon);
															}else if(checkIcon == swordIconC || checkIcon == rodIconC) {
																soulColor = "B";
															}
														}else if(soulColor.equals("Y")) {
															if(checkIcon == swordIconC || checkIcon == rodIconC) {
																soulColor = "G";
															}else if(checkIcon == swordIconM || checkIcon == rodIconM) {
																soulColor = "R";
															}
														}else if(soulColor.equals("C")) {
															if(checkIcon == swordIconM || checkIcon == rodIconM) {
																soulColor = "B";
															}else if(checkIcon == swordIconY || checkIcon == rodIconY) {
																soulColor = "G";
															}
														}else if(soulColor.equals("R")) {
															if(checkIcon == swordIconC || checkIcon == swordIconB || checkIcon == swordIconG || checkIcon == rodIconC || checkIcon == rodIconB || checkIcon == rodIconG) {
																soulColor = "Bl";
															}
														}else if(soulColor.equals("G")) {
															if(checkIcon == swordIconM || checkIcon == swordIconR || checkIcon == swordIconB || checkIcon == rodIconM || checkIcon == rodIconR || checkIcon == rodIconB) {
																soulColor = "Bl";
															}
														}else if(soulColor.equals("B")) {
															if(checkIcon == swordIconY || checkIcon == swordIconR || checkIcon == swordIconG || checkIcon == rodIconY || checkIcon == rodIconR || checkIcon == rodIconG) {
																soulColor = "Bl";
															}
														}
														System.out.println("x:" + x);
														System.out.println("y:" + y);
														System.out.println("m:" + m);
														System.out.println("n:" + n);

														String msg = "kill" + " " + theArrayIndex + " " + m + " " + n;
														out.println(msg);
														out.flush();
													}
												}
											}
										}
									}
									//���M�����쐬����i��M���ɂ́C���̑��������ԂɃf�[�^�����o���D�X�y�[�X���f�[�^�̋�؂�ƂȂ�j
									String msg = "MOVE" + " " + theArrayIndex + " " + locationNum + " " + myChara + " " + soulColor;
									//�T�[�o�ɏ��𑗂�
									out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
									out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����

									locationNum = temp;

									repaint();//��ʂ̃I�u�W�F�N�g��`�悵����
								}
							}
						}
					}
				}
			}else{
				System.out.println("�����ɂ͔z�u�ł��܂���");
			}
		}

		//JLabel�̎��̓���
		if(theClass.equals("javax.swing.JLabel")){
			if(e.getSource() == startLabel) {
				layout.show(cardPanel, "select");
			}else if(e.getSource() == ruleLabel) {
				layout.show(cardPanel, "rule");
			}else if(e.getSource() == backLabel) {
				layout.show(cardPanel, "start");
			}else if(e.getSource() == swordLabel) {
				myChara = "sword";
				if(soulColor.equals("M")){
					buttonArray[myY][myX].setIcon(swordIconM);
					myIcon = swordIconM;
				}else if(soulColor.equals("Y")) {
					buttonArray[myY][myX].setIcon(swordIconY);
					myIcon = swordIconY;
				}else if(soulColor.equals("C")){
					buttonArray[myY][myX].setIcon(swordIconC);
					myIcon = swordIconC;
				}
				String msg = "initSet" + " " + locationNum + " " + myChara + " " + soulColor;
				out.println(msg);
				out.flush();
				layout.show(cardPanel, "field");
			}else if(e.getSource() == rodLabel) {
				myChara = "rod";
				if(soulColor.equals("M")){
					buttonArray[myY][myX].setIcon(rodIconM);
					myIcon = rodIconM;
				}else if(soulColor.equals("Y")) {
					buttonArray[myY][myX].setIcon(rodIconY);
					myIcon = rodIconY;
				}else if(soulColor.equals("C")){
					buttonArray[myY][myX].setIcon(rodIconC);
					myIcon = rodIconC;
				}
				String msg = "initSet" + " " + locationNum + " " + myChara + " " + soulColor;
				out.println(msg);
				out.flush();
				layout.show(cardPanel, "field");
			}
		}
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
		//System.out.println("�}�E�X���h���b�O");
	}

	public void mouseMoved(MouseEvent e) {//�}�E�X���I�u�W�F�N�g��ňړ������Ƃ��̏���
		//System.out.println("�}�E�X�ړ�");
	}

	public void keyPressed(KeyEvent e) {
		//
	}

	public void keyReleased(KeyEvent e) {
		//
	}

	public void keyTyped(KeyEvent e) {
		//
	}

	public void actionPerformed(ActionEvent e) {
		//����{�^���p
		if(e.getSource() == exitButton){
			System.exit(0);
		}
		//��ʐ��ڗp
		String cmd = e.getActionCommand();
		layout.show(cardPanel, cmd);
		//���y�Đ�
		if(cmd.equals("select")) {
		}
		if(cmd.equals("field")) {
		}
		if(cmd.equals("end")) {
			themeMusic.stop();
			gameoverMusic = new SoundPlayer("gameover3.wav");
			gameoverMusic.playSound();
		}
	}

	//���y�Đ��E��~
	public class SoundPlayer{
			private AudioFormat format = null;
			private DataLine.Info info = null;
			private Clip clip = null;
			boolean stopFlag = false;
			Thread soundThread = null;
			private boolean loopFlag = false;

			public SoundPlayer(String pathname){
					File file = new File(pathname);
					try{
							format = AudioSystem.getAudioFileFormat(file).getFormat();
							info = new DataLine.Info(Clip.class, format);
							clip = (Clip) AudioSystem.getLine(info);
							clip.open(AudioSystem.getAudioInputStream(file));
							//clip.setLoopPoints(0,clip.getFrameLength());//�������[�v�ƂȂ�
					}catch(Exception e){
							e.printStackTrace();
					}
			}

			public void SetLoop(boolean flag){
					loopFlag = flag;
			}

			public void playSound(){
					soundThread = new Thread(){
							public void run(){
									long time = (long)clip.getFrameLength();//44100�Ŋ���ƍĐ����ԁi�b�j���ł�
									System.out.println("PlaySound time="+time);
									long endTime = System.currentTimeMillis()+time*1000/44100;
									clip.start();
									System.out.println("PlaySound time="+(int)(time/44100));
									while(true){
											if(stopFlag){//stopFlag��true�ɂȂ����I��
													System.out.println("PlaySound stop by stopFlag");
													clip.stop();
													return;
											}
											System.out.println("endTime="+endTime);
											System.out.println("currentTimeMillis="+System.currentTimeMillis());
											if(endTime < System.currentTimeMillis()){//�Ȃ̒������߂�����I��
													System.out.println("PlaySound stop by sound length");
													if(loopFlag) {
															clip.loop(1);//�������[�v�ƂȂ�
													} else {
															clip.stop();
															return;
													}
											}
											try {
													Thread.sleep(100);
											} catch (InterruptedException e) {
													e.printStackTrace();
											}
									}
							}
					};
					soundThread.start();
			}

			public void stop(){
					stopFlag = true;
					System.out.println("StopSound");
			}

	}

	public void checkAlive() {
		System.out.println(locationNum);
		int checkY = locationNum / col;
		int checkX = locationNum % col;
		Icon check = buttonArray[checkY][checkX].getIcon();
		if(check == fieldIcon && myAlive == 1) {
			myAlive -= 1;
		}
		if(((turn % 6) == myNumberInt) && (myAlive == 0)){
			String msg = "turn";
			out.println(msg);
			out.flush();
		}
	}

	public void nextTurn() {
		turn++;
	}

	public int judgeGame(){//�Q�[�������s�\�Ȃ�0�A1�͎��R�̏����A2�͑���̏���
		//�S�ł��ǂ���
		int judgeNum = 0;
		if(myMemberNum == 0 | enemiesNum == 0) {
			if(myMemberNum > enemiesNum) {
				judgeNum = 1;
			}else if(myMemberNum < enemiesNum) {
				judgeNum = 2;
			}
			judgeNum = 0;//�Ō�ɐ�΂��̍s�͏�������
		}
		//�G�w���B
		if((campNum == 0) && (locationNum < col)) {
			try {
				var finMusic = new SoundPlayer("footstep.wav");
				finMusic.playSound();
				Thread.sleep(3 * 1000);//�~���b
			} catch (InterruptedException e) {
			}
			String msg = "end";
			//�T�[�o�ɏ��𑗂�
			out.println(msg);
			out.flush();
		}else if((campNum == 1) && (locationNum >= col * (row - 1))) {
			try {
				var finMusic = new SoundPlayer("footstep.wav");
				finMusic.playSound();
				Thread.sleep(3 * 1000);//�~���b
			} catch (InterruptedException e) {
			}
			String msg = "end";
			//�T�[�o�ɏ��𑗂�
			out.println(msg);
			out.flush();
		}
		return judgeNum;
	}

	/*
	//�L�����X�e�[�^�X���Z�b�g
	private void setChara(int buttonnNum) {
		name = myName;
		switch(buttonnNum) {
			case 0://��m
				attack = 100;
				defense = 100;
				break;
		}
	}
	*/

	/*
	private void toMax(int AttackerInt, int receiverInt) {

	}
	*/

}