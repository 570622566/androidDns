package net.shenru.dns;

/**
 * Created by xtdhwl on 3/15/17.
 */

public class DnsGenerate {

    public static String str = "c6df81800001000200000000037777770674616f62616f03636f6d0000010001c00c000500010000016c0021037777770674616f62616f03636f6d0764616e756f79690774626361636865c017c02c000100010000013d000474d35cf1";

//	Domain Name System (response)
//    [Request In: 763]
//    [Time: 0.000268410 seconds]
//    Transaction ID: 0xc6df
//    Flags: 0x8180 Standard query response, No error
//        1... .... .... .... = Response: Message is a response
//        .000 0... .... .... = Opcode: Standard query (0)
//        .... .0.. .... .... = Authoritative: Server is not an authority for domain
//        .... ..0. .... .... = Truncated: Message is not truncated
//        .... ...1 .... .... = Recursion desired: Do query recursively
//        .... .... 1... .... = Recursion available: Server can do recursive queries
//        .... .... .0.. .... = Z: reserved (0)
//        .... .... ..0. .... = Answer authenticated: Answer/authority portion was not authenticated by the server
//        .... .... ...0 .... = Non-authenticated data: Unacceptable
//        .... .... .... 0000 = Reply code: No error (0)
//    Questions: 1
//    Answer RRs: 2
//    Authority RRs: 0
//    Additional RRs: 0
//    Queries
//        www.taobao.com: type A, class IN
//            Name: www.taobao.com
//            [Name Length: 14]
//            [Label Count: 3]
//            Type: A (Host Address) (1)
//            Class: IN (0x0001)
//    Answers
//        www.taobao.com: type CNAME, class IN, cname www.taobao.com.danuoyi.tbcache.com
//            Name: www.taobao.com
//            Type: CNAME (Canonical NAME for an alias) (5)
//            Class: IN (0x0001)
//            Time to live: 364
//            Data length: 33
//            CNAME: www.taobao.com.danuoyi.tbcache.com
//        www.taobao.com.danuoyi.tbcache.com: type A, class IN, addr 116.211.92.241
//            Name: www.taobao.com.danuoyi.tbcache.com
//            Type: A (Host Address) (1)
//            Class: IN (0x0001)
//            Time to live: 317
//            Data length: 4
//            Address: 116.211.92.241

//	0000   c6 df 81 80 00 01 00 02 00 00 00 00 03 77 77 77  .............www
//	0010   06 74 61 6f 62 61 6f 03 63 6f 6d 00 00 01 00 01  .taobao.com.....
//	0020   c0 0c 00 05 00 01 00 00 01 6c 00 21 03 77 77 77  .........l.!.www
//	0030   06 74 61 6f 62 61 6f 03 63 6f 6d 07 64 61 6e 75  .taobao.com.danu
//	0040   6f 79 69 07 74 62 63 61 63 68 65 c0 17 c0 2c 00  oyi.tbcache...,.
//	0050   01 00 01 00 00 01 3d 00 04 74 d3 5c f1           ......=..t.\.


    public static void main(String[] args) {
        String transactionId = "c6df";
        String falgs = "8180";
        String questions = "0001";
        String answerRRs = "0002";
        String authorityRRs = "0000";
        String additionalRRs = "0000";
        String nameStr = createQueries("www.taobao.com");
        System.out.println(nameStr);
    }

    private static String createQueries(String nameStr) {
        String result = "";
        String name = createName(nameStr);
        String type = "0001";
        String claszz = "0001";
        result += name;
        result += type;
        result += claszz;
        return result;
    }

    private static String createName(String name) {
        String result = "";
        for(String s : name.split("\\.")){
            String length =Integer.toHexString(s.length());
            if(length.length() == 1 ){
                result += "0";
            }
            result += length;
            for(int i =0; i< s.length();i++){
                int charInt = s.charAt(i);
                result += Integer.toHexString(charInt);
            }

        }
        return result;
    }
}
