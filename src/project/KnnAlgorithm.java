package project;

import java.util.ArrayList;

class KnnAlgorithm {

	//�������� ���� ��ġ�κ��� �����Ÿ��� ���� ����� ���� k + a ���� ����ȣ�� ��ȯ�Ѵ�.

	int[] nodeNum;
	int bigTemp;
	
	double query_x;
	double query_y;
	double carElement_x;
	double carElement_y;
	double base;
	double side;
	double diagonal;
	double big;
	
	double[] shortestDist;
	
	//ArrayList<Car> carArray = new ArrayList<Car>();		//���� �ڵ��� ��ü
	ArrayList<Car> thisCarArray = new ArrayList<Car>();
	AddMapData addMapData = new AddMapData();
	ArrayList<Car> nearCarArray = new ArrayList<Car>();
	int temp = 1;
	int iTempCarCount;
	public ArrayList<Car> KnnAlgorithm(SF_cnode queryP, ArrayList<Car> carArray, int k, int totalCarNum, double userQ_Max_Dist){
		
		//�� ������ �� ���� ã���� �ϴ� ������ ���� �� ������ �ĺ����� ���� �ʿ䰡����.
		if(totalCarNum/2 >= k){
			temp = 2;
		}else {
			temp = 1;
		}
		nodeNum = new int[k*temp];
		//thisCarArray = new Car[k*temp];
		shortestDist = new double[k*temp];
		thisCarArray.clear();
		iTempCarCount = 0;
		query_x = queryP.getNormalizedX();
		query_y = queryP.getNormalizedY();
		
		for(int i=0; i<carArray.size(); i++){
			
			
			carElement_x = carArray.get(i).getPoint_x();
			carElement_y = carArray.get(i).getPoint_y();
			
			base = Math.pow(query_x - carElement_x, 2);
			side = Math.pow(query_y - carElement_y, 2);
			diagonal = base + side;
			
			//System.out.println("userQ_Max_Dist" + userQ_Max_Dist);
			if(userQ_Max_Dist < Math.sqrt(diagonal)){
				//continue;	//���� ��ġ�κ��� ������ ��ġ���� ũ�� ����
			}
			//System.out.println(" Math.sqrt(diagonal) : " +  Math.sqrt(diagonal));
			
			if(iTempCarCount < (k*temp)){
				//thisCarArray[i] = carArray.get(i);
				thisCarArray.add(carArray.get(i));
				thisCarArray.get(iTempCarCount).setNodeID(carArray.get(i).getNodeID());
				shortestDist[iTempCarCount] = diagonal;
			}else {
				for(int j=0; j<k*temp; j++){	//�տ��� ä�� k*2 ������ �Ÿ��� ���� ��� ����
					if(big < shortestDist[j]){
						   big = shortestDist[j];
						   bigTemp = j;
					   }
				}big = 0;
				
				if(shortestDist[bigTemp] > diagonal){
					   shortestDist[bigTemp] = diagonal;
					   thisCarArray.set(bigTemp, carArray.get(i));
					   //thisCarArray[bigTemp].setNodeID(carArray.get(i).getNodeID());
				   }
				
			}
			iTempCarCount++;	//�����Ǵ� ������ �����ϰ� ī��Ʈ �ؾ��Ѵ�.
		}
		
		
		//addMapData.addNearCar(thisCarArray);	//����� ���� ����
		/**
		 * knn �˰����� ������ �����߰�
		 */
			for(int i=0; i<thisCarArray.size(); i++){
				nearCarArray.add(thisCarArray.get(i));
			}
			
		
		return nearCarArray;
	}
	
}
/*
if(i < 3){	//�迭 3���� ������ ����
	   shortestCar[i] = Integer.parseInt(END);//3���� ������ ����
	   shortestDist[i] = dist;				  //3������ �Ÿ�
}else {
	   
	   itemp = Integer.parseInt(END);
	   
	   for(int j=0; j<3; j++){		//�տ��� ä�� 3������ �Ÿ��� ���� ��� ����
		   if(big < shortestDist[j]){
			   big = shortestDist[j];
			   bigTemp = j;
		   }
	   }big = 0;
	   
	   if(shortestDist[bigTemp] > dist){
		   shortestDist[bigTemp] = dist;
		   shortestCar[bigTemp] = itemp;
	   }
	   
	   
	   //shortestCar[0] = Integer.parseInt(END);
	   //shortestCar �� 3���� ���� ū ���� small�� �ְ� �����Ѵ�.
	   //small = dist;
	   
	   
}*/