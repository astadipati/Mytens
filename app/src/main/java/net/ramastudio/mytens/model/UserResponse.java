package net.ramastudio.mytens.model;

import com.google.gson.annotations.SerializedName;

public class UserResponse{

	@SerializedName("dokumen")
	private Dokumen dokumen;

	@SerializedName("proses")
	private String proses;

	public void setDokumen(Dokumen dokumen){
		this.dokumen = dokumen;
	}

	public Dokumen getDokumen(){
		return dokumen;
	}

	public void setProses(String proses){
		this.proses = proses;
	}

	public String getProses(){
		return proses;
	}

	@Override
 	public String toString(){
		return 
			"UserResponse{" + 
			"dokumen = '" + dokumen + '\'' + 
			",proses = '" + proses + '\'' + 
			"}";
		}
}