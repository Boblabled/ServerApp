package Commands;

import Elements.MusicBand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashSet;

/**
 * Класс команды которая выводит все элементы коллекции
 */
public class CommandShow extends Command{
    private static final Logger logger = LogManager.getLogger();

    /**
     * Метод который выводит все элементы коллекции
     *
     * @param collection - коллекция
     */
    public static Object action(LinkedHashSet<MusicBand> collection){
        Object message= "";
        MusicBand[] arr;
        arr = collection.toArray(new MusicBand[0]);
        for (int i =0; i < collection.size(); i++){
            if (i == 0) {
                message = (arr[i]);
            } else if (i == collection.size()-1) {
                message = (message + "\n" +  arr[i] + "\n");
            } else message = (message + "\n" + arr[i]);
        }
        logger.info("Команда выполнена");
        return message;
    }
}
