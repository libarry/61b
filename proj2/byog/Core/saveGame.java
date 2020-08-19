package byog.Core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class saveGame {
    private String fileName;

    public saveGame(String fileName){
        this.fileName=fileName;
    }

    /*
     * 将person对象保存到文件中
     * params:
     * 	p:person类对象
     */
    public void saveObjToFile(gameInfo p){
        try {
            //写对象流的对象
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(fileName));

            oos.writeObject(p);                 //将Person对象p写入到oos中

            oos.close();                        //关闭文件流
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * 从文件中读出对象，并且返回Person对象
     */
    public gameInfo getObjFromFile(){
        try {
            ObjectInputStream ois=new ObjectInputStream(new FileInputStream(fileName));

            gameInfo person=(gameInfo) ois.readObject();              //读出对象

            return person;                                       //返回对象
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}

