package beans;

public class departments {

	private int _id;
	private String _name;

	//Branches.idのセッター・ゲッター
	public void setId(int id){
		this._id = id;
	}
	public int getId(){
		return _id;
	}

	//Branches.nameのセッター・ゲッター
	public void setName(String name){
		this._name = name;
	}
	public String getName(){
		return _name;
	}
}