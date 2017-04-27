package project;

import java.io.Serializable;

class Car implements Serializable{
	
	private int nodeID;			//�ڵ����� ��ġ�� �����ġ
	private int[] neighborNode;
	private int second;			//�ڵ��� ��ġ �۽����� ���� �帥�ð�
	private int carId;			//�ڵ��� ������ȣ
	
	private double point_x;		//�ڵ��� ��ġ
	private double point_y;
	private double km;			//�ڵ��� �ü�

	private double weight = 130;
	
	
	private double ad = 0; //�ڵ����� ���� �ִ� ��� ������ ��
	private double gama = 0; //�ڵ����� ������
	private double dist_Direct = 0; //�ڵ����� ������
	
	/**
	 * �ڵ��� ������ȣ
	 */
	Car(int carId){
		this.carId = carId;
	}
	public void set_Car_Id(int carId){
		this.carId = carId;
	}
	
	/**
	 * �ڵ��� ������ȣ ��ȯ
	 */
	public int get_Car_Id(){
		return carId;
	}
	
	/**
	 * �ڵ����� ���Ÿ�
	 */
	public void set_Ad(double ad){
		this.ad = ad;
	}
	public double get_Ad(){
		return ad;
	}
	
	/**
	 * �ڵ����� ������
	 */
	public void set_Gama(double gama){
		this.gama = gama;
	}
	public double get_Gama(){
		return gama;
	}
	
	/**
	 * �����Ÿ�
	 */
	public void set_Dist_Direct(double dist_Direct){
		this.dist_Direct = dist_Direct;
	}
	public double get_Dist_Direct(){
		return dist_Direct;
	}
	
	
	
	
	/**
	 * �ڵ����� ����ġ
	 */
	public double get_Car_Dist(){
		return weight;	//������ ����ġ
	}
	public void set_Car_Dist(double w){
		weight = w;	//������ ����ġ
	}
	
	
	/**
	 * ���� ��ġ�� ����� ��ġ���̵�
	 */
	public void setNodeID(int data) {
    	nodeID = data;
    }
	
	/**
	 * ������ �ü�
	 */
	public void setKm(int km){
		this.km = km;
		this.km = km;
	}
	public double getKm(){
		return km;
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
