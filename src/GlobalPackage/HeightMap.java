package GlobalPackage;

public class HeightMap {

	private static int[][] array; 
	int xmax;
	int ymax;
	int zmax;

	
	// Get the height of the surface on the given coordinates
	public int getHeight(int i, int k){
		return array[i][k];
	}
	// Create the array giving the height of the surface of the world
	public HeightMap(int xmax, int ymin, int ymax, int zmax){
		array = new int [xmax][zmax];
		this.xmax = xmax;
		this.ymax = ymax;
		this.zmax = zmax;

		rawGeneration(ymin);
		smoothenSurface(10, 20);
	}

	// First loop, totally random
	private void rawGeneration(int ymin){
		for(int i=0; i < xmax; i++)
			for(int k=0; k < zmax; k++){
				int height = 0;
				while(height < ymin){
					height = (int)(Math.random()*ymax);
				}
				array[i][k] = height;
			}
		
	}
	
	// Smoothen the heightArray (lower height differences between close locations)
	private void smoothenSurface(int radius, int iterations){
		int maxx = array.length;
		int maxz = array[0].length;
		for(int count = 0; count < iterations; count++)
			for(int i= 0; i < maxx; i++)
				for(int k= 0; k < maxz; k++){
					int coef = 0;
					for(int di=radius*-1; di<radius; di++)
						for(int dk=radius*-1; dk<radius; dk++){
							if(i+di > 0 && i+di < maxx && k+dk > 0 && k+dk < maxz){
								if(array[i][k] < array[i+di][k+dk])
									coef++;
								else if(array[i][k] > array[i+di][k+dk])
									coef--;
							}
						}
					if(coef > 0)
						array[i][k]++;
					else if(coef < 0)
						array[i][k]--;
				}
	}
}
