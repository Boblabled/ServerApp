package Commands;

import Elements.MusicBand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedHashSet;

/**
 * Класс команды который сохраняет коллекцию
 */
public class CommandSave extends Command{
    private static final Logger logger = LogManager.getLogger();

    /**
     * Метод который сохраняет коллекцию
     *
     * @param file - файл где храниться коллекция
     * @param collection - коллекция
     */
    public static Object action(String file, LinkedHashSet<MusicBand> collection){
        Object message = "";
        Object[] text;
        text = collection.toArray();
        int index;
        try(FileOutputStream out=new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(out))
        {
            for (index = 0; index< text.length; index++){
                byte[] buffer = (text[index] + "\n").getBytes();
                bos.write(buffer, 0, buffer.length);
            }
            message = "Коллекция успешно сохранена";
            logger.info(message);
            message = message + "\n";
        }
        catch(IOException ex){
            message = "Ошибка сохранения";
            logger.error(message);
            message = message + "\n";
        }
        return message;
    }
}

/*
311786,Metallica,4.0,2.0,2021-03-27T22:12:22.516446100,4,13,Mon Mar 01 23:22:52 MSK 2021,POST_PUNK,Bob,70,YELLOW,BROWN,UNITED_KINGDOM
3184934786,FFDP,0.0,0.0,2021-03-27T22:12:22.516446100,4,13,Mon Mar 01 23:22:52 MSK 2021,JAZZ,Jack,100,BROWN,YELLOW,ITALI
*/