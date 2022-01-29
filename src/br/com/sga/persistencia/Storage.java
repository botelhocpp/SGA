package br.com.sga.persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import com.google.gson.Gson;

class Storage {
  private static final String FILENAME = "./util/storage.txt";

  Object obj;

  private void lerArquivo() {
    File file = new File(FILENAME);
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        System.out.println("IOException: " + e.getMessage());
      }
    }

    FileInputStream inputStream = null;
    try {
      inputStream = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      System.out.println("FileNotFoundException: " + e.getMessage());
    }


  }

}