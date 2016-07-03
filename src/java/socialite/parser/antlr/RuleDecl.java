package socialite.parser.antlr;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

import socialite.collection.SArrayList;
import socialite.parser.Literal;
import socialite.parser.Const;
import socialite.parser.Predicate;

public class RuleDecl implements Externalizable {
    private static final long serialVersionUID = 1;

    public Predicate head;
    public SArrayList<Literal> body;
    transient ArrayList<Predicate> bodyP;

    boolean simpleUpdate = false;

    public RuleDecl() {}

    public RuleDecl(Predicate _head, List<Literal> _body) {
        head = _head;
        body = new SArrayList<>(_body);
    }

    public String toString() {
        String result = head.toString() + " :- ";
        for (int i = 0; i < body.size(); i++) {
            Object o = body.get(i);
            boolean noComma = false;
            if (o instanceof Const) {
                Const c = (Const) o;
                result += c.constValStr();
            } else {
                result += o;
                if (("" + o).length() == 0)
                    noComma = true;
            }
            if (i != body.size() - 1 && !noComma) result += ",";
        }
        result += ".";
        return result;
    }

    public boolean isSimpleUpdate() {
        return simpleUpdate;
    }

    public List<Predicate> getBodyP() {
        if (bodyP == null) {
            bodyP = new ArrayList(body.size());
            for (Literal l:body) {
                if (l instanceof Predicate) {
                    bodyP.add((Predicate)l);
                }
            }
        }
        return bodyP;
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        head = (Predicate) in.readObject();
        body = new SArrayList<>(0);
        body.readExternal(in);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(head);
        body.writeExternal(out);
    }
}
