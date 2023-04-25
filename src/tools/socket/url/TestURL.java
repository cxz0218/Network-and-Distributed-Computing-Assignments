package socket.url;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * URL test
 *
 * @author wben
 *
 * cxz:但是改代码保存下来的html打开并不完全一致，甚至相差很大，why？？？
 * 猜测：因为没有获得该网页的其他url？？
 * 2023.2.9
 *
 */
public class TestURL {
    public static void main(String[] args) {
        BufferedReader br = null;
        FileWriter fw = null;
        try {
            URL u = new URL("https://www.baidu.com/"); //获得网络资源
            InputStream fis = u.openStream(); //获得URL的输入数据流

            //InputStreamReader fr = new InputStreamReader(fis, "gbk");
            InputStreamReader fr = new InputStreamReader(fis);
            br = new BufferedReader(fr); //读取并封装字符流

            fw = new FileWriter("D:\\idea-workspace\\Echo\\src\\socket\\url\\baidu.html"); //保存为文件
            String s = null;
            while ((s = br.readLine()) != null) { //操作
                System.out.println(s);
                fw.write(s);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                fw.flush();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
