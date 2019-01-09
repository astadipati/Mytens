package net.ramastudio.mytens.model;

import com.google.gson.annotations.SerializedName;

public class Dokumen{

	@SerializedName("nik")
	private String nik;

	@SerializedName("noHP")
	private String noHP;

	public void setNik(String nik){
		this.nik = nik;
	}

	public String getNik(){
		return nik;
	}

	public void setNoHP(String noHP){
		this.noHP = noHP;
	}

	public String getNoHP(){
		return noHP;
	}

	@Override
 	public String toString(){
		return 
			"Dokumen{" + 
			"nik = '" + nik + '\'' + 
			",noHP = '" + noHP + '\'' + 
			"}";
		}
}