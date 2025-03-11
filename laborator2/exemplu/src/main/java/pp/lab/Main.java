package pp.lab;//import libraria principala polyglot din graalvm

import org.graalvm.polyglot.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class Polyglot {

    private static String RToUpper(String token){

        Context polyglot = Context.newBuilder().allowAllAccess(true).build();

        String resultString = token.toUpperCase();
        polyglot.close();

        return resultString;
    }

    private static int SumCRC(String token){
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();

        Value result = polyglot.eval("python", "sum(ord(ch) for ch in '" + token + "')");
        int resultInt = result.asInt();
        polyglot.close();

        return resultInt;
    }

    private static Map<Integer, List<String>> groupWordsByCRC(Value array) {
        Map<Integer, List<String>> groupedWords = new HashMap<>();

        for (int i = 0; i < array.getArraySize(); i++) {
            String element = RToUpper(array.getArrayElement(i).asString());
            int crc = SumCRC(element);

            groupedWords.putIfAbsent(crc, new ArrayList<>());
            groupedWords.get(crc).add(element);
        }

        return groupedWords;
    }

    public static void main(String[] args) {
        Context polyglot = Context.create("js", "python");
        Value array = polyglot.eval("js", """
        [\"If\",\"we\",\"run\",\"the\",\"java\",\"command\",\"included\",\"in\",\"GraalVM\",
        \"we\",\"will\",\"be\",\"automatically\",\"using\",\"the\",\"Graal\",\"JIT\",\"compiler\",
        \"no\",\"extra\",\"configuration\",\"is\",\"needed\",
        \"I\",\"will\",\"use\",\"the\",\"time\",\"command\",\"to\",\"get\",\"the\",\"real\",\"wall\",\"clock\",\"elapsed\",\"time\",
        \"it\",\"takes\",\"to\",\"run\",\"the\",\"entire\",\"program\",\"from\",\"start\",\"to\",\"finish\",
        \"rather\",\"than\",\"setting\",\"up\",\"a\",\"complicated\",\"micro\",\"benchmark\",
        \"and\",\"I\",\"will\",\"use\",\"a\",\"large\",\"input\",
        \"so\",\"that\",\"we\",\"arent\",\"quibbling\",\"about\",\"a\",\"few\",\"seconds\",\"here\",\"or\",\"there\",
        \"The\",\"large.txt\",\"file\",\"is\",\"150\",\"MB\"];
        """);
        Map<Integer, List<String>> groupedWords = groupWordsByCRC(array);

        for (Map.Entry<Integer, List<String>> entry : groupedWords.entrySet()) {
            System.out.println("Checksum " + entry.getKey() + ": " + entry.getValue());
        }

        polyglot.close();
    }
}
