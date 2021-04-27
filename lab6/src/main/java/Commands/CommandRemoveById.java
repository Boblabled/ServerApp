package Commands;

import Elements.MusicBand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * Класс команды которая удаляет все элементы с указанным id
 */
public class CommandRemoveById extends Command{
    private static final Logger logger = LogManager.getLogger();

    /**
     * Метод который удаляет все элементы с указанным id
     *
     * @param command - команда которую вводят с консоли
     * @param collection - коллекция
     */
    public static Object action(String command, LinkedHashSet<MusicBand> collection){
        Object message = "";
        String[] fields;
        boolean work;
        work = false;
        fields = command.split(" ");
        if (fields.length == 2){
            try {
                collection.removeAll(collection.stream().filter((mb) -> mb.getId() == (Long.parseLong(fields[1]))).collect(Collectors.toList()));
                message = "Элементы с такм id были успешно удалены";
                logger.info(message);
                message = message + "\n";
                work = true;
            } catch (NumberFormatException e) {
                message = "id неверный формат строки!";
                logger.error(message);
                message = message + "\n";
            }
        } else {
            message = "Неверный формат ввода данных";
            logger.error(message);
            message = message + "\n";
            work = false;
        }
        if (!work) {
            message = "Не найдено элемента с таким id";
            logger.error(message);
            message = message + "\n";
        }
        return message;
    }
}
