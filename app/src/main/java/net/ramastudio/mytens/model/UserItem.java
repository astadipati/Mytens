package net.ramastudio.mytens.model;

import com.google.gson.annotations.SerializedName;

public class UserItem{

	@SerializedName("nik")
	private String nik;

	@SerializedName("password")
	private String password;

	@SerializedName("noHP")
	private String noHP;

	@SerializedName("id")
	private String id;

	public void setNik(String nik){
		this.nik = nik;
	}

	public String getNik(){
		return nik;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setNoHP(String noHP){
		this.noHP = noHP;
	}

	public String getNoHP(){
		return noHP;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"UserItem{" + 
			"nik = '" + nik + '\'' + 
			",password = '" + password + '\'' + 
			",noHP = '" + noHP + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}