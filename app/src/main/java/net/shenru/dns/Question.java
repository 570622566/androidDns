package net.shenru.dns;

public class Question {

	private String name;
	private String type;
	private String questionClass; 
	
	
	
	
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




	public String getQuestionClass() {
		return questionClass;
	}




	public void setQuestionClass(String questionClass) {
		this.questionClass = questionClass;
	}




	class QuestionBuild{
		
	}
}
