import java.util.Random;
import javax.swing.*;

public class Map {

  public static void random(JButton[][] buttonArray, int row, int col) {

    //�}�b�v�ԍ��������_���Ō���
    Random rand = new Random();
    int mapNum = rand.nextInt(2);

    //��{�ƂȂ�t�B�[���h��z�u
    for(int i = 0; i < row; i++) {
      for(int j = 0; j < col; j++) {
        buttonArray[i][j].setIcon(Play.fieldIcon);//2�d����o���炱���̈��̍s�ŃA�C�R��������
      }
    }

    //�}�b�v�ݒ�
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

    /*(�v���C���[�S���ňႤ�}�b�v�ɂȂ��Ă��܂��̂ŊJ�����f)
    //mupNum�̒l�Ń}�b�v�I��
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
