package pathFinding;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
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
			inner = new HashMap<>();
			for (Ladepunkt ladepunkt : aoLadepunkte) {
				inner.put(ladepunkt, this.calculateDistance(scooter.x, scooter.y, ladepunkt.x, ladepunkt.y));	
			}
			inner = this.sortByValue(inner);
			out.put(scooter, inner);
		}
		
		return out;
	}
	public HashMap<Scooter, HashMap<Ladepunkt, Double>> CalculateDistances(Scooter[] aoScooter) {
		String[] asFirmenNames = oData.getFirmaNames();
		ArrayList<Ladepunkt> aoLadepunkte = new ArrayList<>();
		ArrayList<Scooter> aoScooters = new ArrayList<>();
		
		for (String s : asFirmenNames) {
			for (Ladepunkt l : oData.getLadepunkte(s)) {
				aoLadepunkte.add(l);
			}
			for (Scooter scooter : aoScooter) {
				aoScooters.add(scooter);
			}
		}
		
		HashMap<Scooter, HashMap<Ladepunkt, Double>> out = new HashMap<>();
		HashMap<Ladepunkt, Double> inner;
		
		for(Scooter scooter : aoScooters) {
			inner = new HashMap<>();
			for (Ladepunkt ladepunkt : aoLadepunkte) {
				inner.put(ladepunkt, this.calculateDistance(scooter.x, scooter.y, ladepunkt.x, ladepunkt.y));	
			}
			inner = this.sortByValue(inner);
			out.put(scooter, inner);
		}
		
		return out;
	}
	
	 // function to sort hashmap by values
    private HashMap<Ladepunkt, Double> sortByValue(HashMap<Ladepunkt, Double> hm)
    {
        // Create a list from elements of HashMap
        LinkedList<Entry<Ladepunkt,Double>> list =
               new LinkedList<Entry<Ladepunkt, Double> >(hm.entrySet());
 
        // Sort the list
        Collections.sort(list, new Comparator<Entry<Ladepunkt, Double> >() {
            public int compare(Entry<Ladepunkt, Double> o1,
                               Entry<Ladepunkt, Double> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
         
        // put data from sorted list to hashmap
        HashMap<Ladepunkt, Double> temp = new LinkedHashMap<Ladepunkt, Double>();
        Entry<Ladepunkt, Double> aa;
        for (int i=0;i<Math.min(3, list.size());i++) {
        	aa = list.get(i);
        	temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
	
	private double calculateDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
	}

	public HashMap<Ladepunkt, Double> calculateNearestLadepunkt(Scooter scooter) {
		String[] asFirmenNames = oData.getFirmaNames();
		ArrayList<Ladepunkt> aoLadepunkte = new ArrayList<>();
		
		for (String s : asFirmenNames) {
			for (Ladepunkt l : oData.getLadepunkte(s)) {
				aoLadepunkte.add(l);
			}
		}
		
		HashMap<Ladepunkt, Double> out = new HashMap<>();
		
		for (Ladepunkt ladepunkt : aoLadepunkte) {
			out.put(ladepunkt, this.calculateDistance(scooter.x, scooter.y, ladepunkt.x, ladepunkt.y));	
		}
		
		out = this.sortByValue(out);
		return out;
	}
}
