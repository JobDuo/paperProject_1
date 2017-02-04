/*-
 *�ۼ���: ���缺
 *�˰��� ���� http://rosettacode.org/wiki/Dijkstra's_algorithm#Java
 *�� �˰����� �˸°� �����Ͽ� �ۼ��߽��ϴ�.
 *�� �˰����� �ð����⵵ O(log n)
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
