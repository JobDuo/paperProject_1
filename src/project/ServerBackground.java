package project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * ���� ��� �����ؾ� �ϰ� Ŭ���̾�Ʈ �����ؾ���
 */

public class ServerBackground {
	
	//�̽�1. �޼����� �ְ�޴´�.
	//�̽�2. GUI
	//�̼�3. ����
	private ServerSocket serverSocket;	//���� ����
	private Socket socket; //��
	
	//����� ������ �����ϴ� ��
	private ArrayList<Car> carList = new ArrayList<Car>();
	
	private AddMapData addmapData = new AddMapData();	//�� ������
	
	private ArrayList<Car> carArray = new ArrayList<Car>();	//�ڵ��� ��ȯ
	
	//����
	private MainView gui;
	public final void setGui(MainView gui){
		this.gui = gui;
		
	}
	
	
	
	
	public void setting(){
		
		
		try {
			
			//Collections.synchronizedMap(carList);	//���� ���� ���ݴϴ�.
			serverSocket = new ServerSocket(7777);	//7777 ��Ʈ ����
			//������ ���� = �ݺ��ؼ� ����ڸ� �޴´�.
			
			
			while(true){
				//������ �湮�ڸ� ��� �ް� ������ ���ù��� ��� ����
				System.out.println("��� ��..");
				socket = serverSocket.accept();
				System.out.println(socket.getInetAddress() + "���� ������ �߽��ϴ�.");
				//�̰����� ���ο� ����� ������ Ŭ���� �����ؼ� ���� ������ �־�����Ѵ�.
				
				Receiver receiver = new Receiver(socket);
				receiver.start();
			}
			
			
			} catch (IOException e) {
				System.out.println("##");
			}
		
	}
	

	
	//���� ���� Ŭ���̾�Ʈ ����� ����
	public void addClient(Car nick) {
		// TODO Auto-generated method stub
		//sendMessage(nick + " ���� ���� �ϼ̽��ϴ�.");
		//clientsMap.put(nick, out);
		carList.add(nick);
	}
	public void removeClient(Car nick){	//�����
		//sendMessage(nick + " ���� �����̽��ϴ�.");
		//clientsMap.remove(nick);
		carList.remove(nick);
	}
	
	//�ڵ��� ��ȯ
	public ArrayList<Car> get_Transportation_Car(){
		return carArray;
	}
	
	
	//���ù� Ŭ����
	class Receiver extends Thread{
		//���ù��� ������ ��Ʈ��ũ ������ �޾Ƽ� ��� ���, ��û�ϴ� ��
		
		private Car car;
		private ObjectInputStream objectInputStream = null;
		
		
		
		
		//��Ʈ��ũ ó�� ����ؼ� ���
		public Receiver(Socket socket) {
			System.out.println("���ù� ����");
			try{
				car = new Car(0);
				// TODO Auto-generated constructor stub

				objectInputStream = new ObjectInputStream(socket.getInputStream());
				
				//Ŭ���̾�Ʈ ���̵�	ó�� �������� ������ �г���
				car = (Car) objectInputStream.readObject();
				addClient(car);
				
				System.out.println(car.get_Car_Id() + " �� ���� ���� �Ͽ����ϴ�.");
				
				
				/**
				 * ���ӵ� ������ ������ �ѷ�����Ѵ�.
				 */
				addmapData.set_Transportation_Car(car);	//�ʵ����͸� ���� ���۹��� ������ �����Ѵ�.
				carArray = addmapData.get_Transportation_Car();	//����� �ڵ����� ��ȯ�Ѵ�.
				
				gui.addTransportationCar();	//������ ����Ҷ����� ���� gui = mainView
				
				
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("����");
			}
			
		}
		
		@Override
		public void run() {
			try{
				
				//��� ��⸸
				while(objectInputStream!=null){
					
					car = (Car) objectInputStream.readObject();
					
					System.out.println(car.get_Car_Id() + " �� ������ ��� ��ȣ" + car.getNodeID());
				}
				
			}catch (Exception e) {
				// TODO: handle exception
				//������� ����� ���⼭ ���� �߻�, ������ �Ǵ°�
				removeClient(car);
			}
			
		}
		
	}
}











