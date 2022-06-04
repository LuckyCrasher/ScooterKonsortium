package pathFinding;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import konsortiumdata.*;
import mapping.*;

public class PathFindingAlgorithm {
	private Map oMap;
	private KonsortiumData oData;
	
	public PathFindingAlgorithm(Map oMap) {
		this.oMap = oMap;
		this.oData = oMap.getData();
	}
	
	public Koordinaten findQuickest () {
		return null;
	}
	
	public HashMap<Scooter, HashMap<Ladepunkt, Double>> CalculateDistances() {
		String[] asFirmenNames = oData.getFirmaNames();
		ArrayList<Ladepunkt> aoLadepunkte = new ArrayList<>();
		ArrayList<Scooter> aoScooters = new ArrayList<>();
		
		for (String s : asFirmenNames) {
			for (Ladepunkt l : oData.getLadepunkte(s)) {
				aoLadepunkte.add(l);
			}
			for (Scooter scooter : oData.getScooters(s)) {
				aoScooters.add(scooter);
			}
		}
		
		HashMap<Scooter, HashMap<Ladepunkt, Double>> out = new HashMap<>();
		HashMap<Ladepunkt, Double> inner;
		
		for(Scooter scooter : aoScooters) {
			for (Ladepunkt ladepunkt : aoLadepunkte) {
				inner = new HashMap<>();
				inner.put(ladepunkt, this.calculateDistance(scooter.x, scooter.y, ladepunkt.x, ladepunkt.y));
				List<Double> LadepunktByDistance = new ArrayList<Double>(inner.values());
				Collections.sort(LadepunktByDistance);
				System.out.println(LadepunktByDistance);
				out.put(scooter, inner);
			};
		}
		
		return out;
	}
	
	private double calculateDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x2-x1)*(x2-x1) - (y2-y1)*(y2-y1));
	}
}
