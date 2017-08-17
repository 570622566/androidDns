package net.shenru.dns;

import java.util.List;

public class DnsResponseBuild {

	private String transactionId;
	private String falgs;
	private List<Question> questions;
	private List<Answer> answers;

	public DnsResponseBuild() {

	}

	public DnsResponseBuild setTransactionId(String transactionId) {
		this.transactionId = transactionId;
		return this;
	}

	public DnsResponseBuild setFalgs(String response, String opcode, String authoritative, String truncated, String rd,
			String ra) {
		String falgsStr = response + opcode + authoritative + truncated + rd + ra + "000" + "0000";
//		System.out.println("falgsStr:" + falgsStr);

		String falgHex = Integer.toHexString(Integer.valueOf(falgsStr, 2));
//		System.out.println("falgHex:" + falgHex);
		this.falgs = falgHex;
		return this;
	}

	public DnsResponseBuild setQuestions(List<Question> questions) {
		this.questions = questions;
		return this;
	}

	public DnsResponseBuild setAnswers(List<Answer> answers) {
		this.answers = answers;
		return this;
	}

	public String build() {
		String result = transactionId;
		result += falgs;

		result += questions != null ? DnsUtil.padding(Integer.toHexString(questions.size()), 4) : "0000";
		result += questions != null ? DnsUtil.padding(Integer.toHexString(answers.size()), 4) : "0000";
		result += "0000";
		result += "0000";
		if (questions != null) {
			for (Question question : questions) {
				result += DnsUtil.generateQuestion(question);
			}
		}

		if (answers != null) {
			for (Answer answer : answers) {
				result += DnsUtil.generateAnswer(result, answer);
			}
		}

		return result;
	}

}
