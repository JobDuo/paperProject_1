/*-
 *작성자: 장재성
 *알고리즘 참고 http://rosettacode.org/wiki/Dijkstra's_algorithm#Java
 *위 알고리즘을 알맞게 변형하여 작성했습니다.
 *위 알고리즘의 시간복잡도 O(log n)
 *
 */

package project;




public class SF_cedge {

    // Primary key is EdgeID
	// Edge ID	Start Node ID	End Node ID	L2 Distance
   
    private int edgeID;

  
    private int start_NodeID;

    private int end_NodeID;
    private double l2Distance;
    

    public void setEdgeID(int data) {
        edgeID = data;
    }

    public void setStart_NodeID(int data) {
        start_NodeID = data;
    }

    public void setEnd_NodeID(int data) {
    	end_NodeID = data;
    }

    public void setL2Distance(double data) {
    	l2Distance = data;
    }
    
    public int getEdgeID() {
        return edgeID;
    }

    public int getStart_NodeID() {
        return start_NodeID;
    }

    public int getEnd_NodeID() {
    	return end_NodeID;
    }

    public double getL2Distance() {
    	return l2Distance;
    }

}
