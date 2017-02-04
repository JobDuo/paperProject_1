/*-
 *작성자: 장재성
 *알고리즘 참고 http://rosettacode.org/wiki/Dijkstra's_algorithm#Java
 *위 알고리즘을 알맞게 변형하여 작성했습니다.
 *위 알고리즘의 시간복잡도 O(log n)
 *
 */
package project;
public class SF_cnode {

    private double normalizedX;
    private double normalizedY;
  

    // Primary key is the NodeID
   
    //Node ID	Normalized X Coordinate	Normalized Y Coordinate	Point of Interest
  
    private int nodeID;

    public void setNodeID(int data) {
    	nodeID = data;
    }

    public void setNormalizedX(double data) {
    	normalizedX = data;
    }

    public void setNormalizedY(double data) {
    	normalizedY = data;
    }

  

    public int getNodeID() {
    	return nodeID;
    }

    public double getNormalizedX() {
    	return normalizedX;
    }

    public double getNormalizedY() {
    	return normalizedY;
    }

  
   
}
