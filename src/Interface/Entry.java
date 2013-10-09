import java.util.Random;

public class Entry{

	String sName, uName, pw, IV;

	public Entry(String s_name, String u_name, String pass){
		sName = s_name;
		uName = u_name;
		pw = pass;
		IV = "";
		byte[] bIV = new byte[16];
		Random rand = new Random();
		rand.nextBytes(bIV);
		for(int i = 0; i < bIV.length; i++){
			IV+=bIV[i];
		}
	}

	public void setServiceName(String s_name){
		sName = s_name;
	}

	public void setUserName(String u_name){
		uName = u_name;
	}

	public void setPassword(String pass){
		pw = pass;
	}

	public String getServiceName(){
		return sName;
	}

	public String getUserName(){
		return uName;
	}

	public String getPassword(){
		return pw;
	}
}
