package core;

import java.io.IOException;

public abstract class StorageManager {
  public abstract void save(User user, String filename) throws IOException;

  public abstract User load(String filename) throws IOException, ClassNotFoundException;
}
