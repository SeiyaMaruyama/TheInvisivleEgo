import java.net.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.Graphics;
import java.awt.Color;
import java.io.File;//音楽再生時に必要
import javax.sound.sampled.AudioFormat;//音楽再生時に必要
import javax.sound.sampled.AudioSystem;//音楽再生時に必要
import javax.sound.sampled.Clip;//音楽再生時に必要
import javax.sound.sampled.DataLine;//音楽再生時に必要

public class Play extends JFrame implements MouseListener,MouseMotionListener, KeyListener, ActionListener {

	String myName;
	int myNumberInt;
	int roomNum;//対戦する部屋の部屋番号
	int campNum;//対戦部屋内での、自分の陣営を番号
	int myCamp;//敵味方の区別
	private String myColor;
	private int turn = 0;
	int myMemberNum = 3;
	int enemiesNum = 3;
	int myAlive = 1;

	//画面設定用
	private static int windowWidth = 1290;
	private static int windowHeight = 680;
	private JPanel cardPanel;
	private CardLayout layout;
	private Container contentPane;
	public static JButton buttonArray[][];//ボタン用の配列
	private JButton exitButton;
	private Gauge gauge;
	JLabel startLabel;
	JLabel ruleLabel;
	JLabel backLabel;
	JLabel swordLabel;
	JLabel rodLabel;
	String turnSt;
	JLabel turnLabel;
	//ダブルバッファリング用
	private Image bufferImage = null;
	private Graphics bufferGraphics;
	//アイコンの設定
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
	//マップの行列
	public static int row = 12;//偶奇自由
	public static int col = 15;//「奇数」で設定すること！
	private int x, y;

	//自分の座標
	private int myX;
	private int myY;
	private int locationNum;
	private int moveNum;
	int nowBnumRow;
	int nowBnumCol;

	SoundPlayer themeMusic;//どこからでもアクセスできるように，クラスのメンバとして宣言
	SoundPlayer selectMusic;//どこからでもアクセスできるように，クラスのメンバとして宣言
	SoundPlayer gameoverMusic;

	private PrintWriter out;//出力用のライター

	//キャラステータス
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
	//各キャラデータ
	String name0,name1,name2,name3,name4,name5;
	int maxHP0,maxHP1,maxHP2,maxHP3,maxHP4,maxHP5;
	int hp0,hp1,hp2,hp3,hp4,hp5;
	int attack0,attack1,attack2,attack3,attack4,attack5;
	int defense0,defense1,defense2,defense3,defense4,defense5;
	String soulColor0,soulColor1,soulColor2,soulColor3,soulColor4,soulColor5;
	int heal0,heal1,heal2,heal3,heal4,heal5;
	*/


