package project;

import java.util.*;
import java.util.Map.Entry;


class Graph {
   private final Map<Integer, Vertex> graph; 
   public static class mapLine {
      public final int v1, v2;
      public final double dist;
      public mapLine(int v1, int v2, double dist) {
         this.v1 = v1;
         this.v2 = v2;
         this.dist = dist;
      }
   }
 /**
  * 패스 반환
  * @author USER
  *
  */
   static ArrayList<Integer> returnPath = new ArrayList<Integer>();
   
   public static class Vertex implements Comparable<Vertex> {
      public final int vertexID;
      
      public double dist = Double.MAX_VALUE; 
      public Vertex previous = null;
      public final Map<Vertex, Double> neighbours = new HashMap<>();
 
      public Vertex(int vertexID) {
         this.vertexID = vertexID;
         
      }
 
      private void printPath() {
         if (this == this.previous) {
            System.out.printf("%d", this.vertexID);
         } else if (this.previous == null) {
            System.out.printf("%d(unreached)", this.vertexID);
         } else {
            this.previous.printPath();
            System.out.println(" -> "+this.vertexID + "( " + this.dist + ")");
            
            returnPath.add(this.vertexID);	//최단 거리 패스 저장
            
         }
      }
 
      public int compareTo(Vertex other) {
         return Double.compare(dist, other.dist);
      }
   }
 
  
   public Graph(mapLine[] edges) {
      graph = new HashMap<>(edges.length);
 
      for (mapLine e : edges) {
         if (!graph.containsKey(e.v1)) graph.put(e.v1, new Vertex(e.v1));
         if (!graph.containsKey(e.v2)) graph.put(e.v2, new Vertex(e.v2));
      }
 
     
      for (mapLine e : edges) {
         graph.get(e.v1).neighbours.put(graph.get(e.v2), e.dist);
         graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist); 
      }
   }
   
   /**
    * 노드와 연결된 노드 값 들 알아오기 
    */
   public Map<Vertex, Double> getLinkNode(){
	return graph.get(1000).neighbours;
   }
 
  
   public void dijkstra(int startPointID) {
      if (!graph.containsKey(startPointID)) {
         System.err.printf("Graph doesn't contain start vertex \"%d\"\n", startPointID);
         return;
      }
      final Vertex source = graph.get(startPointID);
      NavigableSet<Vertex> q = new TreeSet<>();
 
      for (Vertex v : graph.values()) {
         v.previous = v == source ? source : null;
         v.dist = v == source ? 0 : Double.MAX_VALUE;
         q.add(v);
      }
 
      dijkstra(q);
   }
 
  
   private void dijkstra(final NavigableSet<Vertex> q) {      
      Vertex u, v;
      while (!q.isEmpty()) {
 
         u = q.pollFirst(); 
         if (u.dist == Double.MAX_VALUE) break; 
         for (Entry<Vertex, Double> a : u.neighbours.entrySet()) {
            v = a.getKey(); 
 
            final double alternateDist = u.dist + a.getValue();
            if (alternateDist < v.dist) {
               q.remove(v);
               v.dist = alternateDist;
               v.previous = u;
               q.add(v);
            } 
         }
      }
   }
 
   /**
    * 아이디를 받아서 패스 찾아줌
    * @param endPointID
    */
   
   public ArrayList<Integer> printQ2(int endPointID) {
      if (!graph.containsKey(endPointID)) {
         //System.err.printf("Graph doesn't contain end vertex \"%d\"\n", endPointID);
         //return;
      }
      graph.get(endPointID).printPath();
      //System.out.println();
      
      return returnPath;
   }

   /**
    * 최단거리 차량 k개 찾는 함수
    * @param k
    * @param taxiArray
    * @return
    */
   public int[] printQ1(int k, ArrayList<SF_cnode> taxiArray) {
	   int count =0;
	   int nearestTaxiID[]= new int[k];
	   int index = 0;
	   Vertex[] v_Array = new Vertex[graph.size()];
	   Vertex[] taxi_Array = new Vertex[taxiArray.size()];
	   
	   for(Vertex temp : graph.values()) {
		   for( SF_cnode node : taxiArray)
		   {
			   if(temp.vertexID == node.getNodeID())
				   taxi_Array[index++] = temp;
		   }
		  
	   }
	   
	   Arrays.sort(taxi_Array);
	   index = 0;
	   for (Vertex v :taxi_Array) {
	         if(count < k)
	         {
	        	
	        	 System.out.println(v.vertexID+"\t"+v.dist);
	        	 nearestTaxiID[index++] = v.vertexID;
	        	 count++;
	        	 
	        	
	        	 
	         }
	      }
	   
	 
	
      
      return nearestTaxiID;
   }
   public void clear_List(){
	   /**
	    * 차량 새롭게 찾을때 클리어 함수
	    */
	   returnPath.clear();
   }
}
