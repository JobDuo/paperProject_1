package project;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientBackground {

	private Socket socket;
	
	private ObjectOutputStream objectOutputStream;
	
	Car car = new Car(0);
	
	//����
	private ClientGui gui;
	public final void setGui(ClientGui gui){
		this.gui = gui;
	}
	


	public void connet(Car car){
		
		try {
			
				
				socket = new Socket("127.0.0.1", 7777);
				System.out.println("���� �����.");
			
				objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				
				//���޹��� �ڵ����� ������ ������ �����Ѵ�.
				this.car = car;
				objectOutputStream.writeObject(car);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	//����
		
	}
	
	

	public void sendMessage(Car car) {
		// TODO Auto-generated method stub
		try {
			
			objectOutputStream.writeObject(car);	//������ �ڵ��� ����
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}















