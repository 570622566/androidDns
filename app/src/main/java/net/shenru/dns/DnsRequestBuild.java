package net.shenru.dns;

import java.util.List;

public class DnsRequestBuild {

	private String transactionId;
	private String falgs;
	private List<Question> questions;

	public DnsRequestBuild() {

	}

	public DnsRequestBuild setTransactionId(String transactionId) {
		this.transactionId = transactionId;
		return this;
	}

	public DnsRequestBuild setFalgs(String response, String opcode, String authoritative, String truncated, String rd,
			String ra) {
		String falgsStr = response + opcode + authoritative + truncated + rd + ra + "000000";
		System.out.println("falgsStr:" + falgsStr);

		String falgHex = Integer.toHexString(Integer.valueOf(falgsStr, 2));
		System.out.println("falgHex:" + falgHex);
		this.falgs =DnsUtil.padding(falgHex, 4) ;
		return this;
	}

	public DnsRequestBuild setQuestions(List<Question> questions) {
		this.questions = questions;
		return this;
	}

	public String build() {
		String result = transactionId;
		result += falgs;

		result += questions != null ? DnsUtil.padding(Integer.toHexString(questions.size()), 4) : "0000";
		result += "0000";
		result += "0000";
		result += "0000";
		if (questions != null) {
			for (Question question : questions) {
				result += DnsUtil.generateQuestion(question);
			}
		}
		return result;
	}

}
