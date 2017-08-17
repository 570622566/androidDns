package net.shenru.dns;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xtdhwl on 3/15/17.
 */

public class DnsParse {

    private static String str = "dcc3010000010000000000000d6e6f74696669636174696f6e7306676f6f676c6503636f6d0000010001";

    /**
     * Transaction ID: 0x153c
     * Flags: 0x8180 Standard query response, No error
     * Questions: 1
     * Answer RRs: 1
     * Authority RRs: 0
     * Additional RRs: 0
     * Queries
     *  www.google.com: type A, class IN
     *  Name: www.google.com
     *  [Name Length: 14]
     *  [Label Count: 3]
     *  Type: A (Host Address) (1)
     *  Class: IN (0x0001)
     *
     * Answers
     * 	www.google.com: type A, class IN, addr 78.16.49.15
     *  Name: www.google.com
     *  Type: A (Host Address) (1)
     *  Class: IN (0x0001)
     *  Time to live: 719
     *  Data length: 4
     *  Address: 78.16.49.15
     */
    private static String str2 = "153c818000010001000000000377777706676f6f676c6503636f6d0000010001c00c00010001000002cf00044e10310f";
    private static String str3 = "90ff8180000100030000000003777777066b65726e656c036f72670000010001c00c0005000100001a2f000603707562c010c02c000500010000016e000a03707562036e7274c010c03e0001000100004c6c0004934b6ebb";
    private static String str4 = "7faa8180000100050000000003696d6706616c6963646e03636f6d0000010001c00c00050001000085b7001903696d6706616c6963646e03636f6d0764616e756f7969c010c02c00010001000001ff0004b4959b32c02c00010001000001ff0004dcb58df0c02c00010001000001ff0004dcb58dfac02c00010001000001ff0004b4959b28";
    private static String str5 = "eaee81800001000c000000000361646d077368696e6f6269026a700000010001c00c00010001000000dd0004708c2a80c00c00010001000000dd0004708c2a89c00c00010001000000dd0004708c2a8ec00c00010001000000dd0004708c2a9bc00c00010001000000dd0004708c2a81c00c00010001000000dd0004708c2a83c00c00010001000000dd0004708c2a90c00c00010001000000dd0004708c2a87c00c00010001000000dd0004708c2a9cc00c00010001000000dd0004708c2a8cc00c00010001000000dd0004708c2a88c00c00010001000000dd0004708c2a82";
    private static String str6 = "c6df40c00001000100000000037777770674616f62616f03636f6d0000010001c00c000100010000013d000474d35cf1";

    // 153c818000010001000000000377777706676f6f676c6503636f6d0000010001c00c00010001000002cf00044e10310f

    private String content;
    private String transactionId;
    private String falgs;
    private String questions;
    private String answerRRs;
    private String authorityRRs;
    private String additionalRRs;
    private List<Question> questionList = new ArrayList<>();
    private List<Answer> answerList = new ArrayList<>();

    public  List<Question> getQuestionList(){
        return this.questionList;
    }



    public String getTransactionId() {
        return transactionId;
    }



    public String getFalgs() {
        return falgs;
    }



    public String getAnswerRRs() {
        return answerRRs;
    }



    public String getAuthorityRRs() {
        return authorityRRs;
    }



    public String getAdditionalRRs() {
        return additionalRRs;
    }



    public DnsParse(String content) {
        this.content = content;
    }

    public boolean parse() {

        ByteBuffer buffer = ByteBuffer.wrap(this.content.getBytes());

        byte[] transactionIdByte = new byte[4];
        byte[] falgsByte = new byte[4];
        byte[] questionsByte = new byte[4];
        byte[] answerRRsByte = new byte[4];
        byte[] authorityRRsByte = new byte[4];
        byte[] additionalRRsyte = new byte[4];

        buffer.get(transactionIdByte);
        buffer.get(falgsByte);
        buffer.get(questionsByte);
        buffer.get(answerRRsByte);
        buffer.get(authorityRRsByte);
        buffer.get(additionalRRsyte);

        transactionId = new String(transactionIdByte);
        falgs = new String(falgsByte);
        questions = new String(questionsByte);
        answerRRs = new String(answerRRsByte);
        authorityRRs = new String(authorityRRsByte);
        additionalRRs = new String(additionalRRsyte);

//        System.out.println("transactionId	:" + transactionId);
//        System.out.println("falgs			:" + falgs);
//        System.out.println("questions		:" + questions);
//        System.out.println("answerRRs		:" + answerRRs);
//        System.out.println("authorityRRs	:" + authorityRRs);
//        System.out.println("additionalRRs	:" + additionalRRs);

        int questionsInt = Integer.parseInt(questions, 16);
        if (questionsInt != 0) {
//            System.out.println("===============questions================");
            questionList = parseQuerie(buffer, questionsInt);
        }

        int answerRRsInt = Integer.parseInt(answerRRs, 16);
        if (answerRRsInt != 0) {
//            System.out.println("===============answerRRs================");
            answerList = parseAnswerRRs(buffer, answerRRsInt);
        }
        return true;
    }

