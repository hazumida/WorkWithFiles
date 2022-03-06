package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static String successfully = " успешно создан";
    public static String wrong = " произошла ошибка";
    public static String path = "D://Games";

    public static StringBuilder createDir (File file, StringBuilder temp, String tmp) {
        file = new File(path + "/" + tmp);
        if (file.mkdir()) {
            return temp.append("\n" + tmp + successfully);
        }
        return temp.append("\n" + tmp + wrong);
    }

    public static StringBuilder createFile (File file, StringBuilder temp, String tmp) {
        file = new File(path + "/" + tmp);
        try {
            if (file.createNewFile()) {
                return temp.append("\n" + tmp + successfully);
            } else {
                return temp.append("\n" + tmp + wrong);
            }
        } catch (IOException err) {
            return temp.append("\nНе правильно указан путь");
        }
    }

    public static void main(String[] args) {

        File file;
        StringBuilder temp = new StringBuilder("Лог каталогов и файлов");
        file = new File(path);
        file.mkdir();

        createDir(file, temp, "src");
        createDir(file, temp, "res");
        createDir(file, temp, "savegames");
        createDir(file, temp, "temp");
        createDir(file, temp, "/src/main");
        createDir(file, temp, "/src/test");
        createDir(file, temp, "/res/drawables");
        createDir(file, temp, "/res/vectors");
        createDir(file, temp, "/res/icons");

        createFile(file, temp, "src/main/Main.java");
        createFile(file, temp, "src/main/Utils.java");

        file = new File(path + "/temp/temp.txt");
        try (FileWriter writer = new FileWriter(file, false)){
            writer.write(temp.toString());
            writer.flush();
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }

        GameProgress gameProgress1 = new GameProgress(100, 4, 5, 15.5);
        GameProgress gameProgress2 = new GameProgress(30, 5, 8, 35.5);
        GameProgress gameProgress3 = new GameProgress(75, 6, 12, 77.1);

        GameProgress.saveGame(path + "/savegames/save1.dat", gameProgress1);
        GameProgress.saveGame(path + "/savegames/save2.dat", gameProgress2);
        GameProgress.saveGame(path + "/savegames/save3.dat", gameProgress3);

        List<String> list = new ArrayList<>();
        list.add(path + "/savegames/save1.dat");
        list.add(path + "/savegames/save2.dat");
        list.add(path + "/savegames/save3.dat");

        GameProgress.zipFiles(path + "/savegames/zip.zip", list);

        GameProgress.openZip(path + "/savegames/zip.zip", path + "/savegames/");

        GameProgress.openProgress(path + "/savegames/save1.dat");
        GameProgress.openProgress(path + "/savegames/save2.dat");
        GameProgress.openProgress(path + "/savegames/save3.dat");

    }
}
