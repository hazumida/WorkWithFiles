package com.company;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class GameProgress implements Serializable {
    private static final long serialVersionUID = 1L;

    private int health;
    private int weapons;
    private int lvl;
    private double distance;

    public GameProgress(int health, int weapons, int lvl, double distance) {
        this.health = health;
        this.weapons = weapons;
        this.lvl = lvl;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "GameProgress{" +
                "health=" + health +
                ", weapons=" + weapons +
                ", lvl=" + lvl +
                ", distance=" + distance +
                '}';
    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }
    }

    public static void zipFiles(String path, List<String> lists) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path));) {
            for (String list : lists) {
                FileInputStream fis = new FileInputStream(list);
                ZipEntry entry = new ZipEntry(list.split("/")[list.split("/").length - 1]);
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                fis.close();
                zout.closeEntry();
            }
            deleteFiles(lists);
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }

    private static void deleteFiles(List<String> lists) {
        File file;
        try  {
            for (String list : lists) {
                file = new File(list);
                file.delete();
            }
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }

    public static void openZip(String pathZip, String path) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(pathZip))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(path + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }

    public static void openProgress(String path) {
        GameProgress gameProgress;
        try (FileInputStream fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
            System.out.println(gameProgress.toString());
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }
}
