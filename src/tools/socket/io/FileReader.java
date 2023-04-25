package socket.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * java I/O 示例
 *
 * @author wben
 *
 */
public class FileReader {

    public static void main(String[] args) {
        File f=new File("D:\\idea-workspace\\Echo\\src\\socket\\io\\message.txt");
        BufferedReader br = null;
        try {
            br=new BufferedReader(
                    //new InputStreamReader(new FileInputStream(f), "gbk"));
                    new InputStreamReader(new FileInputStream(f)));//可以输出中文
            String line = null;
            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
