package maze;

import java.io.*;

class SerializationUtils {

    private SerializationUtils() {
    }

    static void serialize(Object obj, String path) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
        }
    }

    static Object deserialize(String path) throws IOException, ClassNotFoundException {

        ;
        Object obj = null;
        try (FileInputStream fis = new FileInputStream(path);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            obj = ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.printf("The file %s doesn't exist\n", path);
        }
        return obj;
    }
}