    private List<Answer> parseAnswerRRs(ByteBuffer buffer, int count) {
        List<Answer> answers = new ArrayList<>();
        while (count != 0) {
            count--;

            Answer answer = new Answer();

            String name = parseName(buffer);

            byte[] typeByte = new byte[4];
            byte[] classByte = new byte[4];
            byte[] timeByte = new byte[8];
            byte[] dataLenghtByte = new byte[2];

            buffer.get(typeByte);
            buffer.get(classByte);
            buffer.get(timeByte);
            buffer.get(dataLenghtByte);

            String type = new String(typeByte);
            String cla = new String(classByte);
            String time = new String(timeByte);
//            System.out.println("Name:" + name);
//            System.out.println("Type:" + type);
//            System.out.println("Class:" + cla);
//            System.out.println("Time:" + time);
//            System.out.println("Data:" + new String(dataLenghtByte));

            if ("0005".equals(type)) {
                String data = parseName(buffer);
                answer.setData(data);
            } else {
                int dataLen = Integer.parseInt(new String(dataLenghtByte), 16);
                byte[] dataByte = new byte[dataLen * 2];
                buffer.get(dataByte);
                String data = new String(dataByte);
                answer.setData(data);
            }

            answer.setName(name);
            answer.setType(type);
            answer.setAnswerClass(cla);
            answer.setTime(time);

            answers.add(answer);
        }

        return answers;
    }

    private static List<Question> parseQuerie(ByteBuffer buffer, int count) {
        List<Question> questions = new ArrayList<>();
        while (count != 0) {
            count--;
            Question answer = new Question();

            String name = parseName(buffer);
            byte[] typeByte = new byte[4];
            byte[] classByte = new byte[4];
            buffer.get(typeByte);
            buffer.get(classByte);

            String type = new String(typeByte);
            String cla = new String(classByte);

            answer.setName(name);
            answer.setType(type);
            answer.setQuestionClass(cla);
//            System.out.println("Name:" + name);
//            System.out.println("Type:" + type);
//            System.out.println("Class:" + cla);

            questions.add(answer);
        }
        return questions;
    }

    private static String parseName(ByteBuffer buffer) {
        int pointLen = 0;
        String name = "";
        do {
            byte[] byteTag = new byte[2];
            buffer.get(byteTag);
            String stringTag = new String(byteTag);
            if ("c0".equals(stringTag)) {
                if (!"".equals(name)) {
                    name += ".";
                }
                byte[] pointIndexByte = new byte[2];
                buffer.get(pointIndexByte);
                int pointIndex = Integer.parseInt(new String(pointIndexByte), 16);
                int position = buffer.position();
                buffer.position(pointIndex * 2);
                name += parseName(buffer);
                buffer.position(position);
                return name;
            } else {
                pointLen = Integer.parseInt(new String(byteTag), 16);
                if (pointLen != 0) {
                    if (!"".equals(name)) {
                        name += ".";
                    }
                    byte[] nameByte = new byte[pointLen * 2];
                    buffer.get(nameByte);
                    String nameStr = new String(nameByte);
                    for (int j = 0; j < nameStr.length(); j += 2) {
                        String nameHex = nameStr.substring(j, j + 2);
                        name += (char) Integer.parseInt(nameHex, 16);
                        // System.out.println("==========:" + name);
                    }
                }
            }
        } while (pointLen != 0);
        return name;
    }
}

