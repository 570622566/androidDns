package net.shenru.dns;

public class Answer {

	// Name: www.taobao.com.danuoyi.tbcache.com
	// Type: A (Host Address) (1)
	// Class: IN (0x0001)
	// Time to live: 317
	// Data length: 4
	// Address: 116.211.92.241

	private String name;
	private String type;
	private String answerClass;
	private String time; 
	private String data;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAnswerClass() {
		return answerClass;
	}

	public void setAnswerClass(String answerClass) {
		this.answerClass = answerClass;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

 
	 

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	class AnswerBuild {

	}
}
