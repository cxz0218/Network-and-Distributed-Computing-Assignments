package exercise.HttpWebServer;

import java.io.*;
import java.util.Date;

/**
 * This class is used for constructing HTTP responses and the transfer it
 * to the client.
 * @author 陈心琢-2018303015
 *
 **/
public class HttpResponseConstructor {

    private final BufferedOutputStream serverOutput;
    private final Date date;
    private StringBuilder header;
    /**
     * {@value} Standard line breaks in {@code HTTP} messages.
     */
    public static final String CRLF = "\r\n";
    /**
     * {@value} Specified file root directory in server end.
     */
    public static final String ROOT = "D:\\exercise2\\server\\";
    /**
     * {@value} The buffer size of a byte array.
     */
    public static final int BUFFER_SIZE = 8192;

    public HttpResponseConstructor(BufferedOutputStream outputStream) {
        serverOutput = outputStream;
        date = new Date();
    }

    /**
     * The handler for response construction request.
     * @param responseCode The specified response code.
     * @param httpVersion The http version.
     * @param path The path of a specified file to receive.
     */
    public void constructResponse(int responseCode, float httpVersion, String path) {
        path = path.replaceFirst("/", "");
        path = path.replaceAll("/", "\\\\");
        path = ROOT + path;
        header = new StringBuilder().append("HTTP/").append(httpVersion).append(" ");
        switch (responseCode) {
            case 200: {
                construct200(path);
                break;
            }
            case 201: {
                construct201(path);
                break;
            }
            case 400: {
                construct400();
                break;
            }
            case 404: {
                construct404();
                break;
            }
        }
    }

    /*
     * Construct the http response of code {@code 400}. In this case, a corresponding
     * html file will be sent.
     */
    private void construct400() {
        File file = new File(ROOT + "response\\400.html");
        header.append(400)
                .append(" Bad Request")
                .append(CRLF);
        constructTheShared(file);
    }

    /*
     * Construct the http response of code {@code 201}.
     * @param putPath The path to put the file received from the client.
     */
    private void construct201(String putPath) {
        header.append(201)
                .append(" created")
                .append(CRLF)
                .append("Date: ")
                .append(date)
                .append(CRLF)
                .append("Location: ")
                .append(putPath)
                .append(CRLF)
                .append(CRLF);
        writeHeader();
    }

    /*
     * Construct the http response of code {@code 200}.
     * @param path The path to put the file received from the client.
     */
    private void construct200(String path) {
        if (null == path) {
            construct400();
        }
        assert path != null;
        File file = new File(path);
        if (!file.exists()) {
            construct404();
            return;
        }
        String fileName = file.getName();
        int pos = fileName.indexOf(".");
        String extension = fileName.substring(pos + 1);
        //DataTypeEnum dataTypeEnum;
        String dataType;
        switch (extension) {
            case "jpg":
            case "jpeg": {
                dataType = "image/jpeg";
                break;
            }
            case "png": {
                dataType = "image/png";
                break;
            }
            case "html":
            case "htm": {
                dataType = "text/html";
                break;
            }
            case "txt": {
                dataType = "text/plain";
                break;
            }
            default: {
                dataType = "application/OCTET_STREAM";
            }
        }
        header.append(200)
                .append(" OK")
                .append(CRLF);
        header.append("Date: ")
                .append(date)
                .append(CRLF);
        header.append("Content-Type: ")
                .append(dataType)
                .append(";")
                .append("charset=ISO-8859-1")
                .append(CRLF);
        header.append("Content-Length: ")
                .append(file.length())
                .append(CRLF)
                .append(CRLF);
        writeHeader();
        writeBody(file);
    }

    /*
     * Construct the http response of code {@code 404}.
     */
    private void construct404() {
        File file = new File(ROOT + "response\\404.html");
        header.append(404)
                .append(" Not Found")
                .append(CRLF);
        constructTheShared(file);
        writeHeader();
    }

    /*
     * A method to construct the shared parts in a response.
     */
    private void constructTheShared(File file) {
        header.append("Date: ")
                .append(date)
                .append(CRLF);
        header.append("Content-Type: ")
                .append("text/html")
                .append(";")
                .append("charset=ISO-8859-1")
                .append(CRLF);
        header.append("Content-Length: ")
                .append(file.length())
                .append(CRLF)
                .append(CRLF);
        writeHeader();
        writeBody(file);
    }

    /*
     * This method is used to construct the body of response including {@code Bad Request}
     * and {@code Not Found}. The body contains a corresponding html file in string format.
     *
     * @param file The corresponding html file to send to the client.
     */
    private void writeBody(File file) {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            byte[] data = new byte[BUFFER_SIZE];
            int len;
            while ((len = bis.read(data)) != -1) {
                serverOutput.write(data, 0, len);
            }
            serverOutput.flush();
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * A method to write the header to client.
     */
    private void writeHeader() {
        try {
            serverOutput.write(header.toString().getBytes());
            serverOutput.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

