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
 * 메인 뷰와 연결해야 하고 클라이언트 복사해야함
 */

public class ServerBackground {
	
	//이슈1. 메세지를 주고받는다.
	//이슈2. GUI
	//이수3. 연동
	private ServerSocket serverSocket;	//서버 소켓
	private Socket socket; //귓
	
	//사용자 정보를 저장하는 맵
	private ArrayList<Car> carList = new ArrayList<Car>();
	
	private AddMapData addmapData = new AddMapData();	//맵 데이터
	
	private ArrayList<Car> carArray = new ArrayList<Car>();	//자동차 반환
	
	//연동
	private MainView gui;
	public final void setGui(MainView gui){
		this.gui = gui;
		
	}
	
	
	
	
	public void setting(){
		
		
		try {
			
			//Collections.synchronizedMap(carList);	//교총 정리 해줍니다.
			serverSocket = new ServerSocket(7777);	//7777 포트 오픈
			//서버가 할일 = 반복해서 사용자를 받는다.
			
			
			while(true){
				//서버는 방문자를 계속 받고 쓰레드 리시버를 계속 생성
				System.out.println("대기 중..");
				socket = serverSocket.accept();
				System.out.println(socket.getInetAddress() + "에서 접속을 했습니다.");
				//이곳에서 새로운 사용자 쓰레드 클래스 생성해서 소켓 정보를 넣어줘야한다.
				
				Receiver receiver = new Receiver(socket);
				receiver.start();
			}
			
			
			} catch (IOException e) {
				System.out.println("##");
			}
		
	}
	

	
	//맵의 내용 클라이언트 저장과 삭제
	public void addClient(Car nick) {
		// TODO Auto-generated method stub
		//sendMessage(nick + " 님이 접속 하셨습니다.");
		//clientsMap.put(nick, out);
		carList.add(nick);
	}
	public void removeClient(Car nick){	//지우기
		//sendMessage(nick + " 님이 나가셨습니다.");
		//clientsMap.remove(nick);
		carList.remove(nick);
	}
	
	//자동차 반환
	public ArrayList<Car> get_Transportation_Car(){
		return carArray;
	}
	
	
	//리시버 클래스
	class Receiver extends Thread{
		//리시버가 할일은 네트워크 소켓을 받아서 계속 듣고, 요청하는 일
		
		private Car car;
		private ObjectInputStream objectInputStream = null;
		
		
		
		
		//네트워크 처리 계속해서 듣기
		public Receiver(Socket socket) {
			System.out.println("리시버 생성");
			try{
				car = new Car(0);
				// TODO Auto-generated constructor stub

				objectInputStream = new ObjectInputStream(socket.getInputStream());
				
				//클라이언트 아이디	처음 보내지는 정보는 닉네임
				car = (Car) objectInputStream.readObject();
				addClient(car);
				
				System.out.println(car.get_Car_Id() + " 번 차량 접속 하였습니다.");
				
				
				/**
				 * 접속된 차량을 지도에 뿌려줘야한다.
				 */
				addmapData.set_Transportation_Car(car);	//맵데이터를 통해 전송받은 차량을 셋팅한다.
				carArray = addmapData.get_Transportation_Car();	//통신한 자동차를 반환한다.
				
				gui.addTransportationCar();	//서버와 통신할때마다 갱신 gui = mainView
				
				
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("에러");
			}
			
		}
		
		@Override
		public void run() {
			try{
				
				//계속 듣기만
				while(objectInputStream!=null){
					
					car = (Car) objectInputStream.readObject();
					
					System.out.println(car.get_Car_Id() + " 번 차량의 노드 번호" + car.getNodeID());
				}
				
			}catch (Exception e) {
				// TODO: handle exception
				//사용접속 종료시 여기서 에러 발생, 나가게 되는것
				removeClient(car);
			}
			
		}
		
	}
}











