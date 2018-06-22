package lib;

public class Shop {
	String ShopNo;
	String ShopName;
	double ShopPrice;
	int ShopNum;
	
	//¸³Öµ
	public void setShopNo(String no) {
		this.ShopNo = no;
	}
	public void setShopName(String name) {
		this.ShopName = name;
	}
	public void setShopPrice(double price) {
		this.ShopPrice= price;
	}
	public void setShopNum(int num) {
		this.ShopNum = num;
	}
	
	//È¡Öµ
	public String getShopNo() {
		return ShopNo;
	}
	public String getShopName() {
		return ShopName;
	}	
	public double getShopPrice() {
		return ShopPrice;
	}
	public int getShopNum() {
		return ShopNum;
	}
	
}
