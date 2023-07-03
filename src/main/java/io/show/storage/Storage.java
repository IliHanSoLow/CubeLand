package io.show.storage;

import org.json.JSONObject;
import org.json.JSONWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Vector;

/**
 * @author Samuel Hierl
 */
public class Storage {
    /**
     * Changes  Worldfile into Worldobject
     *
     * @param path
     * @return World object;
     * @throws IOException
     */
    public static World readWorld(String path) throws IOException {
        JSONObject jsonobject = new JSONObject();
        jsonobject = readJson(path);
        String name = jsonobject.getString("name");
        long heightSeed = jsonobject.getLong("heightSeed");
        long orelikelynessSeed = jsonobject.getLong("orelikelynessSeed");
        long treeHeightSeed = jsonobject.getLong("treeLikelinessSeed");
        long treeLikelinessSeed = jsonobject.getLong("treeLikelinessSeed");
        long noodleSeed = jsonobject.getLong("noodleSeed");
        long cheeseSeed = jsonobject.getLong("cheeseSeed");
        World world = new World(name, heightSeed, orelikelynessSeed, treeHeightSeed, treeLikelinessSeed, noodleSeed, cheeseSeed);
        return world;
    }

    /**
     * Reads JSOnObjects form storage
     *
     * @param path
     * @return JSONObject
     * @throws IOException
     */


    public static JSONObject readJson(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));

        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) builder.append(line).append('\n');

        reader.close();

        return new JSONObject(builder.toString());
    }

    /**
     * Reads Image
     *
     * @param path
     * @return BufferedImage
     * @throws IOException
     */
    public static BufferedImage readImage(String path) throws IOException {
        return ImageIO.read(new File(path));
    }

    /**
     * reads String List with texture paths
     *
     * @param paths
     * @return BufferedImage List
     * @throws IOException
     */

    public static List<BufferedImage> readListImage(List<String> paths) throws IOException {
        List<BufferedImage> imageList = new Vector<>();
        for (String path : paths) {
            ImageIO.read(new File(path));
            imageList.add(ImageIO.read(new File(path)));
        }
        return imageList;
    }

    /**
     * Reads list from storage
     *
     * @param path
     * @return List<string></>
     * @throws IOException
     */
    public static List<String> readList(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));

        List<String> list = new Vector<>();
        String line;
        while ((line = reader.readLine()) != null) list.add(line);

        reader.close();

        return list;
    }

    private static final String SAVES_PATH = "saves";


    /**
     * Saves World into storage;
     *
     * @param world
     * @return false if world file aleady exsits,
     * @throws IOException
     */
    public static boolean writeWorld(World world) throws IOException {

        File dir = new File(SAVES_PATH, world.getName());
        if (!dir.exists()) if (!dir.mkdirs()) return false;

        File worldFile = new File(dir, "world.json");
        StringBuilder builder = new StringBuilder();
        JSONWriter writer = new JSONWriter(builder);
        writer.object().key("name").value(world.getName()).endObject();
        writer.object().key("heightSeed").value(world.getHeightSeed()).endObject();
        writer.object().key("treeHeightSeed").value(world.getTreeHeightSeed()).endObject();
        writer.object().key("treeLikelinessSeed").value(world.getTreeLikelinessSeed()).endObject();
        writer.object().key("noodleSeed").value(world.getNoodleSeed()).endObject();
        writer.object().key("cheeseSeed").value(world.getCheeseSeed()).endObject();
        writer.object().key("oreLikelinessSeed").value(world.getOreLikelinessSeed()).endObject();


        FileWriter fileWriter = new FileWriter(worldFile);
        fileWriter.write(builder.toString());
        fileWriter.flush();
        fileWriter.close();

        return true;
    }

    /**
     * Writes chunk files into storage
     *
     * @param world
     * @param x
     * @return false if no world exists
     * @throws IOException
     */
    public static boolean writeChunk(World world, int x) throws IOException {
        /* saves Chunk */
        File dir = new File(SAVES_PATH, world.getName());
        if (!dir.exists()) return false;

        int[] chunk = world.getChunk(x);

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chunk.length; i++) {
            builder.append(chunk[i]).append('\n');
        }
        File worldFile = new File(dir, "chunk_" + x + ".txt");
        FileWriter fileWriter = new FileWriter(worldFile);
        fileWriter.write(builder.toString());
        fileWriter.flush();
        fileWriter.close();
        return true;
    }

    /**
     * Reads in the chunk file for the given x position as chunk start
     *
     * @param x
     * @param world
     * @param length
     * @return false if file or world folder is not found, else true
     * @throws IOException
     */
    public static boolean readChunk(int x, World world, int length) throws IOException {

        File dir = new File(SAVES_PATH, world.getName());
        if (!dir.exists()) return false;
        File file = new File("chunk_" + x + ".txt");
        if (!file.exists()) return false;
        BufferedReader bufferedReader = new BufferedReader(new FileReader("chunk_" + x + ".txt"));
        int[] chunk = new int[length];
        int index = 0;
        while (bufferedReader.readLine() != null) {

            int i = Integer.parseInt(bufferedReader.readLine());
            chunk[index++] = i;
        }
        world.addChunk(x, chunk);
        return true;
    }
}
