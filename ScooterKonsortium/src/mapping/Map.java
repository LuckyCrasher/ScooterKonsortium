package mapping;
import pathFinding.*;

import java.util.ArrayList;
import java.util.HashMap;

import konsortiumdata.*;

public class Map extends MapTextRenderer {
	private KonsortiumData oData;
	private PathFindingAlgorithm oPFA;
	
	public Map(KonsortiumData oData, int width, int height) {
		super(width, height);
		this.oData = oData;
		this.oPFA = new PathFindingAlgorithm(this);
	}
	
	public void loadData() {
		String[] asFirmenNames = oData.getFirmaNames();
		ArrayList<Firma> alFirmen = new ArrayList<Firma>();
		for(String key : asFirmenNames) {
			alFirmen.add(this.oData.getFirma(key));
		}
		
		// go through all companies
		for(Firma f : alFirmen) {
			
			//go through all Scooters of current company
			for(Scooter s : f.getScooters()) {
				this.placeObject(s.x, s.y, s);
			}
			for(Ladepunkt l : f.getladepunkte()) {
				this.placeObject(l.x, l.y, l);
			}
		}
	}
	public KonsortiumData getData() {
		return oData;
	}
	
	public HashMap<Scooter, Ladepunkt[]> calculateNearestLade() {
	
		return null;
	}
	
	public String getCaluclateDistances() {
		StringBuilder sb = new StringBuilder();
		
		HashMap<Scooter, HashMap<Ladepunkt, Double>> distances = this.oPFA.CalculateDistances();
		
		for (Scooter s : distances.keySet()) {
			
		}
		
		return sb.toString();
	}
	
	public Ladepunkt[] calculateNearest(Scooter s) {
		
		return null;
	}
	
	public String toString() {
		loadData();
		return this.render();
	}
}
