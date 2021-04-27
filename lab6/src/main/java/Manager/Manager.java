package Manager;

import Elements.MusicBand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;

/**
 * Класс который работает с коллекцией
 */
public class Manager {

    private static final Logger logger = LogManager.getLogger();

    /**
     * Заполнение коллекции при первом запуске
     *
     * @param InPut - входный файл
     * @param collection - коллекция
     */
    public void fill(String InPut, LinkedHashSet<MusicBand> collection) {
        File file = new File(InPut);
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.error("Файл не найден");
            System.exit(0);
        }

        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String[] fields;
        String element = null;
        int index;
        while (true) {
            MusicBand musicband = new MusicBand();
            try {
                element = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (element == null) break;
            fields = element.split(",");

            for (index = 0; index<fields.length; index++){
                if (fields[index].equals("null")){
                    fields[index] = null;
                }
            }
            musicband.setId(fields[0]);
            musicband.setName(fields[1]);
            musicband.setCoordinates(fields[2], fields[3]);
            musicband.setCreationDate(fields[4]);
            musicband.setNumberOfParticipants(fields[5]);
            musicband.setAlbumsCount(fields[6]);
            musicband.setEstablishmentDate(fields[7]);
            musicband.setGenre(fields[8]);
            musicband.setFrontMan(fields[9], fields[10], fields[11], fields[12], fields[13]);
            collection.add(musicband);
        }
    }

    /**
     * Добавляет новый файл в коллекцию
     *
     * @param element - элемент который вводят с консоли
     * @param collection - коллекция
     */
    public Object add (String element, LinkedHashSet<MusicBand> collection){
        Object message = "";
        MusicBand musicband = new MusicBand();
        LocalDateTime today = LocalDateTime.now();
        String[] fields;
        String id;
        int index;
        fields = element.split(",");
        if (fields.length == 12) {
            for (index = 0; index < fields.length; index++) {
                if (fields[index].equals("null")) {
                    fields[index] = null;
                }
            }
            id = String.valueOf(Math.round(Math.random() * 1000000 + 1));
            musicband.setId(id);
            musicband.setName(fields[0]);
            musicband.setCoordinates(fields[1], fields[2]);
            musicband.setCreationDate(today.toString());
            musicband.setNumberOfParticipants(fields[3]);
            musicband.setAlbumsCount(fields[4]);
            musicband.setEstablishmentDate(fields[5]);
            musicband.setGenre(fields[6]);
            musicband.setFrontMan(fields[7], fields[8], fields[9], fields[10], fields[11]);

            collection.add(musicband);
            message = "Элемент добавлен в коллекцию\n";
            logger.info(message);

        } else {
            message = "Неверный формат элемента\n";
            logger.error(message);
        }
        return message;
    }

    /**
     * Обновляет поле id у элемента коллекции
     *
     * @param element - исходный элемент коллекции
     * @return - элемент с обновлённым id
     */
    public MusicBand idUpdate (String element){
        MusicBand musicband = new MusicBand();
        String[] fields;
        String id;
        fields = element.split(",");
        id = String.valueOf(Math.round(Math.random() * 1000000 + 1));
        while(fields[0] == id){
            id = String.valueOf(Math.round(Math.random() * 1000000 + 1));
        }
        fields[0] = id;
        musicband.setId(fields[0]);
        musicband.setName(fields[1]);
        musicband.setCoordinates(fields[2], fields[3]);
        musicband.setCreationDate(fields[4]);
        musicband.setNumberOfParticipants(fields[5]);
        musicband.setAlbumsCount(fields[6]);
        musicband.setEstablishmentDate(fields[7]);
        musicband.setGenre(fields[8]);
        musicband.setFrontMan(fields[9], fields[10], fields[11], fields[12], fields[13]);
        return (musicband);
    }

    /**
     * Устанавливает id и CreationDate элементу коллекции
     *
     * @param element - неполный элемент коллекции
     * @return - полный элемент коллекции
     */
    public MusicBand set(String element){
        MusicBand musicband = new MusicBand();
        String[] fields;
        fields = element.split(",");
        LocalDateTime today = LocalDateTime.now();
        String id = String.valueOf(Math.round(Math.random() * 1000000 + 1));
        musicband.setId(id);
        musicband.setName(fields[0]);
        musicband.setCoordinates(fields[1], fields[2]);
        musicband.setCreationDate(today.toString());
        musicband.setNumberOfParticipants(fields[3]);
        musicband.setAlbumsCount(fields[4]);
        musicband.setEstablishmentDate(fields[5]);
        musicband.setGenre(fields[6]);
        musicband.setFrontMan(fields[7], fields[8], fields[9], fields[10], fields[11]);
        return (musicband);
    }

    /**
     * Проверяет элемент коллекции
     *
     * @param element - непроверенный элемент коллекции
     * @return - проверенный элемент коллекции
     */
    public MusicBand check(String element){
        MusicBand musicband = new MusicBand();
        String[] fields;
        fields = element.split(",");
        musicband.setId(fields[0]);
        musicband.setName(fields[1]);
        musicband.setCoordinates(fields[2], fields[3]);
        musicband.setCreationDate(fields[4]);
        musicband.setNumberOfParticipants(fields[5]);
        musicband.setAlbumsCount(fields[6]);
        musicband.setEstablishmentDate(fields[7]);
        musicband.setGenre(fields[8]);
        musicband.setFrontMan(fields[9], fields[10], fields[11], fields[12], fields[13]);
        return (musicband);
    }
}
