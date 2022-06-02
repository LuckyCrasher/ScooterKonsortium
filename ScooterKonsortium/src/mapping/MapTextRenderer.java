package mapping;

public class MapTextRenderer extends MapArray {
	
	public MapTextRenderer(int width, int height) {
		super(width, height);
	}
	
	public String render() {
		StringBuilder sb = new StringBuilder();
		
		
		
		/*
		for(MapObjekt[] amo : this.map) {
			sb.append("|");
			for(MapObjekt mo : amo) {
				sb.append(String.format(" %s ", mo.render()));
			}
			sb.append("|\n");
		}*/
		
		sb.append("+");
		for(int i=0;i<this.width*3;i++)sb.append("-");
		sb.append("+\n");
		
		for (int i=0;i<width;i++) {
			sb.append("|");
			for (int j=0;j<height;j++) {
				sb.append("   ");
			}
			sb.append("|\n");
		}
		
		sb.append("+");
		for(int i=0;i<this.width*3;i++)sb.append("-");
		sb.append("+");
		
		
		MapObjekt mo;
		String[] as;
		int offset;
		for (int i=0;i<width;i++) {
			for (int j=0;j<height;j++) {
				mo = this.map[i][j];
				as = mo.render().split("\n");
				offset = (width*3+3) + ((2 + (width*3+3)*j) + i*3);
				if(!as[0].equals(" ")) sb.replace(offset, offset+1, as[0]);
				
				if(as.length>1) {
					offset = (width*3+3) + ((width*3+3)*(j+1)) + i*3;
					sb.replace(offset, offset+as[1].length(), as[1]);
				}
			}
		}
		return sb.toString();
	}
}
