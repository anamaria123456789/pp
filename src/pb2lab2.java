import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;


public class pb2lab2 {
    public static void main(String[] args){
        Context polyglot = Context.create();
        Value array = polyglot.eval("js","[\"If\".\"we\".\"run\".\"the\".\"java\".\"command\".\"");
        for(int i = 0;i < array.getArraySize();i++){
            String element = array.getArrayElement(i).asString();
            String upper = RToUpper(element);
            int crc = SumCRC(upper);
            System.out.println(upper + " -> " + crc);
        }
    }
    private static String RToUpper(String token){
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        Value result = polyglot.eval("R",
                "toupper(\""+token+"\");");
        return result.asString();
    }
    private static int SumCRC(String token){
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        Value result = polyglot.eval("python","sum((5*i**5 + 4*i**4 + 3*i**3 + 2*i**2 + i + 1) * ord(ch) for i, ch in enumerate('"+ token + "'))");
        return result.asInt();

    }

}
