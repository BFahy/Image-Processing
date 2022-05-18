import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;
public class ImageProcessing {
	public static void main(String[] args) {
    // The provided images is apple.jpg, change inputFile to your own file
		int[][] imageData = imgToTwoD("./apple.jpg");
    // Or load your own image using a URL!
		//int[][] imageData = imgToTwoD("Your_URL_Here.com");
		//viewImageData(imageData);



	int[][] trimmed = trimBorders(imageData, 60);
	twoDToImage(trimmed, "./trimmed_apple.jpg");

    int[][] negative = negativeColor(imageData);
    twoDToImage(negative, "./neg_apple.jpg");
    
    int[][] stretch = stretchHorizontally(imageData);
    twoDToImage(stretch, "./stretch_apple.jpg");

    int[][] vertical = shrinkVertically(imageData);
    twoDToImage(vertical, "./vert_apple.jpg");

    int[][] invert = invertImage(imageData);
    twoDToImage(invert, "./flip_apple.jpg");

    int[][] color = colorFilter(imageData, -100, -100, -100);
    twoDToImage(color, "./color_apple.jpg");

    int[][] blankImg = new int[250][250];
    int[][] randomImg = paintRandomImage(blankImg);
    twoDToImage(randomImg, "./random_img.jpg");

    int[] rgba = {255, 0, 255, 255};
    int[][] rectangleImg = paintRectangle(randomImg, 100, 100, 50, 50, getColorIntValFromRGBA(rgba));
    twoDToImage(rectangleImg, "./rectangle.jpg");

		int[][] generatedRectangles = generateRectangles(randomImg, 500);

		twoDToImage(generatedRectangles, "./generated_rect.jpg");
  
		// int[][] allFilters = stretchHorizontally(shrinkVertically(colorFilter(negativeColor(trimBorders(invertImage(imageData), 50)), 200, 20, 40)));
		// Painting with pixels
	}
	// Image Processing Methods

	// Trim border of image by selected pixel amount parameter
	public static int[][] trimBorders(int[][] imageTwoD, int pixelCount) {
		if (imageTwoD.length > pixelCount * 2 && imageTwoD[0].length > pixelCount * 2) {
			int[][] trimmedImg = new int[imageTwoD.length - pixelCount * 2][imageTwoD[0].length - pixelCount * 2];
			for (int i = 0; i < trimmedImg.length; i++) {
				for (int j = 0; j < trimmedImg[i].length; j++) {
					trimmedImg[i][j] = imageTwoD[i + pixelCount][j + pixelCount];
				}
			}
			return trimmedImg;
		} else {
			System.out.println("Cannot trim that many pixels from the given image.");
			return imageTwoD;
		}
	}

	// Method for changing image colour to negative
	public static int[][] negativeColor(int[][] imageTwoD) {
    int[][] newImg = new int[imageTwoD.length][imageTwoD[0].length];
	for(int o = 0; o < imageTwoD.length; o++){
      for (int i = 0; i < imageTwoD[o].length; i++){
        int[] rgba = getRGBAFromPixel(imageTwoD[o][i]);
        rgba[0] = 255 - rgba[0];
        rgba[1] = 255 - rgba[1];
        rgba[2] = 255 - rgba[2];
        newImg[o][i] = getColorIntValFromRGBA(rgba);
      }
    }
		return newImg;
	}

	// Method for stretching image horziontally x2
	public static int[][] stretchHorizontally(int[][] imageTwoD) {
		int[][] newStretch = new int[imageTwoD.length][imageTwoD[0].length*2];
    int track = 0;
    for (int o = 0; o < imageTwoD.length; o++){
      for (int i = 0; i < imageTwoD[o].length; i++){
        track = i * 2;
        newStretch[o][track] = imageTwoD[o][i];
        newStretch[o][track + 1] = imageTwoD[o][i];
      }
    }
		return newStretch;
	}

	// Method for shrinking image vertically /2
	public static int[][] shrinkVertically(int[][] imageTwoD) {
		int[][] newVertical = new int[imageTwoD.length / 2][imageTwoD[0].length];
    for (int o = 0; o < imageTwoD[0].length; o++){
      for(int i = 0; i < imageTwoD.length-1; i+= 2){
        newVertical[i/2][o] = imageTwoD[i][o];
      }
    }
		return newVertical;
	}

	// Method for flipping image to be upside down
	public static int[][] invertImage(int[][] imageTwoD) {
		int[][] newInvert = new int[imageTwoD.length][imageTwoD[0].length];
		for (int o = 0; o < imageTwoD.length; o++) {
			for (int i = 0; i < imageTwoD[o].length; i++) {
				newInvert[o][i] = imageTwoD[(imageTwoD.length - 1) - o][(imageTwoD[i].length - 1) - i];
			}
		}
		return newInvert;
	}

	// Method to apply filter to values of each rgb
	public static int[][] colorFilter(int[][] imageTwoD, int redChangeValue, int greenChangeValue, int blueChangeValue) {
		int[][] newColor = new int[imageTwoD.length][imageTwoD[0].length];
    for(int o = 0; o < imageTwoD.length; o++){
      for(int i = 0; i < imageTwoD[o].length; i++){
        int[] rgba = getRGBAFromPixel(imageTwoD[o][i]);
        int red = rgba[0] + redChangeValue;
        int green = rgba[1] + greenChangeValue;
        int blue = rgba[2] + blueChangeValue;
        if (red > 255){
          red = 255;
        } else if (red < 0){
          red = 0;
        }
        if (blue > 255){
          blue = 255;
        } else if (blue < 0){
          blue = 0;
        }
        if (green > 255){
          green = 255;
        } else if (green < 0){
          green = 0;
        }
        rgba[0] = red;
        rgba[1] = green;
        rgba[2] = blue;
        newColor[o][i] = getColorIntValFromRGBA(rgba);
      }
    }
		return newColor;
	}

