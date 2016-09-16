package noisedraw;

/*
 * OpenSimplex Noise sample class.
 */

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import noiselib.OpenSimplexNoise;

import java.io.*;
import java.util.Random;

public class OpenSimplexNoiseTest
{
	private static final int WIDTH = 128;
	private static final int HEIGHT = 128;
	private static final double FEATURE_SIZE = 5;
	
	// persistence is is the scale factor in each iteration
	public static double getNoise(OpenSimplexNoise simplex, int iterations, int x, int y, double persistence, double scale, double low, double high) {
		double maxAmp = 0;
		double amp = 1;
		double freq = scale;
		double noise = 0;
		
		// add successively smaller, higher-frequency terms
		// each iteration is called an octave, because it is twice the frequency of the iteration before it
		for(int i = 0; i < iterations; ++i) { // iterations = number of octaves
			noise += simplex.eval(x * freq, y * freq) * amp;
			maxAmp += amp;
			amp += 1/(2^iterations); //*= persistence;
			freq *=  2;
		}
		
		// take the average value of the iterations
		noise /= maxAmp;
		
		// normalize the result
		//noise = noise * (high - low) / 2 + (high + low) / 2;
		
		return noise;
	}
	
	public static void main(String[] args)
		throws IOException {
		
		OpenSimplexNoise noise = new OpenSimplexNoise(1233313l);
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_BYTE_INDEXED);
		/*for (int y = 0; y < HEIGHT; y++)
		{
			for (int x = 0; x < WIDTH; x++)
			{
				double value = noise.eval(x / FEATURE_SIZE, y / FEATURE_SIZE);
				int rgb = 0x010101 * (int)((value + 1) * 127.5);
				image.setRGB(x, y, rgb);
			}
		}*/
		
		/*OpenSimplexNoise n1 = new OpenSimplexNoise(1233313l);
		OpenSimplexNoise n2 = new OpenSimplexNoise(12439943l);
		OpenSimplexNoise n3 = new OpenSimplexNoise(92382939l);
		
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				double value = (n1.eval(x / 48.0, y / 48.0, 0.5) + 
						n2.eval(x / 24.0, y / 24.0, 0.5) * .5 + 
						n3.eval(x / 12.0, y / 12.0, 0.5) * .25) / 
						(1 + .5 + .25);
				int rgb = 0x010101 * (int)((value + 1) * 127.5);
				image.setRGB(x, y, rgb);
			}
		}*/
		
		Random rand = new Random(1233313l);
		byteNoiseGen = new NoiseGenerator(rand, 6); // 6 octaves
		
		getNoiseForChunk(0, 0);
		
		double noise2[] = new double[3123];
		
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				
				double scale = .007;
				double value = getNoise(noise, 8, x, y, 1, scale, 0, 255);
				noise2[x * 16 + y] = getNoise(noise, 8, x, y, 1, scale, 0, 230);
				//int rgb = 0x010101 * (int)((value + 1) * 127.5);
				//image.setRGB(x, y, rgb);
			}
		}
		
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				for(int i = 0; i < noise2.length; i++) {
					int rgb = 0x010101 * (int)((noise2[i] + 1) * 127.5);
					image.setRGB(x, y, rgb);
				}
			}
		}

		
		ImageIO.write(image, "png", new File("noise1.bmp"));
	}
	
	public static NoiseGenerator byteNoiseGen;

	private static void getNoiseForChunk(int chunkX, int chunkZ) {
		
	}
}