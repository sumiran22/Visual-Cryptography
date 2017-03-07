//The Color class is used to encapsulate colors in the default sRGB color
import java.awt.Color;
//This Graphics2D class extends the Graphics class to provide more sophisticated control over geometry, coordinate transformations, color management, and text layout.
import java.awt.Graphics2D; 
//The RenderingHints class defines and manages collections of keys and associated values which allow an application to provide input into the choice of algorithms used by other classes which perform rendering and image manipulation services.
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;//The BufferedImage subclass describes an Image with an accessible buffer of image data.
import java.awt.image.ColorConvertOp;//This class performs a pixel-by-pixel color conversion of the data in the source image.
import java.io.File;//An abstract representation of file and directory pathnames
import java.security.SecureRandom;//This class provides a cryptographically strong random number generator (RNG). 

import javax.imageio.ImageIO;//A class containing static convenience methods for locating ImageReaders and ImageWriters, and performing simple encoding and decoding.



public class Crypting {//declare new class name as crypting
	/**
	 * Securely generates a new Key
	 
	 */
	public static BufferedImage generateKey(int width, int height) { //Securely generates a new Key


		width *= 2;//The width of the largest encryptable Image (width of key is two times as wide)
		height *= 2;//The height of the largest encryptable Image (height of key is two times as tall)

		// generate empty key image
		BufferedImage key = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D keyGraphics = key.createGraphics(); //Creates a Graphics2D, which can be used to draw into this BufferedImage.

		
		// fill it with a fully transparent "white" (should allready be this way with TYPE_INT_ARGB)
		keyGraphics.setColor(new Color(255, 255, 255, 0));
		keyGraphics.fillRect(0, 0, width, height);// The left and right edges of the rectangle are at x and x + width - 1. The top and bottom edges are at y and y + height - 1.
		
		
		keyGraphics.setColor(new Color(0, 0, 0, 255));// fill it with the random key structure
		
		// get securerandom. on linux, this uses NativePRNG (e.g. /dev/urandom), on
		// windows, it uses SHA1PRNG
		SecureRandom secureRandom = new SecureRandom();
		
		// each 2x2-pixel-pack has 2 randomly set pixels
		for (int y = 0; y < height; y += 2) {
			for (int x = 0; x < width; x += 2) {
				// determine the two pixels
				int px1 = secureRandom.nextInt(4);
				int px2 = secureRandom.nextInt(4);// determine the two pixels
				while (px1 == px2) px2 = secureRandom.nextInt(4);
				
				// determine the coordinates of them
				int px1x = (px1 < 2) ? px1 : px1 - 2;
				int px1y = (px1 < 2) ? 0 : 1;
				int px2x = (px2 < 2) ? px2 : px2 - 2;
				int px2y = (px2 < 2) ? 0 : 1;
				
				
				keyGraphics.fillRect(x + px1x, y + px1y, 1, 1);// write them
				keyGraphics.fillRect(x + px2x, y + px2y, 1, 1);// write them
			}
		}
		keyGraphics.dispose();//Disposes of this graphics context and releases any system resources that it is using.
		
		return key;//return the value of key
	}
	