	// Painting Methods
	// Method for painting a random image - randomly generated pixel colors
	public static int[][] paintRandomImage(int[][] canvas) {
		Random rand = new Random();
    for (int o = 0; o < canvas.length; o++){
      for (int i = 0; i < canvas[o].length; i++){
        int rr = rand.nextInt(256);
        int rg = rand.nextInt(256);
        int rb = rand.nextInt(256);
        int[] rgbValues = {rr, rg, rb, 255};
        canvas[o][i] = getColorIntValFromRGBA(rgbValues);
        
      }
    }
		return canvas;
	}

	// Paint a rectangle across the randomly generated canvas
	public static int[][] paintRectangle(int[][] canvas, int width, int height, int rowPosition, int colPosition, int color) {
    for (int o = 0; o < canvas.length; o++){
      for (int i = 0; i < canvas[o].length; i++){
        if(o >= rowPosition && o<= rowPosition+width){
          if(i>=colPosition && i<=colPosition+height){
            canvas[o][i] = color;
          }
        }
      }
    }
		return canvas;
	}

	// Generating randomly generated rectangles & sizes
	public static int[][] generateRectangles(int[][] canvas, int numRectangles) {
		Random rand = new Random();
    for(int i = 0; i < numRectangles; i++){
      int rw = rand.nextInt(canvas[0].length);
      int rh = rand.nextInt(canvas.length);
      int rrp = rand.nextInt(canvas.length-rh);
      int rcp = rand.nextInt(canvas[0].length-rw);
      int[] rgba = {rand.nextInt(256), rand.nextInt(256), rand.nextInt(256), 255};
      int rColor = getColorIntValFromRGBA(rgba);
      canvas = paintRectangle(canvas, rw, rh, rrp, rcp, rColor);
    }
		return canvas;
	}

	// Utility Methods
	// Method for input of URL or image file location
	public static int[][] imgToTwoD(String inputFileOrLink) {
		try {
			BufferedImage image = null;
			if (inputFileOrLink.substring(0, 4).toLowerCase().equals("http")) {
				URL imageUrl = new URL(inputFileOrLink);
				image = ImageIO.read(imageUrl);
				if (image == null) {
					System.out.println("Failed to get image from provided URL.");
				}
			} else {
				image = ImageIO.read(new File(inputFileOrLink));
			}
			int imgRows = image.getHeight();
			int imgCols = image.getWidth();
			int[][] pixelData = new int[imgRows][imgCols];
			for (int i = 0; i < imgRows; i++) {
				for (int j = 0; j < imgCols; j++) {
					pixelData[i][j] = image.getRGB(j, i);
				}
			}
			return pixelData;
		} catch (Exception e) {
			System.out.println("Failed to load image: " + e.getLocalizedMessage());
			return null;
		}
	}

	// For saving the image result as set filename
	public static void twoDToImage(int[][] imgData, String fileName) {
		try {
			int imgRows = imgData.length;
			int imgCols = imgData[0].length;
			BufferedImage result = new BufferedImage(imgCols, imgRows, BufferedImage.TYPE_INT_RGB);
			for (int i = 0; i < imgRows; i++) {
				for (int j = 0; j < imgCols; j++) {
					result.setRGB(j, i, imgData[i][j]);
				}
			}
			File output = new File(fileName);
			ImageIO.write(result, "jpg", output);
		} catch (Exception e) {
			System.out.println("Failed to save image: " + e.getLocalizedMessage());
		}
	}

	// Getting RGBA colours
	public static int[] getRGBAFromPixel(int pixelColorValue) {
		Color pixelColor = new Color(pixelColorValue);
		return new int[] { pixelColor.getRed(), pixelColor.getGreen(), pixelColor.getBlue(), pixelColor.getAlpha() };
	}
	// Getting int values from RGBA
	public static int getColorIntValFromRGBA(int[] colorData) {
		if (colorData.length == 4) {
			Color color = new Color(colorData[0], colorData[1], colorData[2], colorData[3]);
			return color.getRGB();
		} else {
			System.out.println("Incorrect number of elements in RGBA array.");
			return -1;
		}
	}
	public static void viewImageData(int[][] imageTwoD) {
		if (imageTwoD.length > 3 && imageTwoD[0].length > 3) {
			int[][] rawPixels = new int[3][3];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					rawPixels[i][j] = imageTwoD[i][j];
				}
			}
			System.out.println("Raw pixel data from the top left corner.");
			System.out.print(Arrays.deepToString(rawPixels).replace("],", "],\n") + "\n");
			int[][][] rgbPixels = new int[3][3][4];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					rgbPixels[i][j] = getRGBAFromPixel(imageTwoD[i][j]);
				}
			}
			System.out.println();
			System.out.println("Extracted RGBA pixel data from top the left corner.");
			for (int[][] row : rgbPixels) {
				System.out.print(Arrays.deepToString(row) + System.lineSeparator());
			}
		} else {
			System.out.println("The image is not large enough to extract 9 pixels from the top left corner");
		}
	}
}