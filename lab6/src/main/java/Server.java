import Commands.CommandExecution;
import Commands.Serialization;
import Elements.MusicBand;
import Manager.Manager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.concurrent.TimeUnit;

public class Server {

    private static Socket clientSocket; //сокет для общения
    private static ServerSocket server; // серверсокет
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет
    private static final LinkedHashSet<MusicBand> collection = new LinkedHashSet<MusicBand>(); // коллекция
    private static final int port = 4004; // порт для подключения
    private static final Serialization serialization = new Serialization(); // сериализптор/десериализатор
    private static final LocalDateTime today = LocalDateTime.now(); //
    private static final String temp = System.getenv().get("MusicBandPATH2"); // переменная окружения
    private static final String file = temp + "MusicBand.csv"; // файл с коллекцией
    private static final String serializedDate = "serializedDate.txt"; // файл для передачи сериализованных сообщений
    private static final Logger logger = LogManager.getLogger(); // логгер

    /**
     * Это main)
     *
     * @param args - что-то
     */
    public static void main(String[] args) {
        try {

            Manager manager = new Manager();
            manager.fill(file, collection);
            logger.info("Сервер запущен!");
            while (true) {
                connection(true, "non");
                while (true) {
                    String message = read();
                    if (message != null) {
                        write(message, "get");
                        if (message.equals("exit")) {
                            connection(false, "non");
                            break;
                        } else if (message.equals("close server")) {
                            execution("save", file, today, temp, serializedDate);
                            connection(false,"close server");
                            break;
                        }
                        write(message, "send");
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            System.err.println(e);
        }
    }

    /**
     * Модуль выполнения команд
     *
     * @param message - сообщение принятое от клиента
     * @param file - файл, где храниться колленкция
     * @param today - текущая дата
     * @param temp - переменная окружения
     * @param serializedDate - файл, где храниться сериализованная команда
     */
    public static void execution(String message, String file, LocalDateTime today, String temp, String serializedDate){
        String command;
        String[] field;
        field = message.split(" ");
        command = field[0];
        serialization.SerializeObject(CommandExecution.action(collection, message, command, file, today), temp, serializedDate);
    }

    /**
     * Модуль приёма подключений
     *
     * @param connect - режим работы (отключиться/подключиться)
     * @param close - звкрытие сервера
     * @throws IOException - ошибка подключения
     */
    public static void connection(boolean connect, String close) throws IOException {
        if (connect) {
            server = new ServerSocket(port);
            logger.info("Ожидание подключения...");
            clientSocket = server.accept();
            logger.info("Соединение с клиентом установлено");
        }
        if (!connect) {
            logger.info("Соединение с клиентом разорвано");
            clientSocket.close();
            server.close();
            if (close.equals("close server")){
                logger.info("Сервер закрыт!");
                System.exit(0);
            }
        }
    }

    /**
     * Модуль чтения запроса
     *
     * @return - возвращает десериализованную команду
     * @throws IOException - ошибка чтения запроса
     */
    public static String read() throws IOException {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        String message = in.readLine();
        message = serialization.DeserializeObject(temp, serializedDate);
        return message;
    }

    /**
     * Модуль отправки ответов клиенту
     *
     * @param message - сообщение от клиента
     * @param command - комманда
     * @throws IOException - ошибка чтения запроса
     * @throws InterruptedException - ошибка ожидания
     */
    public static void write(String message, String command) throws IOException, InterruptedException {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        if (command.equals("get")){
            logger.info(message);
            String messageToClient = "\nСервер принял команду: " + message + "\n";
            serialization.SerializeObject(messageToClient, temp, serializedDate);
            out.write("\n");
            out.flush();
        } else if (command.equals("send")){
            TimeUnit.SECONDS.sleep(1);
            execution(message, file, today, temp, serializedDate);
            out.write("\n");
            out.flush();
        }
    }
}
