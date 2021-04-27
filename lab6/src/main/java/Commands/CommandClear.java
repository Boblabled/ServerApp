package Commands;

import Elements.MusicBand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashSet;

/**
 * Класс команды которая удаляет все элементы из коллекции
 */
public class CommandClear extends Command{
    private static final Logger logger = LogManager.getLogger();

    /**
     * Метод который удаляет все элементы из коллекции
     *
     * @param collection - коллекция
     */
    public static Object action(LinkedHashSet<MusicBand> collection){
        Object message = "";
        collection.clear();
        message = "Коллекция успешно очищена";
        logger.info(message);
        message = message + "\n";
        return message;
    }
}
