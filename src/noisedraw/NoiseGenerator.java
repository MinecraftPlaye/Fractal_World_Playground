package noisedraw;

import java.util.Random;

public class NoiseGenerator {
	
	private int numOctaves;
	
	public byte[][] noise;
	
	public NoiseGenerator(Random rand, int numOctaves) {
		if(numOctaves < 1) { throw new IllegalArgumentException("Must have at least one octave"); }
		this.init(rand, numOctaves);
	}
    
	private void init(Random rand, int numOctaves) {
		this.numOctaves = numOctaves;
	}
}