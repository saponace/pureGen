package package1;

	public class Couple {
	   private int xOffset;
	   private int zOffset;

	   Couple(int i, int k) {
	      this.xOffset = i;
	      this.zOffset = k;
	   }
	   public int i() {
	      return this.xOffset;
	   }
	   public int k() {
	      return this.zOffset;
	   }
	   

	   
	   @Override 
	   public String toString() {
		    StringBuilder result = new StringBuilder(
		    		"(" + this.xOffset + ", " + this.zOffset + ")");
		    return result.toString();
		  }
	   
	   // So that they can be used as keys with hashMaps 
	   @Override
	   public boolean equals(Object object){
		   Couple c = (Couple) object;
		   return ((this.xOffset == c.i()) && (this.zOffset == c.k()));
	   }
	   @Override
	   public int hashCode(){
		   return 0;
	   }
	}
