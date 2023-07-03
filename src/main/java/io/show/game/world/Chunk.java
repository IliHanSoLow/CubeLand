package io.show.game.world;

/**
 * @author Ilian O.
 */
public class Chunk {

    private static final int WIDTH = 16;
    private final int m_Height;
    private static final int DEPTH = 2;
    private int m_StartPosition;
    private World m_ParentWorld;
    private int[][][] m_Blocks;

    public int getHeight() {
        return m_Height;
    }

    public static int getDepth() {
        return DEPTH;
    }

    public int getStartPosition() {
        return m_StartPosition;
    }

    public World getParentWorld() {
        return m_ParentWorld;
    }

    public int[][][] getBlocks() {
        return m_Blocks;
    }

    public static int getWidth() {
        return WIDTH;
    }

    /**
     * Initialise a new Chunk
     *
     * @param startPosition
     * @param parentWorld
     * @param graphicArray
     */
    public Chunk(int startPosition, World parentWorld, int[] graphicArray) {
        m_Height = parentWorld.getHeight();
        m_StartPosition = startPosition;
        m_ParentWorld = parentWorld;
        Generator generator = new Generator(parentWorld, this, graphicArray);
        m_Blocks = generator.generate();
    }

}