	/**
	 * Loads a key or encrypted file in Image and checks it (roughly).
	 * It is assumed that the file is a png
	 * @param keyFile
	 * @return The key file as an image or null if it isn't a key file.  Any white pixels the image might have had are converted to transparent ones.
	 */
	public static BufferedImage loadAndCheckEncrFile(File keyFile) {//Loads a key or encrypted file in Image and checks it (roughly)
		if (keyFile == null) return null;
		BufferedImage imgKey = null;
		try {//if is true
			imgKey = ImageIO.read(keyFile);
		} catch (Exception e) {
			return null;//otherwise
		}
		
		
		if (imgKey.getWidth() % 2 != 0)// check if width + height are divisable by 2
			return null;//otherwise
		if (imgKey.getHeight() % 2 != 0)//// check if width + height are divisable by 2
			return null;//otherwise
		
		// convert image to ARGB colorspace (if it isn't allready)
		if (imgKey.getType() != BufferedImage.TYPE_INT_ARGB) {
			BufferedImage raw_image = imgKey;
			imgKey = new BufferedImage(raw_image.getWidth(), raw_image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			new ColorConvertOp(null).filter(raw_image, imgKey);//Constructs a new ColorConvertOp which will convert from a source color space to a destination color space. 
		}
		
		// check if image contains only black + transparent or white pixels
		// also count those
		long lAmountOfTotalPixels = 0;
		long lAmountOfBlackPixels = 0;
		
		for(int i = 0; i < imgKey.getHeight(); i++) {
			for(int j = 0; j < imgKey.getWidth(); j++) {
				int iRgb = imgKey.getRGB(j, i);
				
				// white to transparent
				if(iRgb == Color.WHITE.getRGB()) {
					imgKey.setRGB(j, i, 0x00FFFFFF);
					iRgb = imgKey.getRGB(j, i);
				}
				
				// check if pixel is either fully transparent or black
				if(iRgb>>>24 == 0) {
					++lAmountOfTotalPixels;
				} else if (iRgb == Color.BLACK.getRGB()) {
					++lAmountOfTotalPixels;
					++lAmountOfBlackPixels;
				} else {
					return null;
				}
				
			}
		}
		if (lAmountOfTotalPixels / lAmountOfBlackPixels != 2) return null;
		
		return imgKey;
	}
	
	/**
	 * Loads the image to be encrypted. If the image is smaller than the maximum possible size and resize is true, it is resized.
	 * Also checks if the image is a valid source file (only black and white (or transparent) pixels). 
	 * It is assumed that the file is a png.
	 * @param sourceFile The image to be encrypted
	 * @param width The width of the key to be used / 2
	 * @param height The height of the key to be used / 2
	 * @param resize true if image should be resized
	 * @return The (resized) image if it was OK or null. Any white pixels the image might have had are converted to transparent ones.
	 */
	public static BufferedImage loadAndCheckSource(File sourceFile, int width, int height, boolean resize) {
		if (sourceFile == null) return null;//checks if the image is a valid source file
		BufferedImage imgSrc = null;
		try {
			imgSrc = ImageIO.read(sourceFile);
		} catch (Exception e) {
			return null;
		}
		
		if (resize && (imgSrc.getWidth() > width || imgSrc.getHeight() > height)) return null;
		
		// convert image to ARGB colorspace (if it isn't allready)
		if (imgSrc.getType() != BufferedImage.TYPE_INT_ARGB) {
			BufferedImage raw_image = imgSrc;
			imgSrc = new BufferedImage(raw_image.getWidth(), raw_image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			new ColorConvertOp(null).filter(raw_image, imgSrc);//Constructs a new ColorConvertOp which will convert from a source color space to a destination color space. 
		}
		
		// check if image contains only black + transparent or white pixels
		// colored pixels get converted to either black or transparent
		for(int i = 0; i < imgSrc.getHeight(); i++) {
			for(int j = 0; j < imgSrc.getWidth(); j++) {
				int iRgb = imgSrc.getRGB(j, i);
				
				// white to transparent
				if(iRgb == Color.WHITE.getRGB()) {
					imgSrc.setRGB(j, i, 0x00FFFFFF);
					iRgb = imgSrc.getRGB(j, i);
				}
				
				// check if pixel is either fully transparent or black
				if(!(iRgb>>>24 == 0 || iRgb == Color.BLACK.getRGB())) {
					int r = (iRgb & 0x00FF0000)>>16;
					int g = (iRgb & 0x0000FF00)>>8;
					int b = iRgb & 0x000000FF;
					
					double brightness = (0.2126 * r) + (0.7152 * g) + (0.0722 * b);// brightness by euclidian distance)
					if (brightness > (255/2)) {
						// transparent
						imgSrc.setRGB(j, i, 0x00FFFFFF);
					} else {
						// black
						imgSrc.setRGB(j, i, Color.BLACK.getRGB());
					}
				}
				
			}
		}
		
		// resize image
		if (!resize || (imgSrc.getWidth() == width && imgSrc.getHeight() == height)) return imgSrc;//resize true if image should be resized
		BufferedImage imgSrcRes =  new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = imgSrcRes.createGraphics();
		int x = (width - imgSrc.getWidth()) / 2;//width The width of the key to be used / 2
		int y = (height - imgSrc.getHeight()) / 2;//The height of the key to be used / 2
		g.drawImage(imgSrc, x, y, imgSrc.getWidth() + x, imgSrc.getHeight() + y, 0, 0, imgSrc.getWidth(), imgSrc.getHeight(), null);
		g.dispose();
		
		return imgSrcRes;//The (resized) image if it was OK or null. Any white pixels the image might have had are converted to transparent ones.
	}
	
	/**
	 * Encrypts an image. It is assumed that the source image is the maximum possible size (width and height half of that of the key).
	 * Validity of source and key image are not checked, see loadAndCheckKey and loadAndCheckSource for that.
	 * @param imgKey The key to be used for the encryption
	 * @param imgSrc The image to be encrypted
	 * @return The encrypted image or null if an error occured
	 */
	public static BufferedImage encryptImage(BufferedImage imgKey, BufferedImage imgSrc) {
		if (imgKey == null || imgSrc == null) return null;//check the key and src
		// check for key/source file match
		if (imgSrc.getWidth() != imgKey.getWidth() / 2 || imgSrc.getHeight() != imgKey.getHeight() / 2) return null;//It is assumed that the source image is the maximum possible size (width and height half of that of the key).
		
		// resize the source to the size of the key
		BufferedImage imgSrcRes =  new BufferedImage(imgKey.getWidth(), imgKey.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = imgSrcRes.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		g.drawImage(imgSrc, 0, 0, imgKey.getWidth(), imgKey.getHeight(), 0, 0, imgSrc.getWidth(), imgSrc.getHeight(), null);
		g.dispose();//Disposes of this graphics context and releases any system resources that it is using.
		
		BufferedImage imgEncr =  new BufferedImage(imgKey.getWidth(), imgKey.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D encrGraphics = imgEncr.createGraphics();//This Graphics2D class extends the Graphics class to provide more sophisticated control over geometry, coordinate transformations, color management, and text layout. 
		
		// fill it with a fully transparent "white" (should allready be this way with TYPE_INT_ARGB)
		encrGraphics.setColor(new Color(255, 255, 255, 0));
		encrGraphics.fillRect(0, 0, imgEncr.getWidth(), imgEncr.getHeight());
		
		// encrypt
		encrGraphics.setColor(new Color(0, 0, 0, 255));
		
		// each 2x2-pixel-pack has 2 pixels to set
		for (int y = 0; y < imgEncr.getHeight(); y += 2) {
			for (int x = 0; x < imgEncr.getWidth(); x += 2) {
				// because 1 black pixel of the original image is now a square of 4 black pixels,
				// only the first pixel has to be checked
				if (imgSrcRes.getRGB(x, y) == Color.BLACK.getRGB()) {
					// write the two pixels to complete the block together with the key
					if (imgKey.getRGB(x, y)>>>24 == 0) encrGraphics.fillRect(x, y, 1, 1);
					if (imgKey.getRGB(x + 1, y)>>>24 == 0) encrGraphics.fillRect(x + 1, y, 1, 1);
					if (imgKey.getRGB(x, y + 1)>>>24 == 0) encrGraphics.fillRect(x, y + 1, 1, 1);
					if (imgKey.getRGB(x + 1, y + 1)>>>24 == 0) encrGraphics.fillRect(x + 1, y + 1, 1, 1);
				} else {
					// write the two pixels at the same position in the key
					if (imgKey.getRGB(x, y) == Color.BLACK.getRGB()) encrGraphics.fillRect(x, y, 1, 1);
					if (imgKey.getRGB(x + 1, y) == Color.BLACK.getRGB()) encrGraphics.fillRect(x + 1, y, 1, 1);
					if (imgKey.getRGB(x, y + 1) == Color.BLACK.getRGB()) encrGraphics.fillRect(x, y + 1, 1, 1);
					if (imgKey.getRGB(x + 1, y + 1) == Color.BLACK.getRGB()) encrGraphics.fillRect(x + 1, y + 1, 1, 1);
				}
			}
		}
		encrGraphics.dispose();
		
		return imgEncr;//The encrypted image or null if an error occured
	}
	
	/**
	 * Generates an overlay of the key and the encrypted file, therefore producing an unclean, but
	 * Human readable decryption
	 * @param imgKey The key file used to encrypt the image
	 
	 
	 */
	public static BufferedImage overlayImages(BufferedImage imgKey, BufferedImage imgEnc) {//Generates an overlay of the key and the encrypted file
		if (imgKey == null || imgEnc == null || imgKey.getWidth() != imgEnc.getWidth() || imgKey.getHeight() != imgEnc.getHeight()) return null;
		
		// copy key to image
		BufferedImage imgOverlay =  new BufferedImage(imgKey.getWidth(), imgKey.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = imgOverlay.createGraphics();
		g.drawImage(imgKey, 0, 0, imgKey.getWidth(), imgKey.getHeight(), 0, 0, imgKey.getWidth(), imgKey.getHeight(), null);//imgEnc The encrypted image
		
		
		g.drawImage(imgEnc, 0, 0, imgEnc.getWidth(), imgEnc.getHeight(), 0, 0, imgEnc.getWidth(), imgEnc.getHeight(), null);//// impose the encrypted image on it
		
		g.dispose();
		
		return imgOverlay;//The overlay or null if the images are of different size
	}
	
	
	public static BufferedImage decryptImage(BufferedImage imgKey, BufferedImage imgEnc) {//Decrypts an encrypted image
		return decryptImage(overlayImages(imgKey, imgEnc));//return The decrypted picture
	}
	
	/**
	 
	 * @param imgOverlay An overlay generated by overlayImages()
	 
	 */
	public static BufferedImage decryptImage(BufferedImage imgOverlay) {// Decrypts an encrypted image (cleans up the provided overlay)
		if (imgOverlay == null || imgOverlay.getHeight() % 2 != 0 || imgOverlay.getWidth() % 2 != 0) return null;
		
		BufferedImage imgClean = new BufferedImage(imgOverlay.getWidth() / 2, imgOverlay.getHeight() / 2, BufferedImage.TYPE_INT_ARGB);
		Graphics2D cleanGraphics = imgClean.createGraphics();
		
		// fill it with a fully transparent "white" (should allready be this way with TYPE_INT_ARGB)
		cleanGraphics.setColor(new Color(255, 255, 255, 0));
		cleanGraphics.fillRect(0, 0, imgClean.getWidth(), imgClean.getHeight());
		
		// fill it with the cleaned up picture
		cleanGraphics.setColor(new Color(0, 0, 0, 255));
		
		// go through the picture and write all fully colored 2x2 blocks to the result picture
		for (int yOver = 0, yCln = 0; yOver < imgOverlay.getHeight(); yOver += 2, ++yCln) {
			for (int xOver = 0, xCln = 0; xOver < imgOverlay.getWidth(); xOver += 2, ++xCln) {
				if (imgOverlay.getRGB(xOver, yOver) == Color.BLACK.getRGB() &&
						imgOverlay.getRGB(xOver + 1, yOver) == Color.BLACK.getRGB() &&
						imgOverlay.getRGB(xOver, yOver + 1) == Color.BLACK.getRGB() &&
						imgOverlay.getRGB(xOver + 1, yOver + 1) == Color.BLACK.getRGB()) {
					cleanGraphics.fillRect(xCln, yCln, 1, 1);//cleanGraphics - Crypting.decryptImage(BufferedImage)

				}
			}
		}
		cleanGraphics.dispose();//Disposes of this graphics context and releases any system resources that it is using.
		return imgClean;//return The decrypted picture
	}
}
