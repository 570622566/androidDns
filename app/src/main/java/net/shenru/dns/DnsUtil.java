package net.shenru.dns;

/**
 * dns工具类
 * @author xtdhwl
 *
 */
public class DnsUtil {

	/**
	 * 创建域名
	 * @param name
	 * @return
	 */
	public static String createName(String name) {
		String result = "";
		for (String s : name.split("\\.")) {
			String length = Integer.toHexString(s.length());
			if (length.length() == 1) {
				result += "0";
			}
			result += length;
			for (int i = 0; i < s.length(); i++) {
				int charInt = s.charAt(i);
				result += Integer.toHexString(charInt);
			}

		}
		return result + "00";
	}

	/**
	 * 填充
	 * @param str
	 * @param length
	 * @return
	 */
	public static String padding(String str, int length) {
		String result = str;
		while (result.length() < length) {
			result = "0" + result;
		}
		return result;
	}

	/**
	 * 生成问题
	 * @param question
	 * @return
	 */
	public static String generateQuestion(Question question) {
		String result = "";
		String name = DnsUtil.createName(question.getName());
		String type = question.getType();
		String claszz = question.getQuestionClass();
		result += name;
		result += type;
		result += claszz;
		return result;
	}

	/**
	 * 生成答案. TODO 只支持Address
	 * @param hex
	 * @param answer
	 * @return
	 */
	public static String generateAnswer(String hex, Answer answer) {
		String result = "";
		String name = DnsUtil.createName(answer.getName());

		int nameIndexOf = hex.indexOf(name);
		if (nameIndexOf != -1) {
			result += "c0";
			result += DnsUtil.padding(Integer.toHexString(nameIndexOf / 2), 2);
		} else {
			result += name;
		}

		result += answer.getType();
		result += answer.getAnswerClass();
		result += answer.getTime();

		String data = answer.getData();
		String hexData = "";
		for (String s : data.split("\\.")) {
			hexData += DnsUtil.padding(Integer.toHexString(Integer.valueOf(s)), 2);
		}

		result += DnsUtil.padding((hexData.length() / 2) + "", 4);
		result += hexData;
		return result;
	}
}
