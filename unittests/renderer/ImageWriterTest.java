package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

/**
 * Testing class ImageWriter
 */
class ImageWriterTest {

    /**
     * Test the functions writePixel() and writeToImage() from the class ImageWriter
     */
    @Test
    public void writeImageTest() {

        // We create an instance of imageWriter with resolution of (800*500)
        ImageWriter imageWriter = new ImageWriter("imageWriterTest", 800, 500);

        // All the image is colored in yellow
        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 500; j++) {
                imageWriter.writePixel(i, j, Color.YELLOW);
            }
        }

        // The rows are colored in red for to see the pixels
        for (int row = 0; row < 16; row++) {
            for (int j = 0; j < 500; j++) {
                imageWriter.writePixel(row * 50, j, Color.RED);
            }
        }

        // The columns are colored in red for to see the pixels
        for (int col = 0; col < 10; col++) {
            for (int j = 0; j < 800; j++) {
                imageWriter.writePixel(j, col * 50, Color.RED);
            }
        }

        // The image is rendered in the package images
        imageWriter.writeToImage();
    }
}