	public Play() {
		//名前の入力ダイアログを開く
		myName = JOptionPane.showInputDialog(null,"名前を入力してください","名前の入力",JOptionPane.QUESTION_MESSAGE);
		if(myName.equals("")){
			myName = "No name";//名前がないときは，"No name"とする
		}

		//IPアドレス入力ダイアログ
		String myAddress = JOptionPane.showInputDialog(null,"IPアドレスを入力してください","IPアドレスの入力",JOptionPane.QUESTION_MESSAGE);
		if(myAddress.equals("")){
			myAddress = "localhost";//IPアドレスがないときは，"localhost"とする
		}

		//ウィンドウを作成・設定する
		setTitle("The Invisible Ego");//ウィンドウのタイトルを設定する
		setSize(windowWidth, windowHeight);//ウィンドウのサイズを設定する
		//setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);//ウィンドウを閉じるときに，正しく閉じるように設定する

		//スタートパネル
		JPanel startPanel = new JPanel();
		startPanel.setLayout(null);
		startPanel.setBackground(Color.BLACK);
		var titleImage = new ImageIcon("title.png");
		var titleLabel = new JLabel(titleImage);
		startPanel.add(titleLabel);
		titleLabel.setBounds(295,200,700,70);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）
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

		//遊び方パネル
		var rulePanel = new JPanel();
		rulePanel.setLayout(null);
		rulePanel.setBackground(Color.BLACK);
		var explanation1 = new JLabel("ルール説明");
		explanation1.setForeground(Color.WHITE);
		explanation1.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 24));
		explanation1.setBounds(550, 50, 140, 30);
		rulePanel.add(explanation1);
		var explanation2 = new JLabel("1. クリア条件は開始地点と真反対の行への到達。");
		explanation2.setForeground(Color.WHITE);
		explanation2.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 24));
		explanation2.setBounds(125, 150, 1000, 30);
		rulePanel.add(explanation2);
		var explanation3 = new JLabel("2. 何かに近接すると浸食し、敵の場合は色を吸収する。");
		explanation3.setForeground(Color.WHITE);
		explanation3.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 24));
		explanation3.setBounds(125, 225, 1000, 30);
		rulePanel.add(explanation3);
		var explanation4 = new JLabel("  ↑ 色の三原色に準ずる。");
		explanation4.setForeground(Color.WHITE);
		explanation4.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 24));
		explanation4.setBounds(125, 300, 500, 30);
		rulePanel.add(explanation4);
		var explanation5 = new JLabel("3. 「黒」になるとゲームオーバー。");
		explanation5.setForeground(Color.WHITE);
		explanation5.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 24));
		explanation5.setBounds(125, 375, 500, 30);
		rulePanel.add(explanation5);
		var explanation6 = new JLabel("4. 通った場所が使えなくなることがある。");
		explanation6.setForeground(Color.WHITE);
		explanation6.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 24));
		explanation6.setBounds(125, 450, 520, 30);
		rulePanel.add(explanation6);
		var backImage = new ImageIcon("back.png");
		backLabel = new JLabel(backImage);
		rulePanel.add(backLabel);
		backLabel.setBounds(565,520,160,32);
		backLabel.addMouseListener(this);

		//キャラ選択パネル
		JPanel selectPanel = new JPanel();
		selectPanel.setLayout(null);
		selectPanel.setBackground(Color.BLACK);
		var comment = new JLabel("自分のジョブを選んでください");
		comment.setForeground(Color.WHITE);
		comment.setBounds(440, 100, 600, 30);
		comment.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
		selectPanel.add(comment);
		swordLabel = new JLabel(swordIcon);
		swordLabel.setBounds(450, 270, 90, 90);
		swordLabel.addMouseListener(this);
		selectPanel.add(swordLabel);
		rodLabel = new JLabel(rodIcon);
		rodLabel.setBounds(740, 270, 90, 90);
		rodLabel.addMouseListener(this);
		selectPanel.add(rodLabel);
		var swordExplanation = new JLabel("　戦士：行動範囲が2で、攻撃範囲が1です。");
		swordExplanation.setForeground(Color.WHITE);
		swordExplanation.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 24));
		swordExplanation.setBounds(410, 400, 500, 30);
		selectPanel.add(swordExplanation);
		var rodExplanation = new JLabel("魔術師：行動範囲が1で、攻撃範囲が2です。");
		rodExplanation.setForeground(Color.WHITE);
		rodExplanation.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 24));
		rodExplanation.setBounds(410, 450, 500, 30);
		selectPanel.add(rodExplanation);

		//フィールドパネル
		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout(null);
		fieldPanel.setBackground(Color.BLACK);
		//gauge = new Gauge(800,800,300,300,200,100);


		//ゲームオーバーパネル
		JPanel endPanel = new JPanel();
		endPanel.setLayout(null);
		endPanel.setBackground(Color.BLACK);
		//画像付きラベルの作成
		ImageIcon gameoverIcon = new ImageIcon("gameover.png");//なにか画像ファイルをダウンロードしておく
		JLabel gameoverLabel = new JLabel(gameoverIcon);
		endPanel.add(gameoverLabel);
		gameoverLabel.setBounds(0,0,windowWidth,windowHeight);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）
		gameoverLabel.addMouseListener(this);//ボタンをマウスでさわったときに反応するようにする
		gameoverLabel.setForeground(Color.WHITE); //文字色の設定．Colorの設定は，このページを見て下さい　http://www.javadrive.jp/tutorial/color/

		//人数超過による終了パネル
		JPanel exitPanel = new JPanel();
		exitPanel.setLayout(null);
		exitPanel.setBackground(Color.GRAY);
		var over = new JLabel("参加人数上限を超えました。");
		over.setForeground(Color.BLACK);
		over.setBounds(440, 300, 600, 30);
		over.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
		exitPanel.add(over);

		//CardLayout用パネル
		cardPanel = new JPanel();
		layout = new CardLayout();
		cardPanel.setLayout(layout);

		cardPanel.add(startPanel, "start");
		cardPanel.add(rulePanel, "rule");
		cardPanel.add(selectPanel, "select");
		cardPanel.add(fieldPanel, "field");
		cardPanel.add(endPanel, "end");
		cardPanel.add(exitPanel, "exit");

		//カード切り替え用ボタン
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

		//終了ボタン
		exitButton = new JButton("exit");
		exitButton.addActionListener(this);
		exitButton.setBounds(600, 500, 60, 36);
		exitPanel.add(exitButton);

		/*
		//遷移確認用
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

		//cardPaneとカード切り替え用ボタンをJFrameに配置
		contentPane = getContentPane();//フレームのペインを取得する
		contentPane.add(cardPanel);
		//contentPane.add(btnPanel, BorderLayout.SOUTH);


		//ボタンの生成
		buttonArray = new JButton[row][col];//ボタンの配列を個作成する
		for(int j = 0; j < row; j++) {
			for(int i = 0; i < col; i++) {
				buttonArray[j][i] = new JButton(fieldIcon);//ボタンにアイコンを設定する
				fieldPanel.add(buttonArray[j][i]);//ペインに貼り付ける
				buttonArray[j][i].setBounds(i*45+30, j*45+50, 45, 45);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）
				buttonArray[j][i].addMouseListener(this);//ボタンをマウスでさわったときに反応するようにする
				buttonArray[j][i].addMouseMotionListener(this);//ボタンをマウスで動かそうとしたときに反応するようにする
				buttonArray[j][i].setActionCommand(Integer.toString(j*col + i));//ボタンに配列の情報を付加する（ネットワークを介してオブジェクトを識別するため）
			}
			System.out.println();
		}


		//ボード初期化
		Map.random(buttonArray, row, col);

		//キャライメージ作成
		jackImg = new ImageIcon("jack.gif");
		var jackLabel = new JLabel(jackImg);
		//fieldPanel.add(jackLabel);
		jackLabel.setBounds(1000,300,80,80);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）
		jackLabel.addMouseListener(this);//ボタンをマウスでさわったときに反応するようにする
		jackLabel.setForeground(Color.WHITE);


		//サーバに接続する
		Socket socket = null;
		try {
			//"localhost"は，自分内部への接続．localhostを接続先のIP Address（"133.42.155.201"形式）に設定すると他のPCのサーバと通信できる
			//10000はポート番号．IP Addressで接続するPCを決めて，ポート番号でそのPC上動作するプログラムを特定する
			socket = new Socket(myAddress, 10000);
		} catch (UnknownHostException e) {
			System.err.println("ホストの IP アドレスが判定できません: " + e);
		} catch (IOException e) {
				System.err.println("エラーが発生しました: " + e);
		}

		MesgRecvThread mrt = new MesgRecvThread(socket, myName);//受信用のスレッドを作成する
		mrt.start();//スレッドを動かす（Runが動く）
	}

	//メッセージ受信のためのスレッド
	public class MesgRecvThread extends Thread {

		Socket socket;
		String myName;

		public MesgRecvThread(Socket s, String n){
			socket = s;
			myName = n;
		}

		//通信状況を監視し，受信データによって動作する
		public void run() {
			try{
				InputStreamReader sisr = new InputStreamReader(socket.getInputStream());
				BufferedReader br = new BufferedReader(sisr);
				out = new PrintWriter(socket.getOutputStream(), true);
				out.println(myName);//接続の最初に名前を送る
				String myNumberStr = br.readLine();//コマの色と先手後手を設定
				myNumberInt = Integer.parseInt(myNumberStr) - 1;
				roomNum = myNumberInt / 6;
				campNum = myNumberInt / 3;
				//7人以上が参加しようとしたら過剰な人を弾く
				if(roomNum != 0) {
					layout.show(cardPanel, "exit");
				}

				//スタート画面の音楽を流す
				themeMusic = new SoundPlayer("theme.wav");
				themeMusic.SetLoop(true);//ＢＧＭとして再生を繰り返す
				themeMusic.playSound();

				//myNumberIntの値で陣営分け
				if (campNum == 0) {
					myCamp = 0;
				}else if(campNum == 1) {
					myCamp = 1;
				}
				//魂の色設定
				if(myNumberInt == 0 | myNumberInt == 3) {
					soulColor = "M";
				}else if(myNumberInt == 1 | myNumberInt == 4) {
					soulColor = "Y";
				}else if(myNumberInt == 2 | myNumberInt == 5) {
					soulColor = "C";
				}
				//最初の自分の座標
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
					String inputLine = br.readLine();//データを一行分だけ読み込んでみる
					if (inputLine != null) {//読み込んだときにデータが読み込まれたかどうかをチェックする
						System.out.println(inputLine);//デバッグ（動作確認用）にコンソールに出力する
						String[] inputTokens = inputLine.split(" ");	//入力データを解析するために、スペースで切り分ける
						String cmd = inputTokens[0];//コマンドの取り出し．１つ目の要素を取り出す
						if(cmd.equals("end")){//相手からゲーム終了の知らせを受けて結果表示
							themeMusic.stop();
							layout.show(cardPanel, cmd);
							gameoverMusic = new SoundPlayer("gameover3.wav");
							gameoverMusic.playSound();
							break;
						}
						if(cmd.equals("MOVE")){//cmdの文字と"MOVE"が同じか調べる．同じ時にtrueとなる
							//MOVEの時の処理(コマの移動の処理)
							String theBName = inputTokens[1];//ボタンの名前（番号）の取得
							int theBnumRow = Integer.parseInt(theBName) / col;//ボタンの名前を数値に変換する
							int theBnumCol = Integer.parseInt(theBName) % col;//ボタンの名前を数値に変換する
							String nowBName = inputTokens[2];
							nowBnumRow = Integer.parseInt(nowBName) / col;//ボタンの名前を数値に変換する
							nowBnumCol = Integer.parseInt(nowBName) % col;//ボタンの名前を数値に変換する
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
							checkAlive();//自分のコマが生きているか(死んでいたらパス)
							/*
							int x = Integer.parseInt(inputTokens[2]);//数値に変換する
							int y = Integer.parseInt(inputTokens[3]);//数値に変換する
							buttonArray[theBnumRow][theBnumCol].setLocation(x,y);//指定のボタンを位置をx,yに設定する
							*/
						}/*else if(cmd.equals("PLACE")) {//cmdの文字と"PLACE"が同じか調べる．同じ時にtrueとなる
							//PLACEの時の処理(コマを置く処理)
							String theBName = inputTokens[1];//ボタンの名前（番号）の取得
							int theBnumRow = Integer.parseInt(theBName) / col;//ボタンの名前を数値に変換する
							int theBnumCol = Integer.parseInt(theBName) % col;//ボタンの名前を数値に変換する
							int theColor = Integer.parseInt(inputTokens[2]);//数値に変換する
							//if(theColor ==  myColor.equals("M")){
								buttonArray[theBnumRow][theBnumCol].setIcon(myIcon);//blackIconに設定する
								//ゲームが続行可能か調べる
									if(judgeGame() == 0){//相手がパスとなる場合に自分が打てるか調べる
										continue;
									}else if(judgeGame() == 1){//} {
										String msg = "end";//相手にゲーム終了の通知
										//サーバに情報を送る
										out.println(msg);//送信データをバッファに書き出す
										out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
										break;
									}else if(judgeGame() == 2) {
										String msg = "end";//相手にゲーム終了の通知
										//サーバに情報を送る
										out.println(msg);//送信データをバッファに書き出す
										out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
										break;
									}
								}else {
									String msg = "change";//ターン交換用のメッセージ作成
									//サーバに情報を送る
									out.println(msg);//送信データをバッファに書き出す
									out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
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
										Thread.sleep(3 * 1000);//ミリ秒
									} catch (InterruptedException e) {
										System.out.println("sleepのエラー");
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
										Thread.sleep(3 * 1000);//ミリ秒
									} catch (InterruptedException e) {
										System.out.println("sleepのエラー");
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
				System.err.println("エラーが発生しました: " + e);
			}
		}
	}

	public static void main(String[] args) {
		Play net = new Play();
		net.setVisible(true);
	}

	public void mouseClicked(MouseEvent e) {//ボタンをクリックしたときの処理
		System.out.println("クリック");

		Object theObj = e.getComponent();
		String theClass = theObj.getClass().getName();

		//JButtonクラスの時の動き
		if(theClass.equals("javax.swing.JButton")) {
			JButton theButton = (JButton)e.getComponent();//クリックしたオブジェクトを得る．型が違うのでキャストする
			String theArrayIndex = theButton.getActionCommand();//ボタンの配列の番号を取り出す
			System.out.println(theArrayIndex);
			int temp = Integer.parseInt(theArrayIndex);//座標に変換
			x = temp % col;
			y = temp / col;
			System.out.println(x);
			System.out.println(y);
			int s = locationNum % col;//自分の座標
			int t = locationNum / col;
			System.out.println(s);
			System.out.println(t);

			Icon theIcon = theButton.getIcon();//theIconには，現在のボタンに設定されたアイコンが入る
			System.out.println(theIcon);//デバッグ（確認用）に，クリックしたアイコンの名前を出力する

			if(((turn % 6) == myNumberInt) && (theIcon == fieldIcon)) {
				//if(((myChara == "sword") && ) || (myChara == "rod"))
				if(myChara == "sword") {
					System.out.println("ok");
					for(int a = -2; a <= 2; a++) {
						for(int b = -2; b <= 2; b++){
							if(((x + a) == s) && ((y + b) == t)) {
								if(judgeGame() == 0){
									//戦闘(soulColor更新)
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
									//送信情報を作成する（受信時には，この送った順番にデータを取り出す．スペースがデータの区切りとなる）
									String msg = "MOVE" + " " + theArrayIndex + " " + locationNum + " " + myChara + " " + soulColor;
									//サーバに情報を送る
									out.println(msg);//送信データをバッファに書き出す
									out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する

									locationNum = temp;

									repaint();//画面のオブジェクトを描画し直す
								}
							}
						}
					}
				}else if(myChara == "rod") {
					for(int a = -1; a <= 1; a++) {
						for(int b = -1; b <= 1; b++){
							if(((x + a) == s) && ((y + b) == t)) {
								if(judgeGame() == 0){
									//戦闘(soulColor更新)
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
									//送信情報を作成する（受信時には，この送った順番にデータを取り出す．スペースがデータの区切りとなる）
									String msg = "MOVE" + " " + theArrayIndex + " " + locationNum + " " + myChara + " " + soulColor;
									//サーバに情報を送る
									out.println(msg);//送信データをバッファに書き出す
									out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する

									locationNum = temp;

									repaint();//画面のオブジェクトを描画し直す
								}
							}
						}
					}
				}
			}else{
				System.out.println("そこには配置できません");
			}
		}

		//JLabelの時の動き
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

	public void mouseEntered(MouseEvent e) {//マウスがオブジェクトに入ったときの処理
		//System.out.println("マウスが入った");
	}

	public void mouseExited(MouseEvent e) {//マウスがオブジェクトから出たときの処理
		//System.out.println("マウス脱出");
	}

	public void mousePressed(MouseEvent e) {//マウスでオブジェクトを押したときの処理（クリックとの違いに注意）
		//System.out.println("マウスを押した");
	}

	public void mouseReleased(MouseEvent e) {//マウスで押していたオブジェクトを離したときの処理
		//System.out.println("マウスを放した");
	}

	public void mouseDragged(MouseEvent e) {//マウスでオブジェクトとをドラッグしているときの処理
		//System.out.println("マウスをドラッグ");
	}

	public void mouseMoved(MouseEvent e) {//マウスがオブジェクト上で移動したときの処理
		//System.out.println("マウス移動");
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
		//閉じるボタン用
		if(e.getSource() == exitButton){
			System.exit(0);
		}
		//画面推移用
		String cmd = e.getActionCommand();
		layout.show(cardPanel, cmd);
		//音楽再生
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

	//音楽再生・停止
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
							//clip.setLoopPoints(0,clip.getFrameLength());//無限ループとなる
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
									long time = (long)clip.getFrameLength();//44100で割ると再生時間（秒）がでる
									System.out.println("PlaySound time="+time);
									long endTime = System.currentTimeMillis()+time*1000/44100;
									clip.start();
									System.out.println("PlaySound time="+(int)(time/44100));
									while(true){
											if(stopFlag){//stopFlagがtrueになった終了
													System.out.println("PlaySound stop by stopFlag");
													clip.stop();
													return;
											}
											System.out.println("endTime="+endTime);
											System.out.println("currentTimeMillis="+System.currentTimeMillis());
											if(endTime < System.currentTimeMillis()){//曲の長さを過ぎたら終了
													System.out.println("PlaySound stop by sound length");
													if(loopFlag) {
															clip.loop(1);//無限ループとなる
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

	public int judgeGame(){//ゲームが続行可能なら0、1は自軍の勝ち、2は相手の勝ち
		//全滅かどうか
		int judgeNum = 0;
		if(myMemberNum == 0 | enemiesNum == 0) {
			if(myMemberNum > enemiesNum) {
				judgeNum = 1;
			}else if(myMemberNum < enemiesNum) {
				judgeNum = 2;
			}
			judgeNum = 0;//最後に絶対この行は消すこと
		}
		//敵陣到達
		if((campNum == 0) && (locationNum < col)) {
			try {
				var finMusic = new SoundPlayer("footstep.wav");
				finMusic.playSound();
				Thread.sleep(3 * 1000);//ミリ秒
			} catch (InterruptedException e) {
			}
			String msg = "end";
			//サーバに情報を送る
			out.println(msg);
			out.flush();
		}else if((campNum == 1) && (locationNum >= col * (row - 1))) {
			try {
				var finMusic = new SoundPlayer("footstep.wav");
				finMusic.playSound();
				Thread.sleep(3 * 1000);//ミリ秒
			} catch (InterruptedException e) {
			}
			String msg = "end";
			//サーバに情報を送る
			out.println(msg);
			out.flush();
		}
		return judgeNum;
	}

	/*
	//キャラステータスをセット
	private void setChara(int buttonnNum) {
		name = myName;
		switch(buttonnNum) {
			case 0://戦士
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