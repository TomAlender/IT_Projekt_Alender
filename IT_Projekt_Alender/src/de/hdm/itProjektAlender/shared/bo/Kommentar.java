package de.hdm.itProjektAlender.shared.bo;

public class Kommentar extends Textbeitrag {
	
	private static final long serialVersionUID = 1L;

	
	private Integer beitrag_Id;

	public Integer getBeitrag_Id() {
		return beitrag_Id;
	}

	public void setBeitrag_Id(Integer beitrag_Id) {
		this.beitrag_Id = beitrag_Id;
	}
	
	public String toString(){
		return "<table><tr><td>"+this.getText()+"</td><td>"+this.getErstellungszeitpunkt()+"</td></tr></table>";
	
	}

}
