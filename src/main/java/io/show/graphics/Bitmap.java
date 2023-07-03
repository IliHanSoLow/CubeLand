package io.show.graphics;

import io.show.storage.Storage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * @author Felix Schreiber
 * <p>
 * A wrapper class for managing image data in form of an integer array,
 * a width and a height, and a float opacity value for transparancy.
 */
public class Bitmap {

    private int[] m_Data;
    private int m_Width;
    private int m_Height;
    private float m_Opacity;

    public Bitmap(int[] data, int width, int height, float opacity) {
        m_Data = data;
        m_Width = width;
        m_Height = height;
        m_Opacity = opacity;
    }

    public Bitmap(int width, int height) {
        this(new int[width * height], width, height, 1.0f);
    }

    public Bitmap(int width, int height, int fillColor, float opacity) {
        m_Data = new int[width * height];
        Arrays.fill(m_Data, fillColor);
        m_Width = width;
        m_Height = height;
        m_Opacity = opacity;
    }

    public Bitmap(BufferedImage image, float opacity) {
        m_Width = image.getWidth();
        m_Height = image.getHeight();
        m_Data = new int[m_Width * m_Height];
        image.getRGB(0, 0, m_Width, m_Height, m_Data, 0, m_Width);
        m_Opacity = opacity;
    }

    public Bitmap(BufferedImage image) {
        this(image, 1.0f);
    }

    public Bitmap(String path, float opacity) throws IOException {
        this(Storage.readImage(path), opacity);
    }

    public Bitmap(String path) throws IOException {
        this(path, 1.0f);
    }

    public Bitmap resize(int width, int height) {

        int oldWidth = m_Width;
        int oldHeight = m_Height;

        m_Width = width;
        m_Height = height;

        int[] oldData = m_Data.clone();

        m_Data = new int[m_Width * m_Height];
        for (int y = 0; y < Math.min(m_Height, oldHeight); y++)
            for (int x = 0; x < Math.min(m_Width, oldWidth); x++)
                m_Data[x + y * m_Width] = oldData[x + y * oldWidth];

        return this;
    }

    public Bitmap setOpacity(float opacity) {
        m_Opacity = opacity;
        return this;
    }

    public int getWidth() {
        return m_Width;
    }

    public int getHeight() {
        return m_Height;
    }

    public float getOpacity() {
        return m_Opacity;
    }

    public Bitmap setPixel(int x, int y, int rgb) {
        m_Data[x + y * m_Width] = rgb;
        return this;
    }

    public int getPixel(int x, int y) {
        return m_Data[x + y * m_Width];
    }

    public int[] getData() {
        return m_Data;
    }

    public byte[] getByteArray() {
        byte[] bytes = new byte[m_Data.length * 4];
        for (int i = 0; i < m_Data.length; i++) {
            int idx = i * 4;
            bytes[idx] = (byte) ((m_Data[i] >> 16) & 0xff);                     // R
            bytes[idx + 1] = (byte) ((m_Data[i] >> 8) & 0xff);                  // G
            bytes[idx + 2] = (byte) (m_Data[i] & 0xff);                         // B
            bytes[idx + 3] = (byte) (((m_Data[i] >> 24) & 0xff) * m_Opacity);   // A
        }
        return bytes;
    }

    public ByteBuffer getByteBuffer() {
        ByteBuffer buffer = ByteBuffer.allocateDirect(m_Data.length * 4).order(ByteOrder.nativeOrder());
        buffer.put(getByteArray());
        buffer.position(0);
        return buffer;
    }
}
