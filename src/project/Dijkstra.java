package project;

import java.util.*;
import java.util.Map.Entry;


class Graph {
   private final Map<Integer, Vertex> graph; 
   public static class mapLine {
      public final int v1, v2;
      public final double dist;
      public mapLine(int v1, int v2, double dist) {	
         this.v1 = v1;		//v1 = 스타트노드
         this.v2 = v2;		//v2 = 엔드노드
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
      public int vertexID;
      
      public double dist = Double.MAX_VALUE; 
      public Vertex previous = null;
      public final Map<Vertex, Double> neighbours = new HashMap<>();
      public final Map<Vertex, Integer> neighbours1 = new HashMap<>();
      
      
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
            //System.out.println(" -> "+this.vertexID + "( " + this.dist + ")");
            
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
    	  /**
    	   * v1 시작 노드와 v2 끝 노드의 dist 를 연결하는 그래프 생성
    	   */
         graph.get(e.v1).neighbours.put(graph.get(e.v2), e.dist);
         graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist); 

         
         /**
          * 차량에서 주변 노드를 검색하기위함
          */
         graph.get(e.v2).neighbours1.put(graph.get(e.v1), e.v1); 
         graph.get(e.v1).neighbours1.put(graph.get(e.v2), e.v2);
         
         
      }
   }
   
   /**
    * 노드와 연결된 노드 값 들 알아오기 
    */
   ArrayList<Integer> neighborNodeId = new ArrayList<Integer>();
   String nodeParseT1;
   String nodeParseT2;
   String[] str;
   String[] str2;
   String[] str3;
   
   public ArrayList<Integer> getLinkNode(int nodiId){
	
	nodeParseT1 = graph.get(nodiId).neighbours1.toString();	//길이	
	//{project.Graph$Vertex@19982de=3327, project.Graph$Vertex@f2bd5b=949, project.Graph$Vertex@1846619=301} 형식을 파싱해야함
	/**
	 * 노드위치만 가져오기 위한 파싱 부분
	 */
	neighborNodeId.clear();
	str = nodeParseT1.split(",");
	for(int i=0; i<str.length; i++){
		str2 = str[i].split("=");
		for(int j=1; j<str2.length; j++){
			if(j == str2.length-1){
				str3 = str2[j].split("}");
				str2[j] = str3[0];
			}
			//System.out.println(str2[j]);
			neighborNodeId.add(Integer.parseInt(str2[j]));
		}
	}
	
	
	return neighborNodeId;
	//return graph.get(nodiId).vertexID;	//노드 아이디
   }
   
   
   /**
    * 차량 주변의 노드가 몇개인지 반환
    */
   public int getNeighborNodeCount(){
	   return neighborNodeId.size();
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
   int count;
   int index = 0;
   int nearestCarID[];
   ArrayList<Double> nearestCarDist = new ArrayList<Double>();
   
   public int[] printQ1(int k, ArrayList<Car> taxiArray) {
	   count = 0;
	   index = 0;
	   nearestCarID = new int[k];	//최단 거리 노드
	   nearestCarDist.clear();
	   
	   Vertex[] v_Array = new Vertex[graph.size()];
	   Vertex[] taxi_Array = new Vertex[taxiArray.size()];
	   
	   for(Vertex temp : graph.values()) {
		   for( Car node : taxiArray)
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
	        	 
	        	 nearestCarDist.add(v.dist); 
	        	 nearestCarID[index++] = v.vertexID;
	        	 count++;
	        	 
	        	
	        	 
	         }
	      }
	   
	 
	
      
      return nearestCarID;
   }
   /**
    * 최단 거리 자동차까지의 거리 표시
    */
   public ArrayList<Double> get_Shortest_K_Car_Dist(){
	   return nearestCarDist;
   }
   
   public void clear_List(){
	   /**
	    * 차량 새롭게 찾을때 클리어 함수
	    */
	   returnPath.clear();
   }
}
