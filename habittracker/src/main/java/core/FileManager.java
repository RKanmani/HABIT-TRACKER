package core;

import java.io.*;

public class FileManager extends StorageManager {
  @Override
  public void save(User user, String filename) throws IOException {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
      oos.writeObject(user);
    }
  }

  @Override
  public User load(String filename) throws IOException, ClassNotFoundException {
    File file = new File(filename);
    if (!file.exists()) {
      return new User("DefaultUser");
    }
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
      return (User) ois.readObject();
    }
  }
}
