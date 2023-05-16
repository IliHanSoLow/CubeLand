package io.show.game.world;

import vendor.opensimplex2.OpenSimplex2;

import java.util.Random;

public class TreeGenerator {
    public TreeGenerator(World world) {
        m_HeightSeed = world.treeHeightSeed;
        m_LikelinessSeed = world.treeLikelinessSeed;
    }

    private long m_HeightSeed;
    private long m_LikelinessSeed;
    private int[][][] Map;

    public int[][][] generateTrees(int[][][] map, int pos) {
        int lastTree = 5;
        for (int i = 0; i < map[0].length; i++) {
            double xOff = 0.001 * pos;
            float likeliness = OpenSimplex2.noise2(m_LikelinessSeed, xOff, 0);
            int height = Math.round((4 - 8) * OpenSimplex2.noise2(m_HeightSeed, xOff, 0) + 4);
            if ((likeliness > 0.65 || likeliness < 0.35) && lastTree > 1) {
                lastTree = 0;
                for (int j = 0; j < map[1].length; j++) {
                    if (map[i][j][0] == 8) {
                        map = setTree(map, i, j, height);
                    }
                }
            } else lastTree++;
        }
        return map;
    }

    public int[][][] setTree(int[][][] Map, int treeXpos, int treeYpos, int height) {
        for (int i = 0; i < height; i++) {
            Map[treeXpos][treeYpos + i][1] = 6;
        }
        return Map;
    }

}
