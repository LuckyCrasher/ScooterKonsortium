package mapping;

public class MapTextRenderer extends MapArray {
	
	public MapTextRenderer(int width, int height) {
		super(width, height);
	}
	
	public String render() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("+");
		for(int i=0;i<this.width*3;i++)sb.append("-");
		sb.append("+\n");
		
		
		for(MapObjekt[] amo : this.map) {
			sb.append("|");
			for(MapObjekt mo : amo) {
				sb.append(String.format(" %s ", mo.render()));
			}
			sb.append("|\n");
		}
		
		sb.append("+");
		for(int i=0;i<this.width*3;i++)sb.append("-");
		sb.append("+");
		return sb.toString();
	}
}
