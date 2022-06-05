package mapping;
import pathFinding.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

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
		ArrayList<Ladepunkt> alLadepunkte = new ArrayList<>();
		ArrayList<Scooter> alScooters = new ArrayList<>();
		
		for(Firma f : alFirmen) {
			//go through all Scooters of current company
			for(Scooter s : f.getScooters()) {
				alScooters.add(s);
			}
			for(Ladepunkt l : f.getladepunkte()) {
				alLadepunkte.add(l);			}
		}
		
		//go through all Scooters of current company
		for(Scooter s : alScooters) {
			this.placeObject(s.x, s.y, s);
		}
		for(Ladepunkt l : alLadepunkte) {
			this.placeObject(l.x, l.y, l);
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
			for(Ladepunkt l : distances.get(s).keySet()) {
				sb.append(String.format("Scooter at %d %d -> %s%n", s.x, s.y, l.getNameLadepunkt()));
			}
		}
		
		return sb.toString();
	}
	public String getCaluclateDistances(Scooter[] aoScooter) {
		StringBuilder sb = new StringBuilder();
		
		HashMap<Scooter, HashMap<Ladepunkt, Double>> distances = this.oPFA.CalculateDistances(aoScooter);
		
		for (Scooter s : distances.keySet()) {
			for(Ladepunkt l : distances.get(s).keySet()) {
				sb.append(String.format("Scooter at %d %d is under 30%% -> %s%n", s.x, s.y, l.getNameLadepunkt()));
			}
		}
		
		return sb.toString();
	}
	
	public Ladepunkt[] calculateNearest(Scooter s) {
		
		return null;
	}
	
	public String toString() {
		clearMap();
		loadData();
		return this.render();
	}

	public Ladepunkt getNearestLadepunkt(Scooter scooter) {
		
		Ladepunkt l;
		double d;
		
		HashMap<Ladepunkt, Double> distance = this.oPFA.calculateNearestLadepunkt(scooter);
		Entry<Ladepunkt, Double> entry = distance.entrySet().iterator().next();
		l = entry.getKey();
		d = entry.getValue();
		
		return l;
	}
	
	public String caluclateNearestLadepunkt(Scooter scooter) {
		StringBuilder sb = new StringBuilder();
		
		Ladepunkt l;
		double d;
		
		HashMap<Ladepunkt, Double> distance = this.oPFA.calculateNearestLadepunkt(scooter);
		Entry<Ladepunkt, Double> entry = distance.entrySet().iterator().next();
		l = entry.getKey();
		d = entry.getValue();
		
		sb.append(String.format("Scooter at %d %d is under 30%% %n Nearest Ladepunkt is %s with a distance of %f %n",
				scooter.x, scooter.y, l.getNameLadepunkt(), d));
		
		return sb.toString();
	}

	public double calculateCost(int x1, int y1, int x2, int y2) {
		return this.oPFA.calculateDistance(x1, y1, x2, y2);
	}
}
