import java.util.Random;
import javax.swing.*;

public class Map {

  public static void random(JButton[][] buttonArray, int row, int col) {

    //マップ番号をランダムで決定
    Random rand = new Random();
    int mapNum = rand.nextInt(2);

    //基本となるフィールドを配置
    for(int i = 0; i < row; i++) {
      for(int j = 0; j < col; j++) {
        buttonArray[i][j].setIcon(Play.fieldIcon);//2重判定出たらここの一つ上の行でアイコンを除去
      }
    }

    //マップ設定
    buttonArray[5][3].setIcon(Play.mountainIcon);
    buttonArray[5][4].setIcon(Play.mountainIcon);
    buttonArray[5][5].setIcon(Play.mountainIcon);
    buttonArray[5][6].setIcon(Play.mountainIcon);
    buttonArray[4][8].setIcon(Play.mountainIcon);
    buttonArray[4][9].setIcon(Play.mountainIcon);
    buttonArray[4][10].setIcon(Play.mountainIcon);
    buttonArray[6][8].setIcon(Play.mountainIcon);
    buttonArray[6][9].setIcon(Play.mountainIcon);
    buttonArray[6][10].setIcon(Play.mountainIcon);
    buttonArray[7][4].setIcon(Play.mountainIcon);
    buttonArray[8][4].setIcon(Play.mountainIcon);

    /*(プレイヤー全員で違うマップになってしまうので開発中断)
    //mupNumの値でマップ選択
    switch (mapNum) {
      case 0:
        buttonArray[3][3].setIcon(Play.mountainIcon);
        buttonArray[3][4].setIcon(Play.mountainIcon);
        buttonArray[4][3].setIcon(Play.mountainIcon);
        buttonArray[4][4].setIcon(Play.swordIcon);
        break;
      case 1:
        buttonArray[3][3].setIcon(Play.mountainIcon);
        buttonArray[3][4].setIcon(Play.mountainIcon);
        buttonArray[4][3].setIcon(Play.mountainIcon);
        buttonArray[4][4].setIcon(Play.mountainIcon);
        break;
    }
    */
  }
}
