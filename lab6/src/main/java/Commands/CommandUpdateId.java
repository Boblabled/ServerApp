package Commands;

import Elements.MusicBand;
import Manager.Manager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashSet;

/**
 * Класс команды которая обновляет id элемента
 */
public class CommandUpdateId extends Command{
    private static final Logger logger = LogManager.getLogger();

    /**
     * Метод который обновляет id элемента
     *
     * @param command - команда которую вводят с консоли
     * @param collection - коллекция
     */
    public static Object action(String command, LinkedHashSet<MusicBand> collection){
        Object message = "";
        Manager manager = new Manager();
        String element = "";
        String[] field;
        Object[] arr;
        boolean work;
        work = false;
        int index;
        field = command.split(" ");
        arr = collection.toArray();
        if (field.length == 1){
            message ="Элемент отсутствует";
            logger.error(message);
            message = message + "\n";
        } else  if (field.length >= 2){
            element = field[1];
            if (field.length > 2){
                for (index = 2; index<field.length; index++) {
                    element = element + " " + field[index];
                }
            }
        } else element = field[1];
        for (index = 0; index<collection.size(); index++) {
            if (element.split(",").length == 14) {
                if (manager.check(element).toString().equals(arr[index].toString())) {
                    collection.remove((arr[index]));
                    collection.add(manager.idUpdate(element));
                    message = "id успешно обновлён";
                    logger.info(message);
                    message = message + "\n";
                    work = true;
                    break;
                }
            }
        }
        if (!work) {
            message = "Такого элемента не существует";
            logger.error(message);
            message = message + "\n";
        }
        return message;
    }
}
