package project;

class Car {
	
	private int nodeID;
	private int[] neighborNode;
	
	private double point_x;
	private double point_y;
	
	/**
	 * ���� ��ġ�� ����� ��ġ���̵�
	 */
	public void setNodeID(int data) {
    	nodeID = data;
    }
	
	/**
	 * ���� ��ġ
	 */
	public void setPoint_x(double x){
		point_x = x;
	}
	public void setPoint_y(double y){
		point_y = y;
	}
	public double getPoint_x(){
		return point_x;
	}
	public double getPoint_y(){
		return point_y;
	}
	public int getNodeID(){
		return nodeID;
	}
	
	/**
	 *  ������ �� �� �ִ� �ֺ� ���
	 */
	public void setNeighborNode(int nodeCount){
		neighborNode = new int[nodeCount];
		for(int i=0; i<nodeCount; i++){
			//nodeCount[i] = 
		}
	}
	
	
}
