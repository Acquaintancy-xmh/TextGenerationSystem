package action;

import java.util.function.Predicate;

public class BookBean {
	private String ISBN;// 编号
	private String title;// 图书名称
	private double price;// 定价
	private String publishdate;
	private int authorid;// 作者
	private String publisher;
	private String name;
	private int age;
	private String country;
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPublishDate() {
		return publishdate;
	}

	public void setPublishDate(String publishdate) {
		this.publishdate = publishdate;
	}
	
	public int getAuthorID() {
		return authorid;
	}

	public void setAuthorID(int authorid) {
		this.authorid = authorid;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

}
