import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

//画面設定用のクラスメソッド
public class Screen extends JFrame implements ActionListener, MouseListener,MouseMotionListener {

    JPanel cardPanel;
    CardLayout layout;

    public static void set() {//ウィンドウ作成
        var frame = new Screen();
        frame.setTitle("弱肉強食(仮)");//ウィンドウのタイトルを設定する
        frame.setSize(1920,1040);//ウィンドウのサイズを設定する
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);//ウィンドウを閉じるときに，正しく閉じるように設定する
        frame.setVisible(true);
    }

    public Screen() {

        //スタートパネル
        JPanel startPanel = new JPanel();
        JButton startBtn = new JButton("start");
        startPanel.add(startBtn);


        //フィールドパネル
        JPanel fieldPanel = new JPanel();
        JButton fieldBtn = new JButton("field");
        fieldPanel.add(fieldBtn);


        //CardLayout用パネル
        cardPanel = new JPanel();
        layout = new CardLayout();
        cardPanel.setLayout(layout);

        cardPanel.add(startPanel, "start");
        cardPanel.add(fieldPanel, "field");


        //カード切り替え用ボタン
        JButton firstButton = new JButton("start");
        firstButton.addActionListener(this);
        firstButton.setActionCommand("field");

        JButton secondButton = new JButton("field");
        secondButton.addActionListener(this);
        secondButton.setActionCommand("start");

        JPanel btnPanel = new JPanel();
        btnPanel.add(firstButton);
        btnPanel.add(secondButton);


        //cardPaneとカード切り替え用ボタンをJFrameに配置
        Container contentPane = getContentPane();//フレームのペインを取得する
        contentPane.setLayout(null);//自動レイアウトの設定を行わない
        contentPane.add(cardPanel);
        contentPane.add(btnPanel);


        //ボタンの生成
		Play.buttonArray = new JButton[Play.row][Play.col];//ボタンの配列を５個作成する[0]から[7]まで使える
		for(int i = 0; i < Play.col; i++) {
			for(int j = 0; j < Play.row; j++) {
				Play.buttonArray[j][i] = new JButton(Play.baseIcon);//ボタンにアイコンを設定する
				fieldPanel.add(Play.buttonArray[j][i]);//ペインに貼り付ける
				Play.buttonArray[j][i].setBounds(i*45+10,j*45+10,45,45);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）
				Play.buttonArray[j][i].addMouseListener(this);//ボタンをマウスでさわったときに反応するようにする
				Play.buttonArray[j][i].addMouseMotionListener(this);//ボタンをマウスで動かそうとしたときに反応するようにする
				Play.buttonArray[j][i].setActionCommand(Integer.toString(j*Play.row + i));//ボタンに配列の情報を付加する（ネットワークを介してオブジェクトを識別するため）
			}
			System.out.println();
		}

		//ボード初期化
		Map.random(Play.buttonArray, Play.row, Play.col);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        layout.show(cardPanel, cmd);
    }

    public void mouseClicked(MouseEvent e) {//ボタンをクリックしたときの処理
		//
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
		//
	}

	public void mouseMoved(MouseEvent e) {//マウスがオブジェクト上で移動したときの処理
		//
	}
}