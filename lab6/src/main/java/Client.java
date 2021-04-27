import Commands.Serialization;
import java.io.*;
import java.net.Socket;

public class Client implements Serializable{

    private static Socket clientSocket; // сокет для общения
    private static BufferedReader reader; // ридер читающий с консоли
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет
    private static final int port = 4004; // порт для подключения
    private static final Serialization serialization = new Serialization(); // сериализптор/десериализатор
    private static final String temp = System.getenv().get("MusicBandPATH2"); // переменная окружения
    private static final String serializedDate = "serializedDate.txt"; // файл для передачи сериализованных сообщений

    /**
     * Это main)
     *
     * @param args - что-то
     */
    public static void main(String[] args){
        try {
            connection( true);
            while (true){
                String message = write();
                read();
                if (message.equals("exit") | message.equals("close server")){
                    connection(false);
                    break;
                }
                read();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * Модуль отправки запросов на сервер
     *
     * @return - введённая команда
     * @throws IOException - ошибка чтения
     */
    public static String write() throws IOException {
        System.out.println("Введите команду:");
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        String message = reader.readLine();
        serialization.SerializeObject((Object) message, temp, serializedDate);
        out.write(message + "\n");
        out.flush();
        return message;
    }

    /**
     * Модуль принятия сообщений от сервера
     *
     * @throws IOException - ошибка принятия сообщений
     */
    public static void read() throws IOException {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        String serverWord = in.readLine();
        serverWord = serialization.DeserializeObject(temp, serializedDate);
        System.out.println(serverWord);
    }

    /**
     * Модуль соединения с сервером
     *
     * @param connect - режим работы (отключиться/подключиться)
     * @throws IOException - ошибка подключения
     */
    public static void connection(boolean connect) throws IOException {
        if (connect) {
            clientSocket = new Socket("localhost", port);
            reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Соединение с сервером установлено");
        }
        if (!connect) {
            System.out.println("Клиент был закрыт...");
            clientSocket.close();
            in.close();
            out.close();
        }
    }
}
