package exercise.exercise2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.util.Date;

/**
 * Class <em>HttpClient</em> is a class representing a simple HTTP client.
 *
 * @author wben
 */

public class HttpClient {

    /**
     * default HTTP port is port 80
     */
    private static int port = 80;

    /**
     * Allow a maximum buffer size of 8192 bytes
     */
    private static int buffer_size = 8192;

    /**
     * Response is stored in a byte array.
     */
    private byte[] buffer;

    /**
     * My socket to the world.
     */
    Socket socket = null;

    /**
     * Default port is 80.
     */
    private static final int PORT = 80;

    /**
     * Output stream to the socket.
     */
    BufferedOutputStream ostream = null;

    /**
     * Input stream from the socket.
     */
    BufferedInputStream istream = null;

    /**
     * StringBuffer storing the header
     */
    private StringBuffer header = null;

    /**
     * StringBuffer storing the response.
     */
    private StringBuffer response = null;

    /**
     * String to represent the Carriage Return and Line Feed character sequence.
     */
    static private String CRLF = "\r\n";
    /**
     * {@value} Specified file root directory in client end.
     */
    public static final String CLIENT_ROOT = "D:\\exercise2\\client\\";

    /**
     * HttpClient constructor;
     */
    public HttpClient() {
        buffer = new byte[buffer_size];
        header = new StringBuffer();
        response = new StringBuffer();
    }

    /**
     * <em>connect</em> connects to the input host on the default http port -- port
     * 80. This function opens the socket and creates the input and output streams
     * used for communication.
     */
    public void connect(String host) throws Exception {

        /**
         * Open my socket to the specified host at the default port.
         */
        socket = new Socket(host, PORT);

        /**
         * Create the output stream.
         */
        ostream = new BufferedOutputStream(socket.getOutputStream());

        /**
         * Create the input stream.
         */
        istream = new BufferedInputStream(socket.getInputStream());
    }

    /**
     * <em>processGetRequest</em> process the input GET request.
     */
    public void processGetRequest(String request) throws Exception {
        /**
         * Send the request to the server.
         */
        request += CRLF + CRLF;
        buffer = request.getBytes();
        ostream.write(buffer, 0, request.length());
        ostream.flush();
        /**
         * waiting for the response.
         */
        processResponse();
    }

    /**
     * <em>processPutRequest</em> process the input PUT request.
     */
    public boolean processPutRequest(String request) throws Exception {
        // =======start your job here============//
        String path = request.split(" ")[1];
        path = path.replaceFirst("/", "");
        path = path.replaceAll("/", "\\\\");
        path = CLIENT_ROOT + path;
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        String[] dir = path.split("\\\\");
        String fileName = dir[dir.length - 1];
        int pos = fileName.indexOf(".");
        String extension = fileName.substring(pos + 1);
        StringBuilder message = new StringBuilder(request);
        message.append(CRLF).append("Date: ").append(new Date()).append("Content-Type: ");
        switch (extension) {
            case "jpg":
            case "jpeg": {
                message.append("image/jpeg");
                break;
            }
            case "png": {
                message.append("image/png");
                break;
            }
            case "html":
            case "htm": {
                message.append("text/html");
                break;
            }

            case "txt": {
                message.append("text/plain");
                break;
            }
            default: {
                message.append("application/OCTET_STREAM");
            }
        }
        message.append(";charset=ISO-8859-1").append(CRLF);
        message.append("Content-Length: ").append(file.length()).append(CRLF).append(CRLF);
        byte[] messageByte = message.toString().getBytes();
        ostream.write(messageByte);
        ostream.flush();
        byte[] body = new byte[buffer_size];
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        int length;
        while ((length = bis.read(body)) != -1) {
            ostream.write(body, 0, length);
        }
        ostream.flush();
        System.out.println("Put request successfully sent to server.");
        processResponse();
        return true;
        // =======end of your job============//
    }

    /**
     * <em>processResponse</em> process the server response.
     *
     */
    public void processResponse() throws Exception {
        int last = 0, c = 0;
        /**
         * Process the header and add it to the header StringBuffer.
         */
        boolean inHeader = true; // loop control
        while (inHeader && ((c = istream.read()) != -1)) {
            switch (c) {
                case '\r':
                    break;
                case '\n':
                    if (c == last) {
                        inHeader = false;
                        break;
                    }
                    last = c;
                    header.append("\n");
                    break;
                default:
                    last = c;
                    header.append((char) c);
            }
        }

        /**
         * Read the contents and add it to the response StringBuffer.
         */
        while (istream.read(buffer) != -1) {
            response.append(new String(buffer, "iso-8859-1"));
        }
    }

    /**
     * Get the response header.
     */
    public String getHeader() {
        return header.toString();
    }

    /**
     * Get the server's response.
     */
    public String getResponse() {
        return response.toString();
    }

    /**
     * Close all open connections -- sockets and streams.
     */
    public void close() throws Exception {
        socket.close();
        istream.close();
        ostream.close();
    }
}
