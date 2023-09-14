package se.fluff.aoc2022.days;

import org.w3c.dom.NodeList;
import se.fluff.aoc.AocDay;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;

/**
 * Created by Fluff on 2022-12-13.
 */
public class Day13 extends AocDay {



    public Day13() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        PacketComparator pc = new PacketComparator();
        int score = 0;
        int pairnumber = 0;
        String left = "";
        String right = "";

        while(in.hasNext()) {

            String l = in.nextLine();
            if(l.equals(""))
                continue;

            if(left.equals("")) {
                left = l;
            }
            else if(right.equals("")) {
                right = l;
            }


            if(!left.equals("") && !right.equals("")) {
                pairnumber++;
                //System.out.println("== Pair " + pairnumber + " ==");
                Node lnode = parse(new StringReader(left));
                Node rnode = parse(new StringReader(right));

                int res = pc.compare(lnode, rnode);
                if(res < 0) {
                    score += pairnumber;
                    //System.out.println("++ In correct order, score is " + score);
                }
                left = right = "";
            }
        }

        return String.valueOf(score);
    }

    @Override
    public String b(Scanner in, boolean isTest) throws Exception {
        PacketComparator pc = new PacketComparator();
        int score = 1;
        ArrayList<Node> dividers = new ArrayList<>();
        dividers.add(parse(new StringReader("[[2]]")));
        dividers.add(parse(new StringReader("[[6]]")));
        ArrayList<Node> nodes = new ArrayList<>(dividers);

        while(in.hasNext()) {

            String l = in.nextLine();
            if(l.equals(""))
                continue;

            nodes.add(parse(new StringReader(l)));

        }

        nodes.sort(pc);
        for(int i = 0; i < nodes.size(); i++) {
            if(dividers.contains(nodes.get(i))) {
                score *= (i + 1);
            }
        }


        return String.valueOf(score);
    }

    public static Node parse(Reader in) throws IOException {
        PacketScanner scanner = new PacketScanner(in);
        scanner.nextToken();
        return parse(scanner);
    }

    private static Node parse(PacketScanner scanner) throws IOException {
        switch (scanner.getToken()) {
            case NUMBER:
                int value = scanner.getNumber();
                scanner.nextToken();
                return new NumberNode(value);
            case LPAR:
                scanner.nextToken();
                List<Node> nodes = parseList(scanner);
                if (scanner.getToken() != PacketScanner.Token.RPAR) {
                    throw new RuntimeException(") expected");
                }
                scanner.nextToken();
                return new ListNode(nodes);
            default:
                throw new RuntimeException("Number or ( expected");
        }
    }

    private static List<Node> parseList(PacketScanner scanner) throws IOException {
        List<Node> nodes = new ArrayList<>();
        if (scanner.getToken() != PacketScanner.Token.RPAR) {
            nodes.add(parse(scanner));
            while (scanner.getToken() == PacketScanner.Token.COMMA) {
                scanner.nextToken();
                nodes.add(parse(scanner));
            }
        }
        return nodes;
    }

}

abstract class Node {

}

class NumberNode extends Node {
    private final int number;

    public NumberNode(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }

}

class ListNode extends Node {
    private final List<Node> list = new ArrayList<>();

    public ListNode(Collection<Node> nodes) {
        list.addAll(nodes);
    }

    public Node get(int pos) {
        return list.get(pos);
    }

    public boolean has(int pos) {
        return list.size() - 1 >= pos;
    }

    public int getLength() {
        return list.size();
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append('[');
        boolean first = true;
        for (Node node: list) {
            if (first) {
                first = false;
            } else {
                buf.append(',');
            }
            buf.append(node);
        }
        buf.append(']');
        return buf.toString();
    }
}


class PacketScanner {
    private final Reader in;
    private int c;
    private Token token;
    private int number;

    public enum Token {LPAR,RPAR,NUMBER,COMMA,EOF};

    public PacketScanner(Reader in) throws IOException {
        this.in = in;
        c = in.read();
    }

    public Token getToken() {
        return token;
    }

    public int getNumber() {
        return number;
    }

    public Token nextToken() throws IOException {
        while (c == ' ') {
            c = in.read();
        }
        if (c < 0) {
            return token = Token.EOF;
        }
        if (c >= '0' && c <= '9') {
            number = c - '0';
            c = in.read();
            while (c >= '0' && c <= '9') {
                number = 10*number + (c-'0');
                c = in.read();
            }
            return token = Token.NUMBER;
        }
        switch (c) {
            case '[':
                c = in.read();
                return token = Token.LPAR;
            case ']':
                c = in.read();
                return token = Token.RPAR;
            case ',':
                c = in.read();
                return token = Token.COMMA;
            default:
                throw new RuntimeException("Unknown character " + c);
        }
    }
}

class PacketComparator implements Comparator<Node> {

    @Override
    public int compare(Node left, Node right) {
        if(left instanceof NumberNode && right instanceof NumberNode) {
            //System.out.println("Compare " + ((NumberNode) left).getNumber() + " vs " + ((NumberNode) right).getNumber());
            return Integer.compare(((NumberNode) left).getNumber(), ((NumberNode) right).getNumber());
        }
        else if(left instanceof ListNode && right instanceof ListNode) {
            int res = 0;
            ListNode lleft = (ListNode) left;
            ListNode lright = (ListNode) right;
            //System.out.println("Compare " + left + " vs " + right);
            for(int i = 0; i < lleft.getLength(); i++) {
                //System.out.println("At sublist pos " + i);
                Node subleft = lleft.get(i);
                if(!lright.has(i)) {
                    //System.out.println("Right side is shorter");
                    return 1;
                }
                Node subright = lright.get(i);
                res = compare(subleft, subright);
                if(res != 0) {
                    return res;
                }
            }

            if(lleft.getLength() == lright.getLength()) {
                return 0;
            }
            return -1;
        }
        else {
            ListNode lleft = null;
            ListNode lright = null;
            //System.out.println("Mixed types");
            if(left instanceof NumberNode) {

                ArrayList<Node> oneElementList = new ArrayList<>();
                oneElementList.add(left);

                lleft = new ListNode(oneElementList);
            }
            else
                lleft = (ListNode) left;
            if(right instanceof NumberNode) {
                ArrayList<Node> oneElementList = new ArrayList<>();
                oneElementList.add(right);
                lright = new ListNode(oneElementList);
            }
            else
                lright = (ListNode) right;

            return compare(lleft, lright);
        }
    }
}